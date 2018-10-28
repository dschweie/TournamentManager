package org.dos.tournament.application.common.dialogs;

import java.awt.Component;

import javax.swing.ProgressMonitor;

public class MatchdayProgressMonitor extends ProgressMonitor
{

  public MatchdayProgressMonitor(Component parentComponent, Object message, String note, int min, int max)
  {
    super(parentComponent, message, note, min, max);
    this.setMillisToDecideToPopup(100);

  }

}
