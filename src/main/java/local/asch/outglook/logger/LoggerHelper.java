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

package local.asch.outglook.logger;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

public class LoggerHelper {

    private static final String CONSOLE_LOG_APPENDER_NAME = "singleMiscLoggerForConsole" ;

	public static void initLogger() {
		Logger Log = Logger.getRootLogger();
        if(!Log.getAllAppenders().hasMoreElements()) {
        	Log.setLevel(Level.INFO);
            ConsoleAppender ca = new ConsoleAppender(new PatternLayout("%d{ISO8601} [%5p %c{1}:%L] %m%n"));
            ca.setTarget("System.out");
        	Log.addAppender(ca);
        }
	}

	public static void initLogger(Logger log) {
        ConsoleAppender ca = new ConsoleAppender(new PatternLayout("%d{ISO8601} [%5p %c{1}:%L] %m%n"));
        ca.setName(CONSOLE_LOG_APPENDER_NAME);
        //ca.setTarget("System.out");
        ca.setTarget("System.err");
        if( log.getAppender(ca.getName()) == null ) {
            log.setLevel(Level.INFO);
            //log.setAdditivity(new Boolean("false"));
            log.addAppender(ca);
        }
	}

}
