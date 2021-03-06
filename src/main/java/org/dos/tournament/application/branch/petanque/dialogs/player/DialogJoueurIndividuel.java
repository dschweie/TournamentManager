package org.dos.tournament.application.branch.petanque.dialogs.player;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import org.dos.tournament.application.TournamentManagerUI;
import org.dos.tournament.application.common.dialogs.player.DialogAssociationAttendee;
import org.dos.tournament.branch.petanque.team.JoueurIndividuel;
import org.dos.tournament.common.player.AssociationAttendee;
import org.dos.tournament.common.player.IParticipant;
import org.dos.tournament.common.player.utils.ParticipantStatus;

public class DialogJoueurIndividuel extends DialogAssociationAttendee
{

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  public DialogJoueurIndividuel()
  {
    super();
    this.getOkButton().setAction(new org.dos.tournament.application.branch.petanque.dialogs.player.DialogJoueurIndividuel.SwingActionOK());
    if(this.getNextButton().isEnabled())
      this.getNextButton().setAction(new org.dos.tournament.application.branch.petanque.dialogs.player.DialogJoueurIndividuel.SwingActionOkAndNext());
  }

  public DialogJoueurIndividuel(ArrayList<IParticipant> participants, int pos)
  {
    super(participants, pos);
    this.getOkButton().setAction(new org.dos.tournament.application.branch.petanque.dialogs.player.DialogJoueurIndividuel.SwingActionOK());
    if(this.getNextButton().isEnabled())
      this.getNextButton().setAction(new org.dos.tournament.application.branch.petanque.dialogs.player.DialogJoueurIndividuel.SwingActionOkAndNext());
    if(-1 < pos)
    {
      this.textSurname.setText(((JoueurIndividuel)participants.get(pos)).getSurname().trim());
      this.textName.setText(((JoueurIndividuel)participants.get(pos)).getForename().trim());
    }
  }

  private class SwingActionOK extends AbstractAction
  {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public SwingActionOK()
    {
      putValue(NAME, ResourceBundle.getBundle(TournamentManagerUI.MESSAGES).getString("DialogAssociationAttendee.actionSaveData.name")); //$NON-NLS-1$ //$NON-NLS-2$
      putValue(SHORT_DESCRIPTION, ResourceBundle.getBundle(TournamentManagerUI.MESSAGES).getString("DialogAssociationAttendee.actionSaveData.short description")); //$NON-NLS-1$ //$NON-NLS-2$
    }
    public void actionPerformed(ActionEvent e) {
      if(DialogJoueurIndividuel.this.checkDialogData())
      {
        boolean _changeConfirmed = true;
        if(ParticipantStatus.DISQUALIFIED == DialogJoueurIndividuel.this.comboBoxStatus.getSelectedItem())
          _changeConfirmed = (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(  DialogJoueurIndividuel.this,
                                                                                        ResourceBundle.getBundle(TournamentManagerUI.MESSAGES).getString("DialogJoueurIndividuel.SwingActionOK.ConfirmDisqualifikation.message").concat("\n\n").concat(ResourceBundle.getBundle(TournamentManagerUI.MESSAGES).getString("Commom.NoUndo.message")),
                                                                                        ResourceBundle.getBundle(TournamentManagerUI.MESSAGES).getString("DialogJoueurIndividuel.SwingActionOK.ConfirmDisqualifikation.title"),
                                                                                        JOptionPane.YES_NO_OPTION));

        if(_changeConfirmed)
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
            DialogJoueurIndividuel.this.vecAttendees.get(DialogJoueurIndividuel.this.iPos).setName(DialogJoueurIndividuel.this.textName.getText());
            ((JoueurIndividuel)DialogJoueurIndividuel.this.vecAttendees.get(DialogJoueurIndividuel.this.iPos)).setSurname(DialogJoueurIndividuel.this.textSurname.getText());
            ((JoueurIndividuel)DialogJoueurIndividuel.this.vecAttendees.get(DialogJoueurIndividuel.this.iPos)).setAssociation(DialogJoueurIndividuel.this.textAssociation.getText());
            DialogJoueurIndividuel.this.vecAttendees.get(DialogJoueurIndividuel.this.iPos).setStatus((ParticipantStatus) DialogJoueurIndividuel.this.comboBoxStatus.getSelectedItem());
          }
          DialogJoueurIndividuel.this.dispose();
        }
      }
    }
  }

  private class SwingActionOkAndNext extends AbstractAction
  {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     *  Standardkonstruktor der Aktion
     */
    public SwingActionOkAndNext() {
      putValue(NAME, ResourceBundle.getBundle(TournamentManagerUI.MESSAGES).getString("DialogAssociationAttendee.actionOkAndNext.name")); //$NON-NLS-1$ //$NON-NLS-2$
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
          AssociationAttendee _attendee = null;

          if(     (     DialogJoueurIndividuel.this.comboBoxDataEntries.isEnabled()         )
              &&  ( 0 < DialogJoueurIndividuel.this.comboBoxDataEntries.getSelectedIndex()  ) )
          { // In diesem Fall ist das Objekt der Combobox zu nehmen
            _attendee = (AssociationAttendee) DialogJoueurIndividuel.this.comboBoxDataEntries.getSelectedItem();
          }
          else
          {
           _attendee = new JoueurIndividuel(Integer.parseInt(DialogJoueurIndividuel.this.textId.getText()), DialogJoueurIndividuel.this.textName.getText().trim(), DialogJoueurIndividuel.this.textSurname.getText().trim(), DialogJoueurIndividuel.this.textAssociation.getText().trim());
          }

          if(null != _attendee)
            _attendee.setStatus((ParticipantStatus) DialogJoueurIndividuel.this.comboBoxStatus.getSelectedItem());

          DialogJoueurIndividuel.this.vecAttendees.add(_attendee);
        }
        else
        { //  existing Attendee will be updated
          ((JoueurIndividuel)DialogJoueurIndividuel.this.vecAttendees.get(DialogJoueurIndividuel.this.iPos)).setSurname(DialogJoueurIndividuel.this.textSurname.getText());
          ((JoueurIndividuel)DialogJoueurIndividuel.this.vecAttendees.get(DialogJoueurIndividuel.this.iPos)).setForename(DialogJoueurIndividuel.this.textName.getText());
          ((JoueurIndividuel)DialogJoueurIndividuel.this.vecAttendees.get(DialogJoueurIndividuel.this.iPos)).setAssociation(DialogJoueurIndividuel.this.textAssociation.getText());
          DialogJoueurIndividuel.this.vecAttendees.get(DialogJoueurIndividuel.this.iPos).setStatus((ParticipantStatus) DialogJoueurIndividuel.this.comboBoxStatus.getSelectedItem());
        }

        DialogJoueurIndividuel.this.textId.setText(String.valueOf(DialogJoueurIndividuel.this.getNextIndex()));
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
