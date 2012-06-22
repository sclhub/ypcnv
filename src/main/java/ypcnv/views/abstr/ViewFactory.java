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
package ypcnv.views.abstr;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ypcnv.converter.conf.DataSourceConf;
import ypcnv.views.ifHi.DataSource;
import ypcnv.views.impl.FileVCF;
import ypcnv.views.impl.FileXLS;

/**
 * Data source objects factory.
 */
public class ViewFactory {

    /** Logger. */
    @SuppressWarnings("unused")
    private static final Log LOG = LogFactory.getLog(ViewFactory.class);
    
    /**
     * Create new data source object using a configuration.
     * @param params - configuration of data source to be build.
     * @return New data source object.
     */
    public static DataSource get(DataSourceConf params) {
        if(params.getObjectFormat().equals("")
                || params.getObjectFormat() == null) {
            return null ;
        }
        
        switch(params.getObjectFormat()) {
        case XLS:
            return new FileXLS(params);
        case VCF :
            return new FileVCF(params);
        }
        return null;
    }

}
