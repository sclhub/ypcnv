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
package ypcnv.test;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Test;

import ypcnv.contact2k3.Contact2k3;
import ypcnv.contact2k3.ContactsBook;
import ypcnv.controller.ControllerContact2k3;
import ypcnv.converter.conf.DataSourceConf;
import ypcnv.exceptions.ContactException;
import ypcnv.exceptions.FileViewException;
import ypcnv.views.abstr.DataFormatID;
import ypcnv.views.abstr.Side;
import ypcnv.views.abstr.ViewFactory;
import ypcnv.views.ifHi.DataSource;

public class ControllerTest {
    
    /** Logger. */
    private static final Log LOG = LogFactory.getLog(ControllerTest.class);

    private ContactsBook contactBook = null ;
    private File testInputFile = null ;
    private File testOutputFile = null ;
    private String testFilePath ;

    public ControllerTest() {
        LOG.info("========> JUnit tests object created. <========");
        testFilePath =  System.getProperty("java.io.tmpdir")
                + System.getProperty("file.separator");
    }
    
    @Test
    public void testXlsExport() throws ContactException, IOException, FileViewException, CloneNotSupportedException, InvalidFormatException {
        contactBook = new ContactsBook();

        testInputFile = new File("src/test/resources/","example-phonebook.xls");
        testOutputFile = new File(testFilePath,"ypcnv-test");

        DataSourceConf outParams = new DataSourceConf(DataFormatID.VCF,
                testOutputFile, Side.destination);        

        DataSource dstObject = ViewFactory.get(outParams);
        
        ControllerContact2k3 controller = new ControllerContact2k3();
        insertFakeData(contactBook);
        controller.importData(contactBook);
        controller.export(dstObject);

        System.out.println("\n===> Have data:\n" + contactBook);
        System.out.println("\nSee : 'libreoffice \"" + testOutputFile + "\" &'.");
        System.out.println("\n---> DONE.\n");
    }

    @Test
    public void testXls2VcfExport() throws ContactException, IOException, FileViewException, CloneNotSupportedException, InvalidFormatException {
        contactBook = new ContactsBook();

        testInputFile = new File("src/test/resources/","example-phonebook.xls");
        testOutputFile = new File(testFilePath,"ypcnv-test");

        DataSourceConf inParams = new DataSourceConf(DataFormatID.XLS,
                testInputFile, Side.source); 

        DataSourceConf outParams = new DataSourceConf(DataFormatID.VCF,
                testOutputFile, Side.destination);        

        DataSource srcObject = ViewFactory.get(inParams);
        DataSource dstObject = ViewFactory.get(outParams);

        ControllerContact2k3 controller = new ControllerContact2k3();
        controller.importData(srcObject);
        controller.export(dstObject);

        System.out.println("\n===> Have data:\n"
                        + controller.getBook().toStringWithoutEmptyFields());
        System.out.println("\nSee : 'ls -ld \""
                        + controller.getViewFileName(DataFormatID.VCF)
                        + "\" && cat \""
                        + controller.getViewFileName(DataFormatID.VCF)
                        + File.separator
                        + "\"*'.");
        System.out.println("\n---> DONE.\n");
    }

    /**
     * @param contactBook
     * @throws ContactException
     */
    private void insertFakeData(ContactsBook contactBook)
            throws ContactException {
        Contact2k3 singleContact = null ;
        DateFormat dateFormat = new SimpleDateFormat("mmss.SS");
        String timeStampString ;

        /* Add header row/content. */
        singleContact = new Contact2k3();
        for (String key : singleContact.getFieldValuesMap().keySet()) {
            timeStampString = dateFormat.format(new Date());
            singleContact.setValue(key, Contact2k3.FIELD_DESCRIPTION_MAP.get(key));
        }
        contactBook.add(singleContact);

        /* Add more rows/content. */
        for( int i=1 ; i<10 ; i++ ) {
            singleContact = new Contact2k3();
            for (String key : singleContact.getFieldValuesMap().keySet()) {
                timeStampString = dateFormat.format(new Date());
                singleContact.setValue(key, timeStampString);
            }
            contactBook.add(singleContact);
        }
    }
}
