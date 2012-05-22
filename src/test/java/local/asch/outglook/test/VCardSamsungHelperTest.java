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

import local.asch.outglook.samsung.VCardSamsungHelper;

import org.junit.Test;

public class VCardSamsungHelperTest {
    private static final String TEST_STRING_01 = "Здравствуйте, я ваша тетя. а б в г д е ё ж з и й к л м н о п р с т у ф х ц ч ш щ ъ ь э ю я . Latin"; 
    @Test
    public void test01() {
        System.out.println(TEST_STRING_01);
        String output = null ;
        output = VCardSamsungHelper.transliterateCyr2Lat(TEST_STRING_01);
        System.out.println(output);
    }
}
