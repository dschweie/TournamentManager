package org.dos.tournament.application.common.wizard.createtournament.petanque;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JRadioButton;
import java.awt.GridBagLayout;
import javax.swing.JPanel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.border.TitledBorder;

import org.dos.tournament.application.common.wizard.AbstractRuleEngineWizard;
import org.dos.tournament.application.common.wizard.createtournament.AbstractTournamentWizardPanel;
import org.dos.tournament.application.common.wizard.createtournament.Messages;

import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Dimension;
import javax.swing.ButtonGroup;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import java.awt.Component;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.awt.Rectangle;
import java.awt.ComponentOrientation;
import java.awt.Cursor;

public class WizardPetanqueCategoryPanel extends AbstractTournamentWizardPanel {
  private final ButtonGroup buttonGroup = new ButtonGroup();
  private JRadioButton rdbtnSupermelee;
  private JRadioButton rdbtnMonthlyClubTournament;
  private JRadioButton rdbtnACBDTournament;
  private JTextArea textInformation;
  private JScrollPane scrollPaneInformation;
  public WizardPetanqueCategoryPanel() {
    setSize(new Dimension(600, 300));
    setLayout(new BorderLayout(0, 0));
    
    JPanel panelOptions = new JPanel();
    panelOptions.setSize(new Dimension(300, 400));
    add(panelOptions, BorderLayout.CENTER);
    GridBagLayout gbl_panelOptions = new GridBagLayout();
    gbl_panelOptions.columnWidths = new int[]{0, 0};
    gbl_panelOptions.rowHeights = new int[]{0, 0, 0};
    gbl_panelOptions.columnWeights = new double[]{1.0, Double.MIN_VALUE};
    gbl_panelOptions.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
    panelOptions.setLayout(gbl_panelOptions);
    
    JPanel panelSupermeleeOptions = new JPanel();
    panelSupermeleeOptions.setBorder(new TitledBorder(null, "Superm\u00EAl\u00E9e", TitledBorder.LEADING, TitledBorder.TOP, null, null));
    GridBagConstraints gbc_panelSupermeleeOptions = new GridBagConstraints();
    gbc_panelSupermeleeOptions.insets = new Insets(0, 0, 5, 0);
    gbc_panelSupermeleeOptions.fill = GridBagConstraints.BOTH;
    gbc_panelSupermeleeOptions.gridx = 0;
    gbc_panelSupermeleeOptions.gridy = 0;
    panelOptions.add(panelSupermeleeOptions, gbc_panelSupermeleeOptions);
    panelSupermeleeOptions.setLayout(new FormLayout(new ColumnSpec[] {
        FormSpecs.RELATED_GAP_COLSPEC,
        FormSpecs.DEFAULT_COLSPEC,},
      new RowSpec[] {
        FormSpecs.RELATED_GAP_ROWSPEC,
        FormSpecs.DEFAULT_ROWSPEC,
        FormSpecs.RELATED_GAP_ROWSPEC,
        FormSpecs.DEFAULT_ROWSPEC,}));
    
    rdbtnSupermelee = new JRadioButton(Messages.getString("Glossary.Tournament.Petanque.Supermelee")); //$NON-NLS-1$
    buttonGroup.add(rdbtnSupermelee);
    panelSupermeleeOptions.add(rdbtnSupermelee, "2, 2");
    rdbtnSupermelee.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) 
      {
        WizardPetanqueCategoryPanel.this.xWizard.setWizardProperty(AbstractTournamentWizardPanel.MOVEMENT, AbstractTournamentWizardPanel.PET_SUPERMELEE);
        WizardPetanqueCategoryPanel.this.textInformation.setText(ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("Glossary.Tournament.Petanque.Supermelee.description"));
        WizardPetanqueCategoryPanel.this.textInformation.setRows(17);
      }
    });
    
    rdbtnMonthlyClubTournament = new JRadioButton(Messages.getString("Glossary.Tournament.Petanque.Monatsturnier")); //$NON-NLS-1$
    buttonGroup.add(rdbtnMonthlyClubTournament);
    panelSupermeleeOptions.add(rdbtnMonthlyClubTournament, "2, 4");
    this.rdbtnMonthlyClubTournament.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) 
      {
        WizardPetanqueCategoryPanel.this.xWizard.setWizardProperty(AbstractTournamentWizardPanel.MOVEMENT, AbstractTournamentWizardPanel.PET_MONATSTURNIER);
        WizardPetanqueCategoryPanel.this.textInformation.setText(ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("Glossary.Tournament.Petanque.Monatsturnier.description"));
        WizardPetanqueCategoryPanel.this.textInformation.setRows(27);
        
        WizardPetanqueCategoryPanel.this.scrollPaneInformation.getVerticalScrollBar().setValue(0);
      }
    });
    
    JPanel panelFormeOptions = new JPanel();
    panelFormeOptions.setBorder(new TitledBorder(null, "Form\u00E9", TitledBorder.LEADING, TitledBorder.TOP, null, null));
    GridBagConstraints gbc_panelFormeOptions = new GridBagConstraints();
    gbc_panelFormeOptions.fill = GridBagConstraints.BOTH;
    gbc_panelFormeOptions.gridx = 0;
    gbc_panelFormeOptions.gridy = 1;
    panelOptions.add(panelFormeOptions, gbc_panelFormeOptions);
    panelFormeOptions.setLayout(new FormLayout(new ColumnSpec[] {
        FormSpecs.RELATED_GAP_COLSPEC,
        FormSpecs.DEFAULT_COLSPEC,},
      new RowSpec[] {
        FormSpecs.RELATED_GAP_ROWSPEC,
        FormSpecs.DEFAULT_ROWSPEC,}));
    
    rdbtnACBDTournament = new JRadioButton(Messages.getString("Glossary.Tournament.SwissMode")); //$NON-NLS-1$
    rdbtnACBDTournament.setEnabled(false);
    buttonGroup.add(rdbtnACBDTournament);
    panelFormeOptions.add(rdbtnACBDTournament, "2, 2");
    
    JPanel panelInformation = new JPanel();
    panelInformation.setBorder(null);
    panelInformation.setSize(new Dimension(300, 400));
    add(panelInformation, BorderLayout.EAST);

    textInformation = new JTextArea();
    textInformation.setRows(0);
    textInformation.setAutoscrolls(false);
    textInformation.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
    textInformation.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
    textInformation.setBounds(new Rectangle(5, 5, 5, 5));
    textInformation.setBorder(null);
    
    
    scrollPaneInformation = new JScrollPane(textInformation);
    scrollPaneInformation.setAutoscrolls(true);
    scrollPaneInformation.setAlignmentX(Component.LEFT_ALIGNMENT);
    scrollPaneInformation.setAlignmentY(Component.TOP_ALIGNMENT);
    scrollPaneInformation.setBorder(null);
    scrollPaneInformation.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    scrollPaneInformation.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    panelInformation.add(scrollPaneInformation);
    
    /*
    textInformation.addPropertyChangeListener(new PropertyChangeListener() {
      public void propertyChange(PropertyChangeEvent evt) {
      }
    });*/
    textInformation.setMargin(new Insets(5, 5, 5, 5));
    textInformation.setEditable(false);
    textInformation.setFocusable(false);
    textInformation.setSize(new Dimension(300, 300));
    textInformation.setPreferredSize(new Dimension(300, 290));
    textInformation.setLineWrap(true);
    textInformation.setWrapStyleWord(true);
    
  }
  
  public WizardPetanqueCategoryPanel(AbstractRuleEngineWizard wizard)
  {
    this();
    this.xWizard = wizard;
  }
}
