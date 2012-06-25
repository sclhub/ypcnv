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

import ypcnv.converter.menu.UIMetaData;
import charva.awt.BorderLayout;
import charva.awt.Toolkit;
import charvax.swing.JPanel;
import charvax.swing.JScrollPane;
import charvax.swing.JTextArea;
import charvax.swing.border.LineBorder;

/** Message and log panel. */
public class MessagePanel extends JPanel {
     /** Logger */
     @SuppressWarnings("unused")
    private static final Log LOG = LogFactory.getLog(MessagePanel.class);

    /** Panel with content. */
    private JTextArea textArea ;

    MessagePanel(String panelText) {
        initMessagePanel(panelText);
    }

    /**
     * Setup frame.
     * @param panelText - panel content.
     */
    public void initMessagePanel(String panelText) {

        setLayout(new BorderLayout());
        //setBorder(new TitledBorder("> messages <"));
        
        Integer textAreaHeight = 6 ;
        Integer textAreaWidth = Toolkit.getDefaultToolkit()
                                            .getScreenColumns() - 4;
        textArea = new JTextArea(panelText,
                                        textAreaHeight, textAreaWidth);
        textArea.setEditable(false);
        textArea.setLineWrap(false);
        textArea.setWrapStyleWord(false);
        
        JScrollPane scrollpane = new JScrollPane(textArea);
        scrollpane.setViewportBorder(new LineBorder(UIMetaData.colorFG));
        add(scrollpane);

    }

}
