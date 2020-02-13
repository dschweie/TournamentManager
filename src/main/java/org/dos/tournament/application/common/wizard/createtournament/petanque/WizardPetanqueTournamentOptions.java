package org.dos.tournament.application.common.wizard.createtournament.petanque;

import org.dos.tournament.application.TournamentManagerUI;
import org.dos.tournament.application.common.wizard.createtournament.AbstractTournamentWizardPanel;
import org.dos.tournament.application.common.wizard.createtournament.CreateTournamentWizard;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.util.ResourceBundle;

import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.ButtonGroup;

public class WizardPetanqueTournamentOptions extends AbstractTournamentWizardPanel
{
  private JTextField textField;
  private final ButtonGroup buttonGroupTimelimt = new ButtonGroup();
  private final ButtonGroup buttonGroupCourts = new ButtonGroup();
  private JRadioButton rdbtnNoTimelimit;
  private JRadioButton rdbtnNoDrawOfThePlaces;
  public WizardPetanqueTournamentOptions() {
    GridBagLayout gridBagLayout = new GridBagLayout();
    gridBagLayout.columnWidths = new int[]{0, 0};
    gridBagLayout.rowHeights = new int[]{0, 0, 0};
    gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
    gridBagLayout.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
    setLayout(gridBagLayout);

    JPanel panelTimeLimit = new JPanel();
    panelTimeLimit.setBorder(new TitledBorder(null, ResourceBundle.getBundle(TournamentManagerUI.MESSAGES).getString("Common.TimeLimit.name"), TitledBorder.LEADING, TitledBorder.TOP, null, null));
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

    JLabel lblTimelimitQuestion = new JLabel(ResourceBundle.getBundle(TournamentManagerUI.MESSAGES).getString("Common.TimeLimit.question"));
    panelTimeLimit.add(lblTimelimitQuestion, "2, 2");

    rdbtnNoTimelimit = new JRadioButton(ResourceBundle.getBundle(TournamentManagerUI.MESSAGES).getString("Common.No.name"));
    buttonGroupTimelimt.add(rdbtnNoTimelimit);
    panelTimeLimit.add(rdbtnNoTimelimit, "2, 4");


    JRadioButton rdbtnSetTimelimit = new JRadioButton(ResourceBundle.getBundle(TournamentManagerUI.MESSAGES).getString("Common.Yes.name"));
    buttonGroupTimelimt.add(rdbtnSetTimelimit);
    panelTimeLimit.add(rdbtnSetTimelimit, "2, 6");

    textField = new JTextField();
    panelTimeLimit.add(textField, "4, 6, fill, default");
    textField.setColumns(10);

    JLabel lblMinuten = new JLabel(ResourceBundle.getBundle(TournamentManagerUI.MESSAGES).getString("Common.Minutes.name"));
    panelTimeLimit.add(lblMinuten, "6, 6");

    JPanel panelDrawOfThePlaces = new JPanel();
    panelDrawOfThePlaces.setBorder(new TitledBorder(null, ResourceBundle.getBundle(TournamentManagerUI.MESSAGES).getString("Common.DrawOfThePlaces.name"), TitledBorder.LEADING, TitledBorder.TOP, null, null));
    GridBagConstraints gbc_panelDrawOfThePlaces = new GridBagConstraints();
    gbc_panelDrawOfThePlaces.fill = GridBagConstraints.BOTH;
    gbc_panelDrawOfThePlaces.gridx = 0;
    gbc_panelDrawOfThePlaces.gridy = 1;
    add(panelDrawOfThePlaces, gbc_panelDrawOfThePlaces);
    panelDrawOfThePlaces.setLayout(new FormLayout(new ColumnSpec[] {
        FormSpecs.RELATED_GAP_COLSPEC,
        FormSpecs.DEFAULT_COLSPEC,},
      new RowSpec[] {
        FormSpecs.RELATED_GAP_ROWSPEC,
        FormSpecs.DEFAULT_ROWSPEC,
        FormSpecs.RELATED_GAP_ROWSPEC,
        FormSpecs.DEFAULT_ROWSPEC,
        FormSpecs.RELATED_GAP_ROWSPEC,
        FormSpecs.DEFAULT_ROWSPEC,}));

    JLabel lblDrawOfThePlacesQuestion = new JLabel(ResourceBundle.getBundle(TournamentManagerUI.MESSAGES).getString("Common.DrawOfThePlaces.question"));
    panelDrawOfThePlaces.add(lblDrawOfThePlacesQuestion, "2, 2");

    rdbtnNoDrawOfThePlaces = new JRadioButton(ResourceBundle.getBundle(TournamentManagerUI.MESSAGES).getString("Common.No.name"));
    buttonGroupCourts.add(rdbtnNoDrawOfThePlaces);
    panelDrawOfThePlaces.add(rdbtnNoDrawOfThePlaces, "2, 4");

    JRadioButton rdbtnSetDrawOfThePlaces = new JRadioButton(ResourceBundle.getBundle(TournamentManagerUI.MESSAGES).getString("Common.Yes.name"));
    buttonGroupCourts.add(rdbtnSetDrawOfThePlaces);
    panelDrawOfThePlaces.add(rdbtnSetDrawOfThePlaces, "2, 6");

    this.revalidate();
    this.setVisible(true);
  }

  public WizardPetanqueTournamentOptions(CreateTournamentWizard wizard)
  {
    this();
    this.xWizard = wizard;

    this.rdbtnNoTimelimit.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {
        WizardPetanqueTournamentOptions.this.xWizard.setWizardProperty(AbstractTournamentWizardPanel.TIMELIMIT_MATCH, WizardPetanqueTournamentOptions.this.rdbtnNoTimelimit.isSelected()?"false":"true");
      }
    });
    this.rdbtnNoTimelimit.setSelected(true);

    this.rdbtnNoDrawOfThePlaces.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {
        WizardPetanqueTournamentOptions.this.xWizard.setWizardProperty(AbstractTournamentWizardPanel.SET_COURSES, WizardPetanqueTournamentOptions.this.rdbtnNoDrawOfThePlaces.isSelected()?"false":"true");
      }
    });
    this.rdbtnNoDrawOfThePlaces.setSelected(true);

    this.textField.getDocument().addDocumentListener(new DocumentListener() {
      public void changedUpdate(DocumentEvent e) {
        setTimelimit();
      }
      public void removeUpdate(DocumentEvent e) {
        setTimelimit();
      }
      public void insertUpdate(DocumentEvent e) {
        setTimelimit();
      }

      public void setTimelimit()
      {
        WizardPetanqueTournamentOptions.this.xWizard.setWizardProperty(AbstractTournamentWizardPanel.TIMELIMIT_MATCH, WizardPetanqueTournamentOptions.this.textField.getText().trim());
      }
    });

  }

}
