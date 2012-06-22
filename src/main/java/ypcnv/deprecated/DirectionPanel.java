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
package ypcnv.deprecated;

import charva.awt.BorderLayout;
import charva.awt.FlowLayout;
import charva.awt.Toolkit;
import charvax.swing.JPanel;
import charvax.swing.JScrollPane;
import charvax.swing.JTextArea;
import charvax.swing.border.TitledBorder;

public class DirectionPanel extends JPanel {
    // /** Logger */
    // private static final Log LOG = LogFactory.getLog(DirectionPanel.class);
    private JTextArea textArea ;
    private String labelTextDirection ;

    DirectionPanel(String panelText) {
        initDirectionPanel(panelText);
    }

    public void initDirectionPanel(String panelText) {
        labelTextDirection = panelText;

        setLayout(new FlowLayout());
        //setBorder(new TitledBorder("txt hdr"));

        Integer areaHeight = 8 ;
        Integer areaWidth = Toolkit.getDefaultToolkit()
                                            .getScreenColumns() / 5 - 1;
        setHeight(areaHeight);
        setWidth(areaWidth);

        textArea = new JTextArea(labelTextDirection,
                                        getHeight()-2, getWidth()-2);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(false);
 
        JScrollPane scrollpane = new JScrollPane(textArea);
        scrollpane.setViewportBorder(new TitledBorder("txt hdr"));

        add(scrollpane, BorderLayout.SOUTH);

    }
    
    public void setText(String newText) {
        textArea.setText(newText);
    }
}
