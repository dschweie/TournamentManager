package org.dos.tournament.branch.petanque;

import org.dos.tournament.branch.petanque.team.TestDoublette;
import org.dos.tournament.branch.petanque.team.TestTriplette;
import org.dos.tournament.branch.petanque.tournament.TestSuperMelee;
import org.dos.tournament.branch.petanque.tournament.TestSuperMeleeClubChampionship;
import org.dos.tournament.branch.petanque.tournament.matchday.TestMatchday;
import org.dos.tournament.branch.petanque.tournament.partie.TestCompetitorPartieResult;
import org.dos.tournament.branch.petanque.tournament.partie.TestPartie;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestTriplette.class                           ,
                TestDoublette.class                           ,
                TestSuperMelee.class                    ,
                TestSuperMeleeClubChampionship.class    ,
                TestMatchday.class             ,
                TestCompetitorPartieResult.class ,
                TestPartie.class                   })
public class TestsuitePetanque
{

}
