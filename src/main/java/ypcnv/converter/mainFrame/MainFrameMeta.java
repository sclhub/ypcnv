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
package ypcnv.converter.mainFrame;

/**
 *
 */
public final class MainFrameMeta {

    /** Header for data donor configuration indicator. */
    public static final String SRC_INFO_PANEL_HEADER = "> source";
    /** Header for data acceptor configuration indicator. */
    public static final String DST_INFO_PANEL_HEADER = "> destination";
    
    /// Message panel initial content.
    public static final String messagePanelContent = "...";
    
    /// Hint panel initial content.
    public static final String hintPanelText=
            "Use LEFT and RIGHT cursor keys to select a menu."
            + " Use ENTER to invoke a menu or menu-item,"
            + " underlined \"mnemonic key\" to invoke a menu.)"
            + " Use BACKSPACE or ESC to dismiss a menu."
            + " Tab - switch frames.";

    /** Menu items. */
    public static class Menu {
        public static final String file = "File" ;
        public static final Character fileMn = 'F' ;
        public static final String fileSelectIn = "Select input file..." ;
        public static final Character fileSelectInMn = 'I' ;
        public static final String fileSelectOut = "Select output file..." ;
        public static final Character fileSelectOutMn = 'O' ;
        public static final String exit = "Exit" ;
        public static final Character exitMn = 'X' ;
        
        
        
        
        public static final String configure = "Configure" ;
        public static final Character configureMn = 'C' ;
        public static final String format = "Format" ;
        public static final Character formatMn = 'F' ;
        

        
        
        public static final String actions = "Actions" ;
        public static final Character actionsMn = 'A' ;
        public static final String convert = "Convert" ;
        public static final Character convertMn = 'C' ;
        
        
        
        
        public static final String info = "Info" ;
        public static final Character infoMn = 'I' ;
        public static final String help = "Help" ;
        public static final Character helpMn = 'H' ;
        public static final String about = "About" ;
        public static final Character aboutMn = 'A' ;

    }
    
    public static class Events {
        public static final String dataFormatsRefreshed = "data source formats refreshed";
        //public static final String dataDonorAddressRefreshed = "data donor address refreshed";
        //public static final String dataAcceptorAddressRefreshed = "data acceptor address refreshed";
    }
}
