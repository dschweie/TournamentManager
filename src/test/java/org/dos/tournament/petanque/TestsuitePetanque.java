package org.dos.tournament.petanque;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ org.dos.tournament.petanque.team.TestTriplette.class                           ,
                org.dos.tournament.petanque.team.TestDoublette.class                           ,
                org.dos.tournament.petanque.tournament.TestSuperMelee.class                    ,
                org.dos.tournament.petanque.tournament.TestSuperMeleeClubChampionship.class    ,
                org.dos.tournament.petanque.tournament.matchday.TestMatchday.class             ,
                org.dos.tournament.petanque.tournament.partie.TestCompetitorPartieResult.class ,
                org.dos.tournament.petanque.tournament.partie.TestPartie.class                   })
public class TestsuitePetanque
{

}
