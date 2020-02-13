package org.dos.tournament.branch.petanque.tournament;

import static org.junit.Assert.*;

import org.dos.tournament.branch.petanque.team.Doublette;
import org.dos.tournament.branch.petanque.team.JoueurIndividuel;
import org.dos.tournament.branch.petanque.tournament.movement.FormeeSwissSystem;
import org.dos.tournament.common.player.utils.NumericAssociationId;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.junit.Assert;

public class TestFormeeSwissSystem {

  static private FormeeSwissSystem tournament;

  @BeforeClass
  public static void setUpBeforeClass() throws Exception
  {
    String[][] competitors = new String[][] { { "Wolf-Dieter",  "Dabelstein", "Hubert",   "Koch"        },
                                              { "Sabrina",      "Scholz",     "Yussuf",   "Bakkoush"    },
                                              { "Helga",        "Beimer",     "Pia",      "Lorenz"      },
                                              { "Otto Wilhelm", "Kern",       "Joshua",   "Zenker"      },
                                              { "Sunny",        "Zöllig",     "Ines",     "Krämer"      },
                                              { "Manoel",       "Griese",     "Benno",    "Zimmermann"  },
                                              { "Pia",          "Lorenz",     "Marion",   "Beimer"      },
                                              { "Vasily",       "Sarikakis",  "Carsten",  "Flöter"      },
                                              { "Oskar",        "Krämer",     "Lara",     "Brooks"      },
                                              { "Robert",       "Engel",      "Frank",    "Dressler"    }   };

    TestFormeeSwissSystem.tournament = new FormeeSwissSystem();
    for(int i=0; i < 9; ++i)
    {
      TestFormeeSwissSystem.tournament.addCompetitor( new Doublette(  new NumericAssociationId(i+1, "PF JUnit", "Petanquefreunde JUnit")    ,
                                                                      new JoueurIndividuel(i*2+0, competitors[i][0], competitors[i][1], "") ,
                                                                      new JoueurIndividuel(i*2+1, competitors[i][2], competitors[i][3], "")   ) );
    }
  }

  @AfterClass
  public static void tearDownAfterClass() throws Exception {
  }

  @Before
  public void setUp() throws Exception {
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void matchdayGeneration()
  {
    // check of preconditions
    Assert.assertEquals("No matchday available", 0, tournament.getMatchdayCount());

    System.out.println("1. Runde");
    tournament.generateNextMatchday();

    Assert.assertEquals("One matchday available", 1, tournament.getMatchdayCount());
    Assert.assertEquals("with 5 matches", 5, tournament.getMatchCount(0));
    System.out.println(tournament.getMatchdayAsString(0));

    // some scores
    for(int i=0; i < 4; ++i)
      tournament.setResultForPartie(0, i, 13, 10);
    System.out.println(tournament.getMatchdayAsString(0));

    // nächster Spieltag
    System.out.println("");
    System.out.println("2. Runde");
    tournament.generateNextMatchday();
    System.out.println(tournament.getMatchdayAsString(1));

    // some scores
    for(int i=0; i < 4; ++i)
      tournament.setResultForPartie(1, i, 13, 9);
    System.out.println(tournament.getMatchdayAsString(1));

    // nächster Spieltag
    System.out.println("");
    System.out.println("3. Runde");
    tournament.generateNextMatchday();
    System.out.println(tournament.getMatchdayAsString(2));
  }



}
