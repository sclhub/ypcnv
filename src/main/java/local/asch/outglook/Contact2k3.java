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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import local.asch.outglook.exceptions.Contact2k3Exception;

/**
 * Model for storage of single contact from MS-Outlook.
 * 
 * Field order is changed inside MS-Office depending on it's localization.
 * English MS-Office 2007 order is used below.
 * 
 * @version 2011-11-08_18-34
 * 
 */
public class Contact2k3 {

    // XXX
    /*
     * ??? static enum fieldDescriptionMap { field1("title","Title"),
     * field2("firstName","FirstName");
     * 
     * private String fieldId; private String fieldName;
     * 
     * Field(String fieldId,String fieldName) { this.fieldId=fieldId;
     * this.fieldName=fieldName; } public String getId() { return fieldId; }
     * public String getName(){ return fieldName; } };
     */

    private static final String ERR_MESSAGE_NO_KEY_IN_MAP = "There is no such a key '%s' in map.";

    /**
     * Mapping of data fields key-names to field's content descriptions. Each
     * contact have a lot of data fields associated with it.
     */
    public static final Map<String, String> FIELD_DESCRIPTION_MAP = Collections
            .unmodifiableMap(new HashMap<String, String>() {
                private static final long serialVersionUID = -1986845664057363787L;
                {
                    put("title", "Title — this is title - Mr., Mrs., Ms.");
                    put("firstName", "FirstName");
                    put("middleName", "MiddleName");
                    put("lastName", "LastName");
                    put("suffix", "Suffix");
                    put("company", "Company");
                    put("department", "Department");
                    put("jobTitle", "JobTitle");
                    put("businessStreet", "BusinessStreet");
                    put("businessStreet2", "BusinessStreet2");
                    put("businessStreet3", "BusinessStreet3");
                    put("businessCity", "BusinessCity");
                    put("businessState", "BusinessState");
                    put("businessPostalCode", "BusinessPostalCode");
                    put("businessCountryRegion", "BusinessCountryRegion");
                    put("homeStreet", "HomeStreet");
                    put("homeStreet2", "HomeStreet2");
                    put("homeStreet3", "HomeStreet3");
                    put("homeCity", "HomeCity");
                    put("homeState", "HomeState");
                    put("homePostalCode", "HomePostalCode");
                    put("homeCountryRegion", "HomeCountryRegion");
                    put("otherStreet", "OtherStreet");
                    put("otherStreet2", "OtherStreet2");
                    put("otherStreet3", "OtherStreet3");
                    put("otherCity", "OtherCity");
                    put("otherState", "OtherState");
                    put("otherPostalCode", "OtherPostalCode");
                    put("otherCountryRegion", "OtherCountryRegion");
                    put("assistantsPhone", "AssistantsPhone");
                    put("businessFax", "BusinessFax");
                    put("businessPhone", "BusinessPhone");
                    put("businessPhone2", "BusinessPhone2");
                    put("callback", "Callback");
                    put("carPhone", "CarPhone");
                    put("companyMainPhone", "CompanyMainPhone");
                    put("homeFax", "HomeFax");
                    put("homePhone", "HomePhone");
                    put("homePhone2", "HomePhone2");
                    put("iSDN", "ISDN");
                    put("mobilePhone", "MobilePhone");
                    put("otherFax", "OtherFax");
                    put("otherPhone", "OtherPhone");
                    put("pager", "Pager");
                    put("primaryPhone", "PrimaryPhone");
                    put("radioPhone", "RadioPhone");
                    put("tTYTDDPhone", "TTYTDDPhone");
                    put("telex", "Telex");
                    put("account", "Account");
                    put("anniversary", "Anniversary");
                    put("assistantsName", "AssistantsName");
                    put("billingInformation", "BillingInformation");
                    put("birthday", "Birthday");
                    put("businessAddressPOBox", "BusinessAddressPOBox");
                    put("categories", "Categories");
                    put("children", "Children");
                    put("directoryServer", "DirectoryServer");
                    put("emailAddress", "EmailAddress");
                    put("emailType", "EmailType");
                    put("emailDisplayName", "EmailDisplayName");
                    put("email2Address", "Email2Address");
                    put("email2Type", "Email2Type");
                    put("email2DisplayName", "Email2DisplayName");
                    put("email3Address", "Email3Address");
                    put("email3Type", "Email3Type");
                    put("email3DisplayName", "Email3DisplayName");
                    put("gender", "Gender");
                    put("governmentIDNumber", "GovernmentIDNumber");
                    put("hobby", "Hobby");
                    put("homeAddressPOBox", "HomeAddressPOBox");
                    put("initials", "Initials");
                    put("internetFreeBusy", "InternetFreeBusy");
                    put("keywords", "Keywords");
                    put("language1", "Language1");
                    put("location", "Location");
                    put("managersName", "ManagersName");
                    put("mileage", "Mileage");
                    put("notes", "Notes");
                    put("officeLocation", "OfficeLocation");
                    put("organizationalIDNumber", "OrganizationalIDNumber");
                    put("otherAddressPOBox", "OtherAddressPOBox");
                    put("priority", "Priority");
                    put("privateFlag", "Private");
                    put("profession", "Profession");
                    put("referredBy", "ReferredBy");
                    put("sensitivity", "Sensitivity");
                    put("spouse", "Spouse");
                    put("user1", "User1");
                    put("user2", "User2");
                    put("user3", "User3");
                    put("user4", "User4");
                    put("webPage", "WebPage");

                }
            });

    /** TODO - Create mapping for data types of the data fields? */
    @SuppressWarnings({ "serial", "unused" })
    private HashMap<String, String> fieldDataTypesMap = new HashMap<String, String>() {
        {
            put("","");
        }
    };
    
    /**
     * There is mapping for values of data fields of a contact being exported
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

}
