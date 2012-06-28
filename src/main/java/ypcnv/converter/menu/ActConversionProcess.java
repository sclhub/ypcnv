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
import ypcnv.converter.main.Converter;
import ypcnv.converter.mainFrame.MainFrame;
import ypcnv.views.abstr.DataFormatID;
import ypcnv.views.abstr.Side;
import charva.awt.BorderLayout;
import charva.awt.Container;
import charva.awt.Point;
import charva.awt.Toolkit;
import charva.awt.event.ActionEvent;
import charva.awt.event.ActionListener;
import charvax.swing.BoxLayout;
import charvax.swing.JButton;
import charvax.swing.JDialog;
import charvax.swing.JOptionPane;
import charvax.swing.JPanel;
import charvax.swing.JProgressBar;
import charvax.swing.SwingUtilities;
import charvax.swing.border.LineBorder;

/** Help and credits panel. */
public class ActConversionProcess extends JDialog 
                                implements ActionListener {

    /** Logger. */
    private static final Log LOG = LogFactory.getLog(ActConversionProcess.class);

    private Point topLeftCorner = new Point(2, 3);

    /**
     * Converter object, which processes statuses will be displayed inside this
     * frame.
     */
    private Converter cnv = null;

    /**
     * Configurations of data sources to be used while conversion. 
     */
    private ArrayList<DataSourceConf> confList = null ;

    private JProgressBar progressBar = new JProgressBar();
    private Thread converterThread = null;
    private Thread progressThread = null;

    /** Progress bar specific. */
    private StringBuilder label = new StringBuilder(UIMetaData.ConverPanel.progressBarLabel);
    /** Progress bar specific. */
    private Integer maxDotsQuantity = 6 ;
    /** Progress bar specific. */
    private Integer index = 0 ;

    private MainFrame frameOwner = null ; 
    private ActConversionProcess dialogInitiator = this;
    /**
     * Setup frame.
     * @param owner - parent frame object.
     */
    public ActConversionProcess(MainFrame owner, ArrayList<DataSourceConf> ar) {
        super(owner, "> " + UIMetaData.ConverPanel.header +" <");
        this.frameOwner = owner ;
        this.confList = ar ;
        initFrame();
    }

    @Override
    public void show() {
        if(isKnownConf(confList) && isComplete(confList)) {
            startConversion();
            super.show();
        } else {
            String msg = UIMetaData.ConverPanel.notImplementedMessage;
            LOG.info(msg);
            JOptionPane.showMessageDialog(this, msg, "> YPCnv <",
                    JOptionPane.PLAIN_MESSAGE);
            Toolkit.getDefaultToolkit().triggerGarbageCollection(this);
            super.hide();
        }
    }
    
    /** Setup frame. */
    private void initFrame() {
        /** Canvas frame. */
        Container cnvPanelContainer = getContentPane();
        cnvPanelContainer.setBackground(UIMetaData.colorBG);
        cnvPanelContainer.setForeground(UIMetaData.colorFG);
        
        /** Frame with content panel. */
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        progressBar.setStringPainted(true);
        //progressBar.setString("");
        //progressBar.setValue(0);
        progressBar.setIndeterminate(true);
        contentPanel.add(progressBar, BorderLayout.CENTER);

        contentPanel.setBackground(UIMetaData.colorBG);
        contentPanel.setForeground(UIMetaData.colorFG);
        contentPanel.setBorder(new LineBorder(UIMetaData.colorFG));
        
        /** Button panel. */
        JPanel buttonPanel = new JPanel();
        /** OK and close button. */
        JButton okButton = new JButton(UIMetaData.ConverPanel.cancelButtonId);
        okButton.addActionListener(this);
        buttonPanel.add(okButton);

        cnvPanelContainer.add(contentPanel, BorderLayout.NORTH);
        cnvPanelContainer.add(buttonPanel, BorderLayout.SOUTH);

        cnvPanelContainer.setLocation(topLeftCorner);
        
        this.addWindowListener(frameOwner);
        
        pack();
        
    }
    
    private void startConversion() {
        if (converterThread != null && converterThread.isAlive()) {
            JOptionPane.showMessageDialog(this, "The conversion is already running",
                    "Error", JOptionPane.PLAIN_MESSAGE);
        } else {
            converterThread = new ConverterThread();
            converterThread.start();
        }
        if (progressThread != null && progressThread.isAlive()) {
            JOptionPane.showMessageDialog(this, "The progress update task is already running",
                    "Error", JOptionPane.PLAIN_MESSAGE);
        } else {
            progressThread = new progressThread();
            progressThread.start();
        }
    }
    
    /** Action and event listener. */
    @Override
    public void actionPerformed(ActionEvent ae) {
        String cmd = ae.getActionCommand();
        if (cmd.equals(UIMetaData.ConverPanel.cancelButtonId))
        {
            terminateTasks();
            hide();
            System.gc();
        } else if(cmd.equals(UIMetaData.ConverPanel.processDone))
        {
            terminateTasks();
            hide();
            //frameOwner.actionPerformed(new ActionEvent(this,
            //        MainFrameMeta.Events.conversionDone));
            
            System.gc();
        }
    }

    private void terminateTasks() {
        if (converterThread != null && converterThread.isAlive()) {
            converterThread.interrupt();
        }
        if (progressThread != null && progressThread.isAlive()) {
            progressThread.interrupt();
        }
    }
    
    private Boolean isKnownConf(ArrayList<DataSourceConf> ar) {
        for (DataSourceConf config : ar) {
            if ((config.getSide() == Side.source 
                    && config.getObjectFormat() != DataFormatID.XLS)
                    || (config.getSide() == Side.destination
                    && config.getObjectFormat() != DataFormatID.VCF)) {
                return false ;
            }
        }
        return true;
    }
    
    private Boolean isComplete(ArrayList<DataSourceConf> ar) {
        Boolean isConfComplete = false;
        Boolean haveSource = false;
        Boolean haveDestination = false;

        Iterator<DataSourceConf> confsListIterator = ar.iterator();
        while (confsListIterator.hasNext()) {
            DataSourceConf config = confsListIterator.next();
            if (config.isComplete()) {
                if (config.getSide() == Side.source) {
                    haveSource = true;
                }
                if (config.getSide() == Side.destination) {
                    haveDestination = true;
                }
            }
        }
        if (haveSource && haveDestination) {
            isConfComplete = true;
        }
        
        return isConfComplete ;
    }
    
    /**
     * Task performing conversion.
     */
    private class ConverterThread extends Thread {

        private ConverterThread() {
        }

        public void run() {
            try {
                cnv = new Converter(confList);
                cnv.processConversion();
            } catch (Exception e) {
                LOG.error("Failed to convert. Parameters were: "
                        + confList.toString());
                e.printStackTrace();
            }
            dialogInitiator.actionPerformed(new ActionEvent(dialogInitiator,
                    UIMetaData.ConverPanel.processDone));
        }
        
    }

    /**
     * Task performing visual process.
     */
    private class progressThread extends Thread {

        private progressThread() {
        }

        public void run() {
            try {
                while(true) {
                    Runnable updater = new ProgressUpdate(label.toString());
                    SwingUtilities.invokeLater(updater);
    
                    if(index >= maxDotsQuantity) {
                        index = - maxDotsQuantity;
                    }

                    if( index >= 0 ) {
                        label.append(" .");
                    } else if( index < 0 ) {
                        label.delete(label.length()-2, label.length());
                    }
                    index+=2;
    
                    Thread.sleep(UIMetaData.ConverPanel.progressBarTime);
                    
                }
            } catch (InterruptedException e) {
                LOG.debug("Progress bar update thread was interrupted");
                return;
            }
        }
    }

    private class ProgressUpdate implements Runnable {
        private Integer percent = -1 ;
        private String text ;

        private ProgressUpdate(Integer percent) {
            this.percent = percent;
        }
        private ProgressUpdate(String text) {
            this.text = text ;
        }
        private ProgressUpdate(String text, Integer percent) {
            this.percent = percent;
            this.text = text ;
        }

        public void run() {
            progressBar.setString(text);
            if(percent >=0 ) {
                if(percent == 100 ) {
                    progressBar.setIndeterminate(false);
                }
                progressBar.setValue(percent);
            }
        }
    }

}
