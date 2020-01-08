package org.dos.tournament.application.common.wizard.createtournament;

import java.util.HashMap;

import javax.swing.JPanel;

import org.dos.tournament.application.common.wizard.AbstractRuleEngineWizard;

public abstract class AbstractTournamentWizardPanel extends JPanel 
{
 
  public static final String BRANCH             = "branch";
  public static final String PETANQUE           = "petanque";
  public static final String BOARDGAME          = "boardgame";
  
  
  public static final String MOVEMENT           = "movement";
  public static final String PET_SUPERMELEE     = "supermelee";
  public static final String PET_MONATSTURNIER  = "monatsturnier_gg";
  public static final String PET_DOUBLETTESWISS = "doublette_formee_swiss";
  
  public static final String TIMELIMIT_MATCH    = "timelimit_match";
  public static final String SET_COURSES        = "set_cources";

  
  protected AbstractRuleEngineWizard xWizard = null;

  
  private HashMap<String,String> properties = new HashMap<String, String>();
  
  /**
   *   \brief       Methode führt Roll-Back durch und löscht das Panel 
   */
  public void dispose(CreateTournamentWizard instance)
  {
    
  }
  
}
