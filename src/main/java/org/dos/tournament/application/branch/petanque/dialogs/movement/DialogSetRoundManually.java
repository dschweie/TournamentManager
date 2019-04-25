package org.dos.tournament.application.branch.petanque.dialogs.movement;

import javax.swing.JDialog;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.Action;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import net.miginfocom.swing.MigLayout;
import java.awt.Dimension;
import javax.swing.SwingConstants;

import org.dos.tournament.branch.petanque.tournament.movement.SuperMelee;
import org.dos.tournament.common.competition.AbstractTournament.Slot;
import org.dos.tournament.common.player.IParticipant;

public class DialogSetRoundManually extends JDialog {
  private final Action actionOK = new SwingActionCreateMatchday();
  private final Action actionCancel = new SwingActionCancel();
  private JPanel panelMain;
  
  private Vector<JComboBox> comboBoxes = new Vector<JComboBox>();
  
  private SuperMelee tournament;
  private Vector<Vector<Vector<Slot>>> grid;
  private Vector<IParticipant> participants;
  private JButton btnOK;
  private final Action actionFillUp = new SwingActionFillUpForm();
  
  public DialogSetRoundManually() {
    setPreferredSize(new Dimension(600, 400));

    this.setTitle(ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("Supermelee.Action.CreateRoundManually.name"));
    
    panelMain = new JPanel();
    getContentPane().add(panelMain, BorderLayout.CENTER);
    panelMain.setLayout(new MigLayout("", "[][grow][grow][grow][][grow][grow][grow]", "[][]"));
    
    JLabel lblHeim = new JLabel("Heim");
    panelMain.add(lblHeim, "cell 1 0 3 1");
    
    JLabel lblGast = new JLabel("Gast");
    panelMain.add(lblGast, "cell 5 0 3 1");
    
    JPanel panel = new JPanel();
    FlowLayout flowLayout = (FlowLayout) panel.getLayout();
    flowLayout.setAlignment(FlowLayout.RIGHT);
    getContentPane().add(panel, BorderLayout.SOUTH);
    
    btnOK = new JButton("New button");
    btnOK.setAction(actionOK);
    this.actionOK.setEnabled(false);
    panel.add(btnOK);
    
    JButton btnFillUp = new JButton(ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("DialogSetRoundManually.btnNewButton.text")); //$NON-NLS-1$ //$NON-NLS-2$
    btnFillUp.setAction(actionFillUp);
    panel.add(btnFillUp);
    
    JButton btnCancel = new JButton("New button");
    btnCancel.setAction(actionCancel);
    panel.add(btnCancel);
    
  }

  public DialogSetRoundManually(SuperMelee tournament, Vector<Vector<Vector<Slot>>> grid, Vector<IParticipant> participants)
  {
    this();
    
    this.tournament = tournament;
    this.grid = grid;
    this.participants = participants;
    
    for(int _match=0; _match<grid.size(); ++_match)
    {
      panelMain.add(new JLabel(String.format("Spiel %d", _match+1)), String.format("cell 0 %d,alignx trailing", _match+1));
      
      JLabel label = new JLabel(":");
      label.setHorizontalAlignment(SwingConstants.CENTER);
      label.setPreferredSize(new Dimension(20, 14));
      this.panelMain.add(label, String.format("cell 4 %d,alignx trailing", _match+1));

      
      for(int _team=0; _team<grid.get(_match).size(); ++_team)
      {
        
        for(int _participant=0; _participant<grid.get(_match).get(_team).size(); ++_participant)
        {
          JComboBox comboBox = new JComboBox();
          comboBox.addItem("-");
          for(IParticipant _it : participants)
          {
            comboBox.addItem(_it.getCode());
          }
          comboBox.addItemListener(new PreventDuplicatedElements());
          ((PreventDuplicatedElements) comboBox.getItemListeners()[0]).setDefaultColor(comboBox.getBackground());
          this.panelMain.add(comboBox, String.format("cell %d %d,growx",_team*4+1+_participant, _match+1));
          this.comboBoxes.add(comboBox);
        }
        
      }
    }
    
    this.setPreferredSize(new Dimension(600, grid.size()*30+100));
    
    this.pack();
  }

  private class SwingActionCreateMatchday extends AbstractAction {
    public SwingActionCreateMatchday() {
      putValue(NAME, ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("Common.OK.name"));
      putValue(SHORT_DESCRIPTION, "Some short description");
    }
    public void actionPerformed(ActionEvent e) {
      DialogSetRoundManually.this.tournament.compileNextMatchDayFromGrid(DialogSetRoundManually.this.grid, DialogSetRoundManually.this.participants);
    }
  }
  
  private class SwingActionCancel extends AbstractAction {
    public SwingActionCancel() {
      putValue(NAME, ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("Common.Cancel.name"));
      putValue(SHORT_DESCRIPTION, "Some short description");
    }
    public void actionPerformed(ActionEvent e) 
    {
      DialogSetRoundManually.this.dispose();
    }
  }
  
  private class PreventDuplicatedElements implements ItemListener
  {

    private Color colorDefault;
    
    /**
     *  \brief  Standardkonstruktor der Klasse
     */
    public PreventDuplicatedElements()
    { 
      this.colorDefault = new Color(238, 238, 238);
    }
    
    /**
     *  \brief  Setter-Methode für die Farbe der ComboBox wenn alles in Ordnung ist.
     *  
     *  Diese Farbe wird gesetzt, wenn 
     *   
     *  @param value
     */
    public void setDefaultColor(Color value)
    {
      this.colorDefault = value;
    }

    /**
     *  \brief  Die Methode prüft den neuen Wert der ComboBox auf Eindeutigkeit
     *  
     *  Jeder Teilnehmer darf in den Spielpaarungen nur einmal vorkommen. Nach
     *  Änderung des Wertes der ComboBox wird geprüft, ob die Regel erfüllt
     *  ist. 
     */
    public void itemStateChanged(ItemEvent e)
    {
      boolean _flagCorrect = true;
      boolean _flagDuplicate = false;
      
      for(JComboBox iti : DialogSetRoundManually.this.comboBoxes)
      { 
        _flagDuplicate = false;
        if(0 < iti.getSelectedIndex())
        {
          for(JComboBox itk : DialogSetRoundManually.this.comboBoxes)
          {
            if(itk != iti)
              _flagDuplicate |= (itk.getSelectedIndex() == iti.getSelectedIndex());
          }
        }
        else
          _flagCorrect = false;
        
        if(_flagDuplicate)
        { //  In this case an index was detected in more than one combobox
          iti.setBackground(Color.red);
          _flagCorrect = false;
        }
        else
          iti.setBackground(colorDefault);

      }
      
      if(0 < ((JComboBox)e.getSource()).getSelectedIndex())
      {
        ((JComboBox)e.getSource()).setToolTipText(DialogSetRoundManually.this.participants.get(((JComboBox)e.getSource()).getSelectedIndex()-1).getDescription());
        this.updateGrid(((JComboBox)e.getSource()));
      }
      DialogSetRoundManually.this.actionOK.setEnabled(_flagCorrect);
    }
    
    /**
     *  \brief  Die Methode übernimmt den ausgewählten Spieler von der ComboBox in die Grid-Struktur.
     *  
     *  Der Dialog kennt zunächst nur die ComboBoxen mit den Werten. Die 
     *  ausgewählten Spieler müssen in das Grid übertragen werden, damit dann
     *  eine Runde gebildet werden kann.
     */
    private void updateGrid(JComboBox comboBox)
    {
      int _idxParticipant = comboBox.getSelectedIndex()-1;
      int _idxSlot = DialogSetRoundManually.this.comboBoxes.indexOf(comboBox);
      int _idxCurrent = 0;
      
      for(int m=0; (m < DialogSetRoundManually.this.grid.size()) && (_idxCurrent <= _idxSlot); ++m)
        for(int t=0; (t < DialogSetRoundManually.this.grid.get(m).size()) && (_idxCurrent <= _idxSlot);++t)
          for(int s=0; (s < DialogSetRoundManually.this.grid.get(m).get(t).size()) && (_idxCurrent <= _idxSlot);++s)
          {
            if(_idxCurrent == _idxSlot)
            {
              DialogSetRoundManually.this.grid.get(m).get(t).get(s).setNumber(new Integer(_idxParticipant));
              DialogSetRoundManually.this.grid.get(m).get(t).get(s).setBooked(-1 < DialogSetRoundManually.this.grid.get(m).get(t).get(s).getNumber().intValue());
            }
            ++_idxCurrent;
          }
    }
      
  }
  private class SwingActionFillUpForm extends AbstractAction {
    public SwingActionFillUpForm() {
      putValue(NAME, ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("DialogSetRoundManually.FillUp.name"));
      putValue(SHORT_DESCRIPTION, ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("DialogSetRoundManually.FillUp.shortDescription"));
    }
    public void actionPerformed(ActionEvent e) {
      DialogSetRoundManually.this.comboBoxes.forEach(it -> {
        if(0 == it.getSelectedIndex())
        { //  
          
        }
      });
    }
  }
}
