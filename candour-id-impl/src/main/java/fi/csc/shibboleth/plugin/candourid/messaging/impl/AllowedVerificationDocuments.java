package fi.csc.shibboleth.plugin.candourid.messaging.impl;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Class implementing fields for allowed verification documents.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AllowedVerificationDocuments {

    /** Whether passport is allowed as verification document. */
    private Boolean passport;
    /** Whether national id card is allowed as verification document. */
    private Boolean idCard;

    /**
     * Set whether passport is allowed as verification document.
     * 
     * @param allowed whether passport is allowed as verification document.
     */
    public void setPassport(Boolean allowed) {
        passport = allowed;
    }

    /**
     * Whether passport is allowed as verification document.
     * 
     * @return Whether passport is allowed as verification document
     */
    public Boolean isPassport() {
        return passport;
    }

    /**
     * Set whether national id card is allowed as verification document.
     * 
     * @param allowed Whether national id card is allowed as verification document
     */
    public void setIdCard(Boolean allowed) {
        idCard = allowed;
    }

    /**
     * Whether national id card is allowed as verification document.
     * 
     * @return Whether national id card is allowed as verification document
     */
    public Boolean isIdCard() {
        return idCard;
    }
}
