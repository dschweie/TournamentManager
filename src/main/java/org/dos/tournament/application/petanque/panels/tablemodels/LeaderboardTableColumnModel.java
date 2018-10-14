package org.dos.tournament.application.petanque.panels.tablemodels;

import java.util.Enumeration;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ListSelectionModel;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class LeaderboardTableColumnModel extends DefaultTableColumnModel implements Observer
{

  @Override
  public void update(Observable o, Object arg)
  {
    for(int i=0; i < this.getColumnCount(); ++i)
      this.getColumn(i).setPreferredWidth(i==1?80:20);
    this.fireColumnMarginChanged();
  }

}
