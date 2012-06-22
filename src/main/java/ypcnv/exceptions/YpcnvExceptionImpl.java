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
package ypcnv.exceptions;

/**
 * Abstract implementation.
 */
public abstract class YpcnvExceptionImpl extends Exception 
                                         implements YpcnvException {
    private static final long serialVersionUID = 6344235729123797972L;
    /** The exception which was caught. */
    private YpcnvException previousException = null;
    /** Identifier. */
    private String throwPointId;
    /** Descriptive message. */
    private String message;
    /** Desired line separator. */
    private String lineSeparator = System.getProperty("line.separator");

    /**
     * @param prevException - previous exception.
     * @param throwPointIdText - descriptive identifier of a point where exception was threw.  
     * @param message - descriptive message text.
     */
    public YpcnvExceptionImpl(YpcnvException prevException,
            String throwPointIdText, String message) {
        this.previousException = prevException;
        this.throwPointId = throwPointIdText;
        this.message = message;
    }

    
    /**
     * Get string representation of trace back.
     * @return String with trace back.
     */
    public String traceBack() {
        return traceBack(lineSeparator);
    }

    /**
     * Get string representation of trace back, using line separator.
     * @return String with trace back.
     */
    public String traceBack(String lineSeparator) {
        this.lineSeparator = lineSeparator;
        String traceText = "Invoked order from top to bottom" + lineSeparator;
        int level = 0;

        YpcnvException e = this;
        while (e != null) {
            level++;
            traceText += "===> Invoked at level " + level + "." + lineSeparator;
            traceText += "     Identifier: " + this.throwPointId + lineSeparator;
            traceText += "     Message is: " + this.message + lineSeparator;
            e = this.previousException;
        }
        return traceText;
    }

}
