package org.dos.tournament.petanque.tournament.matchday;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TestMatchday
{

  @Before
  public void setUp() throws Exception
  {
  }

  @Test
  public void testEmptyMatchday()
  {
    Matchday SUT = new Matchday();
    assertSame("check number of matches.", 0, SUT.countMatches());
    assertSame("check number of completed matches.", 0, SUT.countCompletedMatches());
    assertNull("check get first match", SUT.getMatch(0));
  }

}
