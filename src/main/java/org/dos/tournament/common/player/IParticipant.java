package org.dos.tournament.common.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.bson.Document;
import org.dos.tournament.common.player.utils.IParticipantId;
import org.dos.tournament.common.player.utils.ParticipantStatus;
import org.dos.tournament.common.result.IResult;

/**
 *  \brief      Dieses ist das zentrale Interface f�r Teilnehmer oder Teams im Turnier
 *
 *  Um mit dem Tournament Manager einen m�glichst allgemeinen Ansatz zu
 *  unterst�tzen wurde diese Schnittstelle definiert.
 *
 *  \author     dschweie
 */
public interface IParticipant extends Comparable
{
  /**
   *  \brief    Getter-Methode f�r die Id des Teilnehmers.
   *
   *  Jeder Teilnehmer braucht im Rahmen des Turniers eine eindeutige Id,
   *  die f�r den Teilnehmer steht. Dieses kann z.B. eine Startnummer sein.
   *
   *  @return   Die Methode liefert die Id im R�ckgabewert.
   */
  public IParticipantId getParticipantId();
  /**
   *  \brief
   *  @param id
   */
  public void setParticipantId(IParticipantId id);

  public Object getAttribute(String key);

  public String getCode();

  public String getName();
  public void setName(String name);

  public String getDescription();
  public String getDescriptionByCode();

  public void addResultOfMatchday(int matchday, IResult result);
  public int getTotalScore();
  public int[] getTotalResult();
  public int getTotalResultValue(int category);
  public int getResultValue(int result, int category);

  public boolean hasWinnerOfTheDayTrophy();
  public void setWinnerOfTheDayTrophy(IResult instance);
  public void unsetWinnerOfTheDayTrophy();

  public int compareToByResult(IParticipant opponent, boolean includeTiebreaker);

  public ParticipantStatus getStatus();
  public void setStatus(ParticipantStatus status);
  public boolean activateParticipant();
  public boolean inactivateParticipant();
  public boolean disqualifyParticipant();

  public boolean hasAttendees();
  public IParticipant[] getAttendeesToArray();
  public boolean contains(IParticipant competitor);

  public List<Object> getParticipantAsRow(ArrayList<String> header);
  public Collection<Object> getTotalResultIdentifiers();
  public Document toBsonDocument();
}
