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

import net.shibboleth.idp.profile.context.SpringRequestContext;
import net.shibboleth.idp.profile.context.navigate.WebflowRequestContextProfileRequestContextLookup;
import net.shibboleth.idp.profile.testing.RequestContextBuilder;
import org.opensaml.profile.context.ProfileRequestContext;
import org.springframework.webflow.execution.RequestContext;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/** {@link FlowExecutionKeyLookupFunction} unit test. */
public class FlowExecutionKeyLookupFunctionTest {

    private RequestContext src;

    private ProfileRequestContext prc;

    private FlowExecutionKeyLookupFunction function;

    @BeforeMethod public void setUp() throws Exception {
        src = new RequestContextBuilder().buildRequestContext();
        prc = new WebflowRequestContextProfileRequestContextLookup().apply(src);
        function = new FlowExecutionKeyLookupFunction();
    }

    @Test public void testNullInput() {
        Assert.assertNull(function.apply(null));
    }

    @Test public void testNullSpringRequestContext() {
        Assert.assertNull(prc.getSubcontext(SpringRequestContext.class));
        Assert.assertNull(function.apply(prc));
    }

    @Test public void testNullWebFlowRequestContext() {
        prc.ensureSubcontext(SpringRequestContext.class);
        final SpringRequestContext context = prc.getSubcontext(SpringRequestContext.class);
        assert context != null;
        Assert.assertNull(context.getRequestContext());
        Assert.assertNull(function.apply(prc));
    }

    @Test public void testNullExecutionKey() {
        final SpringRequestContext context = prc.ensureSubcontext(SpringRequestContext.class);
        assert context != null;
        context.setRequestContext(src);
        final SpringRequestContext ctx2 = prc.getSubcontext(SpringRequestContext.class);
        assert ctx2  != null;
        Assert.assertNotNull(ctx2.getRequestContext());
        Assert.assertEquals(function.apply(prc), null);
    }
    //TODO: Test non null execution key.
}
