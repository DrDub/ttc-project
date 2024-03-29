/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package eu.project.ttc.tools.indexer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;

import eu.project.ttc.tools.commons.LanguageItem;
import eu.project.ttc.tools.commons.TTCDirectoryChooser;

/**
 * This JPanel exposes the basic configuration part of the Indexer tool.
 * That is only the parameters common to every tool and nothing specific
 * so to keep it really simple.
 *
 * @author Fabien Poulard <fpoulard@dictanova.com>
 */
@SuppressWarnings("serial")
public class ConfigPanelBasic extends JPanel {

    // Language parameter
    private JLabel lblLanguage;
    private JComboBox<LanguageItem> cbLanguage;
    private JEditorPane epLanguage;

    // Input directory parameter
    private JLabel lblInDirectory;
    private JEditorPane epInDirectory;
    private TTCDirectoryChooser fcInDirectory;

    // Output directory parameter
    private JLabel lblOutDirectory;
    private JEditorPane epOutDirectory;
    private TTCDirectoryChooser fcOutDirectory;

    private GroupLayout cfgLayout;

    public ConfigPanelBasic() {
        super(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Prepare components
        int pWidth = (int) getPreferredSize().getWidth() / 2;
        createLanguageForm(pWidth);
        createInputDirectoryForm(pWidth);
        createOutputDirectoryForm(pWidth);

        // Layout the components
        layoutComponents();
    }

    /**
     * This HUGE method is responsible to layout the panel by grouping
     * together parameters labels, fields and descriptions.
     *
     * Yes, this is quite massive and it looks messy, but I didn't find
     * a better way to do it as there are side effects regarding the
     * group layout.
     * So please, do not intend to change things unless you're sure you
     * plainly understand the group layout!
     */
    private void layoutComponents() {
        JPanel config = new JPanel();

        // Init the group layout
        cfgLayout = new GroupLayout(config);
        config.setLayout(cfgLayout);

        // Configure the horizontal layout
        cfgLayout.setHorizontalGroup(
                cfgLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        // Language parameter
                        .addGroup(cfgLayout.createSequentialGroup()
                                .addGroup(cfgLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(lblLanguage)
                                        .addComponent(cbLanguage))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(epLanguage))
                        // Input directory parameter
                        .addGroup(cfgLayout.createSequentialGroup()
                                .addGroup(cfgLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(lblInDirectory)
                                        .addComponent(fcInDirectory))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(epInDirectory)
                        )
                        // Output directory parameter
                        .addGroup(cfgLayout.createSequentialGroup()
                                .addGroup(cfgLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(lblOutDirectory)
                                        .addComponent(fcOutDirectory))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(epOutDirectory)
                        )
        );

        // Configure the vertical layout
        cfgLayout.setVerticalGroup(
                cfgLayout.createSequentialGroup()
                        // Language parameter
                        .addGroup(cfgLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(cfgLayout.createSequentialGroup()
                                        .addComponent(lblLanguage)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cbLanguage,
                                                GroupLayout.DEFAULT_SIZE,
                                                GroupLayout.PREFERRED_SIZE,
                                                GroupLayout.PREFERRED_SIZE))
                                .addComponent(epLanguage))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                // Input directory parameter
                        .addGroup(cfgLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(cfgLayout.createSequentialGroup()
                                        .addComponent(lblInDirectory)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(fcInDirectory))
                                .addComponent(epInDirectory)
                        )
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                // Output directory parameter
                        .addGroup(cfgLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(cfgLayout.createSequentialGroup()
                                        .addComponent(lblOutDirectory)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(fcOutDirectory))
                                .addComponent(epOutDirectory)
                        )
        );

        add(config, BorderLayout.NORTH);
    }

    /**
     * Create the ParameterGroup dedicated to configure the language to be used.
     */
    public void createLanguageForm(int preferredWidth) {
        // Language label
        lblLanguage = new JLabel("<html><b>Language</b></html>");
        lblLanguage.setPreferredSize(new Dimension(
                (int) lblLanguage.getPreferredSize().getHeight(),
                preferredWidth ));

        // Language combobox
        cbLanguage = new JComboBox<LanguageItem>();
        for(String code: new String[]{"en","fr","de","es","ru","da","lv","zh"}) {
            cbLanguage.addItem( new LanguageItem(code) );
        }
        cbLanguage.setPreferredSize(new Dimension(
                (int) cbLanguage.getPreferredSize().getHeight(),
                preferredWidth ));
        cbLanguage.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    LanguageItem item = (LanguageItem) e.getItem();;
                    System.out.println("Detected a language change, fire indexer.language property change.");
                    firePropertyChange(IndexerBinding.PRM.LANGUAGE.getProperty(), null, item.getValue());
                }
            }
        });

        // Help pane
        epLanguage = new JEditorPane();

        epLanguage.setEditable(false);
        epLanguage.setOpaque(false);
        epLanguage.setPreferredSize(new Dimension(
                (int) epLanguage.getPreferredSize().getHeight(),
                preferredWidth));
        try {
            URL res = getClass().getResource("/eu/project/ttc/gui/texts/indexer/param.language.html");
            epLanguage.setPage(res);
        } catch (IOException e){} // No various
    }

    /**
     * Create the ParameterGroup dedicated to configure the input directory.
     */
    public void createInputDirectoryForm(int preferredWidth) {
        // Input directory label
        lblInDirectory = new JLabel("<html><b>Input Directory</b></html>");
        lblInDirectory.setPreferredSize(new Dimension(
                (int) lblInDirectory.getPreferredSize().getHeight(),
                preferredWidth ));

        // Input directory field
        fcInDirectory = new TTCDirectoryChooser("Choose the input directory");
        fcInDirectory.setPreferredSize(new Dimension(
                (int) fcInDirectory.getPreferredSize().getHeight(),
                preferredWidth ));
        //fcInDirectory.setBorder(BorderFactory.createLineBorder(Color.BLUE, 1));
        fcInDirectory.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                if ("path".equals(evt.getPropertyName()))
                    firePropertyChange(IndexerBinding.PRM.INPUT.getProperty(), evt.getOldValue(), evt.getNewValue());
            }
        });

        // Help panel
        epInDirectory = new JEditorPane();
        epInDirectory.setEditable(false);
        epInDirectory.setOpaque(false);
        epInDirectory.setPreferredSize( new Dimension(
                (int) epInDirectory.getPreferredSize().getHeight(),
                preferredWidth ));
        try {
            URL res = getClass().getResource("/eu/project/ttc/gui/texts/indexer/param.inputdirectory.html");
            epInDirectory.setPage(res);
        } catch (IOException e) {} // No help
    }

    /**
     * Create the ParameterGroup dedicated to configure the output directory.
     */
    public void createOutputDirectoryForm(int preferredWidth) {
        // Output directory label
        lblOutDirectory = new JLabel("<html><b>Output Directory</b></html>");
        lblOutDirectory.setPreferredSize(new Dimension(
                (int) lblOutDirectory.getPreferredSize().getHeight(),
                preferredWidth ));

        // Output directory field
        fcOutDirectory = new TTCDirectoryChooser("Choose the output directory");
        fcOutDirectory.setPreferredSize(new Dimension(
                (int) fcOutDirectory.getPreferredSize().getHeight(),
                preferredWidth ));
        //fcOutDirectory.setBorder(BorderFactory.createLineBorder(Color.BLUE, 1));
        fcOutDirectory.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                if ("path".equals(evt.getPropertyName()))
                    firePropertyChange(IndexerBinding.PRM.OUTPUT.getProperty(), evt.getOldValue(), evt.getNewValue());
            }
        });

        // Help pane
        epOutDirectory = new JEditorPane();
        epOutDirectory.setEditable(false);
        epOutDirectory.setOpaque(false);
        epOutDirectory.setPreferredSize( new Dimension(
                (int) epOutDirectory.getPreferredSize().getHeight(),
                preferredWidth ));
        try {
            URL res = getClass().getResource("/eu/project/ttc/gui/texts/indexer/param.outputdirectory.html");
            epOutDirectory.setPage(res);
        } catch (IOException e) {} // No various
    }

    ////////////////////////////////////////////////////////////////////// ACCESSORS

    public void setLanguage(String language) {
        for(int i=0 ; i < cbLanguage.getItemCount() ; i++) {
            LanguageItem item = (LanguageItem) cbLanguage.getItemAt(i);
            if ( item.getValue().equals(language) ) {
                if (cbLanguage.getSelectedIndex() != i) {
                    cbLanguage.setSelectedItem( item );
                }
                return;
            }
        }
        // If reach here, we have a problem
        throw new IllegalArgumentException("I cannot reflect the change to value '"
                + language + "' as I do not handle this value.");
    }

    public void setLanguageError(Throwable e) {
        lblLanguage.setText("<html><b>Language</b><br/><p style=\"color: red; font-size: small\">"
                + e.getMessage() + "</p></html>");
    }
    public void unsetLanguageError() {
        lblLanguage.setText("<html><b>Language</b></html>");
    }

    public String getLanguage() {
        return ((LanguageItem) cbLanguage.getSelectedItem()).getValue();
    }

    public void setInputDirectory(String inputDirectory) {
        fcInDirectory.setPath(inputDirectory);
    }

    public void setInputDirectoryError(Throwable e) {
        lblInDirectory.setText("<html><b>Input Directory</b><br/><p style=\"color: red; font-size: small\">"
                + e.getMessage() + "</p></html>");
    }
    public void unsetInputDirectoryError() {
        lblInDirectory.setText("<html><b>Input Directory</b></html>");
    }

    public String getInputDirectory() {
        return fcInDirectory.getPath();
    }

    public void setOutputDirectory(String outputDirectory) {
        fcOutDirectory.setPath(outputDirectory);
    }

    public void setOutputDirectoryError(Throwable e) {
        lblOutDirectory.setText("<html><b>Output Directory</b><br/><p style=\"color: red; font-size: small\">"
                + e.getMessage() + "</p></html>");
    }
    public void unsetOutputDirectoryError() {
        lblOutDirectory.setText("<html><b>Output Directory</b></html>");
    }

    public String getOutputDirectory() {
        return fcOutDirectory.getPath();
    }

}
