package org.dos.tournament.application.common.wizard.createtournament;

public class WizardAction 
{
  private String strStatus;
  private Object xAction;
  
  public WizardAction(String status, Object action)
  {
    this.strStatus = status;
    this.xAction = action;
  }
  
  public String getStatus()
  {
    return this.strStatus;
  }
  
  public Object getAction()
  {
    return this.xAction;
  }
}
