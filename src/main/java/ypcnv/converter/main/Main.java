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
 * \brief Entrance point for YPCnv.
 * 
 */

package ypcnv.converter.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Appender;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ypcnv.converter.conf.ConfsFactory;
import ypcnv.converter.conf.DataSourceConf;
import ypcnv.converter.mainFrame.MainFrame;
import ypcnv.errorCodes.ErrorCodes;
import ypcnv.exceptions.FileViewException;
import ypcnv.views.abstr.Side;

/**
 * YPCnv - welcome.
 */
public class Main {

    /** Logger. */
    private static final Log LOG = LogFactory.getLog(Main.class);


    public static void main(String[] args) throws FileViewException,
            IOException, IllegalArgumentException, IllegalAccessException {
        
        LOG.debug("========> Application launch. <========");
        LOG.debug("In case of failures also examine stderr, exceptions output is there.");

        /*
         * if(args.length < 1) { throw new
         * IllegalArgumentException("Command line argument can not" +
         * " be void, must be a file name."); }
         */
        
        // Get data from CLI arguments.
        ArrayList<DataSourceConf> confsList = (ArrayList<DataSourceConf>) ConfsFactory.build(args);

        Boolean isConfComplete = false;
        Boolean haveSource = false;
        Boolean haveDestination = false;
        
        Iterator<DataSourceConf> confsListIterator = confsList.iterator();
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
        
        // Already have all the staff, or need launch UI and setup some more? 
        if(isConfComplete) {
            Converter cnv = new Converter(confsList);
            try {
                cnv.processConversion();
            } catch (Exception e) {
                LOG.error("Failed to convert. Parameters were: "
                        + confsList.toString());
                e.printStackTrace();
                System.gc();
                System.exit(ErrorCodes.ErrorMisc.get());
            }
            System.gc();     // So that Heap/CPU Profiling Tool reports only live objects.
            System.exit(ErrorCodes.OK.get());
        } else {
            terminateConsoleLogging();
            System.setProperty("charva.color","");
            MainFrame mainFrame = new MainFrame(confsList);
            mainFrame.show();
        }
        
    }

    /** Stopper of logging into console. Useful when UI is launched. */
    private static void terminateConsoleLogging() {
        //LOG.warn("Stopping logging into console.");
        @SuppressWarnings("unchecked")
        Enumeration<Logger> loggers = LogManager.getCurrentLoggers();
        if(loggers == null ) return;
        List<Logger> loggersList = Collections.list(loggers);
        if(loggersList == null ) return;

        Iterator<Logger> loggersListIterator = loggersList.iterator();
        while(loggersListIterator.hasNext()) {
            Logger logger = loggersListIterator.next();
            if(logger != null){
                Appender appender = logger.getAppender("LOGCONSOLE");
                if(appender != null) {
                    logger.removeAppender("LOGCONSOLE");
                }
            }
        }
    }

}