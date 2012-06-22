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
package ypcnv.converter.menu;

import charva.awt.Point;



/**
 * Headers, static contents, constants, etc.
 */
public class UIMetaData {
    
    // XXX - magic numbers
    /** Common size of a dialog. */
    public static final Point dialogCommonSize = new Point(60,25);
    
    /** Data source format selection frame. */
    public class SelectFormat {
        public static final String header = "Select formats:";
        /** Formats names list header for UI. */
        public static final String listHeaderSrc="Source";
        /** Formats names list header for UI. */
        public static final String listHeaderDst="Destination";
        /** Selected item area label. */
        public static final String selectedAreaLabel = "Selected => ";
        /** Default name for preselected item. */
        public static final String defaultFormatName = "nothing";
        
        public static final String okButtonId = "OK" ;
        
        /** Hint panel content*/
        public static final String hint = "Use Tab and cursor keys to move.\n" +
        		"Use Enter key to select." ;
    }
    
    
    /* * */
    
    
    /** File select frame. */
    public class FileSelect {
        /** File selector dialog header. */
        public static final String header = "Select file:";
        /** Donor file selector dialog header. */
        public static final String headerDonor = "Select input file:";
        /** Acceptor file selector dialog header. */
        public static final String headerAcceptor = "Select output file:";
        /** OK and select button label. */
        public static final String selectButtonLabel="Select";
    }
    
    
    /* * */
    
    /** "Help" frame. */
    public class Help {
        /** HelpPanel header. */
        public static final String header = "Help YPCnv";
        public static final String contentAddress = "help.txt";
        
        public static final String FAILED_TO_READ_FROM = "Failed to read from '%s'.";
        public static final String okButtonId = "OK" ;
    }
    
    /** "About" frame. */
    public class About {
        /** AboutPanel header. */
        public static final String header = "About YPCnv";
        public static final String okButtonId = "OK" ;
        /** AboutPanel content. */
        public static final String content =
            "\n"
            + "Copyright 2011-2012 ASCH"
            + "\n\n"
            + "This is YPCnv - Yellow Pages Converter."
            + "\n\n"
            + "YPCnv is free software: you can redistribute it and/or modify"
            + "it under the terms of the GNU General Public License as published by"
            + "the Free Software Foundation, either version 3 of the License, or"
            + "(at your option) any later version."
            + "\n\n"
            + "YPCnv is distributed in the hope that it will be useful,"
            + "but WITHOUT ANY WARRANTY; without even the implied warranty of"
            + "MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the"
            + "GNU General Public License for more details."
            + "\n\n"
            + "You should have received a copy of the GNU General Public License"
            + "along with YPCnv.  If not, see <http://www.gnu.org/licenses/>."
            + "\n";
    }

}
