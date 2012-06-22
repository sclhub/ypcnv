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

import info.ineighborhood.cardme.vcard.VCard;
import info.ineighborhood.cardme.vcard.features.AddressFeature;

import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class VCardSamsungHelper {

    /** Logger. */
    private static final Log LOG = LogFactory.getLog(VCardSamsungHelper.class);

    /**
     * Some fields are encoded unproperly. Workaround - transliterate.
     * 
     * @param input
     *            - string to be processed.
     * @return Transliterated string.
     */
    public static String transliterateCyr2Lat(String input) {
        char[] buff = { ' ' };
        StringBuilder outputBuilder = new StringBuilder();

        for (Integer idx = 0; idx < input.length(); idx++) {
            input.getChars(idx, idx + 1, buff, 0);
            if (VCardSamsungHelperMetaData.TRANSLIT_TABLE.get(buff[0]) == null) {
                outputBuilder.append(buff[0]);
            } else {
                outputBuilder.append(VCardSamsungHelperMetaData.TRANSLIT_TABLE.get(buff[0]));
            }
        }
        return outputBuilder.toString();
    }

    /**
     * Check whether or not visit card object is capable with Samsung vision.
     * @param vCard - object to be processed.
     * @return <b>True</b> if capable, <b>false</b> - if not.
     */
    public static Boolean checkAndFixGTC3322Compatibility(VCard vCard) {
        Boolean isSamsungCompatible = true ;

        if (!validateAddressQuantity(vCard,
                VCardSamsungHelperMetaData.MAX_ADDR_QUANTITY_GT_C3322)) {
            isSamsungCompatible = false ;
            String warningMessage = "Quantity of addresses for '"
                + vCard.getDisplayableNameFeature().getName()
                + "' is greater then acceptable by GT-C3322.";
            LOG.info(warningMessage);
        }

        if (!validateScreenNamesArePresent(vCard)) {
            isSamsungCompatible = false ;
            String stubName = new String("empty name");
            String nameSubstitution = null ;

            Iterator<String> organisationsListIterator = vCard.getOrganizations().getOrganizations();
            while(organisationsListIterator.hasNext()) {
                nameSubstitution = organisationsListIterator.next();
                if(! nameSubstitution.equals("")) {
                    stubName = new String(nameSubstitution);
                    break ;
                }
            }
            vCard.getName().setFamilyName(stubName);
            vCard.getDisplayableNameFeature().setName(stubName);
            vCard.getFormattedName().setFormattedName(stubName);
            String warningMessage = "Void personal names substituted with '"
                                        + nameSubstitution
                                        +"'.";
            LOG.info(warningMessage);
        }

        if(!validateaAddressCharacters(vCard)) {
            isSamsungCompatible = false ;
        }
        return isSamsungCompatible ;
    }

    /**
     * In Samsung GT-C3322 there is limited quantity of fields to store address
     * (only one). This is a tool to count addresses and warn if specified limit
     * is exceeded.
     * 
     * @param vCard - vCard to be processed.
     * @param maxQantityOfAddresses
     *            - maximum quantity.
     * @return <b>True</b> if real quantity is not greater than specified maximum.
     */
    private static Boolean validateAddressQuantity(VCard vCard, Integer maxQantityOfAddresses) {
        Integer realQuantityOfaddresses = 0 ;
        if(vCard.hasAddresses()) {
            Iterator<AddressFeature> addressListIterator= vCard.getAddresses();
            for( realQuantityOfaddresses = 0 ;
                    addressListIterator.hasNext() ;
                    realQuantityOfaddresses++ ) {
                addressListIterator.next();
            }
            if (realQuantityOfaddresses > maxQantityOfAddresses) {
                return false;
            }
        }
        return true;
    }
    
	/**
	 * Samsung may produce ugly entries in address book if in vCard name's fields
	 * of all sorts are empty at all.
	 * 
	 * @param vCard
	 *            - vCard to be processed.
	 * @return <b>False</b> if parsed object seems to be a problematic one.
	 */
    private static Boolean validateScreenNamesArePresent(VCard vCard) {
        if( vCard.getDisplayableNameFeature().getName().equals("")
            || vCard.getFormattedName().getFormattedName().equals(""))
        {
            return false ;
        }
    	return true ;
    }
    
    /**
     * New lines '\n' in address strings do not accepted by Samsung.
     * 
     * @param vCard
     *            - vCrad to be processed.
     * @return <b>False</b> if parsed object seems to be a problematic one
     */
    private static Boolean validateaAddressCharacters(VCard vCard) {
        // TODO - New lines '\n' in address strings do not accepted.
        return true ;
        
    }
}
