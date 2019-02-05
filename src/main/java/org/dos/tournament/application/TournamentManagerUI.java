package org.dos.tournament.application;

//  Tournament Manager
//  Copyright (C) 2018 Dirk O. Schweier (dirk@schweier-netz.de)
//
//  This program is free software; you can redistribute it and/or modify it 
//  under the terms of the GNU General Public License as published by 
//  the Free Software Foundation; either version 3 of the License, or 
//  any later version.
//
//  This program is distributed in the hope that it will be useful, 
//  but WITHOUT ANY WARRANTY; without even the implied warranty 
//  of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
//  See the GNU General Public License for more details.
//
//  You should have received a copy of the GNU General Public License along 
//  with this program; if not, see <http://www.gnu.org/licenses/>.

//  Dieses Programm ist freie Software. Sie können es unter den Bedingungen 
//  der GNU General Public License, wie von der Free Software Foundation 
//  veröffentlicht, weitergeben und/oder modifizieren, entweder 
//  gemäß Version 3 der Lizenz oder jeder späteren Version.
//
//  Die Veröffentlichung dieses Programms erfolgt in der Hoffnung, daß es 
//  Ihnen von Nutzen sein wird, aber OHNE IRGENDEINE GARANTIE, sogar ohne 
//  die implizite Garantie der MARKTREIFE oder der VERWENDBARKEIT FÜR EINEN 
//  BESTIMMTEN ZWECK. Details finden Sie in der GNU General Public License.
//
//  Sie sollten ein Exemplar der GNU General Public License zusammen mit diesem
//  Programm erhalten haben. Falls nicht, siehe <http://www.gnu.org/licenses/>.

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.util.ResourceBundle;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.Component;
import java.awt.Container;

import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JSeparator;

import org.dos.tournament.application.common.config.SingletonProperties;
import org.dos.tournament.application.dialogs.petanque.movement.DialogSetRoundManually;
import org.dos.tournament.application.dialogs.tournament.DialogChooseTournament;
import org.dos.tournament.application.dialogs.tournament.DialogChooseTournamentDep;
import org.dos.tournament.application.factory.FactoryTournament;
import org.dos.tournament.application.petanque.factories.SupermeleeMenuFactory;
import org.dos.tournament.application.petanque.panels.PetanqueLEquipeFormePanel;
import org.dos.tournament.application.petanque.panels.PetanqueSuperMeleePanel;
import org.dos.tournament.application.petanque.panels.tablemodels.ParticipantsTableModel;
import org.dos.tournament.branch.petanque.tournament.movement.SuperMelee;
import org.dos.tournament.branch.petanque.tournament.movement.SuperMeleeClubChampionship;
import org.dos.tournament.common.player.AssociationAttendee;
import org.dos.tournament.common.storage.SingletonStorage;

import com.mongodb.MongoClient;

import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.Dialog.ModalExclusionType;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import javax.swing.SwingConstants;

public class TournamentManagerUI
{

  private JFrame frmTurnierverwaltung;
  private final Action actionExit = new SwingAction();
  private final Action createTournament = new CreateTournamentAction(this.frmTurnierverwaltung);
  private final Action createFreitag = new CreateFridayTournamentAction(this.frmTurnierverwaltung);
  private JTextField txtSwVersion;
  private JTextField txtOS;
  
  static private MongoClient mongoClient = null;
  protected JMenu mnSupermelee;
  
  static public TournamentManagerUI application = null;
  private JMenuBar menuBar;
  private final Action openTournament = new OpenTournamentAction();

  /**
   * Launch the application.
   */
  public static void main(String[] args)
  {
    EventQueue.invokeLater(new Runnable()
    {
      public void run()
      {
        try
        {
          TournamentManagerUI.application = new TournamentManagerUI();
          TournamentManagerUI.application.frmTurnierverwaltung.setVisible(true);
        } catch (Exception e)
        {
          e.printStackTrace();
        }
      }
    });
  }

  /**
   * Create the application.
   */
  public TournamentManagerUI()
  {
    initialize();
    ((CreateTournamentAction)this.createTournament).updateApplicationFrame(frmTurnierverwaltung);
  }

  /**
   * Initialize the contents of the frame.
   */
  private void initialize()
  {
    frmTurnierverwaltung = new JFrame();
    frmTurnierverwaltung.setMinimumSize(new Dimension(1024, 600));
    frmTurnierverwaltung.getContentPane().setSize(new Dimension(1024, 800));
    frmTurnierverwaltung.getContentPane().setMinimumSize(new Dimension(1024, 800));
    
    JPanel panel = new JPanel();
    panel.setAlignmentX(Component.RIGHT_ALIGNMENT);
    frmTurnierverwaltung.getContentPane().add(panel, BorderLayout.SOUTH);
    panel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
    
    txtOS = new JTextField();
    txtOS.setRequestFocusEnabled(false);
    txtOS.setHorizontalAlignment(SwingConstants.CENTER);
    txtOS.setFocusable(false);
    txtOS.setFocusTraversalKeysEnabled(false);
    txtOS.setEditable(false);
    panel.add(txtOS);
    txtOS.setColumns(15);
    txtOS.setText(System.getProperty("os.name").concat(" ").concat(System.getProperty("os.version")));
    
    txtSwVersion = new JTextField();
    txtSwVersion.setRequestFocusEnabled(false);
    txtSwVersion.setHorizontalAlignment(SwingConstants.CENTER);
    txtSwVersion.setAlignmentX(Component.RIGHT_ALIGNMENT);
    txtSwVersion.setFocusTraversalKeysEnabled(false);
    txtSwVersion.setFocusable(false);
    panel.add(txtSwVersion);
    txtSwVersion.setEditable(false);
    txtSwVersion.setText(ResourceBundle.getBundle("org.dos.tournament.resources.config").getString("TournamentManager.version"));
    txtSwVersion.setColumns(10);
    frmTurnierverwaltung.setSize(new Dimension(1024, 768));
    frmTurnierverwaltung.setTitle(ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("TournamentManagerUI.frmTurnierverwaltung.title")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-1$ //$NON-NLS-2$
    frmTurnierverwaltung.setBounds(100, 100, 450, 300);
    frmTurnierverwaltung.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    menuBar = new JMenuBar();
    frmTurnierverwaltung.setJMenuBar(menuBar);
    
    JMenu mnDatei = new JMenu(ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("TournamentManagerUI.mnDatei.text")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-1$ //$NON-NLS-2$
    menuBar.add(mnDatei);
    
    JMenuItem mntmNeuesTurnier = new JMenuItem(ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("TournamentManagerUI.mntmNeuesTurnier.text")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-1$ //$NON-NLS-2$
    mntmNeuesTurnier.setAction(createTournament);
    mnDatei.add(mntmNeuesTurnier);
    
    JMenuItem mntmDubletteFormee = new JMenuItem(ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("TournamentManagerUI.mntmNewMenuItem.text")); //$NON-NLS-1$ //$NON-NLS-2$
    mntmDubletteFormee.setAction(createFreitag);
    mnDatei.add(mntmDubletteFormee);
    
    JMenuItem mntmOpenTournament = mnDatei.add(openTournament);
    
    JMenuItem mntmTurnierSpeichern = new JMenuItem(ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("TournamentManagerUI.mntmTurnierSpeichern.text")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-1$ //$NON-NLS-2$
    mnDatei.add(mntmTurnierSpeichern);
    
    JSeparator separator = new JSeparator();
    mnDatei.add(separator);
    
    JMenuItem mntmTurnierverwaltungBeenden = new JMenuItem(ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("TournamentManagerUI.mntmTurnierverwaltungBeenden.text")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-1$ //$NON-NLS-2$
    mntmTurnierverwaltungBeenden.setAction(actionExit);
    mnDatei.add(mntmTurnierverwaltungBeenden);
    
    mnSupermelee = new JMenu(ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("TournamentManagerUI.mnSupermle.text"));
    menuBar.add(mnSupermelee);
    
    /*
    JMenuItem mntmRundeAuslosen = new JMenuItem(ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("TournamentManagerUI.mntmRundeAuslosen.text")); //$NON-NLS-1$ //$NON-NLS-2$
    mnSupermelee.add(mntmRundeAuslosen);
    
    JMenu mnRundeErstellen = new JMenu(ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("TournamentManagerUI.mnRundeErstellen.text")); //$NON-NLS-1$ //$NON-NLS-2$
    mnSupermelee.add(mnRundeErstellen);
    
    JMenuItem mntmPaarungenSetzen = new JMenuItem(ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("TournamentManagerUI.mntmPaarungenSetzen.text")); //$NON-NLS-1$ //$NON-NLS-2$
    mntmPaarungenSetzen.setAction(new CreateRoundManually());
    mnRundeErstellen.add(mntmPaarungenSetzen);
    
    JMenuItem mntmTeilnehmerMischen = new JMenuItem(ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("TournamentManagerUI.mntmTeilnehmerMischen.text")); //$NON-NLS-1$ //$NON-NLS-2$
    mnRundeErstellen.add(mntmTeilnehmerMischen);
    
    JSeparator separator_1 = new JSeparator();
    mnSupermelee.add(separator_1);
    
    JMenuItem mntmAlleRegelnDeaktivieren = new JMenuItem(ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("TournamentManagerUI.mntmAlleRegelnDeaktivieren.text")); //$NON-NLS-1$ //$NON-NLS-2$
    mnSupermelee.add(mntmAlleRegelnDeaktivieren);
    
    JMenuItem mntmAlleRegelnAktivieren = new JMenuItem(ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("TournamentManagerUI.mntmAlleRegelnAktivieren.text")); //$NON-NLS-1$ //$NON-NLS-2$
    mnSupermelee.add(mntmAlleRegelnAktivieren);
    
    JMenuItem mntmAktuellesRegelwerkAnzeigen = new JMenuItem(ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("TournamentManagerUI.mntmAktuellesRegelwerkAnzeigen.text")); //$NON-NLS-1$ //$NON-NLS-2$
    mnSupermelee.add(mntmAktuellesRegelwerkAnzeigen);
    */
    this.mnSupermelee.setVisible(false);
    
    TournamentManagerUI.initConnection();
  }
  
  private void closeOpenTournament()
  {
    for(int i=0; i < this.frmTurnierverwaltung.getContentPane().getComponents().length; ++i)
      if(null != this.frmTurnierverwaltung.getContentPane().getComponent(i).getName())
        if(this.frmTurnierverwaltung.getContentPane().getComponent(i).getName().endsWith("Panel"))
          this.frmTurnierverwaltung.getContentPane().remove(i);
      
  }
  
  static public MongoClient initConnection()
  {
    System.out.println(SingletonProperties.getProperty("storage"));
    /*
    try
    {
      TournamentManagerUI.mongoClient = new MongoClient();
    } catch (Exception e)
    {
      TournamentManagerUI.mongoClient = null;
      e.printStackTrace();
    }
    return TournamentManagerUI.mongoClient;
    */
    return null;
  }
  
  static public MongoClient getMongoClient()
  {
    //return TournamentManagerUI.mongoClient;
    return null;
  }

  static public boolean isConnected()
  {
    return false;
  }
  
  
  public static void setVisibleSupermelee(boolean bFlag)
  {
    TournamentManagerUI.application.mnSupermelee.setVisible(bFlag);
  }
  
  public static void addMenuItemSupermelee(JComponent item)
  {
    TournamentManagerUI.application.mnSupermelee.add(item);
  }

  public static void removeAllMenuItemsSupermelee() {
    // TODO Auto-generated method stub
    TournamentManagerUI.application.mnSupermelee.removeAll();
  }

  
  private class SwingAction extends AbstractAction {
    public SwingAction() {
      putValue(NAME, ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("TournamentManagerUI.action.name")); //$NON-NLS-1$ //$NON-NLS-2$
      putValue(SHORT_DESCRIPTION, ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("TournamentManagerUI.actionExit.short description")); //$NON-NLS-1$ //$NON-NLS-2$
    }
    public void actionPerformed(ActionEvent e) {
      System.exit(0);
    }
  }
  private class CreateTournamentAction extends AbstractAction {
    private JFrame frmApplication;
    public CreateTournamentAction(JFrame frmApplication) {
      putValue(NAME, ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("TournamentManagerUI.createTournament.name")); //$NON-NLS-1$ //$NON-NLS-2$
      putValue(SHORT_DESCRIPTION, ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("TournamentManagerUI.createTournament.short description")); //$NON-NLS-1$ //$NON-NLS-2$

      this.frmApplication = frmApplication;
    }
    
    public void updateApplicationFrame(JFrame frame)
    {
      this.frmApplication = frame;
    }
    
    public void actionPerformed(ActionEvent e) {
      if(null != this.frmApplication)
      {
        TournamentManagerUI.this.closeOpenTournament();
        
        ParticipantsTableModel _tableModel = new ParticipantsTableModel();
        SuperMelee _tournament = new SuperMeleeClubChampionship();
        PetanqueSuperMeleePanel _panel = new PetanqueSuperMeleePanel(_tournament);
        _panel.setTournament(_tournament);
        System.out.println("Anzahl der Komponenten im Frame = ".concat(String.valueOf(this.frmApplication.getContentPane().getComponentCount())));
        for(int i=0; i < this.frmApplication.getContentPane().getComponentCount(); ++i)          
          System.out.println("    ".concat(null==this.frmApplication.getContentPane().getComponent(i).getName()?"unknown":this.frmApplication.getContentPane().getComponent(i).getName().toString()).concat(" => ").concat(this.frmApplication.getContentPane().getComponent(i).toString()));
        this.frmApplication.getContentPane().add(_panel);
        System.out.println("Anzahl der Komponenten im Frame = ".concat(String.valueOf(this.frmApplication.getContentPane().getComponentCount())));
        for(int i=0; i < this.frmApplication.getContentPane().getComponentCount(); ++i)          
          System.out.println("    ".concat(null==this.frmApplication.getContentPane().getComponent(i).getName()?"unknown":this.frmApplication.getContentPane().getComponent(i).getName().toString()).concat(" => ").concat(this.frmApplication.getContentPane().getComponent(i).toString()));
        this.frmApplication.revalidate();
        SupermeleeMenuFactory.buildSupermeleeMenu(_tournament);
        TournamentManagerUI.setVisibleSupermelee(true);
      }
    }
  }
  
  private class CreateFridayTournamentAction extends AbstractAction {
    /**
     * 
     */
    private static final long serialVersionUID = -414080870711326324L;
    public CreateFridayTournamentAction(JFrame frmApplication) {
//      putValue(NAME, ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("TournamentManagerUI.createTournament.name")); //$NON-NLS-1$ //$NON-NLS-2$
//      putValue(SHORT_DESCRIPTION, ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("TournamentManagerUI.createTournament.short description")); //$NON-NLS-1$ //$NON-NLS-2$
      putValue(NAME, "under construction"); //$NON-NLS-1$ //$NON-NLS-2$
      putValue(SHORT_DESCRIPTION, "be patient"); //$NON-NLS-1$ //$NON-NLS-2$
    }
    
    @Override
    public void actionPerformed(ActionEvent e) 
    {
      TournamentManagerUI.this.closeOpenTournament();
      /*      
      PetanqueLEquipeFormePanel _panel = new PetanqueLEquipeFormePanel(null);
      _panel.setTournament(null);
      System.out.println("_panel = ".concat(_panel.toString()));
      System.out.println("TournamentManagerUI.this.frmTurnierverwaltung = ".concat(TournamentManagerUI.this.frmTurnierverwaltung.toString()));
      System.out.println("TournamentManagerUI.this.frmTurnierverwaltung.getContentPane() = ".concat(TournamentManagerUI.this.frmTurnierverwaltung.getContentPane().toString()));
      TournamentManagerUI.this.frmTurnierverwaltung.getContentPane().add(_panel);
      TournamentManagerUI.this.frmTurnierverwaltung.revalidate();
      */
    }
    
  }
  
  private class OpenTournamentAction extends AbstractAction 
  {
    public OpenTournamentAction() 
    {
      putValue(NAME, ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("TournamentManagerUI.openTournament.name"));
      putValue(SHORT_DESCRIPTION, ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("TournamentManagerUI.openTournament.short description"));
    }
    public void actionPerformed(ActionEvent e) 
    {
      DialogChooseTournament _dialog = new DialogChooseTournament();
      _dialog.setVisible(true);
      while(_dialog.isVisible());
      if(_dialog.isTournamentSelected())
      {
        TournamentManagerUI.this.closeOpenTournament();
        
        System.out.println(_dialog.getSelectedTournamentId());
        JPanel _frame = FactoryTournament.SetupTournamentEnvironment(_dialog.getSelectedTournamentId());
        TournamentManagerUI.this.frmTurnierverwaltung.getContentPane().add(_frame);
        TournamentManagerUI.this.frmTurnierverwaltung.revalidate();
      }
    }
  }

}
