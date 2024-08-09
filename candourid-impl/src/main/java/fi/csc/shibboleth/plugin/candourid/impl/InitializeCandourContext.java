
package fi.csc.shibboleth.plugin.candourid.impl;

import javax.annotation.Nonnull;

import org.opensaml.profile.context.ProfileRequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.csc.shibboleth.plugin.candourid.context.CandourContext;
import net.shibboleth.idp.authn.AbstractAuthenticationAction;
import net.shibboleth.idp.authn.context.AuthenticationContext;

/**
 * An {@link AbstractAuthenticationAction action} that instantiates {@link CandourContext} as a sub context of {@link AuthenticationContext}.
 *
 * @event {@link org.opensaml.profile.action.EventIds#PROCEED_EVENT_ID}
 * @post {@link CandourContext} is a sub context of {@link AuthenticationContext}
 */
public class InitializeCandourContext extends AbstractAuthenticationAction {

    /** Class logger. */
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(InitializeCandourContext.class);

    /** {@inheritDoc} */
    @Override
    protected void doExecute(@Nonnull final ProfileRequestContext profileRequestContext, @Nonnull final AuthenticationContext authenticationContext) {
        authenticationContext.addSubcontext(new CandourContext(), true);
    }

}
