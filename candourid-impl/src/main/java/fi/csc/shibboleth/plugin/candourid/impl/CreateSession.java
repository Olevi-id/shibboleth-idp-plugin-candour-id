package fi.csc.shibboleth.plugin.candourid.impl;

import java.net.URI;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.opensaml.profile.context.ProfileRequestContext;
import org.slf4j.Logger;

import net.shibboleth.shared.primitive.LoggerFactory;

public class CreateSession extends AbstractHttpAction {

    /** Class logger. */
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(CreateSession.class);

    /**
     * The back-channel candour URI for which to send the back-channel new session
     * request.
     */
    @Nullable
    private URI candouridURI;

    /** {@inheritDoc} */
    @Override
    protected boolean doPreExecute(@Nonnull final ProfileRequestContext profileRequestContext) {
        return true;
    }

    /** {@inheritDoc} */
    @Override
    protected void doExecute(@Nonnull final ProfileRequestContext profileRequestContext) {
        // Fill the request per properties
        // Perform the request, attach response to context.
        // Direct user in flow same way as oidc rp via servlet to come back to same
        // location.

    }

}
