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

package ypcnv.contact2k3;

import java.util.HashMap;
import java.util.Iterator;

import ypcnv.exceptions.ContactException;


/**
 * Representation of a single contact ("visit card").
 */
public class Contact2k3 implements Contact2k3Names {

    private static final String ERR_MESSAGE_NO_KEY_IN_MAP = "There is no such a key '%s' in map.";

    /* TODO - Create mapping for data types of the data fields? */
    /**
     * Data fields may by of several types. This mapping may correlate names of
     * fields and types. In fact this must be in views implementation. Not used.
     */
    @SuppressWarnings({ "unused" })
    private HashMap<String, String> fieldDataTypesMap = new HashMap<String, String>() {
        private static final long serialVersionUID = -2535490303657266322L;
        {
            put("","");
        }
    };
    
    /**
     * This is mapping for values of data fields for a contact being exported
     * from MS-Outlook 2003 or 2007. Fields names are as is in XLS file exported
     * from the MS-Office, except collisions with Java key words.
     */
    private HashMap<String, String> fieldValuesMap = new HashMap<String, String>() {
        private static final long serialVersionUID = -250289910105129652L;
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
     * Fill map with values of data fields.
     * 
     * @param key
     *            - already existent map's key.
     * @param value
     *            - map's value.
     * @return Previous value of the field.
     * @throws ContactException
     *             in case there is no such a key in the map.
     */
    public Object setValue(String key, String value) throws ContactException{
        if(fieldValuesMap.containsKey(key)){
            return fieldValuesMap.put(key, value);
        }
        String message = String.format(ERR_MESSAGE_NO_KEY_IN_MAP, key);
        throw new ContactException(null, "Contact2k3.setValue()", message);
    }

    public HashMap<String, String> getFieldValuesMap() {
        return fieldValuesMap;
    }
    
    /**
     * Get field value using a key.
     * @param key - key in the map.
     * @return Field value corresponding to the key.
     */
    public Object getFieldValue(Object key) {
        return fieldValuesMap.get(key);
    }

    /**
     * @return Quantity of data fields concerning a single "contact" information.
     */
    public static int getDataFieldsQuantity() {
        return FIELD_DESCRIPTION_MAP.size();
    }

    @Override
    public String toString() {
        return "Contact2k3 [fieldValuesMap=" + fieldValuesMap + "]";
    }
    
    /**
     * Will return a "string" representation, but omitting empty fields. 
     * @return
     */
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
