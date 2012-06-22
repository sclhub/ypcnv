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

import ypcnv.views.abstr.DataFormatID;
import ypcnv.views.abstr.Side;

/**
 * Configuration of a data source object.
 */
public class DataSourceConf {
    /** Data source format Id. */
    private DataFormatID objectFormat = null ;
    /** Data source address. */
    private Object objectAddress = null ;
    /** Space and direction denotation. */
    private Side side = null ;
    
    /**
     * @param format - data source format Id.
     * @param address - data source address.
     * @param side - space and direction denotation.
     */
    public DataSourceConf(DataFormatID format, Object address, Side side) {
        this.objectFormat = format ;
        this.objectAddress = address ;
        this.side = side ;
    }

    public DataSourceConf(DataSourceConf inst) {
        this.objectFormat = inst.getObjectFormat() ;
        this.objectAddress = inst.getObjectAddress() ;
        this.side = inst.getSide() ;
    }
    /**
     * Method to verify that configuration is full and ready to be processed.
     * @return <b>true</b> if configuration is OK, <b>false</b> - if not.
     */
    public Boolean isComplete(){
        if(objectFormat == null
                || objectAddress == null
                || side == null 
                || objectFormat == DataFormatID.none
                || side == Side.heath) {
           return false; 
        }
        return true;
    }
    
    public DataFormatID getObjectFormat() {
        return objectFormat;
    }

    public void setObjectFormat(DataFormatID objectFormat) {
        this.objectFormat = objectFormat;
    }

    public Object getObjectAddress() {
        return objectAddress;
    }

    public void setObjectAddress(Object objectAddress) {
        this.objectAddress = objectAddress;
    }

    public Side getSide() {
        return side;
    }

    public void setSide(Side side) {
        this.side = side;
    }

    @Override
    public String toString() {
        return "DataSourceConf [objectFormat=" + objectFormat
                + ", objectAddress=" + objectAddress + ", side=" + side + "]";
    }
    
    
}
