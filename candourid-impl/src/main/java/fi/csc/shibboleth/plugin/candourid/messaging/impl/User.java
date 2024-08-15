/*
 * Copyright (c) 2024 CSC- IT Center for Science, www.csc.fi
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fi.csc.shibboleth.plugin.candourid.messaging.impl;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Class implementing fields that are input for user matchers.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {

    /** First name. */
    private String firstName;
    /** Last name. */
    private String lastName;
    /** Date of birth in YYYY-MM-DD */
    private String dateOfBirth;
    /** National identification number. */
    private String nationalIdentificationNumber;
    /** Id document number. */
    private String idNumber;
    /** Id document expiration date. */
    private String expirationDate;
    /** Country that has issued ID document. */
    private String issuingCountry;
    /** User nationality. */
    private String nationality;
    /** Custom identifier for the user. */
    private String identifier;
    /** User sex, ‘M’ or ‘F’. */
    private String sex;

    /**
     * Get first name.
     * 
     * @return First name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Set first name.
     * 
     * @param name First name
     */
    public void setFirstName(String name) {
        firstName = name;
    }

    /**
     * Get last name.
     * 
     * @return Last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Set last name.
     * 
     * @param name Last name
     */
    public void setLastName(String name) {
        lastName = name;
    }

    /**
     * Get date of birth in YYYY-MM-DD.
     * 
     * @return Date of birth in YYYY-MM-DD
     */
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Set date of birth in YYYY-MM-DD.
     * 
     * @param date Date of birth in YYYY-MM-DD
     */
    public void setDateOfBirth(String date) {
        dateOfBirth = date;
    }

    /**
     * Get national identification number.
     * 
     * @return National identification number
     */
    public String getNationalIdentificationNumber() {
        return nationalIdentificationNumber;
    }

    /**
     * Set national identification number.
     * 
     * @param number National identification number
     */
    public void setNationalIdentificationNumber(String number) {
        nationalIdentificationNumber = number;
    }

    /**
     * Get id document number.
     * 
     * @return Id document number
     */
    public String getIdNumber() {
        return idNumber;
    }

    /**
     * Set id document number.
     * 
     * @param number Id document number
     */
    public void setIdNumber(String number) {
        idNumber = number;
    }

    /**
     * Get dd document expiration date.
     * 
     * @return Id document expiration date
     */
    public String getExpirationDate() {
        return expirationDate;
    }

    /**
     * Set id document expiration date.
     * 
     * @param date Id document expiration date
     */
    public void setExpirationDate(String date) {
        expirationDate = date;
    }

    /**
     * Get country that has issued ID document.
     * 
     * @return Country that has issued ID document
     */
    public String getIssuingCountry() {
        return issuingCountry;
    }

    /**
     * Set country that has issued ID document.
     * 
     * @param country Country that has issued ID document
     */
    public void setIssuingCountry(String country) {
        issuingCountry = country;
    }

    /**
     * Get user nationality.
     * 
     * @return User nationality
     */
    public String getNationality() {
        return nationality;
    }

    /**
     * Set user nationality.
     * 
     * @param nat User nationality
     */
    public void setNationality(String nat) {
        nationality = nat;
    }

    /**
     * Get custom identifier for the user.
     * 
     * @return Custom identifier for the user
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Set custom identifier for the user.
     * 
     * @param id Custom identifier for the user
     */
    public void setIdentifier(String id) {
        identifier = id;
    }

    /**
     * Get user sex, ‘M’ or ‘F’.
     * 
     * @return User sex, ‘M’ or ‘F’
     */
    public String getSex() {
        return sex;
    }

    /**
     * Set user sex, ‘M’ or ‘F’..
     * 
     * @param sexId User sex, ‘M’ or ‘F’
     */
    public void setSex(String sexId) {
        sex = sexId;
    }
}
