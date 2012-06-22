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
package ypcnv.helpers;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class EnumHelper {

    /** Logger */
    private static final Log LOG = LogFactory.getLog(EnumHelper.class);

    /**
     * A method to get a list of a string representations with object's possible
     * values.
     */
    public static <T extends Enum<T>> List<String> toStringList(Class<T> target) {
        String wantedMethodName = "getDisplayValue" ;
        List<String> list = new LinkedList<String>();
        Method wantedMethodValue = null ;

        try {
            wantedMethodValue = target.getMethod(wantedMethodName);
        } catch (NoSuchMethodException e) {
            LOG.error("method '"
                        + wantedMethodName + "' is not implemented in '"
                        + target +"'.");
            e.printStackTrace();
        }

        for (Object obj : target.getEnumConstants()) {
            if(wantedMethodValue == null) {
                list.add(obj.toString());
            } else {
                try {
                    list.add((String) wantedMethodValue.invoke(obj));
                } catch (Exception e) {
                    LOG.error("Failed to create list of possible values.");
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        }

        return list;
    }

}
