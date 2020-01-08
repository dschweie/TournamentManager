package org.dos.tournament.application.common.wizard;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.Window;
import java.util.HashMap;

import javax.swing.JDialog;
import javax.swing.JFrame;

import org.dos.tournament.application.common.wizard.common.AbstractWizardRuleEngine;
import org.dos.tournament.application.common.wizard.common.IWizardWorker;
import org.dos.tournament.application.factory.model.TournamentFactoryRulesEngine;

public abstract class AbstractRuleEngineWizard extends JDialog implements IWizardWorker
{

  private String strTrace = null;
  private String strTraceCount = null;
  
  protected AbstractWizardRuleEngine xEngine = null;
  
  protected RuleEngineThread xThread = new RuleEngineThread();
  private HashMap<String,Object> mapProperties = new HashMap<String,Object>();
  
  public AbstractRuleEngineWizard(JFrame owner) 
  {
    super(owner);
  }

  public AbstractRuleEngineWizard() {
    super();
  }

  public AbstractRuleEngineWizard(Dialog owner, boolean modal) {
    super(owner, modal);
  }

  public AbstractRuleEngineWizard(Dialog owner, String title, boolean modal, GraphicsConfiguration gc) {
    super(owner, title, modal, gc);
  }

  public AbstractRuleEngineWizard(Dialog owner, String title, boolean modal) {
    super(owner, title, modal);
  }

  public AbstractRuleEngineWizard(Dialog owner, String title) {
    super(owner, title);
  }

  public AbstractRuleEngineWizard(Dialog owner) {
    super(owner);
  }

  public AbstractRuleEngineWizard(Frame owner, boolean modal) {
    super(owner, modal);
  }

  public AbstractRuleEngineWizard(Frame owner, String title, boolean modal, GraphicsConfiguration gc) {
    super(owner, title, modal, gc);
  }

  public AbstractRuleEngineWizard(Frame owner, String title, boolean modal) {
    super(owner, title, modal);
  }

  public AbstractRuleEngineWizard(Frame owner, String title) {
    super(owner, title);
  }

  public AbstractRuleEngineWizard(Frame owner) {
    super(owner);
  }

  public AbstractRuleEngineWizard(Window owner, ModalityType modalityType) {
    super(owner, modalityType);
  }

  public AbstractRuleEngineWizard(Window owner, String title, ModalityType modalityType, GraphicsConfiguration gc) {
    super(owner, title, modalityType, gc);
  }

  public AbstractRuleEngineWizard(Window owner, String title, ModalityType modalityType) {
    super(owner, title, modalityType);
  }

  public AbstractRuleEngineWizard(Window owner, String title) {
    super(owner, title);
  }

  public AbstractRuleEngineWizard(Window owner) {
    super(owner);
  }
  
  protected abstract void execute();

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
  public void setVisible(boolean b) 
  {
    if(b)
    {
      if(!this.xThread.isAlive())
      {
        this.xThread.setDaemon(true);
        //_thread.run();
        xThread.start();
        super.setVisible(true);
      }
    }
    else
    {
      //if(this._thread.isAlive())
      //  this._thread.stop();
      super.setVisible(b);
    }

  }
  
  public void setWizardProperty(String key, Object value)
  {
    this.mapProperties.put(key, value);
  }
  
  public Object getWizardProperty(String key)
  {
    return this.mapProperties.containsKey(key)?this.mapProperties.get(key):null;
  }
  
  public void revertWizardProperty(String key)
  {
    if(this.mapProperties.containsKey(key))
    {
      this.mapProperties.replace(key, null);
    }
  }
  
  public HashMap<String,Object> getWizardProperties()
  {
    return ( HashMap<String,Object>) this.mapProperties.clone();
  }
  
  protected class RuleEngineThread extends Thread
  {
//    @Override
//    public synchronized void start() 
//    {
//      super.start();
//      this.run();
//    }

    @Override
    public void run() {
      //if(!this.isAlive())
      //  this.start();
      
//      while(!AbstractRuleEngineWizard.this.isVisible())
//      {
//        try {
//          Thread.sleep(500);
//        } catch (InterruptedException e) {
//          // TODO Auto-generated catch block
//          e.printStackTrace();
//        }
//      }
      
      //AbstractRuleEngineWizard.super.setVisible(true);
      AbstractRuleEngineWizard.this.setModal(true);
      AbstractRuleEngineWizard.this.execute();
      AbstractRuleEngineWizard.this.setVisible(false);
      //AbstractRuleEngineWizard.this.xEngine.execute(AbstractRuleEngineWizard.this);
    }  
    
  }
  
  

}
