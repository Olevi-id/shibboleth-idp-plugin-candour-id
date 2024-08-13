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
