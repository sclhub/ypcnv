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

package local.asch.outglook;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Meta data concerning contact from MS-Outlook.
 * 
 * Field order is changed inside MS-Office depending on it's localization.
 * English MS-Office 2007 order is used below.
 * 
 * @version 2012-04-15_11-01
 * 
 */
abstract interface Contact2k3Names {
    /**
     * Mapping of data fields key-names to field's content descriptions. Each
     * contact have a lot of data fields associated with it.
     */
    static final Map<String, String> FIELD_DESCRIPTION_MAP = Collections
            .unmodifiableMap(new HashMap<String, String>() {
                private static final long serialVersionUID = -1986845664057363787L;
                {
                    put("title", "Title — this is title - Mr., Mrs., Ms. Think it is \"prefix\".");
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
                    put("telex", "Telex. May be mentioned among eMail features.");
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
                    put("location", "Location. I think this is GIS related information: longtitude, lattitude, etc.");
                    put("managersName", "ManagersName");
                    put("mileage", "Mileage");
                    put("notes", "Notes");
                    put("officeLocation", "OfficeLocation.  I think this is GIS related information: longtitude, lattitude, etc.");
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
}
