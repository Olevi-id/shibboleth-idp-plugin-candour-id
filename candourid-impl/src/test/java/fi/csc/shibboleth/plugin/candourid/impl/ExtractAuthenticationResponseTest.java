package fi.csc.shibboleth.plugin.candourid.impl;

import java.util.HashMap;
import java.util.Map;

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
import net.shibboleth.idp.authn.context.AuthenticationContext;
import net.shibboleth.idp.profile.context.navigate.WebflowRequestContextProfileRequestContextLookup;
import net.shibboleth.idp.profile.testing.RequestContextBuilder;
import net.shibboleth.shared.servlet.impl.HttpServletRequestResponseContext;
import net.shibboleth.shared.servlet.impl.ThreadLocalHttpServletRequestSupplier;

public class ExtractAuthenticationResponseTest {

    private ExtractAuthenticationResponse action;

    private RequestContext src;

    private ProfileRequestContext prc;

    private CandourContext ctx;

    private MockHttpServletRequest mockHttpServletRequest;

    @BeforeMethod
    public void setup() throws Exception {
        src = new RequestContextBuilder().buildRequestContext();
        prc = new WebflowRequestContextProfileRequestContextLookup().apply(src);
        ctx = (CandourContext) prc.addSubcontext(new AuthenticationContext()).addSubcontext(new CandourContext());
        action = new ExtractAuthenticationResponse();
        Map<String, String> mappedStatuses = new HashMap<String, String>();
        mappedStatuses.put("cancelled", CandourEventIds.CANDOUR_REDIRECT_RESP_CANCELLED);
        mappedStatuses.put("cancelledUnsupportedDevice", CandourEventIds.CANDOUR_REDIRECT_RESP_CANCELLED_U_D);
        mappedStatuses.put("cancelledUnsupportedId", CandourEventIds.CANDOUR_REDIRECT_RESP_CANCELLED_U_ID);
        action.setMappedStatuses(mappedStatuses);
        action.setHttpServletRequestSupplier(new ThreadLocalHttpServletRequestSupplier());
        mockHttpServletRequest = new MockHttpServletRequest();
        mockHttpServletRequest.setParameter("status", "success");
        mockHttpServletRequest.setParameter("sessionId", "sessionIdValue");
        HttpServletRequestResponseContext.loadCurrent(mockHttpServletRequest, new MockHttpServletResponse());

    }

    @Test
    public void testExpectedSuccess() throws Exception {
        action.initialize();
        Event event = action.execute(src);
        Assert.assertNull(event);
        Assert.assertEquals(ctx.getSessionId(), "sessionIdValue");

    }

    @Test
    public void testCancel() throws Exception {
        mockHttpServletRequest.setParameter("status", "cancelled");
        action.initialize();
        Event event = action.execute(src);
        Assert.assertEquals(event.getId(), CandourEventIds.CANDOUR_REDIRECT_RESP_CANCELLED);
        Assert.assertNull(ctx.getSessionId());
    }

    @Test
    public void testCancelUD() throws Exception {
        mockHttpServletRequest.setParameter("status", "cancelledUnsupportedDevice");
        action.initialize();
        Event event = action.execute(src);
        Assert.assertEquals(event.getId(), CandourEventIds.CANDOUR_REDIRECT_RESP_CANCELLED_U_D);
        Assert.assertNull(ctx.getSessionId());
    }

    @Test
    public void testCancelUID() throws Exception {
        mockHttpServletRequest.setParameter("status", "cancelledUnsupportedId");
        action.initialize();
        Event event = action.execute(src);
        Assert.assertEquals(event.getId(), CandourEventIds.CANDOUR_REDIRECT_RESP_CANCELLED_U_ID);
        Assert.assertNull(ctx.getSessionId());
    }

    @Test
    public void testUnknown() throws Exception {
        mockHttpServletRequest.setParameter("status", "unkown");
        action.initialize();
        Event event = action.execute(src);
        Assert.assertEquals(event.getId(), CandourEventIds.CANDOUR_REDIRECT_RESP_MALFORMED);
        Assert.assertNull(ctx.getSessionId());
    }

    @Test
    public void testNoSession() throws Exception {
        mockHttpServletRequest.removeParameter("sessionId");
        action.initialize();
        Event event = action.execute(src);
        Assert.assertEquals(event.getId(), CandourEventIds.CANDOUR_REDIRECT_RESP_MALFORMED);
        Assert.assertNull(ctx.getSessionId());
    }

}
