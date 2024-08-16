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

import javax.annotation.Nonnull;

import org.opensaml.profile.action.ActionSupport;
import org.opensaml.profile.context.ProfileRequestContext;
import org.slf4j.Logger;

import fi.csc.shibboleth.plugin.candourid.CandourEventIds;
import net.shibboleth.idp.authn.context.AuthenticationContext;
import net.shibboleth.shared.primitive.LoggerFactory;

/**
 * An {@link AbstractCandourAuthenticationAction action} that verifies the user
 * claim response is a response to invitation link in this session.
 *
 * @event {@link org.opensaml.profile.action.EventIds#PROCEED_EVENT_ID}
 * @event {@link fi.csc.shibboleth.plugin.candourid.CandourEventIds.CANDOUR_INVITE_LINK_VALIDATION_FAILURE}
 *        and based on injected map
 */
public class ValidateInvitationLink extends AbstractCandourAuthenticationAction {

    /** Class logger. */
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(ValidateInvitationLink.class);

    /** Name of the status parameter. */
    @Nonnull
    private String invitationLinkClaim = "invitationLink";

    /**
     * Set name of the invitation link claim.
     * 
     * @param claim Name of the invitation link claim
     */
    public void setInvitationLinkClaim(@Nonnull String claim) {
        checkSetterPreconditions();
        assert claim != null;
        invitationLinkClaim = claim;
    }

    /** {@inheritDoc} */
    @Override
    protected void doExecute(@Nonnull final ProfileRequestContext profileRequestContext,
            @Nonnull final AuthenticationContext authenticationContext) {

        log.debug("{} Validating invitation link", getLogPrefix());
        if (candourContext.getAuthenticationUri() == null || !candourContext.getAuthenticationUri()
                .equals(candourContext.getResultClaims().get(invitationLinkClaim))) {
            log.error("{} Mismatch between original invitation link {} and one received with response {}",
                    getLogPrefix(), candourContext.getAuthenticationUri(),
                    candourContext.getResultClaims().get(invitationLinkClaim));
            ActionSupport.buildEvent(profileRequestContext, CandourEventIds.CANDOUR_INVITE_LINK_VALIDATION_FAILURE);
            return;
        }
    }
}
