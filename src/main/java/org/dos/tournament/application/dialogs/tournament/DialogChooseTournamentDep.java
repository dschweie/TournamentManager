package org.dos.tournament.application.dialogs.tournament;

import javax.swing.JDialog;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.dos.tournament.common.storage.SingletonStorage;

import java.awt.FlowLayout;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.Action;
import javax.swing.DefaultListModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import java.awt.Dimension;

public class DialogChooseTournamentDep extends JDialog 
{
  private final Action action = new SwingAction();
  private final Action action_1 = new SwingAction_1();
  
  private Vector<HashMap<String,String>> listTournamentData;
  private JTextArea textArea;
  private JList<String> list;
  
  public DialogChooseTournamentDep() 
  {
    setAlwaysOnTop(true);
    setSize(new Dimension(500, 300));
    setPreferredSize(new Dimension(400, 300));
    //this.listTournamentData = SingletonStorage.getInstance().getTournamentData();
    
    //while(null == this.listTournamentData)
      //this.listTournamentData = SingletonStorage.getInstance().getTournamentData();
    
    //DefaultListModel<String> _listmodel = new DefaultListModel<String>();
    //this.listTournamentData.forEach(it -> _listmodel.addElement(it.get("name")));
    
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
    
    /*
    list = new JList<String>();
    list.setValueIsAdjusting(true);
    list.setSize(new Dimension(250, 100));
    list.setMinimumSize(new Dimension(50, 50));
    list.setPreferredSize(new Dimension(100, 50));
    scrollPane_3.setViewportView(list);
    list.addListSelectionListener(new ListSelectionListener() {
      public void valueChanged(ListSelectionEvent e) {
        if(-1 < DialogChooseTournament.this.list.getSelectedIndex())
          DialogChooseTournament.this.textArea.setText(DialogChooseTournament.this.listTournamentData.get(DialogChooseTournament.this.list.getSelectedIndex()).get("descriotion"));
        else
          DialogChooseTournament.this.textArea.setText("");
      }
    });
    list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    list.setModel(_listmodel);
    */
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
