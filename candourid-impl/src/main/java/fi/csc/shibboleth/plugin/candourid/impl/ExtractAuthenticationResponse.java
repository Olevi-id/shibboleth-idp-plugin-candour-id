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

import java.util.Map;

import javax.annotation.Nonnull;

import org.opensaml.profile.action.ActionSupport;
import org.opensaml.profile.context.ProfileRequestContext;
import org.slf4j.Logger;

import fi.csc.shibboleth.plugin.candourid.CandourEventIds;
import fi.csc.shibboleth.plugin.candourid.context.CandourContext;
import jakarta.servlet.http.HttpServletRequest;
import net.shibboleth.idp.authn.context.AuthenticationContext;
import net.shibboleth.shared.annotation.constraint.NonnullAfterInit;
import net.shibboleth.shared.component.ComponentInitializationException;
import net.shibboleth.shared.primitive.LoggerFactory;

/**
 * An {@link AbstractCandourAuthenticationAction action} that extracts Candour
 * redirect response parameters to {@link CandourContext}.
 *
 * @event {@link org.opensaml.profile.action.EventIds#PROCEED_EVENT_ID}
 * @event {@link fi.csc.shibboleth.plugin.candourid.CandourEventIds.CANDOUR_REDIRECT_RESP_MALFORMED}
 *        and based on injected map
 * @event {@link fi.csc.shibboleth.plugin.candourid.CandourEventIds.CANDOUR_REDIRECT_RESP_CANCELLED}
 * @event {@link fi.csc.shibboleth.plugin.candourid.CandourEventIds.CANDOUR_REDIRECT_RESP_CANCELLED_U_D}
 * @event {@link fi.csc.shibboleth.plugin.candourid.CandourEventIdsCANDOUR_REDIRECT_RESP_CANCELLED_U_ID}
 * @post {@link CandourContext#getSessionId()} returns session id.
 */
public class ExtractAuthenticationResponse extends AbstractCandourAuthenticationAction {

    /** Class logger. */
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(ExtractAuthenticationResponse.class);

    /** Name of the status parameter. */
    @Nonnull
    private String statusParameter = "status";

    /** Name of the session id parameter. */
    @Nonnull
    private String sessionIdParameter = "sessionId";

    /** Value of the success status parameter. */
    @Nonnull
    private String statusSuccessValue = "success";

    /** Mapping from status codes to events. */
    @NonnullAfterInit
    private Map<String, String> mappedStatuses;

    /**
     * Set mapping from status codes to events.
     * 
     * @param mapping
     */
    public void setMappedStatuses(@Nonnull Map<String, String> mapping) {
        checkSetterPreconditions();
        assert mapping != null;
        mappedStatuses = mapping;
    }

    /**
     * Set name of the status parameter.
     * 
     * @param parameter Name of the status parameter
     */
    public void setStatusParameter(@Nonnull String parameter) {
        checkSetterPreconditions();
        assert parameter != null;
        statusParameter = parameter;
    }

    /**
     * Set value of the success status parameter.
     * 
     * @param value Value of the success status parameter
     */
    public void setStatusSuccessValue(@Nonnull String value) {
        checkSetterPreconditions();
        assert value != null;
        statusSuccessValue = value;
    }

    /**
     * Set name of the session id parameter.
     * 
     * @param parameter Name of the session id parameter
     */
    public void setSessionIdParameter(String parameter) {
        checkSetterPreconditions();
        sessionIdParameter = parameter;
    }

    /** {@inheritDoc} */
    @Override
    protected void doInitialize() throws ComponentInitializationException {
        super.doInitialize();

        if (mappedStatuses == null) {
            throw new ComponentInitializationException("MappedStatuses cannot be null");
        }
    }

    /** {@inheritDoc} */
    @Override
    protected void doExecute(@Nonnull final ProfileRequestContext profileRequestContext,
            @Nonnull final AuthenticationContext authenticationContext) {

        HttpServletRequest request = getHttpServletRequestSupplier().get();
        String status = request.getParameter(statusParameter);
        if (status == null || !status.equals(statusSuccessValue)) {
            log.error("{} Response status is '{}', not indicating success ", getLogPrefix(), status);
            String eventId = status != null ? mappedStatuses.get(status)
                    : CandourEventIds.CANDOUR_REDIRECT_RESP_MALFORMED;
            ActionSupport.buildEvent(profileRequestContext,
                    eventId != null ? eventId : CandourEventIds.CANDOUR_REDIRECT_RESP_MALFORMED);
            return;
        }
        String sessionId = request.getParameter(sessionIdParameter);
        if (sessionId == null) {
            log.error("{} Response session id is null", getLogPrefix());
            ActionSupport.buildEvent(profileRequestContext, CandourEventIds.CANDOUR_REDIRECT_RESP_MALFORMED);
            return;
        }
        candourContext.setSessionId(sessionId);
        log.debug("{} Session id set as {}", getLogPrefix(), sessionId);
    }
}
