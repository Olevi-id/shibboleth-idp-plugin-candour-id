
package fi.csc.shibboleth.plugin.candourid.impl;

import java.util.Set;
import java.util.function.Predicate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.security.auth.Subject;

import org.opensaml.profile.action.ActionSupport;
import org.opensaml.profile.context.ProfileRequestContext;
import org.slf4j.Logger;

import fi.csc.shibboleth.plugin.candourid.principal.CandourIdentifierPrincipal;
import net.shibboleth.idp.authn.AbstractSubjectCanonicalizationAction;
import net.shibboleth.idp.authn.AuthnEventIds;
import net.shibboleth.idp.authn.SubjectCanonicalizationException;
import net.shibboleth.idp.authn.context.SubjectCanonicalizationContext;
import net.shibboleth.shared.annotation.ParameterName;
import net.shibboleth.shared.primitive.LoggerFactory;

public class SimpleCandourIdentifierCanonicalization extends AbstractSubjectCanonicalizationAction {

    /** Class logger. */
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(SimpleCandourIdentifierCanonicalization.class);

    /** Supplies logic for pre-execute test. */
    @Nonnull
    private final ActivationCondition embeddedPredicate;

    /** The custom Principal to operate on. */
    @Nullable
    private CandourIdentifierPrincipal identifierPrincipal;

    /** Constructor. */
    public SimpleCandourIdentifierCanonicalization() {
        embeddedPredicate = new ActivationCondition(false);
    }

    /** {@inheritDoc} */
    @Override
    protected boolean doPreExecute(@Nonnull final ProfileRequestContext profileRequestContext,
            @Nonnull final SubjectCanonicalizationContext c14nContext) {

        if (embeddedPredicate.apply(profileRequestContext, c14nContext, true)) {
            final Subject c14CtxSubject = c14nContext.getSubject();
            assert c14CtxSubject != null;
            identifierPrincipal = c14CtxSubject.getPrincipals(CandourIdentifierPrincipal.class).iterator().next();
            return super.doPreExecute(profileRequestContext, c14nContext);
        }

        return false;
    }

    /** {@inheritDoc} */
    @Override
    protected void doExecute(@Nonnull final ProfileRequestContext profileRequestContext,
            @Nonnull final SubjectCanonicalizationContext c14nContext) {
        assert identifierPrincipal != null;
        c14nContext.setPrincipalName(applyTransforms(identifierPrincipal.getName()));
    }

    /** A predicate that determines if this action can run or not. */
    public static class ActivationCondition implements Predicate<ProfileRequestContext> {

        /** Class logger. */
        @Nonnull
        private final Logger log = LoggerFactory.getLogger(ActivationCondition.class);

        /** Disable this C14N no matter how appropriate the context? */
        private final boolean disabled;

        /**
         * 
         * Constructor.
         *
         * @param disable this C14N no matter how appropriate the context
         */
        public ActivationCondition(@ParameterName(name = "disabled") final boolean disable) {
            disabled = disable;
        }

        /** {@inheritDoc} */
        @Override
        public boolean test(@Nullable final ProfileRequestContext input) {
            if (disabled) {
                log.trace("SimpleCandourIdentifierCanonicalization has been disabled by configuration");
                return false;
            }
            if (input != null) {
                final SubjectCanonicalizationContext c14nContext = input
                        .getSubcontext(SubjectCanonicalizationContext.class);
                if (c14nContext != null) {
                    final boolean shouldRun = apply(input, c14nContext, false);
                    log.trace("SimpleCandourIdentifierCanonicalization is {} for the given context",
                            shouldRun ? "active" : "not active");
                    return shouldRun;
                }
            }
            log.trace("SimpleCandourIdentifierCanonicalization is not active for the given context");
            return false;
        }

        /**
         * Helper method that runs either as part of the {@link Predicate} or directly
         * from the
         * {@link SimpleCandourIdentifierCanonicalization#doPreExecute(ProfileRequestContext, SubjectCanonicalizationContext)}
         * method above.
         * 
         * @param profileRequestContext the current profile request context
         * @param c14nContext           the current c14n context
         * @param duringAction          true iff the method is run from the action above
         * @return true iff the action can operate successfully on the candidate
         *         contexts
         */
        public boolean apply(@Nonnull final ProfileRequestContext profileRequestContext,
                @Nonnull final SubjectCanonicalizationContext c14nContext, final boolean duringAction) {

            final Set<CandourIdentifierPrincipal> subjects;
            final Subject c14CtxSubject = c14nContext.getSubject();
            if (c14CtxSubject != null) {
                subjects = c14CtxSubject.getPrincipals(CandourIdentifierPrincipal.class);
            } else {
                subjects = null;
            }

            if (subjects == null || subjects.isEmpty()) {
                c14nContext
                        .setException(new SubjectCanonicalizationException("No CandourIdentifierPrincipal were found"));
                if (duringAction) {
                    ActionSupport.buildEvent(profileRequestContext, AuthnEventIds.INVALID_SUBJECT);
                }
                return false;
            } else if (subjects.size() > 1) {
                c14nContext.setException(
                        new SubjectCanonicalizationException("Multiple CandourIdentifierPrincipal were found"));
                if (duringAction) {
                    ActionSupport.buildEvent(profileRequestContext, AuthnEventIds.INVALID_SUBJECT);
                }
                return false;
            }

            return true;
        }

    }

}