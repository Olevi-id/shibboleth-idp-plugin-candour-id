package fi.csc.shibboleth.plugin.candourid.messaging.impl;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The payload for {@link CandourInvitationRequest}
 *
 */
public class CandourInvitationRequestPayload {

    /** Duration for the session to be valid. Defaults to 10 minutes. */
    private Duration invitationValidity = Duration.ofMinutes(10);
    /** How many verification tries User is allowed to have. Defaults to 5. */
    private int tries = 5;
    /** redirect callback url. */
    private String callbackUrl;
    /** redirect callback post url. */
    private String callbackPostEndpoint;
    /** Allowed verification methods. */
    private AllowedVerificationMethods allowedVerificationMethods = new AllowedVerificationMethods();
    /** Allowed verification documents. */
    private AllowedVerificationDocuments allowedVerificationDocuments = new AllowedVerificationDocuments();
    /** Expected result claims and used matchers. */
    private ResultProperties resultProperties = new ResultProperties();
    /** Data for matching a user with resultProperties matchers. */
    private User user = new User();
    /** Data for matching a user. */
    private EnforceValues enforceValues = new EnforceValues();

    /**
     * Set duration for the session to be valid.
     * 
     * @param validityDuration Duration for the session to be valid
     */
    public void setInvitationValidity(Duration validityDuration) {
        invitationValidity = validityDuration;
    }

    /**
     * Current instant in format yyyy-MM-dd'T'HH:mm:ss.SSS'Z.
     * 
     * @return Current instant in format yyyy-MM-dd'T'HH:mm:ss.SSS'Z
     */
    public String getTimestamp() {
        return LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
    }

    /**
     * Current instant + session validity in format yyyy-MM-dd'T'HH:mm:ss.SSS'Z.
     * 
     * @return Current instant + session validity in format
     *         yyyy-MM-dd'T'HH:mm:ss.SSS'Z
     */
    public String getValidUntil() {
        return LocalDateTime.ofInstant(Instant.now().plus(invitationValidity), ZoneOffset.UTC)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
    }

    /**
     * Get how many verification tries User is allowed to have. Defaults to 5.
     * 
     * @return How many verification tries User is allowed to have. Defaults to 5
     */
    public int getTries() {
        return tries;
    }

    /**
     * Set how many verification tries User is allowed to have. Defaults to 5.
     * 
     * @param numTries How many verification tries User is allowed to have. Defaults
     *                 to 5
     */
    public void setTries(int numTries) {
        tries = numTries;
    }

    /**
     * Get redirect callback url.
     * 
     * @return Redirect callback url.
     */
    public String getCallbackUrl() {
        return callbackUrl;
    }

    /**
     * Set redirect callback url.
     * 
     * @param url Redirect callback url.
     */
    public void setCallbackUrl(String url) {
        callbackUrl = url;
    }

    /**
     * Get redirect callback post url.
     * 
     * @return redirect callback post url
     */
    public String getCallbackPostEndpoint() {
        return callbackPostEndpoint;
    }

    /**
     * Set redirect callback post url.
     * 
     * @param callbackPostEndpoint Redirect callback post url
     */
    public void setCallbackPostEndpoint(String endPoint) {
        callbackPostEndpoint = endPoint;
    }

    /**
     * Get allowed verification methods.
     * 
     * @return Allowed verification methods
     */
    public AllowedVerificationMethods getAllowedVerificationMethods() {
        return allowedVerificationMethods;
    }

    /**
     * Get allowed verification documents.
     * 
     * @return Allowed verification documents
     */
    public AllowedVerificationDocuments getAllowedVerificationDocuments() {
        return allowedVerificationDocuments;
    }

    /**
     * Get expected result claims and used matchers.
     * 
     * @return Expected result claims and used matchers
     */
    public ResultProperties getResultProperties() {
        return resultProperties;
    }

    /**
     * Get data for matching a user with resultProperties matchers.
     * 
     * @return data for matching a user with resultProperties matchers.
     */
    public User getUser() {
        return user;
    }

    /**
     * Get data for matching a user.
     * 
     * @return Data for matching a user
     */
    public EnforceValues getEnforceValues() {
        return enforceValues;
    }

    /**
     * Set allowed verification methods.
     * 
     * @param documents Allowed verification methods
     */
    public void setAllowedVerificationMethods(AllowedVerificationMethods methods) {
        allowedVerificationMethods = methods;
    }

    /**
     * Set Allowed verification documents.
     * 
     * @param properties Allowed verification documents
     */
    public void setAllowedVerificationDocuments(AllowedVerificationDocuments documents) {
        allowedVerificationDocuments = documents;
    }

    /**
     * Set expected result claims and used matchers.
     * 
     * @param properties Expected result claims and used matchers.
     */
    public void setResultProperties(ResultProperties properties) {
        resultProperties = properties;
    }

    /**
     * Set data for matching a user with resultProperties matchers.
     * 
     * @param usr Data for matching a user with resultProperties matchers
     */
    public void setUser(User usr) {
        user = usr;
    }

    /**
     * Set data for matching a user.
     * 
     * @param values Data for matching a user.
     */
    public void setEnforceValues(EnforceValues values) {
        enforceValues = values;
    }

    /**
     * Class implementing fields for allowed verification methods.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public class AllowedVerificationMethods {

        /** Whether web browser based optical verification is allowed. */
        private Boolean idWeb;

        /**
         * Whether mobile app based optical verification + nfc chip verification is
         * allowed.
         */
        private Boolean rfidApp;

        /** Whether mobile app based optical verification is allowed. */
        private Boolean idApp;

        /**
         * Whether web browser based optical verification is allowed.
         * 
         * @return whether web browser based optical verification is allowed
         */
        public Boolean isIdWeb() {
            return idWeb;
        }

        /**
         * Set whether web browser based optical verification is allowed.
         * 
         * @param allowed Whether web browser based optical verification is allowed
         */
        public void setIdWeb(Boolean allowed) {
            idWeb = allowed;
        }

        /**
         * Whether mobile app based optical verification + nfc chip verification is
         * allowed.
         * 
         * @return Whether mobile app based optical verification + nfc chip verification
         *         is allowed
         */
        public Boolean isRfidApp() {
            return rfidApp;
        }

        /**
         * Set whether mobile app based optical verification + nfc chip verification is
         * allowed.
         * 
         * @param allowed whether mobile app based optical verification + nfc chip
         *                verification is allowed.
         */
        public void setRfidApp(Boolean allowed) {
            rfidApp = allowed;
        }

        /**
         * Whether mobile app based optical verification is allowed.
         * 
         * @return Whether mobile app based optical verification is allowed
         */
        public Boolean isIdApp() {
            return idApp;
        }

        /**
         * Set whether mobile app based optical verification is allowed.
         * 
         * @param idApp whether mobile app based optical verification is allowed.
         */
        public void setIdApp(Boolean allowed) {
            idApp = allowed;
        }
    }

    /**
     * Class implementing fields for allowed verification documents.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public class AllowedVerificationDocuments {

        /** Whether passport is allowed as verification document. */
        private Boolean passport;
        /** Whether national id card is allowed as verification document. */
        private Boolean idCard;

        /**
         * Set whether passport is allowed as verification document.
         * 
         * @param allowed whether passport is allowed as verification document.
         */
        public void setPassport(Boolean allowed) {
            passport = allowed;
        }

        /**
         * Whether passport is allowed as verification document.
         * 
         * @return Whether passport is allowed as verification document
         */
        public Boolean isPassport() {
            return passport;
        }

        /**
         * Set whether national id card is allowed as verification document.
         * 
         * @param allowed Whether national id card is allowed as verification document
         */
        public void setIdCard(Boolean allowed) {
            idCard = allowed;
        }

        /**
         * Whether national id card is allowed as verification document.
         * 
         * @return Whether national id card is allowed as verification document
         */
        public Boolean isIdCard() {
            return idCard;
        }
    }

    /**
     * Class implementing fields for expected result claims and used matchers.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public class ResultProperties {
        /** Whether name data is returned. */
        private Boolean name;
        /** Whether expected name matching is used. */
        private Boolean nameMatch;
        /** Whether expected name matching is used and what is the match score. */
        private Boolean nameScore;
        /** Whether date of birth is returned. */
        private Boolean dateOfBirth;
        /** Whether expected date of birth matching is used. */
        private Boolean dateOfBirthMatch;
        /** Whether national identification data is returned. */
        private Boolean nationalIdenficationNumber;
        /** Whether id number of id document is returned. */
        private Boolean idNumber;
        /** Whether id type id document is returned. */
        private Boolean idDocumentType;
        /** Whether id document expiration is returned. */
        private Boolean idExpiration;
        /** Whether issuer country code is returned. */
        private Boolean idIssuer;
        /** Whether nationality is returned. */
        private Boolean nationality;
        /** Whether sex is returned. */
        private Boolean sex;
        /** Whether selfie img is returned. */
        private Boolean selfieImage;
        /** Whether MRZ side of id img is returned. */
        private Boolean idMrzImage;
        /** Whether other side of id img is returned. */
        private Boolean idOtherImage;
        /** Whether user img from id biometric is returned. */
        private Boolean idChipImage;

        /**
         * Whether name data is returned. True by default.
         * 
         * @return whether name data is returned. True by default
         */
        public Boolean isName() {
            return name;
        }

        /**
         * Set whether name data is returned. True by default.
         * 
         * @param isRequired Whether name data is returned. True by default.
         */
        public void setName(Boolean isRequired) {
            name = isRequired;
        }

        /**
         * Whether expected name matching is used.
         * 
         * @return whether expected name matching is used
         */
        public Boolean isNameMatch() {
            return nameMatch;
        }

        /**
         * Set whether expected name matching is used.
         * 
         * @param isUsed whether expected name matching is used.
         */
        public void setNameMatch(Boolean isUsed) {
            nameMatch = isUsed;
        }

        /**
         * Whether expected name matching is used and what is the match score.
         * 
         * @return Whether expected name matching is used and what is the match score
         */
        public Boolean isNameScore() {
            return nameScore;
        }

        /**
         * Set whether expected name matching is used and what is the match score.
         * 
         * @param isUsed Whether expected name matching is used and what is the match
         *               score
         */
        public void setNameScore(Boolean isUsed) {
            nameScore = isUsed;
        }

        /**
         * Whether date of birth is returned.
         * 
         * @return Whether date of birth is returned
         */
        public Boolean isDateOfBirth() {
            return dateOfBirth;
        }

        /**
         * Set whether date of birth is returned.
         * 
         * @param isRequired Whether date of birth is returned
         */
        public void setDateOfBirth(Boolean isRequired) {
            dateOfBirth = isRequired;
        }

        /**
         * Whether expected date of birth matching is used.
         * 
         * @return Whether expected date of birth matching is used
         */
        public Boolean isDateOfBirthMatch() {
            return dateOfBirthMatch;
        }

        /**
         * Set whether expected date of birth matching is used.
         * 
         * @param isUsed whether expected date of birth matching is used.
         */
        public void setDateOfBirthMatch(Boolean isUsed) {
            dateOfBirthMatch = isUsed;
        }

        /**
         * Whether national identification data is returned.
         * 
         * @return Whether national identification data is returned
         */
        public Boolean isNationalIdenficationNumber() {
            return nationalIdenficationNumber;
        }

        /**
         * Set whether national identification data is returned.
         * 
         * @param isRequired Whether national identification data is returned
         */
        public void setNationalIdenficationNumber(Boolean isRequired) {
            nationalIdenficationNumber = isRequired;
        }

        /**
         * Whether id number of id document is returned.
         * 
         * @return Whether id number of id document is returned
         */
        public Boolean isIdNumber() {
            return idNumber;
        }

        /**
         * Set whether id number of id document is returned.
         * 
         * @param isRequired Whether id number of id document is returned.
         */
        public void setIdNumber(Boolean isRequired) {
            idNumber = isRequired;
        }

        /**
         * Whether id type id document is returned.
         * 
         * @return Whether id type id document is returned
         */
        public Boolean isIdDocumentType() {
            return idDocumentType;
        }

        /**
         * Set whether id type id document is returned
         * 
         * @param isRequired Whether id type id document is returned.
         */
        public void setIdDocumentType(Boolean isRequired) {
            idDocumentType = isRequired;
        }

        /**
         * Whether id document expiration is returned.
         * 
         * @return Whether id document expiration is returned
         */
        public Boolean isIdExpiration() {
            return idExpiration;
        }

        /**
         * Set whether id document expiration is returned.
         * 
         * @param isRequired Whether id document expiration is returned.
         */
        public void setIdExpiration(Boolean isRequired) {
            idExpiration = isRequired;
        }

        /**
         * Whether issuer country code is returned.
         * 
         * @return Whether issuer country code is returned.
         */
        public Boolean isIdIssuer() {
            return idIssuer;
        }

        /**
         * Set whether issuer country code is returned.
         * 
         * @param isRequired Whether issuer country code is returned.
         */
        public void setIdIssuer(Boolean isRequired) {
            idIssuer = isRequired;
        }

        /**
         * Whether nationality is returned.
         * 
         * @return Whether nationality is returned
         */
        public Boolean isNationality() {
            return nationality;
        }

        /**
         * Set whether nationality is returned.
         * 
         * @param isRequired Whether nationality is returned.
         */
        public void setNationality(Boolean isRequired) {
            nationality = isRequired;
        }

        /**
         * Whether sex is returned.
         * 
         * @return Whether sex is returned.
         */
        public Boolean isSex() {
            return sex;
        }

        /**
         * Set whether sex is returned.
         * 
         * @param isRequired Whether sex is returned.
         */
        public void setSex(Boolean isRequired) {
            sex = isRequired;
        }

        /**
         * Whether selfie img is returned.
         * 
         * @return whether selfie img is returned.
         */
        public Boolean isSelfieImage() {
            return selfieImage;
        }

        /**
         * Set whether selfie img is returned.
         * 
         * @param isRequired Whether selfie img is returned
         */
        public void setSelfieImage(Boolean isRequired) {
            selfieImage = isRequired;
        }

        /**
         * Whether MRZ side of id img is returned.
         * 
         * @return Whether MRZ side of id img is returned
         */
        public Boolean isIdMrzImage() {
            return idMrzImage;
        }

        /**
         * Set whether MRZ side of id img is returned.
         * 
         * @param isRequired Whether MRZ side of id img is returned.
         */
        public void setIdMrzImage(Boolean isRequired) {
            idMrzImage = isRequired;
        }

        /**
         * Whether other side of id img is returned.
         * 
         * @return Whether other side of id img is returned
         */
        public Boolean isIdOtherImage() {
            return idOtherImage;
        }

        /**
         * Set whether other side of id img is returned.
         * 
         * @param isRequired Whether other side of id img is returned
         */
        public void setIdOtherImage(Boolean isRequired) {
            idOtherImage = isRequired;
        }

        /**
         * Whether user img from id biometric is returned.
         * 
         * @return Whether user img from id biometric is returned
         */
        public Boolean isIdChipImage() {
            return idChipImage;
        }

        /**
         * Set whether user img from id biometric is returned.
         * 
         * @param isRequired Whether user img from id biometric is returned
         */
        public void setIdChipImage(Boolean isRequired) {
            idChipImage = isRequired;
        }

    }

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
        private String nationalIdenficationNumber;
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
        public String getNationalIdenficationNumber() {
            return nationalIdenficationNumber;
        }

        /**
         * Set national identification number.
         * 
         * @param number National identification number
         */
        public void setNationalIdenficationNumber(String number) {
            nationalIdenficationNumber = number;
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

    /**
     * Serialize instance to json string.
     * 
     * @return json string representing the instance
     * @throws JsonProcessingException something went wrong
     */
    public String serialize() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(this);
    }

}
