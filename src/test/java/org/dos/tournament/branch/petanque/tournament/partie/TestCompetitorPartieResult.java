package org.dos.tournament.branch.petanque.tournament.partie;

import static org.junit.Assert.*;

import org.dos.tournament.branch.petanque.tournament.partie.CompetitorPartieResult;
import org.dos.tournament.common.player.Attendee;
import org.junit.Before;
import org.junit.Test;

public class TestCompetitorPartieResult
{
  static public CompetitorPartieResult SUT = null;
  
  @Before
  public void setUp() throws Exception
  {
    if(null == TestCompetitorPartieResult.SUT)
      TestCompetitorPartieResult.SUT = new CompetitorPartieResult(13, 7, new Attendee(5, "opps"));
  }

  @Test
  public void testGetPartieScore()
  {
    assertSame("check the partie score", CompetitorPartieResult.SCORE_PARTIE_WON, TestCompetitorPartieResult.SUT.getPartieScore());
  }

  @Test
  public void testGetCompetitorValue()
  {
    assertSame("check the value of competitor", 13, TestCompetitorPartieResult.SUT.getCompetitorValue());
  }

  @Test
  public void testSetCompetitorValue()
  {
    TestCompetitorPartieResult.SUT.setCompetitorValue(12);
    TestCompetitorPartieResult.SUT.setOpponentValue(13);
    assertSame("check the value of competitor after change in result", 12, TestCompetitorPartieResult.SUT.getCompetitorValue());
    TestCompetitorPartieResult.SUT = null;
  }

  @Test
  public void testGetOpponentValue()
  {
    assertSame("check the value of opps", 7, TestCompetitorPartieResult.SUT.getOpponentValue());
  }

  @Test
  public void testSetOpponentValue()
  {
    TestCompetitorPartieResult.SUT.setCompetitorValue(12);
    TestCompetitorPartieResult.SUT.setOpponentValue(13);
    assertSame("check the value of opps after change in result", 13, TestCompetitorPartieResult.SUT.getOpponentValue());
    TestCompetitorPartieResult.SUT = null;
  }

  @Test
  public void testGetValueDifference()
  {
    assertSame("check the difference", 6, TestCompetitorPartieResult.SUT.getValueDifference());
  }

  @Test
  public void testGetOpponent()
  {
    assertSame("check the name of opponent", "opps", TestCompetitorPartieResult.SUT.getOpponent().getName());
    assertEquals("check the id of opponent", "  5", TestCompetitorPartieResult.SUT.getOpponent().getParticipantId().toString());
  }

//  @Test
  public void testCompareTo()
  {
    // TODO Hier ist ein geeigneter Testfall zu hinterlegen.
  }

}
