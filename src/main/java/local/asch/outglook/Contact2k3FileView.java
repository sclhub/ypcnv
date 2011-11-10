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
import java.io.FileOutputStream;
import java.io.IOException;

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
     */
    abstract public void setView();

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

}
