package org.dos.tournament.application.branch.petanque.panels.tablemodels;

import java.util.Observable;
import java.util.Observer;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import org.dos.tournament.branch.petanque.tournament.movement.SuperMelee;
import org.dos.tournament.common.player.IParticipant;

public class ParticipantsTableModel extends DefaultTableModel implements Observer
{

  public ParticipantsTableModel()
  {
    super(null, new String[] {"Nummer", "Vorname", "Nachname", "Verein", "Status"});
//    super(new Object[][] {{null, null, null, null}}, new String[] {"Nummer", "Name", "Verein", "Status"});
  }

  private ArrayList vecHeader = null;

  @SuppressWarnings("unchecked")
  @Override
  public void update(Observable o, Object arg)
  {
    ArrayList<IParticipant> _participants = ((SuperMelee)o).getCompetitors();
    ArrayList<ArrayList<Object>> _field = new ArrayList<>();

    if(null == this.vecHeader)
      this.vecHeader = (ArrayList) ParticipantsTableModel.compileDefaultHeader();

    for(int i=0; i < _participants.size(); ++i)
      _field.add((ArrayList<Object>) _participants.get(i).getParticipantAsRow(this.vecHeader));

    Object[][] _fieldMatrix = new Object[_field.size()][_field.get(0).size()];
    for(int i=0; i<_field.size(); ++i)
      _fieldMatrix[i] = _field.get(i).toArray(_fieldMatrix[i]);
    this.setDataVector(_fieldMatrix, this.columnIdentifiers.toArray());

    this.fireTableDataChanged();
  }



  /* (non-Javadoc)
   * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
   */
  @Override
  public Class<?> getColumnClass(int columnIndex)
  {
    switch( columnIndex )
    {
        case 0:   return Integer.class;
        default:  return String.class;
    }
  }



  static private List<String> compileDefaultHeader()
  {
    ArrayList<String> _header=new ArrayList<String>();
    _header.add("id");
    _header.add("name.forename");
    _header.add("name.surname");
    _header.add("club");
    _header.add("status");
    return _header;
  }

  /* (non-Javadoc)
   * @see javax.swing.table.DefaultTableModel#isCellEditable(int, int)
   */
  @Override
  public boolean isCellEditable(int row, int column)
  {
    // the table is read only.
    return false;
  }


}
