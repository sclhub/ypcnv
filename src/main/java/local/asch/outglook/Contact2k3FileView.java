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

import java.util.ArrayList;
import java.util.HashMap;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.log4j.Logger;

import local.asch.outglook.exceptions.FileViewException;

/**
 * @version 2011-09-14_17-49<br>
 * <br>
 * @see <a href="http://odftoolkit.org/projects/odfdom">ODF by odfdom</a><br>
 *      <a href="http://www.jopendocument.org/">ODF by jopendocument</a>
 */

abstract class Contact2k3FileView {

    protected File containerFileName = null ;
    protected FileInputStream containerFileStreamIn = null ;
    protected FileOutputStream containerFileStreamOut = null ;
    protected ArrayList<Contact2k3> containerModelList = null ;
    protected HashMap<String,String> containerFieldNamingMap = null ;
    /** Have write access. Octal designations. */
    protected static final int WRITABLE = 02 ;
    /** Have read access. Octal designations. */
    protected static final int READABLE = 04 ;
    /** Have read-write access. Octal designations. */
    protected static final int RW = 06 ;

    /** Logger. */
    private static final Logger LOG = Logger.getLogger(Contact2k3FileView.class);

    /**
     * @param aContactList - aContactList
     * @param fileNameForUse - fileNameForUse
     */
    Contact2k3FileView(ArrayList<Contact2k3> aContactList,
                       File fileNameForUse){
        containerFileName = fileNameForUse ;
        containerModelList = aContactList ;
        containerFieldNamingMap = new HashMap<String,String>() ;
    }

    /**
     * Set/replace data in the model.
     * @param  contactList - source of data.
     */
    abstract public void set(ArrayList<Contact2k3> contactList); // 
    /**
     * Add data into model.
     * @param  contact - source of data.
     */
    abstract public void add(Contact2k3 contact);
    /**
     * Add data into the model.
     * @param contactList - source of data.
     */
    abstract public void add(ArrayList<Contact2k3> contactList);

    /**
     * Get model.
     */
    public ArrayList<Contact2k3> get() {
        return containerModelList ;
        //ArrayList<Contact2k3> returnObject = new ArrayList<Contact2k3>();
        //containerModelList.copy(returnObject);
        //return returnObject;
    }

    /**
     * This should put data taken from the "model" into the "view".
     * @throws FileViewException 
     * @throws IOException 
     */
    abstract public void setView() throws FileViewException, IOException;

    /**
     * This should get data from the "view" and put it into the "model".
     * 
     * @throws FileViewException
     */
    abstract public void getView() throws FileViewException;

    /**
     * Write down to file.
     * @throws IOException 
     */
    abstract protected void saveToFile() throws IOException;
    
    /**
     * Check whether file is accessible.
     * 
     * @param accessTypeFlag
     *            - set it's value to Contact2k3FileView.RO for read only check,<br>
     *            Contact2k3FileView.RW for read-write access check.
     * @return <b>true</b> if file is accessible.
     * @throws FileViewException 
     */
    public boolean isAccessible(int accessTypeFlag)
            throws FileViewException {
        final String ERR_MESSAGE_IT_IS_DIRECTORY = "The object '%s' is a directory. Waiting it will be a file.";
        final String ERR_MESSAGE_FILE_NOT_EXIST = "Not found file '%s'.";
        final String ERR_MESSAGE_FILE_NOT_READABLE = "There is no read access for file '%s'.";
        final String ERR_MESSAGE_FILE_NOT_WRITABLE = "There is no write access for file '%s'.";
        final String ERR_MESSAGE_FILE_NOT_RW = "There is no read-write access for file '%s'.";
        final String ERR_MESSAGE_UNKNOWN_FLAG = "Got uknown flag as parameter to the method.";

        if (containerFileName == null) {
            throw new NullPointerException(
                    "File name is void. Does the method called too early?");
        }

        // File.isFile() is more valuable than File.exist().
        if (!containerFileName.isFile()) {
            fileAccessCheckLogHelper(ERR_MESSAGE_FILE_NOT_EXIST,
                    containerFileName.getAbsoluteFile());
            return false;
        }

        switch (accessTypeFlag) {
        case Contact2k3FileView.READABLE:
            if (!containerFileName.canRead()) {
                fileAccessCheckLogHelper(ERR_MESSAGE_FILE_NOT_READABLE,
                        containerFileName.getAbsoluteFile());
                return false;
            }
            break;
        case Contact2k3FileView.WRITABLE:
            if (!containerFileName.canWrite()) {
                fileAccessCheckLogHelper(ERR_MESSAGE_FILE_NOT_WRITABLE,
                        containerFileName.getAbsoluteFile());
                return false;
            }
            break;
        case Contact2k3FileView.RW:
            if (!containerFileName.canRead() && !containerFileName.canWrite()) {
                fileAccessCheckLogHelper(ERR_MESSAGE_FILE_NOT_RW,
                        containerFileName.getAbsoluteFile());
                return false;
            }
            break;
        default:
            throw new FileViewException(null, "",
                        String.format(ERR_MESSAGE_UNKNOWN_FLAG,
                                      containerFileName.getAbsoluteFile()));
        }

        if (containerFileName.isDirectory()) {
            fileAccessCheckLogHelper(ERR_MESSAGE_IT_IS_DIRECTORY,
                    containerFileName.getAbsoluteFile());
            return false;
        }

        return true;
    }

    private void fileAccessCheckLogHelper(String messageTemplate, File file){
        LOG.info(String.format(messageTemplate, containerFileName.getAbsoluteFile()));
    }

}
