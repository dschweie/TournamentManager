/**
 * 
 */
package org.dos.tournament.application.petanque.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.dos.tournament.application.common.panels.DefaultMatchdayPanel;
import org.dos.tournament.application.dialogs.petanque.player.DialogJoueurIndividuel;
import org.dos.tournament.application.petanque.panels.tablemodels.LeaderboardTableColumnModel;
import org.dos.tournament.application.petanque.panels.tablemodels.LeaderboardTableModel;
import org.dos.tournament.application.petanque.panels.tablemodels.ParticipantsTableModel;
import org.dos.tournament.branch.petanque.team.JoueurIndividuel;
import org.dos.tournament.branch.petanque.tournament.matchday.Matchday;
import org.dos.tournament.branch.petanque.tournament.movement.SuperMelee;
import org.dos.tournament.common.storage.SingletonStorage;

/**
 * @author dirk.schweier
 *
 */
public class PetanqueLEquipeFormePanel extends JPanel {
  private JTable tableLEquipes;
  private JTable tableCourts;
  private final Action enlistLEquipe = new SwingActionEnlistLEquipe();
  
  private SuperMelee tournament = null;
  private final Action updateLEquipe = new SwingActionUpdateLEquipe();
  private final Action activateLEquipe = new SwingActionActivateLEquipe();
  private final Action inactivateLEquipe = new SwingActionInactivateLEquipe();
  private final Action deleteLEquipe = new SwingActionDeleteLEquipe();
  private final Action createNewMatchday = new SwingActionCreateNewMatchday();
  private JTabbedPane tabbedPaneMatchdays;
  private JTable table;
  private JTable tableLeaderboard;
  private final Action replaceLastMatchday = new SwingActionUpdateLastRound();
  private final Action addToDatabase = new SwingActionAddParticipantsToDatabase();
  private final Action deleteLastMatchday = new SwingActionDeleteLastMatchday();
  
  /**
   * Create the panel.
   */
  public PetanqueLEquipeFormePanel(SuperMelee tournament)
  {
    setPreferredSize(new Dimension(1024, 600));
    
    setBounds(new Rectangle(4, 4, 10, 10));
    setBorder(UIManager.getBorder("ScrollPane.border"));
    setLayout(new BorderLayout(0, 0));
    
    JTabbedPane tabbedPaneLeft = new JTabbedPane(JTabbedPane.TOP);
    
    JPanel panel_1 = new JPanel();
    tabbedPaneLeft.addTab("Teilnehmer", null, panel_1, null);
    panel_1.setLayout(new BorderLayout(0, 0));
    
    JToolBar toolBar_1 = new JToolBar();
    panel_1.add(toolBar_1, BorderLayout.NORTH);
    
    String key = "enlist attendee";
    JButton btnNeuerTeilnehmer = new JButton("");
    btnNeuerTeilnehmer.setIcon(new ImageIcon(PetanqueLEquipeFormePanel.class.getResource("/org/dos/tournament/resources/icons/if_207-User_2124114.png")));
    btnNeuerTeilnehmer.setAction(enlistLEquipe);
    btnNeuerTeilnehmer.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ADD, 0), key);
    btnNeuerTeilnehmer.getActionMap().put(key, enlistLEquipe);
    toolBar_1.add(btnNeuerTeilnehmer);
    
    Component horizontalStrut = Box.createHorizontalStrut(20);
    horizontalStrut.setPreferredSize(new Dimension(10, 0));
    horizontalStrut.setMaximumSize(new Dimension(10, 32767));
    horizontalStrut.setMinimumSize(new Dimension(10, 0));
    toolBar_1.add(horizontalStrut);
    
    JButton btnTeilnehmerbearbeiten = new JButton("TeilnehmerBearbeiten");
    btnTeilnehmerbearbeiten.setAction(updateLEquipe);
    toolBar_1.add(btnTeilnehmerbearbeiten);
    
    JButton btnActivateLEquipe = new JButton("TeilnehmerAktivieren");
    btnActivateLEquipe.setAction(activateLEquipe);
    toolBar_1.add(btnActivateLEquipe);
    
    JButton btnDeactivateLEquipe = new JButton("TeilnehmerDeaktiviert");
    btnDeactivateLEquipe.setAction(inactivateLEquipe);
    toolBar_1.add(btnDeactivateLEquipe);
    
    Component horizontalStrut_1 = Box.createHorizontalStrut(20);
    horizontalStrut_1.setPreferredSize(new Dimension(10, 0));
    horizontalStrut_1.setMinimumSize(new Dimension(10, 0));
    horizontalStrut_1.setMaximumSize(new Dimension(10, 32767));
    toolBar_1.add(horizontalStrut_1);
    
    JButton btnDeleteLEquipe = new JButton("New button");
    btnDeleteLEquipe.setAction(deleteLEquipe);
    toolBar_1.add(btnDeleteLEquipe);
    
    Component horizontalStrut_2 = Box.createHorizontalStrut(20);
    toolBar_1.add(horizontalStrut_2);
    
    JScrollPane scrollPane = new JScrollPane();
    panel_1.add(scrollPane, BorderLayout.CENTER);
    
    tableLEquipes = new JTable();
    tableLEquipes.setCellSelectionEnabled(true);
    tableLEquipes.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
    tableLEquipes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    /*
    scrollPane.setViewportView(tableLEquipes);
    ParticipantsTableModel _ptm = new ParticipantsTableModel();
    tableLEquipes.setModel(_ptm);
    // Sortieren der Tabelle ermöglichen
    // Der TableRowSorter wird die Daten des Models sortieren
    TableRowSorter<TableModel> _sorter = new TableRowSorter<TableModel>();
    // Der Sorter muss dem JTable bekannt sein
    tableLEquipes.setRowSorter( _sorter );
    // ... und der Sorter muss wissen, welche Daten er sortieren muss
    _sorter.setModel( _ptm );    
    
    tableLEquipes.getTableHeader().setReorderingAllowed( false );
    
    this.tournament.addObserver(_ptm);
    tableLEquipes.getColumnModel().getColumn(0).setMinWidth(10);
    tableLEquipes.getColumnModel().getColumn(0).setPreferredWidth(20);
    tableLEquipes.getColumnModel().getColumn(1).setMinWidth(50);
    tableLEquipes.getColumnModel().getColumn(1).setPreferredWidth(100);
    tableLEquipes.getColumnModel().getColumn(2).setMinWidth(50);
    tableLEquipes.getColumnModel().getColumn(2).setPreferredWidth(100);
    tableLEquipes.getColumnModel().getColumn(3).setMinWidth(20);
    tableLEquipes.getColumnModel().getColumn(3).setPreferredWidth(30);

    */

    JPanel panelRight = new JPanel();
 
    JSplitPane splitPane = new JSplitPane();
    add(splitPane, BorderLayout.CENTER);
    splitPane.setLeftComponent(tabbedPaneLeft);
    
    JScrollPane scrollPaneLeaderboard = new JScrollPane();
    tabbedPaneLeft.addTab(ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("PetanqueSuperMeleePanel.scrollPaneLeaderboard.title"), null, scrollPaneLeaderboard, null);
    
    tableLeaderboard = new JTable();
    
    scrollPaneLeaderboard.setViewportView(tableLeaderboard);
    splitPane.setRightComponent(panelRight);
    panelRight.setLayout(new BorderLayout(0, 0));
    
    tabbedPaneMatchdays = new JTabbedPane(JTabbedPane.TOP);
    tabbedPaneMatchdays.setBackground(Color.LIGHT_GRAY);
    tabbedPaneMatchdays.addContainerListener(new ContainerListener() {
      @Override
      public void componentAdded(ContainerEvent e)
      {
        ((SwingActionUpdateLastRound) PetanqueLEquipeFormePanel.this.replaceLastMatchday).updateStatus();
        ((SwingActionDeleteLastMatchday) PetanqueLEquipeFormePanel.this.deleteLastMatchday).updateStatus();
      }

      @Override
      public void componentRemoved(ContainerEvent e)
      {
        ((SwingActionUpdateLastRound) PetanqueLEquipeFormePanel.this.replaceLastMatchday).updateStatus();
        ((SwingActionDeleteLastMatchday) PetanqueLEquipeFormePanel.this.deleteLastMatchday).updateStatus();
      }
    });
    tabbedPaneMatchdays.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e)
      {
        ((SwingActionUpdateLastRound) PetanqueLEquipeFormePanel.this.replaceLastMatchday).updateStatus();
        ((SwingActionDeleteLastMatchday) PetanqueLEquipeFormePanel.this.deleteLastMatchday).updateStatus();
      }
    });
    panelRight.add(tabbedPaneMatchdays, BorderLayout.CENTER);
    
    JToolBar toolBar = new JToolBar();
    panelRight.add(toolBar, BorderLayout.NORTH);
    
    JButton btnNewButton = new JButton("Neue Runde");
    btnNewButton.setAction(createNewMatchday);
    toolBar.add(btnNewButton);
    
    JButton btnReplaceLastMatchday = toolBar.add(replaceLastMatchday);
    
    JButton btnDeleteLastMatchday = new JButton("New button");
    btnDeleteLastMatchday.setAction(deleteLastMatchday);
    toolBar.add(btnDeleteLastMatchday);

//    this.tournament.addObserver((Observer) this.replaceLastMatchday);
//    this.tournament.addObserver((Observer) this.deleteLastMatchday);
    
    table = new JTable();
    panelRight.add(table, BorderLayout.SOUTH);
    
    this.doLayout();
    
  }

  public void setTournament(SuperMelee tournament)
  {
    this.tournament = tournament;
  }

  public SuperMelee getTournament()
  {
    return this.tournament;
  }
  

  private class SwingActionEnlistLEquipe extends AbstractAction {
    
    
    public SwingActionEnlistLEquipe() {
      putValue(MNEMONIC_KEY, KeyEvent.VK_F7);
      putValue(SMALL_ICON, new ImageIcon(PetanqueLEquipeFormePanel.class.getResource("/org/dos/tournament/resources/icons/if_207-User_2124114.png")));
      putValue(NAME, "");
      putValue(SHORT_DESCRIPTION, ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("PetanqueSuperMeleePanel.enlistAttendee.short description")); //$NON-NLS-1$ //$NON-NLS-2$
    }
    
    public void actionPerformed(ActionEvent e) 
    {
    }
  }
  
  private class SwingActionUpdateLEquipe extends AbstractAction {
    public SwingActionUpdateLEquipe() {
      putValue(MNEMONIC_KEY, KeyEvent.VK_F2);
      putValue(SMALL_ICON, new ImageIcon(PetanqueLEquipeFormePanel.class.getResource("/org/dos/tournament/resources/icons/if_215-User_2124127.png")));
      putValue(NAME, "");
      putValue(SHORT_DESCRIPTION, ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("PetanqueSuperMeleePanel.updateAttendee.short description")); //$NON-NLS-1$ //$NON-NLS-2$
    }
    public void actionPerformed(ActionEvent e) {
      if(0 < PetanqueLEquipeFormePanel.this.tableLEquipes.getSelectedRowCount())
      {
      }
      else
        JOptionPane.showMessageDialog(  PetanqueLEquipeFormePanel.this, 
                                        ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("PetanqueSuperMeleePanel.SwingActionUpdateAttendee.Error.NotColumnSelected.message"), 
                                        ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("PetanqueSuperMeleePanel.SwingActionUpdateAttendee.Error.NotColumnSelected.title"), 
                                        JOptionPane.ERROR_MESSAGE );
    }
  }
  
  private class SwingActionActivateLEquipe extends AbstractAction {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    public SwingActionActivateLEquipe() {
      putValue(MNEMONIC_KEY, KeyEvent.VK_F3);
      putValue(SMALL_ICON, new ImageIcon(PetanqueLEquipeFormePanel.class.getResource("/org/dos/tournament/resources/icons/if_209-User_2124118.png")));
      putValue(NAME, "");
      putValue(SHORT_DESCRIPTION, ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("PetanqueSuperMeleePanel.activateAttendee.short description")); //$NON-NLS-1$ //$NON-NLS-2$
    }
    public void actionPerformed(ActionEvent e) {
      if(0 < PetanqueLEquipeFormePanel.this.tableLEquipes.getSelectedRowCount())
      {
      }
      else
        JOptionPane.showMessageDialog(  PetanqueLEquipeFormePanel.this, 
                                        ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("PetanqueSuperMeleePanel.SwingActionUpdateAttendee.Error.NotColumnSelected.message"), 
                                        ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("PetanqueSuperMeleePanel.SwingActionUpdateAttendee.Error.NotColumnSelected.title"), 
                                        JOptionPane.ERROR_MESSAGE );
    }
  }
  private class SwingActionInactivateLEquipe extends AbstractAction {
    public SwingActionInactivateLEquipe() {
      putValue(MNEMONIC_KEY, KeyEvent.VK_F4);
      putValue(SMALL_ICON, new ImageIcon(PetanqueLEquipeFormePanel.class.getResource("/org/dos/tournament/resources/icons/if_211-User_2124119.png")));
      putValue(NAME, "");
      putValue(SHORT_DESCRIPTION, ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("PetanqueSuperMeleePanel.deactivateAttendee.short description")); //$NON-NLS-1$ //$NON-NLS-2$
    }
    public void actionPerformed(ActionEvent e) {
      if(0 < PetanqueLEquipeFormePanel.this.tableLEquipes.getSelectedRowCount())
      {
        String _idx = PetanqueLEquipeFormePanel.this.tableLEquipes.getValueAt(PetanqueLEquipeFormePanel.this.tableLEquipes.getSelectedRows()[0], 0).toString();
        PetanqueLEquipeFormePanel.this.tournament.getCompetitors().forEach(it -> {
          if(_idx.equalsIgnoreCase(it.getCode().trim()))
            it.inactivateParticipant();
        });
        PetanqueLEquipeFormePanel.this.tournament.forceNotifyAll();
      }
      else
        JOptionPane.showMessageDialog(  PetanqueLEquipeFormePanel.this, 
                                        ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("PetanqueSuperMeleePanel.SwingActionUpdateAttendeee.Error.NotColumnSelected.message"), 
                                        ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("PetanqueSuperMeleePanel.SwingActionUpdateAttendee.Error.NotColumnSelected.title"), 
                                        JOptionPane.ERROR_MESSAGE );
    }
  }
  private class SwingActionDeleteLEquipe extends AbstractAction {
    public SwingActionDeleteLEquipe() {
      putValue(MNEMONIC_KEY, KeyEvent.VK_SUBTRACT);
      putValue(SMALL_ICON, new ImageIcon(PetanqueLEquipeFormePanel.class.getResource("/org/dos/tournament/resources/icons/if_208-User_2124123.png")));
      putValue(NAME, "");
      putValue(SHORT_DESCRIPTION, ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("PetanqueSuperMeleePanel.deleteAttendee.short description")); //$NON-NLS-1$ //$NON-NLS-2$
    }
    public void actionPerformed(ActionEvent e) {
      if(0 < PetanqueLEquipeFormePanel.this.tableLEquipes.getSelectedRowCount())
      {
        if(JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog( PetanqueLEquipeFormePanel.this, 
                                                                    ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("PetanqueSuperMeleePanel.SwingActionDeleteAttendee.confirm.message").concat("\n\n").concat(ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("Commom.NoUndo.message")), 
                                                                    ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("PetanqueSuperMeleePanel.SwingActionDeleteAttendee.confirm.title"), 
                                                                    JOptionPane.YES_NO_OPTION))
        {
          String _idx = PetanqueLEquipeFormePanel.this.tableLEquipes.getValueAt(PetanqueLEquipeFormePanel.this.tableLEquipes.getSelectedRows()[0], 0).toString();
          PetanqueLEquipeFormePanel.this.tournament.getCompetitors().forEach(it -> {
            if(_idx.equalsIgnoreCase(it.getCode().trim()))
            {
              PetanqueLEquipeFormePanel.this.tournament.getCompetitors().remove(it);
              PetanqueLEquipeFormePanel.this.tournament.forceNotifyAll();
            }
          });
        }
      }
      else
        JOptionPane.showMessageDialog(  PetanqueLEquipeFormePanel.this, 
                                        ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("PetanqueSuperMeleePanel.SwingActionUpdateAttendee.Error.NotColumnSelected.message"), 
                                        ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("PetanqueSuperMeleePanel.SwingActionUpdateAttendee.Error.NotColumnSelected.title"), 
                                        JOptionPane.ERROR_MESSAGE );
    }
  }
  private class SwingActionCreateNewMatchday extends AbstractAction {
    public SwingActionCreateNewMatchday() {
      putValue(NAME, ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("PetanqueSuperMeleePanel.createNewMatchday.name")); //$NON-NLS-1$ //$NON-NLS-2$
      putValue(SHORT_DESCRIPTION, ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("PetanqueSuperMeleePanel.createNewMatchday.short description")); //$NON-NLS-1$ //$NON-NLS-2$
    }
    public void actionPerformed(ActionEvent e) 
    {
    }
  }

  private class SwingActionUpdateLastRound extends AbstractAction implements Observer {
    public SwingActionUpdateLastRound() {
      putValue(NAME, "Runde neu losen");
      putValue(SHORT_DESCRIPTION, "Some short description");
    }
    public void actionPerformed(ActionEvent e) 
    {
    }
    public void updateStatus()
    {
    }
    
    @Override
    public void update(Observable o, Object arg)
    {
      this.updateStatus();
    }
    
  }
  private class SwingActionAddParticipantsToDatabase extends AbstractAction {
    public SwingActionAddParticipantsToDatabase() {
      putValue(SMALL_ICON, new ImageIcon(PetanqueLEquipeFormePanel.class.getResource("/org/dos/tournament/resources/icons/if_413-Data_Add_2124504.png")));
      putValue(NAME, "SwingAction");
      putValue(SHORT_DESCRIPTION, "Some short description");
    }
    public void actionPerformed(ActionEvent e) {
      if(0 < PetanqueLEquipeFormePanel.this.tableLEquipes.getSelectedRowCount())
      {
      }
      else
        JOptionPane.showMessageDialog(  PetanqueLEquipeFormePanel.this, 
                                        ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("PetanqueSuperMeleePanel.SwingActionUpdateAttendee.Error.NotColumnSelected.message"), 
                                        ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("PetanqueSuperMeleePanel.SwingActionUpdateAttendee.Error.NotColumnSelected.title"), 
                                        JOptionPane.ERROR_MESSAGE );
    }
  }
  private class SwingActionDeleteLastMatchday extends AbstractAction implements Observer {
    public SwingActionDeleteLastMatchday() {
      try
      {
        putValue(NAME, ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("PetanqueSuperMeleePanel.deleteLastMatchday.name")); //$NON-NLS-1$ //$NON-NLS-2$
        putValue(SHORT_DESCRIPTION, ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("PetanqueSuperMeleePanel.deleteLastMatchday.shortDescription")); //$NON-NLS-1$ //$NON-NLS-2$
      }
      catch(Exception e)
      {
        this.setEnabled(false);
      }
    }
    public void actionPerformed(ActionEvent e) {
    }

    public void updateStatus()
    {
    }
    
    @Override
    public void update(Observable o, Object arg)
    {
      this.updateStatus();
    }
  
  }
}
