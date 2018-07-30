package org.dos.tournament.petanque.tournament.movement;

import java.util.Vector;

import org.dos.tournament.petanque.team.AbstractPetanqueTeam;
import org.dos.tournament.petanque.tournament.matchday.Matchday;
import org.dos.tournament.petanque.tournament.partie.Partie;
import org.dos.tournament.player.IParticipant;

public class SuperMelee
{
  private Vector<IParticipant> competitors;
  private Vector<Matchday> matchdays;
  
  private Vector<Partie> parties;
  private Vector<AbstractPetanqueTeam> teams;
  
  public SuperMelee()
  {
    this.competitors = new Vector<IParticipant>();
    this.matchdays = new Vector<Matchday>();
    
    this.parties = new Vector<Partie>();
    this.teams = new Vector<AbstractPetanqueTeam>();
  }
  
  /**
   * @return the competitors
   */
  protected Vector<IParticipant> getCompetitors()
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
      this.competitors.addElement(competitor);
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
}
