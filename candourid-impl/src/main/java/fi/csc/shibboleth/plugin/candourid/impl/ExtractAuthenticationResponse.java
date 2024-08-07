package fi.csc.shibboleth.plugin.candourid.impl;

import javax.annotation.Nonnull;

import org.opensaml.profile.action.ActionSupport;
import org.opensaml.profile.action.EventIds;
import org.opensaml.profile.context.ProfileRequestContext;
import org.slf4j.Logger;

import jakarta.servlet.http.HttpServletRequest;
import net.shibboleth.idp.authn.context.AuthenticationContext;
import net.shibboleth.shared.primitive.LoggerFactory;

/**
 * TODO: Test class is initialized properly before moving to doExecute. General
 * cleanup and unit tests needed.
 * 
 * TODO: Set response to TBD context for further processing.
 */
public class ExtractAuthenticationResponse extends AbstractCandourAuthenticationAction {

    /** Class logger. */
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(ExtractAuthenticationResponse.class);

    /** {@inheritDoc} */
    @Override
    protected void doExecute(@Nonnull final ProfileRequestContext profileRequestContext,
            @Nonnull final AuthenticationContext authenticationContext) {

        HttpServletRequest request = getHttpServletRequestSupplier().get();
        String status = request.getParameter("status");
        if (status == null || !"success".equals(status.toLowerCase())) {
            log.error("{} Response status is '{}', not indicating success ", getLogPrefix());
            // TODO: use candour specific error event
            ActionSupport.buildEvent(profileRequestContext, EventIds.IO_ERROR);
            return;
        }
        String sessionId = request.getParameter("sessionId");
        if (sessionId == null) {
            log.error("{} Response session id is null", getLogPrefix());
            // TODO: use candour specific error event
            ActionSupport.buildEvent(profileRequestContext, EventIds.IO_ERROR);
            return;
        }
        candourContext.setSessionId(sessionId);
        log.debug("{} Session id set as {}", getLogPrefix(), sessionId);
    }
}
