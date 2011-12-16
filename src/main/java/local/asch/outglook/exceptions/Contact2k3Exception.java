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

package local.asch.outglook.exceptions;

/**
 * @version 2011-10-08_19-28
 */
public class Contact2k3Exception extends Exception {
    private static final long serialVersionUID = 2267109618774705641L;
    /** The exception which was caught. */
    private Contact2k3Exception previousException = null;
    /** Identifier. */
    private String throwPointId;
    /** Descriptive message. */
    private String message;
    /** Desired line separator. */
    private String lineSeparator = "\n";
    
    /**
     * @param prevException - previous exception.
     * @param throwPointIdText - descriptive identifier of a point where exception was threw.  
     * @param message - descriptive message text.
     */
    public Contact2k3Exception(Contact2k3Exception prevException,
            String throwPointIdText, String message) {
        this.previousException = prevException;
        this.throwPointId = throwPointIdText;
        this.message = message;
    }

    public String traceBack() {
        return traceBack(lineSeparator);
    }

    public String traceBack(String lineSeparator) {
        this.lineSeparator = lineSeparator;
        Contact2k3Exception e = this;
        String traceText = insertLineSeparator("Invoked order from top to bottom");
        int level = 0;

        while (e != null) {
            level++;
            traceText += insertLineSeparator("===> Invoked at level " + level + ".");
            traceText += insertLineSeparator("     Identifier: " + e.throwPointId);
            traceText += insertLineSeparator("     Message is: " + e.message);
            e = e.previousException;
        }
        return traceText;
    }

    private String insertLineSeparator(String str) {
        return str + lineSeparator;
    }

}
