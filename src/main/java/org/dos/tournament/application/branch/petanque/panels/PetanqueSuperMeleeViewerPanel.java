package org.dos.tournament.application.branch.petanque.panels;

import javax.swing.JPanel;

import org.dos.tournament.application.branch.petanque.panels.components.tables.ParticipantTable;
import org.dos.tournament.application.branch.petanque.panels.components.tables.SuperMeleeLeaderboardTable;
import org.dos.tournament.application.branch.petanque.panels.components.tables.SuperMeleeResultsTable;
import org.dos.tournament.branch.petanque.tournament.movement.SuperMelee;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JSplitPane;
import java.awt.Dimension;
import javax.swing.border.EtchedBorder;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.GregorianCalendar;
import java.util.Observer;

import javax.swing.border.TitledBorder;
import javax.swing.JTable;
import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import java.awt.Component;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.ComponentOrientation;

public class PetanqueSuperMeleeViewerPanel extends AbstractPetanqueSuperMeleePanel {
  private JPanel panelParticipants;
  private JPanel panelLeaderboard;
  private JPanel panelResults;
  private JScrollPane scrollPaneParticipants;
  private ParticipantTable participantTable;
  private SuperMeleeResultsTable resultsTable;
  private SuperMeleeLeaderboardTable leaderboardTable;
  private JScrollPane scrollPaneLeaderboard;
  private JScrollPane scrollPaneResults;

  public PetanqueSuperMeleeViewerPanel(SuperMelee tournament)
  {
    super(tournament);
    setSize(new Dimension(820, 550));
    setMinimumSize(new Dimension(800, 10));
    GridBagLayout gridBagLayout = new GridBagLayout();
    gridBagLayout.columnWidths = new int[] {150, 150, 300};
    gridBagLayout.rowHeights = new int[]{550};
    gridBagLayout.columnWeights = new double[]{0.3, 0.4, 0.3};
    gridBagLayout.rowWeights = new double[]{1.0};
    setLayout(gridBagLayout);

    panelParticipants = new JPanel();
    panelParticipants.setPreferredSize(new Dimension(15, 15));
    panelParticipants.setBorder(new TitledBorder(null, "Teilnehmer", TitledBorder.LEADING, TitledBorder.TOP, null, null));
    GridBagConstraints gbc_panelParticipants = new GridBagConstraints();
    gbc_panelParticipants.anchor = GridBagConstraints.WEST;
    gbc_panelParticipants.fill = GridBagConstraints.BOTH;
    gbc_panelParticipants.insets = new Insets(0, 0, 0, 5);
    gbc_panelParticipants.gridx = 0;
    gbc_panelParticipants.gridy = 0;
    add(panelParticipants, gbc_panelParticipants);
    panelParticipants.setLayout(new BoxLayout(panelParticipants, BoxLayout.X_AXIS));

    participantTable = new ParticipantTable(tournament);
    participantTable.setFont(new Font("Verdana", Font.PLAIN, 24));
    participantTable.setRowHeight(28);

    scrollPaneParticipants = new JScrollPane();
    scrollPaneParticipants.setOpaque(false);
    scrollPaneParticipants.setBorder(null);
    scrollPaneParticipants.setViewportBorder(null);
    scrollPaneParticipants.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    scrollPaneParticipants.setAlignmentY(Component.TOP_ALIGNMENT);
    scrollPaneParticipants.setViewportView(participantTable);
    panelParticipants.add(scrollPaneParticipants);

    panelResults = new JPanel();
    panelResults.setPreferredSize(new Dimension(15, 15));
    panelResults.setOpaque(false);
    panelResults.setAlignmentY(Component.TOP_ALIGNMENT);
    panelResults.setName("PetanqueSuperMeleeViewerPanel");
    panelResults.setBorder(new TitledBorder(null, "Ergebnisse", TitledBorder.LEADING, TitledBorder.TOP, null, null));
    GridBagConstraints gbc_panelResults = new GridBagConstraints();
    gbc_panelResults.anchor = GridBagConstraints.WEST;
    gbc_panelResults.fill = GridBagConstraints.BOTH;
    gbc_panelResults.gridx = 1;
    gbc_panelResults.gridy = 0;
    add(panelResults, gbc_panelResults);
    panelResults.setLayout(new BoxLayout(panelResults, BoxLayout.X_AXIS));

    this.resultsTable = new SuperMeleeResultsTable(tournament, 1);
    resultsTable.setFillsViewportHeight(false);
    resultsTable.setDragEnabled(false);
    resultsTable.setFont(new Font("Verdana", Font.PLAIN, 24));
    resultsTable.setRowHeight(28);

    scrollPaneResults = new JScrollPane();
    scrollPaneResults.setOpaque(false);
    scrollPaneResults.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    scrollPaneResults.setBorder(null);
    scrollPaneResults.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
    scrollPaneResults.setAlignmentY(Component.TOP_ALIGNMENT);
    scrollPaneResults.setViewportView(resultsTable);
    panelResults.add(scrollPaneResults);

    panelLeaderboard = new JPanel();
    panelLeaderboard.setPreferredSize(new Dimension(30, 30));
    panelLeaderboard.setAlignmentY(Component.TOP_ALIGNMENT);
    panelLeaderboard.setBorder(new TitledBorder(null, "Rangliste", TitledBorder.LEADING, TitledBorder.TOP, null, null));
    GridBagConstraints gbc_panelLeaderboard = new GridBagConstraints();
    gbc_panelLeaderboard.fill = GridBagConstraints.BOTH;
    gbc_panelLeaderboard.insets = new Insets(0, 0, 0, 5);
    gbc_panelLeaderboard.gridx = 2;
    gbc_panelLeaderboard.gridy = 0;
    add(panelLeaderboard, gbc_panelLeaderboard);
    panelLeaderboard.setLayout(new BoxLayout(panelLeaderboard, BoxLayout.X_AXIS));

    this.leaderboardTable = new SuperMeleeLeaderboardTable(tournament);
    leaderboardTable.setFillsViewportHeight(false);
    leaderboardTable.setDragEnabled(false);
    leaderboardTable.setFont(new Font("Verdana", Font.PLAIN, 24));
    leaderboardTable.setRowHeight(28);

    scrollPaneLeaderboard = new JScrollPane();
    scrollPaneLeaderboard.setOpaque(false);
    scrollPaneLeaderboard.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    scrollPaneLeaderboard.setBorder(null);
    scrollPaneLeaderboard.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
    scrollPaneLeaderboard.setAlignmentY(Component.TOP_ALIGNMENT);
    scrollPaneLeaderboard.setOpaque(false);
    scrollPaneLeaderboard.setBorder(null);
    scrollPaneLeaderboard.setViewportView(leaderboardTable);
    panelLeaderboard.add(scrollPaneLeaderboard);


    this.revalidate();
    scrollPaneResults.getVerticalScrollBar().setValue(this.scrollPaneResults.getVerticalScrollBar().getMaximum());
    this.revalidate();

  }

  @Override
  public void updateUI() {
    try
    {
      if(this.scrollPaneLeaderboard.getVerticalScrollBar().isVisible())
        this.scrollPaneLeaderboard.getVerticalScrollBar().setValue(this.scrollPaneLeaderboard.getVerticalScrollBar().getMinimum());
      if(this.scrollPaneResults.getVerticalScrollBar().isVisible())
        this.scrollPaneResults.getVerticalScrollBar().setValue(this.scrollPaneResults.getVerticalScrollBar().getMaximum());
      if(this.scrollPaneParticipants.getVerticalScrollBar().isVisible())
      {
        int _maximum = this.scrollPaneParticipants.getVerticalScrollBar().getMaximum();
        int _current = this.scrollPaneParticipants.getVerticalScrollBar().getValue();
        int _size = this.scrollPaneParticipants.getHeight();

        this.scrollPaneParticipants.getVerticalScrollBar().setValue((_current + Math.round(0.9f * _size)) % _maximum);
      }
    }
    catch(NullPointerException ne) { /* no problem */ }
    catch(Exception e) { /* no problem, too */ }
    super.updateUI();
  }


}
