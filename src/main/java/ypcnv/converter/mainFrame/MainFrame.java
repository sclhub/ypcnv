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

/*!
 * \file
 * \brief Main window.
 * 
 */

package ypcnv.converter.mainFrame;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ypcnv.converter.conf.DataSourceConf;
import ypcnv.converter.menu.ActAboutPanel;
import ypcnv.converter.menu.ConstructMainMenu;
import ypcnv.converter.menu.ActConversionProcess;
import ypcnv.converter.menu.ActHelpPanel;
import ypcnv.converter.menu.ActSelectDataSourceFormat;
import ypcnv.converter.menu.ActSelectFile;
import ypcnv.converter.menu.UIMetaData;
import ypcnv.errorCodes.ErrorCodes;
import ypcnv.helpers.GBC;
import ypcnv.views.abstr.Side;
import charva.awt.Container;
import charva.awt.GridBagLayout;
import charva.awt.Toolkit;
import charva.awt.event.ActionEvent;
import charva.awt.event.ActionListener;
import charva.awt.event.WindowEvent;
import charva.awt.event.WindowListener;
import charvax.swing.JFrame;
import charvax.swing.JOptionPane;


/**
 * Main frame of visual UI.
 */
public class MainFrame extends JFrame
                       implements ActionListener,
                                  WindowListener {

    
    /** Logger. */
    private static final Log LOG = LogFactory.getLog(MainFrame.class);

    /** Data source - data donor. */
    private DataSourceConf srcObjectConfig = null ;
    /** Data destination - data acceptor. */
    private DataSourceConf dstObjectConfig = null ;
    
    /* Main frame panels. */
    
    /** Data donor configuration indicator. */
    private ChoosenFilePanel inFileNamePanel = null ;
    /** Data acceptor configuration indicator. */
    private ChoosenFilePanel outFileNamePanel = null ;

//    /** Name of the last used file or directory. */
//    private File lastUsedrFileObject = null ;
    
    /** Message and log panel. */
    private MessagePanel messagePanel = null ;
    
    /** Panel with hints and similar. */
    private HintPanel hintPanel = null ;

    /** File selection dialog. */
    private ActSelectDataSourceFormat selectFileFormatPane = null ;

    MainFrame() {
        super("> YPCnv <");
    }
    
    public MainFrame(ArrayList<DataSourceConf> confsList) {
        this();

        refactorConfigurations(confsList);
        
        LOG.debug("Processing main frame launch with requested parameters for a source: "
                                            + this.srcObjectConfig.toString());
        LOG.debug("Processing main frame launched with requested parameters for a destination: "
                                            + this.dstObjectConfig.toString());
        initMainFrame();
    }

//    public File getLastUsedrFileObject() {
//        return lastUsedrFileObject;
//    }

    /** Setup main frame. */
    private void initMainFrame() {
        setForeground(UIMetaData.colorFG);
        setBackground(UIMetaData.colorBG);
        Container contentPane = getContentPane();
        contentPane.setLayout(new GridBagLayout());

        setJMenuBar(ConstructMainMenu.construct(this));

        Object donorAddress = srcObjectConfig.getObjectAddress() ;
        Object acceptorAddress = dstObjectConfig.getObjectAddress() ;
        if(donorAddress == null) {
            donorAddress = "";
        }
        if(acceptorAddress == null) {
            acceptorAddress = "";
        }
        
        String header ; 
        header = new String(MainFrameMeta.SRC_INFO_PANEL_HEADER + " - "
                + srcObjectConfig.getObjectFormat() + " "
                + MainFrameMeta.SRC_INFO_PANEL_HEADER_SUFFIX + " <");
        inFileNamePanel = new ChoosenFilePanel(header,
                                               donorAddress.toString());
        header = new String(MainFrameMeta.DST_INFO_PANEL_HEADER + " - "
                + dstObjectConfig.getObjectFormat() + " "
                + MainFrameMeta.DST_INFO_PANEL_HEADER_SUFFIX + " <");
        outFileNamePanel = new ChoosenFilePanel(header,
                                                acceptorAddress.toString());
        
        messagePanel = new MessagePanel(MainFrameMeta.messagePanelContent);
        hintPanel = new HintPanel(MainFrameMeta.hintPanelText);

        GBC gbc = new GBC();
        gbc.setGBC(1,0,5,1);
        contentPane.add(inFileNamePanel, gbc);
        gbc.setGBC(1,1,5,1);
        contentPane.add(outFileNamePanel, gbc);
        gbc.setGBC(0,2,5,1);
        contentPane.add(messagePanel, gbc);
        gbc.setGBC(0,3,5,1);
        contentPane.add(hintPanel, gbc);

        setLocation(0, 0);
        setSize(Toolkit.getDefaultToolkit().getScreenColumns(), Toolkit
                .getDefaultToolkit().getScreenRows());

        validate();

    }
    
    /** Main frame's action and event listener. */
    @Override
    public void actionPerformed(ActionEvent ae_) {
        String actionCommand = ae_.getActionCommand();
        if (actionCommand.equals(MainFrameMeta.Menu.exit))
        {
            System.gc();    // So that Heap/CPU Profiling Tool reports only live objects.
            System.exit(ErrorCodes.OK.get());
        } else if (actionCommand.equals(MainFrameMeta.Menu.fileSelectIn))
        {
            File newAddress = ActSelectFile.selectFileDialog(
                                    this, 
                                    UIMetaData.FileSelect.headerDonor,
                                    null,
                                    srcObjectConfig);
            if(newAddress != null) {
                srcObjectConfig.setObjectAddress(newAddress);
                inFileNamePanel.setContent(srcObjectConfig.getObjectAddress().toString());
                inFileNamePanel.repaint();
            }
        } else if (actionCommand.equals(MainFrameMeta.Menu.fileSelectOut))
        {
            File newAddress = ActSelectFile.selectFileDialog(
                                    this,
                                    UIMetaData.FileSelect.headerAcceptor,
                                    null,
                                    dstObjectConfig);
            if(newAddress != null) {
                dstObjectConfig.setObjectAddress(newAddress);
                outFileNamePanel.setContent(dstObjectConfig.getObjectAddress().toString());
                outFileNamePanel.repaint();
            }
        } else if (actionCommand.equals(MainFrameMeta.Menu.format))
        {
            ArrayList<DataSourceConf> ar = new ArrayList<DataSourceConf>();
            ar.add(srcObjectConfig);
            ar.add(dstObjectConfig);
            selectFileFormatPane = new ActSelectDataSourceFormat(this, ar);
            selectFileFormatPane.show();
        } else if (actionCommand.equals(MainFrameMeta.Menu.convert))
        {

            ArrayList<DataSourceConf> ar = new ArrayList<DataSourceConf>(
                    Arrays.asList(srcObjectConfig, dstObjectConfig));
            ActConversionProcess convProcessPanel = new ActConversionProcess(this, ar);
            convProcessPanel.show();
        } else if (actionCommand.equals(MainFrameMeta.Menu.help))
        {
            ActHelpPanel actHelpPanel = new ActHelpPanel(this);
            actHelpPanel.show();
        } else if (actionCommand.equals(MainFrameMeta.Menu.about))
        {
            ActAboutPanel actAboutPanel = new ActAboutPanel(this);
            actAboutPanel.show();
        } else {
            String message = "Menu item '" + actionCommand + "' not implemented.";
            LOG.error(message);
            JOptionPane.showMessageDialog(this, message,
                                            "Error", JOptionPane.PLAIN_MESSAGE);
        }
        Toolkit.getDefaultToolkit().triggerGarbageCollection(this);
    }

    /**
     * Check configurations for nulls, arrange them, etc.
     * @param confsList - configurations to be processed.
     */
    private void refactorConfigurations(ArrayList<DataSourceConf> confsList) {
        CopyOnWriteArrayList<DataSourceConf> confs 
                                = new CopyOnWriteArrayList<DataSourceConf>();
        confs.addAll(confsList);
        
        srcObjectConfig = null ;
        dstObjectConfig = null ;

        for(DataSourceConf config : confs) {
            Side side = config.getSide();
            if(side == null) { side = Side.heath; }
            switch(side) {
            case source:
                srcObjectConfig = new DataSourceConf(config);
                    confsList.remove(config);
                break;
            case destination:
                dstObjectConfig =  new DataSourceConf(config);
                confsList.remove(config);
                break;
            }
        }

        int quantityOfWantedDataSources = 2 ;
        for(int idx=0 ;
                    idx < confs.size()
                    && idx < quantityOfWantedDataSources
                    && confs.size() > 0 ;
                        idx++) {
            Iterator<DataSourceConf> iter = confs.iterator() ;
            while(iter.hasNext()) {
                DataSourceConf conf = iter.next();
                if(srcObjectConfig == null) {
                    srcObjectConfig = conf;
                    confsList.remove(conf);
                } else if(dstObjectConfig == null) {
                    dstObjectConfig = conf;
                    confsList.remove(conf);
                }
                
            }
        }
        
        if(srcObjectConfig == null ) {
            srcObjectConfig = new DataSourceConf(null,null,null);
        }
        if(dstObjectConfig == null ) {
            dstObjectConfig = new DataSourceConf(null,null,null);
        }
        
        confsList = new ArrayList<DataSourceConf>();
        confsList.add(srcObjectConfig);
        confsList.add(dstObjectConfig);

    }

    /* (non-Javadoc)
     * @see charva.awt.event.WindowListener#windowClosing(charva.awt.event.WindowEvent)
     */
    @Override
    public void windowClosing(WindowEvent we) {
        String sourceName =  we.getSource().getClass().getName();
        if(sourceName.equals(ActSelectDataSourceFormat.class.getName()))
        {
            ArrayList<DataSourceConf> ar = selectFileFormatPane.getRequestedConvParam();
            for(DataSourceConf config : ar) {
                String hdr ;
                switch(config.getSide()) {
                case source:
                    hdr = MainFrameMeta.SRC_INFO_PANEL_HEADER
                            + " - " + config.getObjectFormat()
                            + " "
                            + MainFrameMeta.SRC_INFO_PANEL_HEADER_SUFFIX
                            + " <";
                    inFileNamePanel.setHeader(hdr);
                    inFileNamePanel.repaint();
                    break;
                case destination:
                    hdr = MainFrameMeta.DST_INFO_PANEL_HEADER
                            + " - " + config.getObjectFormat()
                            + " "
                            + MainFrameMeta.DST_INFO_PANEL_HEADER_SUFFIX
                            + " <";
                    outFileNamePanel.setHeader(hdr);
                    outFileNamePanel.repaint();
                    break;
                }
            }
        } else if(sourceName.equals(ActConversionProcess.class.getName()))
        {
            String msg = MainFrameMeta.Messages.conversionDone;
            JOptionPane.showMessageDialog(this,
                    msg, "> YPCnv <" , JOptionPane.PLAIN_MESSAGE);
        }
    }

    /* (non-Javadoc)
     * @see charva.awt.event.WindowListener#windowOpened(charva.awt.event.WindowEvent)
     */
    @Override
    public void windowOpened(WindowEvent arg0) {
        return;
    }
    
}
