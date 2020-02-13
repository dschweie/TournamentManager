package org.dos.tournament.application.factory;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.bson.Document;
import org.dos.tournament.application.TournamentManagerUI;
import org.dos.tournament.application.branch.petanque.factories.SupermeleeMenuFactory;
import org.dos.tournament.application.branch.petanque.panels.PetanqueSuperMeleePanel;
import org.dos.tournament.application.branch.petanque.panels.PetanqueSuperMeleeViewerPanel;
import org.dos.tournament.application.branch.petanque.panels.tablemodels.ParticipantsTableModel;
import org.dos.tournament.application.common.panels.AbstractTournamentPanel;
import org.dos.tournament.application.common.wizard.createtournament.CreateTournamentWizard;
import org.dos.tournament.application.factory.model.TournamentFactoryRulesEngine;
import org.dos.tournament.branch.petanque.tournament.movement.SuperMelee;
import org.dos.tournament.branch.petanque.tournament.movement.SuperMeleeClubChampionship;
import org.dos.tournament.common.storage.SingletonStorage;

public class FactoryTournament
{
  static public AbstractTournamentPanel SetupTournamentEnvironment(JFrame owner)
  {
    while(!CreateTournamentWizard.xSettings.isEmpty())
      CreateTournamentWizard.xSettings.pop();
    CreateTournamentWizard _instance = new CreateTournamentWizard();
    _instance.setVisible(true);
    _instance.revalidate();

    while(_instance.isVisible())
    {
      /* wait and see */
      try {
        TimeUnit.MILLISECONDS.sleep(500);
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }

    Iterator<String> keys = _instance.getWizardProperties().keySet().iterator();
    while(keys.hasNext())
    {
      String key = keys.next();
      System.out.println(key.concat(" hat den Wert ").concat(_instance.getWizardProperty(key).toString()));
    }
    return _instance.getTournamentEnvironment();

  }

  static public AbstractTournamentPanel SetupTournamentEnvironment(String tid, boolean active)
  {
    return FactoryTournament.SetupTournamentEnvironment(SingletonStorage.getInstance().loadTournamentAsDocument(tid), active);
  }

  static public AbstractTournamentPanel SetupTournamentEnvironment(Document data, boolean active)
  {
    AbstractTournamentPanel _retval = null;
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

  private static AbstractTournamentPanel SetupSuperMeleeClubChampionship(Document data, boolean active)
  {
    SuperMeleeClubChampionship _tournament = new SuperMeleeClubChampionship(data);
    ParticipantsTableModel _tableModel = new ParticipantsTableModel();
    AbstractTournamentPanel _retval = null;
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
