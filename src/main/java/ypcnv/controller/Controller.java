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
package ypcnv.controller;

import java.io.IOException;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ypcnv.contact2k3.ContactsBook;
import ypcnv.exceptions.FileViewException;
import ypcnv.views.abstr.DataFormatID;
import ypcnv.views.ifHi.DataSource;


/**
 * Controller in MVC pattern.
 */
abstract public class Controller {

    /** Logger. */
    @SuppressWarnings("unused")
    private static final Log LOG = LogFactory.getLog(Controller.class);

    /** Storage of contacts. */
    protected final ContactsBook contactsBook = new ContactsBook();

    /**
     * Storage of already created objects.
     */
    protected HashMap<DataFormatID, Object> createdViews = new HashMap<DataFormatID, Object>();

    /**
     * Will replace model data.
     * 
     * @param contactsBook - Contacts pool to be imported.
     * @throws CloneNotSupportedException
     */
    abstract public void importData(ContactsBook contactsBook)
            throws CloneNotSupportedException;

    /**
     * Will replace model data.
     * 
     * @param srcObject - data source, data donor.
     * @throws CloneNotSupportedException
     */
    abstract public void importData(DataSource srcObject)
            throws CloneNotSupportedException;

    /**
     * Dump data to view.
     * @param dstObject - destination object, data acceptor.
     * @throws IOException
     * @throws FileViewException
     * @throws CloneNotSupportedException
     */
    abstract public void export(DataSource dstObject) throws IOException,
            FileViewException, CloneNotSupportedException;

    
    /**
     * Get current contacts pool.
     * @return Current contacts pool.
     * @throws CloneNotSupportedException
     */
    public ContactsBook getBook() throws CloneNotSupportedException {
        // XXX - copy-clone
        ContactsBook destinationBook = new ContactsBook();
        contactsBook.pushToBook(destinationBook);
        return destinationBook;
    }

    /**
     * Test whether or not already created a view of some type and properties. 
     * @param formatName - Data source format.
     * @return Object-view if it was already created, or null - if not.
     */
    protected Object getViewIfCreated(DataFormatID formatName) {
        // XXX - logic should be more complicated.
        if (createdViews.containsKey(formatName)) {
            return createdViews.get(formatName);
        }
        return null;
    }

}
