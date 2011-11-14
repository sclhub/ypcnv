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

import info.ineighborhood.cardme.engine.VCardEngine;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import local.asch.outglook.exceptions.FileViewException;

/**
 * @version 2011-11-14_16-27
 */
public class Contact2k3VCardView extends Contact2k3FileView {
    // /** Logger. */
    // private static final Logger LOG =
    // Logger.getLogger(Contact2k3VCardView.class);

    /**
     * This is list of names for files with vCards.<br>
     * Contact2k3FileView.containerFileName will be path to vCard files.
     */
    private ArrayList<File> vCardFiles = new ArrayList<File>();
    private VCardEngine vCardEngine = null;
    
    /*
     * CompatibilityMode.MS_OUTLOOK
     * CompatibilityMode.RFC2426
     */

    Contact2k3VCardView(ArrayList<Contact2k3> aContactList, File fileNameForUse) {
        super(aContactList, fileNameForUse);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void set(ArrayList<Contact2k3> contactList) {
        // TODO Auto-generated method stub

    }

    @Override
    public void add(Contact2k3 contact) {
        // TODO Auto-generated method stub

    }

    @Override
    public void add(ArrayList<Contact2k3> contactList) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setView() throws FileViewException, IOException {
        // TODO Auto-generated method stub

    }

    @Override
    public void getView() throws FileViewException {
        // TODO Auto-generated method stub

    }

    @Override
    protected void saveToFile() throws IOException {
        // TODO Auto-generated method stub

    }

}
