/*
 *  Copyright 2011 ASCH
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

package local.asch.outglook;

import java.util.HashMap;
import java.util.Iterator;

import local.asch.outglook.exceptions.Contact2k3Exception;

/**
 * Model for storage of single contact from MS-Outlook.
 * 
 * @version 2011-11-08_18-34
 * 
 */
public class Contact2k3 implements Contact2k3Names {

    private static final String ERR_MESSAGE_NO_KEY_IN_MAP = "There is no such a key '%s' in map.";

    /** TODO - Create mapping for data types of the data fields? */
    @SuppressWarnings({ "serial", "unused" })
    private HashMap<String, String> fieldDataTypesMap = new HashMap<String, String>() {
        {
            put("","");
        }
    };
    
    /**
     * There is mapping for values of data fields for a contact being exported
     * from MS-Outlook 2003 or 2007. Fields names are as is in XLS file exported
     * from the MS-Office, except collisions with Java key words.
     */
    @SuppressWarnings("serial")
    private HashMap<String, String> fieldValuesMap = new HashMap<String, String>() {
        {
            put("title", "");
            put("firstName", "");
            put("middleName", "");
            put("lastName", "");
            put("suffix", "");
            put("company", "");
            put("department", "");
            put("jobTitle", "");
            put("businessStreet", "");
            put("businessStreet2", "");
            put("businessStreet3", "");
            put("businessCity", "");
            put("businessState", "");
            put("businessPostalCode", "");
            put("businessCountryRegion", "");
            put("homeStreet", "");
            put("homeStreet2", "");
            put("homeStreet3", "");
            put("homeCity", "");
            put("homeState", "");
            put("homePostalCode", "");
            put("homeCountryRegion", "");
            put("otherStreet", "");
            put("otherStreet2", "");
            put("otherStreet3", "");
            put("otherCity", "");
            put("otherState", "");
            put("otherPostalCode", "");
            put("otherCountryRegion", "");
            put("assistantsPhone", "");
            put("businessFax", "");
            put("businessPhone", "");
            put("businessPhone2", "");
            put("callback", "");
            put("carPhone", "");
            put("companyMainPhone", "");
            put("homeFax", "");
            put("homePhone", "");
            put("homePhone2", "");
            put("iSDN", "");
            put("mobilePhone", "");
            put("otherFax", "");
            put("otherPhone", "");
            put("pager", "");
            put("primaryPhone", "");
            put("radioPhone", "");
            put("tTYTDDPhone", "");
            put("telex", "");
            put("account", "");
            put("anniversary", "");
            put("assistantsName", "");
            put("billingInformation", "");
            put("birthday", "");
            put("businessAddressPOBox", "");
            put("categories", "");
            put("children", "");
            put("directoryServer", "");
            put("emailAddress", "");
            put("emailType", "");
            put("emailDisplayName", "");
            put("email2Address", "");
            put("email2Type", "");
            put("email2DisplayName", "");
            put("email3Address", "");
            put("email3Type", "");
            put("email3DisplayName", "");
            put("gender", "");
            put("governmentIDNumber", "");
            put("hobby", "");
            put("homeAddressPOBox", "");
            put("initials", "");
            put("internetFreeBusy", "");
            put("keywords", "");
            put("language1", "");
            put("location", "");
            put("managersName", "");
            put("mileage", "");
            put("notes", "");
            put("officeLocation", "");
            put("organizationalIDNumber", "");
            put("otherAddressPOBox", "");
            put("priority", "");
            put("privateFlag", "");
            put("profession", "");
            put("referredBy", "");
            put("sensitivity", "");
            put("spouse", "");
            put("user1", "");
            put("user2", "");
            put("user3", "");
            put("user4", "");
            put("webPage", "");
        }
    };

    /**
     * Set values in map with data fields content.
     * 
     * @param key
     *            - already existent map's key.
     * @param value
     *            - map's value.
     * @return Previous value of the field.
     * @throws Contact2k3Exception
     *             in case there is no such a key in the map.
     */
    public Object setValue(String key, String value) throws Contact2k3Exception{
        if(fieldValuesMap.containsKey(key)){
            return fieldValuesMap.put(key, value);
        }
        String message = String.format(ERR_MESSAGE_NO_KEY_IN_MAP, key);
        throw new Contact2k3Exception(null, "Contact2k3.setValue()", message);
    }

    public HashMap<String, String> getFieldValuesMap() {
        return fieldValuesMap;
    }
    public Object getFieldValue(Object key) {
        return fieldValuesMap.get(key);
    }

    /**
     * @return Quantity of data fields concerning a "contact" information.
     */
    public static int getDataFieldsQuantity() {
        return FIELD_DESCRIPTION_MAP.size();
    }

    @Override
    public String toString() {
        return "Contact2k3 [fieldValuesMap=" + fieldValuesMap + "]";
    }
    
    public String toStringWithoutEmptyFields() {
        StringBuilder fieldValuesMapString = new StringBuilder();
        final String IN_STRING_DELIMETER = ", " ;

        Iterator<String> fieldValuesMapKeySetIterator = fieldValuesMap.keySet().iterator();
        while(fieldValuesMapKeySetIterator.hasNext()) {
            String key = fieldValuesMapKeySetIterator.next() ;
            String value=fieldValuesMap.get(key);

            if(!value.isEmpty()) {
                fieldValuesMapString.append(key + "=" + value + IN_STRING_DELIMETER);
            }

        }
        
        Integer trailingDelimeterStartIndex = fieldValuesMapString.lastIndexOf(", ", 
                fieldValuesMapString.length()-IN_STRING_DELIMETER.length()+1);
        if(trailingDelimeterStartIndex > 0) {
        fieldValuesMapString.delete(trailingDelimeterStartIndex,
                trailingDelimeterStartIndex + IN_STRING_DELIMETER.length());
        }
        fieldValuesMapString.insert(0, "{");
        fieldValuesMapString.append("}");
        return "Contact2k3 [fieldValuesMap=" + fieldValuesMapString + "]";
    }

}
