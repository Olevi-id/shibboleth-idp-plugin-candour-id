
package fi.csc.shibboleth.plugin.candourid.messaging.impl;

import java.io.IOException;
import java.net.URI;
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

    private String hmacKey = "xxxyyy-31e1-aaaaa-8cb9-bdddddfc4";
    private String publicKey = "7xxxyyyf0-2ssaa-4789-ee2-cddb05e7a";

    @BeforeMethod
    protected void setUp() throws Exception {
        message = new CandourInvitationRequest(new URI("https://example.com"), publicKey, hmacKey);
    }

    @Test
    public void testHeaders() throws ProtocolException, InvalidKeyException, NoSuchAlgorithmException,
            IllegalStateException, IOException {
        ClassicHttpRequest request = message.toHttpRequest();
        Assert.assertEquals(request.getHeader("Content-Type").getValue(), "application/json");
        Assert.assertEquals(request.getHeader("X-AUTH-CLIENT").getValue(), publicKey);
        Assert.assertNotNull(request.getHeader("X-HMAC-SIGNATURE").getValue());
        Assert.assertNotNull(EntityUtils.toString(request.getEntity()));
        System.out.println(
                String.format("curl -X POST https://rest-sandbox.candour.fi/v1 -H \"%s\" -H \"%s\" -H \"%s\" -d \"%s\"",
                        request.getHeader("Content-Type"), request.getHeader("X-AUTH-CLIENT"),
                        request.getHeader("X-HMAC-SIGNATURE"), EntityUtils.toString(request.getEntity())));

    }

    @Test
    public void testHmacDynamicPayload() throws ProtocolException, InvalidKeyException, NoSuchAlgorithmException,
            IllegalStateException, IOException {
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
