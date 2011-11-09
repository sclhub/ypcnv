package local.asch.outglook;
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

import static org.junit.Assert.assertEquals;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.lang.System;
import java.util.ArrayList;
import java.util.Iterator;

import local.asch.outglook.exceptions.Contact2k3Exception;
import local.asch.outglook.exceptions.FileViewException;

public class Contact2k3XlsViewTest {
    ArrayList<Contact2k3> aContactList = new ArrayList<Contact2k3>();
    File testInputFile = new File("src/test/java/resources/", "contacts2k3.xls");
    
    @Test
    public void test01(){
        assertEquals("EXIST", testInputFile.exists() ? "EXIST" : "NotFound" ) ;
    }

    @Test
    public void test02() throws Contact2k3Exception, IOException,
            FileViewException, InvalidFormatException {
        System.out.println("===> " + testInputFile.getAbsolutePath());
        for (int i = 0; i < 3; i++) {
            aContactList.add(new Contact2k3());
        }
            Contact2k3XlsView xlsContainer = new Contact2k3XlsView(aContactList,
                                                            testInputFile);
        xlsContainer.getView();
        // ArrayList<Contact2k3> obtainedContactList;
        // obtainedContactList = xlsContainer.get();
        // System.out.println("===> " + obtainedContactList);
    }

    @Test
    public void test03() throws Contact2k3Exception{

//      String fileName = "tmpfile.xls" ;
//      String filePath =  System.getProperty("java.io.tmpdir")
//                         + System.getProperty("file.separator");
//      File tmpFile = new File(filePath,fileName);
//      Contact2k3XlsView xlsView = new Contact2k3XlsView(aContactList,tmpFile);
//
//      // XXX
//      System.out.println("--->" + xlsView + "|");
//
//      System.out.println("--->" + "" + "|");
        insertFakeData(aContactList);
        
    }

    private void insertFakeData(ArrayList<Contact2k3> contactList)
            throws Contact2k3Exception {
        Iterator<Contact2k3> contactListIterator = contactList.iterator();
        while (contactListIterator.hasNext()) {
            Contact2k3 contact = contactListIterator.next();
            for (String key : contact.getFieldValuesMap().keySet()) {
                contact.setValue(key, Contact2k3.FIELD_DESCRIPTION_MAP.get(key));
            }
        }

    }
    
}
