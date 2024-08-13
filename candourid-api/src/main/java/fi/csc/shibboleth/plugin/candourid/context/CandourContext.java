
package fi.csc.shibboleth.plugin.candourid.context;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.opensaml.messaging.context.BaseContext;

/**
 * Context carrying the state between actions.
 */
public class CandourContext extends BaseContext {

    /** Response to invitation request. */
    @Nullable
    private String invitationResponse;

    /** Uri to authenticate user. */
    @Nullable
    private String authenticationUri;

    /** Uri for callback. */
    @Nullable
    private String callbackUri;

    /** Session id as result of successful authentication response. */
    @Nullable
    private String sessionId;

    /** Result claims from candour. */
    @Nonnull
    private Map<String, Object> resultClaims = new HashMap<String, Object>();

    /**
     * Get response to invitation request.
     * 
     * @return
     */
    @Nullable
    public String getInvitationResponse() {
        return invitationResponse;
    }

    /**
     * Set response to invitation request.
     * 
     * @param response response to invitation request.
     */
    public void setInvitationResponse(@Nullable String response) {
        invitationResponse = response;
    }

    /**
     * Get uri to authenticate user.
     * 
     * @return Uri to authenticate user
     */
    @Nullable
    public String getAuthenticationUri() {
        return authenticationUri;
    }

    /**
     * Set uri to authenticate user.
     * 
     * @param uri Uri to authenticate user
     */
    public void setAuthenticationUri(@Nonnull String uri) {
        assert uri != null;
        authenticationUri = uri;
    }

    /**
     * Get uri for callback.
     * 
     * @return Uri for callback
     */
    @Nullable
    public String getCallbackUri() {
        return callbackUri;
    }

    /**
     * Set uri for callback.
     * 
     * @param uri Uri for callback
     */
    public void setCallbackUri(@Nullable String uri) {
        callbackUri = uri;
    }

    /**
     * Get session id as result of successful authentication response.
     * 
     * @return Session id as result of successful authentication response.
     */
    @Nullable
    public String getSessionId() {
        return sessionId;
    }

    /**
     * Get session id as result of successful authentication response.
     * 
     * @param id Session id as result of successful authentication response.
     */
    public void setSessionId(@Nullable String id) {
        sessionId = id;
    }

    /**
     * Get result claims from candour.
     * 
     * @return Result claims from candour
     */
    public Map<String, Object> getResultClaims() {
        return resultClaims;
    }

    /**
     * Set result claims from candour
     * 
     * @param claims Result claims from candour
     */
    public void setResultClaims(@Nonnull Map<String, Object> claims) {
        assert claims != null;
        resultClaims = claims;
    }

}
