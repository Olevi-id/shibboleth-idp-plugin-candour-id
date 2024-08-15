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
package fi.csc.shibboleth.plugin.candourid.messaging.impl;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.hc.client5.http.utils.Hex;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.ProtocolException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CandourInvitationRequestTest {

    private CandourInvitationRequest message;

    private String hmacKey = "d818a281-3226-4615-8455-7a004894ca97";
    private String publicKey = "4c75a6b6-88e5-4365-972b-f88edb7f14e8";

    @BeforeMethod
    protected void setUp() throws Exception {
        message = new CandourInvitationRequest(new URI("https://example.com"), publicKey, hmacKey);
        message.setPayload(new CandourInvitationRequestPayload());
        message.getPayload().setCallbackUrl("https://example.com/callback/init");
        message.getPayload().setCallbackPostEndpoint("https://example.com/callback/done");
        message.getPayload().getAllowedVerificationMethods().setIdWeb(true);
        message.getPayload().getAllowedVerificationDocuments().setIdCard(true);
        message.getPayload().getResultProperties().setName(true);

    }

    @Test
    public void testHeaders() throws ProtocolException, InvalidKeyException, NoSuchAlgorithmException,
            IllegalStateException, IOException, URISyntaxException {
        ClassicHttpRequest request = message.toHttpRequest();
        Assert.assertEquals(request.getHeader("Content-Type").getValue(), "application/json");
        Assert.assertEquals(request.getHeader("X-AUTH-CLIENT").getValue(), publicKey);
        Assert.assertNotNull(request.getHeader("X-HMAC-SIGNATURE").getValue());
        Assert.assertNotNull(EntityUtils.toString(request.getEntity()));
        System.out.println(
                String.format("curl -X POST https://rest-sandbox.candour.fi/v1 -H \"%s\" -H \"%s\" -H \"%s\" -d \"%s\"",
                        request.getHeader("Content-Type"), request.getHeader("X-AUTH-CLIENT"),
                        request.getHeader("X-HMAC-SIGNATURE"),
                        EntityUtils.toString(request.getEntity()).replace("\"", "\\\"")));
        System.out.println();
        System.out.println(EntityUtils.toString(request.getEntity()));

    }

    @Test
    public void testHmacDynamicPayload() throws ProtocolException, InvalidKeyException, NoSuchAlgorithmException,
            IllegalStateException, IOException, URISyntaxException {
        ClassicHttpRequest request = message.toHttpRequest();
        String requestHmac = request.getHeader("X-HMAC-SIGNATURE").getValue();
        String requestPayload = EntityUtils.toString(request.getEntity());
        SecretKeySpec secretKeySpec = new SecretKeySpec(hmacKey.getBytes(), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(secretKeySpec);
        String hmac = Hex.encodeHexString(mac.doFinal(requestPayload.getBytes("UTF-8")));
        Assert.assertEquals(requestHmac, hmac);
    }

}
