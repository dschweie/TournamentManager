package org.dos.tournament;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 *  \brief    Testsuite f�r allgemeine Klassen in der Turnierverwaltung
 *  
 *  \test     Die Turnierverwaltung soll unterschiedliche Turniere verwalten
 *            k�nnen. In dieser Testsuite werden Test zusammengefasst, die
 *            Funktionalit�t testen, die �bergreifend verwendet wird.
 *            
 *  @author dirk.schweier
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ org.dos.tournament.player.TestAttendee.class                             ,
                org.dos.tournament.competition.TestDefaultCompetitorResult.class         ,
                org.dos.tournament.competition.TestDefaultCompetitorResultCompare.class    })
public class TestsuiteCommonFeatures
{

}
