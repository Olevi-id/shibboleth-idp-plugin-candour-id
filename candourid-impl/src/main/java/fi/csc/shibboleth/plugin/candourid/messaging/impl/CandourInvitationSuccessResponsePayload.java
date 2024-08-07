package fi.csc.shibboleth.plugin.candourid.messaging.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The payload of success response to {@link CandourInvitationRequest}
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CandourInvitationSuccessResponsePayload {

    /** Url for redirecting user to authenticate. */
    private String redirectUrl;
    /** Session verification id. */
    private String verificationSessionId;
    /** Time stamp. */
    private String timestamp;
    /** Valid until. */
    private String validUntil;

    /**
     * Get url for redirecting user to authenticate.
     * 
     * @return Url for redirecting user to authenticate
     */
    public String getRedirectUrl() {
        return redirectUrl;
    }

    /**
     * Set url for redirecting user to authenticate.
     * 
     * @param url Url for redirecting user to authenticate
     */
    public void setRedirectUrl(String url) {
        redirectUrl = url;
    }

    /**
     * Get session verification id.
     * 
     * @return Session verification id
     */
    public String getVerificationSessionId() {
        return verificationSessionId;
    }

    /**
     * Set session verification id.
     * 
     * @param id Session verification id
     */
    public void setVerificationSessionId(String id) {
        verificationSessionId = id;
    }

    /**
     * Get time stamp.
     * 
     * @return Time stamp
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * Set time stamp.
     * 
     * @param Time stamp.
     */
    public void setTimestamp(String stamp) {
        timestamp = stamp;
    }

    /**
     * Get Valid until.
     * 
     * @return Valid until
     */
    public String getValidUntil() {
        return validUntil;
    }

    /**
     * Set
     * 
     * @param stamp
     */
    public void setValidUntil(String stamp) {
        validUntil = stamp;
    }

    /**
     * Parse instance from json representation.
     * 
     * @param payload json string representing the instance
     * @return AccessTokenImpl parsed from json representation
     * @throws JsonMappingException    json contained illegal fields
     * @throws JsonProcessingException json is not json at all
     */
    public static CandourInvitationSuccessResponsePayload parse(String payload)
            throws JsonMappingException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(payload, CandourInvitationSuccessResponsePayload.class);
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
