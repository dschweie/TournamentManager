/**
 * 
 */
package org.dos.tournament.petanque.team;

import static org.junit.Assert.*;

import org.dos.tournament.player.Attendee;
import org.dos.tournament.player.utils.NumericParticipantId;
import org.dos.tournament.player.utils.ParticipantStatus;
import org.junit.Before;
import org.junit.Test;

/**
 * @author dirk.schweier
 *
 */
public class TestDoublette
{
  static public Doublette SUT = null; 

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception
  {
    if(null == TestDoublette.SUT)
      TestDoublette.SUT = new Doublette(new NumericParticipantId(22), new Attendee(1, "A"), new Attendee(2, "B"));
  }

  /**
   * Test method for {@link org.dos.tournament.petanque.team.Doublette#getDescription()}.
   */
  @Test
  public void testGetDescription()
  {
    assertTrue("check for Description", "A, B".equals(SUT.getDescription()));
  }

  /**
   * Test method for {@link org.dos.tournament.petanque.team.AbstractPetanqueTeam#getPointeur()}.
   */
  @Test
  public void testGetPointeur()
  {
    assertSame("check for pointeur", "A", SUT.getPointeur().getName());
  }

  /**
   * Test method for {@link org.dos.tournament.petanque.team.AbstractPetanqueTeam#setPointeur(org.dos.tournament.player.IParticipant)}.
   */
  @Test
  public void testSetPointeur()
  {
    SUT.setPointeur(null);
    assertSame("check for new pointeur", null, SUT.getPointeur());
    SUT = null;
  }

  /**
   * Test method for {@link org.dos.tournament.petanque.team.AbstractPetanqueTeam#getMilieu()}.
   */
  @Test
  public void testGetMilieu()
  {
    assertSame("check for milieu", null, SUT.getMilieu());
  }

  /**
   * Test method for {@link org.dos.tournament.petanque.team.AbstractPetanqueTeam#getTireur()}.
   */
  @Test
  public void testGetTireur()
  {
    assertSame("check for tireur", "B", SUT.getTireur().getName());
  }

  /**
   * Test method for {@link org.dos.tournament.petanque.team.AbstractPetanqueTeam#setTireur(org.dos.tournament.player.IParticipant)}.
   */
  @Test
  public void testSetTireur()
  {
    SUT.setTireur(null);
    assertSame("check for new tireur", null, SUT.getTireur());
    SUT = null;
  }

  /**
   * Test method for {@link org.dos.tournament.player.AbstractParticipant#getCode()}.
   */
  @Test
  public void testGetCode()
  {
    assertTrue("check for code of team", " 22".equals(SUT.getCode()));
  }

  /**
   * Test method for {@link org.dos.tournament.player.AbstractParticipant#getName()}.
   */
  @Test
  public void testGetName()
  {
    assertTrue("check for name of team", "22".equals(SUT.getName()));
  }

  /**
   * Test method for {@link org.dos.tournament.player.AbstractParticipant#getStatus()}.
   */
  @Test
  public void testGetStatus()
  {
    assertSame("check for status of team", ParticipantStatus.ACTIVE, SUT.getStatus());
  }

  /**
   * Test method for {@link org.dos.tournament.player.AbstractParticipant#hasAttendees()}.
   */
  @Test
  public void testHasAttendees()
  {
    assertTrue("check for participants", SUT.hasAttendees());
  }

}
