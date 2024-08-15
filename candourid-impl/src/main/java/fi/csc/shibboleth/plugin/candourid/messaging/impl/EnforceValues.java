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

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Class implementing fields that indicate which matchers must match.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EnforceValues {

    /** Whether expected date of birth matching is enforced. */
    @Nullable
    private Boolean dateOfBirth;

    /** Whether identity document number in id matching is enforced. */
    @Nullable
    private Boolean idNumber;

    /** Whether national identification number in id matching is enforced. */
    @Nullable
    private Boolean nationalIdentificationNumber;

    /** Whether document expiration date in id matching is enforced. */
    @Nullable
    private Boolean expirationDate;

    /** Whether document issuing country in id matching is enforced. */
    @Nullable
    private Boolean issuingCountry;

    /** Whether user nationality in id matching is enforced. */
    @Nullable
    private Boolean nationality;

    /** Whether user sex id matching is enforced. */
    @Nullable
    private Boolean sex;

    /**
     * Number between 1-100, a threshold that name score must be equal or higher.
     */
    @Nullable
    private Integer nameScore;

    /**
     * Whether expected date of birth matching is enforced.
     * 
     * @return Whether expected date of birth matching is enforced.
     */
    @Nullable
    public Boolean isDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Set whether expected date of birth matching is enforced.
     * 
     * @param isEnforced Whether expected date of birth matching is enforced
     */
    public void setDateOfBirth(@Nullable Boolean isEnforced) {
        dateOfBirth = isEnforced;
    }

    /**
     * Whether identity document number in id matching is enforced.
     * 
     * @return Whether identity document number in id matching is enforced
     */
    @Nullable
    public Boolean isIdNumber() {
        return idNumber;
    }

    /**
     * Set whether identity document number in id matching is enforced.
     * 
     * @param isEnforced Whether identity document number in id matching is enforced
     */
    public void setIdNumber(@Nullable Boolean isEnforced) {
        idNumber = isEnforced;
    }

    /**
     * Whether national identification number in id matching is enforced.
     * 
     * @return Whether national identification number in id matching is enforced.
     */
    @Nullable
    public Boolean isNationalIdentificationNumber() {
        return nationalIdentificationNumber;
    }

    /**
     * Set whether national identification number in id matching is enforced.
     * 
     * @param isEnforced Whether national identification number in id matching is
     *                   enforced
     */
    public void setNationalIdentificationNumber(@Nullable Boolean isEnforced) {
        nationalIdentificationNumber = isEnforced;
    }

    /**
     * Whether document expiration date in id matching is enforced.
     * 
     * @return Whether document expiration date in id matching is enforced
     */
    @Nullable
    public Boolean isExpirationDate() {
        return expirationDate;
    }

    /**
     * Set whether document expiration date in id matching is enforced.
     * 
     * @param isEnforced Whether document expiration date in id matching is enforced
     */
    public void setExpirationDate(@Nullable Boolean isEnforced) {
        expirationDate = isEnforced;
    }

    /**
     * Whether document issuing country in id matching is enforced.
     * 
     * @return Whether document issuing country in id matching is enforced.
     */
    @Nullable
    public Boolean isIssuingCountry() {
        return issuingCountry;
    }

    /**
     * Set whether document issuing country in id matching is enforced.
     * 
     * @param isEnforced Whether document issuing country in id matching is enforced
     */
    public void setIssuingCountry(@Nullable Boolean isEnforced) {
        issuingCountry = isEnforced;
    }

    /**
     * Whether nationality in id matching is enforced.
     * 
     * @return Whether nationality in id matching is enforced
     */
    @Nullable
    public Boolean isNationality() {
        return nationality;
    }

    /**
     * Set whether nationality in id matching is enforced.
     * 
     */
    public void setNationality(@Nullable Boolean isEnforced) {
        nationality = isEnforced;
    }

    /**
     * Whether user sex in id matching is enforced.
     * 
     * @return Whether user sex in id matching is enforced
     */
    @Nullable
    public Boolean isSex() {
        return sex;
    }

    /**
     * Set whether user sex in id matching is enforced.
     * 
     */
    public void setSex(@Nullable Boolean isEnforced) {
        sex = isEnforced;
    }

    /**
     * Get number between 1-100, a threshold that name score must be equal or
     * higher.
     * 
     * @return Number between 1-100, a threshold that name score must be equal or
     *         higher
     */
    @Nullable
    public Integer getNameScore() {
        return nameScore;
    }

    /**
     * Set number between 1-100, a threshold that name score must be equal or
     * higher.
     * 
     * @param score Number between 1-100, a threshold that name score must be equal
     *              or higher
     */
    public void setNameScore(@Nullable Integer score) {
        nameScore = score;
    }

}
