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

package ypcnv.contact2k3;

import java.util.ArrayList;
import java.util.Iterator;

import ypcnv.contact2k3.Contact2k3;

/**
 * Storage of contacts. This is a Model in MVC pattern.
 */
public class ContactsBook {
    /** Contacts("visit cards") pool. */
    private ArrayList<Contact2k3> contactsList = null ;
    
    /**
     * Flag used for indication whether or not output empty fields in "string"
     * representation of this object.
     */
    private enum ToStringFullness {
        FULL, NOEMPTY
    }
    
    public ContactsBook() {
        contactsList = new ArrayList<Contact2k3>();
    }
    
    /** Adder. */
    public void add(Contact2k3 singleContact) {
        this.contactsList.add(singleContact);
    }
    
    /** Adder. */
    public void add(ArrayList<Contact2k3> contacts) {
        this.contactsList.addAll(contacts);
    }
    
    /** Remover. */
    public void delete(Contact2k3 singleContact) {
        // TODO - delete() realization.
    }

    /** Remover. Clear all. */
    public void clear() {
        contactsList.clear();
    }
    
    /** Setter. */
    @SuppressWarnings("unchecked")
    public void set(ArrayList<Contact2k3> contacts) {
        this.contactsList = (ArrayList<Contact2k3>) contacts.clone() ;
    }
    
    /** Setter. */
    @SuppressWarnings("unchecked")
    public ArrayList<Contact2k3> getContacts() {
        return (ArrayList<Contact2k3>) contactsList.clone();
    }
    
    /**
     * Push current instance's data into one specified as argument.
     * @param destinationBook - data acceptor.
     * @throws CloneNotSupportedException
     */
    public void pushToBook(ContactsBook destinationBook) throws CloneNotSupportedException {
        destinationBook.clear();
        destinationBook.add(contactsList);
    }
    
    /** Returns full "string" representation. */
    @Override
    public String toString() {
        return toString(ToStringFullness.FULL);
    }

    /** Returns "string" representation without empty fields. */
    public String toStringWithoutEmptyFields() {
        return toString(ToStringFullness.NOEMPTY);
    }
    

    /**
     * "As string" representer.
     * @param fulnessType
     *            - Flag indicating whether or not include empty fields into
     *            "string" representation.
     * @return "String" representation of object.
     */
    private String toString(ToStringFullness fulnessType) {
        StringBuilder stringRepresentation = new StringBuilder();
        final String IN_STRING_DELIMETER = "\n" ; // XXX - new line may be significant for a log parsers. 

        Iterator<Contact2k3> contactsIterator = contactsList.iterator();
        stringRepresentation.append("ContactsBook :[\n");
        while(contactsIterator.hasNext()) {
            String contactFieldValue = null ;
            switch (fulnessType) {
            case FULL:
                contactFieldValue = contactsIterator.next().toString();
                break;
            case NOEMPTY:
                contactFieldValue = contactsIterator.next().toStringWithoutEmptyFields();
                break;
            default:
                contactFieldValue = "" ;
            }
            stringRepresentation.append("{"
                                        + contactFieldValue
                                        + "}"
                                        + IN_STRING_DELIMETER);
        }
        stringRepresentation.append("]");
        return stringRepresentation.toString();
    }
}
