package org.dos.tournament.application.petanque.factories;

import java.util.ResourceBundle;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

import org.dos.tournament.application.TournamentManagerUI;
import org.dos.tournament.branch.petanque.tournament.movement.SuperMelee;

public class SupermeleeMenuFactory
{
  
  static public void buildSupermeleeMenu(SuperMelee tournament)
  {
    TournamentManagerUI.removeAllMenuItemsSupermelee();
    JMenuItem mntmRundeAuslosen = new JMenuItem(ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("TournamentManagerUI.mntmRundeAuslosen.text")); //$NON-NLS-1$ //$NON-NLS-2$
    TournamentManagerUI.addMenuItemSupermelee(mntmRundeAuslosen);
    
    JMenu mnRundeErstellen = new JMenu(ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("TournamentManagerUI.mnRundeErstellen.text")); //$NON-NLS-1$ //$NON-NLS-2$
    TournamentManagerUI.addMenuItemSupermelee(mnRundeErstellen);
    
    JMenuItem mntmPaarungenSetzen = new JMenuItem(ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("TournamentManagerUI.mntmPaarungenSetzen.text")); //$NON-NLS-1$ //$NON-NLS-2$
    mntmPaarungenSetzen.setAction(tournament.getActionCreateRoundManually());
    mnRundeErstellen.add(mntmPaarungenSetzen);
    
    JMenuItem mntmTeilnehmerMischen = new JMenuItem(ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("TournamentManagerUI.mntmTeilnehmerMischen.text")); //$NON-NLS-1$ //$NON-NLS-2$
    mnRundeErstellen.add(mntmTeilnehmerMischen);
    
    TournamentManagerUI.addMenuItemSupermelee(new JSeparator());
    
    JMenuItem mntmAlleRegelnDeaktivieren = new JMenuItem(ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("TournamentManagerUI.mntmAlleRegelnDeaktivieren.text")); //$NON-NLS-1$ //$NON-NLS-2$
    TournamentManagerUI.addMenuItemSupermelee(mntmAlleRegelnDeaktivieren);
    
    JMenuItem mntmAlleRegelnAktivieren = new JMenuItem(ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("TournamentManagerUI.mntmAlleRegelnAktivieren.text")); //$NON-NLS-1$ //$NON-NLS-2$
    TournamentManagerUI.addMenuItemSupermelee(mntmAlleRegelnAktivieren);
    
    JMenuItem mntmAktuellesRegelwerkAnzeigen = new JMenuItem(ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("TournamentManagerUI.mntmAktuellesRegelwerkAnzeigen.text")); //$NON-NLS-1$ //$NON-NLS-2$
    TournamentManagerUI.addMenuItemSupermelee(mntmAktuellesRegelwerkAnzeigen);
    
    TournamentManagerUI.setVisibleSupermelee(true);
  }
}
