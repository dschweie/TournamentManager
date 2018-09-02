package org.dos.tournament.application.petanque.panels;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTabbedPane;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.UIManager;
import java.awt.Color;

import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import org.dos.tournament.application.common.panels.DefaultMatchdayPanel;
import org.dos.tournament.application.common.panels.tablemodels.PetanqueMatchdayTableModel;
import org.dos.tournament.application.dialogs.player.DialogAssociationAttendee;
import org.dos.tournament.application.petanque.panels.tablemodels.ParticipantsTableModel;
import org.dos.tournament.petanque.tournament.movement.SuperMelee;
import org.dos.tournament.player.AssociationAttendee;

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
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
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

  /**
   * Create the panel.
   */
  public PetanqueSuperMeleePanel(ParticipantsTableModel tableModel)
  {
    setBounds(new Rectangle(4, 4, 10, 10));
    setBorder(UIManager.getBorder("ScrollPane.border"));
    setLayout(new BorderLayout(0, 0));
    
    JTabbedPane tabbedPaneLeft = new JTabbedPane(JTabbedPane.TOP);
    
    JPanel panel_1 = new JPanel();
    tabbedPaneLeft.addTab("Teilnehmer", null, panel_1, null);
    panel_1.setLayout(new BorderLayout(0, 0));
    
    JToolBar toolBar_1 = new JToolBar();
    panel_1.add(toolBar_1, BorderLayout.NORTH);
    
    JButton btnNeuerTeilnehmer = new JButton("");
    btnNeuerTeilnehmer.setIcon(new ImageIcon(PetanqueSuperMeleePanel.class.getResource("/org/dos/tournament/resources/icons/if_207-User_2124114.png")));
    btnNeuerTeilnehmer.setAction(enlistAttendee);
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
    
    JScrollPane scrollPane = new JScrollPane();
    panel_1.add(scrollPane, BorderLayout.CENTER);
    
    tableAttendees = new JTable();
    tableAttendees.setCellSelectionEnabled(true);
    tableAttendees.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
    tableAttendees.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    scrollPane.setViewportView(tableAttendees);
    tableAttendees.setModel(tableModel);
    tableAttendees.getColumnModel().getColumn(0).setMinWidth(10);
    tableAttendees.getColumnModel().getColumn(0).setPreferredWidth(20);
    tableAttendees.getColumnModel().getColumn(1).setMinWidth(50);
    tableAttendees.getColumnModel().getColumn(1).setPreferredWidth(100);
    tableAttendees.getColumnModel().getColumn(2).setMinWidth(50);
    tableAttendees.getColumnModel().getColumn(2).setPreferredWidth(100);
    tableAttendees.getColumnModel().getColumn(3).setMinWidth(20);
    tableAttendees.getColumnModel().getColumn(3).setPreferredWidth(30);
    
    
    JScrollPane scrollPaneCourts = new JScrollPane();
    tabbedPaneLeft.addTab("Pl\u00E4tze", null, scrollPaneCourts, null);
    scrollPaneCourts.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    scrollPaneCourts.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
    
    tableCourts = new JTable();
    tableCourts.setModel(new DefaultTableModel(
      new Object[][] {
        {null, null},
        {null, null},
        {null, null},
        {null, null},
        {null, null},
      },
      new String[] {
        "Nummer", "Begegnung"
      }
    ));
    scrollPaneCourts.setViewportView(tableCourts);
    
    JPanel panelRight = new JPanel();
 
    JSplitPane splitPane = new JSplitPane();
    add(splitPane, BorderLayout.CENTER);
    splitPane.setLeftComponent(tabbedPaneLeft);
    splitPane.setRightComponent(panelRight);
    panelRight.setLayout(new BorderLayout(0, 0));
    
    tabbedPaneMatchdays = new JTabbedPane(JTabbedPane.TOP);
    tabbedPaneMatchdays.setBackground(Color.LIGHT_GRAY);
    panelRight.add(tabbedPaneMatchdays, BorderLayout.CENTER);
    
    JToolBar toolBar = new JToolBar();
    panelRight.add(toolBar, BorderLayout.NORTH);
    
    JButton btnNewButton = new JButton("Neue Runde");
    btnNewButton.setAction(createNewMatchday);
    toolBar.add(btnNewButton);
    
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
      putValue(MNEMONIC_KEY, KeyEvent.VK_ADD);
      putValue(SMALL_ICON, new ImageIcon(PetanqueSuperMeleePanel.class.getResource("/org/dos/tournament/resources/icons/if_207-User_2124114.png")));
      putValue(NAME, "");
      putValue(SHORT_DESCRIPTION, ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("PetanqueSuperMeleePanel.enlistAttendee.short description")); //$NON-NLS-1$ //$NON-NLS-2$
    }
    
    public void actionPerformed(ActionEvent e) 
    {
      //this.panel.getTournament().addCompetitor(new AssociationAttendee (5, "Peter Oertel", "Rumkugler Hasslinghausen"));
      DialogAssociationAttendee _dialog = new DialogAssociationAttendee(PetanqueSuperMeleePanel.this.tournament.getCompetitors(), -1);
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
        DialogAssociationAttendee _dialog = new DialogAssociationAttendee(PetanqueSuperMeleePanel.this.tournament.getCompetitors(), PetanqueSuperMeleePanel.this.tableAttendees.getSelectedRows()[0]);
        _dialog.setVisible(true);
        while(_dialog.isVisible());
        PetanqueSuperMeleePanel.this.tournament.forceNotifyAll();
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
        PetanqueSuperMeleePanel.this.tournament.getCompetitors().elementAt(PetanqueSuperMeleePanel.this.tableAttendees.getSelectedRows()[0]).activateParticipant();
        PetanqueSuperMeleePanel.this.tournament.forceNotifyAll();
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
        PetanqueSuperMeleePanel.this.tournament.getCompetitors().elementAt(PetanqueSuperMeleePanel.this.tableAttendees.getSelectedRows()[0]).inactivateParticipant();
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
        PetanqueSuperMeleePanel.this.tournament.getCompetitors().remove(PetanqueSuperMeleePanel.this.tableAttendees.getSelectedRows()[0]).activateParticipant();
        PetanqueSuperMeleePanel.this.tournament.forceNotifyAll();
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
      if(PetanqueSuperMeleePanel.this.tournament.generateNextMatchday())
      { // in this case a new matchday was created
        int _matchdays = PetanqueSuperMeleePanel.this.tournament.countMatchdays();
        PetanqueMatchdayTableModel _model = new PetanqueMatchdayTableModel(_matchdays - 1);
        PetanqueSuperMeleePanel.this.tournament.addObserver(_model);
        DefaultMatchdayPanel _panel = new DefaultMatchdayPanel(_model);
        PetanqueSuperMeleePanel.this.tabbedPaneMatchdays.addTab("Runde ".concat(String.valueOf(_matchdays)), null, _panel, null);
        
        PetanqueSuperMeleePanel.this.tournament.forceNotifyAll();
      }
    }
  }
}
