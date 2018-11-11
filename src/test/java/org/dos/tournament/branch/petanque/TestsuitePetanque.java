package org.dos.tournament.branch.petanque;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ org.dos.tournament.branch.petanque.team.TestTriplette.class                           ,
                org.dos.tournament.branch.petanque.team.TestDoublette.class                           ,
                org.dos.tournament.branch.petanque.tournament.TestSuperMelee.class                    ,
                org.dos.tournament.branch.petanque.tournament.TestSuperMeleeClubChampionship.class    ,
                org.dos.tournament.branch.petanque.tournament.matchday.TestMatchday.class             ,
                org.dos.tournament.branch.petanque.tournament.partie.TestCompetitorPartieResult.class ,
                org.dos.tournament.branch.petanque.tournament.partie.TestPartie.class                   })
public class TestsuitePetanque
{

}
