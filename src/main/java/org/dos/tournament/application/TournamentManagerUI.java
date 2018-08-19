package org.dos.tournament.application;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.util.ResourceBundle;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.Component;
import java.awt.Container;

import javax.swing.Box;
import javax.swing.JSeparator;

import org.dos.tournament.application.petanque.panels.PetanqueSuperMeleePanel;

import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;

public class TournamentManagerUI
{

  private JFrame frmTurnierverwaltung;
  private final Action action = new SwingAction();
  private final Action createTournament = new CreateTournamentAction(this.frmTurnierverwaltung);

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
          TournamentManagerUI window = new TournamentManagerUI();
          window.frmTurnierverwaltung.setVisible(true);
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
    frmTurnierverwaltung.setTitle(ResourceBundle.getBundle("org.dos.tournament.application.messages").getString("TournamentManagerUI.frmTurnierverwaltung.title")); //$NON-NLS-1$ //$NON-NLS-2$
    frmTurnierverwaltung.setBounds(100, 100, 450, 300);
    frmTurnierverwaltung.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    JMenuBar menuBar = new JMenuBar();
    frmTurnierverwaltung.setJMenuBar(menuBar);
    
    JMenu mnDatei = new JMenu(ResourceBundle.getBundle("org.dos.tournament.application.messages").getString("TournamentManagerUI.mnDatei.text")); //$NON-NLS-1$ //$NON-NLS-2$
    menuBar.add(mnDatei);
    
    JMenuItem mntmNeuesTurnier = new JMenuItem(ResourceBundle.getBundle("org.dos.tournament.application.messages").getString("TournamentManagerUI.mntmNeuesTurnier.text")); //$NON-NLS-1$ //$NON-NLS-2$
    mntmNeuesTurnier.setAction(createTournament);
    mnDatei.add(mntmNeuesTurnier);
    
    JMenuItem mntmTurnierSpeichern = new JMenuItem(ResourceBundle.getBundle("org.dos.tournament.application.messages").getString("TournamentManagerUI.mntmTurnierSpeichern.text")); //$NON-NLS-1$ //$NON-NLS-2$
    mnDatei.add(mntmTurnierSpeichern);
    
    JSeparator separator = new JSeparator();
    mnDatei.add(separator);
    
    JMenuItem mntmTurnierverwaltungBeenden = new JMenuItem(ResourceBundle.getBundle("org.dos.tournament.application.messages").getString("TournamentManagerUI.mntmTurnierverwaltungBeenden.text")); //$NON-NLS-1$ //$NON-NLS-2$
    mntmTurnierverwaltungBeenden.setAction(action);
    mnDatei.add(mntmTurnierverwaltungBeenden);
  }

  private class SwingAction extends AbstractAction {
    public SwingAction() {
      putValue(NAME, ResourceBundle.getBundle("org.dos.tournament.application.messages").getString("TournamentManagerUI.mntmTurnierverwaltungBeenden.text"));
      putValue(SHORT_DESCRIPTION, ResourceBundle.getBundle("org.dos.tournament.application.messages").getString("TournamentManagerUI.mntmTurnierverwaltungBeenden.description"));
    }
    public void actionPerformed(ActionEvent e) {
      System.exit(0);
    }
  }
  private class CreateTournamentAction extends AbstractAction {
    private JFrame frmApplication;
    public CreateTournamentAction(JFrame frmApplication) {
      putValue(NAME, ResourceBundle.getBundle("org.dos.tournament.application.messages").getString("TournamentManagerUI.mntmNeuesTurnier.text"));
      putValue(SHORT_DESCRIPTION, ResourceBundle.getBundle("org.dos.tournament.application.messages").getString("TournamentManagerUI.mntmNeuesTurnier.description"));

      this.frmApplication = frmApplication;
    }
    
    public void updateApplicationFrame(JFrame frame)
    {
      this.frmApplication = frame;
    }
    
    public void actionPerformed(ActionEvent e) {
      if(null != this.frmApplication)
      {
        this.frmApplication.getContentPane().add(new PetanqueSuperMeleePanel());
        this.frmApplication.revalidate();
        /*
        this.frmApplication.getContentPane().doLayout();
        this.frmApplication.getWindowListeners().notifyAll();
        this.frmApplication.getContentPane().repaint();
        this.frmApplication.repaint();*/
      }
    }
  }
}
