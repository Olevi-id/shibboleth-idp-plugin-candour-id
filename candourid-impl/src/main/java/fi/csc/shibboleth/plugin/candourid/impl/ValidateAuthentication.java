/*
 * Copyright (c) 2024 CSC- IT Center for Science, www.csc.fi
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fi.csc.shibboleth.plugin.candourid.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
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
import net.shibboleth.shared.annotation.constraint.NonnullAfterInit;
import net.shibboleth.shared.annotation.constraint.NotEmpty;
import net.shibboleth.shared.collection.CollectionSupport;
import net.shibboleth.shared.component.ComponentInitializationException;
import net.shibboleth.shared.primitive.LoggerFactory;
import net.shibboleth.shared.primitive.StringSupport;

/**
 * An action that builds an {@link AuthenticationResult} based on an Candour API
 * result response.
 * 
 * <p>
 * A {@link CandourContext} is used as the basis of the result, which stores
 * Candour API result response claims.
 * 
 * <p>
 * Actual validation is all upstream of this action, but the use of the
 * ValidationAction subclass is a convenience for auditing and handling the
 * result.
 * </p>
 * 
 * @event {@link EventIds#PROCEED_EVENT_ID}
 * @event {@link EventIds#INVALID_PROFILE_CTX}
 * @pre
 * 
 *      <pre>
 *      ProfileRequestContext.getSubcontext(AuthenticationContext.class).getAttemptedFlow() != null
 *      </pre>
 * 
 * @post {@link net.shibboleth.idp.authn.AuthenticationResult} is saved to the
 *       {@link AuthenticationContext}.
 */
public class ValidateAuthentication extends AbstractValidationAction {

    /** Default prefix for metrics. */
    @Nonnull
    @NotEmpty
    private static final String DEFAULT_METRIC_NAME = "fi.csc.shibboleth.plugin.candourid";

    /** Class logger. */
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(ValidateAuthentication.class);

    /**
     * Ordered list of candour claims of which the first one found is set as Candour
     * identifier principal.
     */
    @NonnullAfterInit
    private List<String> claimSourceIds;

    /** Candour context. */
    @Nullable
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
    public void setClaimSourceIds(@Nonnull @NotEmpty final List<String> ids) {
        checkSetterPreconditions();
        assert ids != null;
        assert !ids.isEmpty();
        claimSourceIds = new ArrayList<>(StringSupport.normalizeStringCollection(ids));
    }

    @Override
    protected void doInitialize() throws ComponentInitializationException {
        super.doInitialize();

        if (claimSourceIds == null || claimSourceIds.isEmpty()) {
            throw new ComponentInitializationException("ClaimSourceIds cannot be null or empty");
        }
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
