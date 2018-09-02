package org.dos.tournament.application.dialogs.player;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

import org.dos.tournament.player.AssociationAttendee;
import org.dos.tournament.player.IParticipant;
import org.dos.tournament.player.utils.ParticipantStatus;
import java.awt.Dialog.ModalityType;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;

public class DialogAssociationAttendee extends JDialog
{
  static private int INDEX = 0;

  private final JPanel contentPanel = new JPanel();
  private JTextField textId;
  private JTextField textName;
  private JTextField textAssociation;
  private JLabel lblId;
  private JLabel lblName;
  private JLabel lblAssociation;
  private JLabel lblState;
  private JComboBox comboBoxStatus;
  private final Action actionSaveData = new SwingActionOK();
  
  private Vector<IParticipant> vecAttendees = null;
  private int iPos;
  private final Action actionCancel = new SwingActionCancel();

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
    contentPanel.setLayout(new FormLayout(new ColumnSpec[] {
        ColumnSpec.decode("center:4px"),
        ColumnSpec.decode("center:100px"),
        ColumnSpec.decode("center:4px"),
        ColumnSpec.decode("100px:grow"),},
      new RowSpec[] {
        FormSpecs.LINE_GAP_ROWSPEC,
        RowSpec.decode("30px"),
        RowSpec.decode("30px"),
        RowSpec.decode("30px"),
        RowSpec.decode("30px"),}));
    {
      lblId = new JLabel(ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("DialogAssociationAttendee.lblId.text")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-1$ //$NON-NLS-2$
      contentPanel.add(lblId, "2, 2, right, center");
    }
    {
      textId = new JTextField();
      textId.setEditable(false);
      textId.setEnabled(false);
      lblId.setLabelFor(textId);
      contentPanel.add(textId, "4, 2, fill, default");
      textId.setColumns(10);
    }
    {
      lblName = new JLabel(ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("DialogAssociationAttendee.lblName.text")); //$NON-NLS-1$ //$NON-NLS-2$
      contentPanel.add(lblName, "2, 3, right, center");
    }
    {
      textName = new JTextField();
      lblName.setLabelFor(textName);
      contentPanel.add(textName, "4, 3, fill, default");
      textName.setColumns(10);
    }
    {
      lblAssociation = new JLabel(ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("DialogAssociationAttendee.lblAssociation.text")); //$NON-NLS-1$ //$NON-NLS-2$
      contentPanel.add(lblAssociation, "2, 4, right, default");
    }
    {
      textAssociation = new JTextField();
      lblAssociation.setLabelFor(textAssociation);
      contentPanel.add(textAssociation, "4, 4, fill, default");
      textAssociation.setColumns(10);
    }
    {
      lblState = new JLabel(ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("DialogAssociationAttendee.lblState.text")); //$NON-NLS-1$ //$NON-NLS-2$
      contentPanel.add(lblState, "2, 5, right, default");
    }
    {
      comboBoxStatus = new JComboBox();
      lblState.setLabelFor(comboBoxStatus);
      comboBoxStatus.setModel(new DefaultComboBoxModel(ParticipantStatus.values()));
      comboBoxStatus.setSelectedIndex(1);
      contentPanel.add(comboBoxStatus, "4, 5, fill, default");
    }
    {
      JPanel buttonPane = new JPanel();
      getContentPane().add(buttonPane, BorderLayout.SOUTH);
      buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
      {
        JButton okButton = new JButton(ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("DialogAssociationAttendee.okButton.text")); //$NON-NLS-1$ //$NON-NLS-2$
        okButton.setAction(actionSaveData);
        okButton.setActionCommand(ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("DialogAssociationAttendee.okButton.actionCommand")); //$NON-NLS-1$ //$NON-NLS-2$
        buttonPane.add(okButton);
        getRootPane().setDefaultButton(okButton);
      }
      {
        JButton cancelButton = new JButton(ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("DialogAssociationAttendee.cancelButton.text")); //$NON-NLS-1$ //$NON-NLS-2$
        cancelButton.setAction(actionCancel);
        cancelButton.setActionCommand(ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("DialogAssociationAttendee.cancelButton.actionCommand")); //$NON-NLS-1$ //$NON-NLS-2$
        buttonPane.add(cancelButton);
      }
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
    }
    else
    {
      
      this.textId.setText(String.valueOf(++DialogAssociationAttendee.INDEX));
      this.textId.setEditable(false);
      this.textId.setFocusable(false);
    }    
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
      DialogAssociationAttendee.this.dispose();
    }
  }
}
