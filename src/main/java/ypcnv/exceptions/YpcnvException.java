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
 * Exception interface.
 */
public interface YpcnvException {

    /**
     * Get string representation of trace back.
     * @return String with trace back.
     */
    public String traceBack();

    /**
     * Get string representation of trace back, using line separator.
     * @return String with trace back.
     */
    public String traceBack(String lineSeparator);

}
