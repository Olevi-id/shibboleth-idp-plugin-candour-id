package fi.csc.shibboleth.plugin.candourid.messaging.impl;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.hc.client5.http.utils.Hex;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.support.ClassicRequestBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * Class to create Candour invitation request.
 *
 */
public class CandourInvitationRequest {

    /** Client public key. */
    private final String publicKey;
    /** Client hmac key. */
    private final String hmacKey;
    /** API uri. */
    private final URI uri;
    /** Request payload. */
    private CandourInvitationRequestPayload payload = new CandourInvitationRequestPayload();

    /**
     * Constructor.
     * 
     * @param apiUri          API uri
     * @param clientPublicKey Client public key
     * @param clientHmacKey   Client hmac key
     */
    public CandourInvitationRequest(final URI apiUri, final String clientPublicKey, final String clientHmacKey) {
        publicKey = clientPublicKey;
        hmacKey = clientHmacKey;
        uri = apiUri;
    }

    /**
     * Get client public key.
     * 
     * @return Client public key
     */
    public String getPublicKey() {
        return publicKey;
    }

    /**
     * Get client hmac key.
     * 
     * @return Client hmac key
     */
    public String getHmacKey() {
        return hmacKey;
    }

    /**
     * Get API uri.
     * 
     * @return API uri
     */
    public URI getUri() {
        return uri;
    }

    /**
     * Get request payload.
     * 
     * @return request payload
     */
    public CandourInvitationRequestPayload getPayload() {
        return payload;
    }

    /**
     * Set request payload.
     * 
     * @param load request payload
     */
    public void setPayload(CandourInvitationRequestPayload load) {
        payload = load;
    }

    /**
     * CandourInvitationRequest to http request.
     * 
     * @return http request
     * @throws JsonProcessingException      something went wrong
     * @throws InvalidKeyException          something went wrong
     * @throws NoSuchAlgorithmException     something went wrong
     * @throws IllegalStateException        something went wrong
     * @throws UnsupportedEncodingException something went wrong
     */
    public ClassicHttpRequest toHttpRequest() throws JsonProcessingException, InvalidKeyException,
            NoSuchAlgorithmException, IllegalStateException, UnsupportedEncodingException {
        String freezedPayload = payload.serialize();
        final ClassicRequestBuilder rb = ClassicRequestBuilder.post().setUri(uri)
                .setHeader("Content-Type", "application/json").setHeader("X-AUTH-CLIENT", publicKey)
                .setHeader("X-HMAC-SIGNATURE", calculateHmac(freezedPayload)).setCharset(Charset.forName("UTF-8"))
                .setEntity(freezedPayload, ContentType.APPLICATION_JSON);
        return rb.build();

    }

    /**
     * Calculate hmac for the payload.
     * 
     * @param payload payload of the request
     * @return
     * @throws NoSuchAlgorithmException     something went wrong
     * @throws InvalidKeyException          something went wrong
     * @throws JsonProcessingException      something went wrong
     * @throws IllegalStateException        something went wrong
     * @throws UnsupportedEncodingException something went wrong
     */
    private String calculateHmac(String payload) throws NoSuchAlgorithmException, InvalidKeyException,
            JsonProcessingException, IllegalStateException, UnsupportedEncodingException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(hmacKey.getBytes(), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(secretKeySpec);
        return Hex.encodeHexString(mac.doFinal(payload.getBytes("UTF-8")));
    }

}
