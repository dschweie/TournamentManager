package org.dos.tournament.application.common.utils.tablecelleditor;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableCellEditor;

public class PetanqueTableCellEditor extends JTextField implements TableCellEditor, DocumentListener
{
  private List<CellEditorListener> listeners = new ArrayList<CellEditorListener>();
  
  public PetanqueTableCellEditor()
  {
    // Der Editor hört sich selbst ab, so kann er auf jede Benutzereingabe reagieren
    getDocument().addDocumentListener( this );
  }
  
  @Override
  public Object getCellEditorValue() 
  {
    // Gibt den aktuellen Wert des Editors zurück
    return getText();
  }

  @Override
  public boolean isCellEditable( EventObject anEvent ) 
  {
    // Im Falle eines MouseEvents, muss ein Doppelklick erfolgen, um den Editor zu aktivieren.
    // Ansonsten wird der Editor auf jeden Fall aktiviert
    if( anEvent instanceof MouseEvent )
        return ((MouseEvent)anEvent).getClickCount() > 1;
        
    return true;
  }

  @Override
  public boolean shouldSelectCell( EventObject anEvent ) 
  {
    return true;
  }

  @Override
  public boolean stopCellEditing()
  {
    // Sollte die Eingabe falsch sein, darf das editieren nich gestoppt werden
    if( !isValidText() )
        return false;
    
    // Ansonsten werden die Listener vom stop unterrichtet
    ChangeEvent event = new ChangeEvent( this );
    for( CellEditorListener listener : listeners.toArray( new CellEditorListener[ listeners.size() ] ))
        listener.editingStopped( event );
    
    return true;
  }

  @Override
  public void cancelCellEditing() 
  {
    // Falls abgebrochen wird, werden alle Listeners informiert
    ChangeEvent event = new ChangeEvent( this );
    for( CellEditorListener listener : listeners.toArray( new CellEditorListener[ listeners.size() ] ))
        listener.editingCanceled( event );
  }

  @Override
  public void addCellEditorListener( CellEditorListener l ) 
  {
      listeners.add( l );
  }

  @Override
  public void removeCellEditorListener(CellEditorListener l)
  {
    listeners.remove( l );
  }

  @Override
  public void insertUpdate(DocumentEvent e)
  {
    update();
  }

  @Override
  public void removeUpdate(DocumentEvent e)
  {
    update();
  }

  @Override
  public void changedUpdate(DocumentEvent e)
  {
    update();
  }

  @Override
  public Component getTableCellEditorComponent( JTable table, Object value, boolean isSelected, int row, int column ) 
  {
    // Diese Methode wird von der JTable aufgerufen, wenn der Editor angezeigt werden soll
    setText( value.toString() );
    return this;
  }

  private boolean isValidText()
  {
    try
    {
      int value = Integer.parseInt(this.getText());
      return ((-1 < value) && (14 > value));
    }
    catch (Exception e)
    {
      return false;//return getText().matches( "[1-9]+" );
    }
  }

  public void update(){
    // Verändert die Umrandung des Editors, jenachdem, ob eine gültige
    // oder eine ungültige Eingabe gemacht wurde
    Color color;
    if( isValidText() )
        color = Color.GREEN;
    else
        color = Color.RED;
    
    setBorder( BorderFactory.createLineBorder( color ));
  }
}
