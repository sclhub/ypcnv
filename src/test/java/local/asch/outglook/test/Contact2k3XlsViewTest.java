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
package local.asch.outglook.test;

import static org.junit.Assert.assertEquals;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.lang.System;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;


import local.asch.outglook.Contact2k3;
import local.asch.outglook.Contact2k3XlsView;
import local.asch.outglook.exceptions.Contact2k3Exception;
import local.asch.outglook.exceptions.FileViewException;



public class Contact2k3XlsViewTest {
    private File testInputFile ;
    private File testOutputFile ;
    private String testFilePath ;
    private String testFileName = "out.xls";

    public Contact2k3XlsViewTest() throws IOException {
        testFilePath =  System.getProperty("java.io.tmpdir")
                + System.getProperty("file.separator");
        testInputFile = new File(testFilePath,testFileName);
        testOutputFile = new File(testFilePath,testFileName);
    }

    @Test
    public void testFileExist() throws FileViewException, InvalidFormatException {

        ArrayList<Contact2k3> contacts = new ArrayList<Contact2k3>();
        Contact2k3XlsView xlsContainerSrc = new Contact2k3XlsView(contacts,
                testInputFile);
        Contact2k3XlsView xlsContainerDst = new Contact2k3XlsView(contacts,
                testOutputFile);

        int[] accessFlagsPool = { Contact2k3XlsView.READABLE,
                Contact2k3XlsView.WRITABLE, Contact2k3XlsView.RW };
        for (int flag : accessFlagsPool) {
            assertEquals("ALLOWED",
                    xlsContainerSrc.isAccessible(flag) ? "ALLOWED" : "Denied" );
            assertEquals("ALLOWED",
                    xlsContainerDst.isAccessible(flag) ? "ALLOWED" : "Denied" );
        }
    }

    @Test
    public void testSetView() throws Contact2k3Exception, IOException,
            InvalidFormatException, FileViewException {

        ArrayList<Contact2k3> contactList = new ArrayList<Contact2k3>();

        System.out
                .println("===> Try write: " + testInputFile.getAbsolutePath());
        Contact2k3XlsView xlsContainer = new Contact2k3XlsView(contactList,
                testOutputFile);
        for (int i = 0; i < 5; i++) {
            contactList.add(new Contact2k3());
        }
        insertFakeData(contactList);
        xlsContainer.set(contactList);
        xlsContainer.setView();
        System.out.println("===> Done, try 'libreoffice "
                + testInputFile.getAbsolutePath() + " &'.");
    }

    @Test
    public void testGetView() throws Contact2k3Exception, IOException,
            FileViewException, InvalidFormatException {
        ArrayList<Contact2k3> contactList = new ArrayList<Contact2k3>();
        
        System.out.println("===> Try read: " + testInputFile.getAbsolutePath());
        Contact2k3XlsView xlsContainer = new Contact2k3XlsView(contactList, testInputFile);
        xlsContainer.getView();
        ArrayList<Contact2k3> obtainedContactList;
        obtainedContactList = xlsContainer.get();
        System.out.println("===> Got: " + obtainedContactList);
    }

    /**
     * @param contactList
     * @throws Contact2k3Exception
     */
    private void insertFakeData(ArrayList<Contact2k3> contactList)
            throws Contact2k3Exception {

        DateFormat dateFormat = new SimpleDateFormat("mmss.SS");
        String timeStampString ;

        Iterator<Contact2k3> contactListIterator = contactList.iterator();

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
