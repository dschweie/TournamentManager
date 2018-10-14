package org.dos.tournament.player;

import java.util.Collection;
import java.util.Vector;

import org.dos.tournament.player.utils.IParticipantId;
import org.dos.tournament.player.utils.ParticipantStatus;
import org.dos.tournament.result.IResult;

public interface IParticipant extends Comparable
{
  public IParticipantId getParticipantId();
  public void setParticipantId(IParticipantId id);
  
  public String getCode();
  
  public String getName();
  public void setName(String name);
  
  public String getDescription();
  
  public void addResultOfMatchday(int matchday, IResult result);
  public int getTotalScore();
  public int[] getTotalResult();
  
  public ParticipantStatus getStatus();
  public void setStatus(ParticipantStatus status);
  public boolean activateParticipant();
  public boolean inactivateParticipant();
  public boolean disqualifyParticipant();
  
  public boolean hasAttendees();
  public IParticipant[] getAttendeesToArray();
  public boolean contains(IParticipant competitor);
  
  public Vector<String> getParticipantAsRow(Vector<String> header);
  public Collection<Object> getTotalResultIdentifiers();
}
