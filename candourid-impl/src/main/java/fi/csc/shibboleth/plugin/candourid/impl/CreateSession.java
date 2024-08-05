package fi.csc.shibboleth.plugin.candourid.impl;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.annotation.Nonnull;

import org.apache.hc.core5.net.URIBuilder;
import org.opensaml.profile.context.ProfileRequestContext;
import org.slf4j.Logger;

import fi.csc.shibboleth.plugin.candourid.messaging.impl.CandourInvitationRequest;
import fi.csc.shibboleth.plugin.candourid.messaging.impl.CandourInvitationRequestPayload;
import jakarta.servlet.http.HttpServletRequest;
import net.shibboleth.shared.annotation.constraint.NonnullAfterInit;
import net.shibboleth.shared.primitive.LoggerFactory;

/**
 * TODO: Test class is initialized properly before moving to doExecute. General
 * cleanup and unit tests needed.
 * 
 * TODO: Set response to TBD context for further processing.
 */
public class CreateSession extends AbstractHttpAction {

    /** Class logger. */
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(CreateSession.class);

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
    protected boolean doPreExecute(@Nonnull final ProfileRequestContext profileRequestContext) {
        return true;
    }

    /** {@inheritDoc} */
    @Override
    protected void doExecute(@Nonnull final ProfileRequestContext profileRequestContext) {
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
                    request.getContextPath() + request.getServletPath() + callbackServletPath);
        } catch (URISyntaxException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        message.getPayload().setCallbackUrl(redirectUri.toString());
        message.getPayload().setCallbackPostEndpoint(redirectUri.toString());
        // Clean <-

        try {
            String result = executeHttpRequest(message.toHttpRequest());
            log.info("{} Result is {}", getLogPrefix(), result);
        } catch (IOException | InvalidKeyException | NoSuchAlgorithmException | IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

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
