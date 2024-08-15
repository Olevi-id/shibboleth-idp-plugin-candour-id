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
package fi.csc.shibboleth.plugin.candourid.context.navigate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.opensaml.messaging.context.navigate.ContextDataLookupFunction;
import org.opensaml.profile.context.ProfileRequestContext;
import org.slf4j.Logger;
import org.springframework.webflow.execution.FlowExecutionContext;
import org.springframework.webflow.execution.RequestContext;

import net.shibboleth.idp.profile.context.SpringRequestContext;
import net.shibboleth.shared.primitive.LoggerFactory;

/**
 * {@link ContextDataLookupFunction} that returns the current flow execution
 * key.
 */
public class FlowExecutionKeyLookupFunction implements ContextDataLookupFunction<ProfileRequestContext, String> {

    /** Class logger. */
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(FlowExecutionKeyLookupFunction.class);

    /** {@inheritDoc} */
    @Nullable
    public String apply(@Nullable final ProfileRequestContext input) {
        if (input == null) {
            return null;
        }

        final SpringRequestContext springRequestContext = input.getSubcontext(SpringRequestContext.class);
        if (springRequestContext == null) {
            return null;
        }

        final RequestContext requestContext = springRequestContext.getRequestContext();
        if (requestContext == null) {
            return null;
        }

        final FlowExecutionContext flowExecutionContext = requestContext.getFlowExecutionContext();
        if (flowExecutionContext == null) {
            return null;
        }
        return flowExecutionContext.getKey() != null ? flowExecutionContext.getKey().toString() : null;

    }

}
