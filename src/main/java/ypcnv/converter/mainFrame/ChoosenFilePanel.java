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
package ypcnv.converter.mainFrame;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import charva.awt.BorderLayout;
import charva.awt.Component;
import charva.awt.Toolkit;
import charvax.swing.JPanel;
import charvax.swing.JScrollPane;
import charvax.swing.JTextArea;
import charvax.swing.border.TitledBorder;

/** Chosen configuration of a data source indication frame. */
public class ChoosenFilePanel extends JPanel {
    /** Logger */
    private static final Log LOG = LogFactory.getLog(ChoosenFilePanel.class);

    /** Panel with content. */
    private JTextArea textArea ;
    
    /** Unique ID for the component inside this dialog. */
    private static final String scrollPaneID = "Scroll pane main.";
            
    ChoosenFilePanel(String panelHeader, String panelText) {
        initChoosenFilePanel(panelHeader, panelText);
    }

    /**
     * Setup frame.
     * @param panelHeader - panel header.
     * @param panelText - panel content.
     */
    public void initChoosenFilePanel(String panelHeader, String panelText) {
        if( panelText == null ) {
            panelText="";
        }
        
        setLayout(new BorderLayout());

        Integer areaHeight = 4 ;
        Integer areaWidth = Toolkit.getDefaultToolkit()
                                            .getScreenColumns() - 2;
        setHeight(areaHeight);
        setWidth(areaWidth);
        
        textArea = new JTextArea(panelText, getHeight()-2, getWidth()-2);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(false);
 
        JScrollPane scrollpane = new JScrollPane(textArea);
        scrollpane.setViewportBorder(new TitledBorder(panelHeader));
        scrollpane.setName(scrollPaneID);

        add(scrollpane, BorderLayout.NORTH);
        
    }

    /**
     * Header setter.
     * @param header - new header.
     */
    public void setHeader(String header) {
        Component[] allItems = this.getComponents();
        for(Component item : allItems) {
            if(item.getName() == scrollPaneID) {
                ((JScrollPane) item).setViewportBorder(new TitledBorder(header));
            }
        }
    }

    /**
     * Content setter.
     * @param content - new content.
     */
    public void setContent(String content) {
        if (textArea == null ) {
            LOG.debug("Tried to set panel's content when panel do not exist.");
            return ;
        } else if (content == null) {
            return;
        }
        textArea.setText(content.toString());
    }

}
