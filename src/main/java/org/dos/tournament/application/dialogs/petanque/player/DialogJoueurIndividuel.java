package org.dos.tournament.application.dialogs.petanque.player;

import java.awt.event.ActionEvent;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.AbstractAction;

import org.dos.tournament.application.dialogs.player.DialogAssociationAttendee;
import org.dos.tournament.petanque.team.JoueurIndividuel;
import org.dos.tournament.player.AssociationAttendee;
import org.dos.tournament.player.IParticipant;
import org.dos.tournament.player.utils.ParticipantStatus;

public class DialogJoueurIndividuel extends DialogAssociationAttendee
{
  public DialogJoueurIndividuel()
  {
    super();
    this.getOkButton().setAction(new org.dos.tournament.application.dialogs.petanque.player.DialogJoueurIndividuel.SwingActionOK());
    if(this.getNextButton().isEnabled())
      this.getNextButton().setAction(new org.dos.tournament.application.dialogs.petanque.player.DialogJoueurIndividuel.SwingActionOkAndNext());
  }

  public DialogJoueurIndividuel(Vector<IParticipant> participants, int pos)
  {
    super(participants, pos);  
    this.getOkButton().setAction(new org.dos.tournament.application.dialogs.petanque.player.DialogJoueurIndividuel.SwingActionOK());
    if(this.getNextButton().isEnabled())
      this.getNextButton().setAction(new org.dos.tournament.application.dialogs.petanque.player.DialogJoueurIndividuel.SwingActionOkAndNext());
  }
  
  private class SwingActionOK extends AbstractAction {
    public SwingActionOK() 
    {
      putValue(NAME, ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("DialogAssociationAttendee.actionSaveData.name")); //$NON-NLS-1$ //$NON-NLS-2$
      putValue(SHORT_DESCRIPTION, ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("DialogAssociationAttendee.actionSaveData.short description")); //$NON-NLS-1$ //$NON-NLS-2$
    }
    public void actionPerformed(ActionEvent e) {
      if(-1 == DialogJoueurIndividuel.this.iPos)
      { //  new Attendee will be added
        AssociationAttendee _attendee = new JoueurIndividuel(Integer.parseInt(DialogJoueurIndividuel.this.textId.getText()), DialogJoueurIndividuel.this.textName.getText().trim(), DialogJoueurIndividuel.this.textSurname.getText().trim(), DialogJoueurIndividuel.this.textAssociation.getText().trim());
        _attendee.setStatus((ParticipantStatus) DialogJoueurIndividuel.this.comboBoxStatus.getSelectedItem());
        DialogJoueurIndividuel.this.vecAttendees.add(_attendee);
      }
      else
      { //  existing Attendee will be updated
        DialogJoueurIndividuel.this.vecAttendees.elementAt(DialogJoueurIndividuel.this.iPos).setName(DialogJoueurIndividuel.this.textName.getText());
        ((JoueurIndividuel)DialogJoueurIndividuel.this.vecAttendees.elementAt(DialogJoueurIndividuel.this.iPos)).setAssociation(DialogJoueurIndividuel.this.textAssociation.getText());
        DialogJoueurIndividuel.this.vecAttendees.elementAt(DialogJoueurIndividuel.this.iPos).setStatus((ParticipantStatus) DialogJoueurIndividuel.this.comboBoxStatus.getSelectedItem());
      }
      DialogJoueurIndividuel.this.dispose();
    }
  }

  private class SwingActionOkAndNext extends AbstractAction {
    public SwingActionOkAndNext() {
      putValue(NAME, ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("DialogAssociationAttendee.actionOkAndNext.name")); //$NON-NLS-1$ //$NON-NLS-2$
      putValue(SHORT_DESCRIPTION, "Some short description");
    }
    public void actionPerformed(ActionEvent e) {
      if(-1 == DialogJoueurIndividuel.this.iPos)
      { //  new Attendee will be added
        AssociationAttendee _attendee = new JoueurIndividuel(Integer.parseInt(DialogJoueurIndividuel.this.textId.getText()), DialogJoueurIndividuel.this.textName.getText().trim(), DialogJoueurIndividuel.this.textSurname.getText().trim(), DialogJoueurIndividuel.this.textAssociation.getText().trim());
        _attendee.setStatus((ParticipantStatus) DialogJoueurIndividuel.this.comboBoxStatus.getSelectedItem());
        DialogJoueurIndividuel.this.vecAttendees.add(_attendee);
      }
      else
      { //  existing Attendee will be updated
        DialogJoueurIndividuel.this.vecAttendees.elementAt(DialogJoueurIndividuel.this.iPos).setName(DialogJoueurIndividuel.this.textName.getText());
        ((JoueurIndividuel)DialogJoueurIndividuel.this.vecAttendees.elementAt(DialogJoueurIndividuel.this.iPos)).setAssociation(DialogJoueurIndividuel.this.textAssociation.getText());
        DialogJoueurIndividuel.this.vecAttendees.elementAt(DialogJoueurIndividuel.this.iPos).setStatus((ParticipantStatus) DialogJoueurIndividuel.this.comboBoxStatus.getSelectedItem());
      }
      
      DialogJoueurIndividuel.this.textId.setText(String.valueOf(DialogAssociationAttendee.getNextIndex()));
      DialogJoueurIndividuel.this.textName.setText("");
      DialogJoueurIndividuel.this.textAssociation.setText("");
      DialogJoueurIndividuel.this.comboBoxStatus.setSelectedIndex(1);
      DialogJoueurIndividuel.this.repaint();
    }
  }
}
