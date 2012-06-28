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

/**
 * 
 */
package ypcnv.converter.menu;

import ypcnv.converter.mainFrame.MainFrame;
import ypcnv.converter.mainFrame.MainFrameMeta;
import charvax.swing.JMenu;
import charvax.swing.JMenuBar;
import charvax.swing.JMenuItem;

/**
 * Construct main menu in main frame. 
 */
public class ConstructMainMenu {
    
    /**
     * Builder of menu.
     * @param owner - Frame object, whom menu belong to.
     * @return Menu bar object.
     */
    public static JMenuBar construct(MainFrame owner) {
        /// Main menu bar.
        JMenuBar menubar = new JMenuBar();

        /* * */

        JMenu jMenuFile = new JMenu(MainFrameMeta.Menu.file);
        jMenuFile.setMnemonic(MainFrameMeta.Menu.fileMn);

        JMenuItem jMenuItemInputFileChooser
                    = new JMenuItem(MainFrameMeta.Menu.fileSelectIn,
                                    MainFrameMeta.Menu.fileSelectInMn);
        jMenuItemInputFileChooser.addActionListener(owner);
        jMenuFile.add(jMenuItemInputFileChooser);

        JMenuItem jMenuItemOutputFileChooser
                    = new JMenuItem(MainFrameMeta.Menu.fileSelectOut,
                                    MainFrameMeta.Menu.fileSelectOutMn);
        jMenuItemOutputFileChooser.addActionListener(owner);
        jMenuFile.add(jMenuItemOutputFileChooser);

        jMenuFile.addSeparator();

        JMenuItem jMenuItemFileExit = new JMenuItem(MainFrameMeta.Menu.exit,
                                                    MainFrameMeta.Menu.exitMn);
        jMenuItemFileExit.addActionListener(owner);
        jMenuFile.add(jMenuItemFileExit);

        /* * */

        JMenu jMenuConfig = new JMenu(MainFrameMeta.Menu.configure);
        jMenuConfig.setMnemonic(MainFrameMeta.Menu.configureMn);

        JMenuItem jMenuItemSelectFormat = new JMenuItem(MainFrameMeta.Menu.format,
                                                        MainFrameMeta.Menu.formatMn);
        jMenuItemSelectFormat.addActionListener(owner);
        jMenuConfig.add(jMenuItemSelectFormat);

        /* * */

        JMenu jMenuActions = new JMenu(MainFrameMeta.Menu.actions);
        jMenuConfig.setMnemonic(MainFrameMeta.Menu.actionsMn);

        JMenuItem jMenuItemConvert = new JMenuItem(MainFrameMeta.Menu.convert,
                                                    MainFrameMeta.Menu.convertMn);
        jMenuItemConvert.addActionListener(owner);
        jMenuActions.add(jMenuItemConvert);

        /* * */
        
        JMenu jMenuInfo = new JMenu(MainFrameMeta.Menu.info);
        jMenuInfo.setMnemonic(MainFrameMeta.Menu.infoMn);

        JMenuItem jMenuItemHelp = new JMenuItem(MainFrameMeta.Menu.help,
                                                MainFrameMeta.Menu.helpMn);
        jMenuItemHelp.addActionListener(owner);
        jMenuInfo.add(jMenuItemHelp);

        JMenuItem jMenuItemAbout = new JMenuItem(MainFrameMeta.Menu.about,
                                                MainFrameMeta.Menu.aboutMn);
        jMenuItemAbout.addActionListener(owner);
        jMenuInfo.add(jMenuItemAbout);

        menubar.add(jMenuFile);
        menubar.add(jMenuConfig);
        menubar.add(jMenuActions);
        menubar.add(jMenuInfo);

        return menubar ;
    }

}
