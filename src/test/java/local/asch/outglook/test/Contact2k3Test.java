package local.asch.outglook.test;
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

import static org.junit.Assert.*;

import org.junit.Test;

import local.asch.outglook.Contact2k3;
import local.asch.outglook.exceptions.Contact2k3Exception;

public class Contact2k3Test {

    private Contact2k3 contactSingle = new Contact2k3();
    private String key = "title";
    private String value = "test value";

    @Test
    public void test01() throws Contact2k3Exception{
        contactSingle.setValue(key, value);

        String message = String.format("For key '%s' value is: ", key);
        assertEquals(message, value , contactSingle.getFieldValuesMap().get(key));

    }

    @Test
    public void test02() {
        assertEquals(contactSingle.getFieldValuesMap().size(),
                Contact2k3.FIELD_DESCRIPTION_MAP.size());
    }

}
