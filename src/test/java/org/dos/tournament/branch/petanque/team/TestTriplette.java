package org.dos.tournament.branch.petanque.team;

import static org.junit.Assert.*;

import org.dos.tournament.branch.petanque.team.Triplette;
import org.dos.tournament.common.player.Attendee;
import org.dos.tournament.common.player.utils.NumericParticipantId;
import org.dos.tournament.common.player.utils.ParticipantStatus;
import org.junit.Before;
import org.junit.Test;

public class TestTriplette
{

  static public Triplette SUT = null; 

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception
  {
    if(null == TestTriplette.SUT)
      TestTriplette.SUT = new Triplette(new NumericParticipantId(42), new Attendee(1, "P"), new Attendee(2, "M"), new Attendee(2, "T"));
  }

  @Test
  public void testGetDescription()
  {
    assertTrue("check for Description", "P, M, T".equals(SUT.getDescription()));
  }

  @Test
  public void testGetPointeur()
  {
    assertSame("check for pointeur", "P", SUT.getPointeur().getName());
  }

  @Test
  public void testSetPointeur()
  {
    SUT.setPointeur(null);
    assertSame("check for new pointeur", null, SUT.getPointeur());
    SUT = null;
  }

  @Test
  public void testGetMilieu()
  {
    assertSame("check for milieu", "M", SUT.getMilieu().getName());
  }

  @Test
  public void testSetMilieu()
  {
    SUT.setMilieu(null);
    assertSame("check for new milieu", null, SUT.getMilieu());
    SUT = null;
  }

  @Test
  public void testGetTireur()
  {
    assertSame("check for tireur", "T", SUT.getTireur().getName());
  }

  @Test
  public void testSetTireur()
  {
    SUT.setTireur(null);
    assertSame("check for new tireur", null, SUT.getTireur());
    SUT = null;
  }

  @Test
  public void testGetStatus()
  {
    assertSame("check for status of team", ParticipantStatus.ACTIVE, SUT.getStatus());
  }

  @Test
  public void testHasAttendees()
  {
    assertTrue("check for participants", SUT.hasAttendees());
  }

}
