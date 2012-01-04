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

import org.junit.Test;

import local.asch.outglook.Contact2k3;
import local.asch.outglook.Contact2k3VCardView;



public class Contact2k3VCardViewTest {
    private File vCardContainerFileName = new File("src/test/java/resources/vcards/");
    private ArrayList<Contact2k3> aContactList;

    @Test
    public void test01() throws IOException {
        Contact2k3VCardView vcv = new Contact2k3VCardView(aContactList,
                vCardContainerFileName);
        vcv.toString();

    }

}
