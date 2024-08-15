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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CandourInvitationRequestPayload {

    /** Duration for the session to be valid. Defaults to 10 minutes. */
    private Duration invitationValidity = Duration.ofMinutes(10);

    /** How many verification tries User is allowed to have. Defaults to 5. */
    private Integer tries = 5;

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
    public Integer getTries() {
        return tries;
    }

    /**
     * Set how many verification tries User is allowed to have. Defaults to 5.
     * 
     * @param numTries How many verification tries User is allowed to have. Defaults
     *                 to 5
     */
    public void setTries(Integer numTries) {
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
     * Serialize instance to json string.
     * 
     * @return json string representing the instance
     * @throws JsonProcessingException something went wrong
     */
    public String serialize() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(this);
    }

    /**
     * Serialize instance to json string.
     * 
     * @return json string representing the instance. Null if serialization fails.
     */
    public String toString() {
        try {
            return serialize();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
