package fi.csc.shibboleth.plugin.candourid.messaging.impl;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Class implementing fields for allowed verification methods.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AllowedVerificationMethods {

    /** Whether web browser based optical verification is allowed. */
    private Boolean idWeb;

    /**
     * Whether mobile app based optical verification + nfc chip verification is
     * allowed.
     */
    private Boolean rfidApp;

    /** Whether mobile app based optical verification is allowed. */
    private Boolean idApp;

    /**
     * Whether web browser based optical verification is allowed.
     * 
     * @return whether web browser based optical verification is allowed
     */
    public Boolean isIdWeb() {
        return idWeb;
    }

    /**
     * Set whether web browser based optical verification is allowed.
     * 
     * @param allowed Whether web browser based optical verification is allowed
     */
    public void setIdWeb(Boolean allowed) {
        idWeb = allowed;
    }

    /**
     * Whether mobile app based optical verification + nfc chip verification is
     * allowed.
     * 
     * @return Whether mobile app based optical verification + nfc chip verification
     *         is allowed
     */
    public Boolean isRfidApp() {
        return rfidApp;
    }

    /**
     * Set whether mobile app based optical verification + nfc chip verification is
     * allowed.
     * 
     * @param allowed whether mobile app based optical verification + nfc chip
     *                verification is allowed.
     */
    public void setRfidApp(Boolean allowed) {
        rfidApp = allowed;
    }

    /**
     * Whether mobile app based optical verification is allowed.
     * 
     * @return Whether mobile app based optical verification is allowed
     */
    public Boolean isIdApp() {
        return idApp;
    }

    /**
     * Set whether mobile app based optical verification is allowed.
     * 
     * @param idApp whether mobile app based optical verification is allowed.
     */
    public void setIdApp(Boolean allowed) {
        idApp = allowed;
    }
}