package org.dos.tournament.application.common.wizard.createtournament.petanque;

import org.dos.tournament.application.common.wizard.createtournament.AbstractTournamentWizardPanel;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class WizardPetanqueTournamentOptions extends AbstractTournamentWizardPanel 
{
  private JTextField textField;
  public WizardPetanqueTournamentOptions() {
    GridBagLayout gridBagLayout = new GridBagLayout();
    gridBagLayout.columnWidths = new int[]{0, 0};
    gridBagLayout.rowHeights = new int[]{0, 0, 0};
    gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
    gridBagLayout.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
    setLayout(gridBagLayout);
    
    JPanel panelTimeLimit = new JPanel();
    panelTimeLimit.setBorder(new TitledBorder(null, "Zeitbegrenzung", TitledBorder.LEADING, TitledBorder.TOP, null, null));
    GridBagConstraints gbc_panelTimeLimit = new GridBagConstraints();
    gbc_panelTimeLimit.insets = new Insets(0, 0, 5, 0);
    gbc_panelTimeLimit.fill = GridBagConstraints.BOTH;
    gbc_panelTimeLimit.gridx = 0;
    gbc_panelTimeLimit.gridy = 0;
    add(panelTimeLimit, gbc_panelTimeLimit);
    panelTimeLimit.setLayout(new FormLayout(new ColumnSpec[] {
        FormSpecs.RELATED_GAP_COLSPEC,
        FormSpecs.DEFAULT_COLSPEC,
        FormSpecs.RELATED_GAP_COLSPEC,
        ColumnSpec.decode("default:grow"),
        FormSpecs.RELATED_GAP_COLSPEC,
        FormSpecs.DEFAULT_COLSPEC,},
      new RowSpec[] {
        FormSpecs.RELATED_GAP_ROWSPEC,
        FormSpecs.DEFAULT_ROWSPEC,
        FormSpecs.RELATED_GAP_ROWSPEC,
        FormSpecs.DEFAULT_ROWSPEC,
        FormSpecs.RELATED_GAP_ROWSPEC,
        FormSpecs.DEFAULT_ROWSPEC,}));
    
    JLabel lblFrage = new JLabel("Frage");
    panelTimeLimit.add(lblFrage, "2, 2");
    
    JRadioButton rdbtnNoTimelimit = new JRadioButton("nein");
    panelTimeLimit.add(rdbtnNoTimelimit, "2, 4");
    
    JRadioButton rdbtnSetTimelimit = new JRadioButton("New radio button");
    panelTimeLimit.add(rdbtnSetTimelimit, "2, 6");
    
    textField = new JTextField();
    panelTimeLimit.add(textField, "4, 6, fill, default");
    textField.setColumns(10);
    
    JLabel lblMinuten = new JLabel("Minuten");
    panelTimeLimit.add(lblMinuten, "6, 6");
    
    JPanel panel_1 = new JPanel();
    panel_1.setBorder(new TitledBorder(null, "Auslosung der Pl\u00E4tze", TitledBorder.LEADING, TitledBorder.TOP, null, null));
    GridBagConstraints gbc_panel_1 = new GridBagConstraints();
    gbc_panel_1.fill = GridBagConstraints.BOTH;
    gbc_panel_1.gridx = 0;
    gbc_panel_1.gridy = 1;
    add(panel_1, gbc_panel_1);
    panel_1.setLayout(new FormLayout(new ColumnSpec[] {
        FormSpecs.RELATED_GAP_COLSPEC,
        FormSpecs.DEFAULT_COLSPEC,},
      new RowSpec[] {
        FormSpecs.RELATED_GAP_ROWSPEC,
        FormSpecs.DEFAULT_ROWSPEC,
        FormSpecs.RELATED_GAP_ROWSPEC,
        FormSpecs.DEFAULT_ROWSPEC,
        FormSpecs.RELATED_GAP_ROWSPEC,
        FormSpecs.DEFAULT_ROWSPEC,}));
    
    JLabel lblFrage_1 = new JLabel("Frage");
    panel_1.add(lblFrage_1, "2, 2");
    
    JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("New radio button");
    panel_1.add(rdbtnNewRadioButton_1, "2, 4");
    
    JRadioButton rdbtnNewRadioButton_2 = new JRadioButton("New radio button");
    panel_1.add(rdbtnNewRadioButton_2, "2, 6");
  }

}
