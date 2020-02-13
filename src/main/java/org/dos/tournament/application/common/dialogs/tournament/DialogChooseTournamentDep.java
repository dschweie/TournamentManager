package org.dos.tournament.application.common.dialogs.tournament;

import javax.swing.JDialog;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.Action;
import javax.swing.JScrollPane;
import java.awt.Dimension;

public class DialogChooseTournamentDep extends JDialog
{
  private final Action action = new SwingAction();
  private final Action action_1 = new SwingAction_1();

  private ArrayList<HashMap<String,String>> listTournamentData;
  private JTextArea textArea;
  private JList<String> list;

  public DialogChooseTournamentDep()
  {
    setAlwaysOnTop(true);
    setSize(new Dimension(500, 300));
    setPreferredSize(new Dimension(400, 300));

    JPanel buttonPanel = new JPanel();
    FlowLayout flowLayout = (FlowLayout) buttonPanel.getLayout();
    flowLayout.setAlignment(FlowLayout.RIGHT);
    getContentPane().add(buttonPanel, BorderLayout.SOUTH);

    JButton btnNewButton = new JButton("New button");
    btnNewButton.setSize(new Dimension(50, 23));
    btnNewButton.setAction(action);
    buttonPanel.add(btnNewButton);

    JButton btnNewButton_1 = new JButton("New button");
    btnNewButton_1.setSize(new Dimension(50, 23));
    btnNewButton_1.setAction(action_1);
    buttonPanel.add(btnNewButton_1);

    JScrollPane scrollPane_2 = new JScrollPane();
    scrollPane_2.setPreferredSize(new Dimension(200, 200));
    getContentPane().add(scrollPane_2, BorderLayout.EAST);

    textArea = new JTextArea();
    scrollPane_2.setViewportView(textArea);
    textArea.setColumns(20);
    textArea.setRows(5);

    JScrollPane scrollPane_3 = new JScrollPane();
    scrollPane_3.setSize(new Dimension(300, 200));
    getContentPane().add(scrollPane_3, BorderLayout.CENTER);

    this.revalidate();
    this.pack();
  }

  private class SwingAction extends AbstractAction {
    public SwingAction() {
      putValue(NAME, "SwingAction");
      putValue(SHORT_DESCRIPTION, "Some short description");
    }
    public void actionPerformed(ActionEvent e) {
    }
  }
  private class SwingAction_1 extends AbstractAction {
    public SwingAction_1() {
      putValue(NAME, "SwingAction_1");
      putValue(SHORT_DESCRIPTION, "Some short description");
    }
    public void actionPerformed(ActionEvent e) {
      DialogChooseTournamentDep.this.dispose();
    }
  }
}
