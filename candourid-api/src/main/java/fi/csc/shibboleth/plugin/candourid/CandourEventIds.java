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
     * Candour API response cannot be decoded..
     */
    @Nonnull
    @NotEmpty
    public static final String CANDOUR_API_RESP_MALFORMED = "CandourApiRespMalformed";

    /**
     * Constructor.
     */
    private CandourEventIds() {
        // no op
    }

}
