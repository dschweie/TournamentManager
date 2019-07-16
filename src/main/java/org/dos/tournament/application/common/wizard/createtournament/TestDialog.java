package org.dos.tournament.application.common.wizard.createtournament;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.Window.Type;
import javax.swing.JScrollPane;

import org.dos.tournament.application.common.wizard.AbstractRuleEngineWizard;
import org.dos.tournament.application.common.wizard.createtournament.common.WizardBranchPanel;
import org.dos.tournament.application.factory.model.ITournamentWorker;
import org.dos.tournament.application.factory.model.TournamentFactoryRulesEngine;

public class TestDialog extends AbstractRuleEngineWizard implements ITournamentWorker  
{
  private boolean bEmpty = false;
  
  private String strTrace = null;
  private String strTraceCount = null;
  
  public TestDialog(JFrame owner) {
    super(owner);

    setMinimumSize(new Dimension(600, 500));
    setResizable(false);
    setPreferredSize(new Dimension(600, 500));
    setModalityType(ModalityType.APPLICATION_MODAL);
    setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
    setModal(true);
    
    JPanel panel = new JPanel();
    getContentPane().add(panel, BorderLayout.CENTER);
    panel.setLayout(new BorderLayout(0, 0));
    
    JScrollPane scrollPane = new JScrollPane();
    panel.add(scrollPane, BorderLayout.CENTER);
    
    JPanel panel_1 = new WizardBranchPanel();
    scrollPane.setViewportView(panel_1);
  }

  @Override
  public boolean isPropertiesBranch_Null() {
    // TODO Auto-generated method stub
    return true;
  }

  @Override
  public boolean isPropertiesBranch_Petanque() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean isWizardControlEmptyPane() {
    // TODO Auto-generated method stub
    return this.bEmpty;
  }

  @Override
  public boolean isWizardControlCommand_Cancel() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean isWizardControlCommand_Back() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean isWizardControlCommand_Next() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean isWizardBranch_Null() {
    // TODO Auto-generated method stub
    return true;
  }

  @Override
  public boolean isWizardBranch_Petanque() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean isWizardLastStep_Branch() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean isWizardLastStep_PetCat() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean isWizardLastStep_PetSupSel() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean isPetCatTournamentCategory_Null() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean isPetCatTournamentCategory_Supermelee() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean isPetComMovTimeBoxed() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean isPetComMovDefineTracksByMovement() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void doDisposeSelectedBranchFromProperties() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void doPetanqueTimeBoxedMatchday() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void doPetanqueDefineTracksByMovement() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void doWizardControlStatusCancel_Enabled() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void doWizardControlStatusCancel_Disabled() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void doWizardControlStatusBack_Enabled() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void doWizardControlStatusBack_Disabled() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void doWizardControlStatusNext_Enabled() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void doWizardControlStatusNext_Disabled() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void doWizardControlStatusNext_To_Finish() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void doWizardControlSetPanel_Dispose() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void doWizardControlSetPanel_Branch() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void doWizardControlSetPanel_PetCat() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void doWizardControlSetPanel_PetSupSel() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void doWizardControlSetPanel_PetComMov() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void doWizardControlRollBack_Dispose() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void doWizardControlRollBack_Branch() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void doWizardControlRollBack_PetCat() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void doWizardControlRollBack_PetSupSel() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void doWizardControlRollBack_PetComMov() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void doWizardControlPause() {
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  @Override
  public void doDisplayHint_Select() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void doDisposeProduct() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void doDeliverProduct() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void doTrace(String dtName, String version, int rules, int rule) {
    if(null == this.strTrace)
    {
      this.strTrace = "";
      this.strTraceCount = ".";
    }

    String _strTrace = dtName.concat(", ").concat(version).concat(", ").concat(String.valueOf(rule));
    if(_strTrace.equals(this.strTrace))
    {
      switch(this.strTraceCount.length() % 10)
      {
        case 4:   this.strTraceCount = this.strTraceCount.concat("i"); System.out.print("i"); break;
        case 9:   this.strTraceCount = this.strTraceCount.concat("|"); System.out.print("|"); break;
        default:  this.strTraceCount = this.strTraceCount.concat("."); System.out.print("."); break;
      }
    }
    else
    {
      this.strTrace = _strTrace;
      this.strTraceCount = ".";
      System.out.println("");
      System.out.print(this.strTrace.concat(" ."));
    }
  }

  @Override
  public boolean isWizardControlWizardVisible() {
    return this.isVisible();
  }

  @Override
  public void createRuleEngine() {
    // TODO Auto-generated method stub
    
  }

  @Override
  protected void execute() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public boolean isPetCatTournamentCategory_SupMonat() {
    // TODO Auto-generated method stub
    return false;
  }


}
