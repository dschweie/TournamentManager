package org.dos.tournament.application.dialogs.player;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

import org.dos.tournament.application.TournamentManagerUI;
import org.dos.tournament.player.AssociationAttendee;
import org.dos.tournament.player.IParticipant;
import org.dos.tournament.player.utils.ParticipantStatus;

import com.mongodb.MongoClient;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;

import java.awt.event.ActionEvent;
import javax.swing.Action;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class DialogAssociationAttendee extends JDialog
{
  static private int INDEX = 0;

  private final JPanel contentPanel = new JPanel();
  protected JTextField textId;
  protected JTextField textName;
  protected JTextField textSurname;
  protected JTextField textAssociation;
  private JLabel lblId;
  private JLabel lblName;
  private JLabel lblAssociation;
  private JLabel lblState;
  protected JComboBox comboBoxStatus;
  private final Action actionSaveData = new SwingActionOK();
  
  protected Vector<IParticipant> vecAttendees = null;
  protected int iPos;
  private final Action actionCancel = new SwingActionCancel();
  private JButton btnOk;
  private JButton btnCancel;
  private JButton btnAdditionalAttendee;
  private final Action actionOkAndNext = new SwingActionOkAndNext();
  private JLabel lblSurname;
  private JLabel lblDataEntries;
  private JComboBox comboBoxDataEntries;
  private JLabel lblHits;


  /**
   * Launch the application.
   */
  public static void main(String[] args)
  {
    try
    {
      DialogAssociationAttendee dialog = new DialogAssociationAttendee();
      dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
      dialog.setVisible(true);
    } catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  /**
   * Create the dialog.
   */
  public DialogAssociationAttendee()
  {
    setModalityType(ModalityType.APPLICATION_MODAL);
    setType(Type.POPUP);

    setTitle(ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("DialogAssociationAttendee.this.title")); //$NON-NLS-1$ //$NON-NLS-2$
    setBounds(100, 100, 500, 200);
    getContentPane().setLayout(new BorderLayout());
    contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
    getContentPane().add(contentPanel, BorderLayout.NORTH);
    GridBagLayout gbl_contentPanel = new GridBagLayout();
    gbl_contentPanel.columnWidths = new int[] {60, 150, 60, 100, 50, 0};
    gbl_contentPanel.rowHeights = new int[]{22, 0, 22, 0, 0};
    gbl_contentPanel.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
    gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE, 0.0};
    contentPanel.setLayout(gbl_contentPanel);
    {
      lblId = new JLabel(ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("DialogAssociationAttendee.lblId.text")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-1$ //$NON-NLS-2$
      GridBagConstraints gbc_lblId = new GridBagConstraints();
      gbc_lblId.anchor = GridBagConstraints.EAST;
      gbc_lblId.fill = GridBagConstraints.VERTICAL;
      gbc_lblId.insets = new Insets(0, 0, 5, 5);
      gbc_lblId.gridx = 0;
      gbc_lblId.gridy = 0;
      contentPanel.add(lblId, gbc_lblId);
    }
    lblId.setLabelFor(textId);
    {
      textId = new JTextField();
      textId.setEditable(false);
      textId.setEnabled(false);
      GridBagConstraints gbc_textId = new GridBagConstraints();
      gbc_textId.gridwidth = 3;
      gbc_textId.fill = GridBagConstraints.BOTH;
      gbc_textId.insets = new Insets(0, 0, 5, 5);
      gbc_textId.gridx = 1;
      gbc_textId.gridy = 0;
      contentPanel.add(textId, gbc_textId);
      textId.setColumns(10);
    }
    {
      lblName = new JLabel(ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("DialogAssociationAttendee.lblName.text")); //$NON-NLS-1$ //$NON-NLS-2$
      GridBagConstraints gbc_lblName = new GridBagConstraints();
      gbc_lblName.anchor = GridBagConstraints.EAST;
      gbc_lblName.fill = GridBagConstraints.VERTICAL;
      gbc_lblName.insets = new Insets(0, 0, 5, 5);
      gbc_lblName.gridx = 0;
      gbc_lblName.gridy = 1;
      contentPanel.add(lblName, gbc_lblName);
    }
    {
      textName = new JTextField();
      GridBagConstraints gbc_textName = new GridBagConstraints();
      gbc_textName.fill = GridBagConstraints.BOTH;
      gbc_textName.insets = new Insets(0, 0, 5, 5);
      gbc_textName.gridx = 1;
      gbc_textName.gridy = 1;
      contentPanel.add(textName, gbc_textName);
      textName.setColumns(10);
    }
    lblName.setLabelFor(textName);
    {
      lblSurname = new JLabel(ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("DialogAssociationAttendee.lblSurname.text")); //$NON-NLS-1$ //$NON-NLS-2$
      GridBagConstraints gbc_lblSurname = new GridBagConstraints();
      gbc_lblSurname.anchor = GridBagConstraints.EAST;
      gbc_lblSurname.insets = new Insets(0, 0, 5, 5);
      gbc_lblSurname.gridx = 2;
      gbc_lblSurname.gridy = 1;
      contentPanel.add(lblSurname, gbc_lblSurname);
    }
    {
      textSurname = new JTextField();
      GridBagConstraints gbc_textSurname = new GridBagConstraints();
      gbc_textSurname.gridwidth = 2;
      gbc_textSurname.insets = new Insets(0, 0, 5, 0);
      gbc_textSurname.fill = GridBagConstraints.HORIZONTAL;
      gbc_textSurname.gridx = 3;
      gbc_textSurname.gridy = 1;
      contentPanel.add(textSurname, gbc_textSurname);
      textSurname.setColumns(10);
    }
    {
      lblAssociation = new JLabel(ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("DialogAssociationAttendee.lblAssociation.text")); //$NON-NLS-1$ //$NON-NLS-2$
      GridBagConstraints gbc_lblAssociation = new GridBagConstraints();
      gbc_lblAssociation.anchor = GridBagConstraints.EAST;
      gbc_lblAssociation.fill = GridBagConstraints.VERTICAL;
      gbc_lblAssociation.insets = new Insets(0, 0, 5, 5);
      gbc_lblAssociation.gridx = 0;
      gbc_lblAssociation.gridy = 2;
      contentPanel.add(lblAssociation, gbc_lblAssociation);
    }
    lblAssociation.setLabelFor(textAssociation);
    {
      textAssociation = new JTextField();
      GridBagConstraints gbc_textAssociation = new GridBagConstraints();
      gbc_textAssociation.insets = new Insets(0, 0, 5, 0);
      gbc_textAssociation.gridwidth = 4;
      gbc_textAssociation.fill = GridBagConstraints.BOTH;
      gbc_textAssociation.gridx = 1;
      gbc_textAssociation.gridy = 2;
      contentPanel.add(textAssociation, gbc_textAssociation);
      textAssociation.setColumns(10);
    }
    {
      lblState = new JLabel(ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("DialogAssociationAttendee.lblState.text")); //$NON-NLS-1$ //$NON-NLS-2$
      GridBagConstraints gbc_lblState = new GridBagConstraints();
      gbc_lblState.anchor = GridBagConstraints.EAST;
      gbc_lblState.fill = GridBagConstraints.VERTICAL;
      gbc_lblState.insets = new Insets(0, 0, 5, 5);
      gbc_lblState.gridx = 0;
      gbc_lblState.gridy = 3;
      contentPanel.add(lblState, gbc_lblState);
    }
    lblState.setLabelFor(comboBoxStatus);
    {
      comboBoxStatus = new JComboBox();
      comboBoxStatus.setModel(new DefaultComboBoxModel(ParticipantStatus.values()));
      comboBoxStatus.setSelectedIndex(1);
      GridBagConstraints gbc_comboBoxStatus = new GridBagConstraints();
      gbc_comboBoxStatus.insets = new Insets(0, 0, 5, 0);
      gbc_comboBoxStatus.gridwidth = 4;
      gbc_comboBoxStatus.fill = GridBagConstraints.BOTH;
      gbc_comboBoxStatus.gridx = 1;
      gbc_comboBoxStatus.gridy = 3;
      contentPanel.add(comboBoxStatus, gbc_comboBoxStatus);
    }
    {
      lblDataEntries = new JLabel(ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("DialogAssociationAttendee.lblStoredData.text")); //$NON-NLS-1$ //$NON-NLS-2$
      lblDataEntries.setEnabled(TournamentManagerUI.isConnected());
//      lblDataEntries.setVisible(null!=TournamentManagerUI.getMongoClient());
      GridBagConstraints gbc_lblDataEntries = new GridBagConstraints();
      gbc_lblDataEntries.anchor = GridBagConstraints.EAST;
      gbc_lblDataEntries.insets = new Insets(0, 0, 0, 5);
      gbc_lblDataEntries.gridx = 0;
      gbc_lblDataEntries.gridy = 4;
      contentPanel.add(lblDataEntries, gbc_lblDataEntries);
    }
    {
      comboBoxDataEntries = new JComboBox();
      comboBoxDataEntries.setEnabled(TournamentManagerUI.isConnected());
//      comboBoxDataEntries.setVisible(null!=TournamentManagerUI.getMongoClient());
      GridBagConstraints gbc_comboBoxDataEntries = new GridBagConstraints();
      gbc_comboBoxDataEntries.gridwidth = 3;
      gbc_comboBoxDataEntries.insets = new Insets(0, 0, 0, 5);
      gbc_comboBoxDataEntries.fill = GridBagConstraints.HORIZONTAL;
      gbc_comboBoxDataEntries.gridx = 1;
      gbc_comboBoxDataEntries.gridy = 4;
      contentPanel.add(comboBoxDataEntries, gbc_comboBoxDataEntries);
    }
    {
      lblHits = new JLabel(ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("DialogAssociationAttendee.lblMatches.text")); //$NON-NLS-1$ //$NON-NLS-2$
//      lblHits.setVisible(null!=TournamentManagerUI.getMongoClient());
      GridBagConstraints gbc_lblHits = new GridBagConstraints();
      gbc_lblHits.gridx = 4;
      gbc_lblHits.gridy = 4;
      contentPanel.add(lblHits, gbc_lblHits);
    }
    {
      JPanel buttonPane = new JPanel();
      getContentPane().add(buttonPane, BorderLayout.SOUTH);
      buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
      {
        btnOk = new JButton(ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("DialogAssociationAttendee.okButton.text")); //$NON-NLS-1$ //$NON-NLS-2$
        btnOk.setAction(actionSaveData);
        btnOk.setActionCommand(ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("DialogAssociationAttendee.okButton.actionCommand")); //$NON-NLS-1$ //$NON-NLS-2$
        buttonPane.add(btnOk);
        getRootPane().setDefaultButton(btnOk);
      }
      {
        btnAdditionalAttendee = new JButton(ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("DialogAssociationAttendee.btnNewButton.text")); //$NON-NLS-1$ //$NON-NLS-2$
        btnAdditionalAttendee.setMnemonic('w');
        btnAdditionalAttendee.setAction(actionOkAndNext);
        buttonPane.add(btnAdditionalAttendee);
      }
      {
        btnCancel = new JButton(ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("DialogAssociationAttendee.cancelButton.text")); //$NON-NLS-1$ //$NON-NLS-2$
        btnCancel.setAction(actionCancel);
        btnCancel.setActionCommand(ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("DialogAssociationAttendee.cancelButton.actionCommand")); //$NON-NLS-1$ //$NON-NLS-2$
        buttonPane.add(btnCancel);
      }
    }
    this.pack();
    
    if(null == TournamentManagerUI.getMongoClient())
    {
      lblDataEntries.setEnabled(false);
      comboBoxDataEntries.setEnabled(false);
      lblHits.setVisible(false);
    }
  }

  public DialogAssociationAttendee(Vector<IParticipant> participants, int pos)
  {
    this();
    this.vecAttendees = participants;
    this.iPos = pos;
    if(-1 < pos)
    {
      this.textId.setText(participants.elementAt(pos).getCode().trim());
      this.textId.setEditable(false);
      this.textId.setFocusable(false);

      this.textName.setText(participants.elementAt(pos).getName().trim());
      this.textAssociation.setText(((AssociationAttendee)participants.elementAt(pos)).getAssociation());
      this.comboBoxStatus.setSelectedItem(participants.elementAt(pos).getStatus());
      
      this.setTitle("Teilnehmer bearbeiten");
      this.btnAdditionalAttendee.setVisible(false);
      this.btnAdditionalAttendee.setEnabled(false);
      
      this.lblDataEntries.setVisible(false);
      this.comboBoxDataEntries.setVisible(false);
      this.lblHits.setVisible(false);
      
    }
    else
    {
      this.setTitle("Teilnehmer erfassen");
      this.textId.setText(String.valueOf(DialogAssociationAttendee.getNextIndex()));
      this.textId.setEditable(false);
      this.textId.setFocusable(false);
    }    
    this.pack();
  }
  
  
  private class SwingActionOK extends AbstractAction {
    public SwingActionOK() 
    {
      putValue(NAME, ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("DialogAssociationAttendee.actionSaveData.name")); //$NON-NLS-1$ //$NON-NLS-2$
      putValue(SHORT_DESCRIPTION, ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("DialogAssociationAttendee.actionSaveData.short description")); //$NON-NLS-1$ //$NON-NLS-2$
    }
    public void actionPerformed(ActionEvent e) {
      if(-1 == DialogAssociationAttendee.this.iPos)
      { //  new Attendee will be added
        AssociationAttendee _attendee = new AssociationAttendee(Integer.parseInt(DialogAssociationAttendee.this.textId.getText()), DialogAssociationAttendee.this.textName.getText(), DialogAssociationAttendee.this.textAssociation.getText());
        _attendee.setStatus((ParticipantStatus) DialogAssociationAttendee.this.comboBoxStatus.getSelectedItem());
        DialogAssociationAttendee.this.vecAttendees.add(_attendee);
      }
      else
      { //  existing Attendee will be updated
        DialogAssociationAttendee.this.vecAttendees.elementAt(DialogAssociationAttendee.this.iPos).setName(DialogAssociationAttendee.this.textName.getText());
        ((AssociationAttendee)DialogAssociationAttendee.this.vecAttendees.elementAt(DialogAssociationAttendee.this.iPos)).setAssociation(DialogAssociationAttendee.this.textAssociation.getText());
        DialogAssociationAttendee.this.vecAttendees.elementAt(DialogAssociationAttendee.this.iPos).setStatus((ParticipantStatus) DialogAssociationAttendee.this.comboBoxStatus.getSelectedItem());
      }
      DialogAssociationAttendee.this.dispose();
    }
  }
  private class SwingActionCancel extends AbstractAction {
    public SwingActionCancel() {
      putValue(NAME, ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("DialogAssociationAttendee.actionCancel.name")); //$NON-NLS-1$ //$NON-NLS-2$
      putValue(SHORT_DESCRIPTION, ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("DialogAssociationAttendee.actionCancel.short description")); //$NON-NLS-1$ //$NON-NLS-2$
    }
    public void actionPerformed(ActionEvent e) {
      DialogAssociationAttendee.dropIndex();
      DialogAssociationAttendee.this.dispose();
    }
  }
  
  static public int getNextIndex()
  {
    return ++DialogAssociationAttendee.INDEX;
  }
  
  static protected void dropIndex()
  {
    --DialogAssociationAttendee.INDEX;
  }
  
  protected JButton getOkButton() {
    return btnOk;
  }
  protected JButton getCancelButton() {
    return btnCancel;
  }
  protected AbstractButton getNextButton()
  {
    return this.btnAdditionalAttendee;
  }


  private class SwingActionOkAndNext extends AbstractAction {
    public SwingActionOkAndNext() {
      putValue(NAME, ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("DialogAssociationAttendee.actionOkAndNext.name")); //$NON-NLS-1$ //$NON-NLS-2$
      putValue(SHORT_DESCRIPTION, "Some short description");
    }
    public void actionPerformed(ActionEvent e) {
      if(-1 == DialogAssociationAttendee.this.iPos)
      { //  new Attendee will be added
        AssociationAttendee _attendee = new AssociationAttendee(Integer.parseInt(DialogAssociationAttendee.this.textId.getText()), DialogAssociationAttendee.this.textName.getText(), DialogAssociationAttendee.this.textAssociation.getText());
        _attendee.setStatus((ParticipantStatus) DialogAssociationAttendee.this.comboBoxStatus.getSelectedItem());
        DialogAssociationAttendee.this.vecAttendees.add(_attendee);
      }
      else
      { //  existing Attendee will be updated
        DialogAssociationAttendee.this.vecAttendees.elementAt(DialogAssociationAttendee.this.iPos).setName(DialogAssociationAttendee.this.textName.getText());
        ((AssociationAttendee)DialogAssociationAttendee.this.vecAttendees.elementAt(DialogAssociationAttendee.this.iPos)).setAssociation(DialogAssociationAttendee.this.textAssociation.getText());
        DialogAssociationAttendee.this.vecAttendees.elementAt(DialogAssociationAttendee.this.iPos).setStatus((ParticipantStatus) DialogAssociationAttendee.this.comboBoxStatus.getSelectedItem());
      }
      DialogAssociationAttendee.this.textId.setText(String.valueOf(DialogAssociationAttendee.getNextIndex()));
      DialogAssociationAttendee.this.textName.setText("");
      DialogAssociationAttendee.this.textAssociation.setText("");
      DialogAssociationAttendee.this.comboBoxStatus.setSelectedIndex(1);
    }
  }
}
