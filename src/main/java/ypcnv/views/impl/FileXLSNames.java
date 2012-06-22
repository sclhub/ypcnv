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
package ypcnv.views.impl;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Meta data concerning contact from MS-Outlook exported into XLS container.
 * <br><br>
 * Field order is changed inside MS-Office depending on it's localization.
 */
abstract interface FileXLSNames {
    /**
     * Mapping of the model fields names to container's fields names. <b>Key</b>
     * - container's field name as it was set by MS-Outlook inside XLS contacts
     * container's in header row,<br>
     * <b>value</b> - model's class field name.<br>
     * <br>
     */
    static final Map<String, String> CONTAINER_FIELD_NAMING_MAP = Collections
            .unmodifiableMap(new LinkedHashMap<String, String>() {
                private static final long serialVersionUID = -6949471211802960406L;

                {
                    put("Title", "title");
                    put("FirstName", "firstName");
                    put("MiddleName", "middleName");
                    put("LastName", "lastName");
                    put("Suffix", "suffix");
                    put("Company", "company");
                    put("Department", "department");
                    put("JobTitle", "jobTitle");
                    put("BusinessStreet", "businessStreet");
                    put("BusinessStreet2", "businessStreet2");
                    put("BusinessStreet3", "businessStreet3");
                    put("BusinessCity", "businessCity");
                    put("BusinessState", "businessState");
                    put("BusinessPostalCode", "businessPostalCode");
                    put("BusinessCountryRegion", "businessCountryRegion");
                    put("HomeStreet", "homeStreet");
                    put("HomeStreet2", "homeStreet2");
                    put("HomeStreet3", "homeStreet3");
                    put("HomeCity", "homeCity");
                    put("HomeState", "homeState");
                    put("HomePostalCode", "homePostalCode");
                    put("HomeCountryRegion", "homeCountryRegion");
                    put("OtherStreet", "otherStreet");
                    put("OtherStreet2", "otherStreet2");
                    put("OtherStreet3", "otherStreet3");
                    put("OtherCity", "otherCity");
                    put("OtherState", "otherState");
                    put("OtherPostalCode", "otherPostalCode");
                    put("OtherCountryRegion", "otherCountryRegion");
                    put("AssistantsPhone", "assistantsPhone");
                    put("BusinessFax", "businessFax");
                    put("BusinessPhone", "businessPhone");
                    put("BusinessPhone2", "businessPhone2");
                    put("Callback", "callback");
                    put("CarPhone", "carPhone");
                    put("CompanyMainPhone", "companyMainPhone");
                    put("HomeFax", "homeFax");
                    put("HomePhone", "homePhone");
                    put("HomePhone2", "homePhone2");
                    put("ISDN", "iSDN");
                    put("MobilePhone", "mobilePhone");
                    put("OtherFax", "otherFax");
                    put("OtherPhone", "otherPhone");
                    put("Pager", "pager");
                    put("PrimaryPhone", "primaryPhone");
                    put("RadioPhone", "radioPhone");
                    put("TTYTDDPhone", "tTYTDDPhone");
                    put("Telex", "telex");
                    put("Account", "account");
                    put("Anniversary", "anniversary");
                    put("AssistantsName", "assistantsName");
                    put("BillingInformation", "billingInformation");
                    put("Birthday", "birthday");
                    put("BusinessAddressPOBox", "businessAddressPOBox");
                    put("Categories", "categories");
                    put("Children", "children");
                    put("DirectoryServer", "directoryServer");
                    put("EmailAddress", "emailAddress");
                    put("EmailType", "emailType");
                    put("EmailDisplayName", "emailDisplayName");
                    put("Email2Address", "email2Address");
                    put("Email2Type", "email2Type");
                    put("Email2DisplayName", "email2DisplayName");
                    put("Email3Address", "email3Address");
                    put("Email3Type", "email3Type");
                    put("Email3DisplayName", "email3DisplayName");
                    put("Gender", "gender");
                    put("GovernmentIDNumber", "governmentIDNumber");
                    put("Hobby", "hobby");
                    put("HomeAddressPOBox", "homeAddressPOBox");
                    put("Initials", "initials");
                    put("InternetFreeBusy", "internetFreeBusy");
                    put("Keywords", "keywords");
                    put("Language1", "language1");
                    put("Location", "location");
                    put("ManagersName", "managersName");
                    put("Mileage", "mileage");
                    put("Notes", "notes");
                    put("OfficeLocation", "officeLocation");
                    put("OrganizationalIDNumber", "organizationalIDNumber");
                    put("OtherAddressPOBox", "otherAddressPOBox");
                    put("Priority", "priority");
                    put("Private", "privateFlag");
                    put("Profession", "profession");
                    put("ReferredBy", "referredBy");
                    put("Sensitivity", "sensitivity");
                    put("Spouse", "spouse");
                    put("User1", "user1");
                    put("User2", "user2");
                    put("User3", "user3");
                    put("User4", "user4");
                    put("WebPage", "webPage");
                }
            });

    /**
     * Mapping for Russian and English container's fields names. <br>
     * <b>Key</b> - Russian container's field name as it was set by MS-Outlook inside
     * XLS contacts container's in header row,<br>
     * <b>value</b> - corresponding English field's name.<br>
     * <br>
     */
    static final Map<String, String> CONTAINER_FIELD_NAMING_MAP_RUS2ENGL = Collections
            .unmodifiableMap(new LinkedHashMap<String, String>() {
                private static final long serialVersionUID = -6949471211802960406L;

                {
                    put("Обращение", "Title");
                    put("Имя", "FirstName");
                    put("Отество", "MiddleName");
                    put("Фамилия", "LastName");
                    put("Суффикс", "Suffix");
                    put("Организация", "Company");
                    put("Отдел", "Department");
                    put("Должность", "JobTitle");
                    put("Улицарабадрес", "BusinessStreet");
                    put("Улица2рабадрес", "BusinessStreet2");
                    put("Улица3рабадрес", "BusinessStreet3");
                    put("Городрабадрес", "BusinessCity");
                    put("Областьрабадрес", "BusinessState");
                    put("Индексрабадрес", "BusinessPostalCode");
                    put("Странарабадрес", "BusinessCountryRegion");
                    put("Улицадомадрес", "HomeStreet");
                    put("Улица2домадрес", "HomeStreet2");
                    put("Улица3домадрес", "HomeStreet3");
                    put("Городдомадрес", "HomeCity");
                    put("Областьдомадрес", "HomeState");
                    put("Потовыйкоддом", "HomePostalCode");
                    put("Странадомадрес", "HomeCountryRegion");
                    put("Улицадругойадрес", "OtherStreet");
                    put("Улица2другойадрес", "OtherStreet2");
                    put("Улица3другойадрес", "OtherStreet3");
                    put("Городдругойадрес", "OtherCity");
                    put("Областьдругойадрес", "OtherState");
                    put("Индексдругойадрес", "OtherPostalCode");
                    put("Странадругойадрес", "OtherCountryRegion");
                    put("Телефонпомощника", "AssistantsPhone");
                    put("Рабоийфакс", "BusinessFax");
                    put("Рабоийтелефон", "BusinessPhone");
                    put("Телефонраб2", "BusinessPhone2");
                    put("Обратныйвызов", "Callback");
                    put("Телефонвмашине", "CarPhone");
                    put("Основнойтелефонорганизации", "CompanyMainPhone");
                    put("Домашнийфакс", "HomeFax");
                    put("Домашнийтелефон", "HomePhone");
                    put("Телефондом2", "HomePhone2");
                    put("ISDN", "ISDN");
                    put("Телефонпереносной", "MobilePhone");
                    put("Другойфакс", "OtherFax");
                    put("Другойтелефон", "OtherPhone");
                    put("Пейджер", "Pager");
                    put("Основнойтелефон", "PrimaryPhone");
                    put("Радиотелефон", "RadioPhone");
                    put("Телетайптелефонститрами", "TTYTDDPhone");
                    put("Телекс", "Telex");
                    put("Сет", "Account");
                    put("Годовщина", "Anniversary");
                    put("Имяпомощника", "AssistantsName");
                    put("Сета", "BillingInformation");
                    put("Деньрождения", "Birthday");
                    put("Потовыйящикрабадрес", "BusinessAddressPOBox");
                    put("Категории", "Categories");
                    put("Дети", "Children");
                    put("Серверкаталогов", "DirectoryServer");
                    put("Адресэлпоты", "EmailAddress");
                    put("Типэлпоты", "EmailType");
                    put("Краткоеимяэлпоты", "EmailDisplayName");
                    put("Адрес2элпоты", "Email2Address");
                    put("Тип2элпоты", "Email2Type");
                    put("Краткое2имяэлпоты", "Email2DisplayName");
                    put("Адрес3элпоты", "Email3Address");
                    put("Тип3элпоты", "Email3Type");
                    put("Краткое3имяэлпоты", "Email3DisplayName");
                    put("Пол", "Gender");
                    put("Линыйкод", "GovernmentIDNumber");
                    put("Хобби", "Hobby");
                    put("Потовыйящикдомадрес", "HomeAddressPOBox");
                    put("Инициалы", "Initials");
                    put("СведенияодоступностивИнтернете", "InternetFreeBusy");
                    put("Клюевыеслова", "Keywords");
                    put("Язык", "Language1");
                    put("Расположение", "Location");
                    put("Руководитель", "ManagersName");
                    put("Расстояние", "Mileage");
                    put("Заметки", "Notes");
                    put("Расположениекомнаты", "OfficeLocation");
                    put("Кодорганизации", "OrganizationalIDNumber");
                    put("Потовыйящикдругойадрес", "OtherAddressPOBox");
                    put("Пометка", "Priority");
                    put("_астное", "Private");
                    put("Профессия", "Profession");
                    put("Отложено", "ReferredBy");
                    put("Важность", "Sensitivity");
                    put("Супруга", "Spouse");
                    put("Пользователь1", "User1");
                    put("Пользователь2", "User2");
                    put("Пользователь3", "User3");
                    put("Пользователь4", "User4");
                    put("Вебстраница", "WebPage");
                }
            });
}

      