/*  Copyright 2011-2012 ASCH
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

package ypcnv.helpers;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/** Data specific to and concerning Samsung devices.*/
public class VCardSamsungHelperMetaData {
    /**
     * Maximum quantity of addresses per one contact in phone Samsung GT-C3322.
     */
    public static final Integer MAX_ADDR_QUANTITY_GT_C3322 = 1 ;
    
    /**
     * Hand made transliteration table. Compiled from several sources. It may be
     * not a standards compliant.
     */
    public static final Map<Character, String> TRANSLIT_TABLE = Collections
            .unmodifiableMap(new HashMap<Character, String>() {
                private static final long serialVersionUID = 1493126395236752619L;

                {
                    put('А', "A");
                    put('а', "a");
                    put('Б', "B");
                    put('б', "b");
                    put('в', "v");
                    put('В', "V");
                    put('г', "g");
                    put('Г', "G");
                    put('д', "d");
                    put('Д', "D");
                    put('е', "e");
                    put('ё', "yo");
                    put('Ё', "Yo");
                    put('ж', "zh");
                    put('Ж', "Zh");
                    put('з', "z");
                    put('З', "Z");
                    put('и', "i");
                    put('И', "I");
                    put('й', "j");
                    put('Й', "J");
                    put('к', "k");
                    put('К', "K");
                    put('л', "l");
                    put('Л', "L");
                    put('м', "m");
                    put('М', "M");
                    put('н', "n");
                    put('Н', "N");
                    put('о', "o");
                    put('О', "O");
                    put('п', "p");
                    put('П', "P");
                    put('р', "r");
                    put('Р', "R");
                    put('с', "s");
                    put('С', "S");
                    put('т', "t");
                    put('Т', "T");
                    put('у', "u");
                    put('У', "U");
                    put('ф', "f");
                    put('Ф', "F");
                    put('х', "kh");
                    put('Х', "Kh");
                    put('ц', "c");
                    put('Ц', "C");
                    put('ч', "ch");
                    put('Ч', "Ch");
                    put('ш', "sh");
                    put('Ш', "Sh");
                    put('щ', "sch");
                    put('Щ', "Sch");
                    put('э', "e");
                    put('Э', "E");
                    put('ю', "ju");
                    put('Ю', "Ju");
                    put('я', "ya");
                    put('Я', "Ya");
                    put('ъ', "`");
                    put('Ъ', "`");
                    put('ь', "'");
                    put('Ь', "'");
                    put('ы', "y");
                    put('Ы', "Y");
                }
            });

}
