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
