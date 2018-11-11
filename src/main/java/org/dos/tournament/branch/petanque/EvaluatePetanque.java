package org.dos.tournament.branch.petanque;

import org.dos.tournament.branch.petanque.team.Doublette;
import org.dos.tournament.branch.petanque.tournament.movement.SuperMelee;
import org.dos.tournament.branch.petanque.tournament.movement.SuperMeleeClubChampionship;
import org.dos.tournament.branch.petanque.tournament.partie.CompetitorPartieResult;
import org.dos.tournament.branch.petanque.tournament.partie.Partie;
import org.dos.tournament.common.competition.DefaultCompetitorResult;
import org.dos.tournament.common.player.Attendee;
import org.dos.tournament.common.player.utils.NumericAssociationId;

public class EvaluatePetanque
{

  public static void main(String[] args)
  {
    /*
    Doublette team = new Doublette(new NumericAssociationId("GG1", "Blau-Gelb Groﬂ-Gerau 1", null), 
        new Attendee(1, "Gerhard Olschewski"), 
        new Attendee(2, "Klaus Franz"));
    Doublette opps = new Doublette(new NumericAssociationId("TVC", "TV Crumstadt 1", null), 
        new Attendee(1, "Hans Meister"), 
        new Attendee(2, "Fritz Boule"));
    System.out.println(team);
    System.out.println(team.getCode());
    System.out.println(team.getName());
    System.out.println(team.getDescription());
    System.out.println("Status des Legers : " + team.getPointeur().getStatus().toString());
    
    
    Partie freundschaftsspiel = new Partie();
    System.out.println(freundschaftsspiel);
    freundschaftsspiel.addParticipant(team);
    System.out.println(freundschaftsspiel);
    freundschaftsspiel.addParticipant(opps);
    System.out.println(freundschaftsspiel);
    
    freundschaftsspiel.setResult(team, new DefaultCompetitorResult(4));
    freundschaftsspiel.setResult(opps, new DefaultCompetitorResult(5));

    System.out.println(freundschaftsspiel);
    System.out.println("Sieger:    ".concat(freundschaftsspiel.getWinner().getName()));
    System.out.println("Rang 1:    ".concat(freundschaftsspiel.getRank(1).getName()));
    System.out.println("Verlierer: ".concat(freundschaftsspiel.getRank(2).getName()));
    
    CompetitorPartieResult teamResult = freundschaftsspiel.getCompetitorResult(team);
    System.out.println("Ergebnis:  ".concat(teamResult.toString()));
    */
    SuperMelee _tournament = new SuperMeleeClubChampionship();
    _tournament.addCompetitor(new Attendee( 1, "Gerd Lipp"));
    _tournament.addCompetitor(new Attendee( 2, "Frank Graf"));
    _tournament.addCompetitor(new Attendee( 3, "Gerhard Olschewski"));
    _tournament.addCompetitor(new Attendee( 4, "Georg Romberg"));
    _tournament.addCompetitor(new Attendee( 5, "Elvira Lipp"));
    _tournament.addCompetitor(new Attendee( 6, "Helmut Dreyer"));
    _tournament.addCompetitor(new Attendee( 7, "Winfried Ott"));
    _tournament.addCompetitor(new Attendee( 8, "Karnail Singh"));
    _tournament.addCompetitor(new Attendee( 9, "Mustaq Ahmad"));
    _tournament.addCompetitor(new Attendee(10, "Elke Fischer"));
    _tournament.addCompetitor(new Attendee(11, "Petra Spalt"));
    _tournament.addCompetitor(new Attendee(12, "Paul Neumann"));
    _tournament.addCompetitor(new Attendee(13, "Dirk Schweier"));
    _tournament.addCompetitor(new Attendee(14, "Ben Weiland"));
    _tournament.addCompetitor(new Attendee(15, "Gerhard Scherer"));
    
    _tournament.generateNextMatchday(null);
    System.out.println("Runde 1:\n" +_tournament.getMatchdayAsString(1));
    _tournament.generateNextMatchday(null);
    System.out.println("\nRunde 2:\n" + _tournament.getMatchdayAsString(2));
    _tournament.generateNextMatchday(null);
    System.out.println("\nRunde 3:\n" + _tournament.getMatchdayAsString(3));
   
    _tournament.getCompetitorByParticipantIdCode("  4").inactivateParticipant();
    _tournament.getCompetitorByParticipantIdCode("  8").inactivateParticipant();
    _tournament.getCompetitorByParticipantIdCode("  2").inactivateParticipant();
    _tournament.generateNextMatchday(null);
    System.out.println("\nRunde 4:\n" + _tournament.getMatchdayAsString(4));
    _tournament.generateNextMatchday(null);
    System.out.println("\nRunde 5:\n" + _tournament.getMatchdayAsString(5));
    _tournament.generateNextMatchday(null);
    System.out.println("\nRunde 6:\n" + _tournament.getMatchdayAsString(6));
//    _tournament.generateNextMatchday();
//    System.out.println("\nRunde 7:\n" + _tournament.getMatchdayAsString(7));
  }

}
