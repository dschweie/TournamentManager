package org.dos.tournament.application.common.panels;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import org.dos.tournament.application.common.controls.ToggleButton;
import org.dos.tournament.application.common.panels.components.SuperMeleeMatchdayTable;
import org.dos.tournament.application.common.utils.tablecelleditor.PetanqueTableCellEditor;
import org.dos.tournament.branch.petanque.tournament.movement.SuperMelee;

import javax.swing.JTable;
import javax.swing.JTable.PrintMode;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import java.lang.Thread.State;
import java.net.URL;
import java.text.MessageFormat;
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
/**
 * \brief       Diese Klasse repräsentiert die GUI für einen Spieltag
 *
 * @author dirk.schweier
 */
public class DefaultMatchdayPanel extends JPanel
{
  private JTable tableMatches;
  private final Action printMatchday = new SwingActionPrintMatchday();
  private final SwingActionMatchdayTimer actionTimer = new SwingActionMatchdayTimer();
  private JProgressBar progressBar;
  private final Action actionToggleTeamPresentation = new SwingActionToggleTeamPresentation();
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

    Component horizontalStrut = Box.createHorizontalStrut(10);
    horizontalStrut.setMinimumSize(new Dimension(10, 10));
    horizontalStrut.setMaximumSize(new Dimension(32000, 10));
    toolBar.add(horizontalStrut);

    JButton btnNewButton = new JButton("");
    btnNewButton.setAction(actionToggleTeamPresentation);
    toolBar.add(btnNewButton);

    JButton btnTimer = toolBar.add(actionTimer);

    progressBar = new JProgressBar();
    progressBar.setStringPainted(true);
    progressBar.setMinimumSize(new Dimension(64, 32));
    progressBar.setMaximumSize(new Dimension(64, 32));
    progressBar.addMouseListener(new ListenerTimerModification());
    toolBar.add(progressBar);
    this.actionTimer.updateProgressbar();

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
    this.tableMatches.getTableHeader().setReorderingAllowed( false );


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



  private class SwingActionPrintMatchday extends AbstractAction {
    public SwingActionPrintMatchday() {
      putValue(SMALL_ICON, new ImageIcon(DefaultMatchdayPanel.class.getResource("/org/dos/tournament/resources/icons/if_181-Printer_2124005.png")));
      putValue(NAME, "SwingAction");
      putValue(SHORT_DESCRIPTION, "prints the current data of this matchday");
    }
    public void actionPerformed(ActionEvent e)
    {
      PrinterJob _printJob = PrinterJob.getPrinterJob();
      PageFormat _page = new PageFormat();

      _page.setOrientation(PageFormat.LANDSCAPE);

      _printJob.setPrintable(DefaultMatchdayPanel.this.getPrintable(), _page);

      if(_printJob.printDialog())
      {
        try
        {
          _printJob.print();
        }
        catch (PrinterException e1)
        {
          e1.printStackTrace();
        }
      }
    }
  }
  private class SwingAction_1 extends AbstractAction {
    public SwingAction_1() {
      putValue(NAME, "SwingAction_1");
      putValue(SHORT_DESCRIPTION, "Some short description");
    }

    public void actionPerformed(ActionEvent e)
    {
    }
  }

  private class SwingActionMatchdayTimer extends AbstractAction implements Runnable {
    private Thread thread;
    private GregorianCalendar xStart;
    private GregorianCalendar xStop;
    private int iMillis = 50 * 60 * 1000;
    private boolean bRunning = false;
    private Clip clip;


    public SwingActionMatchdayTimer() {
      putValue(SMALL_ICON, new ImageIcon(DefaultMatchdayPanel.class.getResource("/org/dos/tournament/resources/icons/if_18-Time_2123866.png")));
      putValue(NAME, ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("DefaultMatchdayPanel.timer.name")); //$NON-NLS-1$ //$NON-NLS-2$
      putValue(SHORT_DESCRIPTION, ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("DefaultMatchdayPanel.timer.short description")); //$NON-NLS-1$ //$NON-NLS-2$
      this.thread = new Thread(this);
      this.updateProgressbar();

      try {
        URL url = DefaultMatchdayPanel.class.getResource("/org/dos/tournament/resources/sounds/AirHorn.wav");
        System.out.println(url.toString());
        AudioInputStream audioIn;
        audioIn = AudioSystem.getAudioInputStream(url);
        // Get a sound clip resource.
        this.clip = AudioSystem.getClip();
        // Open audio clip and load samples from the audio input stream.
        clip.open(audioIn);
      } catch (LineUnavailableException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      } catch (UnsupportedAudioFileException e) {
        e.printStackTrace();
      }
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

      //  Abspielen der Sounddatei
      // Open an audio input stream.
      this.clip.loop(3);
      this.clip.start();
      /*
      try {
        for(int i=0; i<3; ++i)
        {

          clip.start();
          try {
            Thread.sleep(500);
          } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
        }
      } catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
      }
      */
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
      if(!this.thread.isAlive())
        this.updateProgressbar();
    }

    public void increaseDurationInMinutes(int minutes)
    {
      if(!this.thread.isAlive())
      {
        this.iMillis += minutes * 60 * 1000;
        this.iMillis = Math.max(0, iMillis);
        this.updateProgressbar();
      }
    }

    public void updateProgressbar()
    {
      if(null != DefaultMatchdayPanel.this.progressBar)
      {
        DefaultMatchdayPanel.this.progressBar.setString(String.format("%tT", new GregorianCalendar(0, 0, 0, 0, 0, (int)Math.round((this.iMillis)/1000.0f))));
        DefaultMatchdayPanel.this.progressBar.setValue( 0 );
        DefaultMatchdayPanel.this.progressBar.repaint();
        DefaultMatchdayPanel.this.progressBar.validate();
        DefaultMatchdayPanel.this.actionTimer.setEnabled(0 < this.iMillis);
      }
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

  private class ListenerTimerModification extends MouseAdapter
  {
    public void mousePressed(MouseEvent e)
    {
      int _size = e.getComponent().getWidth();
      if (!e.isPopupTrigger())
      {
        if (e.getX() < ( ( _size / 7 ) * 3 ) )
        { // decrease time
          if(1 == e.getClickCount())
            DefaultMatchdayPanel.this.actionTimer.increaseDurationInMinutes(-1);
          else if (1 < e.getClickCount())
            DefaultMatchdayPanel.this.actionTimer.increaseDurationInMinutes(-4);
        }
        if ( e.getX() > ( ( _size / 7 )  * 4 ) )
        { // decrease time
          if( 1 == e.getClickCount() )
            DefaultMatchdayPanel.this.actionTimer.increaseDurationInMinutes(1);
          else if ( 2 == e.getClickCount() )
            DefaultMatchdayPanel.this.actionTimer.increaseDurationInMinutes(4);
        }

      }
    }

    public void mouseReleased(MouseEvent e)
    {
        if (e.isPopupTrigger())
            doPop(e);
    }

    private void doPop(MouseEvent e){
        //PopUpDemo menu = new PopUpDemo();
        //menu.show(e.getComponent(), e.getX(), e.getY());
    }
  }

  public Printable getPrintable()
  {
    return this.tableMatches.getPrintable(PrintMode.FIT_WIDTH, new MessageFormat(String.format("Runde %d", ((SuperMeleeMatchdayTable) this.tableMatches).getMatchdayIndex()+1)), new MessageFormat(String.format("Tournament Manager %1$s               %2$td.%2$tm.%2$tY               Seite {0}", ResourceBundle.getBundle("org.dos.tournament.resources.config").getString("TournamentManager.version"), GregorianCalendar.getInstance())));
  }

}
