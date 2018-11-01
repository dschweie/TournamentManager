package org.dos.tournament.application.common.panels;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import org.dos.tournament.application.common.controls.ToggleButton;
import org.dos.tournament.application.common.panels.components.SuperMeleeMatchdayTable;
import org.dos.tournament.application.common.utils.tablecelleditor.PetanqueTableCellEditor;
import org.dos.tournament.petanque.tournament.movement.SuperMelee;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import java.lang.Thread.State;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.Action;
import javax.swing.JProgressBar;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.ImageIcon;
import java.awt.Dimension;
import java.awt.ComponentOrientation;
import java.util.ResourceBundle;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
/**
 * \brief       Diese Klasse repr�sentiert die GUI f�r einen Spieltag
 * 
 * @author dirk.schweier
 */
public class DefaultMatchdayPanel extends JPanel
{
  private JTable tableMatches;
  private final Action printMatchday = new SwingAction();
  private final Action timer = new SwingActionMatchdayTimer();
  private JProgressBar progressBar;
  private final Action action = new SwingActionToggleTeamPresentation();
  private ToggleButton stateTeammates = new ToggleButton("P", "0", false);
  private ToggleButton stateOpponents = new ToggleButton("O", "0", false);
  private ToggleButton stateTriplette = new ToggleButton("T", "0", false);
  private JScrollPane scrollPane;
  private JButton btnPrintMatchday;
  

  /**
   * Create the panel.
   */
  public DefaultMatchdayPanel()
  {
    setLayout(new BorderLayout(0, 0));
    
    JToolBar toolBar = new JToolBar();
    add(toolBar, BorderLayout.SOUTH);
    
    btnPrintMatchday = toolBar.add(printMatchday);
    
    /*
    toolBar.add(this.stateTeammates);
    toolBar.add(this.stateOpponents);
    toolBar.add(this.stateTriplette);
    this.stateTeammates.setToolTipText("Regel: Keine Runde mit dem gleichen Partner.");
    this.stateOpponents.setToolTipText("Regel: Keine Runde gegen den gleichen Gegner.");
    this.stateTriplette.setToolTipText("Regel: Keine zwei Runden im Triplette.");
     */
    
    Component horizontalStrut = Box.createHorizontalStrut(10);
    horizontalStrut.setMinimumSize(new Dimension(10, 10));
    horizontalStrut.setMaximumSize(new Dimension(32000, 10));
    toolBar.add(horizontalStrut);
    
    JButton btnNewButton = new JButton("");
    btnNewButton.setAction(action);
    toolBar.add(btnNewButton);
    
    JButton btnTimer = toolBar.add(timer);
    
    progressBar = new JProgressBar();
    progressBar.setString("0:50");
    progressBar.setStringPainted(true);
    progressBar.setMinimumSize(new Dimension(64, 32));
    progressBar.setMaximumSize(new Dimension(64, 32));
    toolBar.add(progressBar);
    
    scrollPane = new JScrollPane();
    add(scrollPane, BorderLayout.CENTER);
  }

  public DefaultMatchdayPanel(DefaultTableModel model)
  {
    this();
  }
  
  public DefaultMatchdayPanel(SuperMelee tournament, int matchday)
  {
    this();
    
    this.tableMatches = new SuperMeleeMatchdayTable(tournament, matchday);
    
    
    
    this.btnPrintMatchday.setToolTipText("<html><p width=\"650\">" +tournament.getRegulationState().replaceAll("\n", "<br/>")+"</p></html>");
        
    scrollPane.setViewportView(tableMatches);
  }

  public void setStateRuleTeammates(boolean state)
  {
    this.stateTeammates.setSelected(state);
    this.stateTeammates.repaint();
  }
  
  public void setStateRuleOpponents(boolean state)
  {
    this.stateOpponents.setSelected(state);
    this.stateOpponents.repaint();
  }
  
  public void setStateRuleTriplette(boolean state)
  {
    this.stateTriplette.setSelected(state);
    this.stateTriplette.repaint();
  }
  
  
  
  /* (non-Javadoc)
   * @see javax.swing.JPanel#updateUI()
   */
  @Override
  public void updateUI()
  {
    super.updateUI();
    try
    {
        tableMatches.getColumnModel().getColumn(0).setPreferredWidth(  30 );
        tableMatches.getColumnModel().getColumn(1).setPreferredWidth( 200 );
        tableMatches.getColumnModel().getColumn(2).setPreferredWidth(  30 );
        tableMatches.getColumnModel().getColumn(3).setPreferredWidth( 10 );
        tableMatches.getColumnModel().getColumn(3).setPreferredWidth( 30 );
    }
    catch (Exception e) { /* currently nothing */ };
  }



  private class SwingAction extends AbstractAction {
    public SwingAction() {
      putValue(SMALL_ICON, new ImageIcon(DefaultMatchdayPanel.class.getResource("/org/dos/tournament/resources/icons/if_181-Printer_2124005.png")));
      putValue(NAME, "SwingAction");
      putValue(SHORT_DESCRIPTION, "prints the current data of this matchday");
    }
    public void actionPerformed(ActionEvent e) {
    }
  }
  private class SwingAction_1 extends AbstractAction {
    public SwingAction_1() {
      putValue(NAME, "SwingAction_1");
      putValue(SHORT_DESCRIPTION, "Some short description");
    }
    public void actionPerformed(ActionEvent e) {
    }
  }
  
  private class SwingActionMatchdayTimer extends AbstractAction implements Runnable {
    private Thread thread;
    private GregorianCalendar xStart;
    private GregorianCalendar xStop;
    private int iMillis = 50 * 60 * 1000;
    private boolean bRunning = false;
   
    public SwingActionMatchdayTimer() {
      putValue(SMALL_ICON, new ImageIcon(DefaultMatchdayPanel.class.getResource("/org/dos/tournament/resources/icons/if_18-Time_2123866.png")));
      putValue(NAME, ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("DefaultMatchdayPanel.timer.name")); //$NON-NLS-1$ //$NON-NLS-2$
      putValue(SHORT_DESCRIPTION, ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("DefaultMatchdayPanel.timer.short description")); //$NON-NLS-1$ //$NON-NLS-2$
      this.thread = new Thread(this);
    }
    
    public void actionPerformed(ActionEvent e) {
      
      if(! this.thread.isAlive())
      { //  Thread is not running and should be started
        this.bRunning = true;
        this.xStart = new GregorianCalendar();
        this.xStop = (GregorianCalendar) this.xStart.clone();
        this.xStop.add(GregorianCalendar.SECOND, (int)(Math.round(this.iMillis / 1000.0f))); 
        DefaultMatchdayPanel.this.progressBar.setMaximum(this.iMillis);
        DefaultMatchdayPanel.this.progressBar.setOrientation(JProgressBar.HORIZONTAL);
        DefaultMatchdayPanel.this.progressBar.setIndeterminate(false);
        //  this.setEnabled(false);
        this.thread.start();
        
        //  update the button
        this.putValue(SMALL_ICON, new ImageIcon(DefaultMatchdayPanel.class.getResource("/org/dos/tournament/resources/icons/if_180-Time_2123970.png")));
        this.putValue(NAME, ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("DefaultMatchdayPanel.timer.running.name")); //$NON-NLS-1$ //$NON-NLS-2$
        this.putValue(SHORT_DESCRIPTION, ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("DefaultMatchdayPanel.timer.running.short description")); //$NON-NLS-1$ //$NON-NLS-2$
      }
      else
      { //  stop and reset the timer
        this.putValue(SMALL_ICON, new ImageIcon(DefaultMatchdayPanel.class.getResource("/org/dos/tournament/resources/icons/if_18-Time_2123866.png")));
        this.putValue(NAME, ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("DefaultMatchdayPanel.timer.name")); //$NON-NLS-1$ //$NON-NLS-2$
        this.putValue(SHORT_DESCRIPTION, ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("DefaultMatchdayPanel.timer.short description")); //$NON-NLS-1$ //$NON-NLS-2$
        
        this.bRunning = false;
        //this.thread.interrupt();
      }
    }
    
    @Override
    public void run()
    {
      GregorianCalendar _now = new GregorianCalendar();
      
      while(_now.before(this.xStop) && this.bRunning)
      {
        try
        {
          long _diff = (_now.getTimeInMillis()-this.xStart.getTimeInMillis());
          DefaultMatchdayPanel.this.progressBar.setString(String.format("%tT", new GregorianCalendar(0, 0, 0, 0, 0, (int)Math.round((this.xStop.getTimeInMillis() -_now.getTimeInMillis())/1000.0f))));
          DefaultMatchdayPanel.this.progressBar.setValue(Integer.parseInt(String.valueOf(_diff)) );
          DefaultMatchdayPanel.this.progressBar.repaint();
          DefaultMatchdayPanel.this.progressBar.validate();
          Thread.sleep(200);
          _now = new GregorianCalendar();
        } catch (InterruptedException e)
        {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    
      if(!this.bRunning)
      { //  reset progressbar
        DefaultMatchdayPanel.this.progressBar.setString(String.format("%tT", new GregorianCalendar(0, 0, 0, 0, 0, (int)Math.round((this.iMillis)/1000.0f))));
        DefaultMatchdayPanel.this.progressBar.setValue( 0 );
        DefaultMatchdayPanel.this.progressBar.repaint();
        DefaultMatchdayPanel.this.progressBar.validate();
      }
      
      this.thread = new Thread(this);
 
      this.putValue(SMALL_ICON, new ImageIcon(DefaultMatchdayPanel.class.getResource("/org/dos/tournament/resources/icons/if_18-Time_2123866.png")));
      this.putValue(NAME, ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("DefaultMatchdayPanel.timer.name")); //$NON-NLS-1$ //$NON-NLS-2$
      this.putValue(SHORT_DESCRIPTION, ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("DefaultMatchdayPanel.timer.short description")); //$NON-NLS-1$ //$NON-NLS-2$
    }
    
    public final void join() throws InterruptedException
    {
      this.thread.join();
    }
    
    public void setDurationInMillis(int time)
    {
      this.iMillis = time;
    }
  }
  private class SwingAction_2 extends AbstractAction {
    public SwingAction_2() {
      putValue(NAME, "SwingAction_2");
      putValue(SHORT_DESCRIPTION, "Some short description");
    }
    public void actionPerformed(ActionEvent e) {
    }
  }
  private class SwingAction_3 extends AbstractAction {
    public SwingAction_3() {
      putValue(NAME, "SwingAction_3");
      putValue(SHORT_DESCRIPTION, "Some short description");
    }
    public void actionPerformed(ActionEvent e) {
    }
  }
  private class SwingActionToggleTeamPresentation extends AbstractAction {
    public SwingActionToggleTeamPresentation() {
      putValue(SMALL_ICON, new ImageIcon(DefaultMatchdayPanel.class.getResource("/org/dos/tournament/resources/icons/if_210-User_alpha.png")));
      putValue(NAME, "");
      putValue(SHORT_DESCRIPTION, "Some short description");
    }
    
    private void updateButton(boolean numeric)
    {
      if(numeric)
      {
        putValue(SMALL_ICON, new ImageIcon(DefaultMatchdayPanel.class.getResource("/org/dos/tournament/resources/icons/if_210-User_alpha.png")));
        putValue(NAME, "");
        putValue(SHORT_DESCRIPTION, ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("DefaultMatchdayPanel.footer-toolbar.description.switchToAlpha"));
      }
      else
      {
        putValue(SMALL_ICON, new ImageIcon(DefaultMatchdayPanel.class.getResource("/org/dos/tournament/resources/icons/if_210-User_numeric.png")));
        putValue(NAME, "");
        putValue(SHORT_DESCRIPTION, ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("DefaultMatchdayPanel.footer-toolbar.description.switchToNumeric"));
      }
    }
    
    public void actionPerformed(ActionEvent e) {
      this.updateButton(((SuperMeleeMatchdayTable) DefaultMatchdayPanel.this.tableMatches).toggleOutputParticipants());
    }
  }
}
