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

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.hc.client5.http.utils.Hex;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.support.ClassicRequestBuilder;
import org.springframework.http.HttpMethod;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * Abstract class for classes making http requests to Candour.
 *
 */
abstract class AbstractCandourRequest<T> {

    /** Client public key. */
    private final String publicKey;

    /** Client hmac key. */
    private final String hmacKey;

    /** API uri. */
    private final URI uri;

    /** Request payload. */
    private T payload;

    /** Http method to use. */
    private HttpMethod httpMethod;

    /**
     * Constructor.
     * 
     * @param apiUri          API uri
     * @param clientPublicKey Client public key
     * @param clientHmacKey   Client hmac key
     * @param method          Http method to use
     */
    public AbstractCandourRequest(final URI apiUri, final String clientPublicKey, final String clientHmacKey,
            HttpMethod method) {
        publicKey = clientPublicKey;
        hmacKey = clientHmacKey;
        uri = apiUri;
        httpMethod = method;
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
    public T getPayload() {
        return payload;
    }

    /**
     * Set request payload.
     * 
     * @param load request payload
     */
    public void setPayload(T load) {
        payload = load;
    }

    /**
     * CandourRequest to http request.
     * 
     * @return http request
     * @throws JsonProcessingException      something went wrong
     * @throws InvalidKeyException          something went wrong
     * @throws NoSuchAlgorithmException     something went wrong
     * @throws IllegalStateException        something went wrong
     * @throws UnsupportedEncodingException something went wrong
     * @throws URISyntaxException
     */
    public ClassicHttpRequest toHttpRequest() throws JsonProcessingException, InvalidKeyException,
            NoSuchAlgorithmException, IllegalStateException, UnsupportedEncodingException, URISyntaxException {
        String freezedPayload = payload.toString();
        ClassicRequestBuilder rb = null;
        if (httpMethod == HttpMethod.GET) {
            rb = ClassicRequestBuilder.get().setUri(new URI(uri.toString() + "/" + freezedPayload))
                    .setHeader("Content-Type", "application/json").setHeader("X-AUTH-CLIENT", publicKey)
                    .setHeader("X-HMAC-SIGNATURE", calculateHmac(freezedPayload)).setCharset(Charset.forName("UTF-8"));
        } else {
            rb = ClassicRequestBuilder.post().setUri(uri).setHeader("Content-Type", "application/json")
                    .setHeader("X-AUTH-CLIENT", publicKey).setHeader("X-HMAC-SIGNATURE", calculateHmac(freezedPayload))
                    .setCharset(Charset.forName("UTF-8")).setEntity(freezedPayload, ContentType.APPLICATION_JSON);
        }
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
