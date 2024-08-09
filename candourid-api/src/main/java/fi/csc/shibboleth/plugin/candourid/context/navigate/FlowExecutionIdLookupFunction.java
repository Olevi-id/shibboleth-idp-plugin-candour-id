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
 * {@link ContextDataLookupFunction} that returns the current flow id.
 */
public class FlowExecutionIdLookupFunction implements ContextDataLookupFunction<ProfileRequestContext, String> {

    /** Class logger. */
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(FlowExecutionIdLookupFunction.class);

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
        return flowExecutionContext.getKey().toString();

    }

}
