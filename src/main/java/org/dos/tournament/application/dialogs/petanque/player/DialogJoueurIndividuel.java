package org.dos.tournament.application.dialogs.petanque.player;

import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.AbstractAction;

import org.dos.tournament.application.dialogs.player.DialogAssociationAttendee;
import org.dos.tournament.branch.petanque.team.JoueurIndividuel;
import org.dos.tournament.common.player.AssociationAttendee;
import org.dos.tournament.common.player.IParticipant;
import org.dos.tournament.common.player.utils.ParticipantStatus;

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
    if(-1 < pos)
    {
      this.textSurname.setText(((JoueurIndividuel)participants.elementAt(pos)).getSurname().trim());
      this.textName.setText(((JoueurIndividuel)participants.elementAt(pos)).getForename().trim());
    }
  }
  
  private class SwingActionOK extends AbstractAction 
  {
    public SwingActionOK() 
    {
      putValue(NAME, ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("DialogAssociationAttendee.actionSaveData.name")); //$NON-NLS-1$ //$NON-NLS-2$
      putValue(SHORT_DESCRIPTION, ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("DialogAssociationAttendee.actionSaveData.short description")); //$NON-NLS-1$ //$NON-NLS-2$
    }
    public void actionPerformed(ActionEvent e) {
      if(DialogJoueurIndividuel.this.checkDialogData())
      {
        if(-1 == DialogJoueurIndividuel.this.iPos)
        { //  new Attendee will be added
          AssociationAttendee _attendee = null;
          
          if(     (     DialogJoueurIndividuel.this.comboBoxDataEntries.isEnabled()         ) 
              &&  ( 0 < DialogJoueurIndividuel.this.comboBoxDataEntries.getSelectedIndex()  ) )
          { // In diesem Fall ist das Objekt der Combobox zu nehmen
            _attendee = (AssociationAttendee) DialogJoueurIndividuel.this.comboBoxDataEntries.getSelectedItem();
          }
          else
          {
            _attendee = new JoueurIndividuel(Integer.parseInt(DialogJoueurIndividuel.this.textId.getText()), DialogJoueurIndividuel.this.textName.getText().trim(), DialogJoueurIndividuel.this.textSurname.getText().trim(), DialogJoueurIndividuel.this.textAssociation.getText().trim());
            _attendee.setStatus((ParticipantStatus) DialogJoueurIndividuel.this.comboBoxStatus.getSelectedItem());
          }
          
          if(null != _attendee)
            DialogJoueurIndividuel.this.vecAttendees.add(_attendee);
        }
        else
        { //  existing Attendee will be updated
          DialogJoueurIndividuel.this.vecAttendees.elementAt(DialogJoueurIndividuel.this.iPos).setName(DialogJoueurIndividuel.this.textName.getText());
          ((JoueurIndividuel)DialogJoueurIndividuel.this.vecAttendees.elementAt(DialogJoueurIndividuel.this.iPos)).setSurname(DialogJoueurIndividuel.this.textSurname.getText());
          ((JoueurIndividuel)DialogJoueurIndividuel.this.vecAttendees.elementAt(DialogJoueurIndividuel.this.iPos)).setAssociation(DialogJoueurIndividuel.this.textAssociation.getText());
          DialogJoueurIndividuel.this.vecAttendees.elementAt(DialogJoueurIndividuel.this.iPos).setStatus((ParticipantStatus) DialogJoueurIndividuel.this.comboBoxStatus.getSelectedItem());
        }
        DialogJoueurIndividuel.this.dispose();
      }
    }
  }

  private class SwingActionOkAndNext extends AbstractAction 
  {
    /**
     *  Standardkonstruktor der Aktion
     */
    public SwingActionOkAndNext() {
      putValue(NAME, ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("DialogAssociationAttendee.actionOkAndNext.name")); //$NON-NLS-1$ //$NON-NLS-2$
      putValue(SHORT_DESCRIPTION, "Some short description");
    }
    
    /**
     *  Diese 
     */
    public void actionPerformed(ActionEvent e) {
      if(DialogJoueurIndividuel.this.checkDialogData())
      {
        if(-1 == DialogJoueurIndividuel.this.iPos)
        { //  new Attendee will be added
          AssociationAttendee _attendee = new JoueurIndividuel(Integer.parseInt(DialogJoueurIndividuel.this.textId.getText()), DialogJoueurIndividuel.this.textName.getText().trim(), DialogJoueurIndividuel.this.textSurname.getText().trim(), DialogJoueurIndividuel.this.textAssociation.getText().trim());
          _attendee.setStatus((ParticipantStatus) DialogJoueurIndividuel.this.comboBoxStatus.getSelectedItem());
          DialogJoueurIndividuel.this.vecAttendees.add(_attendee);
        }
        else
        { //  existing Attendee will be updated
          ((JoueurIndividuel)DialogJoueurIndividuel.this.vecAttendees.elementAt(DialogJoueurIndividuel.this.iPos)).setSurname(DialogJoueurIndividuel.this.textSurname.getText());
          ((JoueurIndividuel)DialogJoueurIndividuel.this.vecAttendees.elementAt(DialogJoueurIndividuel.this.iPos)).setForename(DialogJoueurIndividuel.this.textName.getText());
          ((JoueurIndividuel)DialogJoueurIndividuel.this.vecAttendees.elementAt(DialogJoueurIndividuel.this.iPos)).setAssociation(DialogJoueurIndividuel.this.textAssociation.getText());
          DialogJoueurIndividuel.this.vecAttendees.elementAt(DialogJoueurIndividuel.this.iPos).setStatus((ParticipantStatus) DialogJoueurIndividuel.this.comboBoxStatus.getSelectedItem());
        }
        
        DialogJoueurIndividuel.this.textId.setText(String.valueOf(DialogAssociationAttendee.getNextIndex()));
        DialogJoueurIndividuel.this.textName.setText("");
        DialogJoueurIndividuel.this.textSurname.setText("");
        DialogJoueurIndividuel.this.textSurname.requestFocus();
        DialogJoueurIndividuel.this.textAssociation.setText("");
        DialogJoueurIndividuel.this.comboBoxStatus.setSelectedIndex(1);
        DialogJoueurIndividuel.this.repaint();
      }
    }
  }
}
