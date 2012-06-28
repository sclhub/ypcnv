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
package ypcnv.controller;

import java.io.File;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ypcnv.contact2k3.ContactsBook;
import ypcnv.exceptions.FileViewException;
import ypcnv.views.abstr.DataFormatID;
import ypcnv.views.ifHi.DataSource;
import ypcnv.views.impl.FileXLS;


public class ControllerContact2k3 extends ControllerAbstr {

    /** Logger. */
    private static final Log LOG = LogFactory.getLog(ControllerContact2k3.class);

    @Override
    public void importData(ContactsBook contactsBook) throws CloneNotSupportedException {
        // XXX - copy-clone
        contactsBook.pushToBook(this.contactsBook) ;
    }

    @Override
    public void importData(DataSource srcObject) throws CloneNotSupportedException {
        DataFormatID formatName = srcObject.getFormat();
        if(getViewIfCreated(formatName) == null) {
            createdViews.put(formatName, (DataSource) srcObject);
        }
        
        try {
            srcObject.pullData();
        } catch (FileViewException e) {
            String message = "Failed to pull data from '"
                                    + srcObject.toString()
                                    + "'.";
            LOG.error(message);
            e.printStackTrace();
        }
        return ;
    }

    @Override
    public void export(DataSource dstObject) throws IOException, FileViewException, CloneNotSupportedException{
        DataFormatID formatName = dstObject.getFormat();
        if((DataSource) getViewIfCreated(formatName) == null) {
            createdViews.put(formatName, (DataSource) dstObject);
        }
        dstObject.pushData();
    }

    /**
     * Deprecated. Get file associated with this instance.
     * @param formatName - file's internal wanted format.
     * @return Filename if view is created, or null. 
     */
    public File getViewFileName(DataFormatID formatName) {
        DataSource fileViewInstance
            = (DataSource) createdViews.get(formatName) ;
        if(fileViewInstance != null) {
            return (File) ((FileXLS)fileViewInstance).getAddress();
        }
        return null ;
    }

}
