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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataFormatter;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import ypcnv.contact2k3.Contact2k3;
import ypcnv.contact2k3.ContactsBook;
import ypcnv.converter.conf.DataSourceConf;
import ypcnv.errorCodes.ErrorCodes;
import ypcnv.exceptions.ContactException;
import ypcnv.exceptions.FileViewException;
import ypcnv.views.abstr.DataSourceAbstractImpl;
import ypcnv.views.ifLow.FileSystem;

/**
 * Data source - file system, regular file, XLS format.
 */
public class FileXLS extends DataSourceAbstractImpl implements FileSystem {

    /** Logger. */
    private static final Log LOG = LogFactory.getLog(DataSourceAbstractImpl.class);

    private FileInputStream fileStreamIn = null;
    private FileOutputStream fileStreamOut = null;

    private HSSFWorkbook xlsWorkbook = null;
    /** Edge of an interval with data. */
    private Integer startColumnIdx;
    /** Edge of an interval with data. */
    private Integer stopColumnIdx;

    /**
     * Mapping between data fields names(map keys) and their column number(map
     * values) in work sheet.
     */
    private HashMap<String, Integer> dataColumnsSequenceMap = null ;

    /**
     * @param configuration - data source configuration.
     */
    public FileXLS(DataSourceConf configuration) {
        super(configuration);
        build(configuration); // XXX - seems the arguments not always used. Remove them?
    }

    /* (non-Javadoc)
     * @see ypcnv.outglook.views.DataSource#pullData()
     */
    @Override
    public void pullData() throws FileViewException {
        dataContainer = new ContactsBook();
        HSSFRow headerRow = findHeaderRow(xlsWorkbook);
        validateHeaderNames(headerRow);
        insertDataToModel(headerRow);
        LOG.debug("Model is set using a view.");
    }

    /* (non-Javadoc)
     * @see ypcnv.outglook.views.DataSource#pushToStorage()
     */
    @Override
    public void pushData() throws FileViewException {
        HSSFRow headerRow = findHeaderRow(xlsWorkbook);
        validateHeaderNames(headerRow);
        insertDataToWorkbook(headerRow);
        if(!dumpToFile()) {
            LOG.error("Failed to save into file '" + address.toString() + "'.");
        } else {
            LOG.debug("View is set using a model.");
        }
    }

    /* (non-Javadoc)
     * @see ypcnv.views.other.DataContainer#build(ypcnv.converter.conf.ConfsFactory)
     */
    @Override
    public void build(DataSourceConf userInput) {
        dataColumnsSequenceMap = new HashMap<String, Integer>();
        tryGetWorkbook();
        try {
            pullData();
        } catch (FileViewException e) {
            LOG.error(FileXLSMeta.ERR_MESSAGE_FAILED_TO_PULL_FROM_STORAGE
                    + " Error token: "
                    + this.toString());
            e.printStackTrace();
        }
    }

    /**
     * Obtain workbook object instance.
     * @throws InvalidFormatException
     */
    private void tryGetWorkbook() {
        if (xlsWorkbook != null) {
            //xlsWorkbook.setMissingCellPolicy(Row.CREATE_NULL_AS_BLANK);
            return;
        }

        try {
            if( ((File)address).exists()) {
                fileStreamIn = new FileInputStream(((File)address));
                xlsWorkbook = (HSSFWorkbook) WorkbookFactory
                        .create(fileStreamIn);
            } else {
                xlsWorkbook = new HSSFWorkbook();
                fileStreamOut = new FileOutputStream(((File)address));
                xlsWorkbook.write(fileStreamOut);
            }

            if(xlsWorkbook.getNumberOfSheets() == 0 ) {
                long timeStamp = new Date().getTime();
                xlsWorkbook.createSheet("ypcnv" + timeStamp);
            }
            //xlsWorkbook.setMissingCellPolicy(Row.CREATE_NULL_AS_BLANK);

        } catch (FileNotFoundException e) {
            LOG.error(String.format(FileXLSMeta.ERR_MESSAGE_FILE_ACCESS,
                    ((File)address).getAbsoluteFile()));
            e.printStackTrace();
            System.exit(ErrorCodes.ErrorMisc.get());
        } catch (IOException e) {
            LOG.error(String.format(FileXLSMeta.ERR_MESSAGE_FILE_ACCESS,
                    ((File)address).getAbsoluteFile()));
            e.printStackTrace();
            System.exit(ErrorCodes.ErrorMisc.get());
        } catch (InvalidFormatException e) {
            LOG.error(String.format(FileXLSMeta.ERR_MESSAGE_WORKBOOK_WRONG_FORMAT,
                    ((File)address).getAbsoluteFile()));
            e.printStackTrace();
            System.exit(ErrorCodes.ErrorMisc.get());
        } finally {
            if (fileStreamIn != null) {
                try {
                    fileStreamIn.close();
                } catch (IOException e) {
                    LOG.error(String.format(
                            "Failed to close input stream for file '%s'.",
                            ((File)address).getAbsoluteFile()));
                    e.printStackTrace();
                }
            }
            if (fileStreamOut != null) {
                try {
                    fileStreamOut.close();
                } catch (IOException e) {
                    LOG.error(String.format(
                            "Failed to close output stream for file '%s'.",
                            ((File)address).getAbsoluteFile()));
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Check whether or not the given row's cells content have all the expected
     * names of data fields. <br>
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
                || foundQuantityOfDataFields != FileXLSMeta.NUMBER_OF_DATA_FIELDS) {
            String message = String.format(FileXLSMeta.ERR_MESSAGE_WRONG_FIELD_QUANTITY,
                    ((File)address).getAbsoluteFile(), FileXLSMeta.NUMBER_OF_DATA_FIELDS);
            LOG.error(message);
            throw new FileViewException(null,
                    "Contact2k3Xls.checkHeaderNames()", message);
        }
        /* Whether data fields names are valid. */
        for (int idx = startColumnIdx; idx <= stopColumnIdx; idx++) {
            HSSFCell currentCell = headerRow.getCell(idx);
            String xlsFieldName = currentCell.getStringCellValue();
            String modelDataFieldName = null;
            
            modelDataFieldName = FileXLSNames.CONTAINER_FIELD_NAMING_MAP
                    .get(xlsFieldName);

            if(modelDataFieldName == null) {
                ArrayList<String> foreignNamesSearchResultList = new ArrayList<String>();

                /*
                 * Add one by one mappings of other localizations, or add
                 * explicit locality flag support.
                 */
                foreignNamesSearchResultList
                    .add(FileXLSNames.CONTAINER_FIELD_NAMING_MAP_RUS2ENGL.get(xlsFieldName));
                
                Iterator<String> namesWereFoundListIterator
                                    = foreignNamesSearchResultList.iterator();
                while(namesWereFoundListIterator.hasNext()) {
                    String foundForeignName = namesWereFoundListIterator.next();
                    if(foundForeignName != null) {
                        modelDataFieldName = FileXLSNames.CONTAINER_FIELD_NAMING_MAP
                                .get(foundForeignName);
                    }
                }
            }
            
            if (modelDataFieldName != null) {
                dataColumnsSequenceMap.put(modelDataFieldName, idx);
            } else {
                String message = String.format(FileXLSMeta.ERR_MESSAGE_WRONG_FIELD_NAME,
                        ((File)address).getAbsoluteFile(), xlsFieldName);
                throw new FileViewException(null,
                        "Contact2k3Xls.checkHeaderNames()", message);
            }
        }
    }
    
    /**
     * Read data row by row and put it into model.
     * 
     * @param headerRow
     *            - row previous to row with real data, in fact this is row with
     *            columns headers.
     * @throws ContactException
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
                        .getCell(cellIdx, Row.RETURN_BLANK_AS_NULL);
                
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
                } catch (ContactException e) {
                    LOG.error(String.format(FileXLSMeta.ERR_MESSAGE_NO_KEY_IN_MAP,
                            dataFieldKeyName));
                    e.printStackTrace();
                }
            }
            if(! isVoidContact) {
                dataContainer.add(currentContact);
            }
        }
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
            String message = String
                    .format("There is more than one worksheet in workbook file '%s'. Will use the first one.",
                            ((File)address).getAbsoluteFile());
            LOG.info(message);
        }

        int choosenSheetIdx = 0; // zero based
        int headerRowIdx = 0; // zero based

        HSSFSheet currentSheet = xlsWorkbook.getSheetAt(choosenSheetIdx);

        if (currentSheet.getPhysicalNumberOfRows() == 0) {
            createHeaderRow(currentSheet, headerRowIdx);
        }

        HSSFRow headerRow = currentSheet.getRow(headerRowIdx);
        if (headerRow == null) {
            String message = String.format(FileXLSMeta.ERR_MESSAGE_NO_HEADER_ROW,
                    choosenSheetIdx, headerRowIdx,
                    ((File)address).getAbsoluteFile());
            LOG.error(message);
            throw new FileViewException(null,
                    "Contact2k3Xls.FileViewException()", message);
        }
        return headerRow;
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
        Iterator<String> CONTAINER_FIELD_NAMING_MAP_Iterator = FileXLSNames.CONTAINER_FIELD_NAMING_MAP
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
     * Insert data from model into workbook object instance.
     * 
     * @param headerRow
     *            - row previous to row with real data, in fact this is row with
     *            columns headers.
     */
    private void insertDataToWorkbook(HSSFRow headerRow) {
        Contact2k3 currentContact;
        Iterator<Contact2k3> containerModelListIterator = dataContainer.getContacts().iterator(); 
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

    /** Save to file containers and buffers. */
    private Boolean dumpToFile() {
        Boolean haveErrors = false ;
        try {
            fileStreamOut = new FileOutputStream(((File)address));
            xlsWorkbook.write(fileStreamOut);
        } catch (IOException e) {
            String message = "Failed to write into file '"
                                                + address.toString() +"'.";
            LOG.error(message);
            e.printStackTrace();
            haveErrors = true ;
        } finally {
            if (fileStreamOut != null) {
                try {
                    fileStreamOut.close();
                } catch (IOException e) {
                    LOG.error(String.format(
                            "Failed to close output stream for file '%s'.",
                            address.toString()));
                    e.printStackTrace();
                    haveErrors = true ;
                }
            }
        }
        return ! haveErrors ;
    }

}
