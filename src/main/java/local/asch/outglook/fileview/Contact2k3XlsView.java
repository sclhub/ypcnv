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

package local.asch.outglook.fileview;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import local.asch.outglook.Contact2k3;
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
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * @version 2011-11-08_19-08
 * 
 */

public class Contact2k3XlsView extends Contact2k3FileView
                               implements Contact2k3XlsViewNames {

    /** Logger. */
    private static final Logger LOG = Logger.getLogger(Contact2k3XlsView.class);

    /* Error messages. */
    private static final String ERR_MESSAGE_FILE_ACCESS = "Failed with file '%s'.";
    private static final String ERR_MESSAGE_FILE_SAVE = "Failed with file '%s'.";
    private static final String ERR_MESSAGE_NO_KEY_IN_MAP = "There is no such a key '%s' in map." ;
    private static final String ERR_MESSAGE_WORKBOOK_WRONG_FORMAT = "Failed with file '%s'.";
    private static final String ERR_MESSAGE_WRONG_FIELD_QUANTITY = "In workbook file '%s' number of data fields in header row does not equals to expected value '%d'.";
    private static final String ERR_MESSAGE_WRONG_FIELD_NAME = "In workbook file '%s' in a row with data fields headers/names found unexpected name '%s'.";
    private static final String ERR_MESSAGE_NO_HEADER_ROW = "Failed to locate on sheet num. '%d' row number '%d' of cells with data columns headers. Workbook from file '%s'.";
    
    public static final int NUMBER_OF_DATA_FIELDS = 92;

    private HSSFWorkbook xlsWorkbook = null;
    /** Edge of an interval with data. */
    private Integer startColumnIdx;
    /** Edge of an interval with data. */
    private Integer stopColumnIdx;

    /**
     * Mapping between data fields names(map keys) and their column number(map
     * values) in work sheet.
     */
    private HashMap<String, Integer> dataColumnsSequenceMap = new HashMap<String, Integer>();

    /**
     * @param aContactList
     *            - aContactList
     * @param fileNameForUse
     *            - fileNameForUse
     * @throws InvalidFormatException 
     */
    public Contact2k3XlsView(ArrayList<Contact2k3> aContactList,
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
     * @throws FileViewException 
     * @throws IOException
     */
    public void setView() throws FileViewException, IOException {
        HSSFRow headerRow = findHeaderRow(xlsWorkbook);
        validateHeaderNames(headerRow);
        insertDataToWorkbook(headerRow);
        saveToFile();
    }

    /**
     * {@inheritDoc}
     * @throws FileViewException
     */
    public void getView() throws FileViewException {
        HSSFRow headerRow = findHeaderRow(xlsWorkbook);
        validateHeaderNames(headerRow);
        insertDataToModel(headerRow);
    }

    /**
     * Search for an a row with the data columns headers.
     * 
     * @param workbook
     *            - where to search.
     * @return The row number (zero based).
     * @throws FileViewException
     */
    private HSSFRow findHeaderRow(HSSFWorkbook workbook)
            throws FileViewException {
        if (xlsWorkbook.getNumberOfSheets() > 1) {
            LOG.info(String
                    .format("In workbook file '%s' there is more than one worksheet. Will use the first one.",
                            containerFileName.getAbsoluteFile()));
        }

        int choosenSheetIdx = 0; // zero based
        int headerRowIdx = 0; // zero based

        HSSFSheet currentSheet = xlsWorkbook.getSheetAt(choosenSheetIdx);

        if (currentSheet.getPhysicalNumberOfRows() == 0) {
            createHeaderRow(currentSheet, headerRowIdx);
        }

        HSSFRow headerRow = currentSheet.getRow(headerRowIdx);
        if (headerRow == null) {
            String message = String.format(ERR_MESSAGE_NO_HEADER_ROW,
                    choosenSheetIdx, headerRowIdx,
                    containerFileName.getAbsoluteFile());
            throw new FileViewException(null,
                    "Contact2k3XlsView.FileViewException()", message);
        }
        return headerRow;
    }
    
    /**
     * Create in a work sheet row of cells with headers of data columns.
     * 
     * @param currentSheet
     *            - worksheet where to create.
     * @param headerRowIdx
     *            - row number where to create, and which may should be created.
     */
    private void createHeaderRow(HSSFSheet currentSheet, int headerRowIdx) {
        HSSFRow headerRow;
        HSSFCell currentCell;
        Iterator<String> CONTAINER_FIELD_NAMING_MAP_Iterator = CONTAINER_FIELD_NAMING_MAP
                                                        .keySet().iterator();

        headerRow = currentSheet.createRow(headerRowIdx);
        int startColumnIdx = 0 ;
        for (int currentCellIdx = startColumnIdx;
                    CONTAINER_FIELD_NAMING_MAP_Iterator.hasNext();
                    currentCellIdx++) {
            currentCell = headerRow.createCell(currentCellIdx);
            currentCell.setCellType(Cell.CELL_TYPE_STRING);
            currentCell.setCellValue(CONTAINER_FIELD_NAMING_MAP_Iterator.next());
        }
    }

    /**
     * Obtain workbook object instance.
     * @throws InvalidFormatException
     */
    private void tryGetWorkbook() throws InvalidFormatException {
        if (xlsWorkbook != null) {
            //xlsWorkbook.setMissingCellPolicy(Row.CREATE_NULL_AS_BLANK);
            return;
        }

        try {
            if( containerFileName.exists()) {
                containerFileStreamIn = new FileInputStream(containerFileName);
                xlsWorkbook = (HSSFWorkbook) WorkbookFactory
                        .create(containerFileStreamIn);
            } else {
                xlsWorkbook = new HSSFWorkbook();
                containerFileStreamOut = new FileOutputStream(containerFileName);
                xlsWorkbook.write(containerFileStreamOut);
            }

            if(xlsWorkbook.getNumberOfSheets() == 0 ) {
                long timeStamp = new Date().getTime();
                xlsWorkbook.createSheet("ypcnv" + timeStamp);
            }
            //xlsWorkbook.setMissingCellPolicy(Row.CREATE_NULL_AS_BLANK);

        } catch (FileNotFoundException e) {
            LOG.error(String.format(ERR_MESSAGE_FILE_ACCESS,
                    containerFileName.getAbsoluteFile()));
            e.printStackTrace();
        } catch (IOException e) {
            LOG.error(String.format(ERR_MESSAGE_FILE_ACCESS,
                    containerFileName.getAbsoluteFile()));
            e.printStackTrace();
        } catch (InvalidFormatException e) {
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
            if (containerFileStreamOut != null) {
                try {
                    containerFileStreamOut.close();
                } catch (IOException e) {
                    LOG.error(String.format(
                            "Failed to close output stream for file '%s'.",
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
        try {
            containerFileStreamOut = new FileOutputStream(containerFileName);
            xlsWorkbook.write(containerFileStreamOut);
        } catch (FileNotFoundException e) {
            LOG.error(String.format(ERR_MESSAGE_FILE_ACCESS,
                    containerFileName.getAbsoluteFile()));
            e.printStackTrace();
        } catch (IOException e) {
            LOG.error(String.format(ERR_MESSAGE_FILE_SAVE,
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
     * fixed number of data fields.<br>
     * <br>
     * Obtain meta data concerning distribution of header names.
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
            String modelDataFieldName = null;
            
            modelDataFieldName = CONTAINER_FIELD_NAMING_MAP
                    .get(xlsFieldName);

            if(modelDataFieldName == null) {
                ArrayList<String> foreignNamesSearchResultList = new ArrayList<String>();

                /*
                 * Add one by one mappings of other localizations, or add
                 * explicit locality flag support.
                 */
                foreignNamesSearchResultList
                    .add(CONTAINER_FIELD_NAMING_MAP_RUS2ENGL.get(xlsFieldName));
                
                Iterator<String> namesWereFoundListIterator
                                    = foreignNamesSearchResultList.iterator();
                while(namesWereFoundListIterator.hasNext()) {
                    String foundForeignName = namesWereFoundListIterator.next();
                    if(foundForeignName != null) {
                        modelDataFieldName = CONTAINER_FIELD_NAMING_MAP
                                .get(foundForeignName);
                    }
                }
            }
            
            if (modelDataFieldName != null) {
                dataColumnsSequenceMap.put(modelDataFieldName, idx);
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
    private void insertDataToModel(HSSFRow headerRow) {
        HSSFSheet currentSheet = headerRow.getSheet();
        int lastRowIdx = currentSheet.getLastRowNum();
        int currentRowIdx = headerRow.getRowNum() + 1;
        for (; currentRowIdx <= lastRowIdx; currentRowIdx++) {
            Contact2k3 currentContact = new Contact2k3();
            Boolean isVoidContact = true ;
            for (String dataFieldKeyName : currentContact.getFieldValuesMap()
                    .keySet()) {
                int cellIdx = dataColumnsSequenceMap.get(dataFieldKeyName);

                HSSFCell cell = currentSheet
                                        .getRow(currentRowIdx)
                                        .getCell(cellIdx, Row.CREATE_NULL_AS_BLANK);
                
                String cellValue = null;
                if(cell != null) {
                    cellValue = getCellContent(cell);
                } else {
                    cellValue = "";
                }
                if(! cellValue.equals("")) {
                    isVoidContact = false ;
                }

                try {
                    currentContact.setValue(dataFieldKeyName, cellValue);
                } catch (Contact2k3Exception e) {
                    LOG.error(String.format(ERR_MESSAGE_NO_KEY_IN_MAP,
                            dataFieldKeyName));
                    e.printStackTrace();
                }
            }
            if(! isVoidContact) {
                containerModelList.add(currentContact);
            }
        }
    }

    /**
     * Insert data from model into workbook object instance.
     * 
     * @param headerRow
     *            - row previous to row with real data, in fact this is row with
     *            columns headers.
     */
    private void insertDataToWorkbook(HSSFRow headerRow) {
        Contact2k3 currentContact;
        Iterator<Contact2k3> containerModelListIterator = containerModelList.iterator(); 
        HSSFSheet currentSheet = headerRow.getSheet();
        HSSFRow currentRow;
        HSSFCell currentCell;
        int currentRowIdx = headerRow.getRowNum() + 1;
        int lastRowIdx = currentSheet.getLastRowNum();
        int currentColumnIdx; 

        // XXX - there is silent wipe of previous content of the sheet.
        for(int idx = currentRowIdx ;idx <= lastRowIdx; idx++) {
            currentSheet.removeRow(currentSheet.getRow(idx));
        }
        
        for (; containerModelListIterator.hasNext(); currentRowIdx++) {
            currentContact = containerModelListIterator.next();
            currentSheet.createRow(currentRowIdx);
            currentRow = currentSheet.getRow(currentRowIdx);
            for (String dataFieldKeyName : dataColumnsSequenceMap.keySet()) {
                currentColumnIdx = dataColumnsSequenceMap
                        .get(dataFieldKeyName);
                currentRow.createCell(currentColumnIdx);
                currentCell = currentRow.getCell(currentColumnIdx);
                currentCell.setCellType(Cell.CELL_TYPE_STRING);
                currentCell.setCellValue(currentContact.getFieldValuesMap()
                                                    .get(dataFieldKeyName));
            }
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
            cellContent = cell.getStringCellValue();
            break;
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
