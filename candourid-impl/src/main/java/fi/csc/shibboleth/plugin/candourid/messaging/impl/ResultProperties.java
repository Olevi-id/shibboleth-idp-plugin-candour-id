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
 * Class implementing fields for expected result claims and used matchers.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultProperties {

    /** Whether name data is returned. */
    @Nullable
    private Boolean name;

    /** Whether expected name matching is used. */
    @Nullable
    private Boolean nameMatch;

    /** Whether expected name matching is used and what is the match score. */
    @Nullable
    private Boolean nameScore;

    /** Whether date of birth is returned. */
    @Nullable
    private Boolean dateOfBirth;

    /** Whether expected date of birth matching is used. */
    @Nullable
    private Boolean dateOfBirthMatch;

    /** Whether national identification data is returned. */
    @Nullable
    private Boolean nationalIdentificationNumber;

    /** Whether id number of id document is returned. */
    @Nullable
    private Boolean idNumber;

    /** Whether id type id document is returned. */
    @Nullable
    private Boolean idDocumentType;

    /** Whether id document expiration is returned. */
    @Nullable
    private Boolean idExpiration;

    /** Whether issuer country code is returned. */
    @Nullable
    private Boolean idIssuer;

    /** Whether nationality is returned. */
    @Nullable
    private Boolean nationality;

    /** Whether sex is returned. */
    @Nullable
    private Boolean sex;

    /** Whether selfie img is returned. */
    @Nullable
    private Boolean selfieImage;

    /** Whether MRZ side of id img is returned. */
    @Nullable
    private Boolean idMrzImage;

    /** Whether other side of id img is returned. */
    @Nullable
    private Boolean idOtherImage;

    /** Whether user img from id biometric is returned. */
    @Nullable
    private Boolean idChipImage;

    /**
     * Whether name data is returned. True by default.
     * 
     * @return whether name data is returned. True by default
     */
    @Nullable
    public Boolean isName() {
        return name;
    }

    /**
     * Set whether name data is returned. True by default.
     * 
     * @param isRequired Whether name data is returned. True by default.
     */
    public void setName(@Nullable Boolean isRequired) {
        name = isRequired;
    }

    /**
     * Whether expected name matching is used.
     * 
     * @return whether expected name matching is used
     */
    @Nullable
    public Boolean isNameMatch() {
        return nameMatch;
    }

    /**
     * Set whether expected name matching is used.
     * 
     * @param isUsed whether expected name matching is used.
     */
    public void setNameMatch(@Nullable Boolean isUsed) {
        nameMatch = isUsed;
    }

    /**
     * Whether expected name matching is used and what is the match score.
     * 
     * @return Whether expected name matching is used and what is the match score
     */
    @Nullable
    public Boolean isNameScore() {
        return nameScore;
    }

    /**
     * Set whether expected name matching is used and what is the match score.
     * 
     * @param isUsed Whether expected name matching is used and what is the match
     *               score
     */
    public void setNameScore(@Nullable Boolean isUsed) {
        nameScore = isUsed;
    }

    /**
     * Whether date of birth is returned.
     * 
     * @return Whether date of birth is returned
     */
    @Nullable
    public Boolean isDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Set whether date of birth is returned.
     * 
     * @param isRequired Whether date of birth is returned
     */
    public void setDateOfBirth(@Nullable Boolean isRequired) {
        dateOfBirth = isRequired;
    }

    /**
     * Whether expected date of birth matching is used.
     * 
     * @return Whether expected date of birth matching is used
     */
    @Nullable
    public Boolean isDateOfBirthMatch() {
        return dateOfBirthMatch;
    }

    /**
     * Set whether expected date of birth matching is used.
     * 
     * @param isUsed whether expected date of birth matching is used.
     */
    public void setDateOfBirthMatch(@Nullable Boolean isUsed) {
        dateOfBirthMatch = isUsed;
    }

    /**
     * Whether national identification data is returned.
     * 
     * @return Whether national identification data is returned
     */
    @Nullable
    public Boolean isNationalIdentificationNumber() {
        return nationalIdentificationNumber;
    }

    /**
     * Set whether national identification data is returned.
     * 
     * @param isRequired Whether national identification data is returned
     */
    public void setNationalIdentificationNumber(@Nullable Boolean isRequired) {
        nationalIdentificationNumber = isRequired;
    }

    /**
     * Whether id number of id document is returned.
     * 
     * @return Whether id number of id document is returned
     */
    @Nullable
    public Boolean isIdNumber() {
        return idNumber;
    }

    /**
     * Set whether id number of id document is returned.
     * 
     * @param isRequired Whether id number of id document is returned.
     */
    public void setIdNumber(@Nullable Boolean isRequired) {
        idNumber = isRequired;
    }

    /**
     * Whether id type id document is returned.
     * 
     * @return Whether id type id document is returned
     */
    @Nullable
    public Boolean isIdDocumentType() {
        return idDocumentType;
    }

    /**
     * Set whether id type id document is returned
     * 
     * @param isRequired Whether id type id document is returned.
     */
    public void setIdDocumentType(@Nullable Boolean isRequired) {
        idDocumentType = isRequired;
    }

    /**
     * Whether id document expiration is returned.
     * 
     * @return Whether id document expiration is returned
     */
    @Nullable
    public Boolean isIdExpiration() {
        return idExpiration;
    }

    /**
     * Set whether id document expiration is returned.
     * 
     * @param isRequired Whether id document expiration is returned.
     */
    public void setIdExpiration(@Nullable Boolean isRequired) {
        idExpiration = isRequired;
    }

    /**
     * Whether issuer country code is returned.
     * 
     * @return Whether issuer country code is returned.
     */
    @Nullable
    public Boolean isIdIssuer() {
        return idIssuer;
    }

    /**
     * Set whether issuer country code is returned.
     * 
     * @param isRequired Whether issuer country code is returned.
     */
    public void setIdIssuer(@Nullable Boolean isRequired) {
        idIssuer = isRequired;
    }

    /**
     * Whether nationality is returned.
     * 
     * @return Whether nationality is returned
     */
    @Nullable
    public Boolean isNationality() {
        return nationality;
    }

    /**
     * Set whether nationality is returned.
     * 
     * @param isRequired Whether nationality is returned.
     */
    public void setNationality(@Nullable Boolean isRequired) {
        nationality = isRequired;
    }

    /**
     * Whether sex is returned.
     * 
     * @return Whether sex is returned.
     */
    @Nullable
    public Boolean isSex() {
        return sex;
    }

    /**
     * Set whether sex is returned.
     * 
     * @param isRequired Whether sex is returned.
     */
    public void setSex(@Nullable Boolean isRequired) {
        sex = isRequired;
    }

    /**
     * Whether selfie img is returned.
     * 
     * @return whether selfie img is returned.
     */
    @Nullable
    public Boolean isSelfieImage() {
        return selfieImage;
    }

    /**
     * Set whether selfie img is returned.
     * 
     * @param isRequired Whether selfie img is returned
     */
    public void setSelfieImage(@Nullable Boolean isRequired) {
        selfieImage = isRequired;
    }

    /**
     * Whether MRZ side of id img is returned.
     * 
     * @return Whether MRZ side of id img is returned
     */
    @Nullable
    public Boolean isIdMrzImage() {
        return idMrzImage;
    }

    /**
     * Set whether MRZ side of id img is returned.
     * 
     * @param isRequired Whether MRZ side of id img is returned.
     */
    public void setIdMrzImage(@Nullable Boolean isRequired) {
        idMrzImage = isRequired;
    }

    /**
     * Whether other side of id img is returned.
     * 
     * @return Whether other side of id img is returned
     */
    @Nullable
    public Boolean isIdOtherImage() {
        return idOtherImage;
    }

    /**
     * Set whether other side of id img is returned.
     * 
     * @param isRequired Whether other side of id img is returned
     */
    public void setIdOtherImage(@Nullable Boolean isRequired) {
        idOtherImage = isRequired;
    }

    /**
     * Whether user img from id biometric is returned.
     * 
     * @return Whether user img from id biometric is returned
     */
    @Nullable
    public Boolean isIdChipImage() {
        return idChipImage;
    }

    /**
     * Set whether user img from id biometric is returned.
     * 
     * @param isRequired Whether user img from id biometric is returned
     */
    public void setIdChipImage(@Nullable Boolean isRequired) {
        idChipImage = isRequired;
    }

}
