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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import charva.awt.BorderLayout;
import charvax.swing.JPanel;
import charvax.swing.JScrollPane;
import charvax.swing.JTextArea;
import charvax.swing.border.EmptyBorder;

/** Hint panel. */
public class HintPanel extends JPanel {
    /** Logger */
    @SuppressWarnings("unused")
    private static final Log LOG = LogFactory.getLog(HintPanel.class);
    
    /** Panel with content. */
    private JTextArea textArea ;

    HintPanel(String panelText) {
        initHintPanel(panelText);
    }
    
    /**
     * Setup frame.
     * @param panelText - panel content.
     */
    private void initHintPanel(String panelText) {

        setLayout(new BorderLayout());
        //setBorder(new TitledBorder("> hint <"));
        
        // XXX - magic numbers
        Integer textAreaHeight = 3 ;
        Integer textAreaWidth = UIMetaData.dialogCommonSize.x - 4;
        textArea = new JTextArea(panelText);
        textArea.setEditable(false);
        textArea.setEnabled(false); // Will not gain a focus.
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setRows(textAreaHeight);
        textArea.setColumns(textAreaWidth);
        
        JScrollPane scrollpane = new JScrollPane(textArea);
        
        //scrollpane.setViewportBorder(new LineBorder(UIMetaData.colorFG));
        scrollpane.setViewportBorder(new EmptyBorder(1, 1, 1, 1));
        scrollpane.setBackground(UIMetaData.colorBG);
        
        add(scrollpane);

    }

}
