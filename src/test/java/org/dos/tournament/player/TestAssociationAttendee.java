package org.dos.tournament.player;

import static org.junit.Assert.*;

import org.dos.tournament.player.utils.ParticipantStatus;
import org.junit.Before;
import org.junit.Test;

public class TestAssociationAttendee
{

  static public AssociationAttendee SUT;
  
  @Before
  public void setUp() throws Exception
  {
    if(null == TestAssociationAttendee.SUT)
      TestAssociationAttendee.SUT = new AssociationAttendee(19, "Player", "Club");
  }

  @Test
  public void testAssociationAttendeeIntString()
  {
    AssociationAttendee _sut = new AssociationAttendee(27, "Player");
    assertNotNull("check if instance was created", _sut);
    assertSame("check name of attendee", "Player", _sut.getName());
    assertNull("check association", _sut.getAssociation());
  }

  @Test
  public void testAssociationAttendeeIntStringString()
  {
    AssociationAttendee _sut = new AssociationAttendee(23, "Spieler", "Verein");
    assertNotNull("check if instance was created", _sut);
    assertEquals("check name of attendee", "Spieler", _sut.getName());
    assertEquals("check association", "Verein", _sut.getAssociation());
  }

  @Test
  public void testGetAssociation()
  {
    assertSame("check association", "Club", TestAssociationAttendee.SUT.getAssociation());
  }

  @Test
  public void testSetAssociation()
  {
    TestAssociationAttendee.SUT.setAssociation("PSG Eindhoven");
    assertSame("check change of association", "PSG Eindhoven", TestAssociationAttendee.SUT.getAssociation());
    TestAssociationAttendee.SUT = null;
  }

  @Test
  public void testGetCode()
  {
    assertEquals("check code", " 19", TestAssociationAttendee.SUT.getCode());
  }

  @Test
  public void testGetDescription()
  {
    assertEquals("check description", " 19 - Player", TestAssociationAttendee.SUT.getDescription());
  }

  @Test
  public void testGetStatus()
  {
    assertSame("check status", ParticipantStatus.ACTIVE, TestAssociationAttendee.SUT.getStatus());
  }

  @Test
  public void testHasAttendees()
  {
    assertFalse("check hasAttendees", TestAssociationAttendee.SUT.hasAttendees());
  }

}
