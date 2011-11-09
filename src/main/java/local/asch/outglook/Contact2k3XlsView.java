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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import local.asch.outglook.exceptions.Contact2k3Exception;
import local.asch.outglook.exceptions.FileViewException;


import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataFormatter;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * @version 2011-11-08_19-08
 * 
 */

public class Contact2k3XlsView extends Contact2k3FileView {

    /** Logger. */
    private static final Logger LOG = Logger.getLogger(Contact2k3XlsView.class);

    /* Error messages. */
    private static final String ERR_MESSAGE_FILE_OPEN = "Failed with file '%s'.";
    private static final String ERR_MESSAGE_FILE_SAVE = "Failed with file '%s'.";
    private static final String ERR_MESSAGE_WORKBOOK_WRONG_FORMAT = "Failed with file '%s'.";
    private static final String ERR_MESSAGE_WRONG_FIELD_QUANTITY = "In workbook file '%s' number of data fields in header row does not equals to expected value '%d'.";
    private static final String ERR_MESSAGE_WRONG_FIELD_NAME = "In workbook file '%s' in a row with data fields headers/names found unexpected name '%s'.";
    private static final String ERR_MESSAGE_NO_KEY_IN_MAP = "There is no such a key '%s' in map." ;
    
    public static final int NUMBER_OF_DATA_FIELDS = 92;

    private HSSFWorkbook xlsWorkbook = null;
    /** Edge of an interval with data. */
    private Integer startColumnIdx;
    /** Edge of an interval with data. */
    private Integer stopColumnIdx;

    /**
     * Mapping between data fileds names(map keys) and their column number(map
     * values) in work sheet.
     */
    private HashMap<String, Integer> dataColumnsSequenceMap = new HashMap<String, Integer>();

    /**
     * Mapping of the model fields names to container's fields names. <b>Key</b>
     * - container's field name as it was set by MS-Outlook inside XLS contacts
     * container's in header row,<br>
     * <b>value</b> - model's class field name.<br>
     * <br>
     */
    private static final Map<String, String> CONTAINER_FIELD_NAMING_MAP = Collections
            .unmodifiableMap(new HashMap<String, String>() {
                private static final long serialVersionUID = -6949471211802960406L;

                {
                    put("Title", "title");
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

    /**
     * @param aContactList
     *            - aContactList
     * @param fileNameForUse
     *            - fileNameForUse
     * @throws InvalidFormatException 
     * @throws IOException
     */
    Contact2k3XlsView(ArrayList<Contact2k3> aContactList,
            File fileNameForUse) throws InvalidFormatException {
        super(aContactList, fileNameForUse);
        tryGetWorkbook();
    }

    /**
     * {@inheritDoc}
     */
    public void set(ArrayList<Contact2k3> contactList) {
    }

    /**
     * {@inheritDoc}
     */
    public void add(Contact2k3 contact) {
    }

    /**
     * {@inheritDoc}
     */
    public void add(ArrayList<Contact2k3> contactList) {
    }

    /**
     * {@inheritDoc}
     * 
     * @throws IOException
     * @see local.asch.outglook.Contact2k3FileView#setView()
     */
    public void setView() {
        /*
         * TODO Write down into file
         */
        try {
            saveToFile();
        } catch (IOException e) {
            LOG.error(String.format(ERR_MESSAGE_FILE_SAVE,
                    containerFileName.getAbsoluteFile()));
            e.printStackTrace();
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @throws FileViewException
     * @see local.asch.outglook.Contact2k3FileView#getView()
     */
    public void getView() throws FileViewException {
        if (xlsWorkbook.getNumberOfSheets() > 1) {
            LOG.info(String
                    .format("In workbook file '%s' there is more than one worksheet. Will use the first one.",
                            containerFileName.getAbsoluteFile()));
        }
        int currentSheetIdx = 0 ;

        int headerRowIdx = 0 ;
        HSSFSheet currentSheet = xlsWorkbook.getSheetAt(currentSheetIdx);
        HSSFRow headerRow = currentSheet.getRow(headerRowIdx);
        
        validateHeaderNames(headerRow);
        readDataToModel(headerRow);
    }

    /**
     * If workbook was not already created, then try to create instance.
     * @throws InvalidFormatException 
     */
    private void tryGetWorkbook() throws InvalidFormatException {
        if (xlsWorkbook != null) {
            return;
        }

        try {
            containerFileStreamIn = new FileInputStream(containerFileName);
            xlsWorkbook = (HSSFWorkbook) WorkbookFactory
                    .create(containerFileStreamIn);
        } catch (FileNotFoundException e) {
            LOG.error(String.format(ERR_MESSAGE_FILE_OPEN,
                    containerFileName.getAbsoluteFile()));
            e.printStackTrace();
        } catch (IOException e) {
            LOG.error(String.format(ERR_MESSAGE_FILE_OPEN,
                    containerFileName.getAbsoluteFile()));
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            
            System.out.println(String.format(ERR_MESSAGE_WORKBOOK_WRONG_FORMAT,
                    containerFileName.getAbsoluteFile()));
            
            LOG.error(String.format(ERR_MESSAGE_WORKBOOK_WRONG_FORMAT,
                    containerFileName.getAbsoluteFile()));
            e.printStackTrace();
        } finally {
            if (containerFileStreamIn != null) {
                try {
                    containerFileStreamIn.close();
                } catch (IOException e) {
                    LOG.error(String.format(
                            "Failed to close input stream for file '%s'.",
                            containerFileName.getAbsoluteFile()));
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @throws IOException
     */
    protected void saveToFile() throws IOException {
        // TODO - set workbook content from model
        try {
            containerFileStreamOut = new FileOutputStream(containerFileName);
            xlsWorkbook.write(containerFileStreamOut);
        } catch (FileNotFoundException e) {
            LOG.error(String.format(ERR_MESSAGE_FILE_OPEN,
                    containerFileName.getAbsoluteFile()));
            e.printStackTrace();
        } finally {
            containerFileStreamOut.close();
        }

    }

    /**
     * Check whether or not the given row's cells content of have all the
     * expected names of data fields. <br>
     * <br>
     * XLS workbooks generated by MS-Outlook while contacts export all ways have
     * fixed number of data fields.
     * 
     * @param headerRow
     *            - row to be processed.
     * @throws FileViewException
     *             in case check is not passed.
     */
    private void validateHeaderNames(HSSFRow headerRow)
            throws FileViewException {
        startColumnIdx = (int) headerRow.getFirstCellNum();
        /*
         * It's Apache POI feature - getLastCellNum gets the index of the last
         * cell contained in this row PLUS ONE.
         */
        stopColumnIdx = -1 + (int) headerRow.getLastCellNum();

        /* Whether quantity of fields is valid. */
        int foundQuantityOfDataFields = 1 + stopColumnIdx - startColumnIdx;
        if (foundQuantityOfDataFields != Contact2k3.getDataFieldsQuantity()
                || foundQuantityOfDataFields != NUMBER_OF_DATA_FIELDS) {
            String message = String.format(ERR_MESSAGE_WRONG_FIELD_QUANTITY,
                    containerFileName.getAbsoluteFile(), NUMBER_OF_DATA_FIELDS);
            throw new FileViewException(null,
                    "Contact2k3XlsView.checkHeaderNames()", message);
        }
        /* Whether data fields names are valid. */
        for (int idx = startColumnIdx; idx <= stopColumnIdx; idx++) {
            HSSFCell currentCell = headerRow.getCell(idx);
            String xlsFieldName = currentCell.getStringCellValue();
            String dataFieldKeyName = CONTAINER_FIELD_NAMING_MAP
                    .get(xlsFieldName);
            if (dataFieldKeyName != null) {
                dataColumnsSequenceMap.put(dataFieldKeyName, idx);
            } else {
                String message = String.format(ERR_MESSAGE_WRONG_FIELD_NAME,
                        containerFileName.getAbsoluteFile(), xlsFieldName);
                throw new FileViewException(null,
                        "Contact2k3XlsView.checkHeaderNames()", message);
            }
        }
    }

    /**
     * Read data row by row and put it into model.
     * 
     * @param headerRow
     *            - row previous to row with real data, in fact this is row with
     *            columns headers.
     * @throws Contact2k3Exception
     */
    private void readDataToModel(HSSFRow headerRow) {
        HSSFSheet currentSheet = headerRow.getSheet();
        int lastRowIdx = currentSheet.getLastRowNum();
        int currentRowIdx = headerRow.getRowNum() + 1;
        for (; currentRowIdx <= lastRowIdx; currentRowIdx++) {
            Contact2k3 currentContact = new Contact2k3();

            for (String dataFieldKeyName : currentContact.getFieldValuesMap()
                    .keySet()) {
                int cellIdx = dataColumnsSequenceMap.get(dataFieldKeyName);
                String cellValue = getCellContent(currentSheet.getRow(
                        currentRowIdx).getCell(cellIdx));
                try {
                    currentContact.setValue(dataFieldKeyName, cellValue);
                } catch (Contact2k3Exception e) {
                    LOG.error(String.format(ERR_MESSAGE_NO_KEY_IN_MAP,
                            dataFieldKeyName));
                    e.printStackTrace();
                }
            }
            containerModelList.add(currentContact);
        }
    }

    /**
     * With respect to cell type get cell's content.
     * 
     * @param cell
     *            - cell to be processed.
     * @return Cell's content.
     */
    private String getCellContent(HSSFCell cell) {
        int foundCellType = cell.getCellType();
        String cellContent = null;

        switch (foundCellType) {
        case Cell.CELL_TYPE_STRING:
        case Cell.CELL_TYPE_FORMULA:
            cellContent = cell.getStringCellValue();
            break;
        case Cell.CELL_TYPE_BOOLEAN:
            cellContent = cell.getBooleanCellValue() ? "TRUE" : "FALSE";
            break;
        case Cell.CELL_TYPE_ERROR:
            byte xlsErrorCode = cell.getErrorCellValue();
            if (xlsErrorCode == 0) {
                cellContent = "";
            } else {
                cellContent = "XLS error code '" + xlsErrorCode + "'.";
                String message = "XLS cell type is 'ERROR', the XLS error code is '"
                        + xlsErrorCode
                        + "'."
                        + " The cell row="
                        + cell.getRowIndex()
                        + ", col="
                        + cell.getColumnIndex()
                        + ".";
                LOG.info(message);
            }
            break;
        case Cell.CELL_TYPE_NUMERIC:
            HSSFDataFormatter numericFormat = new HSSFDataFormatter();
            cellContent = numericFormat.formatCellValue(cell);
            // cellContent = cell.getNumericCellValue();
            break;
        default:
            cellContent = "";
            break;
        }
        return cellContent;
    }
}
