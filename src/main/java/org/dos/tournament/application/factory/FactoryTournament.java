package org.dos.tournament.application.factory;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.bson.Document;
import org.dos.tournament.application.TournamentManagerUI;
import org.dos.tournament.application.branch.petanque.factories.SupermeleeMenuFactory;
import org.dos.tournament.application.branch.petanque.panels.PetanqueSuperMeleePanel;
import org.dos.tournament.application.branch.petanque.panels.PetanqueSuperMeleeViewerPanel;
import org.dos.tournament.application.branch.petanque.panels.tablemodels.ParticipantsTableModel;
import org.dos.tournament.branch.petanque.tournament.movement.SuperMelee;
import org.dos.tournament.branch.petanque.tournament.movement.SuperMeleeClubChampionship;
import org.dos.tournament.common.storage.SingletonStorage;

public class FactoryTournament 
{
  static public JPanel SetupTournamentEnvironment(String tid, boolean active)
  {
    return FactoryTournament.SetupTournamentEnvironment(SingletonStorage.getInstance().loadTournamentAsDocument(tid), active);
  }
  
  static public JPanel SetupTournamentEnvironment(Document data, boolean active)
  {
    JPanel _retval = null;
    String _class = data.getString("class");
    if(null == _class)
      _class = "org.dos.tournament.branch.petanque.tournament.movement.SuperMeleeClubChampionship";
    
    switch(_class)
    {
      case "org.dos.tournament.branch.petanque.tournament.movement.SuperMeleeClubChampionship": 
                    _retval = FactoryTournament.SetupSuperMeleeClubChampionship(data, active);
                    break;
      default:      break;
    }
    
    
    return _retval;
  }

  private static JPanel SetupSuperMeleeClubChampionship(Document data, boolean active) 
  {
    SuperMeleeClubChampionship _tournament = new SuperMeleeClubChampionship(data);
    ParticipantsTableModel _tableModel = new ParticipantsTableModel();
    JPanel _retval = null;
    if(active)
    {
      _retval = new PetanqueSuperMeleePanel(_tournament);
      ((PetanqueSuperMeleePanel) _retval).setTournament(_tournament);
      _tournament.forceNotifyAll();
      _retval.revalidate();
    }
    else
    {
      _retval = new PetanqueSuperMeleeViewerPanel(_tournament);
      ((PetanqueSuperMeleeViewerPanel) _retval).setTournament(_tournament);
      _tournament.forceNotifyAll();
      _retval.revalidate();
    }
    return _retval;
  }
}
