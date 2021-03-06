// *** WARNING: DO NOT MODIFY *** This is a generated Java source code! 
// 
// Generated by LF-ET 2.1.5 (190717a), http://www.lohrfink.de/lfet
// From decision table
// "C:/Users/dirk.schweier/git/TournamentManager/src/lfet/tournament/movement/swiss_system_ensuing_matchday_generator/MatchdayGeneratorSwissSystem.lfet"
// 13.02.2020 22:41
// 
// Prolog Standard ---->
// profile LFET.Java.Prolog.Standard.Interface.ini not found
// used LF-ET 2.1.5 (190717a) build in default

package org.dos.tournament.branch.petanque.tournament.movement.swisssystem;

public interface ISwissMatchdayGeneration
{
 
    // Prolog Standard <----

    // Prolog Decision Table ---->
    // $$InterfaceName=ISwissMatchdayGeneration        
    // $$InterfaceRulesClassName=SwissMatchdayGeneratorRulesEngine        
    // $$package=org.dos.tournament.branch.petanque.tournament.movement.swisssystem        
    //         
    // $$InterfaceRuleGroups=true     
    // $$InterfaceRuleMethods=false
    // Prolog Decision Table <----
    
    /** 
     * <b>B02: noch aktive Teams auszuwerten?</b><br>
     * <br>
     * <b>Java LogArg f�r B02</b><br>
     * <br>
     * $$MN=hasNextTeam
     */
    public boolean hasNextTeam();
    
    /** 
     * <b>B03: alle Spieltage f�r Team ausgewertet?</b><br>
     * <br>
     * <b>Java LogArg f�r B03</b><br>
     * <br>
     * $$MN=isMatchdayCounterBehindLast
     */
    public boolean isMatchdayCounterBehindLast();
    
    /** 
     * <b>B04: hat Team an Spieltag gewonnen?</b><br>
     * <br>
     * <b>Java LogArg f�r B04</b><br>
     * <br>
     * $$MN=isTeamWinner
     */
    public boolean isTeamWinner();
    
    /** 
     * <b>B05: Anzahl der Teams im Lostopf</b><br>
     * <br>
     * <b>B05/01: 0 - Lostopf ist leer</b><br>
     * <br>
     * <b>Java LogArg f�r B05/01: 0</b><br>
     * <br>
     * $$MN=isPotEmpty
     */
    public boolean isPotEmpty();
    
    /** 
     * <b>B05: Anzahl der Teams im Lostopf</b><br>
     * <br>
     * <b>B05/02: 1 - noch ein Team im Lostopf</b><br>
     * <br>
     * <b>Java LogArg f�r B05/02: 1</b><br>
     * <br>
     * $$MN=isOneTeamLeftInPot
     */
    public boolean isOneTeamLeftInPot();
    
    /** 
     * <b>B06: Z�hler Gruppentopf</b><br>
     * <br>
     * <b>B06/01: =0 - letzter Topf</b><br>
     * <br>
     * <b>Java LogArg f�r B06/01: =0</b><br>
     * <br>
     * $$MN=isCounterInLastPot
     */
    public boolean isCounterInLastPot();
    
    /** 
     * <b>A01: Ermitteln der aktiven Teams</b><br>
     * <br>
     * <b>Java StmtSeq f�r A01</b><br>
     * <br>
     * $$MN=doReadActiveTeams
     */
    public void doReadActiveTeams();
    
    /** 
     * <b>A02: Ermitteln der verf�gbaren Pl�tze</b><br>
     * <br>
     * <b>Java StmtSeq f�r A02</b><br>
     * <br>
     * $$MN=doReadAvailableTerrains
     */
    public void doReadAvailableTerrains();
    
    /** 
     * <b>A03: Erzeugen der Lost�pfe</b><br>
     * <br>
     * <b>Java StmtSeq f�r A03</b><br>
     * <br>
     * $$MN=doCreatePots
     */
    public void doCreatePots();
    
    /** 
     * <b>A04: Team in ersten Lostopf setzen</b><br>
     * <br>
     * <b>Java StmtSeq f�r A04</b><br>
     * <br>
     * $$MN=doSetTeamToFirstPot
     */
    public void doSetTeamToFirstPot();
    
    /** 
     * <b>A05: Team in h�heren Lostopf versetzen</b><br>
     * <br>
     * <b>Java StmtSeq f�r A05</b><br>
     * <br>
     * $$MN=doPromoteTeamToNextPot
     */
    public void doPromoteTeamToNextPot();
    
    /** 
     * <b>A06: Heim-Team aus Lostopf ziehen</b><br>
     * <br>
     * <b>Java StmtSeq f�r A06</b><br>
     * <br>
     * $$MN=doDrawHomeFromPot
     */
    public void doDrawHomeFromPot();
    
    /** 
     * <b>A07: Gast-Team ermitteln</b><br>
     * <br>
     * <b>A07/01: Pot - Gast-Team kommt aus aktuellem Lostopf</b><br>
     * <br>
     * <b>Java StmtSeq f�r A07/01: Pot</b><br>
     * <br>
     * $$MN=doDrawGuestFromPot
     */
    public void doDrawGuestFromPot();
    
    /** 
     * <b>A07: Gast-Team ermitteln</b><br>
     * <br>
     * <b>A07/02: Pro - Gast-Team wird aus niedrigerem Lostopf "hochgelost"</b><br>
     * <br>
     * <b>Java StmtSeq f�r A07/02: Pro</b><br>
     * <br>
     * $$MN=doDrawGuestPromotedFromPot
     */
    public void doDrawGuestPromotedFromPot();
    
    /** 
     * <b>A07: Gast-Team ermitteln</b><br>
     * <br>
     * <b>A07/03: BYE - Freilos wird gesetzt</b><br>
     * <br>
     * <b>Java StmtSeq f�r A07/03: BYE</b><br>
     * <br>
     * $$MN=doSetGuestBye
     */
    public void doSetGuestBye();
    
    /** 
     * <b>A08: Partie anlegen</b><br>
     * <br>
     * <b>Java StmtSeq f�r A08</b><br>
     * <br>
     * $$MN=doCreatePartie
     */
    public void doCreatePartie();
    
    /** 
     * <b>A09: Z�hler Matchday</b><br>
     * <br>
     * <b>A09/01: reset - Startwert ist 0</b><br>
     * <br>
     * <b>Java StmtSeq f�r A09/01: reset</b><br>
     * <br>
     * $$MN=doResetCounterMatchday
     */
    public void doResetCounterMatchday();
    
    /** 
     * <b>A09: Z�hler Matchday</b><br>
     * <br>
     * <b>A09/02: inc - um 1 erh�hen</b><br>
     * <br>
     * <b>Java StmtSeq f�r A09/02: inc</b><br>
     * <br>
     * $$MN=doIncreaseCounterMatchday
     */
    public void doIncreaseCounterMatchday();
    
    /** 
     * <b>A10: Z�hler Lostopf</b><br>
     * <br>
     * <b>A10/01: init - Startwert ist Index des Gruppentopf, in dem Team alle Spiele gewonnen haben</b><br>
     * <br>
     * <b>Java StmtSeq f�r A10/01: init</b><br>
     * <br>
     * $$MN=doInitCounterPot
     */
    public void doInitCounterPot();
    
    /** 
     * <b>A10: Z�hler Lostopf</b><br>
     * <br>
     * <b>A10/02: dec - um 1 verringern</b><br>
     * <br>
     * <b>Java StmtSeq f�r A10/02: dec</b><br>
     * <br>
     * $$MN=doDecreaseCounterPot
     */
    public void doDecreaseCounterPot();
    
    public void doTrace(java.lang.String dtName, java.lang.String version, int rules, int rule);

    // Epilog Standard ---->
    // profile LFET.Java.Epilog.Standard.Interface.ini not found
    // used LF-ET 2.1.5 (190717a) build in default

}
 
// Epilog Standard <----

// End of generated Java source code
// Generated by LF-ET 2.1.5 (190717a), http://www.lohrfink.de/lfet

