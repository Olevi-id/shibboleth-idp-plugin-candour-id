package fi.csc.shibboleth.plugin.candourid.impl;

import java.io.IOException;

import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpException;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;

import net.shibboleth.shared.httpclient.HttpClientSupport;

/**
 * First draft to make the implementation to compile and run.
 * 
 */
public class CandourResponseHandler implements HttpClientResponseHandler<String> {

    @Override
    public String handleResponse(ClassicHttpResponse response) throws HttpException, IOException {
        int status = response.getCode();
        if (status == 200) {
            return HttpClientSupport.toString(response.getEntity(), "UTF-8", 65536);
        }
        return null;

    }

}
