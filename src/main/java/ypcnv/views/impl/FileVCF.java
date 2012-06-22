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
package ypcnv.views.impl;

import info.ineighborhood.cardme.engine.VCardEngine;
import info.ineighborhood.cardme.io.CompatibilityMode;
import info.ineighborhood.cardme.io.FoldingScheme;
import info.ineighborhood.cardme.io.VCardWriter;
import info.ineighborhood.cardme.vcard.VCard;
import info.ineighborhood.cardme.vcard.VCardImpl;
import info.ineighborhood.cardme.vcard.VCardVersion;
import info.ineighborhood.cardme.vcard.errors.VCardError;
import info.ineighborhood.cardme.vcard.errors.VCardErrorHandling;
import info.ineighborhood.cardme.vcard.features.AddressFeature;
import info.ineighborhood.cardme.vcard.features.CategoriesFeature;
import info.ineighborhood.cardme.vcard.features.DisplayableNameFeature;
import info.ineighborhood.cardme.vcard.features.EmailFeature;
import info.ineighborhood.cardme.vcard.features.LabelFeature;
import info.ineighborhood.cardme.vcard.features.NicknameFeature;
import info.ineighborhood.cardme.vcard.features.NoteFeature;
import info.ineighborhood.cardme.vcard.features.OrganizationFeature;
import info.ineighborhood.cardme.vcard.features.TelephoneFeature;
import info.ineighborhood.cardme.vcard.types.AddressType;
import info.ineighborhood.cardme.vcard.types.BeginType;
import info.ineighborhood.cardme.vcard.types.BirthdayType;
import info.ineighborhood.cardme.vcard.types.CategoriesType;
import info.ineighborhood.cardme.vcard.types.DisplayableNameType;
import info.ineighborhood.cardme.vcard.types.EmailType;
import info.ineighborhood.cardme.vcard.types.EndType;
import info.ineighborhood.cardme.vcard.types.FormattedNameType;
import info.ineighborhood.cardme.vcard.types.LabelType;
import info.ineighborhood.cardme.vcard.types.NameType;
import info.ineighborhood.cardme.vcard.types.NicknameType;
import info.ineighborhood.cardme.vcard.types.NoteType;
import info.ineighborhood.cardme.vcard.types.OrganizationType;
import info.ineighborhood.cardme.vcard.types.ProfileType;
import info.ineighborhood.cardme.vcard.types.RoleType;
import info.ineighborhood.cardme.vcard.types.TelephoneType;
import info.ineighborhood.cardme.vcard.types.TitleType;
import info.ineighborhood.cardme.vcard.types.URLType;
import info.ineighborhood.cardme.vcard.types.VersionType;
import info.ineighborhood.cardme.vcard.types.parameters.AddressParameterType;
import info.ineighborhood.cardme.vcard.types.parameters.EmailParameterType;
import info.ineighborhood.cardme.vcard.types.parameters.LabelParameterType;
import info.ineighborhood.cardme.vcard.types.parameters.ParameterTypeStyle;
import info.ineighborhood.cardme.vcard.types.parameters.TelephoneParameterType;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ypcnv.contact2k3.Contact2k3;
import ypcnv.contact2k3.ContactsBook;
import ypcnv.converter.conf.DataSourceConf;
import ypcnv.exceptions.FileViewException;
import ypcnv.helpers.VCardSamsungHelper;
import ypcnv.views.abstr.DataSourceAbstractImpl;
import ypcnv.views.ifLow.FileSystem;

/**
 * Data source - file system, regular file, VCF format.
 */
public class FileVCF extends DataSourceAbstractImpl implements FileSystem {

    /** Logger. */
    private static final Log LOG = LogFactory.getLog(FileVCF.class);

    /**
     * Not sure in all devices software support multiply notes. Will store all
     * notes in a single container - in a single note.
     */
    private String noteContent = new String();

    private EmailFeature eMail1 = new EmailType();
    private EmailFeature eMail2 = new EmailType();
    private EmailFeature eMail3 = new EmailType();

    /**
     * This is list of names for files with vCards.<br>
     * DataContainer.address will be a path to vCard files.
     */
    private List<File> vCardFiles = null; // = new ArrayList<File>();
    private File outDirUUIDAsString = null;
    private VCardEngine vCardEngine = null;
    private VCardWriter vCardWriter = null;

    /*
     * CompatibilityMode.MS_OUTLOOK CompatibilityMode.RFC2426
     */
    private static final CompatibilityMode compatibilityMode = CompatibilityMode.RFC2426;
    
    /** List of VCF visit cards. */
    private ArrayList<VCard> vCardsList = new ArrayList<VCard>();
    
    FileVCF() { super(); }

    /**
     * @param dataSourceConfiguration - configuration of data source to be used.
     */
    public FileVCF(DataSourceConf dataSourceConfiguration) {
        super(dataSourceConfiguration);
        vCardWriter = new VCardWriter();
        build(dataSourceConfiguration);
    }

    /* (non-Javadoc)
     * @see ypcnv.outglook.views.DataSource#pullData()
     */
    @Override
    public void pullData() throws FileViewException {
        dataContainer = new ContactsBook();
        vCardsList.clear();
        for ( File aVCF : vCardFiles ) {
            VCard vCard;
            try {
                vCard = vCardEngine.parse(aVCF);
                vCardsList.add(vCard);
            } catch (IOException e) {
                String message = String.format("Failed to read VCard file: '%s'.",
                                            aVCF.getAbsolutePath());
                LOG.error(message);
                e.printStackTrace();
            }
        }

        // TODO - need implementation of split a single VCF into several vCards.
        Iterator<VCard> vCardsListIterator= vCardsList.iterator();
        while(vCardsListIterator.hasNext()){
            // XXX - set up of a model from vCards is not implemented at all.
            return;
        }
    }

    /* (non-Javadoc)
     * @see ypcnv.outglook.views.DataSource#pushToStorage()
     */
    @Override
    public void pushData() throws FileViewException {
        if (dataContainer.getContacts().isEmpty()) {
            return;
        }

        vCardsList = new ArrayList<VCard>();
        VCard vCard = null;

        Iterator<Contact2k3> containerModelListIterator = dataContainer
                .getContacts().iterator();
        while (containerModelListIterator.hasNext()) {

            vCard = new VCardImpl();

            noteContent = new String();

            // setVCardVersion(vCard, VCardVersion.V2_1);
            setVCardVersion(vCard, VCardVersion.V3_0);
            vCard.setProfile(new ProfileType("VCARD")); // Or must be "VCard" ?

            Contact2k3 contactCurrent = containerModelListIterator.next();
            HashMap<String, String> contact2k3FieldValuesMap = contactCurrent
                    .getFieldValuesMap();

            Iterator<String> contact2k3FieldValuesMapIterator = contact2k3FieldValuesMap
                    .keySet().iterator();
            while (contact2k3FieldValuesMapIterator.hasNext()) {
                String key = contact2k3FieldValuesMapIterator.next();

                if (!FileVCFNames.CONTAINER_FIELD_NAMING_MAP.containsKey(key)) {
                    String message = String.format(FileVCFMeta.ERR_MESSAGE_NOT_FOUND_KEY,
                            key, ((File)address).getAbsoluteFile());
                    throw new FileViewException(null,
                            "Contact2k3VCard.FileViewException()", message);
                }

                Object feedBack = null;
                // Set value in VCard field from model's field.
                feedBack = invokeInternalSetter(
                        FileVCF.class.getCanonicalName(),
                        FileVCFNames.CONTAINER_FIELD_NAMING_MAP.get(key), new Class[] {
                                Contact2k3.class, VCard.class, String.class },
                                new Object[] { contactCurrent, vCard, key });
                if (feedBack != null) {
                    if (key == "notes") {
                        noteContent = (String) feedBack + noteContent;
                    } else {
                        noteContent += (String) feedBack;
                    }
                }
            }

            if (noteContent != null) {
                addNoteFeature(vCard, noteContent);
            }
            vCard.setBegin(new BeginType());
            vCard.setEnd(new EndType());
            String displayName = createDisplayName(vCard);
            vCard.setFormattedName(new FormattedNameType(displayName));

            DisplayableNameFeature displayableName = new DisplayableNameType();
            displayableName.setName(displayName);
            vCard.setDisplayableNameFeature(displayableName);

            //new VCardSamsungHelper();
            if(! VCardSamsungHelper.checkAndFixGTC3322Compatibility(vCard)) {
                String warningMessage = "Warning token: "
                                + contactCurrent.toStringWithoutEmptyFields();
                LOG.info(warningMessage);
            }

            vCardsList.add(vCard);
        }
        
        try {
            saveToFile(vCardsList);
        } catch (FileViewException e) {
            LOG.error("Failed to write into data storage.");
            e.printStackTrace();
        }
    }
    
    private void saveToFile(ArrayList<VCard> vCardsList)
            throws FileViewException {
        Long outNameId = new Long(0);

        File outDir = new File(((File)address).getPath() + File.separator + outDirUUIDAsString);
        if (!outDir.exists()) {
            if (!outDir.mkdirs()) {
                String message = String.format(
                        "Failed to create directory '%s'.", outDir.toString());
                throw new FileViewException(null, "", message);
            }
        }

        Iterator<VCard> vCardListIterator = vCardsList.iterator();
        while (vCardListIterator.hasNext()) {
            VCard vCard = vCardListIterator.next();
            vCardWriter.setOutputVersion(VCardVersion.V3_0); // Another version
                                                                // is not
                                                                // supported
                                                                // today.
            vCardWriter.setFoldingScheme(FoldingScheme.MIME_DIR);
            vCardWriter.setCompatibilityMode(compatibilityMode);
            vCardWriter.setVCard(vCard);

            String vCardString = vCardWriter.buildVCardString();

            if (vCardWriter.hasErrors()) {
                LOG.error(String
                        .format("Before writing to file failed with vCard container setup:"));
                List<VCardError> errors = ((VCardErrorHandling) vCardWriter
                        .getVCard()).getErrors();
                for (int i = 0; i < errors.size(); i++) {
                    LOG.error("---> "
                            + String.format(errors.get(i).getErrorMessage()));
                }
            }

            File outFileName = new File(outDir, outNameId + ".vcf");
            Writer output = null;
            try {
                output = new BufferedWriter(new FileWriter(outFileName));
                output.write(vCardString); // Default encoding is not
                                            // controlled.
            } catch (IOException e1) {
                LOG.error(String.format("Failed to write into '%s'.",
                        outFileName));
                e1.printStackTrace();
            } finally {
                try {
                    output.close();
                } catch (IOException e) {
                    LOG.error(String.format("Failed to close output for '%s'.",
                            outFileName));
                    e.printStackTrace();
                }
            }

            outNameId++;
        }
    }

    /* (non-Javadoc)
     * @see ypcnv.views.other.DataContainer#build(ypcnv.converter.conf.ConfsFactory)
     */
    @Override
    public void build(DataSourceConf configuration) {

        vCardFiles = collectFileNames();
        if(vCardFiles == null) {
            String message = String.format("Failed to build an object. Parameters: %s",
                    configuration.toString());
            LOG.error(message);
            return ;
        }
        vCardEngine = new VCardEngine();
        vCardEngine.setCompatibilityMode(compatibilityMode);
        try {
            pullData();
        } catch (FileViewException e) {
            String message = String.format("Failed to pull data from an object/objects. Parameters: %s",
                    configuration.toString());
            LOG.error(message);
            e.printStackTrace();
        }
    }

    /**
     * Collect filenames to be processed.
     */
    private List<File> collectFileNames() {
        Calendar timestamp = Calendar.getInstance();
        outDirUUIDAsString = new File(String.format(
                "ypcnv-test-%1$tY-%1$tm-%1$te_%1$tH%1$tM_%1$tS-%1$tL", timestamp));

        if (((File)address).isDirectory()) {
            File[] vcfFiles = ((File)address)
                    .listFiles(new java.io.FileFilter() {
                        public boolean accept(File f) {
                            return f.getName().toLowerCase().endsWith(".vcf")
                                    && ! f.isDirectory();
                        }
                    });
//            File[] vcfFiles = ((File)address)
//                    .listFiles(new java.io.FileFilter() {
//                        public boolean accept(File f) {
//                            return f.getName().toLowerCase().endsWith(".vcf")
//                                    || f.isDirectory();
//                        }
//                    });
            return (List<File>) Arrays.asList(vcfFiles);
        } else if (((File)address).isFile()) {
            // TODO - need implementation of split a single VCF into several vCards.
            String format = "TODO not realised here: ((File)address).isFile()";
            LOG.error(format);
            return null ;
            //throw new IOException(message);
        } else {
            String message = String.format("Was not recognized object. Error token: %s",
                                                            address.toString());
            LOG.error(message);
            vCardFiles = null ;
            vCardEngine = null ;
            return null ;
        }
    }

    private void setVCardVersion(VCard vCard, VCardVersion version) {
        vCard.setVersion(new VersionType(version));
    }

    /**
     * Invoker for a method via reflection.
     * @param className - to be used.
     * @param methodName - to be called.
     * @param argumentsTypes - types of items in the method's arguments list.
     * @param args - arguments for the method.
     */
    private static Object invokeInternalSetter(String className,
                                String methodName,
                                Class<?>[] argumentsTypes,
                                Object[] args) {
        Class<?> c = null;
        Method m = null;
        Object i = null;
        Object r = null;  
        try {
            c = Class.forName(className);
            m = c.getDeclaredMethod(methodName, argumentsTypes);
            i = c.newInstance();
            r = m.invoke(i, args);
        } catch (ClassNotFoundException e) {
            LOG.error(String.format(
                    "Not found class '%s'.", className));
            e.printStackTrace();
            return null;
        } catch (NoSuchMethodException e) {
            LOG.error(String.format(
                    "No such method: '%s.%s'.",
                    className, methodName));
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        } catch (InstantiationException e) {
            e.printStackTrace();
            return null;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
        return r;
    }

    private void addNoteFeature(VCard vCard, String value) {
        if(! value.isEmpty()) {
            NoteFeature note = new NoteType();
            /* Special (for vCard) symbols must be shielded in note's content. */
            note.setNote(value);
            vCard.addNote(note);
        }
    }

    /**
     * Compile several vCard objects into one object for usage as a content of
     * another "feature".
     * 
     * @param vCard
     *            - object to be processed.
     * @return Compiled value.
     */
    private String createDisplayName(VCard vCard) {
        String displayName = new String() ;
        String buffer = new String() ;
        
        buffer = vCard.getName().getFamilyName();
        if(buffer != null && ! buffer.isEmpty()) {
            displayName = buffer ;
        }

        buffer = vCard.getName().getGivenName();
        if(buffer != null && ! buffer.isEmpty()){
            displayName += " " + buffer ;
        }

        Iterator<String> additionalNamesIterator
                                     = vCard.getName().getAdditionalNames();
        while(additionalNamesIterator.hasNext()) {
            buffer = additionalNamesIterator.next();
            if(buffer != null && ! buffer.isEmpty()) {
                displayName += " " + buffer ;
            }
        }
        
        if(displayName.indexOf(' ') == 0) {
            return displayName.substring(1) ;
        }
        return displayName ;
    }

    /**
     * Address type validator, logic is specific to data container-buffer's logic.
     * @param addrType - type to be processed.
     * @return <b>True</b> if type is acceptable.
     * @throws FileViewException If type is not valid.
     */
    private Boolean validateAddressType(AddressParameterType addrType) throws FileViewException {
        switch(addrType) {
        case WORK:
        case HOME:
        case OTHER:
            return true;
        }
        String errorMessage= new String("Got unexpected address parameter type '"
                                                + addrType.toString()
                                                + "'.");
        LOG.error(errorMessage);
        throw new FileViewException(null,
                            "Validator of address type specific to model.",
                            errorMessage);
    }

    private void setStreetUniversal(Contact2k3 contact, VCard vCard,
            String key, AddressParameterType addrType) throws FileViewException {
        validateAddressType(addrType);
        String value = (String) contact.getFieldValue(key);
        if (!value.isEmpty()) {
            value = new String(VCardSamsungHelper.transliterateCyr2Lat(value));

            AddressFeature address = pickupAddressFeature(vCard, addrType);
            String prevValue = address.getStreetAddress();

            if (prevValue == null) {
                prevValue = new String("");
            }
            if (prevValue.isEmpty()) {
                address.setStreetAddress(value);
            } else {
                address.setStreetAddress(prevValue + "; " + value);
            }
        }
    }

    private void setPhoneUniversal(VCard vCard, String phoneNumber, ArrayList<TelephoneParameterType> phoneType) {
        TelephoneFeature telephone = new TelephoneType();
        
        telephone.setTelephone(phoneNumber);
        Iterator<TelephoneParameterType> phoneTypeIterator = phoneType.iterator();
        while(phoneTypeIterator.hasNext()){
            telephone.addTelephoneParameterType(phoneTypeIterator.next());
        }
        telephone.setParameterTypeStyle(ParameterTypeStyle.PARAMETER_VALUE_LIST);
        vCard.addTelephoneNumber(telephone);
    }

    private void setEmailUniversal(VCard vCard, EmailFeature eMail, String value) {
        eMail.setEmail(value);
        eMail.addEmailParameterType(EmailParameterType.NON_STANDARD);
        eMail.addEmailParameterType(EmailParameterType.INTERNET);
        vCard.addEmail(eMail);
    }
    
    /****************
     *              *
     *              *
     *              *
     *              *
     *              *
     *              *
     ****************/

    /*
     * Below are creators of VCard internal objects.
     */
    
    private void pickupNameFeature(VCard vCard) {
        if ( vCard.getName() == null ) {
            vCard.setName(new NameType());
        }
    }
    
    /**
     * Creator and getter of address object, with logic specific for model.
     * 
     * @param vCard
     *            - vCard object
     * @param addrType
     *            - Type of location address points to.
     * @return Address object.
     * @throws FileViewException
     */
    private AddressType pickupAddressFeature(VCard vCard, AddressParameterType addrType) throws FileViewException {
        validateAddressType(addrType);
        Iterator<AddressFeature> addressListIterator = vCard.getAddresses();
        while(addressListIterator.hasNext()) {
            AddressType foundAddress = (AddressType) addressListIterator.next();
            if(foundAddress.containsAddressParameterType(addrType)) {
                return foundAddress ;
            }
        }
        
        AddressType newAddress = new AddressType();
        newAddress.addAddressParameterType(addrType);

        LabelFeature labelForAddress = new LabelType();
        switch (addrType) {
        case WORK:
            labelForAddress.addLabelParameterType(LabelParameterType.WORK);
            break;
        case HOME:
            labelForAddress.addLabelParameterType(LabelParameterType.HOME);
            break;
        case OTHER:
            labelForAddress.addLabelParameterType(LabelParameterType.OTHER);
            break;
        }
        vCard.addAddress(newAddress); 
        vCard.setLabel(labelForAddress, newAddress);
        return newAddress ;
    }

    /*
     * 
     * 
     * 
     * 
     * Below are setters of VCard fields from model's fields.
     */
    
    @SuppressWarnings("unused")
    private void setTitle(Contact2k3 contact, VCard vCard, String key) {
        String value = (String) contact.getFieldValue(key);
        if(! value.isEmpty()) {
            pickupNameFeature(vCard);
            vCard.getName().addHonorificPrefix(value);
        }
    }

    @SuppressWarnings("unused")
    private void setFirstName(Contact2k3 contact, VCard vCard, String key) {
        String value = (String) contact.getFieldValue(key);
        if(! value.isEmpty()) {
            pickupNameFeature(vCard);
            vCard.getName().setGivenName(value);
        }
    }

    @SuppressWarnings("unused")
    private void setMiddleName(Contact2k3 contact, VCard vCard, String key) {
        String value = (String) contact.getFieldValue(key);
        if(! value.isEmpty()) {
            pickupNameFeature(vCard);
            vCard.getName().addAdditionalName(value);
        }
    }

    @SuppressWarnings("unused")
    private void setLastName(Contact2k3 contact, VCard vCard, String key) {
        String value = (String) contact.getFieldValue(key);
        if(! value.isEmpty()) {
            pickupNameFeature(vCard);
            vCard.getName().setFamilyName(value);
        } else {
            pickupNameFeature(vCard);
            vCard.getName().setFamilyName(""); // CardMe do not allow NameFeature to be void. 
        }
    }

    @SuppressWarnings("unused")
    private void setSuffix(Contact2k3 contact, VCard vCard, String key) {
        String value = (String) contact.getFieldValue(key);
        if(! value.isEmpty()) {
            pickupNameFeature(vCard);
            vCard.getName().addHonorificSuffix(value);
        }
    }

    @SuppressWarnings("unused")
    private void setCompany(Contact2k3 contact, VCard vCard, String key) {
        String value = (String) contact.getFieldValue(key);
        if(! value.isEmpty()) {
            OrganizationFeature organization = new OrganizationType();
            organization.addOrganization((String) contact.getFieldValue(key));
            vCard.setOrganizations(organization);
        }
    }

    @SuppressWarnings("unused")
    private String setDepartment(Contact2k3 contact, VCard vCard, String key) {
        String value = (String) contact.getFieldValue(key);
        if(! value.isEmpty()) {
            return "department - " + value + "\n";
        }
        return null;
    }

    @SuppressWarnings("unused")
    private void setJobTitle(Contact2k3 contact, VCard vCard, String key) {
        String value = (String) contact.getFieldValue(key);
        if(! value.isEmpty()) {
            vCard.setRole(new RoleType((String) contact.getFieldValue(key)));
        }
    }

    @SuppressWarnings("unused")
    private void setBusinessStreet1(Contact2k3 contact, VCard vCard, String key) throws FileViewException {
        setStreetUniversal(contact, vCard, key, AddressParameterType.WORK);
    }

    @SuppressWarnings("unused")
    private void setBusinessStreet2(Contact2k3 contact, VCard vCard, String key) throws FileViewException {
        setStreetUniversal(contact, vCard, key, AddressParameterType.WORK);
    }

    @SuppressWarnings("unused")
    private void setBusinessStreet3(Contact2k3 contact, VCard vCard, String key) throws FileViewException {
        setStreetUniversal(contact, vCard, key, AddressParameterType.WORK);
    }

    @SuppressWarnings("unused")
    private void setBusinessCity(Contact2k3 contact, VCard vCard, String key) throws FileViewException {
        String value = (String) contact.getFieldValue(key);
        if(! value.isEmpty()) {
            value = new String(VCardSamsungHelper.transliterateCyr2Lat(value));
            pickupAddressFeature(vCard, AddressParameterType.WORK).setRegion(value);

        }
    }

    @SuppressWarnings("unused")
    private void setBusinessState(Contact2k3 contact, VCard vCard, String key) throws FileViewException {
        String value = (String) contact.getFieldValue(key);
        if(! value.isEmpty()) {
            value = new String(VCardSamsungHelper.transliterateCyr2Lat(value));
            pickupAddressFeature(vCard, AddressParameterType.WORK).setLocality(value);
        }
    }

    @SuppressWarnings("unused")
    private void setBusinessPostalCode(Contact2k3 contact, VCard vCard, String key) throws FileViewException {
        String value = (String) contact.getFieldValue(key);
        if(! value.isEmpty()) {
            value = new String(VCardSamsungHelper.transliterateCyr2Lat(value));
            pickupAddressFeature(vCard, AddressParameterType.WORK).setPostalCode(value);
        }
    }

    @SuppressWarnings("unused")
    private void setBusinessCountryRegion(Contact2k3 contact, VCard vCard, String key) throws FileViewException {
        String value = (String) contact.getFieldValue(key);
        if(! value.isEmpty()) {
            value = new String(VCardSamsungHelper.transliterateCyr2Lat(value));
            pickupAddressFeature(vCard, AddressParameterType.WORK).setCountryName(value);
        }
    }

    @SuppressWarnings("unused")
    private void setHomeStreet1(Contact2k3 contact, VCard vCard, String key) throws FileViewException {
        setStreetUniversal(contact, vCard, key, AddressParameterType.HOME);
    }

    @SuppressWarnings("unused")
    private void setHomeStreet2(Contact2k3 contact, VCard vCard, String key) throws FileViewException {
        setStreetUniversal(contact, vCard, key, AddressParameterType.HOME);
    }

    @SuppressWarnings("unused")
    private void setHomeStreet3(Contact2k3 contact, VCard vCard, String key) throws FileViewException {
        setStreetUniversal(contact, vCard, key, AddressParameterType.HOME);
    }

    @SuppressWarnings("unused")
    private void setHomeCity(Contact2k3 contact, VCard vCard, String key) throws FileViewException {
        String value = (String) contact.getFieldValue(key);
        if(! value.isEmpty()) {
            value = new String(VCardSamsungHelper.transliterateCyr2Lat(value));
            pickupAddressFeature(vCard, AddressParameterType.HOME).setRegion(value);
        }
    }

    @SuppressWarnings("unused")
    private void setHomeState(Contact2k3 contact, VCard vCard, String key) throws FileViewException {
        String value = (String) contact.getFieldValue(key);
        if(! value.isEmpty()) {
            value = new String(VCardSamsungHelper.transliterateCyr2Lat(value));
            pickupAddressFeature(vCard, AddressParameterType.HOME).setLocality(value);
        }
    }

    @SuppressWarnings("unused")
    private void setHomePostalCode(Contact2k3 contact, VCard vCard, String key) throws FileViewException {
        String value = (String) contact.getFieldValue(key);
        if(! value.isEmpty()) {
            value = new String(VCardSamsungHelper.transliterateCyr2Lat(value));
            pickupAddressFeature(vCard, AddressParameterType.HOME).setPostalCode(value);
        }
    }

    @SuppressWarnings("unused")
    private void setHomeCountryRegion(Contact2k3 contact, VCard vCard, String key) throws FileViewException {
        String value = (String) contact.getFieldValue(key);
        if(! value.isEmpty()) {
            value = new String(VCardSamsungHelper.transliterateCyr2Lat(value));
            pickupAddressFeature(vCard, AddressParameterType.HOME).setCountryName(value);
        }
    }

    @SuppressWarnings("unused")
    private void setOtherStreet1(Contact2k3 contact, VCard vCard, String key) throws FileViewException {
        setStreetUniversal(contact, vCard, key, AddressParameterType.OTHER);
    }

    @SuppressWarnings("unused")
    private void setOtherStreet2(Contact2k3 contact, VCard vCard, String key) throws FileViewException {
        setStreetUniversal(contact, vCard, key, AddressParameterType.OTHER);
    }

    @SuppressWarnings("unused")
    private void setOtherStreet3(Contact2k3 contact, VCard vCard, String key) throws FileViewException {
        setStreetUniversal(contact, vCard, key, AddressParameterType.OTHER);
    }

    @SuppressWarnings("unused")
    private void setOtherCity(Contact2k3 contact, VCard vCard, String key) throws FileViewException {
        String value = (String) contact.getFieldValue(key);
        if(! value.isEmpty()) {
            value = new String(VCardSamsungHelper.transliterateCyr2Lat(value));
            pickupAddressFeature(vCard, AddressParameterType.OTHER).setRegion(value);
        }
    }

    @SuppressWarnings("unused")
    private void setOtherState(Contact2k3 contact, VCard vCard, String key) throws FileViewException {
        String value = (String) contact.getFieldValue(key);
        if(! value.isEmpty()) {
            value = new String(VCardSamsungHelper.transliterateCyr2Lat(value));
            pickupAddressFeature(vCard, AddressParameterType.OTHER).setLocality(value);
        }
    }

    @SuppressWarnings("unused")
    private void setOtherPostalCode(Contact2k3 contact, VCard vCard, String key) throws FileViewException {
        String value = (String) contact.getFieldValue(key);
        if(! value.isEmpty()) {
            value = new String(VCardSamsungHelper.transliterateCyr2Lat(value));
            pickupAddressFeature(vCard, AddressParameterType.OTHER).setPostalCode(value);
        }
    }

    @SuppressWarnings("unused")
    private void setOtherCountryRegion(Contact2k3 contact, VCard vCard, String key) throws FileViewException {
        String value = (String) contact.getFieldValue(key);
        if(! value.isEmpty()) {
            value = new String(VCardSamsungHelper.transliterateCyr2Lat(value));
            pickupAddressFeature(vCard, AddressParameterType.OTHER).setCountryName(value);
        }
    }

    @SuppressWarnings("unused")
    private void setAssistantsPhone(Contact2k3 contact, VCard vCard, String key) {
        String value = (String) contact.getFieldValue(key);
        if(! value.isEmpty()) {
            ArrayList<TelephoneParameterType> phoneTypesList
                = new ArrayList<TelephoneParameterType>();
            phoneTypesList.add(TelephoneParameterType.NON_STANDARD);
            setPhoneUniversal(vCard, value, phoneTypesList);
        }
    }

    @SuppressWarnings("unused")
    private void setBusinessFax(Contact2k3 contact, VCard vCard, String key) {
        String value = (String) contact.getFieldValue(key);
        if(! value.isEmpty()) {
            ArrayList<TelephoneParameterType> phoneTypesList
                = new ArrayList<TelephoneParameterType>();
            phoneTypesList.add(TelephoneParameterType.FAX);
            phoneTypesList.add(TelephoneParameterType.WORK);
            setPhoneUniversal(vCard, value, phoneTypesList);
        }
    }

    @SuppressWarnings("unused")
    private void setBusinessPhone1(Contact2k3 contact, VCard vCard, String key) {
        String value = (String) contact.getFieldValue(key);
        if(! value.isEmpty()) {
            ArrayList<TelephoneParameterType> phoneTypesList
                = new ArrayList<TelephoneParameterType>();
            phoneTypesList.add(TelephoneParameterType.WORK);
            setPhoneUniversal(vCard, value, phoneTypesList);
        }
    }

    @SuppressWarnings("unused")
    private void setBusinessPhone2(Contact2k3 contact, VCard vCard, String key) {
        String value = (String) contact.getFieldValue(key);
        if(! value.isEmpty()) {
            ArrayList<TelephoneParameterType> phoneTypesList
                = new ArrayList<TelephoneParameterType>();
            phoneTypesList.add(TelephoneParameterType.WORK);
            setPhoneUniversal(vCard, value, phoneTypesList);
        }
    }

    @SuppressWarnings("unused")
    private void setCallback(Contact2k3 contact, VCard vCard, String key) {
        String value = (String) contact.getFieldValue(key);
        if(! value.isEmpty()) {
            ArrayList<TelephoneParameterType> phoneTypesList
                = new ArrayList<TelephoneParameterType>();
            phoneTypesList.add(TelephoneParameterType.OTHER);
            setPhoneUniversal(vCard, value, phoneTypesList);
        }
    }

    @SuppressWarnings("unused")
    private void setCarPhone(Contact2k3 contact, VCard vCard, String key) {
        String value = (String) contact.getFieldValue(key);
        if(! value.isEmpty()) {
            ArrayList<TelephoneParameterType> phoneTypesList
                = new ArrayList<TelephoneParameterType>();
            phoneTypesList.add(TelephoneParameterType.CAR);
            setPhoneUniversal(vCard, value, phoneTypesList);
        }
    }

    @SuppressWarnings("unused")
    private void setCompanyMainPhone(Contact2k3 contact, VCard vCard, String key) {
        String value = (String) contact.getFieldValue(key);
        if(! value.isEmpty()) {
            ArrayList<TelephoneParameterType> phoneTypesList
                = new ArrayList<TelephoneParameterType>();
            phoneTypesList.add(TelephoneParameterType.WORK);
            phoneTypesList.add(TelephoneParameterType.PREF);
            setPhoneUniversal(vCard, value, phoneTypesList);
        }
    }

    @SuppressWarnings("unused")
    private void setHomeFax(Contact2k3 contact, VCard vCard, String key) {
        String value = (String) contact.getFieldValue(key);
        if(! value.isEmpty()) {
            ArrayList<TelephoneParameterType> phoneTypesList
                = new ArrayList<TelephoneParameterType>();
            phoneTypesList.add(TelephoneParameterType.FAX);
            phoneTypesList.add(TelephoneParameterType.HOME);
            setPhoneUniversal(vCard, value, phoneTypesList);
        }
    }

    @SuppressWarnings("unused")
    private void setHomePhone1(Contact2k3 contact, VCard vCard, String key) {
        String value = (String) contact.getFieldValue(key);
        if(! value.isEmpty()) {
            ArrayList<TelephoneParameterType> phoneTypesList
                = new ArrayList<TelephoneParameterType>();
            phoneTypesList.add(TelephoneParameterType.HOME);
            setPhoneUniversal(vCard, value, phoneTypesList);
        }
    }

    @SuppressWarnings("unused")
    private void setHomePhone2(Contact2k3 contact, VCard vCard, String key) {
        String value = (String) contact.getFieldValue(key);
        if(! value.isEmpty()) {
            ArrayList<TelephoneParameterType> phoneTypesList
                = new ArrayList<TelephoneParameterType>();
            phoneTypesList.add(TelephoneParameterType.HOME);
            setPhoneUniversal(vCard, value, phoneTypesList);
        }
    }

    @SuppressWarnings("unused")
    private void setISDN(Contact2k3 contact, VCard vCard, String key) {
        String value = (String) contact.getFieldValue(key);
        if(! value.isEmpty()) {
            ArrayList<TelephoneParameterType> phoneTypesList
                = new ArrayList<TelephoneParameterType>();
            phoneTypesList.add(TelephoneParameterType.ISDN);
            setPhoneUniversal(vCard, value, phoneTypesList);
        }
    }

    @SuppressWarnings("unused")
    private void setMobilePhone(Contact2k3 contact, VCard vCard, String key) {
        String value = (String) contact.getFieldValue(key);
        if(! value.isEmpty()) {
            ArrayList<TelephoneParameterType> phoneTypesList
                = new ArrayList<TelephoneParameterType>();
            phoneTypesList.add(TelephoneParameterType.CELL);
            setPhoneUniversal(vCard, value, phoneTypesList);
        }
    }

    @SuppressWarnings("unused")
    private void setOtherFax(Contact2k3 contact, VCard vCard, String key) {
        String value = (String) contact.getFieldValue(key);
        if(! value.isEmpty()) {
            ArrayList<TelephoneParameterType> phoneTypesList
                = new ArrayList<TelephoneParameterType>();
            phoneTypesList.add(TelephoneParameterType.FAX);
            phoneTypesList.add(TelephoneParameterType.OTHER);
            setPhoneUniversal(vCard, value, phoneTypesList);
        }
    }

    @SuppressWarnings("unused")
    private void setOtherPhone(Contact2k3 contact, VCard vCard, String key) {
        String value = (String) contact.getFieldValue(key);
        if(! value.isEmpty()) {
            ArrayList<TelephoneParameterType> phoneTypesList
                = new ArrayList<TelephoneParameterType>();
            phoneTypesList.add(TelephoneParameterType.OTHER);
            setPhoneUniversal(vCard, value, phoneTypesList);
        }
    }

    @SuppressWarnings("unused")
    private void setPager(Contact2k3 contact, VCard vCard, String key) {
        String value = (String) contact.getFieldValue(key);
        if(! value.isEmpty()) {
            ArrayList<TelephoneParameterType> phoneTypesList
                = new ArrayList<TelephoneParameterType>();
            phoneTypesList.add(TelephoneParameterType.PAGER);
            setPhoneUniversal(vCard, value, phoneTypesList);
        }
    }

    @SuppressWarnings("unused")
    private void setPrimaryPhone(Contact2k3 contact, VCard vCard, String key) {
        String value = (String) contact.getFieldValue(key);
        if(! value.isEmpty()) {
            ArrayList<TelephoneParameterType> phoneTypesList
                = new ArrayList<TelephoneParameterType>();
            phoneTypesList.add(TelephoneParameterType.PREF);
            setPhoneUniversal(vCard, value, phoneTypesList);
        }
    }

    @SuppressWarnings("unused")
    private void setRadioPhone(Contact2k3 contact, VCard vCard, String key) {
        String value = (String) contact.getFieldValue(key);
        if(! value.isEmpty()) {
            ArrayList<TelephoneParameterType> phoneTypesList
                = new ArrayList<TelephoneParameterType>();
            phoneTypesList.add(TelephoneParameterType.NON_STANDARD);
            setPhoneUniversal(vCard, value, phoneTypesList);
        }
    }

    @SuppressWarnings("unused")
    private void setTTYTDDPhone(Contact2k3 contact, VCard vCard, String key) {
        String value = (String) contact.getFieldValue(key);
        if(! value.isEmpty()) {
            ArrayList<TelephoneParameterType> phoneTypesList
                = new ArrayList<TelephoneParameterType>();
            phoneTypesList.add(TelephoneParameterType.NON_STANDARD);
            setPhoneUniversal(vCard, value, phoneTypesList);
        }
    }

    @SuppressWarnings("unused")
    private void setTelex(Contact2k3 contact, VCard vCard, String key) {
        String value = (String) contact.getFieldValue(key);
        if(! value.isEmpty()) {
            ArrayList<TelephoneParameterType> phoneTypesList
                = new ArrayList<TelephoneParameterType>();
            phoneTypesList.add(TelephoneParameterType.NON_STANDARD);
            setPhoneUniversal(vCard, value, phoneTypesList);
        }
    }

    @SuppressWarnings("unused")
    private String setAccount(Contact2k3 contact, VCard vCard, String key) {
        String value = (String) contact.getFieldValue(key);
        if(! value.isEmpty()) {
            return "account - " + value + "\n";
        }
        return null ;
    }

    @SuppressWarnings("unused")
    private String setAnniversary(Contact2k3 contact, VCard vCard, String key) {
        String value = (String) contact.getFieldValue(key);
        if(! value.isEmpty()) {
            return "anniversary - " + value + "\n";
        }
        return null ;
    }

    @SuppressWarnings("unused")
    private String setAssistantsName(Contact2k3 contact, VCard vCard, String key) {
        String value = (String) contact.getFieldValue(key);
        if(! value.isEmpty()) {
            /* This is AgentFeature assistants = new AgentType(); */
            return "assistant name - " + value + "\n";
        }
        return null ;
    }

    @SuppressWarnings("unused")
    private String setBillingInformation(Contact2k3 contact, VCard vCard, String key) {
        String value = (String) contact.getFieldValue(key);
        if(! value.isEmpty()) {
            return "billing name - " + value + "\n";
        }
        return null ;
    }

    @SuppressWarnings("unused")
    private void setBirthday(Contact2k3 contact, VCard vCard, String key) throws FileViewException {
        String value = (String) contact.getFieldValue(key);
        if(! value.isEmpty()) {
            // XXX - Here is a bug with date format and XLS cell content.
            // XXX - Try to convert in XLS file cell's into "text".
            //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateFormatString = "d.M.y";
            SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatString);
            Date date = null;
            try {
                date = dateFormat.parse(value);
            } catch (ParseException e) {
                LOG.error(String.format(
                        "Failed with date parsing, wanted format is '"
                        + dateFormatString
                        + "'. Input value is '%s'"
                        + " from field '%s'. Problem caused with: %s",
                                value, key, contact.toString()));
                e.printStackTrace();
                return;
            }
            
            Calendar birthday = Calendar.getInstance();
            birthday.clear();
            birthday.setTime(date);
//            birthday.set(Calendar.YEAR, 1001);
//            birthday.set(Calendar.MONTH, 1);
//            birthday.set(Calendar.DAY_OF_MONTH, 1);
            vCard.setBirthday(new BirthdayType(birthday));
        }
    }

    @SuppressWarnings("unused")
    private void setBusinessAddressPOBox(Contact2k3 contact, VCard vCard, String key) throws FileViewException {
        String value = (String) contact.getFieldValue(key);
        if(! value.isEmpty()) {
            value = new String(VCardSamsungHelper.transliterateCyr2Lat(value));
            pickupAddressFeature(vCard, AddressParameterType.WORK).setPostOfficeBox(value);
        }
    }

    @SuppressWarnings("unused")
    private void setCategories(Contact2k3 contact, VCard vCard, String key) {
        String value = (String) contact.getFieldValue(key);
        if(! value.isEmpty()) {
            CategoriesFeature categories = new CategoriesType();
            // XXX - need parser for model's text string.
            categories.addCategory(value);
            vCard.setCategories(categories);
        }
    }

    @SuppressWarnings("unused")
    private String setChildren(Contact2k3 contact, VCard vCard, String key) {
        String value = (String) contact.getFieldValue(key);
        if(! value.isEmpty()) {
            return "children - " + value + "\n";
        }
        return null ;
    }

    @SuppressWarnings("unused")
    private void setDirectoryServer(Contact2k3 contact, VCard vCard, String key) {
        String value = (String) contact.getFieldValue(key);
        if(! value.isEmpty()) {
            try {
                vCard.addURL(new URLType(new URL(value)));
            } catch (MalformedURLException e) {
                LOG.error(String.format(
                        "Failed to add to vCard URL: '%s'.", value));
                e.printStackTrace();
            }
        }
    }
    
    @SuppressWarnings("unused")
    private void setEmail1Address(Contact2k3 contact, VCard vCard, String key) {
        String value = (String) contact.getFieldValue(key);
        if(! value.isEmpty()) {
            setEmailUniversal(vCard, eMail1, value);
        }
    }
    
    @SuppressWarnings("unused")
    private String setEmail1Type(Contact2k3 contact, VCard vCard, String key) {
        String value = (String) contact.getFieldValue(key);
        if(! value.isEmpty()) {
            /*
             * Don't know about compatibility of MS and RFC. So the data will be
             * dropped to notes. 
             */
            return "email 1 type - " + value + "\n";
        }
        return null ;
    }

    @SuppressWarnings("unused")
    private String setEmail1DisplayName(Contact2k3 contact, VCard vCard, String key) {
        String value = (String) contact.getFieldValue(key);
        if(! value.isEmpty()) {
            /*
             * Don't know about compatibility of MS and RFC. So the data will be
             * dropped to notes. 
             */
            return "email 1 display name - " + value + "\n";
        }
        return null ;
    }

    @SuppressWarnings("unused")
    private void setEmail2Address(Contact2k3 contact, VCard vCard, String key) {
        String value = (String) contact.getFieldValue(key);
        if(! value.isEmpty()) {
            setEmailUniversal(vCard, eMail2, value);
        }
    }

    @SuppressWarnings("unused")
    private String setEmail2Type(Contact2k3 contact, VCard vCard, String key) {
        String value = (String) contact.getFieldValue(key);
        if(! value.isEmpty()) {
            /*
             * Don't know about compatibility of MS and RFC. So the data will be
             * dropped to notes. 
             */
            return "email 2 type - " + value + "\n";
        }
        return null ;
    }

    @SuppressWarnings("unused")
    private String setEmail2DisplayName(Contact2k3 contact, VCard vCard, String key) {
        String value = (String) contact.getFieldValue(key);
        if(! value.isEmpty()) {
            /*
             * Don't know about compatibility of MS and RFC. So the data will be
             * dropped to notes. 
             */
            return "email 2 display name - " + value + "\n";
        }
        return null ;
    }

    @SuppressWarnings("unused")
    private void setEmail3Address(Contact2k3 contact, VCard vCard, String key) {
        String value = (String) contact.getFieldValue(key);
        if(! value.isEmpty()) {
            setEmailUniversal(vCard, eMail3, value);
        }
    }

    @SuppressWarnings("unused")
    private String setEmail3Type(Contact2k3 contact, VCard vCard, String key) {
        String value = (String) contact.getFieldValue(key);
        if(! value.isEmpty()) {
            /*
             * Don't know about compatibility of MS and RFC. So the data will be
             * dropped to notes. 
             */
            return "email 3 type - " + value + "\n";
        }
        return null ;
    }

    @SuppressWarnings("unused")
    private String setEmail3DisplayName(Contact2k3 contact, VCard vCard, String key) {
        String value = (String) contact.getFieldValue(key);
        if(! value.isEmpty()) {
            /*
             * Don't know about compatibility of MS and RFC. So the data will be
             * dropped to notes. 
             */
            return "email 3 display name - " + value + "\n";
        }
        return null ;
    }

    @SuppressWarnings("unused")
    private String setGender(Contact2k3 contact, VCard vCard, String key) {
        String value = (String) contact.getFieldValue(key);
        if(! value.isEmpty()) {
            return "gender - " + value + "\n";
        }
        return null ;
    }

    @SuppressWarnings("unused")
    private String setGovernmentIDNumber(Contact2k3 contact, VCard vCard, String key) {
        String value = (String) contact.getFieldValue(key);
        if(! value.isEmpty()) {
            /*
             * UIDFeature may be used, but it is not exactly the case.
             */
            return "goverment ID number - " + value + "\n";
        }
        return null ;
    }

    @SuppressWarnings("unused")
    private String setHobby(Contact2k3 contact, VCard vCard, String key) {
        String value = (String) contact.getFieldValue(key);
        if(! value.isEmpty()) {
            return "hobby - " + value + "\n";
        }
        return null ;
    }

    @SuppressWarnings("unused")
    private void setHomeAddressPOBox(Contact2k3 contact, VCard vCard, String key) throws FileViewException {
        String value = (String) contact.getFieldValue(key);
        if(! value.isEmpty()) {
            value = new String(VCardSamsungHelper.transliterateCyr2Lat(value));
            pickupAddressFeature(vCard, AddressParameterType.HOME).setPostOfficeBox(value);
        }
    }

    @SuppressWarnings("unused")
    private void setInitials(Contact2k3 contact, VCard vCard, String key) {
        String value = (String) contact.getFieldValue(key);
        if(! value.isEmpty()) {
            NicknameFeature nicknames = new NicknameType();
            nicknames.addNickname(value);
            vCard.setNicknames(nicknames);
        }
    }

    @SuppressWarnings("unused")
    private String setInternetFreeBusy(Contact2k3 contact, VCard vCard, String key) {
        String value = (String) contact.getFieldValue(key);
        if(! value.isEmpty()) {
            return "internet free busy - " + value + "\n";
        }
        return null ;
    }

    @SuppressWarnings("unused")
    private String setKeywords(Contact2k3 contact, VCard vCard, String key) {
        String value = (String) contact.getFieldValue(key);
        if(! value.isEmpty()) {
            return "keywords - " + value + "\n";
        }
        return null ;
    }

    @SuppressWarnings("unused")
    private String setLanguage1(Contact2k3 contact, VCard vCard, String key) {
        String value = (String) contact.getFieldValue(key);
        if(! value.isEmpty()) {
            return "language - " + value + "\n";
        }
        return null ;
    }

    @SuppressWarnings("unused")
    private String setLocation(Contact2k3 contact, VCard vCard, String key) {
        String value = (String) contact.getFieldValue(key);
        if(! value.isEmpty()) {
            /* vCard.setGeographicPosition(new GeographicPositionType(1.2f, -3.4f));
             * but need a parser for subject. Dropping to notes.
             */
            return "location - " + value + "\n";
        }
        return null ;
    }

    @SuppressWarnings("unused")
    private String setManagersName(Contact2k3 contact, VCard vCard, String key) {
        String value = (String) contact.getFieldValue(key);
        if(! value.isEmpty()) {
            /* - Related to AgentFeature. */
            return "manager's name - " + value + "\n";
        }
        return null ;
    }

    @SuppressWarnings("unused")
    private String setMileage(Contact2k3 contact, VCard vCard, String key) {
        String value = (String) contact.getFieldValue(key);
        if(! value.isEmpty()) {
            return "mileage - " + value + "\n";
        }
        return null ;
    }

    @SuppressWarnings("unused")
    private String setNotes(Contact2k3 contact, VCard vCard, String key) {
        String value = (String) contact.getFieldValue(key);
        if(! value.isEmpty()) {
            return value + "\n\n";
        }
        return null;
    }

    @SuppressWarnings("unused")
    private String setOfficeLocation(Contact2k3 contact, VCard vCard, String key) {
        String value = (String) contact.getFieldValue(key);
        if(! value.isEmpty()) {
            return "office location - " + value + "\n";
        }
        return null ;
    }

    @SuppressWarnings("unused")
    private String setOrganizationalIDNumber(Contact2k3 contact, VCard vCard, String key) {
        String value = (String) contact.getFieldValue(key);
        if(! value.isEmpty()) {
            return "organizational ID number - " + value + "\n";
        }
        return null ;
    }

    @SuppressWarnings("unused")
    private void setOtherAddressPOBox(Contact2k3 contact, VCard vCard, String key) throws FileViewException {
        String value = (String) contact.getFieldValue(key);
        if(! value.isEmpty()) {
            value = new String(VCardSamsungHelper.transliterateCyr2Lat(value));
            pickupAddressFeature(vCard, AddressParameterType.OTHER).setPostOfficeBox(value);
        }
    }

    @SuppressWarnings("unused")
    private String setPriority(Contact2k3 contact, VCard vCard, String key) {
        String value = (String) contact.getFieldValue(key);
        if(! value.isEmpty()) {
            return "priority - " + value + "\n";
        }
        return null ;
    }

    @SuppressWarnings("unused")
    private String setPrivateFlag(Contact2k3 contact, VCard vCard, String key) {
        String value = (String) contact.getFieldValue(key);
        if(! value.isEmpty()) {
            return "private flag - " + value + "\n";
        }
        return null ;
    }

    @SuppressWarnings("unused")
    private void setProfession(Contact2k3 contact, VCard vCard, String key) {
        String value = (String) contact.getFieldValue(key);
        if(! value.isEmpty()) {
            vCard.setTitle(new TitleType((String) contact.getFieldValue(key)));
        }
    }

    @SuppressWarnings("unused")
    private String setReferredBy(Contact2k3 contact, VCard vCard, String key) {
        String value = (String) contact.getFieldValue(key);
        if(! value.isEmpty()) {
            return "referred by - " + value + "\n";
        }
        return null ;
    }

    @SuppressWarnings("unused")
    private String setSensitivity(Contact2k3 contact, VCard vCard, String key) {
        String value = (String) contact.getFieldValue(key);
        if(! value.isEmpty()) {
            return "sensitivity - " + value + "\n";
        }
        return null ;
    }

    @SuppressWarnings("unused")
    private String setSpouse(Contact2k3 contact, VCard vCard, String key) {
        String value = (String) contact.getFieldValue(key);
        if(! value.isEmpty()) {
            return "spouse - " + value + "\n";
        }
        return null ;
    }

    @SuppressWarnings("unused")
    private String setUser1(Contact2k3 contact, VCard vCard, String key) {
        String value = (String) contact.getFieldValue(key);
        if(! value.isEmpty()) {
            return "user 1 - " + value + "\n";
        }
        return null ;
    }

    @SuppressWarnings("unused")
    private String setUser2(Contact2k3 contact, VCard vCard, String key) {
        String value = (String) contact.getFieldValue(key);
        if(! value.isEmpty()) {
            return "user 2 - " + value + "\n";
        }
        return null ;
    }

    @SuppressWarnings("unused")
    private String setUser3(Contact2k3 contact, VCard vCard, String key) {
        String value = (String) contact.getFieldValue(key);
        if(! value.isEmpty()) {
            return "user 3 - " + value + "\n";
        }
        return null ;
    }

    @SuppressWarnings("unused")
    private String setUser4(Contact2k3 contact, VCard vCard, String key) {
        String value = (String) contact.getFieldValue(key);
        if(! value.isEmpty()) {
            return "user 4 - " + value + "\n";
        }
        return null ;
    }
    
    @SuppressWarnings("unused")
    private void setWebPage(Contact2k3 contact, VCard vCard, String key){
        String value = (String) contact.getFieldValue(key);
        if(! value.isEmpty()) {
            try {
                vCard.addURL(new URLType(new URL(value)));
            } catch (MalformedURLException e) {
                LOG.error(String.format(
                        "Failed to add into vCard URL: '%s'"
                        + " from field '%s'. Problem caused with: %s",
                                value, key, contact.toString()));
                e.printStackTrace();
            }
        }
    }

    
}
