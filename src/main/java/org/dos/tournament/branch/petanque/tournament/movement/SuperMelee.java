package org.dos.tournament.branch.petanque.tournament.movement;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.JDialog;
import javax.swing.ProgressMonitor;

import org.bson.Document;
import org.dos.tournament.application.common.dialogs.MatchdayProgressMonitor;
import org.dos.tournament.application.dialogs.petanque.movement.DialogSetRoundManually;
import org.dos.tournament.application.petanque.panels.PetanqueSuperMeleePanel;
import org.dos.tournament.branch.petanque.team.AbstractPetanqueTeam;
import org.dos.tournament.branch.petanque.team.Doublette;
import org.dos.tournament.branch.petanque.team.Triplette;
import org.dos.tournament.branch.petanque.tournament.matchday.Matchday;
import org.dos.tournament.branch.petanque.tournament.movement.regulations.CoreRuleSuperMeleeAllIndicesUnique;
import org.dos.tournament.branch.petanque.tournament.movement.regulations.RuleSuperMeleeNeverMeetOpponentTandem;
import org.dos.tournament.branch.petanque.tournament.movement.regulations.RuleSuperMeleeNeverMeetOpponentTwice;
import org.dos.tournament.branch.petanque.tournament.movement.regulations.RuleSuperMeleeNeverMeetTeammateTandem;
import org.dos.tournament.branch.petanque.tournament.movement.regulations.RuleSuperMeleeNeverMeetTeammateTwice;
import org.dos.tournament.branch.petanque.tournament.movement.regulations.RuleSuperMeleeNeverPlayTripletteTandem;
import org.dos.tournament.branch.petanque.tournament.movement.regulations.RuleSuperMeleeNeverPlayTripletteTwice;
import org.dos.tournament.branch.petanque.tournament.partie.Partie;
import org.dos.tournament.branch.petanque.tournament.utils.TournamentUtils;
import org.dos.tournament.common.competition.AbstractTournament;
import org.dos.tournament.common.competition.ITournament;
import org.dos.tournament.common.movement.regulations.Regulation;
import org.dos.tournament.common.player.IParticipant;
import org.dos.tournament.common.player.utils.NumericParticipantId;
import org.dos.tournament.common.player.utils.ParticipantStatus;
import org.dos.tournament.common.result.IResult;
import org.dos.tournament.common.storage.SingletonStorage;

public class SuperMelee extends AbstractTournament
{

  static final public char FLAG_NEVER_MET       = ' ';
  static final public char FLAG_WERE_TEAMMATES  = 'T';
  static final public char FLAG_WERE_OPPONENTS  = 'O';
  static final public char FLAG_INVALID_PAIR    = 'X';
  
  protected Vector<IParticipant> competitors;
  protected Vector<Matchday> matchdays;
  
  protected Vector<Partie> parties;
  protected Vector<AbstractPetanqueTeam> teams;

  protected int idxTeam = 0; 

  
  private boolean bRuleNotSamePartner = true;
  private boolean bRuleNotSameOpponent = true;
  private boolean bRuleNoTripletteTwice = true;
  
  private ProgressMonitor xProgressMonitor = null;      

  protected Regulation<SuperMelee, Vector<Vector<Integer>>, IParticipant> regulations;
  protected IResult xTrophy = null;
  
  private boolean runningGenerateRound = false;
  private boolean resultGenerateRound = false;
  private int iProcessMax = 100;
  private int iProcessCurrent = 0;
  private Thread thread;
  private MonitoringThread xMonitoring;
  
  /**
   * @return the bRuleNotSamePartner
   */
  public boolean isRuleNotSamePartnerActive()
  {
    return bRuleNotSamePartner;
  }

  /**
   * @param bRuleNotSamePartner the bRuleNotSamePartner to set
   */
  public void setRuleNotSamePartner(boolean bRuleNotSamePartner)
  {
    this.bRuleNotSamePartner = bRuleNotSamePartner;
  }

  /**
   * @return the bRuleNotSameOpponent
   */
  public boolean isRuleNotSameOpponentActive()
  {
    return bRuleNotSameOpponent;
  }

  /**
   * @param bRuleNotSameOpponent the bRuleNotSameOpponent to set
   */
  public void setRuleNotSameOpponent(boolean bRuleNotSameOpponent)
  {
    this.bRuleNotSameOpponent = bRuleNotSameOpponent;
  }

  /**
   * @return the bRuleNoTripletteTwice
   */
  public boolean isRuleNoTripletteTwiceActive()
  {
    return bRuleNoTripletteTwice;
  }

  /**
   * @param bRuleNoTripletteTwice the bRuleNoTripletteTwice to set
   */
  public void setRuleNoTripletteTwice(boolean bRuleNoTripletteTwice)
  {
    this.bRuleNoTripletteTwice = bRuleNoTripletteTwice;
  }

  /**
   * @return the xTrophy
   */
  public IResult getTrophy()
  {
    return xTrophy;
  }

  /**
   * @param xTrophy the xTrophy to set
   */
  protected void setTrophy(IResult trophy)
  {
    this.xTrophy = trophy;
  }
  
  public SuperMelee()
  {
    this.competitors = new Vector<IParticipant>();
    this.matchdays = new Vector<Matchday>();
    
    this.parties = new Vector<Partie>();
    this.teams = new Vector<AbstractPetanqueTeam>();
 
    
    this.regulations = new CoreRuleSuperMeleeAllIndicesUnique();
    this.regulations = new RuleSuperMeleeNeverMeetTeammateTandem(this.regulations, true, false);
    this.regulations = new RuleSuperMeleeNeverMeetTeammateTwice(this.regulations, true, false);
    this.regulations = new RuleSuperMeleeNeverPlayTripletteTandem(this.regulations, true, true);
    this.regulations = new RuleSuperMeleeNeverMeetOpponentTandem(this.regulations, true, true);
    this.regulations = new RuleSuperMeleeNeverPlayTripletteTwice(this.regulations, true, true);
    this.regulations = new RuleSuperMeleeNeverMeetOpponentTwice(this.regulations, true, true);
    
  }
  
  public Document toBsonDocument()
  {
    Document _doc = super.toBsonDocument();

    //  ---------------------------------------------------------------------------------
    //  Hinzufügen der Teilnehmer zum Document
    //  ---------------------------------------------------------------------------------
    if(!this.competitors.isEmpty())
    {
      List<Document> _participants = new ArrayList<Document>();
      this.competitors.forEach(it -> {
        Document _competitor = new Document();
        _competitor.append("code", new Integer(Integer.parseInt( it.getCode().trim())));
        _competitor.append("_id", it.getAttribute("_id"));
        _competitor.append("info", it.toString());
        
        _participants.add(_competitor);
      });
      _doc.append("participants", _participants);
    }
    
    //  ---------------------------------------------------------------------------------
    //  Hinzufügen der bereits gelosten Runden zum Document
    //  ---------------------------------------------------------------------------------
    if(!this.matchdays.isEmpty())
    {
      List<Document> _matchdays = new ArrayList<Document>();
      this.matchdays.forEach(it -> { _matchdays.add(it.toBsonDocument()); });
      _doc.append("matchdays", _matchdays);
    }
    
    return _doc;
  }
  
  /**
   * @return the competitors
   */
  public Vector<IParticipant> getCompetitors()
  {
    return competitors;
  }
  /**
   * @param competitors the competitors to set
   */
  protected void setCompetitors(Vector<IParticipant> competitors)
  {
    this.competitors = competitors;
  }
 
  public void addCompetitor(IParticipant competitor)
  {
    if(!this.competitors.contains(competitor))
    {
      this.competitors.addElement(competitor);
      this.setChanged();
      this.notifyObservers();
      this.clearChanged();
    }
  }
  
  public IParticipant getCompetitorByParticipantIdCode(String code)
  {
    IParticipant _retval = null;
    
    for(IParticipant it : this.getCompetitors())
    { //  durchlaufe alle Mitspieler aus dem Feld
      if(code.equals(it.getParticipantId().getCode()))
        _retval = it;
    }
    
    return _retval;
  }
  
  public int countCompetitors()
  {
    return this.competitors.size();
  }
  
  /**
   * @return the matchdays
   */
  protected Vector<Matchday> getMatchdays()
  {
    return matchdays;
  }
  /**
   * @param matchdays the matchdays to set
   */
  protected void setMatchdays(Vector<Matchday> matchdays)
  {
    this.matchdays = matchdays;
  }
  
  public Matchday getMatchday(int index)
  {
    return ((-1<index)&&(this.matchdays.size()>index))?this.matchdays.get(index):null;
  }
  
  public int countMatchdays()
  {
    return this.matchdays.size();
  }
  
  protected void addPartie(Partie partie)
  {
    this.parties.addElement(partie);
  }
  
  protected void addTeam(AbstractPetanqueTeam team)
  {
    this.teams.addElement(team);
  }
  
  protected boolean wereTeammates(IParticipant first, IParticipant second)
  {
    boolean _retval = false;
    
    for(int i=0; ((this.teams.size() > i) && ( false == _retval )); ++i)
      _retval = this.teams.get(i).contains(first) && this.teams.get(i).contains(second);
    
    return _retval;
  }

  protected boolean wereOpponents(IParticipant first, IParticipant second)
  {
    boolean _retval = false;
    
    for(int i=0; ((this.parties.size() > i) && ( !_retval )); ++i)
      _retval = this.parties.get(i).wereOpponents(first, second);
    
    return _retval;    
  }

  public void forceNotifyAll()
  {
    this.setChanged();
    
    //  Benutzer werden in die Datenbank geschrieben
    this.competitors.forEach(it ->  { if(null == it.getAttribute("_id")) SingletonStorage.getInstance().saveParticipant(it, true);});
    SingletonStorage.getInstance().saveTournament(this, true);
    
    this.notifyObservers();
    this.clearChanged();
  }

  /**
   *  \brief    Mit dieser Methode wird ein neuer "Spieltag" erzeugt.
   *  
   *  Diese Methode steht anderen Klassen als Schnittstelle zur Verfügung,
   *  um einen neuen Spieltag anzulegen. 
   *  
   *  In der Methode selbst wird lediglich die Entscheidung getroffen, ob
   *  \li       der erste Spieltag oder
   *  \li       ein weiterer Spieltag anzulegen ist.
   *  
   *  Anschließend wird an die entsprechende Methode delegiert.
   *  
   *  @return   Die Methode liefert im Rückgabewert die Information, ob 
   *            ein neuer Spieltag angelegt werden konnte.
   *            
   *  @see      org.dos.tournament.petanque.tournament.movement.SuperMeleeClubChampionship.generateFirstMatchday()
   *  @see      org.dos.tournament.petanque.tournament.movement.SuperMeleeClubChampionship.generateNextMatchdayByAlgorithm()
   */
  public boolean generateNextMatchday(Component parent)
  {
    this.runningGenerateRound = true;
    this.resultGenerateRound = true;
   
    this.xMonitoring = new MonitoringThread();
    
    this.thread = new Thread(this.xMonitoring);
    thread.start ();
    this.resultGenerateRound &= (0 == SuperMelee.this.countMatchdays())?SuperMelee.this.generateFirstMatchday():SuperMelee.this.generateNextMatchdayByAlgorithm();
    this.xMonitoring.setProgress(100);
    this.runningGenerateRound = false;
    
    try
    {
      this.xProgressMonitor.setProgress(10000);
    }
    catch(Exception e) { /* NOTHING TO DO */ }
    
    return this.resultGenerateRound;
  }
  
  protected boolean isNextMatchdayCanceled()
  {
    return (null==this.xProgressMonitor?false:this.xProgressMonitor.isCanceled());
  }
  
  protected void updateNextMatchdayProgressLeft(int leftValue)
  {
    if(null!=this.xProgressMonitor)
      this.xProgressMonitor.setProgress(this.xProgressMonitor.getMaximum()-leftValue);
  }

  protected void updateNextMatchdayProgress(int value)
  {
    if(null!=this.xProgressMonitor)
    {
      this.xProgressMonitor.setProgress(value);
    }
  }

  protected void setNextMatchdayProgressMaximum(int value)
  {
    if(null!=this.xProgressMonitor)
      this.xProgressMonitor.setMaximum(value);
  }

  public void setResult(int iMatchdayIndex, int iPartieIndex, int iHome, int iGuest)
  {
    // TODO Auto-generated method stub
    
  }

  protected boolean alreadyPlayedTriplette(IParticipant participant)
  {
    boolean _retval = false;
    
    for(int i=0; ((this.parties.size() > i) && ( !_retval )); ++i)
      _retval = this.parties.get(i).playedInTriplette(participant);
    
    return _retval;    
  }

  public void regenerateLastMatchday(Component panel)
  {
    int       _matchdayIndex  = this.countMatchdays()-1;
    Matchday  _matchday       = this.getMatchday(_matchdayIndex);
    if(0 == _matchday.countScoredMatches())
    { //  matchday has no scored and can be generated
      int _matches = _matchday.countMatches();
      for(int i = 0; i < _matches; ++i)
        this.parties.remove(_matchday.getMatch(i));
      this.matchdays.remove(_matchday);
      
      if(this.generateNextMatchday(panel))
      {
        this.setChanged();
        this.notifyObservers(new MatchdayUpdate(_matchdayIndex));
        this.clearChanged();
      }
    }
  }
  
  public void deleteLastMatchday()
  {
    int       _matchdayIndex  = this.countMatchdays()-1;
    Matchday  _matchday       = this.getMatchday(_matchdayIndex);
    if(0 == _matchday.countScoredMatches())
    { //  matchday has no scored and can be generated
      int _matches = _matchday.countMatches();
      for(int i = 0; i < _matches; ++i)
        this.parties.remove(_matchday.getMatch(i));
      this.matchdays.remove(_matchday);
    }
  }
  
  public boolean suspendWeakestRule()
  {
    return this.regulations.suspend();
  }
  
  public class MatchdayUpdate {
    private int matchday;
    
    public MatchdayUpdate(int matchday)
    {
      this.matchday = matchday;
    }

    /**
     * @return the matchday
     */
    public int getMatchday()
    {
      return matchday;
    }
  }

  public String getRegulationState() 
  {
    return this.regulations.toString();
  }

  /**
   *  \brief    Die Methode erzeugt einen neuen "Spieltag" mit den aktiven Teilnehmern.
   *  
   *  
   *  @return   Die Methode liefert im Rückgabewert die Information, ob ein 
   *            neuer Spieltag angelegt werden konnte.
   */
  protected boolean generateNextMatchdayByAlgorithm() {
    int _matchdaysExpected = this.countMatchdays() + 1;
    Vector<IParticipant> _members = TournamentUtils.filterParticipantsByStatus(this.getCompetitors(), ParticipantStatus.ACTIVE);
    Vector<Vector<Vector<Integer>>> _grid = compileGridTemplateForSupermelee(_members.size());
    
    this.iProcessMax = _grid.size() * 2;
    this.iProcessCurrent = 0;

    if(null != _grid)
    {
      Collections.shuffle(_members);
      this.regulations.init(this, this.countMatchdays(), _members);
      _grid = fillGridWithParticipants(_grid, _members);
      
      if(null != _grid)
        compileNextMatchDayFromGrid(_grid, _members);
      this.regulations.teardown();
    }
  
    return (_matchdaysExpected == this.countMatchdays());
  }

  public void compileNextMatchDayFromGrid(Vector<Vector<Vector<Integer>>> grid, Vector<IParticipant> participants) {
    if((null != grid) && (null != participants))
    {
      //  Partien bilden
      Matchday _matchday = new Matchday();
      for(int p = 0; p < grid.size(); ++p)
      {
        Partie _partie = new Partie();
        
        for(int t=0; t < grid.get(p).size(); ++t)
        {
          if( 3 == grid.get(p).get(t).size())
          {
            Triplette _tt = new Triplette(new NumericParticipantId(idxTeam++), participants.get(grid.get(p).get(t).get(0).intValue()), participants.get(grid.get(p).get(t).get(1).intValue()), participants.get(grid.get(p).get(t).get(2).intValue()));
            _partie.addParticipant(_tt);
            this.addTeam(_tt);
          }
          else
          {
            Doublette _td = new Doublette(new NumericParticipantId(idxTeam++), participants.get(grid.get(p).get(t).get(0).intValue()), participants.get(grid.get(p).get(t).get(1).intValue()));
            _partie.addParticipant(_td);
            this.addTeam(_td);
          }
        }
        
        _matchday.addPartie(_partie);
        this.addPartie(_partie);
      }
      this.getMatchdays().addElement(_matchday);
    }
  }

  private Vector<Vector<Vector<Integer>>> fillGridWithParticipants(Vector<Vector<Vector<Integer>>> grid, Vector<IParticipant> participants) {
    //  this counter should help tio avoid to long runnings of this algorithm
    long _stepper = 0;
    // Durchlaufe das Grid, um Plätze zu füllen
    int _idxPartie = 0;
    int _idxTeam = 0;
    int _idxSlot = 0;
    boolean _valid = true;
  
    while( (-1 < _idxPartie) && (grid.size() > _idxPartie) )
    { 
      while(( -1 < _idxTeam ) && ( grid.get(_idxPartie).size() > _idxTeam) )
      {
        while (( -1 < _idxSlot ) && ( grid.get(_idxPartie).get(_idxTeam).size() > _idxSlot ))
        {
          while(( -1 < _idxSlot ) &&  ( grid.get(_idxPartie).get(_idxTeam).size() > _idxSlot ) && grid.get(_idxPartie).get(_idxTeam).get(_idxSlot).intValue() < participants.size() )
          {
            grid.get(_idxPartie).get(_idxTeam).set(_idxSlot, new Integer(grid.get(_idxPartie).get(_idxTeam).get(_idxSlot).intValue() + 1) );
            
            _valid = grid.get(_idxPartie).get(_idxTeam).get(_idxSlot).intValue() < participants.size();
            
            if(_valid)
            {
              int[] _pointer = {_idxPartie, _idxTeam, _idxSlot};
              
              _valid &= this.regulations.isValid(_pointer, grid, participants);
              //System.out.println(String.format("Pruefung: in Summe     : %d ; %d ; %d ", _idxPartie, _idxTeam, _idxSlot).concat(String.valueOf(_valid)).concat(" => ").concat(grid.toString()));
            }
            if(_valid)
            {
              this.iProcessCurrent = (_idxPartie*2+_idxTeam);
              this.xMonitoring.setProgress(Math.floorDiv(this.iProcessCurrent * 100, this.iProcessMax));
              ++_idxSlot;
            }
          }
  
          if(( -1 < _idxSlot ) &&  ( grid.get(_idxPartie).get(_idxTeam).size() > _idxSlot ))
          {
            if(grid.get(_idxPartie).get(_idxTeam).get(_idxSlot).intValue() >= participants.size())
            { // Backtracking: Spieler zurücksetzen und Platz zurück gehen
              grid.get(_idxPartie).get(_idxTeam).set(_idxSlot, new Integer(-1) );
              --_idxSlot;
            }
          }
        }
        
        if(-1 == _idxSlot)
        { // 
          --_idxTeam;
          if(-1 < _idxTeam)
            _idxSlot = grid.get(_idxPartie).get(_idxTeam).size() - 1;
        }
        else
        {
          ++_idxTeam;
          _idxSlot=0;
        }
        
      }
      
      if(-1 == _idxTeam)
      {
        --_idxPartie;
        if(-1 < _idxPartie)
        {
          _idxTeam = grid.get(_idxPartie).size() - 1;
          _idxSlot = grid.get(_idxPartie).get(_idxTeam).size() - 1;
        }
      }
      else
      {
        ++_idxPartie;
        _idxTeam = 0;
      }
      
    }
    
    return (-1 == _idxPartie)?null:grid;
  }

  protected boolean generateFirstMatchday() {
    boolean _retval = false;
    Vector<IParticipant> _members = TournamentUtils.filterParticipantsByStatus(this.getCompetitors(), ParticipantStatus.ACTIVE);
    
    this.iProcessMax = _members.size();
    
    this.xMonitoring.setProgress(Math.floorDiv(this.iProcessMax - _members.size() * 100, this.iProcessMax));
    
    if(     ( 3 <  _members.size() )
        &&  ( 7 != _members.size() ) )
    { //  mindestens 4 aktive Teilnehmer werden benötigt, damit eine Runde erstellt werden kann
      
      
      Collections.shuffle(_members);
      Matchday _matchday = new Matchday();
      Partie _p = null;
      while(0 < _members.size())
      {
        _p = new Partie();
        AbstractPetanqueTeam _home = null;
        AbstractPetanqueTeam _guest = null;
        
        switch( _members.size() % 4 )
        {
          case 3:
          case 2:   //  in diesem Fall spielen zwei Triplette
                    _home   = new Triplette(new NumericParticipantId(idxTeam++), _members.remove(0), _members.remove(0), _members.remove(0));
                    _guest  = new Triplette(new NumericParticipantId(idxTeam++), _members.remove(0), _members.remove(0), _members.remove(0));
                    break;
          case 1:   //  in diesem Fall spielt ein Triplette gegen ein Doublette
                    _home   = new Triplette(new NumericParticipantId(idxTeam++), _members.remove(0), _members.remove(0), _members.remove(0));
                    _guest  = new Doublette(new NumericParticipantId(idxTeam++), _members.remove(0), _members.remove(0));
                    break;
          default:  //  in diesem Fall werden zwei Doublette gebildet
                    _home   = new Doublette(new NumericParticipantId(idxTeam++), _members.remove(0), _members.remove(0));
                    _guest  = new Doublette(new NumericParticipantId(idxTeam++), _members.remove(0), _members.remove(0));
                    break;
        }
  
        _p.addParticipant(_home);
        _p.addParticipant(_guest);       
        _matchday.addPartie(_p);
        
        this.addPartie(_p);
        this.addTeam(_home);
        this.addTeam(_guest);
  
        this.iProcessCurrent = this.iProcessMax - _members.size();
        this.xMonitoring.setProgress(Math.floorDiv(this.iProcessMax - _members.size() * 100, this.iProcessMax));
      }
      
      this.getMatchdays().addElement(_matchday);
      
      _retval = true;   //  Flag wird gesetzt, da nächste Runde erstellt ist
    }
    
    
    return _retval;
  }

  public String getMatchdayAsString(int number) {
    int _idx = number - 1;
    
    if(     ( -1 < _idx                         ) 
        &&  ( this.getMatchdays().size() > _idx ) )
    {
      return this.getMatchdays().get(_idx).toStringWithNames();
    }
    else
      return "";
  }

  protected Vector<Vector<Vector<Integer>>> compileGridTemplateForSupermelee(int competitors) {
    int _membersLeft = competitors;
    Vector<Vector<Vector<Integer>>> _retval = null;
    
    if(     ( 3 <  competitors )
        &&  ( 7 != competitors ) )
    { //  mindestens 4 aktive Teilnehmer werden benötigt, damit eine Runde erstellt werden kann
    
      _retval = new Vector<Vector<Vector<Integer>>>();
      // Bilde das Grid für die Runde
      for(int i=0; ( competitors / 4 ) > i; ++i)
      {
        Vector<Integer> _home;
        Vector<Integer> _guest;
        
        switch( _membersLeft % 4 )
        {
          case 3:
          case 2:   //  in diesem Fall spielen zwei Triplette
                    _home  = new Vector<Integer>(3); _home.add(new Integer(-1)); _home.add(new Integer(-1)); _home.add(new Integer(-1)); 
                    _guest = new Vector<Integer>(3); _guest.add(new Integer(-1)); _guest.add(new Integer(-1)); _guest.add(new Integer(-1));
                    _membersLeft -= 6;
                    break;
          case 1:   //  in diesem Fall spielt ein Triplette gegen ein Doublette
                    _home  = new Vector<Integer>(3); _home.add(new Integer(-1)); _home.add(new Integer(-1)); _home.add(new Integer(-1)); 
                    _guest = new Vector<Integer>(2); _guest.add(new Integer(-1)); _guest.add(new Integer(-1));
                    _membersLeft -= 5;
                    break;
          default:  //  in diesem Fall werden zwei Doublette gebildet
                    _home  = new Vector<Integer>(2); _home.add(new Integer(-1)); _home.add(new Integer(-1));
                    _guest = new Vector<Integer>(2); _guest.add(new Integer(-1)); _guest.add(new Integer(-1));
                    _membersLeft -= 4;
                    break;
        }
          
        Vector<Vector<Integer>> _partie = new Vector<Vector<Integer>>(2);
        _partie.addElement(_home);
        _partie.addElement(_guest);
        _retval.add(_partie);
      }
    }
    return _retval;
  }
  
  public AbstractAction getActionCreateRoundManually()
  {
    return new CreateRoundManually();
  } 
  
  private class CreateRoundManually extends AbstractAction 
  {
    public CreateRoundManually() {
      putValue(NAME, ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("Supermelee.Action.CreateRoundManually.name")); //$NON-NLS-1$ //$NON-NLS-2$
      putValue(SHORT_DESCRIPTION, ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("Supermelee.Action.CreateRoundManually.short description")); //$NON-NLS-1$ //$NON-NLS-2$
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
      Vector<IParticipant> _participants = TournamentUtils.filterParticipantsByStatus(SuperMelee.this.getCompetitors(), ParticipantStatus.ACTIVE);
      Vector<Vector<Vector<Integer>>> _grid = SuperMelee.this.compileGridTemplateForSupermelee(_participants.size());
      JDialog dialog = new DialogSetRoundManually(SuperMelee.this, _grid, _participants);
      dialog.setModal(true);
      dialog.setVisible(true);
    }
    
  }

  private class MonitoringThread implements Runnable
  {
    private int iMaximum = 100;
    private int iProgress = 7;
    
    public void run() 
    {
      ProgressMonitor pm =   new ProgressMonitor(null, "Auslosung läuft", "zwei", iProgress, iMaximum);
      while(iProgress<iMaximum)
      {
        pm.setProgress(iProgress);
      }
      pm.close();
    }
    
    public void setProgress(int value)
    {
      this.iProgress = value;
    }
  }
}
