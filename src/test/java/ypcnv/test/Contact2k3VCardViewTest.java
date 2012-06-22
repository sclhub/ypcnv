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


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import ypcnv.contact2k3.Contact2k3;
import ypcnv.contact2k3.ContactsBook;
import ypcnv.converter.conf.DataSourceConf;
import ypcnv.exceptions.ContactException;
import ypcnv.exceptions.FileViewException;
import ypcnv.views.abstr.DataFormatID;
import ypcnv.views.abstr.Side;
import ypcnv.views.abstr.ViewFactory;
import ypcnv.views.ifHi.DataSource;
import ypcnv.views.impl.FileVCF;

/**
 * @version 2012-02-16_20-40
 */

public class Contact2k3VCardViewTest {

    /** Logger. */
    private static final Log LOG = LogFactory.getLog(Contact2k3VCardViewTest.class);

    private File testInputFile = new File("src/test/resources");
    private File testOutputFile = new File("src/test/resources");
    private ContactsBook aContactList = new ContactsBook();
    
    public Contact2k3VCardViewTest() {
        LOG.info("========> " + this.getClass().getName() + " started. <========");
    }
    
    @Test
    public void testGetView() throws IOException, FileViewException {
        
        DataSourceConf inputParams = new DataSourceConf(DataFormatID.VCF,
                testInputFile, Side.source);        

        @SuppressWarnings("unused")
        FileVCF vcfContainerSrcTest = new FileVCF(inputParams) ;

        DataSource vcfContainerSrc = ViewFactory.get(inputParams);
        vcfContainerSrc.setDataContainer(aContactList);
        vcfContainerSrc.pullData();
        aContactList = vcfContainerSrc.getDataContainer();
        
        LOG.debug("Get VCard view test done.");
    }

    @Test
    public void testSetView() throws IOException, FileViewException {
        Contact2k3 singleContact = new Contact2k3();
        try {
            singleContact.setValue("firstName", "Wall-Street");
            singleContact.setValue("middleName", "Panic");
            singleContact.setValue("lastName", "Snopes");
            singleContact.setValue("title", "Mr.");
            singleContact.setValue("birthday", "25.09.1897");
            singleContact.setValue("webPage", "http://www.ubuntu.com");
        } catch (ContactException e) {
            LOG.error("Failed with creation of fake contact.");
            e.printStackTrace();
        }
        aContactList.add(singleContact);

        DataSourceConf outputParams = new DataSourceConf(DataFormatID.VCF,
                testOutputFile, Side.destination);        
        DataSource vcfContainerSrc = ViewFactory.get(outputParams);
        vcfContainerSrc.setDataContainer(aContactList);
        vcfContainerSrc.pushData();
        System.out.println("See files: 'ls -d " + testOutputFile + "/*/*vcf | tail -n 1'.");
        LOG.debug("Set VCard view test done.");
    }
}
