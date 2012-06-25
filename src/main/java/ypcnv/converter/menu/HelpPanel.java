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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

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
import charvax.swing.JOptionPane;
import charvax.swing.JPanel;
import charvax.swing.JScrollPane;
import charvax.swing.JTextArea;
import charvax.swing.border.LineBorder;

/** Help and credits panel. */
public class HelpPanel extends JDialog implements ActionListener {

    /** Logger. */
    private static final Log LOG = LogFactory.getLog(HelpPanel.class);

    private Point topLeftCorner = new Point(2, 3);

    /**
     * Setup frame.
     * @param owner - parent frame object.
     */
    public HelpPanel(Frame owner) {
        super(owner, "> " + UIMetaData.Help.header +" <");
        
        /** Canvas frame. */
        Container aboutPanelContainer = getContentPane();
        aboutPanelContainer.setBackground(UIMetaData.colorBG);
        aboutPanelContainer.setForeground(UIMetaData.colorFG);
        
        /** Frame with content panel. */
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.add(new TextAreaPanel());
        contentPanel.setBackground(UIMetaData.colorBG);
        contentPanel.setForeground(UIMetaData.colorFG);
        contentPanel.setBorder(new LineBorder(UIMetaData.colorFG));
        
        /** Hint panel. */
        JPanel hintPanel = new JPanel();
        hintPanel.setLayout(new BoxLayout(hintPanel, BoxLayout.Y_AXIS));
        hintPanel.add(new JLabel(""));// XXX - extract to Meta
        hintPanel.add(new JLabel("Use cursor UP and DOWN keys to scroll."));
        hintPanel.add(new JLabel("Use TAB key to move input focus."));
        hintPanel.add(new JLabel(""));
        
        /** Button panel. */
        JPanel buttonPanel = new JPanel();
        /** OK and close button. */
        JButton okButton = new JButton(UIMetaData.Help.okButtonId);
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
        if (ae_.getActionCommand().equals(UIMetaData.Help.okButtonId)) {
            hide();
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
            
            StringBuilder content = new StringBuilder(loadContent());
            contentTextArea = new JTextArea(content.toString()
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

    private String loadContent() {
        StringBuilder content = new StringBuilder();
        String lineSep = System.getProperty("line.separator");
        String bf ;
        
        InputStream is = null ;
        BufferedReader br = null ;
        try {
            is = getClass().getClassLoader()
                    .getResourceAsStream(UIMetaData.Help.contentAddress);
            if (is == null) {
                String message = String.format(
                        UIMetaData.Help.FAILED_TO_READ_FROM,
                        UIMetaData.Help.contentAddress);
                LOG.error(message);
                JOptionPane.showMessageDialog(this, message, "> YPCnv <",
                        JOptionPane.PLAIN_MESSAGE);
                return "";
            }
            br = new BufferedReader(new InputStreamReader(is));
            while ((bf = br.readLine()) != null) {
                content.append(bf + lineSep);
            }
            
        } catch (Exception e) {
            String message = String.format(UIMetaData.Help.FAILED_TO_READ_FROM,
                    UIMetaData.Help.contentAddress);
            LOG.error(message);
            JOptionPane.showMessageDialog(this,
            message, "> YPCnv <" , JOptionPane.PLAIN_MESSAGE);
            e.printStackTrace();
        } finally {
            try {
                if(br != null ) { br.close(); }
                if(is != null ) { is.close(); }
            } catch (Exception e) {
                String message = String.format(UIMetaData.Help.FAILED_TO_READ_FROM,
                        UIMetaData.Help.contentAddress);
                LOG.error(message);
                e.printStackTrace();
            }
        }
        return content.toString();
    }
}
