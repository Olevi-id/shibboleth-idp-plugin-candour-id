package fi.csc.shibboleth.plugin.candourid.context;

import org.testng.Assert;
import org.testng.annotations.Test;

/** Tests for {@link CandourContext}. */
public class CandourContextTest {

    private CandourContext ctx = new CandourContext();

    @Test
    public void testInitialState() {
        Assert.assertNull(ctx.getAuthenticationUri());
        Assert.assertNull(ctx.getCallbackUri());
        Assert.assertNull(ctx.getInvitationResponse());
        Assert.assertNull(ctx.getSessionId());
        Assert.assertNotNull(ctx.getResultClaims());
    }

    @Test
    public void testSetters() {
        ctx.setAuthenticationUri("auri");
        ctx.setCallbackUri("curi");
        ctx.setInvitationResponse("resp");
        ctx.setSessionId("id");
        Assert.assertEquals("auri", ctx.getAuthenticationUri());
        Assert.assertEquals("curi", ctx.getCallbackUri());
        Assert.assertEquals("resp", ctx.getInvitationResponse());
        Assert.assertEquals("id", ctx.getSessionId());
    }

}
