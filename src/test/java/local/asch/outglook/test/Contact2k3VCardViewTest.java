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
package local.asch.outglook.test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import local.asch.outglook.Contact2k3;
import local.asch.outglook.exceptions.Contact2k3Exception;
import local.asch.outglook.exceptions.FileViewException;
import local.asch.outglook.fileview.Contact2k3VCardView;

import org.junit.Test;

/**
 * @version 2012-02-16_20-40
 */

public class Contact2k3VCardViewTest {

//    /** Logger. */
//    private static final Logger LOG = Logger
//            .getLogger(Contact2k3VCardViewTest.class);
    
    private File vCardContainerFileName = new File("src/test/java/resources/vcards/");
    private File outPutDir = new File("/tmp/123");
    private ArrayList<Contact2k3> aContactList = new ArrayList<Contact2k3>();

    @Test
    public void testGetView() throws IOException, FileViewException {
        Contact2k3VCardView vcv = new Contact2k3VCardView(aContactList,
                vCardContainerFileName);
        vcv.getView();
    }

    @Test
    public void testSetView() throws IOException, FileViewException {
        
        aContactList.add(new Contact2k3());
        try {
            aContactList.get(0).setValue("firstName", "Wall-Street");
            aContactList.get(0).setValue("middleName", "Panic");
            aContactList.get(0).setValue("lastName", "Snopes");
            aContactList.get(0).setValue("title", "Mr.");
            aContactList.get(0).setValue("birthday", "09.05.2008");
            aContactList.get(0).setValue("webPage", "http://www.ubuntu.com");
        } catch (Contact2k3Exception e) {
            e.printStackTrace();
        }

        Contact2k3VCardView vcv = new Contact2k3VCardView(aContactList,
                outPutDir);
        vcv.setView();
        System.out.println("See files in '" + outPutDir + "'.");
    }

}
