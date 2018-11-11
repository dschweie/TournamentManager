package org.dos.tournament.common.competition;

import org.dos.tournament.common.competition.DefaultCompetitorResult;
import org.junit.Assert;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;



@RunWith(Parameterized.class)
public class TestDefaultCompetitorResultCompare
{
  
  @Parameters
  public static Collection<Object[]> data() 
  {
      return Arrays.asList( new Object[][] 
                            { 
                                { new DefaultCompetitorResult(5), new DefaultCompetitorResult(5), 5, 5, false }, 
                                { new DefaultCompetitorResult(9), new DefaultCompetitorResult(5), 9, 5, true  }, 
                                { new DefaultCompetitorResult(5), new DefaultCompetitorResult(7), 5, 7, false }, 
                                { new DefaultCompetitorResult(0), new DefaultCompetitorResult(0), 0, 0, false }, 
                                { new DefaultCompetitorResult(5), null, 5, 4, true } 
                            });
  }

  @Parameter // first data value (0) is default
  public /* NOT private */ DefaultCompetitorResult home;

  @Parameter(1)
  public /* NOT private */ DefaultCompetitorResult guest;

  @Parameter(2)
  public /* NOT private */ int expectedHomeValue;
  
  @Parameter(3)
  public /* NOT private */ int expectedGuestValue;
  
  @Parameter(4)
  public /* NOT private */ boolean expectedHomeWins;
  
  @Test
  public void testCompareTo()
  {
    Assert.assertEquals("Check the winner.", expectedHomeWins, 0 < this.home.compareTo(guest));
    Assert.assertEquals("Check the difference of comparison.", this.expectedHomeValue - this.expectedGuestValue, this.home.compareTo(guest));
  }

  @Test
  public void testGetValue()
  { 
    Assert.assertEquals("Check the home value", this.expectedHomeValue, this.home.getValue());
  }


}
