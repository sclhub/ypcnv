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

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.System;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;


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

        testFileIsRWAble(testInputFile);
        testFileIsRWAble(testOutputFile);
        
    }
    
    // @Test
    // public void testFileExist(){
    // assertEquals("EXIST", testInputFile.exists() ? "EXIST" : "NotFound" ) ;
    // }

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

    /** Try rw access and file creation. */
    private void testFileIsRWAble(File targetFile) throws IOException {
        if (targetFile.isDirectory()) {
            throw new FileNotFoundException("Output file name '"
                    + targetFile.getAbsoluteFile()
                    + "' is a directory. Wait for a file to be here.");
        }

/*

        try {
            boolean existanceFlag = targetFile.exists();;
            if (!existanceFlag) {
                targetFile.createNewFile();
            }
        } catch (SecurityException e) {
            e.printStackTrace();
//            throw new SecurityException(
//                    "A security manager exists and its"
//                    + " java.lang.SecurityManager.checkRead(java.lang.String)"
//                    + "method denies read access to the file or directory"
//                    + " '" + targetFile.getAbsoluteFile()
//                    + "'.");
        } catch (IOException e) {
            e.printStackTrace();
//            throw new IOException(
//                    "I/O error occurred. File is "
//                    + " '" + targetFile.getAbsoluteFile()
//                    + "'.");
        }

        if (!targetFile.isFile()) {
            throw new FileNotFoundException("File not found. Looked for '"
                    + targetFile.getAbsoluteFile() + "'.");
        }
        if (!targetFile.canWrite()) { // no sence here, file created by user
                                      // will be writable for the user
            throw new FileNotFoundException("File '"
                    + targetFile.getAbsoluteFile() + "' is not writable.");
        }

*/

    }

}
