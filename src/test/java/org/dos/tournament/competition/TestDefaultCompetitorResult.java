package org.dos.tournament.competition;

import org.dos.tournament.competition.DefaultCompetitorResult;
import org.junit.Assert;
import org.junit.Test;

public class TestDefaultCompetitorResult
{

  @Test
  public void testDefaultCompetitorResult()
  {
    Assert.assertEquals("Check constructror by value.", 9, (new DefaultCompetitorResult(9)).getValue());
  }

  @Test
  public void testGetValue()
  {
    int value = (int) Math.round((Integer.MAX_VALUE * Math.random()) - Integer.MAX_VALUE );
    DefaultCompetitorResult sut = new DefaultCompetitorResult(value);
    
    Assert.assertEquals("Check the value of instance.", value, sut.getValue());
  }

  @Test
  public void testSetValue()
  {
    int value = (int) Math.round((Integer.MAX_VALUE * Math.random()) - Integer.MAX_VALUE );
    DefaultCompetitorResult sut = new DefaultCompetitorResult(0);
    sut.setValue(value);
    
    Assert.assertEquals("Check setter by asking for updated value.", value, sut.getValue());
  }

}
