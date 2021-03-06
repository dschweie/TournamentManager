// *** WARNING: DO NOT MODIFY *** This is a generated Java source code! 
// 
// Generated by LF-ET 2.1.5 (190717a), http://www.lohrfink.de/lfet
// From decision table
// "C:/Users/dirk.schweier/git/TournamentManager/src/lfet/tournament/movement/matchday_generator/MatchdayGeneration.lfet"
// 13.02.2020 22:41
// 

// Prolog Standard ---->
// code used from profile: 
// C:/Users/dirk.schweier/git/TournamentManager/src/lfet/tournament/movement/matchday_generator/LFET.Java.Prolog.Standard.Interface.Dt.ini (09.02.2020 16:03, 165 bytes)
package org.dos.tournament.common.movement.utils;

public final class MatchdayGeneratorEngine
{
    static public void execute(IMatchdayGenerator iface)
    {
    // Prolog Standard <----

        // Prolog Decision Table ---->
        // $$InterfaceName=IMatchdayGenerator         
        // $$InterfaceRulesClassName=MatchdayGeneratorEngine         
        // $$package=org.dos.tournament.common.movement.utils
        // Prolog Decision Table <----
        
        if ( iface.isFirstMatchday() )
        {
            // Rule R01 ---->
            
            iface.doTrace("MatchdayGeneration", "20200213.224134", 4, 1);
            iface.doGenerateFirstMatchday();
            
            // Rule R01 <----
        }
        else
        {
            if ( iface.isFinalsDefined() )
            {
                if ( iface.isMatchdaysPlayed() )
                {
                    // Rule R02 ---->
                    
                    iface.doTrace("MatchdayGeneration", "20200213.224134", 4, 2);
                    iface.doGenerateFinals();
                    
                    // Rule R02 <----
                }
                else
                {
                    // Rule R03 ---->
                    
                    iface.doTrace("MatchdayGeneration", "20200213.224134", 4, 3);
                    iface.doGenerateEnsuingMatchday();
                    
                    // Rule R03 <----
                }
            }
            else
            {
                // Rule R04 ---->
                
                iface.doTrace("MatchdayGeneration", "20200213.224134", 4, 4);
                iface.doGenerateEnsuingMatchday();
                
                // Rule R04 <----
            }
        }
        
        // Epilog Standard ---->
        // profile LFET.Java.Epilog.Standard.Interface.Dt.ini not found
        // used LF-ET 2.1.5 (190717a) build in default

    }

}
 
// Epilog Standard <----

// End of generated Java source code
// Generated by LF-ET 2.1.5 (190717a), http://www.lohrfink.de/lfet

