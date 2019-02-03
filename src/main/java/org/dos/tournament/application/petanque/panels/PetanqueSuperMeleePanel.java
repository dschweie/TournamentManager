package org.dos.tournament.application.petanque.panels;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTabbedPane;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.Color;

import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.dos.tournament.application.common.panels.DefaultMatchdayPanel;
import org.dos.tournament.application.dialogs.petanque.player.DialogJoueurIndividuel;
import org.dos.tournament.application.dialogs.player.DialogAssociationAttendee;
import org.dos.tournament.application.petanque.panels.tablemodels.LeaderboardTableColumnModel;
import org.dos.tournament.application.petanque.panels.tablemodels.LeaderboardTableModel;
import org.dos.tournament.application.petanque.panels.tablemodels.ParticipantsTableModel;
import org.dos.tournament.branch.petanque.team.JoueurIndividuel;
import org.dos.tournament.branch.petanque.tournament.matchday.Matchday;
import org.dos.tournament.branch.petanque.tournament.movement.SuperMelee;
import org.dos.tournament.common.player.AssociationAttendee;
import org.dos.tournament.common.player.IParticipant;
import org.dos.tournament.common.storage.SingletonStorage;

import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import javax.swing.ScrollPaneConstants;
import java.awt.Rectangle;
import javax.swing.JSplitPane;
import java.awt.CardLayout;
import javax.swing.JMenu;
import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JMenuBar;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import javax.swing.Action;
import javax.swing.ImageIcon;
import java.awt.Dimension;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.ListSelectionModel;
import java.awt.ComponentOrientation;
import java.awt.event.KeyEvent;
import javax.swing.KeyStroke;
import java.awt.Checkbox;
import javax.swing.JProgressBar;

public class PetanqueSuperMeleePanel extends JPanel
{
  private JTable tableAttendees;
  private JTable tableCourts;
  private final Action enlistAttendee = new SwingActionEnlistAttendee();
  
  private SuperMelee tournament = null;
  private final Action updateAttendee = new SwingActionUpdateAttendee();
  private final Action activateAttendee = new SwingActionActivateAttendee();
  private final Action inactivateAttendee = new SwingActionInactivateAttendee();
  private final Action deleteAttendee = new SwingActionDeleteAttendee();
  private final Action createNewMatchday = new SwingActionCreateNewMatchday();
  private JTabbedPane tabbedPaneMatchdays;
  private JTable table;
  private JTable tableLeaderboard;
  private final Action replaceLastMatchday = new SwingActionUpdateLastRound();
  private final Action deleteLastMatchday = new SwingActionDeleteLastMatchday();
  
  /**
   * Create the panel.
   */
  public PetanqueSuperMeleePanel(SuperMelee tournament)
  {
    setPreferredSize(new Dimension(1024, 600));
    this.tournament = tournament;
    
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
    btnNeuerTeilnehmer.setIcon(new ImageIcon(PetanqueSuperMeleePanel.class.getResource("/org/dos/tournament/resources/icons/if_207-User_2124114.png")));
    btnNeuerTeilnehmer.setAction(enlistAttendee);
    btnNeuerTeilnehmer.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ADD, 0), key);
    btnNeuerTeilnehmer.getActionMap().put(key, enlistAttendee);
    toolBar_1.add(btnNeuerTeilnehmer);
    
    Component horizontalStrut = Box.createHorizontalStrut(20);
    horizontalStrut.setPreferredSize(new Dimension(10, 0));
    horizontalStrut.setMaximumSize(new Dimension(10, 32767));
    horizontalStrut.setMinimumSize(new Dimension(10, 0));
    toolBar_1.add(horizontalStrut);
    
    JButton btnTeilnehmerbearbeiten = new JButton("TeilnehmerBearbeiten");
    btnTeilnehmerbearbeiten.setAction(updateAttendee);
    toolBar_1.add(btnTeilnehmerbearbeiten);
    
    JButton btnActivateAttendee = new JButton("TeilnehmerAktivieren");
    btnActivateAttendee.setAction(activateAttendee);
    toolBar_1.add(btnActivateAttendee);
    
    JButton btnDeactivateAttendee = new JButton("TeilnehmerDeaktiviert");
    btnDeactivateAttendee.setAction(inactivateAttendee);
    toolBar_1.add(btnDeactivateAttendee);
    
    Component horizontalStrut_1 = Box.createHorizontalStrut(20);
    horizontalStrut_1.setPreferredSize(new Dimension(10, 0));
    horizontalStrut_1.setMinimumSize(new Dimension(10, 0));
    horizontalStrut_1.setMaximumSize(new Dimension(10, 32767));
    toolBar_1.add(horizontalStrut_1);
    
    JButton btnDeleteAttendee = new JButton("New button");
    btnDeleteAttendee.setAction(deleteAttendee);
    toolBar_1.add(btnDeleteAttendee);
    
    Component horizontalStrut_2 = Box.createHorizontalStrut(20);
    toolBar_1.add(horizontalStrut_2);
    
    JScrollPane scrollPane = new JScrollPane();
    panel_1.add(scrollPane, BorderLayout.CENTER);
    
    tableAttendees = new JTable();
    tableAttendees.setCellSelectionEnabled(true);
    tableAttendees.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
    tableAttendees.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    scrollPane.setViewportView(tableAttendees);
    ParticipantsTableModel _ptm = new ParticipantsTableModel();
    tableAttendees.setModel(_ptm);

    // Sortieren der Tabelle ermöglichen
    // Der TableRowSorter wird die Daten des Models sortieren
    TableRowSorter<TableModel> _sorter = new TableRowSorter<TableModel>();
    // Der Sorter muss dem JTable bekannt sein
    tableAttendees.setRowSorter( _sorter );
    // ... und der Sorter muss wissen, welche Daten er sortieren muss
    _sorter.setModel( _ptm );    
    
    tableAttendees.getTableHeader().setReorderingAllowed( false );
    
    this.tournament.addObserver(_ptm);
    tableAttendees.getColumnModel().getColumn(0).setMinWidth(10);
    tableAttendees.getColumnModel().getColumn(0).setPreferredWidth(20);
    tableAttendees.getColumnModel().getColumn(1).setMinWidth(50);
    tableAttendees.getColumnModel().getColumn(1).setPreferredWidth(100);
    tableAttendees.getColumnModel().getColumn(2).setMinWidth(50);
    tableAttendees.getColumnModel().getColumn(2).setPreferredWidth(100);
    tableAttendees.getColumnModel().getColumn(3).setMinWidth(20);
    tableAttendees.getColumnModel().getColumn(3).setPreferredWidth(30);
    
    
//    JScrollPane scrollPaneCourts = new JScrollPane();
//    tabbedPaneLeft.addTab("Pl\u00E4tze", null, scrollPaneCourts, null);
//    scrollPaneCourts.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
//    scrollPaneCourts.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
//    
//    tableCourts = new JTable();
//    tableCourts.setModel(new DefaultTableModel(
//      new Object[][] {
//        {null, null},
//        {null, null},
//        {null, null},
//        {null, null},
//        {null, null},
//      },
//      new String[] {
//        "Nummer", "Begegnung"
//      }
//    ));
//    scrollPaneCourts.setViewportView(tableCourts);
    
    JPanel panelRight = new JPanel();
 
    JSplitPane splitPane = new JSplitPane();
    add(splitPane, BorderLayout.CENTER);
    splitPane.setLeftComponent(tabbedPaneLeft);
    
    JScrollPane scrollPaneLeaderboard = new JScrollPane();
    tabbedPaneLeft.addTab(ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("PetanqueSuperMeleePanel.scrollPaneLeaderboard.title"), null, scrollPaneLeaderboard, null);
    
    tableLeaderboard = new JTable();
    LeaderboardTableModel _ltm = new LeaderboardTableModel();
    tableLeaderboard.setModel(_ltm);
    tableLeaderboard.setColumnModel(new LeaderboardTableColumnModel());
    this.tournament.addObserver(_ltm);
    this.tournament.addObserver((Observer) tableLeaderboard.getColumnModel());
    tableLeaderboard.getTableHeader().setReorderingAllowed( false );
    
    scrollPaneLeaderboard.setViewportView(tableLeaderboard);
    splitPane.setRightComponent(panelRight);
    panelRight.setLayout(new BorderLayout(0, 0));
    
    tabbedPaneMatchdays = new JTabbedPane(JTabbedPane.TOP);
    tabbedPaneMatchdays.setBackground(Color.LIGHT_GRAY);
    tabbedPaneMatchdays.addContainerListener(new ContainerListener() {
      @Override
      public void componentAdded(ContainerEvent e)
      {
        ((SwingActionUpdateLastRound) PetanqueSuperMeleePanel.this.replaceLastMatchday).updateStatus();
        ((SwingActionDeleteLastMatchday) PetanqueSuperMeleePanel.this.deleteLastMatchday).updateStatus();
      }

      @Override
      public void componentRemoved(ContainerEvent e)
      {
        ((SwingActionUpdateLastRound) PetanqueSuperMeleePanel.this.replaceLastMatchday).updateStatus();
        ((SwingActionDeleteLastMatchday) PetanqueSuperMeleePanel.this.deleteLastMatchday).updateStatus();
      }
    });
    tabbedPaneMatchdays.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e)
      {
        ((SwingActionUpdateLastRound) PetanqueSuperMeleePanel.this.replaceLastMatchday).updateStatus();
        ((SwingActionDeleteLastMatchday) PetanqueSuperMeleePanel.this.deleteLastMatchday).updateStatus();
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

    this.tournament.addObserver((Observer) this.replaceLastMatchday);
    this.tournament.addObserver((Observer) this.deleteLastMatchday);
    
    table = new JTable();
    panelRight.add(table, BorderLayout.SOUTH);
    
    if(0 < tournament.countMatchdays())
      for(int i = 0; i < tournament.countMatchdays(); ++i)
        this.tabbedPaneMatchdays.addTab("Runde ".concat(String.valueOf(i+1)), null, new DefaultMatchdayPanel(tournament, i), null);
    
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
  

  private class SwingActionEnlistAttendee extends AbstractAction {
    
    
    public SwingActionEnlistAttendee() {
      putValue(MNEMONIC_KEY, KeyEvent.VK_F7);
      putValue(SMALL_ICON, new ImageIcon(PetanqueSuperMeleePanel.class.getResource("/org/dos/tournament/resources/icons/if_207-User_2124114.png")));
      putValue(NAME, "");
      putValue(SHORT_DESCRIPTION, ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("PetanqueSuperMeleePanel.enlistAttendee.short description")); //$NON-NLS-1$ //$NON-NLS-2$
    }
    
    public void actionPerformed(ActionEvent e) 
    {
      //this.panel.getTournament().addCompetitor(new AssociationAttendee (5, "Peter Oertel", "Rumkugler Hasslinghausen"));
      DialogAssociationAttendee _dialog = new DialogJoueurIndividuel(PetanqueSuperMeleePanel.this.tournament.getCompetitors(), -1);
      _dialog.setVisible(true);
      while(_dialog.isVisible());
      PetanqueSuperMeleePanel.this.tournament.forceNotifyAll();
    }
  }
  
  private class SwingActionUpdateAttendee extends AbstractAction {
    public SwingActionUpdateAttendee() {
      putValue(MNEMONIC_KEY, KeyEvent.VK_F2);
      putValue(SMALL_ICON, new ImageIcon(PetanqueSuperMeleePanel.class.getResource("/org/dos/tournament/resources/icons/if_215-User_2124127.png")));
      putValue(NAME, "");
      putValue(SHORT_DESCRIPTION, ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("PetanqueSuperMeleePanel.updateAttendee.short description")); //$NON-NLS-1$ //$NON-NLS-2$
    }
    public void actionPerformed(ActionEvent e) {
      if(0 < PetanqueSuperMeleePanel.this.tableAttendees.getSelectedRowCount())
      {
        String _idx = PetanqueSuperMeleePanel.this.tableAttendees.getValueAt(PetanqueSuperMeleePanel.this.tableAttendees.getSelectedRows()[0], 0).toString();
        PetanqueSuperMeleePanel.this.tournament.getCompetitors().forEach(it -> {
          if(_idx.equalsIgnoreCase(it.getCode().trim()))
          {
            DialogAssociationAttendee _dialog = new DialogJoueurIndividuel(PetanqueSuperMeleePanel.this.tournament.getCompetitors(), PetanqueSuperMeleePanel.this.tournament.getCompetitors().indexOf(it));
            _dialog.setVisible(true);
            while(_dialog.isVisible());
            SingletonStorage.getPrimaryStorage().saveParticipant(it, false);
            PetanqueSuperMeleePanel.this.tournament.forceNotifyAll();
          }
        });
      }
      else
        JOptionPane.showMessageDialog(  PetanqueSuperMeleePanel.this, 
                                        ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("PetanqueSuperMeleePanel.SwingActionUpdateAttendee.Error.NotColumnSelected.message"), 
                                        ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("PetanqueSuperMeleePanel.SwingActionUpdateAttendee.Error.NotColumnSelected.title"), 
                                        JOptionPane.ERROR_MESSAGE );
    }
  }
  
  private class SwingActionActivateAttendee extends AbstractAction {
    public SwingActionActivateAttendee() {
      putValue(MNEMONIC_KEY, KeyEvent.VK_F3);
      putValue(SMALL_ICON, new ImageIcon(PetanqueSuperMeleePanel.class.getResource("/org/dos/tournament/resources/icons/if_209-User_2124118.png")));
      putValue(NAME, "");
      putValue(SHORT_DESCRIPTION, ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("PetanqueSuperMeleePanel.activateAttendee.short description")); //$NON-NLS-1$ //$NON-NLS-2$
    }
    public void actionPerformed(ActionEvent e) {
      if(0 < PetanqueSuperMeleePanel.this.tableAttendees.getSelectedRowCount())
      {
        String _idx = PetanqueSuperMeleePanel.this.tableAttendees.getValueAt(PetanqueSuperMeleePanel.this.tableAttendees.getSelectedRows()[0], 0).toString();
        PetanqueSuperMeleePanel.this.tournament.getCompetitors().forEach(it -> {
          if(_idx.equalsIgnoreCase(it.getCode().trim()))
          {
            it.activateParticipant();
            PetanqueSuperMeleePanel.this.tournament.forceNotifyAll();
          }
        });
      }
      else
        JOptionPane.showMessageDialog(  PetanqueSuperMeleePanel.this, 
                                        ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("PetanqueSuperMeleePanel.SwingActionUpdateAttendee.Error.NotColumnSelected.message"), 
                                        ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("PetanqueSuperMeleePanel.SwingActionUpdateAttendee.Error.NotColumnSelected.title"), 
                                        JOptionPane.ERROR_MESSAGE );
    }
  }
  private class SwingActionInactivateAttendee extends AbstractAction {
    public SwingActionInactivateAttendee() {
      putValue(MNEMONIC_KEY, KeyEvent.VK_F4);
      putValue(SMALL_ICON, new ImageIcon(PetanqueSuperMeleePanel.class.getResource("/org/dos/tournament/resources/icons/if_211-User_2124119.png")));
      putValue(NAME, "");
      putValue(SHORT_DESCRIPTION, ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("PetanqueSuperMeleePanel.deactivateAttendee.short description")); //$NON-NLS-1$ //$NON-NLS-2$
    }
    public void actionPerformed(ActionEvent e) {
      if(0 < PetanqueSuperMeleePanel.this.tableAttendees.getSelectedRowCount())
      {
        String _idx = PetanqueSuperMeleePanel.this.tableAttendees.getValueAt(PetanqueSuperMeleePanel.this.tableAttendees.getSelectedRows()[0], 0).toString();
        PetanqueSuperMeleePanel.this.tournament.getCompetitors().forEach(it -> {
          if(_idx.equalsIgnoreCase(it.getCode().trim()))
            it.inactivateParticipant();
        });
        PetanqueSuperMeleePanel.this.tournament.forceNotifyAll();
      }
      else
        JOptionPane.showMessageDialog(  PetanqueSuperMeleePanel.this, 
                                        ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("PetanqueSuperMeleePanel.SwingActionUpdateAttendee.Error.NotColumnSelected.message"), 
                                        ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("PetanqueSuperMeleePanel.SwingActionUpdateAttendee.Error.NotColumnSelected.title"), 
                                        JOptionPane.ERROR_MESSAGE );
    }
  }
  private class SwingActionDeleteAttendee extends AbstractAction {
    public SwingActionDeleteAttendee() {
      putValue(MNEMONIC_KEY, KeyEvent.VK_SUBTRACT);
      putValue(SMALL_ICON, new ImageIcon(PetanqueSuperMeleePanel.class.getResource("/org/dos/tournament/resources/icons/if_208-User_2124123.png")));
      putValue(NAME, "");
      putValue(SHORT_DESCRIPTION, ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("PetanqueSuperMeleePanel.deleteAttendee.short description")); //$NON-NLS-1$ //$NON-NLS-2$
    }
    public void actionPerformed(ActionEvent e) {
      if(0 < PetanqueSuperMeleePanel.this.tableAttendees.getSelectedRowCount())
      {
        if(JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog( PetanqueSuperMeleePanel.this, 
                                                                    ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("PetanqueSuperMeleePanel.SwingActionDeleteAttendee.confirm.message").concat("\n\n").concat(ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("Commom.NoUndo.message")), 
                                                                    ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("PetanqueSuperMeleePanel.SwingActionDeleteAttendee.confirm.title"), 
                                                                    JOptionPane.YES_NO_OPTION))
        {
          String _idx = PetanqueSuperMeleePanel.this.tableAttendees.getValueAt(PetanqueSuperMeleePanel.this.tableAttendees.getSelectedRows()[0], 0).toString();
          PetanqueSuperMeleePanel.this.tournament.getCompetitors().forEach(it -> {
            if(_idx.equalsIgnoreCase(it.getCode().trim()))
            {
              PetanqueSuperMeleePanel.this.tournament.getCompetitors().remove(it);
              PetanqueSuperMeleePanel.this.tournament.forceNotifyAll();
            }
          });
        }
      }
      else
        JOptionPane.showMessageDialog(  PetanqueSuperMeleePanel.this, 
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
    public void actionPerformed(ActionEvent e) {
      
      boolean _processed = false;
      
      
      while(!_processed)
      {
        if(PetanqueSuperMeleePanel.this.tournament.generateNextMatchday(PetanqueSuperMeleePanel.this.tabbedPaneMatchdays))
        { // in this case a new matchday was created
          int _matchdays = PetanqueSuperMeleePanel.this.tournament.countMatchdays();
//          PetanqueMatchdayTableModel _model = new PetanqueMatchdayTableModel(_matchdays - 1);
//          PetanqueSuperMeleePanel.this.tournament.addObserver(_model);
          DefaultMatchdayPanel _panel = new DefaultMatchdayPanel(PetanqueSuperMeleePanel.this.tournament, _matchdays-1);
          
          PetanqueSuperMeleePanel.this.tabbedPaneMatchdays.addTab("Runde ".concat(String.valueOf(_matchdays)), null, _panel, null);
          _panel.setStateRuleTeammates(PetanqueSuperMeleePanel.this.tournament.isRuleNotSamePartnerActive());
          _panel.setStateRuleOpponents(PetanqueSuperMeleePanel.this.tournament.isRuleNotSameOpponentActive());
          _panel.setStateRuleTriplette(PetanqueSuperMeleePanel.this.tournament.isRuleNoTripletteTwiceActive());
          
          PetanqueSuperMeleePanel.this.tournament.forceNotifyAll();
          _processed = true;
        }
        else
        { //  in this case the ruleset should be made more flexible
          if(!PetanqueSuperMeleePanel.this.tournament.suspendWeakestRule())
          {
            JOptionPane.showMessageDialog(null,
                "Es kann keine weitere Runde angelegt werden, ohne die Regeln zu verletzen.",
                "Fehler: Anlegen einer neuen Runde",
                JOptionPane.ERROR_MESSAGE);              
            _processed = true;           
          }
        }
      }
    }
  }

  private class SwingActionUpdateLastRound extends AbstractAction implements Observer {
    public SwingActionUpdateLastRound() {
      putValue(NAME, "Runde neu losen");
      putValue(SHORT_DESCRIPTION, "Some short description");
      this.updateStatus();
      try
      {
        this.setEnabled(PetanqueSuperMeleePanel.this.tabbedPaneMatchdays.getSelectedIndex() == PetanqueSuperMeleePanel.this.tabbedPaneMatchdays.getTabCount()-1);
      }
      catch(java.lang.NullPointerException e)
      {
        this.setEnabled(false);
      }
    }
    public void actionPerformed(ActionEvent e) 
    {
      /*
      JTabbedPane _pane = PetanqueSuperMeleePanel.this.tabbedPaneMatchdays.getSelectedComponent();
      PetanqueSuperMeleePanel.this.tabbedPaneMatchdays.remove(component)
      */
      PetanqueSuperMeleePanel.this.tournament.regenerateLastMatchday(PetanqueSuperMeleePanel.this);
      //PetanqueSuperMeleePanel.this.tournament.forceNotifyAll();
    }
    public void updateStatus()
    {
      try
      {
        if(0 == PetanqueSuperMeleePanel.this.tabbedPaneMatchdays.getTabCount())
          this.setEnabled(false);
        else
        {
          if(PetanqueSuperMeleePanel.this.tabbedPaneMatchdays.getSelectedIndex() == PetanqueSuperMeleePanel.this.tabbedPaneMatchdays.getTabCount()-1)
          {
            Matchday _matchday = PetanqueSuperMeleePanel.this.tournament.getMatchday(PetanqueSuperMeleePanel.this.tabbedPaneMatchdays.getSelectedIndex());
            this.setEnabled(0 == _matchday.countScoredMatches());
          }
          else
            this.setEnabled(false);
        }
      } catch (Exception ex)
      {
        this.setEnabled(false);
      }
    }
    
    @Override
    public void update(Observable o, Object arg)
    {
      this.updateStatus();
    }
    
  }

  private class SwingActionDeleteLastMatchday extends AbstractAction implements Observer {
    public SwingActionDeleteLastMatchday() {
      try
      {
        putValue(NAME, ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("PetanqueSuperMeleePanel.deleteLastMatchday.name")); //$NON-NLS-1$ //$NON-NLS-2$
        putValue(SHORT_DESCRIPTION, ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("PetanqueSuperMeleePanel.deleteLastMatchday.shortDescription")); //$NON-NLS-1$ //$NON-NLS-2$
        this.updateStatus();
      }
      catch(Exception e)
      {
        this.setEnabled(false);
      }
    }
    public void actionPerformed(ActionEvent e) {
      PetanqueSuperMeleePanel.this.tabbedPaneMatchdays.remove(PetanqueSuperMeleePanel.this.tabbedPaneMatchdays.getSelectedIndex());
      PetanqueSuperMeleePanel.this.tournament.deleteLastMatchday();
    }

    public void updateStatus()
    {
      try
      {
        if(0 == PetanqueSuperMeleePanel.this.tabbedPaneMatchdays.getTabCount())
          this.setEnabled(false);
        else
        {
          if(PetanqueSuperMeleePanel.this.tabbedPaneMatchdays.getSelectedIndex() == PetanqueSuperMeleePanel.this.tabbedPaneMatchdays.getTabCount()-1)
          {
            Matchday _matchday = PetanqueSuperMeleePanel.this.tournament.getMatchday(PetanqueSuperMeleePanel.this.tabbedPaneMatchdays.getSelectedIndex());
            this.setEnabled(0 == _matchday.countScoredMatches());
          }
          else
            this.setEnabled(false);
        }
      } catch (Exception ex)
      {
        this.setEnabled(false);
      }
    }
    
    @Override
    public void update(Observable o, Object arg)
    {
      this.updateStatus();
    }
  
  }
}
