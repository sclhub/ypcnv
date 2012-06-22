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
package ypcnv.views.abstr;

import ypcnv.helpers.EnumExtensions;

/** Possible data format identifiers. */
public enum DataFormatID implements EnumExtensions {
    none("none"), XLS("XLS"), VCF("VCF");

    private String value ;
    
    DataFormatID(String value) {
        this.value = value ; 
    }
    
    public String get() {
        return value ;
    }
    
    @Override
    public String getDisplayValue() {
        return toString();
    }
    
}
