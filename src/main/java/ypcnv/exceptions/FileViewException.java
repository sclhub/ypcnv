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

package ypcnv.exceptions;

/**
 * An exception class for events concerning file representations of a data
 * sources implementations.
 */
public class FileViewException extends YpcnvExceptionImpl {
    private static final long serialVersionUID = -7737405338894428508L;

    /**
     * @param prevException - previous exception.
     * @param throwPointIdText - descriptive identifier of a point where exception was threw.  
     * @param message - descriptive message text.
     */
    public FileViewException(YpcnvException prevException,
            String throwPointIdText, String message) {
        super(prevException, throwPointIdText, message);
    }
}
