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

/**
 * 
 */
package ypcnv.views.ifHi;

import ypcnv.contact2k3.ContactsBook;
import ypcnv.exceptions.FileViewException;
import ypcnv.views.abstr.DataFormatID;

/**
 * Object realizing interface to a data source.
 */
public interface DataSource {

    /** Set data in container-buffer - replace. */
    public void setDataContainer(ContactsBook dataContainer);
    /** Get data container-buffer. */
    public ContactsBook getDataContainer();
    /** Get data format Id. */
    public DataFormatID getFormat();
    
    /** Synchronize. Load data from source to container 
     * @throws FileViewException */
    public void pullData() throws FileViewException;
    /** Synchronize. Push data from container to data source. 
     * @throws FileViewException */
    public void pushData() throws FileViewException;
}
