package fi.csc.shibboleth.plugin.candourid.impl;

import java.io.IOException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.protocol.HttpClientContext;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.opensaml.security.httpclient.HttpClientSecurityParameters;
import org.opensaml.security.httpclient.HttpClientSecuritySupport;
import org.slf4j.Logger;

import fi.csc.shibboleth.plugin.candourid.messaging.impl.CandourResponse;
import fi.csc.shibboleth.plugin.candourid.messaging.impl.CandourResponseHandler;

import net.shibboleth.shared.annotation.constraint.NonnullAfterInit;
import net.shibboleth.shared.annotation.constraint.ThreadSafeAfterInit;
import net.shibboleth.shared.component.ComponentInitializationException;
import net.shibboleth.shared.logic.Constraint;
import net.shibboleth.shared.primitive.LoggerFactory;

/**
 * An abstract class for Candour actions that make synchronous HTTP requests and
 * return types of {@link Response responses}.
 * 
 * @param <T> the response type of the object returned as a result of the
 *            request.
 */
@ThreadSafeAfterInit
public abstract class AbstractCandourHttpAuthenticationAction extends AbstractCandourAuthenticationAction {

    /** Class logger. */
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(AbstractCandourHttpAuthenticationAction.class);

    /** Http client for contacting the endpoint. */
    @NonnullAfterInit
    private HttpClient httpClient;

    /** HTTP client security parameters. */
    @Nullable
    private HttpClientSecurityParameters httpClientSecurityParameters;

    @Override
    protected void doInitialize() throws ComponentInitializationException {
        super.doInitialize();

        if (httpClient == null) {
            throw new ComponentInitializationException("httpClient cannot be null");
        }
    }

    /**
     * Set the {@link HttpClient} to use.
     * 
     * @param client client to use
     */
    public void setHttpClient(@Nonnull final HttpClient client) {
        checkSetterPreconditions();
        httpClient = Constraint.isNotNull(client, "HttpClient cannot be null");
    }

    /**
     * Set the optional client security parameters.
     * 
     * @param params the new client security parameters
     */
    public void setHttpClientSecurityParameters(@Nullable final HttpClientSecurityParameters params) {
        checkSetterPreconditions();
        httpClientSecurityParameters = params;
    }

    /**
     * Performs a call to an Http endpoint using the configured HttpClient,
     * HttpClientResponseHandler, and security parameters.
     * 
     * @param request                the prepared HTTP request
     * @param authenticatableContext an authenticatable context to set the
     *                               authenticated flag. Can be {@literal null} if
     *                               no flag is supplied.
     * 
     * @return the encoded Http response.
     * 
     * @throws IOException if there is an error producing a response
     */
    @Nullable
    protected CandourResponse executeHttpRequest(@Nonnull final ClassicHttpRequest request) throws IOException {

        Constraint.isNotNull(request, "Request can not be null");
        final HttpClientContext clientContext = HttpClientContext.create();
        assert clientContext != null;
        HttpClientSecuritySupport.marshalSecurityParameters(clientContext, httpClientSecurityParameters, true);
        HttpClientSecuritySupport.addDefaultTLSTrustEngineCriteria(clientContext, request);
        CandourResponse candourResponse = httpClient.execute(request, clientContext, new CandourResponseHandler());
        final String scheme = request.getScheme();
        assert scheme != null;
        HttpClientSecuritySupport.checkTLSCredentialEvaluated(clientContext, scheme);
        return candourResponse;

    }

}
