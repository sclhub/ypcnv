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
package ypcnv.views.abstr;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ypcnv.contact2k3.ContactsBook;
import ypcnv.converter.conf.DataSourceConf;

/**
 * @see <a href="http://odftoolkit.org/projects/odfdom">ODF by odfdom</a><br>
 *      <a href="http://www.jopendocument.org/">ODF by jopendocument</a>
 */
public abstract class DataSourceAbstractImpl /*implements DataSource*/ {

    /** Logger. */
    @SuppressWarnings("unused")
    private static final Log LOG = LogFactory.getLog(DataSourceAbstractImpl.class);

    /** Data source format Id. */
    protected DataFormatID format = null;
    /** Data source address. */
    protected Object address = null;
    /** Data source space and direction denotation. */
    protected Side side = null;

    protected ContactsBook dataContainer = null;

    public DataSourceAbstractImpl() {
    }

    /**
     * @param configuration - data source configuration.
     */
    public DataSourceAbstractImpl(DataSourceConf configuration) {
        this.format = configuration.getObjectFormat();
        this.address = configuration.getObjectAddress();
        this.side = configuration.getSide();
        this.dataContainer = new ContactsBook();
    }

    /**
     * Build data source object from parameters.
     * 
     * @param sourceConfiguration
     *            - the parameters.
     */
    public abstract void build(DataSourceConf sourceConfiguration);

    public DataFormatID getFormat() {
        return format;
    }

    public Object getAddress() {
        return address;
    }

    public Side getSide() {
        return side;
    }

    /*
     * (non-Javadoc)
     * 
     * @see ypcnv.outglook.views.DataSource#setDataContainer(ypcnv.contact2k3.
     * ContactsBook)
     */
    public void setDataContainer(ContactsBook dataContainer) {
        this.dataContainer = dataContainer;
    }

    /*
     * (non-Javadoc)
     * 
     * @see ypcnv.outglook.views.DataSource#setDataContainer(ypcnv.contact2k3.
     * ContactsBook)
     */
    public ContactsBook getDataContainer() {
        return dataContainer; // XXX - return a copy of object?
    }

    @Override
    public String toString() {
        return "DataContainer [side=" + side + ", address=" + address
                + ", dataContainer=" + dataContainer + "]";
    }

}
