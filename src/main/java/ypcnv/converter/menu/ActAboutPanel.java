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
import charva.awt.Container;
import charva.awt.Frame;
import charva.awt.Point;
import charva.awt.Toolkit;
import charva.awt.event.ActionEvent;
import charva.awt.event.ActionListener;
import charvax.swing.BoxLayout;
import charvax.swing.JButton;
import charvax.swing.JDialog;
import charvax.swing.JLabel;
import charvax.swing.JPanel;
import charvax.swing.JScrollPane;
import charvax.swing.JTextArea;
import charvax.swing.border.LineBorder;

/** About and credits panel. */
public class ActAboutPanel extends JDialog implements ActionListener {

    /** Logger. */
    private static final Log LOG = LogFactory.getLog(ActAboutPanel.class);

    private Point topLeftCorner = new Point(2, 3);

    /**
     * Setup frame.
     * @param owner - parent frame object.
     */
    public ActAboutPanel(Frame owner) {
        super(owner, "> " + UIMetaData.About.header +" <");
        
        /** Canvas frame. */
        Container aboutPanelContainer = getContentPane();

        /** Frame with content panel. */
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.add(new TextAreaPanel());

        /** Hint panel. */
        JPanel hintPanel = new JPanel();
        hintPanel.setLayout(new BoxLayout(hintPanel, BoxLayout.Y_AXIS));
        hintPanel.add(new JLabel(""));
        hintPanel.add(new JLabel("Use cursor UP and DOWN keys to scroll."));
        hintPanel.add(new JLabel("Use TAB key to move input focus."));
        hintPanel.add(new JLabel(""));
        
        /** Button panel. */
        JPanel buttonPanel = new JPanel();
        /** OK and close button. */
        JButton okButton = new JButton(UIMetaData.About.okButtonId);
        okButton.addActionListener(this);
        buttonPanel.add(okButton);

        aboutPanelContainer.add(contentPanel, BorderLayout.NORTH);
        aboutPanelContainer.add(hintPanel, BorderLayout.CENTER);
        aboutPanelContainer.add(buttonPanel, BorderLayout.SOUTH);

        aboutPanelContainer.setLocation(topLeftCorner);
        pack();

    }

    /** Action and event listener. */
    @Override
    public void actionPerformed(ActionEvent ae_) {
        if (ae_.getActionCommand().equals(UIMetaData.About.okButtonId)) {
            hide();
            LOG.debug("Hide ActAboutPanel done.");
        }
    }

    /** Frame with content. */
    private class TextAreaPanel extends JPanel {
        /** Content carrier panel. */
        private JTextArea contentTextArea;
        private Integer numOfNestedFrames = 3;
        private Integer maximumTextAreaWidth=90;
        private Integer textAreaWidth = Toolkit.getDefaultToolkit()
                .getScreenColumns()
                - numOfNestedFrames * 2
                - topLeftCorner.x * 2;
        private Integer textAreaHeight = 8;

        TextAreaPanel() {
            setLayout(new BorderLayout());
            // setBorder(new TitledBorder("JScrollPane title"));

            if(textAreaWidth>maximumTextAreaWidth) {
                textAreaWidth = maximumTextAreaWidth;
            }
            
            contentTextArea = new JTextArea(UIMetaData.About.content
                                        , textAreaHeight, textAreaWidth);
            contentTextArea.setEditable(false);
            contentTextArea.setLineWrap(true);
            contentTextArea.setWrapStyleWord(true);
            contentTextArea.setBackground(UIMetaData.colorBG);
            contentTextArea.setForeground(UIMetaData.colorFG);

            JScrollPane scrollpane = new JScrollPane(contentTextArea);
            scrollpane.setViewportBorder(new LineBorder(UIMetaData.colorFG));
            scrollpane.setBackground(UIMetaData.colorScrollCursor);

            add(scrollpane, BorderLayout.NORTH);
        }

    }
}
