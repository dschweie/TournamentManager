package org.dos.tournament.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 *  \brief    Testsuite mit allen Testsuiten des Projekts.
 *  
 *  \test     In dieser Klasse werden die einzelnen JUnit-Testklassen
 *            zu einer Testsuite zusammengefasst. Damit ist diese 
 *            zentrale Testsuite aufzurufen, um die die Unittests in
 *            diesem Projekt auszuführen.
 * 
 *  @author dirk.schweier
 */
@RunWith(Suite.class)
@SuiteClasses({ org.dos.tournament.test.TestsuiteCommonFeatures.class     ,
                org.dos.tournament.petanque.test.TestsuitePetanque.class    })
public class TestsuiteTournamentManager
{

}
