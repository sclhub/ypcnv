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

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import ypcnv.views.impl.FileVCF;
import ypcnv.views.impl.FileXLS;

/**
 * Mapping between data format identifiers (keys) and implementations of data
 * source objects (values).
 */
public class DataFormatIDsMaping {

    /**
     * Mapping of data format's ID to corresponding view's class realization
     * name.
     */
    public static final Map<DataFormatID, String> FILE_FORMAT_NAMING_MAP = Collections
            .unmodifiableMap(new LinkedHashMap<DataFormatID, String>() {
                private static final long serialVersionUID = -6949471211802960406L;

                {
                    put(DataFormatID.XLS, FileXLS.class.getCanonicalName());
                    put(DataFormatID.VCF, FileVCF.class.getCanonicalName());
                }
            });
}
