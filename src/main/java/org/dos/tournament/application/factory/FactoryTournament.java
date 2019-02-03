package org.dos.tournament.application.factory;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.bson.Document;
import org.dos.tournament.application.TournamentManagerUI;
import org.dos.tournament.application.petanque.factories.SupermeleeMenuFactory;
import org.dos.tournament.application.petanque.panels.PetanqueSuperMeleePanel;
import org.dos.tournament.application.petanque.panels.tablemodels.ParticipantsTableModel;
import org.dos.tournament.branch.petanque.tournament.movement.SuperMelee;
import org.dos.tournament.branch.petanque.tournament.movement.SuperMeleeClubChampionship;
import org.dos.tournament.common.storage.SingletonStorage;

public class FactoryTournament 
{
  static public JPanel SetupTournamentEnvironment(String tid)
  {
    return FactoryTournament.SetupTournamentEnvironment(SingletonStorage.getInstance().loadTournamentAsDocument(tid));
  }
  
  static public JPanel SetupTournamentEnvironment(Document data)
  {
    JPanel _retval = null;
    String _class = data.getString("class");
    if(null == _class)
      _class = "org.dos.tournament.branch.petanque.tournament.movement.SuperMeleeClubChampionship";
    
    switch(_class)
    {
      case "org.dos.tournament.branch.petanque.tournament.movement.SuperMeleeClubChampionship": 
                    _retval = FactoryTournament.SetupSuperMeleeClubChampionship(data);
                    break;
      default:      break;
    }
    
    
    return _retval;
  }

  private static JPanel SetupSuperMeleeClubChampionship(Document data) 
  {
    SuperMeleeClubChampionship _tournament = new SuperMeleeClubChampionship(data);
    ParticipantsTableModel _tableModel = new ParticipantsTableModel();
    PetanqueSuperMeleePanel _panel = new PetanqueSuperMeleePanel(_tournament);
    _panel.setTournament(_tournament);
    _tournament.forceNotifyAll();
    _panel.revalidate();
    return _panel;
  }
}
