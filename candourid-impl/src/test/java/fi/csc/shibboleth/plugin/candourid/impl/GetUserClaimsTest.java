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

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpUriRequest;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.http.protocol.HttpContext;
import org.mockito.Mockito;
import org.opensaml.profile.context.ProfileRequestContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import fi.csc.shibboleth.plugin.candourid.CandourEventIds;
import fi.csc.shibboleth.plugin.candourid.context.CandourContext;
import fi.csc.shibboleth.plugin.candourid.messaging.impl.CandourResponse;
import net.shibboleth.idp.authn.context.AuthenticationContext;
import net.shibboleth.idp.profile.context.navigate.WebflowRequestContextProfileRequestContextLookup;
import net.shibboleth.idp.profile.testing.RequestContextBuilder;
import net.shibboleth.shared.servlet.impl.HttpServletRequestResponseContext;
import net.shibboleth.shared.servlet.impl.ThreadLocalHttpServletRequestSupplier;

public class GetUserClaimsTest {

    private GetUserClaims action;

    private RequestContext src;

    private ProfileRequestContext prc;

    private CandourContext ctx;

    @BeforeMethod
    public void setup() throws Exception {
        src = new RequestContextBuilder().buildRequestContext();
        prc = new WebflowRequestContextProfileRequestContextLookup().apply(src);
        ctx = (CandourContext) prc.addSubcontext(new AuthenticationContext()).addSubcontext(new CandourContext());
        ctx.setSessionId("sessionIdValue");
        action = new GetUserClaims();
        action.setCandouridURI("https://example.com/api");
        action.setClientPublicKey("publicKey");
        action.setClientHmacKey("hmacKey");
        action.setHttpServletRequestSupplier(new ThreadLocalHttpServletRequestSupplier());
        HttpServletRequestResponseContext.loadCurrent(new MockHttpServletRequest(), new MockHttpServletResponse());

    }

    @SuppressWarnings("unchecked")
    @Test
    public void testExpectedSuccess() throws Exception {
        final HttpClient httpClient = Mockito.mock(HttpClient.class);
        CandourResponse candourResponse = new CandourResponse(200,
                "{\"claim1\":\"value1\", \"claim2\":\"value2\",\"claim3\":\"value3\",\"claim4\":\"value4\"}");
        Mockito.when(httpClient.execute((HttpUriRequest) Mockito.any(), (HttpContext) Mockito.any(),
                (HttpClientResponseHandler<CandourResponse>) Mockito.any())).thenReturn(candourResponse);
        action.setHttpClient(httpClient);
        action.initialize();
        Event event = action.execute(src);
        Assert.assertNull(event);
        Assert.assertEquals(ctx.getResultClaims().get("claim1"), "value1");
        Assert.assertEquals(ctx.getResultClaims().get("claim2"), "value2");
        Assert.assertEquals(ctx.getResultClaims().get("claim3"), "value3");
        Assert.assertEquals(ctx.getResultClaims().get("claim4"), "value4");
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testFailure() throws Exception {

        final HttpClient httpClient = Mockito.mock(HttpClient.class);
        // http code not indicating success
        CandourResponse candourResponse = new CandourResponse(400, "Something bad happened");
        Mockito.when(httpClient.execute((HttpUriRequest) Mockito.any(), (HttpContext) Mockito.any(),
                (HttpClientResponseHandler<CandourResponse>) Mockito.any())).thenReturn(candourResponse);
        action.setHttpClient(httpClient);
        action.initialize();
        Event event = action.execute(src);
        Assert.assertEquals(event.getId(), CandourEventIds.CANDOUR_API_RESP_FAILURE);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testMalformed() throws Exception {

        final HttpClient httpClient = Mockito.mock(HttpClient.class);
        // Success case is expected to have json sting in payload
        CandourResponse candourResponse = new CandourResponse(200, "Something bad happened");
        Mockito.when(httpClient.execute((HttpUriRequest) Mockito.any(), (HttpContext) Mockito.any(),
                (HttpClientResponseHandler<CandourResponse>) Mockito.any())).thenReturn(candourResponse);
        action.setHttpClient(httpClient);
        action.initialize();
        Event event = action.execute(src);
        Assert.assertEquals(event.getId(), CandourEventIds.CANDOUR_API_RESP_MALFORMED);
    }
}
