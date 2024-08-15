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
