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
import javax.swing.table.DefaultTableModel;
import javax.swing.ScrollPaneConstants;
import java.awt.Rectangle;
import javax.swing.JSplitPane;
import java.awt.CardLayout;
import javax.swing.JMenu;
import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JToolBar;
import javax.swing.JButton;

public class PetanqueSuperMeleePanel extends JPanel
{
  private JTable tableAttendees;
  private JTable tableCourts;

  /**
   * Create the panel.
   */
  public PetanqueSuperMeleePanel()
  {
    setBounds(new Rectangle(4, 4, 10, 10));
    setBorder(UIManager.getBorder("ScrollPane.border"));
    setLayout(new BorderLayout(0, 0));
    
    JTabbedPane tabbedPaneLeft = new JTabbedPane(JTabbedPane.TOP);
    
    JScrollPane scrollPaneAttendees = new JScrollPane();
    tabbedPaneLeft.addTab("Teilnehmer", null, scrollPaneAttendees, null);
    scrollPaneAttendees.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    scrollPaneAttendees.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
    
    tableAttendees = new JTable();
    tableAttendees.setModel(new DefaultTableModel(
      new Object[][] {
        {null, null, null, null},
        {null, null, null, null},
        {null, null, null, null},
        {null, null, null, null},
        {null, null, null, null},
        {null, null, null, null},
      },
      new String[] {
        "Nummer", "Name", "Verein", "Status"
      }
    ));
    tableAttendees.getColumnModel().getColumn(0).setPreferredWidth(50);
    tableAttendees.getColumnModel().getColumn(0).setMinWidth(10);
    tableAttendees.getColumnModel().getColumn(3).setPreferredWidth(50);
    tableAttendees.setColumnSelectionAllowed(true);
    tableAttendees.setCellSelectionEnabled(true);
    scrollPaneAttendees.setViewportView(tableAttendees);
    
    
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
    
    JTabbedPane tabbedPaneMatchdays = new JTabbedPane(JTabbedPane.TOP);
    tabbedPaneMatchdays.setBackground(Color.LIGHT_GRAY);
    panelRight.add(tabbedPaneMatchdays, BorderLayout.CENTER);
    
    JToolBar toolBar = new JToolBar();
    panelRight.add(toolBar, BorderLayout.NORTH);
    
    JButton btnNewButton = new JButton("Neue Runde");
    toolBar.add(btnNewButton);
    
    this.doLayout();
    
  }

}
