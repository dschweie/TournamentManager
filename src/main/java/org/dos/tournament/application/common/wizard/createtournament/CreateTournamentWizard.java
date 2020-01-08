 package org.dos.tournament.application.common.wizard.createtournament;

import javax.swing.JFrame;

import org.dos.tournament.application.common.panels.AbstractTournamentPanel;
import org.dos.tournament.application.common.wizard.AbstractRuleEngineWizard;
import org.dos.tournament.application.common.wizard.createtournament.common.WizardBranchPanel;
import org.dos.tournament.application.common.wizard.createtournament.petanque.WizardPetanqueCategoryPanel;
import org.dos.tournament.application.common.wizard.createtournament.petanque.WizardPetanqueTournamentOptions;
import org.dos.tournament.application.factory.model.ITournamentWorker;
import org.dos.tournament.application.factory.model.TournamentFactoryRulesEngine;

import javax.swing.BoxLayout;
import java.awt.BorderLayout;
import java.awt.Dialog.ModalExclusionType;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import java.util.ResourceBundle;
import java.util.Stack;

import javax.swing.Action;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.JScrollPane;
import java.awt.Dimension;
import javax.swing.ScrollPaneConstants;
import java.awt.Window.Type;
import java.awt.Dialog.ModalityType;
import java.awt.ComponentOrientation;
import java.awt.Component;

public class CreateTournamentWizard extends AbstractRuleEngineWizard implements ITournamentWorker 
{
  public static Stack<WizardAction> xSettings = new Stack<WizardAction>();
 
  private final AbstractWizardAction actionCancel = new WizardCancelAction();
  private final AbstractWizardAction actionBack = new WizardBackAction();
  private final AbstractWizardAction actionNext = new WizardNextAction();
  private JScrollPane scrollPaneWizard;
  
  private Stack<AbstractTournamentWizardPanel> panelStack = new Stack<AbstractTournamentWizardPanel>();
  
  private WizardStep step = WizardStep.branch;
  
  
  //public CreateTournamentWizard(JFrame owner) 
  public CreateTournamentWizard() 
  {
    super();
    //super(owner);
    
    setModal(true);
    setModalityType(ModalityType.APPLICATION_MODAL);
    setPreferredSize(new Dimension(600, 400));
    setMinimumSize(new Dimension(600, 400));
    setResizable(false);
    setTitle("Create Tournament");
    getContentPane().setLayout(new BorderLayout(0, 0));
    
    JPanel mainPanel = new JPanel();
    getContentPane().add(mainPanel, BorderLayout.CENTER);
    mainPanel.setLayout(new BorderLayout(0, 0));
    
    scrollPaneWizard = new JScrollPane();
    scrollPaneWizard.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
    mainPanel.add(scrollPaneWizard, BorderLayout.CENTER);
    scrollPaneWizard.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    scrollPaneWizard.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
    scrollPaneWizard.setPreferredSize(new Dimension(600, 300));
    
    JPanel panelNavigation = new JPanel();
    mainPanel.add(panelNavigation, BorderLayout.SOUTH);
    panelNavigation.setPreferredSize(new Dimension(600, 35));
    panelNavigation.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
    
    JButton btnCancel = new JButton("New button");
    btnCancel.setAction(actionCancel);
    panelNavigation.add(btnCancel);
    
    JButton btnBack = new JButton("New button");
    btnBack.setAction(actionBack);
    panelNavigation.add(btnBack);
    
    JButton btnNext = new JButton("New button");
    btnNext.setAction(actionNext);
    panelNavigation.add(btnNext);
    
    panelNavigation.setVisible(true);
    mainPanel.setVisible(true);
    
    this.setVisible(false);
    this.pack();
    this.revalidate();
    
    this.xEngine = new TournamentFactoryRulesEngine();
  }

  @Override
  public boolean isPropertiesBranch_Null() 
  {
    try
    {
      return true;
    }
    catch(NullPointerException e)
    {
      return true; 
    }
  }

  @Override
  public boolean isPropertiesBranch_Petanque() {
    return false;
    //return this.panelStack.isEmpty()?null:AbstractTournamentWizardPanel.PETANQUE.equals(this.panelStack.peek().getValue(AbstractTournamentWizardPanel.BRANCH));
  }

  @Override
  public boolean isWizardControlEmptyPane() 
  {
    //return this.panelStack.isEmpty()?true:this.scrollPaneWizard.getComponent(0) == this.panelStack.peek();
    //return 0 == this.scrollPaneWizard.getComponentCount();
    return 0 == this.scrollPaneWizard.getViewport().getComponentCount();
  }

  @Override
  public boolean isWizardControlCommand_Cancel() 
  {
    return this.actionCancel.isPressed();
  }

  @Override
  public boolean isWizardControlCommand_Back() 
  {
    return this.actionBack.isPressed();
  }

  @Override
  public boolean isWizardControlCommand_Next() 
  {
    return this.actionNext.isPressed();
  }

  @Override
  public boolean isWizardBranch_Null() {
    return null == this.getWizardProperty(AbstractTournamentWizardPanel.BRANCH);
  }

  @Override
  public boolean isWizardBranch_Petanque() {
    try {
      return AbstractTournamentWizardPanel.PETANQUE.equals((String)this.getWizardProperty(AbstractTournamentWizardPanel.BRANCH));
    } catch (Exception e) {
      // TODO Auto-generated catch block
      return false;
    }
  }

  @Override
  public boolean isWizardLastStep_Branch() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean isWizardLastStep_PetCat() {
    return xSettings.peek().getStatus().equals("pet-cat");
  }

  @Override
  public boolean isWizardLastStep_PetSupSel() {
    return xSettings.peek().getStatus().equals("pet-sup-sel");
  }

  @Override
  public boolean isPetCatTournamentCategory_Null() {
    return null == this.getWizardProperty(AbstractTournamentWizardPanel.MOVEMENT);
  }

  @Override
  public boolean isPetCatTournamentCategory_Supermelee() {
    try {
      return AbstractTournamentWizardPanel.PET_SUPERMELEE.equals((String)this.getWizardProperty(AbstractTournamentWizardPanel.MOVEMENT));
    } catch (Exception e) {
      return false;
    }
  }

  @Override
  public boolean isPetComMovTimeBoxed() {
    Object value = this.getWizardProperty(AbstractTournamentWizardPanel.TIMELIMIT_MATCH);
    return null==value?false:value.toString().matches("(true)|(\\d+)");
  }

  @Override
  public boolean isPetComMovDefineTracksByMovement() {
    // TODO Auto-generated method stub
    return null!=this.getWizardProperty(AbstractTournamentWizardPanel.SET_COURSES);
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
    this.actionCancel.setEnabled(true);
  }

  @Override
  public void doWizardControlStatusCancel_Disabled() {
    this.actionCancel.setEnabled(false);
  }

  @Override
  public void doWizardControlStatusBack_Enabled() {
    this.actionBack.setEnabled(true);
  }

  @Override
  public void doWizardControlStatusBack_Disabled() {
    this.actionBack.setEnabled(false);
  }

  @Override
  public void doWizardControlStatusNext_Enabled() {
    this.actionNext.setEnabled(true);
  }

  @Override
  public void doWizardControlStatusNext_Disabled() 
  {
    this.actionNext.setEnabled(false);
  }

  @Override
  public void doWizardControlStatusNext_To_Finish() {
    this.actionNext.putValue(Action.NAME, ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("Common.Finish.name"));
  }

  @Override
  public void doWizardControlSetPanel_Dispose() 
  {

    int _count = this.scrollPaneWizard.getViewport().getComponents().length;
    int _match = -1;
    
    for(int i=0; i<_count; ++i)
      if(this.scrollPaneWizard.getViewport().getComponent(i) == this.panelStack.peek())
        _match=i;
    
    if(-1 < _match)
      this.scrollPaneWizard.getViewport().remove(_match);
    
    this.actionBack.resetPressedFlag();
    this.actionCancel.resetPressedFlag();
    this.actionNext.resetPressedFlag();
  }

  @Override
  public void doWizardControlSetPanel_Branch() 
  {
    AbstractTournamentWizardPanel _panel = new WizardBranchPanel(this);
    
    this.actionCancel.resetPressedFlag();
    this.actionBack.resetPressedFlag();
    this.actionNext.resetPressedFlag();
    this.panelStack.add(_panel);
    this.scrollPaneWizard.setViewportView(_panel);
    this.repaint();
    _panel.setVisible(true);
    _panel.revalidate();
    this.scrollPaneWizard.getViewport().revalidate();
    this.scrollPaneWizard.revalidate();
    this.pack();
    this.revalidate();
  }

  @Override
  public void doWizardControlSetPanel_PetCat() {
    AbstractTournamentWizardPanel _panel = new WizardPetanqueCategoryPanel(this);

    this.actionCancel.resetPressedFlag();
    this.actionBack.resetPressedFlag();
    this.actionNext.resetPressedFlag();
    this.panelStack.add(_panel);
    this.scrollPaneWizard.setViewportView(_panel);
    this.repaint();
    _panel.setVisible(true);
    _panel.revalidate();
//    this.scrollPaneWizard.getViewport().revalidate();
    this.scrollPaneWizard.revalidate();
    this.pack();
    this.revalidate();
  }

  @Override
  public void doWizardControlSetPanel_PetSupSel() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void doWizardControlSetPanel_PetComMov() {
    AbstractTournamentWizardPanel _panel = new WizardPetanqueTournamentOptions(this);

    this.actionCancel.resetPressedFlag();
    this.actionBack.resetPressedFlag();
    this.actionNext.resetPressedFlag();
    this.panelStack.add(_panel);
    this.panelStack.add(_panel);
    this.scrollPaneWizard.setViewportView(_panel);
    this.repaint();
    _panel.setVisible(true);
    _panel.revalidate();
//    this.scrollPaneWizard.getViewport().revalidate();
    this.scrollPaneWizard.revalidate();
    this.pack();
    this.revalidate();
  }

  @Override
  public void doWizardControlRollBack_Dispose() 
  {
    int _count = this.scrollPaneWizard.getComponents().length;
    int _match = -1;
    
    for(int i=0; i<_count; ++i)
      if(this.scrollPaneWizard.getComponent(i) == this.panelStack.peek())
        _match=i;
    
    if(-1 < _match)
      this.scrollPaneWizard.remove(_match);
    //this.scrollPaneWizard.getComponent(0).setVisible(false);
    //this.scrollPaneWizard.removeAll();
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

  
  private abstract class AbstractWizardAction extends AbstractAction
  {
    protected boolean bPressed = false;

    public boolean isPressed() 
    {
      return bPressed;
    }
    
    public void resetPressedFlag()
    {
      this.bPressed = false;
    }

    public void actionPerformed(ActionEvent e) 
    {
      this.bPressed = true;
    }
  }

  private class WizardCancelAction extends AbstractWizardAction {
    
    public WizardCancelAction() 
    {
      putValue(NAME, ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("Common.Cancel.name"));
      // putValue(SHORT_DESCRIPTION, "Some short description");
      this.enabled = false;
    }
  }
  
  private class WizardBackAction extends AbstractWizardAction {
    
    public WizardBackAction() 
    {
      putValue(NAME, ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("Common.Back.name"));
      //putValue(SHORT_DESCRIPTION, "Some short description");
      this.enabled = false;
    }
  }
  
  private class WizardNextAction extends AbstractWizardAction {
    
    public WizardNextAction() 
    {
      putValue(NAME, ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("Common.Next.name"));
      // putValue(SHORT_DESCRIPTION, "Some short description");
      this.enabled = false;
    }
  }

  public AbstractTournamentPanel getTournamentEnvironment() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void doWizardControlPause() 
  {
    try {
      Thread.sleep(500);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
  }

  @Override
  public boolean isWizardControlWizardVisible() 
  {
    return this.isWizardControlEmptyPane()&&this.xThread.isAlive()?true:this.isVisible();
    //return true;
    //return this.isVisible();
  }

  @Override
  public void createRuleEngine() {
    // TODO Auto-generated method stub
    
  }

  @Override
  protected void execute() 
  {
    this.xEngine.execute((ITournamentWorker)this);
  }

  @Override
  public boolean isPetCatTournamentCategory_SupMonat() {
    try {
      return AbstractTournamentWizardPanel.PET_MONATSTURNIER.equals((String)this.getWizardProperty(AbstractTournamentWizardPanel.MOVEMENT));
    } catch (Exception e) {
      return false;
    }
  }

  @Override
  public boolean isPetComMovTimeLimitDefined_J() {
    Object value = this.getWizardProperty(AbstractTournamentWizardPanel.TIMELIMIT_MATCH);
    return null==value?false:value.toString().matches("\\d+");
  }
  
  


  
}
