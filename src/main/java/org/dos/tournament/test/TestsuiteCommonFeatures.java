package org.dos.tournament.test;

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
@SuiteClasses({ org.dos.tournament.player.test.TestAttendee.class                             ,
                org.dos.tournament.competition.test.TestDefaultCompetitorResult.class         ,
                org.dos.tournament.competition.test.TestDefaultCompetitorResultCompare.class    })
public class TestsuiteCommonFeatures
{

}
