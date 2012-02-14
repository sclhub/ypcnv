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

import info.ineighborhood.cardme.engine.VCardEngine;
import info.ineighborhood.cardme.vcard.VCard;
import info.ineighborhood.cardme.vcard.VCardImpl;
import info.ineighborhood.cardme.vcard.features.TitleFeature;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import local.asch.outglook.exceptions.FileViewException;

/**
 * @version 2011-11-14_16-27
 */
public class Contact2k3VCardView extends Contact2k3FileView {

    // /** Logger. */
    // private static final Logger LOG =
    // Logger.getLogger(Contact2k3VCardView.class);

    /**
     * This is list of names for files with vCards.<br>
     * Contact2k3FileView.containerFileName will be path to vCard files.
     */
    private List<File> vCardFiles = null; //= new ArrayList<File>();
    private VCardEngine vCardEngine = null;
    
    /*
     * CompatibilityMode.MS_OUTLOOK
     * CompatibilityMode.RFC2426
     */

    /**
     * Mapping of the model's fields names to vCard container's classes names.
     * <b>Key</b> - container's object name,<br>
     * <b>value</b> - model's class field name.
     */
    private static final Map<String, String> CONTAINER_FIELD_NAMING_MAP = Collections
            .unmodifiableMap(new LinkedHashMap<String, String>() {
                private static final long serialVersionUID = -6949471211802960406L;

                {
                    put("TitleFeature", "title");
                    
                    put("FirstName", "firstName");
                    put("MiddleName", "middleName");
                    put("LastName", "lastName");
                    put("Suffix", "suffix");
                    put("Company", "company");
                    put("Department", "department");
                    put("JobTitle", "jobTitle");
                    put("BusinessStreet", "businessStreet");
                    put("BusinessStreet2", "businessStreet2");
                    put("BusinessStreet3", "businessStreet3");
                    put("BusinessCity", "businessCity");
                    put("BusinessState", "businessState");
                    put("BusinessPostalCode", "businessPostalCode");
                    put("BusinessCountryRegion", "businessCountryRegion");
                    put("HomeStreet", "homeStreet");
                    put("HomeStreet2", "homeStreet2");
                    put("HomeStreet3", "homeStreet3");
                    put("HomeCity", "homeCity");
                    put("HomeState", "homeState");
                    put("HomePostalCode", "homePostalCode");
                    put("HomeCountryRegion", "homeCountryRegion");
                    put("OtherStreet", "otherStreet");
                    put("OtherStreet2", "otherStreet2");
                    put("OtherStreet3", "otherStreet3");
                    put("OtherCity", "otherCity");
                    put("OtherState", "otherState");
                    put("OtherPostalCode", "otherPostalCode");
                    put("OtherCountryRegion", "otherCountryRegion");
                    put("AssistantsPhone", "assistantsPhone");
                    put("BusinessFax", "businessFax");
                    put("BusinessPhone", "businessPhone");
                    put("BusinessPhone2", "businessPhone2");
                    put("Callback", "callback");
                    put("CarPhone", "carPhone");
                    put("CompanyMainPhone", "companyMainPhone");
                    put("HomeFax", "homeFax");
                    put("HomePhone", "homePhone");
                    put("HomePhone2", "homePhone2");
                    put("ISDN", "iSDN");
                    put("MobilePhone", "mobilePhone");
                    put("OtherFax", "otherFax");
                    put("OtherPhone", "otherPhone");
                    put("Pager", "pager");
                    put("PrimaryPhone", "primaryPhone");
                    put("RadioPhone", "radioPhone");
                    put("TTYTDDPhone", "tTYTDDPhone");
                    put("Telex", "telex");
                    put("Account", "account");
                    put("Anniversary", "anniversary");
                    put("AssistantsName", "assistantsName");
                    put("BillingInformation", "billingInformation");
                    put("Birthday", "birthday");
                    put("BusinessAddressPOBox", "businessAddressPOBox");
                    put("Categories", "categories");
                    put("Children", "children");
                    put("DirectoryServer", "directoryServer");
                    put("EmailAddress", "emailAddress");
                    put("EmailType", "emailType");
                    put("EmailDisplayName", "emailDisplayName");
                    put("Email2Address", "email2Address");
                    put("Email2Type", "email2Type");
                    put("Email2DisplayName", "email2DisplayName");
                    put("Email3Address", "email3Address");
                    put("Email3Type", "email3Type");
                    put("Email3DisplayName", "email3DisplayName");
                    put("Gender", "gender");
                    put("GovernmentIDNumber", "governmentIDNumber");
                    put("Hobby", "hobby");
                    put("HomeAddressPOBox", "homeAddressPOBox");
                    put("Initials", "initials");
                    put("InternetFreeBusy", "internetFreeBusy");
                    put("Keywords", "keywords");
                    put("Language1", "language1");
                    put("Location", "location");
                    put("ManagersName", "managersName");
                    put("Mileage", "mileage");
                    put("Notes", "notes");
                    put("OfficeLocation", "officeLocation");
                    put("OrganizationalIDNumber", "organizationalIDNumber");
                    put("OtherAddressPOBox", "otherAddressPOBox");
                    put("Priority", "priority");
                    put("Private", "privateFlag");
                    put("Profession", "profession");
                    put("ReferredBy", "referredBy");
                    put("Sensitivity", "sensitivity");
                    put("Spouse", "spouse");
                    put("User1", "user1");
                    put("User2", "user2");
                    put("User3", "user3");
                    put("User4", "user4");
                    put("WebPage", "webPage");
                }
            });

    public Contact2k3VCardView(ArrayList<Contact2k3> aContactList, File fileNameForUse)
            throws IOException {
        super(aContactList, fileNameForUse);
        if(containerFileName.isDirectory()){ // TODO try to obtain all vCard file
            File[] vcfFiles = containerFileName.listFiles(new java.io.FileFilter(){
                @Override
                public boolean accept(File f) {
                    return f.getName().toLowerCase().endsWith(".vcf") || f.isDirectory();
                }
            });
            vCardFiles = (List<File>) Arrays.asList(vcfFiles);
        } else if(containerFileName.isFile()){
            // TODO use as a single file
            throw new IOException(
                    "TODO not realised here: containerFileName.isFile()");
        }
    }

    @Override
    public void set(ArrayList<Contact2k3> contactList) {
        // TODO Auto-generated method stub

    }

    @Override
    public void add(Contact2k3 contact) {
        // TODO Auto-generated method stub

    }

    @Override
    public void add(ArrayList<Contact2k3> contactList) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setView() throws FileViewException, IOException {
        // TODO Auto-generated method stub

    }

    @Override
    public void getView() throws FileViewException {
        // TODO Auto-generated method stub

    }

    @Override
    protected void saveToFile() throws IOException {
        // TODO Auto-generated method stub

    }

}
