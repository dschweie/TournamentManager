package org.dos.tournament.branch.petanque.tournament.movement;

import java.util.Random;
import java.util.Deque;
import java.util.ArrayDeque;
import java.util.ArrayList;

import org.dos.tournament.application.common.panels.AbstractTournamentPanel;
import org.dos.tournament.common.competition.AbstractTournament;
import org.dos.tournament.common.movement.IFinals;
import org.dos.tournament.common.movement.utils.IMatchdayGenerator;
import org.dos.tournament.common.movement.utils.MatchdayGeneratorEngine;
import org.dos.tournament.common.player.utils.ParticipantStatus;
import org.dos.tournament.branch.petanque.result.PetanqueMatchResult;
import org.dos.tournament.branch.petanque.result.PetanqueResult;
import org.dos.tournament.branch.petanque.team.AbstractPetanqueTeam;
import org.dos.tournament.branch.petanque.team.PetanqueBye;
import org.dos.tournament.branch.petanque.tournament.matchday.Matchday;
import org.dos.tournament.branch.petanque.tournament.movement.swisssystem.ISwissMatchdayGeneration;
import org.dos.tournament.branch.petanque.tournament.movement.swisssystem.SwissMatchdayGeneratorRulesEngine;
import org.dos.tournament.branch.petanque.tournament.partie.Partie;
import org.dos.tournament.branch.petanque.tournament.partie.PartieTerrain;
import org.dos.tournament.branch.petanque.tournament.partie.Terrain;

public class FormeeSwissSystem extends AbstractTournament implements IMatchdayGenerator
{
  private ArrayList<AbstractPetanqueTeam> competitors = new ArrayList<>();
  private ArrayList<Matchday> matchdays = new ArrayList<>();
  private ArrayList<Terrain> terrains = null;
  private IFinals finals = new TwoFinals();
  private int iMatchdaysToPlay = 3;


  @Override
  public AbstractTournamentPanel getManagementPanel()
  {
    // TODO Auto-generated method stub
    return null;
  }

  /**
   *  \brief    Methode fügt den Teilnehmern eine weitere Mannschaft hinzu
   *  \param    team          Objekt repräsentiert die Mannschaft, die dem
   *                          Feld hinzugefügt wird.
   *  \return   Die Methode liefert im Rückgabewert die Information, ob das
   *            Einfügen erfolgreich war.
   */
  public boolean addCompetitor(AbstractPetanqueTeam team)
  {
    boolean _retval = false;

    if(!this.competitors.contains(team))
      _retval = this.competitors.add(team);

    return _retval;
  }

  public int getMatchdayCount()
  {
    int _retval = -1;
    if(null!=this.matchdays)
      _retval = this.matchdays.size();
    return _retval;
  }

  public int getMatchCount(int matchday)
  {
    int _retval = -1;
    if(null!=this.matchdays)
      if(matchday<this.matchdays.size())
        _retval = this.matchdays.get(matchday).countMatches();
    return _retval;
  }

  public String getMatchdayAsString(int matchday)
  {
    String _retval = "n.a.";
    if(null!=this.matchdays)
      if(matchday<this.matchdays.size())
        _retval = this.matchdays.get(matchday).toString();
    return _retval;
  }

  public boolean setResultForPartie(int matchday, int partie, int home, int guest)
  {
    try
    {
      this.matchdays.get(matchday).getMatch(partie).setResult(new PetanqueResult(home, guest));
      this.matchdays.get(matchday).getMatch(partie).getCompetitor(0).addResultOfMatchday(matchday, new PetanqueMatchResult(home, guest));
      this.matchdays.get(matchday).getMatch(partie).getCompetitor(1).addResultOfMatchday(matchday, new PetanqueMatchResult(guest, home));
      return true;
    }
    catch(Exception e)
    {
      System.out.println(e.getMessage());
      return false;
    }
  }

  /**
   *  \brief    Methode generiert den nächsten Spieltag/Runde
   *
   *  In der Methode wird die MatchdayGeneratorEngine genutzt, um den Ablauf
   *  festzulegen.
   *
   *  Dieses hat den wesentlichen Vorteil, dass der immer gleiche Ablauf in
   *  einer einheitlichen Prozedur gekapselt ist.
   *
   *  \see      MatchdayGeneratorEngine
   */
  public void generateNextMatchday()
  {
    MatchdayGeneratorEngine.execute(this);
  }

  /**
   *  \brief    Methode liefert die Teams, die den Status "aktiv" haben
   *
   *  Die Methode filtert aus den Teams des Turniers alle Teams aus, die
   *  aufgrund des aktuellen Status im Turnier sind und an weiteren Runden
   *  teilnehmen können.
   *
   *  \return
   */
  protected ArrayList<AbstractPetanqueTeam> getActiveCompetitors()
  {
    ArrayList<AbstractPetanqueTeam> _pool = new ArrayList<>();
    for(int i=0; i < this.competitors.size(); ++i)
    {
      if(ParticipantStatus.ACTIVE == this.competitors.get(i).getStatus())
        _pool.add(this.competitors.get(i));
    }
    return _pool;
  }

  /**
   *  \brief    Methode liefert die Information, ob der erste Spieltag/Runde
   *            anzulegen ist
   *
   *  Diese Methode implementiert eine Methode, die durch das Interface
   *  IMatchdayGenerator gefordert wird.
   *
   *  \return   Die Methode liefert <code>true</code>, wenn der aktuelle
   *            Spieltag/Runde der erste ist.
   *
   *  \see      IMatchdayGenerator
   */
  @Override
  public boolean isFirstMatchday()
  {
    return this.matchdays.isEmpty();
  }

  /**
   *  \brief    Methode liefert die Information, ob für das Turnier Finalrunden
   *            vorgesehen sind
   *
   *  Diese Methode implementiert eine Methode, die durch das Interface
   *  IMatchdayGenerator gefordert wird.
   *
   *  \return   Die Methode liefert <code>true</code>, wenn das Turnier mit
   *            Finalrunden konfiguriert ist.
   *
   *  \see      IMatchdayGenerator
   */
  @Override
  public boolean isFinalsDefined()
  {
    return (null != this.finals);
  }

  /**
   *  \brief    Methode liefert die Information, ob der bereits alle
   *            Spieltage/Runden gespielt sind
   *
   *  Diese Methode implementiert eine Methode, die durch das Interface
   *  IMatchdayGenerator gefordert wird.
   *
   *  \see      IMatchdayGenerator
   */
  @Override
  public boolean isMatchdaysPlayed() {
    return !(this.matchdays.size() < this.iMatchdaysToPlay);
  }

  /**
   *  \brief
   *
   *  Diese Methode implementiert eine Methode, die durch das Interface
   *  IMatchdayGenerator gefordert wird.
   *
   *  \see      IMatchdayGenerator
   */
  @Override
  public void doGenerateFirstMatchday()
  {
    Matchday _matchday = new Matchday();
    ArrayList<AbstractPetanqueTeam> _pool = this.getActiveCompetitors();
    Random _randomizer = new Random();

    Deque<Terrain> _terrains = new ArrayDeque<>();

    int _idxBYE = -1;

    while(0 < _pool.size())
    {
      int _randomIndex;

      // Home ermitteln
      _randomIndex = _randomizer.nextInt(_pool.size());
      AbstractPetanqueTeam _home = _pool.get(_randomIndex);
      _pool.remove(_randomIndex);

      // Guest ermitteln
      AbstractPetanqueTeam _guest;
      if(0 == _pool.size())
      { // Freilos wird eingesetzt
        _guest = PetanqueBye.getBye();
        _idxBYE = _matchday.countMatches();
      }
      else
      {
        _randomIndex = _randomizer.nextInt(_pool.size());
        _guest = _pool.get(_randomIndex);
        _pool.remove(_randomIndex);
      }

      if(null == _terrains)
      { // in diesem Fall wird eine Begegnung ohne Platzuweisung erzeugt
        _matchday.addPartie(new Partie(_home, _guest));
      }
      else
        if(_terrains.isEmpty())
        { // in diesem Fall wird Terra Libre gespielt
          _matchday.addPartie(new PartieTerrain(Terrain.getTerraLibre(), _home, _guest));
        }
        else
        { // in diesem Fall wird Platz zugewiesen
          _matchday.addPartie(new PartieTerrain(_terrains.pop(), _home, _guest));
        }

    }

    this.matchdays.add(_matchday);

    if(-1 < _idxBYE)
      this.setResultForPartie(this.matchdays.size()-1, _idxBYE, 13, 7);
  }

  private ArrayList<Terrain> getAvailableTerrains()
  {
    // TODO Auto-generated method stub
    return null;
  }

  /**
   *  \brief    Methode liefert die Information, ob der erste Spieltag/Runde
   *            anzulegen ist
   *
   *  Diese Methode implementiert eine Methode, die durch das Interface
   *  IMatchdayGenerator gefordert wird.
   *
   *  \see      IMatchdayGenerator
   */
  @Override
  public void doGenerateEnsuingMatchday()
  {
    EnsuingMatchdayGenerator generator = new EnsuingMatchdayGenerator();
    this.matchdays.add(generator.nextMatchday());
    if("BYE".equals(this.matchdays.get(this.matchdays.size()-1).getMatch(this.matchdays.get(this.matchdays.size()-1).countMatches()-1).getCompetitor(1).getCode()))
      this.setResultForPartie(this.matchdays.size()-1, this.matchdays.get(this.matchdays.size()-1).countMatches() -1, 13, 7);
  }

  /**
   *  \brief    Methode liefert die Information, ob der erste Spieltag/Runde
   *            anzulegen ist
   *
   *  Diese Methode implementiert eine Methode, die durch das Interface
   *  IMatchdayGenerator gefordert wird.
   *
   *  \see      IMatchdayGenerator
   */
  @Override
  public void doGenerateFinals()
  {
  }

  /**
   *  \brief    Methode liefert die Information, ob der erste Spieltag/Runde
   *            anzulegen ist
   *
   *  Diese Methode implementiert eine Methode, die durch das Interface
   *  IMatchdayGenerator gefordert wird.
   *
   *  \see      IMatchdayGenerator
   */
  @Override
  public void doTrace(String dtName, String version, int rules, int rule) {
    // TODO Auto-generated method stub

  }

  private class EnsuingMatchdayGenerator implements ISwissMatchdayGeneration
  {
    private SwissMatchdayGeneratorRulesEngine engine = new SwissMatchdayGeneratorRulesEngine();
    private int iPotCounter = -1;
    private int iMatchdayCounter = 0;
    private Deque<Terrain> terrains = new ArrayDeque<>();
    private Deque<AbstractPetanqueTeam> competitors = new ArrayDeque<>();
    private ArrayList<ArrayList<AbstractPetanqueTeam>> pots = new ArrayList<>();
    private AbstractPetanqueTeam currentTeam = null;
    private AbstractPetanqueTeam currentHome = null;
    private AbstractPetanqueTeam currentGuest = null;
    private Matchday matchday = new Matchday();
    private Random randomizer = new Random();

    /**
     *  \brief  Methode generiert basierend auf den Informationen des Turniers
     *          einen nächsten Spieltag/Runde
     *
     *  Diese Methode ist die zentrale Methode, die aufgerufen werden kann, um
     *  einen Spieltag/Runde neu auszulosen. Die Methode ruft dazu das aus LF-ET
     *  generierte Regelwerk auf, das in der Klasse SwissMatchdayGeneratorRulesEngine
     *  zu finden ist.
     *
     *  \return Die Methode liefert einen Matchtag mit den ausgelosten Partien zurück.
     */
    public Matchday nextMatchday()
    {
      this.engine.execute(this);
      return this.matchday;
    }

    @Override
    public boolean isCounterInLastPot()
    {
      return (1 > this.iPotCounter);
    }

    @Override
    public boolean hasNextTeam()
    {
      return !this.competitors.isEmpty();
    }

    @Override
    public boolean isMatchdayCounterBehindLast()
    {
      return FormeeSwissSystem.this.matchdays.size() <= this.iMatchdayCounter;
    }

    @Override
    public boolean isTeamWinner()
    {
      return (this.currentTeam.getTotalScore() > this.iMatchdayCounter);
    }

    @Override
    public boolean isPotEmpty()
    {
      return this.pots.get(iPotCounter).isEmpty();
    }

    @Override
    public boolean isOneTeamLeftInPot()
    {
      return 1 == this.pots.get(iPotCounter).size();
    }

    @Override
    public void doReadActiveTeams()
    {
      this.competitors.addAll(FormeeSwissSystem.this.getActiveCompetitors());
    }

    @Override
    public void doReadAvailableTerrains()
    {
      // TODO Auto-generated method stub

    }

    @Override
    public void doCreatePots()
    {
      for(int i=0; i < (FormeeSwissSystem.this.getMatchdayCount() + 1); ++i)
        this.pots.add(new ArrayList<AbstractPetanqueTeam>());
    }

    @Override
    public void doSetTeamToFirstPot() {
      this.currentTeam = this.competitors.pop();
      this.pots.get(0).add(this.currentTeam);
    }

    @Override
    public void doPromoteTeamToNextPot()
    {
      int _idx = 0;
      boolean _bPromoted = false;

      while((!_bPromoted) && (_idx < (this.pots.size()-1)))
      {
        if(this.pots.get(_idx).contains(this.currentTeam))
        {
          this.pots.get(_idx).remove(currentTeam);
          _bPromoted = this.pots.get(_idx+1).add(currentTeam);
        }
        else
          ++_idx;
      }
    }

    @Override
    public void doDrawHomeFromPot()
    {
      this.currentHome = this.pots.get(this.iPotCounter).remove(this.randomizer.nextInt(this.pots.get(this.iPotCounter).size()));
    }

    @Override
    public void doDrawGuestFromPot()
    {
      this.currentGuest = this.pots.get(this.iPotCounter).remove(this.randomizer.nextInt(this.pots.get(this.iPotCounter).size()));
    }

    @Override
    public void doDrawGuestPromotedFromPot()
    {
      this.currentGuest = this.pots.get(this.iPotCounter-1).remove(this.randomizer.nextInt(this.pots.get(this.iPotCounter-1).size()));
    }

    @Override
    public void doSetGuestBye()
    {
      this.currentGuest = PetanqueBye.getBye();
    }

    @Override
    public void doCreatePartie()
    {
      if(null == this.terrains)
      { //  In diesem Fall wird davon ausgegangen, dass keine Platzwahl erfolgen soll.
        this.matchday.addPartie(new Partie(this.currentHome, this.currentGuest));
      }
      else
      {
        if(this.terrains.isEmpty())
        { //  In diesem Fall wird als Platz TerraLibre gesetzt, da es keinen Platz mehr gibt, der zugewiesen werden kann.
          this.matchday.addPartie(new PartieTerrain(Terrain.getTerraLibre(), this.currentHome, this.currentGuest));
        }
        else
        {
          this.matchday.addPartie(new PartieTerrain(this.terrains.pop(), this.currentHome, this.currentGuest));
        }
      }

      //  In diesem Schritt werden Home und Guest gelöscht, damit ein Team nicht versehentlich zweimal zugewiesen wird.
      this.currentHome = null;
      this.currentGuest = null;
    }

    @Override
    public void doResetCounterMatchday()
    {
      this.iMatchdayCounter = 0;
    }

    @Override
    public void doIncreaseCounterMatchday()
    {
      ++this.iMatchdayCounter;
    }

    @Override
    public void doInitCounterPot()
    {
      this.iPotCounter = (this.pots.size() - 1);
    }

    @Override
    public void doDecreaseCounterPot()
    {
      --this.iPotCounter;
    }

    @Override
    public void doTrace(String dtName, String version, int rules, int rule) {
      // TODO Auto-generated method stub

    }

  }
}
