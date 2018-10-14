package org.dos.tournament.application.common.panels;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableModel;

import org.dos.tournament.application.common.utils.tablecelleditor.PetanqueTableCellEditor;

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

public class DefaultMatchdayPanel extends JPanel
{
  private JTable tableMatches;
  private final Action printMatchday = new SwingAction();
  private final Action timer = new SwingActionMatchdayTimer();
  private JProgressBar progressBar;

  /**
   * Create the panel.
   */
  public DefaultMatchdayPanel()
  {
    setLayout(new BorderLayout(0, 0));
    
    JToolBar toolBar = new JToolBar();
    add(toolBar, BorderLayout.SOUTH);
    
    JButton btnPrintMatchday = toolBar.add(printMatchday);
    
    Component horizontalStrut = Box.createHorizontalStrut(10);
    horizontalStrut.setMinimumSize(new Dimension(10, 10));
    horizontalStrut.setMaximumSize(new Dimension(32000, 10));
    toolBar.add(horizontalStrut);
    
    JButton btnTimer = toolBar.add(timer);
    
    progressBar = new JProgressBar();
    progressBar.setString("0:50");
    progressBar.setStringPainted(true);
    progressBar.setMinimumSize(new Dimension(64, 32));
    progressBar.setMaximumSize(new Dimension(64, 32));
    toolBar.add(progressBar);
    
    JScrollPane scrollPane = new JScrollPane();
    add(scrollPane, BorderLayout.CENTER);
    
    tableMatches = new JTable();
    tableMatches.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        if('\t' == e.getKeyChar())
        {
          int _iCurrentColumn = DefaultMatchdayPanel.this.tableMatches.getSelectedColumn();
          int _iCurrentRow    = DefaultMatchdayPanel.this.tableMatches.getSelectedRow();
          if(3 == _iCurrentColumn)
            DefaultMatchdayPanel.this.tableMatches.changeSelection(_iCurrentRow, 4, false, false);
          else
            DefaultMatchdayPanel.this.tableMatches.changeSelection(_iCurrentRow+1, 2, false, false);
        }
      }
    });
    tableMatches.setDefaultEditor(Object.class, new PetanqueTableCellEditor());
    
    scrollPane.setViewportView(tableMatches);

  }

  public DefaultMatchdayPanel(DefaultTableModel model)
  {
    this();
    this.tableMatches.setModel(model);
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
}
