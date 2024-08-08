package fi.csc.shibboleth.plugin.candourid.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;
import javax.security.auth.Subject;

import org.opensaml.profile.action.ActionSupport;
import org.opensaml.profile.action.EventIds;
import org.opensaml.profile.context.ProfileRequestContext;
import org.slf4j.Logger;

import fi.csc.shibboleth.plugin.candourid.context.CandourContext;
import fi.csc.shibboleth.plugin.candourid.principal.CandourIdentifierPrincipal;
import net.shibboleth.idp.attribute.IdPAttribute;
import net.shibboleth.idp.attribute.StringAttributeValue;
import net.shibboleth.idp.authn.AbstractValidationAction;
import net.shibboleth.idp.authn.context.AuthenticationContext;
import net.shibboleth.idp.authn.principal.IdPAttributePrincipal;
import net.shibboleth.shared.annotation.constraint.NotEmpty;
import net.shibboleth.shared.collection.CollectionSupport;
import net.shibboleth.shared.primitive.LoggerFactory;
import net.shibboleth.shared.primitive.StringSupport;

/**
 * 
 *
 */
public class ValidateAuthentication extends AbstractValidationAction {

    /** Default prefix for metrics. */
    @Nonnull
    @NotEmpty
    private static final String DEFAULT_METRIC_NAME = "fi.csc.shibboleth.plugin.candourid";

    /** Class logger. */
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(ValidateAuthentication.class);

    /** Ordered list of candour claims to set as principal. */
    @Nonnull
    private List<String> claimSourceIds;

    private CandourContext candourContext;

    /** Constructor. */
    public ValidateAuthentication() {
        setMetricName(DEFAULT_METRIC_NAME);
        claimSourceIds = CollectionSupport.emptyList();
    }

    /**
     * Set the claims to read from in order of preference.
     * 
     * @param ids claims to read from
     */
    public void setClaimSourceIds(@Nonnull final List<String> ids) {
        checkSetterPreconditions();
        claimSourceIds = new ArrayList<>(StringSupport.normalizeStringCollection(ids));
    }

    @Override
    protected boolean doPreExecute(@Nonnull final ProfileRequestContext profileRequestContext,
            @Nonnull final AuthenticationContext authenticationContext) {

        if (!super.doPreExecute(profileRequestContext, authenticationContext)) {
            return false;
        }
        if (authenticationContext.getAttemptedFlow() == null) {
            log.debug("{} No attempted flow within authentication context", getLogPrefix());
            ActionSupport.buildEvent(profileRequestContext, EventIds.INVALID_PROFILE_CTX);
            return false;
        }
        candourContext = authenticationContext.getSubcontext(CandourContext.class);
        if (candourContext == null) {
            log.error("{} Unable to locate end-user claims context", getLogPrefix());
            ActionSupport.buildEvent(profileRequestContext, EventIds.INVALID_PROFILE_CTX);
            return false;
        }
        return true;
    }

    /** {@inheritDoc} */
    @Override
    protected void doExecute(@Nonnull final ProfileRequestContext profileRequestContext,
            @Nonnull final AuthenticationContext authenticationContext) {

        recordSuccess(profileRequestContext);
        log.debug("{} Validating Candour ID authentication", getLogPrefix());
        buildAuthenticationResult(profileRequestContext, authenticationContext);

    }

    @Override
    protected Subject populateSubject(@Nonnull final Subject subject) {

        // We set at most string claim as CandourIdentifierPrincipal. Not sure yet what
        // we actually want to do with it.
        for (String id : claimSourceIds) {
            if (candourContext.getResultClaims().containsKey(id)
                    && candourContext.getResultClaims().get(id) instanceof String) {
                subject.getPrincipals()
                        .add(new CandourIdentifierPrincipal((String) candourContext.getResultClaims().get(id)));
            }
            break;
        }
        for (String id : candourContext.getResultClaims().keySet()) {
            if (candourContext.getResultClaims().get(id) == null)
                continue;
            IdPAttribute attribute = new IdPAttribute(id);
            attribute.setValues(Collections
                    .singletonList(new StringAttributeValue(candourContext.getResultClaims().get(id).toString())));
            subject.getPrincipals().add(new IdPAttributePrincipal(attribute));
        }
        return subject;
    }

}
