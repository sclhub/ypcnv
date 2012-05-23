/*
 *  Copyright 2011 ASCH
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

package local.asch.pbookConverter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import local.asch.outglook.Contact2k3;
import local.asch.outglook.exceptions.FileViewException;
import local.asch.outglook.fileview.Contact2k3VCardView;
import local.asch.outglook.fileview.Contact2k3XlsView;
import local.asch.outglook.logger.LoggerHelper;

import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

public class Main {
    /** Logger. */
    private static final Logger LOG = Logger
            .getLogger(Main.class);

    private static File getOutputDirectory(File candidatePath) {
        File outputPath = null ;
        if(candidatePath.isFile()) {
            outputPath = new File(candidatePath.getParent());
        }
        if(candidatePath.isDirectory()) {
            outputPath = new File(candidatePath.getPath());
        }
        return outputPath;
    }
    
    public Main(){
        LoggerHelper.initLogger(LOG);
    }
    
    public static void main(String[] args) throws FileViewException, IOException {
        if(args.length < 1) {
            throw new IllegalArgumentException("Command line argument can not be void, must be a file name.");
        }
        File inputFile = new File(args[0]);
        File outputPath = getOutputDirectory(inputFile);
        
        ArrayList<Contact2k3> contactList = new ArrayList<Contact2k3>();
        Contact2k3XlsView xlsView = null ;
        Contact2k3VCardView vCardView = new Contact2k3VCardView(contactList, outputPath);
        
        try {
            xlsView = new Contact2k3XlsView(contactList, inputFile);
        } catch (InvalidFormatException e) {
            LOG.error("Invalid format of input file.");
            e.printStackTrace();
            return ;
        }
        
        xlsView.getView();
        vCardView.setView();
    }
}