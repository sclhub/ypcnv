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

import org.junit.Test;

import java.io.File;
import java.lang.System;


public class Contact2k3XlsViewTest {
    File testInputFile = new File("/home/*/git/ypcnv/src/test/java/resources/", "contacts2k3.ods");

    @Test
    public void test01(){
        System.out.println("===> " + testInputFile.getAbsolutePath());
//        ArrayList<Contact2k3> aContactList = new ArrayList<Contact2k3>();
//        String fileName = "tmpfile.xls" ;
//        String filePath =  System.getProperty("java.io.tmpdir")
//                           + System.getProperty("file.separator");
//        File tmpFile = new File(filePath,fileName);
//        Contact2k3XlsView xlsView = new Contact2k3XlsView(aContactList,tmpFile);
//
//        // XXX
//        System.out.println("--->" + xlsView + "|");
//
//        System.out.println("--->" + "" + "|");
        
    }

    @Test
    public void spacer(){
        System.out.print("\n\n\n\n");
    }

}
