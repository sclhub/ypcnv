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
import ypcnv.converter.main.Converter;
import ypcnv.converter.menu.AboutPanel;
import ypcnv.converter.menu.HelpPanel;
import ypcnv.converter.menu.SelectDataSourceFormat;
import ypcnv.converter.menu.SelectFile;
import ypcnv.converter.menu.UIMetaData;
import ypcnv.errorCodes.ErrorCodes;
import ypcnv.views.abstr.DataFormatID;
import ypcnv.views.abstr.Side;
import charva.awt.Container;
import charva.awt.GridBagConstraints;
import charva.awt.GridBagLayout;
import charva.awt.Toolkit;
import charva.awt.event.ActionEvent;
import charva.awt.event.ActionListener;
import charvax.swing.JFrame;
import charvax.swing.JMenu;
import charvax.swing.JMenuBar;
import charvax.swing.JMenuItem;
import charvax.swing.JOptionPane;


/**
 * Main frame of visual UI.
 */
public class MainFrame extends JFrame
                       implements ActionListener {

    
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

    /* Miscellaneous panels to be called. */

    /** File selection dialog. */
    private SelectDataSourceFormat selectFileFormatPane = null ;

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

    /** Setup main farme. */
    private void initMainFrame() {
        setForeground(UIMetaData.colorFG);
        setBackground(UIMetaData.colorBG);
        Container contentPane = getContentPane();
        contentPane.setLayout(new GridBagLayout());

        // XXX - menu is a UI object. Why not to extract into separate class?
        // XXX - package named "menu" contains several panels.
        /// Main menu bar.
        JMenuBar menubar = new JMenuBar();

        /* * */

        JMenu jMenuFile = new JMenu(MainFrameMeta.Menu.file);
        jMenuFile.setMnemonic(MainFrameMeta.Menu.fileMn);

        JMenuItem jMenuItemInputFileChooser
                    = new JMenuItem(MainFrameMeta.Menu.fileSelectIn,
                                    MainFrameMeta.Menu.fileSelectInMn);
        jMenuItemInputFileChooser.addActionListener(this);
        jMenuFile.add(jMenuItemInputFileChooser);

        JMenuItem jMenuItemOutputFileChooser
                    = new JMenuItem(MainFrameMeta.Menu.fileSelectOut,
                                    MainFrameMeta.Menu.fileSelectOutMn);
        jMenuItemOutputFileChooser.addActionListener(this);
        jMenuFile.add(jMenuItemOutputFileChooser);

        jMenuFile.addSeparator();

        JMenuItem jMenuItemFileExit = new JMenuItem(MainFrameMeta.Menu.exit,
                                                    MainFrameMeta.Menu.exitMn);
        jMenuItemFileExit.addActionListener(this);
        jMenuFile.add(jMenuItemFileExit);

        /* * */

        JMenu jMenuConfig = new JMenu(MainFrameMeta.Menu.configure);
        jMenuConfig.setMnemonic(MainFrameMeta.Menu.configureMn);

        JMenuItem jMenuItemSelectFormat = new JMenuItem(MainFrameMeta.Menu.format,
                                                        MainFrameMeta.Menu.formatMn);
        jMenuItemSelectFormat.addActionListener(this);
        jMenuConfig.add(jMenuItemSelectFormat);

        /* * */

        JMenu jMenuActions = new JMenu(MainFrameMeta.Menu.actions);
        jMenuConfig.setMnemonic(MainFrameMeta.Menu.actionsMn);

        JMenuItem jMenuItemConvert = new JMenuItem(MainFrameMeta.Menu.convert,
                                                    MainFrameMeta.Menu.convertMn);
        jMenuItemConvert.addActionListener(this);
        jMenuActions.add(jMenuItemConvert);

        /* * */
        
        JMenu jMenuInfo = new JMenu(MainFrameMeta.Menu.info);
        jMenuInfo.setMnemonic(MainFrameMeta.Menu.infoMn);

        JMenuItem jMenuItemHelp = new JMenuItem(MainFrameMeta.Menu.help,
                                                MainFrameMeta.Menu.helpMn);
        jMenuItemHelp.addActionListener(this);
        jMenuInfo.add(jMenuItemHelp);

        JMenuItem jMenuItemAbout = new JMenuItem(MainFrameMeta.Menu.about,
                                                MainFrameMeta.Menu.aboutMn);
        jMenuItemAbout.addActionListener(this);
        jMenuInfo.add(jMenuItemAbout);

        menubar.add(jMenuFile);
        menubar.add(jMenuConfig);
        menubar.add(jMenuActions);
        menubar.add(jMenuInfo);

        setJMenuBar(menubar);

        Object donorAddress = srcObjectConfig.getObjectAddress() ;
        Object acceptorAddress = dstObjectConfig.getObjectAddress() ;
        if(donorAddress == null) {
            donorAddress = "";
        }
        if(acceptorAddress == null) {
            acceptorAddress = "";
        }
        
        inFileNamePanel = new ChoosenFilePanel(MainFrameMeta.SRC_INFO_PANEL_HEADER
                               +" <",
                               donorAddress.toString());
        outFileNamePanel = new ChoosenFilePanel(MainFrameMeta.DST_INFO_PANEL_HEADER
                                + " <",
                                acceptorAddress.toString());
        messagePanel = new MessagePanel(MainFrameMeta.messagePanelContent);
        hintPanel = new HintPanel(MainFrameMeta.hintPanelText);

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 5;
        gbc.gridheight = 1;
        contentPane.add(inFileNamePanel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 5;
        gbc.gridheight = 1;
        contentPane.add(outFileNamePanel, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 5;
        gbc.gridheight = 1;
        contentPane.add(messagePanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 5;
        gbc.gridheight = 1;
        contentPane.add(hintPanel, gbc);

        setLocation(0, 0);
        setSize(Toolkit.getDefaultToolkit().getScreenColumns(), Toolkit
                .getDefaultToolkit().getScreenRows());

        validate();

    }
    
    // XXX - split into separated methods or classes?
    /** Main frame's action and event listener. */
    @Override
    public void actionPerformed(ActionEvent ae_) {
        String actionCommand = ae_.getActionCommand();
        if (actionCommand.equals(MainFrameMeta.Menu.exit))
        {
            LOG.debug("On exit have formats: source "
                        + srcObjectConfig.getObjectFormat()
                        + ", destination "
                        + dstObjectConfig.getObjectFormat());
            System.gc();    // So that Heap/CPU Profiling Tool reports only live objects.
            System.exit(ErrorCodes.OK.get());
        } else if (actionCommand.equals(MainFrameMeta.Menu.fileSelectIn))
        {
            File newAddress = SelectFile.selectFileDialog(
                                    this, 
                                    UIMetaData.FileSelect.headerDonor,
                                    null,
                                    srcObjectConfig);
            LOG.debug("Choosen input file (null is Cancel pressed): " + newAddress);
            if(newAddress != null) {
                srcObjectConfig.setObjectAddress(newAddress);
                inFileNamePanel.setContent(srcObjectConfig.getObjectAddress().toString());
                inFileNamePanel.repaint();
            }
        } else if (actionCommand.equals(MainFrameMeta.Menu.fileSelectOut))
        {
            File newAddress = SelectFile.selectFileDialog(
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
            LOG.debug("Going with source and destination: "
                        + srcObjectConfig
                        + " ; "
                        + dstObjectConfig);
            ArrayList<DataSourceConf> ar = new ArrayList<DataSourceConf>();
            ar.add(srcObjectConfig);
            ar.add(dstObjectConfig);
            selectFileFormatPane = new SelectDataSourceFormat(this, ar);
            selectFileFormatPane.show();
            LOG.debug("After format selection have parameters: "
                    + srcObjectConfig.toString()
                    + ", destination "
                    + dstObjectConfig.toString());
        } else if (actionCommand.equals(MainFrameMeta.Menu.convert))
        {

            ArrayList<DataSourceConf> ar = new ArrayList<DataSourceConf>(
                    Arrays.asList(srcObjectConfig, dstObjectConfig));
            for(DataSourceConf config : ar) {
                if((config.getSide() == Side.source
                        && config.getObjectFormat() != DataFormatID.XLS)
                    ||
                    (config.getSide() == Side.destination
                        && config.getObjectFormat() != DataFormatID.VCF))
                {
                    String msg = "Terra incognita in front. Stopping for debug.";
                    LOG.info(msg);
                    JOptionPane.showMessageDialog(this,
                            msg, "> YPCnv <" , JOptionPane.PLAIN_MESSAGE);
                    Toolkit.getDefaultToolkit().triggerGarbageCollection(this);
                    return ;
                }
            }

            Boolean isConfComplete = false;
            Boolean haveSource = false;
            Boolean haveDestination = false;
            
            Iterator<DataSourceConf> confsListIterator = ar.iterator();
            while(confsListIterator.hasNext()) {
                DataSourceConf config = confsListIterator.next();
                if(config.isComplete()) {
                    if (config.getSide() == Side.source ) {
                        haveSource = true;
                    }
                    if (config.getSide() == Side.destination ) {
                        haveDestination = true;
                    }
                }
            }

            if(haveSource && haveDestination) {
                isConfComplete = true ;
            }
            
            if(isConfComplete) {
                Converter cnv = new Converter(ar);
                try {
                    cnv.processConversion();
                } catch (Exception e) {
                    LOG.error("Failed to convert. Parameters were: "
                            + srcObjectConfig.toString()
                            + ", destination "
                            + dstObjectConfig.toString());
                    e.printStackTrace();
                }
                String msg = "Conversion done.";
                LOG.info(msg);
                JOptionPane.showMessageDialog(this,
                        msg, "> YPCnv <" , JOptionPane.PLAIN_MESSAGE);
                //Toolkit.getDefaultToolkit().triggerGarbageCollection(this);
                System.gc();
            }
        } else if (actionCommand.equals(MainFrameMeta.Menu.help))
        {
            LOG.debug("Help panel start.");
            HelpPanel helpPanel = new HelpPanel(this);
            helpPanel.show();
            LOG.debug("Help panel done.");
        } else if (actionCommand.equals(MainFrameMeta.Menu.about))
        {
            AboutPanel aboutPanel = new AboutPanel(this);
            aboutPanel.show();
        } else if (actionCommand.equals(MainFrameMeta.Events.dataFormatsRefreshed))
        {
            ArrayList<DataSourceConf> ar = selectFileFormatPane.getRequestedConvParam();
            for(DataSourceConf config : ar) {
                String hdr ;
                switch(config.getSide()) {
                case source:
                    hdr = MainFrameMeta.SRC_INFO_PANEL_HEADER
                            + " - " + config.getObjectFormat() + " format <";
                    inFileNamePanel.setHeader(hdr);
                    inFileNamePanel.repaint();
                    break;
                case destination:
                    hdr = MainFrameMeta.DST_INFO_PANEL_HEADER
                            + " - " + config.getObjectFormat() + " format <";
                    outFileNamePanel.setHeader(hdr);
                    outFileNamePanel.repaint();
                    break;
                }
            }
//        } else if (actionCommand.equals(MainFrameMeta.Events.dataDonorAddressRefreshed))
//        {
//            inFileNamePanel.setContent(srcObjectConfig.getObjectAddress().toString());
//        } else if (actionCommand.equals(MainFrameMeta.Events.dataAcceptorAddressRefreshed))
//        {
//            outFileNamePanel.setContent(dstObjectConfig.getObjectAddress().toString());
        } else {
            LOG.error("Menu item '" + actionCommand + "' not implemented.");
            JOptionPane.showMessageDialog(this,
                    "Menu item '" + actionCommand + "' not implemented.",
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
}
