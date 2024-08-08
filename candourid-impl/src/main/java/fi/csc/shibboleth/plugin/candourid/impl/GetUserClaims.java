package fi.csc.shibboleth.plugin.candourid.impl;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import javax.annotation.Nonnull;

import org.opensaml.profile.action.ActionSupport;
import org.opensaml.profile.action.EventIds;
import org.opensaml.profile.context.ProfileRequestContext;
import org.slf4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import fi.csc.shibboleth.plugin.candourid.messaging.impl.CandourInvitationSuccessResponsePayload;
import fi.csc.shibboleth.plugin.candourid.messaging.impl.CandourResponse;
import fi.csc.shibboleth.plugin.candourid.messaging.impl.CandourResultRequest;
import net.shibboleth.idp.authn.context.AuthenticationContext;
import net.shibboleth.shared.primitive.LoggerFactory;

/**
 * TODO: Test class is initialized properly before moving to doExecute. General
 * cleanup and unit tests needed.
 * 
 * TODO: Set response to TBD context for further processing.
 */
public class GetUserClaims extends AbstractCandourHttpAuthenticationAction {

    /** Class logger. */
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(GetUserClaims.class);

    /** Candour API location. */
    private URI candouridURI;

    /** Candour API client public key. */
    private String clientPublicKey;

    /** Candour API client hmac key. */
    private String clientHmacKey;

    /**
     * Set Candour API location.
     * 
     * @param uri Candour API location
     * @throws URISyntaxException
     */
    public void setCandouridURI(String uri) throws URISyntaxException {
        candouridURI = new URI(uri);
    }

    /**
     * Set Candour API client public key.
     * 
     * @param publicKey Candour API client public key
     */
    public void setClientPublicKey(String publicKey) {
        clientPublicKey = publicKey;
    }

    /**
     * Set Candour API client hmac key.
     * 
     * @param hmacKey Candour API client hmac key
     */
    public void setClientHmacKey(String hmacKey) {
        clientHmacKey = hmacKey;
    }

    /** {@inheritDoc} */
    @Override
    protected void doExecute(@Nonnull final ProfileRequestContext profileRequestContext,
            @Nonnull final AuthenticationContext authenticationContext) {
        CandourResultRequest message = new CandourResultRequest(candouridURI, clientPublicKey, clientHmacKey);
        message.setPayload(candourContext.getSessionId());
        CandourResponse response = null;
        try {
            response = executeHttpRequest(message.toHttpRequest());
        } catch (IOException | InvalidKeyException | NoSuchAlgorithmException | IllegalStateException
                | URISyntaxException e) {
            log.error("{} Exception occurred", getLogPrefix(), e);
            ActionSupport.buildEvent(profileRequestContext, EventIds.IO_ERROR);
            return;
        }
        if (!response.indicateSuccess()) {
            log.error("{} Candour response status code {} and payload {}", getLogPrefix(), response.getCode(),
                    response.getPayload());
            // TODO: use candour specific error event
            ActionSupport.buildEvent(profileRequestContext, EventIds.IO_ERROR);
            return;
        }
        try {
            candourContext.setResultClaims(
                    new ObjectMapper().readValue(response.getPayload(), new TypeReference<Map<String, Object>>() {
                    }));
        } catch (JsonProcessingException e) {
            log.error("{} Candour response claims parsing failed.", getLogPrefix(), e);
            // TODO: use candour specific error event
            ActionSupport.buildEvent(profileRequestContext, EventIds.IO_ERROR);
            return;
        }
        //TODO: Next action should validate result claims like invitation and status
        log.debug("{} Successfully received candour response payload {}", getLogPrefix(), response.getPayload());

    }

}
