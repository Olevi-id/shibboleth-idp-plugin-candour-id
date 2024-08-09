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
