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
package ypcnv.views.impl;

/**
 * Meta data - constants, etc.
 */
public final class FileXLSMeta {

    /** Width of e-table's work sheet. */
    public static final int NUMBER_OF_DATA_FIELDS = 92;

    /* Error messages. */
    
    public static final String ERR_MESSAGE_FILE_ACCESS = "Failed with file '%s'.";
    public static final String ERR_MESSAGE_FILE_SAVE = "Failed with file '%s'.";
    public static final String ERR_MESSAGE_NO_KEY_IN_MAP = "There is no such a key '%s' in map." ;
    public static final String ERR_MESSAGE_WORKBOOK_WRONG_FORMAT = "Failed with file '%s'.";
    public static final String ERR_MESSAGE_WRONG_FIELD_QUANTITY = "In workbook file '%s' number of data fields in header row does not equals to expected value '%d'.";
    public static final String ERR_MESSAGE_WRONG_FIELD_NAME = "In workbook file '%s' in a row with data fields headers/names found unexpected name '%s'.";
    public static final String ERR_MESSAGE_NO_HEADER_ROW = "Failed to locate on sheet num. '%d' row number '%d' of cells with data columns headers. Workbook from file '%s'.";
    public static final String ERR_MESSAGE_FAILED_TO_PULL_FROM_STORAGE = "Failed to pull data from file with e-table.";

}
