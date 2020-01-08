package org.dos.tournament.common.player;

import org.dos.tournament.common.player.Attendee;
import org.dos.tournament.common.player.utils.ParticipantStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *  \brief  Testklasse für org.dos.tournament.player.Attendee
 *    
 *  \test   In dieser Klasse befinden sich die Tests, die auf einer Instanz 
 *          von Attendee definiert sind. 
 *  
 *  Da die Superklasse AbstractParticipant eine abstrakte Klasse ist, wurde
 *  bei den Tests darauf Wert gelegt, dass auch die Superklasse getestet
 *  wird.
 *  
 *  
 *  @see      org.dos.tournament.common.player.Attendee
 *  @see      org.dos.tournament.common.player.AbstractParticipant
 *  
 *  @author   dirk.schweier
 */
public class TestAttendee
{
  /**
   *  \brief  In dem Attribut wird die Instanz gehalten, auf der Tests 
   *          ausgeführt werden.
   */
  protected Attendee sutAttendee = null;
  
  @Before
  public void initDefaultAttendee()
  {
    this.sutAttendee = new Attendee(15, "Max Maier");
  }
  
  @Test
  public void testDefaultAttributeCode()
  {
    Assert.assertEquals("Check Attendee Code.", " 15", this.sutAttendee.getCode());
    Assert.assertNotNull("Check presence of ParticipantId.", this.sutAttendee.getParticipantId());
    Assert.assertEquals("Check class name of ParticipantId.", "org.dos.tournament.common.player.utils.NumericParticipantId", this.sutAttendee.getParticipantId().getClass().getName());
    Assert.assertEquals("Check code in ParticipantId", " 15", this.sutAttendee.getParticipantId().getCode());
  }

  @Test
  public void testDefaultAttributeName()
  {
    Assert.assertEquals("Check name of attendee.", "Max Maier", this.sutAttendee.getName());  
  }
  
  @Test
  public void testModifyingName()
  {
    this.sutAttendee.setName("Markus Arden");
    Assert.assertEquals("Check name of attendee.", "Markus Arden", this.sutAttendee.getName());  
    Assert.assertEquals("Check description of attendee.", " 15 - Markus Arden", this.sutAttendee.getDescription());  
  }

  @Test
  public void testDefaultAttributeDescription()
  {
    Assert.assertEquals("Check description of attendee.", " 15 - Max Maier", this.sutAttendee.getDescription());  
  }

  @Test
  public void testDefaultAttributeStatus()
  {
    Assert.assertNotNull("Check presence of status.", this.sutAttendee.getStatus());
    Assert.assertEquals("Check status of attendee.", ParticipantStatus.ACTIVE, this.sutAttendee.getStatus());  
  }
  
  @Test
  public void testDefaultAttributeAttendees()
  {
    Assert.assertFalse("Check attendee has no nested attendees.", this.sutAttendee.hasAttendees());
    Assert.assertNull("Check array of nested attendees.", this.sutAttendee.getAttendeesToArray());
  }
  
  @Test
  public void testActivateParticipant()
  {
    this.sutAttendee.inactivateParticipant();
    Assert.assertTrue("Check activating an inactive participant.", this.sutAttendee.activateParticipant());
    Assert.assertEquals("Check change of status.", ParticipantStatus.ACTIVE, this.sutAttendee.getStatus());
  }
  
  @Test
  public void testInactivateParticipant()
  {
    Assert.assertTrue("Check inactivating a  participant.", this.sutAttendee.inactivateParticipant());
    Assert.assertEquals("Check change of status.", ParticipantStatus.INACTIVE, this.sutAttendee.getStatus());
  }
  
  @Test
  public void testDisqualifyParticipant()
  {
    Assert.assertTrue("Check disqualifying a  participant.", this.sutAttendee.disqualifyParticipant());
    Assert.assertEquals("Check change of status.", ParticipantStatus.DISQUALIFIED, this.sutAttendee.getStatus());
  }

  @Test
  public void testDisqualificationBlocksActivation()
  {
    this.sutAttendee.disqualifyParticipant();
    Assert.assertFalse("Check participant must not be activated.", this.sutAttendee.activateParticipant());
    Assert.assertEquals("Check participant stays disqualified.", ParticipantStatus.DISQUALIFIED, this.sutAttendee.getStatus());
  }

  @Test
  public void testDisqualificationBlocksInactivation()
  {
    this.sutAttendee.disqualifyParticipant();
    Assert.assertFalse("Check participant must not be inactivated.", this.sutAttendee.inactivateParticipant());
    Assert.assertEquals("Check participant stays disqualified.", ParticipantStatus.DISQUALIFIED, this.sutAttendee.getStatus());
  }
}
