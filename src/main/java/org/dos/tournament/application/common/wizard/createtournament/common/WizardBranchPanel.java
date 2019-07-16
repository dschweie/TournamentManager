package org.dos.tournament.application.common.wizard.createtournament.common;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import org.dos.tournament.application.common.wizard.AbstractRuleEngineWizard;
import org.dos.tournament.application.common.wizard.createtournament.AbstractTournamentWizardPanel;

import javax.swing.JRadioButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import java.awt.Component;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

public class WizardBranchPanel extends AbstractTournamentWizardPanel 
{
  private final ButtonGroup buttonGroup = new ButtonGroup();
  
  public WizardBranchPanel(AbstractRuleEngineWizard wizard)
  {
    this();
    this.xWizard = wizard;
  }
  
  public WizardBranchPanel() 
  {
    setBorder(new TitledBorder(null, "Turnierart", TitledBorder.LEADING, TitledBorder.TOP, null, null));
    setLayout(new FormLayout(new ColumnSpec[] {
        FormSpecs.RELATED_GAP_COLSPEC,
        ColumnSpec.decode("129px"),},
      new RowSpec[] {
        FormSpecs.PARAGRAPH_GAP_ROWSPEC,
        RowSpec.decode("23px"),
        FormSpecs.LINE_GAP_ROWSPEC,
        RowSpec.decode("23px"),}));
    
    JRadioButton rdbtnPetanque = new JRadioButton("Petanque");
    buttonGroup.add(rdbtnPetanque);
    add(rdbtnPetanque, "2, 2, fill, center");
    
    JRadioButton rdbtnBrettspiele = new JRadioButton("Brettspiele");
    buttonGroup.add(rdbtnBrettspiele);
    add(rdbtnBrettspiele, "2, 4, fill, center");
    
    rdbtnPetanque.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) 
      {
        WizardBranchPanel.this.xWizard.setWizardProperty(AbstractTournamentWizardPanel.BRANCH, AbstractTournamentWizardPanel.PETANQUE);
      }
    });

    rdbtnBrettspiele.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) 
      {
        WizardBranchPanel.this.xWizard.setWizardProperty(AbstractTournamentWizardPanel.BRANCH, AbstractTournamentWizardPanel.BOARDGAME);
      }
    });
  }

  
}
