package fi.csc.shibboleth.plugin.candourid.messaging.impl;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Class implementing fields that indicate which matchers must match.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EnforceValues {
    /** Whether expected date of birth matching is enforced. */
    private Boolean dateOfBirth;
    /** Whether identity document number in id matching is enforced. */
    private Boolean idNumber;
    /** Whether national identification number in id matching is enforced. */
    private Boolean nationalIdentificationNumber;
    /** Whether document expiration date in id matching is enforced. */
    private Boolean expirationDate;
    /** Whether document issuing country in id matching is enforced. */
    private Boolean issuingCountry;
    /** Whether user nationality in id matching is enforced. */
    private Boolean nationality;
    /** Whether user sex id matching is enforced. */
    private Boolean sex;
    /**
     * Number between 1-100, a threshold that name score must be equal or higher.
     */
    private int nameScore = 0;

    /**
     * Whether expected date of birth matching is enforced.
     * 
     * @return Whether expected date of birth matching is enforced.
     */
    public Boolean isDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Set whether expected date of birth matching is enforced.
     * 
     * @param isEnforced Whether expected date of birth matching is enforced
     */
    public void setDateOfBirth(Boolean isEnforced) {
        dateOfBirth = isEnforced;
    }

    /**
     * Whether identity document number in id matching is enforced.
     * 
     * @return Whether identity document number in id matching is enforced
     */
    public Boolean isIdNumber() {
        return idNumber;
    }

    /**
     * Set whether identity document number in id matching is enforced.
     * 
     * @param isEnforced Whether identity document number in id matching is enforced
     */
    public void setIdNumber(Boolean isEnforced) {
        idNumber = isEnforced;
    }

    /**
     * Whether national identification number in id matching is enforced.
     * 
     * @return Whether national identification number in id matching is enforced.
     */
    public Boolean isNationalIdentificationNumber() {
        return nationalIdentificationNumber;
    }

    /**
     * Set whether national identification number in id matching is enforced.
     * 
     * @param isEnforced Whether national identification number in id matching is
     *                   enforced
     */
    public void setNationalIdentificationNumber(Boolean isEnforced) {
        nationalIdentificationNumber = isEnforced;
    }

    /**
     * Whether document expiration date in id matching is enforced.
     * 
     * @return Whether document expiration date in id matching is enforced
     */
    public Boolean isExpirationDate() {
        return expirationDate;
    }

    /**
     * Set whether document expiration date in id matching is enforced.
     * 
     * @param isEnforced Whether document expiration date in id matching is enforced
     */
    public void setExpirationDate(Boolean isEnforced) {
        expirationDate = isEnforced;
    }

    /**
     * Whether document issuing country in id matching is enforced.
     * 
     * @return Whether document issuing country in id matching is enforced.
     */
    public Boolean isIssuingCountry() {
        return issuingCountry;
    }

    /**
     * Set whether document issuing country in id matching is enforced.
     * 
     * @param isEnforced Whether document issuing country in id matching is enforced
     */
    public void setIssuingCountry(Boolean isEnforced) {
        issuingCountry = isEnforced;
    }

    /**
     * Whether nationality in id matching is enforced.
     * 
     * @return Whether nationality in id matching is enforced
     */
    public Boolean isNationality() {
        return nationality;
    }

    /**
     * Set whether nationality in id matching is enforced.
     * 
     */
    public void setNationality(Boolean isEnforced) {
        nationality = isEnforced;
    }

    /**
     * Whether user sex in id matching is enforced.
     * 
     * @return Whether user sex in id matching is enforced
     */
    public Boolean isSex() {
        return sex;
    }

    /**
     * Set whether user sex in id matching is enforced.
     * 
     */
    public void setSex(Boolean isEnforced) {
        sex = isEnforced;
    }

    /**
     * Get number between 1-100, a threshold that name score must be equal or
     * higher.
     * 
     * @return Number between 1-100, a threshold that name score must be equal or
     *         higher
     */
    public int getNameScore() {
        return nameScore;
    }

    /**
     * Set number between 1-100, a threshold that name score must be equal or
     * higher.
     * 
     * @param score Number between 1-100, a threshold that name score must be equal
     *              or higher
     */
    public void setNameScore(int score) {
        nameScore = score;
    }

}
