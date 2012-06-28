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

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ypcnv.converter.conf.DataSourceConf;
import charva.awt.Component;
import charvax.swing.JFileChooser;

/** File selection dialog. */
public class ActSelectFile {
    
    /** Logger. */
    @SuppressWarnings("unused")
    private static final Log LOG = LogFactory.getLog(ActSelectFile.class);

    /**
     * Dialog launcher.
     * @param owner - parent component.
     * @param dialogTitle - header.
     * @param fileFilterExpression - file name mask, filter - regular expression.
     * @return Selected file, or <b>null</b> if nothing selected. 
     */
    public static File selectFileDialog(Component owner,
                                String dialogTitle,
                                final String fileFilterExpression,
                                final DataSourceConf preSelectFrom) {
        File selectedFile = null ;
        if(preSelectFrom == null) {
            selectedFile = new File(System.getProperty("user.dir"));
        } else if(preSelectFrom.getObjectAddress() != null) {
            String preSelectName =  preSelectFrom.getObjectAddress().toString();
            if(preSelectName != null) {            
                selectedFile = new File(preSelectName);
            } else {
                selectedFile = new File(System.getProperty("user.dir"));
            }
    
            if(selectedFile != null 
                    && ! selectedFile.exists()) {
    
                String upName = selectedFile.getParent();
                
                if(upName != null && new File(upName).exists()) {
                    selectedFile = new File(upName);
                } else {
                    selectedFile = new File(System.getProperty("user.dir"));
                }
            }
        } else {
            selectedFile = new File(System.getProperty("user.dir"));
        }
        
        if(!selectedFile.isDirectory()) {
            selectedFile = new File(selectedFile.getParent());
        }
        
        JFileChooser filenameSelector = new JFileChooser(selectedFile);
        filenameSelector.setDialogTitle(dialogTitle);
        filenameSelector.setForeground(UIMetaData.colorFG);
        filenameSelector.setBackground(UIMetaData.colorBG);
        
        /** Class of files to be allowed to be selected. */
        int targetObjectFamily = JFileChooser.FILES_AND_DIRECTORIES;
        filenameSelector.setFileSelectionMode(targetObjectFamily);

        //  Uncomment this section of code to apply a FileFilter that masks out all
        // files whose names do not end with ".java".
//        charvax.swing.filechooser.FileFilter filenameFilter = 
//            new charvax.swing.filechooser.FileFilter() {
//            public boolean accept(File fileName) {
//            String pathname = fileName.getAbsolutePath();
//            return (pathname.endsWith(fileFilterExpression));
//            }
//        };
//        filenameSelector.setFileFilter(filenameFilter);

        if (filenameSelector.showDialog(owner, UIMetaData.FileSelect.selectButtonLabel)
                    ==
                JFileChooser.APPROVE_OPTION) {
            selectedFile = new File(filenameSelector.getSelectedFile().getAbsolutePath());
        } else {
            selectedFile = null ;
        }
        
        return selectedFile ;
    }

}
