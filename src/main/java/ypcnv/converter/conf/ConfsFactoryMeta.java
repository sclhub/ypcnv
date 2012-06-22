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
package ypcnv.converter.conf;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Meta data - constants, etc.
 */
public class ConfsFactoryMeta {
    
    
    /** Logger. */
    @SuppressWarnings("unused")
    private static final Log LOG = LogFactory.getLog(ConfsFactoryMeta.class);

    /** Synonym for GNU-GetOpt internal constants. */
    static final int CLI_ARG_IS_OPTION = 0 ;
    /** Synonym for GNU-GetOpt internal constants. */
    static final int CLI_ARG_IS_NOT_AN_OPTION = 1 ;

    /** CLI option name numeric Id. */
    static final int OPT_IDX_HELP = 0 ;
    /** CLI option name numeric Id. */
    static final int OPT_IDX_INPUTFILE = 1 ;
    /** CLI option name numeric Id. */
    static final int OPT_IDX_OUTPUTFILE = 2 ;
    /** CLI option name numeric Id. */
    static final int OPT_IDX_SRC_FORMAT = 3 ;
    /** CLI option name numeric Id. */
    static final int OPT_IDX_DST_FORMAT = 4 ;
    
    /**
     * Mapping between internal IDs (key in the map) and CLI options names
     * (value in the map).
     */
    static final Map<Integer, String> OPTIONS_NAMES_MAP = Collections
            .unmodifiableMap(new HashMap<Integer, String>() {
                private static final long serialVersionUID = -2239936201872922446L;
                {
                    put(OPT_IDX_HELP, "help");
                    put(OPT_IDX_INPUTFILE, "if");
                    put(OPT_IDX_OUTPUTFILE, "of");
                    put(OPT_IDX_SRC_FORMAT, "from");
                    put(OPT_IDX_DST_FORMAT, "to");
                }
            });


}
