package org.dos.tournament.common.player;

import java.util.Collection;
import java.util.Vector;

import org.dos.tournament.common.player.utils.IParticipantId;
import org.dos.tournament.common.player.utils.ParticipantStatus;
import org.dos.tournament.common.result.IResult;

/**
 *  \brief      Dieses ist das zentrale Interface für Teilnehmer oder Teams im Turnier
 *  
 *  Um mit dem Tournament Manager einen möglichst allgemeinen Ansatz zu
 *  unterstützen wurde mit dieser Schnittstelle ein
 *  
 *  \author     dschweie
 */
public interface IParticipant extends Comparable
{
  public IParticipantId getParticipantId();
  public void setParticipantId(IParticipantId id);
  
  public String getCode();
  
  public String getName();
  public void setName(String name);
  
  public String getDescription();
  public String getDescriptionByCode();
  
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
