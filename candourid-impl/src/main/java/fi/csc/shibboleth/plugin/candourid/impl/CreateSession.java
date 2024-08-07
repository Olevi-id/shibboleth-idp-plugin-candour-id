package fi.csc.shibboleth.plugin.candourid.impl;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.annotation.Nonnull;

import org.apache.hc.core5.net.URIBuilder;
import org.opensaml.profile.action.ActionSupport;
import org.opensaml.profile.action.EventIds;
import org.opensaml.profile.context.ProfileRequestContext;
import org.slf4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;

import fi.csc.shibboleth.plugin.candourid.messaging.impl.CandourInvitationRequest;
import fi.csc.shibboleth.plugin.candourid.messaging.impl.CandourInvitationRequestPayload;
import fi.csc.shibboleth.plugin.candourid.messaging.impl.CandourInvitationSuccessResponsePayload;
import fi.csc.shibboleth.plugin.candourid.messaging.impl.CandourResponse;
import jakarta.servlet.http.HttpServletRequest;
import net.shibboleth.idp.authn.context.AuthenticationContext;
import net.shibboleth.shared.annotation.constraint.NonnullAfterInit;
import net.shibboleth.shared.annotation.constraint.NotEmpty;
import net.shibboleth.shared.primitive.LoggerFactory;

/**
 * TODO: Test class is initialized properly before moving to doExecute. General
 * cleanup and unit tests needed.
 * 
 * TODO: Set response to TBD context for further processing.
 */
public class CreateSession extends AbstractCandourHttpAuthenticationAction {

    /** Class logger. */
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(CreateSession.class);

    /** Parameter supplied to identify the per-conversation parameter. */
    @Nonnull
    @NotEmpty
    public static final String CONVERSATION_KEY = "conversation";

    /** Candour API location. */
    private URI candouridURI;

    /** Candour API client public key. */
    private String clientPublicKey;

    /** Candour API client hmac key. */
    private String clientHmacKey;

    /** callback servlet path. */
    private String callbackServletPath;

    /** The payload to send to Candour. */
    @NonnullAfterInit
    private CandourInvitationRequestPayload payload;

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

    /**
     * Set the payload to send to Candour.
     * 
     * @param content the payload to send to Candour. Implement a strategy to set it
     */
    public void setPayload(CandourInvitationRequestPayload content) {
        payload = content;
    }

    /**
     * Set callback servlet path.
     * 
     * @param path callback servlet path
     */
    public void setCallbackServletPath(String path) {
        callbackServletPath = path;
    }

    /** {@inheritDoc} */
    @Override
    protected void doExecute(@Nonnull final ProfileRequestContext profileRequestContext,
            @Nonnull final AuthenticationContext authenticationContext) {
        CandourInvitationRequest message = new CandourInvitationRequest(candouridURI, clientPublicKey, clientHmacKey);
        message.setPayload(payload);
        // Clean following. Copied from rp auth module. ->
        HttpServletRequest request = getHttpServletRequestSupplier().get();
        final String scheme = request.getScheme();
        assert scheme != null;
        final String serverName = request.getServerName();
        assert serverName != null;
        URI redirectUri = null;
        try {
            redirectUri = buildURIIgnoreDefaultPorts(scheme, serverName, request.getServerPort(),
                    candourContext.getCallbackUri());
        } catch (URISyntaxException e1) {
            log.error("{} Exception occurred", getLogPrefix(), e1);
            ActionSupport.buildEvent(profileRequestContext, EventIds.INVALID_PROFILE_CTX);
        }
        message.getPayload().setCallbackUrl(redirectUri.toString());
        message.getPayload().setCallbackPostEndpoint(redirectUri.toString());
        log.debug("candour callback uri set as {}",candourContext.getCallbackUri());
        // Clean <-
        CandourResponse response = null;
        try {
            response = executeHttpRequest(message.toHttpRequest());
        } catch (IOException | InvalidKeyException | NoSuchAlgorithmException | IllegalStateException e) {
            log.error("{} Exception occurred", getLogPrefix(), e);
            ActionSupport.buildEvent(profileRequestContext, EventIds.IO_ERROR);
        }
        if (!response.indicateSuccess()) {
            log.error("{} Candour response status code {} and payload {}", getLogPrefix(), response.getCode(),
                    response.getPayload());
            // TODO: use candour specific error event
            ActionSupport.buildEvent(profileRequestContext, EventIds.IO_ERROR);
            return;
        }
        CandourInvitationSuccessResponsePayload payload = null;
        try {
            payload = CandourInvitationSuccessResponsePayload.parse(response.getPayload());
            log.debug("{} uri {} and session id {} parsed from payload", getLogPrefix(), payload.getRedirectUrl(),
                    payload.getVerificationSessionId());
        } catch (JsonProcessingException e) {
            log.error("{} Candour response parsing failed.", getLogPrefix(), e);
            // TODO: use candour specific error event
            ActionSupport.buildEvent(profileRequestContext, EventIds.IO_ERROR);
            return;
        }
        candourContext.setInvitationResponse(response.getPayload());
        candourContext.setAuthenticationUri(payload.getRedirectUrl());
        log.debug("{} Successfully received candour response payload {}", getLogPrefix(), response.getPayload());

    }

    /**
     * Build a {@link URI} from the given parameters. If the scheme is either 'http'
     * or 'https' with their respective default port, the port is set to -1.
     * 
     * @param scheme the scheme
     * @param host   the hostname
     * @param port   the port
     * @param path   the path
     * 
     * @return a fully built URI from the given parameters.
     * 
     * @throws URISyntaxException if the URI can not be constructed.
     */
    @Nonnull
    private final URI buildURIIgnoreDefaultPorts(@Nonnull final String scheme, @Nonnull final String host,
            final int port, @Nonnull final String path) throws URISyntaxException {

        int usedPort = port;
        if ("http".equalsIgnoreCase(scheme)) {
            // ignore port iff using the default http port
            if (port == 80) {
                usedPort = -1;
            }
        } else if ("https".equalsIgnoreCase(scheme)) {
            // ignore port iff using the default https port
            if (port == 443) {
                usedPort = -1;
            }
        }
        final URI builtUri = new URIBuilder().setScheme(scheme).setHost(host).setPort(usedPort).setPath(path).build();
        assert builtUri != null;
        return builtUri;
    }

}
