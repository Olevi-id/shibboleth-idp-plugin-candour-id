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

    /** Duration for the session to be valid. Defaults to 10 minutes.*/
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
    private Users users = new Users();
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
    public Users getUsers() {
        return users;
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
     * Class implementing fields for allowed verification methods.
     */
    public class AllowedVerificationMethods {

        /** Whether web browser based optical verification is allowed. */
        private boolean idWeb;

        /**
         * Whether mobile app based optical verification + nfc chip verification is
         * allowed.
         */
        private boolean rfidApp;

        /** Whether mobile app based optical verification is allowed. */
        private boolean idApp;

        /**
         * Whether web browser based optical verification is allowed.
         * 
         * @return whether web browser based optical verification is allowed
         */
        public boolean isIdWeb() {
            return idWeb;
        }

        /**
         * Set whether web browser based optical verification is allowed.
         * 
         * @param allowed Whether web browser based optical verification is allowed
         */
        public void setIdWeb(boolean allowed) {
            idWeb = allowed;
        }

        /**
         * Whether mobile app based optical verification + nfc chip verification is
         * allowed.
         * 
         * @return Whether mobile app based optical verification + nfc chip verification
         *         is allowed
         */
        public boolean isRfidApp() {
            return rfidApp;
        }

        /**
         * Set whether mobile app based optical verification + nfc chip verification is
         * allowed.
         * 
         * @param allowed whether mobile app based optical verification + nfc chip
         *                verification is allowed.
         */
        public void setRfidApp(boolean allowed) {
            rfidApp = allowed;
        }

        /**
         * Whether mobile app based optical verification is allowed.
         * 
         * @return Whether mobile app based optical verification is allowed
         */
        public boolean isIdApp() {
            return idApp;
        }

        /**
         * Set whether mobile app based optical verification is allowed.
         * 
         * @param idApp whether mobile app based optical verification is allowed.
         */
        public void setIdApp(boolean allowed) {
            idApp = allowed;
        }
    }

    /**
     * Class implementing fields for allowed verification documents.
     */
    public class AllowedVerificationDocuments {

        /** Whether passport is allowed as verification document. */
        private boolean passport;
        /** Whether national id card is allowed as verification document. */
        private boolean idCard;

        /**
         * Set whether passport is allowed as verification document.
         * 
         * @param allowed whether passport is allowed as verification document.
         */
        public void setPassport(boolean allowed) {
            passport = allowed;
        }

        /**
         * Whether passport is allowed as verification document.
         * 
         * @return Whether passport is allowed as verification document
         */
        public boolean isPassport() {
            return passport;
        }

        /**
         * Set whether national id card is allowed as verification document.
         * 
         * @param allowed Whether national id card is allowed as verification document
         */
        public void setIdCard(boolean allowed) {
            idCard = allowed;
        }

        /**
         * Whether national id card is allowed as verification document.
         * 
         * @return Whether national id card is allowed as verification document
         */
        public boolean isIdCard() {
            return idCard;
        }
    }

    /**
     * Class implementing fields for expected result claims and used matchers.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public class ResultProperties {
        /** Whether name data is returned. True by default. */
        private boolean name = true;
        /** Whether expected name matching is used. */
        private boolean nameMatch;
        /** Whether expected name matching is used and what is the match score. */
        private boolean nameScore;
        /** Whether date of birth is returned. */
        private boolean dateOfBirth;
        /** Whether expected date of birth matching is used. */
        private boolean dateOfBirthMatch;
        /** Whether national identification data is returned. */
        private boolean nationalIdenficationNumber;
        /** Whether id number of id document is returned. */
        private boolean idNumber;
        /** Whether id type id document is returned. */
        private boolean idDocumentType;
        /** Whether id document expiration is returned. */
        private boolean idExpiration;
        /** Whether issuer country code is returned. */
        private boolean idIssuer;
        /** Whether nationality is returned. */
        private boolean nationality;
        /** Whether sex is returned. */
        private boolean sex;
        /** Whether selfie img is returned. */
        private boolean selfieImage;
        /** Whether MRZ side of id img is returned. */
        private boolean idMrzImage;
        /** Whether other side of id img is returned. */
        private boolean idOtherImage;
        /** Whether user img from id biometric is returned. */
        private boolean idChipImage;

        /**
         * Whether name data is returned. True by default.
         * 
         * @return whether name data is returned. True by default
         */
        public boolean isName() {
            return name;
        }

        /**
         * Set whether name data is returned. True by default.
         * 
         * @param isRequired Whether name data is returned. True by default.
         */
        public void setName(boolean isRequired) {
            name = isRequired;
        }

        /**
         * Whether expected name matching is used.
         * 
         * @return whether expected name matching is used
         */
        public boolean isNameMatch() {
            return nameMatch;
        }

        /**
         * Set whether expected name matching is used.
         * 
         * @param isUsed whether expected name matching is used.
         */
        public void setNameMatch(boolean isUsed) {
            nameMatch = isUsed;
        }

        /**
         * Whether expected name matching is used and what is the match score.
         * 
         * @return Whether expected name matching is used and what is the match score
         */
        public boolean isNameScore() {
            return nameScore;
        }

        /**
         * Set whether expected name matching is used and what is the match score.
         * 
         * @param isUsed Whether expected name matching is used and what is the match
         *               score
         */
        public void setNameScore(boolean isUsed) {
            nameScore = isUsed;
        }

        /**
         * Whether date of birth is returned.
         * 
         * @return Whether date of birth is returned
         */
        public boolean isDateOfBirth() {
            return dateOfBirth;
        }

        /**
         * Set whether date of birth is returned.
         * 
         * @param isRequired Whether date of birth is returned
         */
        public void setDateOfBirth(boolean isRequired) {
            dateOfBirth = isRequired;
        }

        /**
         * Whether expected date of birth matching is used.
         * 
         * @return Whether expected date of birth matching is used
         */
        public boolean isDateOfBirthMatch() {
            return dateOfBirthMatch;
        }

        /**
         * Set whether expected date of birth matching is used.
         * 
         * @param isUsed whether expected date of birth matching is used.
         */
        public void setDateOfBirthMatch(boolean isUsed) {
            dateOfBirthMatch = isUsed;
        }

        /**
         * Whether national identification data is returned.
         * 
         * @return Whether national identification data is returned
         */
        public boolean isNationalIdenficationNumber() {
            return nationalIdenficationNumber;
        }

        /**
         * Set whether national identification data is returned.
         * 
         * @param isRequired Whether national identification data is returned
         */
        public void setNationalIdenficationNumber(boolean isRequired) {
            nationalIdenficationNumber = isRequired;
        }

        /**
         * Whether id number of id document is returned.
         * 
         * @return Whether id number of id document is returned
         */
        public boolean isIdNumber() {
            return idNumber;
        }

        /**
         * Set whether id number of id document is returned.
         * 
         * @param isRequired Whether id number of id document is returned.
         */
        public void setIdNumber(boolean isRequired) {
            idNumber = isRequired;
        }

        /**
         * Whether id type id document is returned.
         * 
         * @return Whether id type id document is returned
         */
        public boolean isIdDocumentType() {
            return idDocumentType;
        }

        /**
         * Set whether id type id document is returned
         * 
         * @param isRequired Whether id type id document is returned.
         */
        public void setIdDocumentType(boolean isRequired) {
            idDocumentType = isRequired;
        }

        /**
         * Whether id document expiration is returned.
         * 
         * @return Whether id document expiration is returned
         */
        public boolean isIdExpiration() {
            return idExpiration;
        }

        /**
         * Set whether id document expiration is returned.
         * 
         * @param isRequired Whether id document expiration is returned.
         */
        public void setIdExpiration(boolean isRequired) {
            idExpiration = isRequired;
        }

        /**
         * Whether issuer country code is returned.
         * 
         * @return Whether issuer country code is returned.
         */
        public boolean isIdIssuer() {
            return idIssuer;
        }

        /**
         * Set whether issuer country code is returned.
         * 
         * @param isRequired Whether issuer country code is returned.
         */
        public void setIdIssuer(boolean isRequired) {
            idIssuer = isRequired;
        }

        /**
         * Whether nationality is returned.
         * 
         * @return Whether nationality is returned
         */
        public boolean isNationality() {
            return nationality;
        }

        /**
         * Set whether nationality is returned.
         * 
         * @param isRequired Whether nationality is returned.
         */
        public void setNationality(boolean isRequired) {
            nationality = isRequired;
        }

        /**
         * Whether sex is returned.
         * 
         * @return Whether sex is returned.
         */
        public boolean isSex() {
            return sex;
        }

        /**
         * Set whether sex is returned.
         * 
         * @param isRequired Whether sex is returned.
         */
        public void setSex(boolean isRequired) {
            sex = isRequired;
        }

        /**
         * Whether selfie img is returned.
         * 
         * @return whether selfie img is returned.
         */
        public boolean isSelfieImage() {
            return selfieImage;
        }

        /**
         * Set whether selfie img is returned.
         * 
         * @param isRequired Whether selfie img is returned
         */
        public void setSelfieImage(boolean isRequired) {
            selfieImage = isRequired;
        }

        /**
         * Whether MRZ side of id img is returned.
         * 
         * @return Whether MRZ side of id img is returned
         */
        public boolean isIdMrzImage() {
            return idMrzImage;
        }

        /**
         * Set whether MRZ side of id img is returned.
         * 
         * @param isRequired Whether MRZ side of id img is returned.
         */
        public void setIdMrzImage(boolean isRequired) {
            idMrzImage = isRequired;
        }

        /**
         * Whether other side of id img is returned.
         * 
         * @return Whether other side of id img is returned
         */
        public boolean isIdOtherImage() {
            return idOtherImage;
        }

        /**
         * Set whether other side of id img is returned.
         * 
         * @param isRequired Whether other side of id img is returned
         */
        public void setIdOtherImage(boolean isRequired) {
            idOtherImage = isRequired;
        }

        /**
         * Whether user img from id biometric is returned.
         * 
         * @return Whether user img from id biometric is returned
         */
        public boolean isIdChipImage() {
            return idChipImage;
        }

        /**
         * Set whether user img from id biometric is returned.
         * 
         * @param isRequired Whether user img from id biometric is returned
         */
        public void setIdChipImage(boolean isRequired) {
            idChipImage = isRequired;
        }

    }

    /**
     * Class implementing fields that are input for user matchers.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public class Users {

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
    }

    /**
     * Class implementing fields that indicate which matchers must match.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public class EnforceValues {
        /** Whether expected date of birth matching is enforced. */
        private boolean dateOfBirth;
        /** Whether Identity document number in id matching is enforced. */
        private boolean idNumber;

        /**
         * Whether expected date of birth matching is enforced.
         * 
         * @return Whether expected date of birth matching is enforced.
         */
        public boolean isDateOfBirth() {
            return dateOfBirth;
        }

        /**
         * Set whether expected date of birth matching is enforced.
         * 
         * @param isEnforced Whether expected date of birth matching is enforced
         */
        public void setDateOfBirth(boolean isEnforced) {
            dateOfBirth = isEnforced;
        }

        /**
         * Whether Identity document number in id matching is enforced.
         * 
         * @return Whether Identity document number in id matching is enforced
         */
        public boolean isIdNumber() {
            return idNumber;
        }

        /**
         * Set whether Identity document number in id matching is enforced.
         * 
         * @param isEnforced Whether Identity document number in id matching is enforced
         */
        public void setIdNumber(boolean isEnforced) {
            idNumber = isEnforced;
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
