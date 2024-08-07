package fi.csc.shibboleth.plugin.candourid.messaging.impl;

import java.io.IOException;

import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpException;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;

import net.shibboleth.shared.httpclient.HttpClientSupport;

/**
 * Instantiates and returns {@CandourResponse}.
 */
public class CandourResponseHandler implements HttpClientResponseHandler<CandourResponse> {

    @Override
    public CandourResponse handleResponse(ClassicHttpResponse response) throws HttpException, IOException {
        return new CandourResponse(response.getCode(),
                HttpClientSupport.toString(response.getEntity(), "UTF-8", 65536));
    }

}
