package org.dos.tournament.application.common.dialogs.tournament;

import javax.swing.JDialog;
import java.awt.Dimension;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.dos.tournament.common.storage.SingletonStorage;

import javax.swing.border.TitledBorder;
import java.awt.Color;
import javax.swing.border.EtchedBorder;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;

public class DialogChooseTournament extends JDialog {
  /**
   *
   */
  private static final long serialVersionUID = 7802118033876069503L;
  private JList<String> list;
  private JTextArea txtTournamentDescription;
  private ArrayList<HashMap<String, String>> listTournamentData;
  private final Action selectTournament = new SelectTournamentAction();
  private final Action cancelAction = new CancelAction();

  private boolean bChoiceDone = false;

  public DialogChooseTournament() {
    setSize(new Dimension(500, 400));
    setPreferredSize(new Dimension(500, 400));
    setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    setType(Type.POPUP);
    setModal(true);

    this.setTitle(ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("DialogSelectTournament.title"));

    JPanel buttonPane = new JPanel();
    FlowLayout flowLayout = (FlowLayout) buttonPane.getLayout();
    flowLayout.setAlignment(FlowLayout.RIGHT);
    getContentPane().add(buttonPane, BorderLayout.SOUTH);

    JButton btnOpen = new JButton("New button");
    btnOpen.setAction(selectTournament);
    buttonPane.add(btnOpen);

    JButton btnCancel = new JButton("New button");
    btnCancel.setAction(cancelAction);
    buttonPane.add(btnCancel);

    JPanel descriptionPane = new JPanel();
    descriptionPane.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("Glossary.TournamentDesciption"), TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
    getContentPane().add(descriptionPane, BorderLayout.EAST);
    descriptionPane.setLayout(new BorderLayout(0, 0));

    txtTournamentDescription = new JTextArea();
    txtTournamentDescription.setRows(20);
    descriptionPane.add(txtTournamentDescription, BorderLayout.CENTER);
    txtTournamentDescription.setColumns(25);

    JPanel tournamentPane = new JPanel();
    tournamentPane.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("Glossary.Tournament"), TitledBorder.LEADING, TitledBorder.TOP, null, null));
    getContentPane().add(tournamentPane, BorderLayout.CENTER);
    tournamentPane.setLayout(new BorderLayout(0, 0));

    JScrollPane scrollPane = new JScrollPane();
    tournamentPane.add(scrollPane, BorderLayout.CENTER);

    list = new JList<String>();
    list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    this.listTournamentData = SingletonStorage.getInstance().getTournamentData();

    DefaultListModel<String> _listmodel = new DefaultListModel<String>();
    this.listTournamentData.forEach(it -> _listmodel.addElement(it.get("name")));
    list.setModel(_listmodel);

    list.addListSelectionListener(new ListSelectionListener() {
      public void valueChanged(ListSelectionEvent e) {
        if(-1 < DialogChooseTournament.this.list.getSelectedIndex())
        {
          DialogChooseTournament.this.txtTournamentDescription.setText(DialogChooseTournament.this.listTournamentData.get(DialogChooseTournament.this.list.getSelectedIndex()).get("description"));
          DialogChooseTournament.this.selectTournament.setEnabled(true);
        }
        else
        {
          DialogChooseTournament.this.txtTournamentDescription.setText("");
          DialogChooseTournament.this.selectTournament.setEnabled(true);
        }
      }
    });

    scrollPane.setViewportView(list);
  }

  protected JList<String> getList() {
    return list;
  }
  protected JTextArea getTextArea() {
    return txtTournamentDescription;
  }

  public String getSelectedTournamentId()
  {
    return ( -1 < this.list.getSelectedIndex() )?this.listTournamentData.get(this.list.getSelectedIndex()).get("tid"):null;
  }

  public boolean isTournamentSelected()
  {
    return ( this.bChoiceDone && ( -1 < this.list.getSelectedIndex() ) );
  }

  private class SelectTournamentAction extends AbstractAction {
    /**
     *
     */
    private static final long serialVersionUID = -223462148324202555L;
    public SelectTournamentAction() {
      putValue(NAME, ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("DialogSelectTournament.btnSelectTournament"));
      this.setEnabled(false);
      try
      {
        this.setEnabled(-1 < DialogChooseTournament.this.list.getSelectedIndex());
      }
      catch(Exception e) { /* Alles wird gut */ }
    }
    public void actionPerformed(ActionEvent e)
    {
      DialogChooseTournament.this.bChoiceDone  = true;
      DialogChooseTournament.this.dispose();
    }
  }
  private class CancelAction extends AbstractAction {
    /**
     *
     */
    private static final long serialVersionUID = 435423715276541635L;
    public CancelAction() {
      putValue(NAME, ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("Common.Cancel.name"));
    }
    public void actionPerformed(ActionEvent e) {
      DialogChooseTournament.this.list.setSelectedIndex(-1);
      DialogChooseTournament.this.dispose();
    }
  }
}
