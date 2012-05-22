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
package local.asch.outglook.fileview;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Meta data concerning contact from MS-Outlook exported into XLS container.
 * 
 * Field order is changed inside MS-Office depending on it's localization.
 * 
 */
abstract interface Contact2k3VCardViewNames {
    /**
     * Mapping of the model's fields names to vCard container's setter methods
     * names.<br>
     * <b>Key</b> - setter method's name,<br>
     * <b>value</b> - model's class field name.
     */
    static final Map<String, String> CONTAINER_FIELD_NAMING_MAP = Collections
            .unmodifiableMap(new LinkedHashMap<String, String>() {
                private static final long serialVersionUID = -6949471211802960406L;

                {
                    put("title", "setTitle");
                    put("firstName", "setFirstName");
                    put("middleName", "setMiddleName");
                    put("lastName", "setLastName");
                    put("suffix", "setSuffix");
                    put("company", "setCompany");
                    put("department", "setDepartment");
                    put("jobTitle", "setJobTitle");
                    put("businessStreet", "setBusinessStreet1");
                    put("businessStreet2", "setBusinessStreet2");
                    put("businessStreet3", "setBusinessStreet3");
                    put("businessCity", "setBusinessCity");
                    put("businessState", "setBusinessState");
                    put("businessPostalCode", "setBusinessPostalCode");
                    put("businessCountryRegion", "setBusinessCountryRegion");
                    put("homeStreet", "setHomeStreet1");
                    put("homeStreet2", "setHomeStreet2");
                    put("homeStreet3", "setHomeStreet3");
                    put("homeCity", "setHomeCity");
                    put("homeState", "setHomeState");
                    put("homePostalCode", "setHomePostalCode");
                    put("homeCountryRegion", "setHomeCountryRegion");
                    put("otherStreet", "setOtherStreet1");
                    put("otherStreet2", "setOtherStreet2");
                    put("otherStreet3", "setOtherStreet3");
                    put("otherCity", "setOtherCity");
                    put("otherState", "setOtherState");
                    put("otherPostalCode", "setOtherPostalCode");
                    put("otherCountryRegion", "setOtherCountryRegion");
                    put("assistantsPhone", "setAssistantsPhone");
                    put("businessFax", "setBusinessFax");
                    put("businessPhone", "setBusinessPhone1");
                    put("businessPhone2", "setBusinessPhone2");
                    put("callback", "setCallback");
                    put("carPhone", "setCarPhone");
                    put("companyMainPhone", "setCompanyMainPhone");
                    put("homeFax", "setHomeFax");
                    put("homePhone", "setHomePhone1");
                    put("homePhone2", "setHomePhone2");
                    put("iSDN", "setISDN");
                    put("mobilePhone", "setMobilePhone");
                    put("otherFax", "setOtherFax");
                    put("otherPhone", "setOtherPhone");
                    put("pager", "setPager");
                    put("primaryPhone", "setPrimaryPhone");
                    put("radioPhone", "setRadioPhone");
                    put("tTYTDDPhone", "setTTYTDDPhone");
                    put("telex", "setTelex");
                    put("account", "setAccount");
                    put("anniversary", "setAnniversary");
                    put("assistantsName", "setAssistantsName");
                    put("billingInformation", "setBillingInformation");
                    put("birthday", "setBirthday");
                    put("businessAddressPOBox", "setBusinessAddressPOBox");
                    put("categories", "setCategories");
                    put("children", "setChildren");
                    put("directoryServer", "setDirectoryServer");
                    put("emailAddress", "setEmail1Address");
                    put("emailType", "setEmail1Type");
                    put("emailDisplayName", "setEmail1DisplayName");
                    put("email2Address", "setEmail2Address");
                    put("email2Type", "setEmail2Type");
                    put("email2DisplayName", "setEmail2DisplayName");
                    put("email3Address", "setEmail3Address");
                    put("email3Type", "setEmail3Type");
                    put("email3DisplayName", "setEmail3DisplayName");
                    put("gender", "setGender");
                    put("governmentIDNumber", "setGovernmentIDNumber");
                    put("hobby", "setHobby");
                    put("homeAddressPOBox", "setHomeAddressPOBox");
                    put("initials", "setInitials");
                    put("internetFreeBusy", "setInternetFreeBusy");
                    put("keywords", "setKeywords");
                    put("language1", "setLanguage1");
                    put("location", "setLocation");
                    put("managersName", "setManagersName");
                    put("mileage", "setMileage");
                    put("notes", "setNotes");
                    put("officeLocation", "setOfficeLocation");
                    put("organizationalIDNumber", "setOrganizationalIDNumber");
                    put("otherAddressPOBox", "setOtherAddressPOBox");
                    put("priority", "setPriority");
                    put("privateFlag", "setPrivateFlag");
                    put("profession", "setProfession");
                    put("referredBy", "setReferredBy");
                    put("sensitivity", "setSensitivity");
                    put("spouse", "setSpouse");
                    put("user1", "setUser1");
                    put("user2", "setUser2");
                    put("user3", "setUser3");
                    put("user4", "setUser4");
                    put("webPage", "setWebPage");
                }
            });

}
