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
package ypcnv.test;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Test;

import ypcnv.contact2k3.Contact2k3;
import ypcnv.contact2k3.ContactsBook;
import ypcnv.converter.conf.DataSourceConf;
import ypcnv.exceptions.ContactException;
import ypcnv.exceptions.FileViewException;
import ypcnv.views.abstr.AccessFlag;
import ypcnv.views.abstr.DataFormatID;
import ypcnv.views.abstr.DataSourceAbstractImpl;
import ypcnv.views.abstr.Side;
import ypcnv.views.abstr.ViewFactory;
import ypcnv.views.ifHi.DataSource;
import ypcnv.views.impl.FileXLS;



public class Contact2k3XlsViewTest {
    
    /** Logger. */
    private static final Log LOG = LogFactory.getLog(DataSourceAbstractImpl.class);

    private File testInputFile ;
    private File testOutputFile ;
    private String testFilePath ;
    private String testFileName = "ypcnv-test.xls";

    public Contact2k3XlsViewTest() throws IOException {
        LOG.info("========> JUnit tests object created. <========");
        testFilePath =  System.getProperty("java.io.tmpdir")
                + System.getProperty("file.separator");
        testInputFile = new File(testFilePath,testFileName);
        testOutputFile = new File(testFilePath,testFileName);
    }

    @SuppressWarnings("unused")
    @Test
    public void testFileExist() throws FileViewException, InvalidFormatException {

        ContactsBook contacts = new ContactsBook();
        DataSourceConf inParams = new DataSourceConf(DataFormatID.XLS,
                testInputFile, Side.source); 

        DataSourceConf outParams = new DataSourceConf(DataFormatID.XLS,
                testOutputFile, Side.destination);        
        
        FileXLS xlsContainerSrcTest = new FileXLS(inParams);
        FileXLS xlsContainerDstTest = new FileXLS(outParams);

        DataSource xlsContainerSrc = ViewFactory.get(inParams);
        DataSource xlsContainerDst = ViewFactory.get(outParams);

        AccessFlag[] accessFlagsPool = { AccessFlag.R,
                AccessFlag.W, AccessFlag.RW };
        for (AccessFlag flag : accessFlagsPool) {
            ;
        }
    }

    @Test
    public void testSetView() throws ContactException, IOException,
            InvalidFormatException, FileViewException {

        ContactsBook contactList = new ContactsBook();

        DataSourceConf outParams = new DataSourceConf(DataFormatID.XLS,
                testOutputFile, Side.destination);        
        DataSource xlsContainerDst = ViewFactory.get(outParams);


        System.out
                .println("===> Try write: " + testInputFile.getAbsolutePath());
        for (int i = 0; i < 5; i++) {
            contactList.add(new Contact2k3());
        }
        insertFakeData(contactList);
        xlsContainerDst.setDataContainer(contactList);
        xlsContainerDst.pushData();
        System.out.println("===> Done, try 'libreoffice "
                + testInputFile.getAbsolutePath() + " &'.");
    }

    @Test
    public void testGetView() throws ContactException, IOException,
            FileViewException, InvalidFormatException {
        
        System.out.println("===> Try reading from: " + testInputFile.getAbsolutePath());

        DataSourceConf outParams = new DataSourceConf(DataFormatID.XLS,
                testOutputFile, Side.destination);        
        DataSource xlsContainerDst = ViewFactory.get(outParams);
        xlsContainerDst.pullData();
        ContactsBook obtainedContactList = xlsContainerDst.getDataContainer();
        System.out.println("===> Got: " + obtainedContactList.toString());
        xlsContainerDst.pushData();
    }

    /**
     * @param contactList
     * @throws ContactException
     */
    private void insertFakeData(ContactsBook contactList)
            throws ContactException {

        DateFormat dateFormat = new SimpleDateFormat("mmss.SS");
        String timeStampString ;

        Iterator<Contact2k3> contactListIterator = contactList.getContacts().iterator();

        Contact2k3 contact = contactListIterator.next();
        for (String key : contact.getFieldValuesMap().keySet()) {
            timeStampString = dateFormat.format(new Date());
            contact.setValue(key, Contact2k3.FIELD_DESCRIPTION_MAP.get(key));
        }

        while (contactListIterator.hasNext()) {
            contact = contactListIterator.next();
            for (String key : contact.getFieldValuesMap().keySet()) {
                timeStampString = dateFormat.format(new Date());
                contact.setValue(key, timeStampString);
            }
        }
        return;
    }

}
