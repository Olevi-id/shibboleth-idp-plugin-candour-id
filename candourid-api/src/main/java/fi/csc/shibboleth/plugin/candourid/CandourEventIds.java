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
package fi.csc.shibboleth.plugin.candourid;

import javax.annotation.Nonnull;

import net.shibboleth.shared.annotation.constraint.NotEmpty;

public final class CandourEventIds {

    /**
     * Communication failed with Candour API.
     */
    @Nonnull
    @NotEmpty
    public static final String CANDOUR_API_COMM_FAILURE = "CandourApiCommFailure";

    /**
     * Candour API response indicates failure.
     */
    @Nonnull
    @NotEmpty
    public static final String CANDOUR_API_RESP_FAILURE = "CandourApiRespFailure";

    /**
     * Candour API response cannot be decoded.
     */
    @Nonnull
    @NotEmpty
    public static final String CANDOUR_API_RESP_MALFORMED = "CandourApiRespMalformed";
    
    /**
     * Candour redirect response cannot be decoded.
     */
    @Nonnull
    @NotEmpty
    public static final String CANDOUR_REDIRECT_RESP_MALFORMED = "CandourRedirectRespMalformed";
    
    /**
     * Candour redirect response indicated user cancelled.
     */
    @Nonnull
    @NotEmpty
    public static final String CANDOUR_REDIRECT_RESP_CANCELLED = "CandourRedirectRespCancelled";
    
    /**
     * Candour redirect response indicated user cancelled due to unsupported device.
     */
    @Nonnull
    @NotEmpty
    public static final String CANDOUR_REDIRECT_RESP_CANCELLED_U_D = "CandourRedirectRespCancelledUD";
    
    /**
     * Candour redirect response indicated user cancelled due to unsupported id.
     */
    @Nonnull
    @NotEmpty
    public static final String CANDOUR_REDIRECT_RESP_CANCELLED_U_ID = "CandourRedirectRespCancelledUId";

    /**
     * Constructor.
     */
    private CandourEventIds() {
        // no op
    }

}
