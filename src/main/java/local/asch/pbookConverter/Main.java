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

package local.asch.pbookConverter;

import gnu.getopt.Getopt;
import gnu.getopt.LongOpt;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import local.asch.outglook.Contact2k3;
import local.asch.outglook.exceptions.FileViewException;
import local.asch.outglook.fileview.Contact2k3VCardView;
import local.asch.outglook.fileview.Contact2k3XlsView;
import local.asch.outglook.logger.LoggerHelper;

import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

public class Main {

    /* Synonyms for GNU-GetOpt internal constants. */
    static final int CLI_ARG_IS_OPTION = 0 ;
    static final int CLI_ARG_IS_NOT_AN_OPTION = 1 ;

    /*
     * CLI options names and Id.
     */

    static final int OPT_IDX_HELP = 0 ;
    static final int OPT_IDX_INPUTFILE = 1 ;
    
    static final Map<Integer, String> OPTIONS_NAMES_MAP = Collections
            .unmodifiableMap(new HashMap<Integer, String>() {
                private static final long serialVersionUID = -1986848358300263787L;
                {
                    put(OPT_IDX_HELP, "help");
                    put(OPT_IDX_INPUTFILE, "if");
                }
            });
    
    /** Logger. */
    private static final Logger LOG = Logger.getLogger(Main.class);

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
    
    static {
        LoggerHelper.initLogger(LOG);
    }
    
    public static void main(String[] args) throws FileViewException, IOException {
        if(args.length < 1) {
            throw new IllegalArgumentException("Command line argument can not be void, must be a file name.");
        }

        int detectorFeedback;
        String theOptionArgument;
        
        LongOpt[] longopts = new LongOpt[OPTIONS_NAMES_MAP.size()];

        StringBuffer strBuffer = new StringBuffer();
        
        longopts[OPT_IDX_HELP] = new LongOpt(
                                        OPTIONS_NAMES_MAP.get(OPT_IDX_HELP),
                                        LongOpt.NO_ARGUMENT, null, 'h');
        longopts[OPT_IDX_INPUTFILE] = new LongOpt(
                                        OPTIONS_NAMES_MAP.get(OPT_IDX_INPUTFILE),
                                        LongOpt.REQUIRED_ARGUMENT, strBuffer, 'i');

        Getopt getOption = new Getopt("local.asch.pbookConverter.Main", args, "", longopts);
        getOption.setOpterr(false); // Turn off error messages in GNU-GetOpt.

        while ((detectorFeedback = getOption.getopt()) != -1)
            switch (detectorFeedback) {
            case CLI_ARG_IS_OPTION:
                theOptionArgument = getOption.getOptarg();
                
                char shadowShortName= (char) (new Integer(strBuffer.toString())).intValue() ;
                String argumentOfOption = ((theOptionArgument != null) ? theOptionArgument : "null");
                String optionName = longopts[getOption.getLongind()].getName() ;
                LOG.info("Found option: '" + optionName + "/" + shadowShortName + "' = '" + argumentOfOption + "'.");

                // XXX
                if(optionName.compareTo(OPTIONS_NAMES_MAP.get(OPT_IDX_INPUTFILE)) == 0 ) {
                    processInputFile(argumentOfOption);
                }
                
                break;
            case CLI_ARG_IS_NOT_AN_OPTION:
                String messageNotAnOption = "Found CLI argument which is not an defined option." ; 
                LOG.info(messageNotAnOption);
                throw new IllegalArgumentException(messageNotAnOption);
            case ':':
                LOG.error("Need additional arguments for option"
                        + (char) getOption.getOptopt());
                break;
            case '?':
                String messageInvalidOption = "Invalid option '" + (char) getOption.getOptopt() + "'.";
                LOG.error(messageInvalidOption);
                throw new IllegalArgumentException(messageInvalidOption);
            default:
                String messageUnexpectedValue = "By getopt() returned unexpected value '" + detectorFeedback + "'.";
                LOG.error(messageUnexpectedValue);
                throw new IllegalArgumentException(messageUnexpectedValue);
            }
        }

    private static void processInputFile(String inputFileName) throws IOException, FileViewException {
        File inputFile = new File(inputFileName);
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
        
        System.out.println("\nSee file/files in '" + vCardView.getViewContainerName() + "'.");
    }
}