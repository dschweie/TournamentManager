package org.dos.tournament.branch.petanque;

import org.dos.tournament.branch.petanque.tournament.movement.SuperMelee;
import org.dos.tournament.branch.petanque.tournament.movement.SuperMeleeClubChampionship;
import org.dos.tournament.common.player.Attendee;

public class EvaluatePetanque
{

  public static void main(String[] args)
  {
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
