/*
 *  Copyright 2011-2012 ASCH
 *  
 *  This file is part of YPCnv.
 *
 *  YPCnv is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  YPCnv is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with YPCnv.  If not, see <http://www.gnu.org/licenses/>.
 *  
 */
package ypcnv.converter.menu;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ypcnv.converter.conf.DataSourceConf;
import ypcnv.converter.mainFrame.MainFrame;
import ypcnv.converter.mainFrame.MainFrameMeta;
import ypcnv.helpers.EnumHelper;
import ypcnv.views.abstr.DataFormatID;
import ypcnv.views.abstr.Side;
import charva.awt.BorderLayout;
import charva.awt.Component;
import charva.awt.Container;
import charva.awt.Font;
import charva.awt.Point;
import charva.awt.event.ActionEvent;
import charva.awt.event.ActionListener;
import charvax.swing.BoxLayout;
import charvax.swing.DefaultListModel;
import charvax.swing.JButton;
import charvax.swing.JDialog;
import charvax.swing.JLabel;
import charvax.swing.JList;
import charvax.swing.JPanel;
import charvax.swing.JScrollPane;
import charvax.swing.JTextField;
import charvax.swing.border.TitledBorder;
import charvax.swing.event.ListDataEvent;
import charvax.swing.event.ListDataListener;
import charvax.swing.event.ListSelectionEvent;
import charvax.swing.event.ListSelectionListener;

/** Select data sources formats frame. */
public class SelectDataSourceFormat extends JDialog implements ActionListener {

    /** Logger */
    private static final Log LOG = LogFactory.getLog(SelectDataSourceFormat.class);

    /** Parent frame. */
    private MainFrame owner ;
    /** Data sources configurations to be processed. */
    private ArrayList<DataSourceConf> confsList ;
    
    private Point topLeftCorner = new Point(2, 3);
    
    /** Own names of lists with available formats. */
    private enum AllowedListNames {
        Source(UIMetaData.SelectFormat.listHeaderSrc),
        Destination(UIMetaData.SelectFormat.listHeaderDst);
        
        String value = null ;
        
        AllowedListNames(String value) {
            this.value = value ;
        }
        
        public String get() {
            return value ;
        }
        
    }
    
    /**
     * @param owner - parent frame.
     * @param confsList - list of configurations to work with.
     */
    public SelectDataSourceFormat(MainFrame owner, ArrayList<DataSourceConf> confsList) {
        super(owner, "> " + UIMetaData.SelectFormat.header + " <");
        this.owner = owner ;
        this.confsList = confsList;
        initSelectDataSourceFormat(owner);
    }
    
    /**
     * Setup frame.
     * @param owner - parent component.
     */
    private void initSelectDataSourceFormat(Component owner) {
        Container selectFormatPaneContainer = getContentPane();
        selectFormatPaneContainer.setBackground(UIMetaData.colorBG);
        
        JPanel hintPanel = new HintPanel(UIMetaData.SelectFormat.hint);
        
        /* * */
        
        JPanel inFormatsPanel = null ;
        JPanel outFormatsPanel = null ;
        
        for(DataSourceConf config : confsList) {
            Side side = config.getSide();
            if( side == null) {
                side = Side.heath;
            }
            switch(side) {
            case source:
                inFormatsPanel = new FormatNamesListPanel(
                        AllowedListNames.Source.get(),
                        config.getObjectFormat());
                inFormatsPanel.setLayout(new BoxLayout(inFormatsPanel,
                        BoxLayout.Y_AXIS));
                inFormatsPanel.add(new JLabel(""));
                break;
            case destination:
                outFormatsPanel = new FormatNamesListPanel(
                        AllowedListNames.Destination.get(),
                        config.getObjectFormat());
                outFormatsPanel.setLayout(new BoxLayout(outFormatsPanel,
                        BoxLayout.Y_AXIS));
                outFormatsPanel.add(new JLabel(""));
                break;
            }
        }
        
        /* * */
        
        JPanel buttonPanel = new JPanel();
        
        JButton okButton = new JButton(UIMetaData.SelectFormat.okButtonId);
        okButton.addActionListener(this);
        buttonPanel.add(okButton);


        selectFormatPaneContainer.add(hintPanel, BorderLayout.NORTH);
        selectFormatPaneContainer.add(inFormatsPanel, BorderLayout.WEST);
        selectFormatPaneContainer.add(outFormatsPanel, BorderLayout.EAST);
        selectFormatPaneContainer.add(buttonPanel, BorderLayout.SOUTH);

        selectFormatPaneContainer.setLocation(topLeftCorner);
        selectFormatPaneContainer.setWidth( UIMetaData.dialogCommonSize.x );
        selectFormatPaneContainer.setHeight( UIMetaData.dialogCommonSize.y );

        pack();
    }

    /** Action and event listener. */
    @Override
    public void actionPerformed(ActionEvent ae_) {
        if (ae_.getActionCommand().equals(UIMetaData.SelectFormat.okButtonId)) {
            hide();
            owner.actionPerformed(new ActionEvent(this, MainFrameMeta.Events.dataFormatsRefreshed));
        }
    }
    
    /**
     * Panel with a list of available values.
     */
    class FormatNamesListPanel extends JPanel
                                implements ListSelectionListener,
                                            ListDataListener {
        private JList fileFormatsList;
        private JTextField choosenItemsTextField ;
        private Integer visibleRowsQuantity = 5 ;
        private Integer minimumListWidth = 22 ;

        /**
         * @param listTitle - header.
         * @param preChoosenItem - preselected item if any.
         */
        FormatNamesListPanel(String listTitle, DataFormatID preChoosenItem) {
            DefaultListModel model = new DefaultListModel();

            Integer listWidth = 0 ;
            Iterator<String> listIterator
                    = EnumHelper.toStringList(DataFormatID.class).iterator();
            while(listIterator.hasNext()) {
                String str = listIterator.next();
                model.addElement(str);
                listWidth = ( listWidth < str.length() ) ? str.length() : listWidth ;
            }
            if(listWidth < minimumListWidth ) {
                listWidth = minimumListWidth ;
            }

            model.addListDataListener(this);

            fileFormatsList = new JList(model);
            fileFormatsList.setName(listTitle);
            fileFormatsList.setVisibleRowCount( visibleRowsQuantity );
            fileFormatsList.setColumns( listWidth );
            fileFormatsList.addListSelectionListener(this);
            fileFormatsList.setBackground(UIMetaData.colorBG);
            fileFormatsList.setForeground(UIMetaData.colorFG);

            JScrollPane formatsScrollPane = new JScrollPane(fileFormatsList);
            formatsScrollPane.setViewportBorder(new TitledBorder("> " + listTitle + " <"));
            formatsScrollPane.setBackground(UIMetaData.colorBG);
            formatsScrollPane.setForeground(UIMetaData.colorFG);
            
            add(formatsScrollPane, BorderLayout.NORTH);

            JPanel choosenItemsPanel = new JPanel();
            choosenItemsPanel.add(new JLabel(UIMetaData.SelectFormat.selectedAreaLabel));

            choosenItemsTextField = new JTextField( UIMetaData.SelectFormat.choosenItemsFieldWidth );
            choosenItemsTextField.setEnabled(false);
            if(preChoosenItem == null) {
                choosenItemsTextField.setText(UIMetaData.SelectFormat.defaultFormatName);
            } else {
                choosenItemsTextField.setText(preChoosenItem.toString());
            }
            choosenItemsTextField.setBackground(UIMetaData.colorBG);
            choosenItemsTextField.setForeground(UIMetaData.colorFGHi);
            choosenItemsTextField.setFont(new Font(null, charva.awt.Font.BOLD, 0));
            choosenItemsPanel.add(choosenItemsTextField);

            choosenItemsPanel.setBackground(UIMetaData.colorBG);
            choosenItemsPanel.setForeground(UIMetaData.colorFG);
            
            add(choosenItemsPanel, BorderLayout.SOUTH);

            pack();
            
        }

        /**
         * This method implements the ListSelectionListener interface,
         * and is called when an item is selected or deselected in the
         * JList.
         */
        public void valueChanged(ListSelectionEvent e_) {
            Object[] items = fileFormatsList.getSelectedValues();
            String s = ( items.length > 0 ) ? (String) items[0] : "" ;
            String listName=fileFormatsList.getName();

            LOG.debug("Going with item named '" + s + "' from list '" + listName + "'.");
            
            choosenItemsTextField.setText(s);

            switch(AllowedListNames.valueOf(listName)) {
            case Source:
                for(DataSourceConf dsc : confsList ) {
                    if(dsc.getSide() == Side.source) {
                        if(s.equals("")) {
                            dsc.setObjectFormat(null);
                        } else {
                            dsc.setObjectFormat(DataFormatID.valueOf(s));
                        }
                    }
                }
                break;
            case Destination:
                for(DataSourceConf dsc : confsList ) {
                    if(dsc.getSide() == Side.destination) {
                        if(s.equals("")) {
                            dsc.setObjectFormat(null);
                        } else {
                            dsc.setObjectFormat(DataFormatID.valueOf(s));
                        }
                    }
                }
                break;
            }
        }

        /**
         * This method is called on change in a list object.
         */
        public void contentsChanged(ListDataEvent e) {
            fileFormatsList.removeSelectionInterval(e.getIndex0(), e.getIndex1());
            fileFormatsList.repaint();
        }

        /**
         * This method is defined for compatibility with Swing,
         * but is not used in CHARVA.
         */
        public void intervalAdded(ListDataEvent e) { }

        /**
         * This method is defined for compatibility with Swing,
         * but is not used in CHARVA.
         */
        public void intervalRemoved(ListDataEvent e) { }

    }
    
    /** Configurations getter. */
    public ArrayList<DataSourceConf> getRequestedConvParam() {
        return confsList;
    }

    
    
}

