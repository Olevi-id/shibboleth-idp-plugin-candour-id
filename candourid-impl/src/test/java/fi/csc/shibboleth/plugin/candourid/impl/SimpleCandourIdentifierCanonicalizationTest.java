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

import java.util.Arrays;

import javax.security.auth.Subject;

import org.opensaml.profile.context.ProfileRequestContext;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import fi.csc.shibboleth.plugin.candourid.principal.CandourIdentifierPrincipal;
import net.shibboleth.idp.authn.AuthnEventIds;
import net.shibboleth.idp.authn.context.SubjectCanonicalizationContext;
import net.shibboleth.idp.profile.context.navigate.WebflowRequestContextProfileRequestContextLookup;
import net.shibboleth.idp.profile.testing.ActionTestingSupport;
import net.shibboleth.idp.profile.testing.RequestContextBuilder;
import net.shibboleth.shared.collection.Pair;
import net.shibboleth.shared.logic.PredicateSupport;

public class SimpleCandourIdentifierCanonicalizationTest {

    private SimpleCandourIdentifierCanonicalization action;

    private RequestContext src;

    private ProfileRequestContext prc;

    @BeforeMethod
    public void setup() throws Exception {
        src = new RequestContextBuilder().buildRequestContext();
        prc = new WebflowRequestContextProfileRequestContextLookup().apply(src);
        action = new SimpleCandourIdentifierCanonicalization();
        action.setTransforms(Arrays.asList(new Pair<>("^(.+)@osu\\.edu$", "$1")));
        action.setActivationCondition(PredicateSupport.alwaysTrue());
        action.initialize();
    }

    @Test
    public void testNoContext() {
        final Event event = action.execute(src);

        ActionTestingSupport.assertEvent(event, AuthnEventIds.INVALID_SUBJECT_C14N_CTX);
    }

    @Test
    public void testNoPrincipal() {
        final Subject subject = new Subject();
        prc.ensureSubcontext(SubjectCanonicalizationContext.class).setSubject(subject);

        final Event event = action.execute(src);

        ActionTestingSupport.assertEvent(event, AuthnEventIds.INVALID_SUBJECT);
        final var subjectC14nCtx = prc.getSubcontext(SubjectCanonicalizationContext.class);
        assert subjectC14nCtx != null;
        Assert.assertNotNull(subjectC14nCtx.getException());
    }

    @Test
    public void testMultiPrincipals() {
        final Subject subject = new Subject();
        subject.getPrincipals().add(new CandourIdentifierPrincipal("foo"));
        subject.getPrincipals().add(new CandourIdentifierPrincipal("bar"));
        prc.ensureSubcontext(SubjectCanonicalizationContext.class).setSubject(subject);

        final Event event = action.execute(src);

        ActionTestingSupport.assertEvent(event, AuthnEventIds.INVALID_SUBJECT);
        final var subjectC14nCtx = prc.getSubcontext(SubjectCanonicalizationContext.class);
        assert subjectC14nCtx != null;
        Assert.assertNotNull(subjectC14nCtx.getException());
    }

    @Test
    public void testSuccess() {
        final Subject subject = new Subject();
        subject.getPrincipals().add(new CandourIdentifierPrincipal("foo"));
        prc.ensureSubcontext(SubjectCanonicalizationContext.class).setSubject(subject);

        final Event event = action.execute(src);

        ActionTestingSupport.assertProceedEvent(event);
        final SubjectCanonicalizationContext sc = prc.getSubcontext(SubjectCanonicalizationContext.class);
        assert sc != null;
        Assert.assertEquals(sc.getPrincipalName(), "foo");
    }

    @Test
    public void testTransform() {
        final Subject subject = new Subject();
        subject.getPrincipals().add(new CandourIdentifierPrincipal("foo@osu.edu"));
        prc.ensureSubcontext(SubjectCanonicalizationContext.class).setSubject(subject);

        final Event event = action.execute(src);

        ActionTestingSupport.assertProceedEvent(event);
        final SubjectCanonicalizationContext sc = prc.getSubcontext(SubjectCanonicalizationContext.class);
        assert sc != null;
        Assert.assertEquals(sc.getPrincipalName(), "foo");
    }

}
