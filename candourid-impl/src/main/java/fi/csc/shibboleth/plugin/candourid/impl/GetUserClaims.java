package fi.csc.shibboleth.plugin.candourid.impl;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import javax.annotation.Nonnull;

import org.opensaml.profile.action.ActionSupport;
import org.opensaml.profile.context.ProfileRequestContext;
import org.slf4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import fi.csc.shibboleth.plugin.candourid.CandourEventIds;
import fi.csc.shibboleth.plugin.candourid.context.CandourContext;
import fi.csc.shibboleth.plugin.candourid.messaging.impl.CandourResponse;
import fi.csc.shibboleth.plugin.candourid.messaging.impl.CandourResultRequest;
import net.shibboleth.idp.authn.context.AuthenticationContext;
import net.shibboleth.shared.primitive.LoggerFactory;

/**
 * An {@link AbstractCandourHttpAuthenticationAction action} that forms and
 * sends a result request to Candour and stores the response to
 * {@link CandourContext}.
 *
 * @event {@link org.opensaml.profile.action.EventIds#PROCEED_EVENT_ID}
 * @event {@link org.opensaml.profile.action.EventIdsEventIds.INVALID_PROFILE_CTX}
 * @event {@link fi.csc.shibboleth.plugin.candourid.CandourEventIds.CANDOUR_API_COMM_FAILURE}
 * @event {@link fi.csc.shibboleth.plugin.candourid.CandourEventIds.CANDOUR_API_RESP_FAILURE}
 * @event {@link fi.csc.shibboleth.plugin.candourid.CandourEventIds.CANDOUR_API_RESP_MALFORMED}
 * @post {@link CandourContext#getResultClaims()} returns response claims.
 *       response.
 */
public class GetUserClaims extends AbstractCandourHttpAuthenticationAction {

    /** Class logger. */
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(GetUserClaims.class);

    /** {@inheritDoc} */
    @Override
    protected void doExecute(@Nonnull final ProfileRequestContext profileRequestContext,
            @Nonnull final AuthenticationContext authenticationContext) {
        CandourResultRequest message = new CandourResultRequest(getCandouridURI(), getClientPublicKey(),
                getClientHmacKey());
        message.setPayload(candourContext.getSessionId());
        CandourResponse response = null;
        try {
            response = executeHttpRequest(message.toHttpRequest());
        } catch (IOException | InvalidKeyException | NoSuchAlgorithmException | IllegalStateException
                | URISyntaxException e) {
            log.error("{} Exception occurred", getLogPrefix(), e);
            ActionSupport.buildEvent(profileRequestContext, CandourEventIds.CANDOUR_API_COMM_FAILURE);
            return;
        }
        if (!response.indicateSuccess()) {
            log.error("{} Candour invitation response indicates error. Status code {}, payload {}", getLogPrefix(),
                    response.getCode(), response.getPayload());
            ActionSupport.buildEvent(profileRequestContext, CandourEventIds.CANDOUR_API_RESP_FAILURE);
            return;
        }
        try {
            candourContext.setResultClaims(
                    new ObjectMapper().readValue(response.getPayload(), new TypeReference<Map<String, Object>>() {
                    }));
        } catch (JsonProcessingException e) {
            log.error("{} Candour response parsing failed.", getLogPrefix(), e);
            ActionSupport.buildEvent(profileRequestContext, CandourEventIds.CANDOUR_API_RESP_MALFORMED);
            return;
        }

    }

}
