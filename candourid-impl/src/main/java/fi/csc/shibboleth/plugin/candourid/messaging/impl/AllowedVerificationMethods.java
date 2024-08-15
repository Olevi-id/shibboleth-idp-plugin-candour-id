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

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Class implementing fields for allowed verification methods.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AllowedVerificationMethods {

    /** Whether web browser based optical verification is allowed. */
    @Nullable
    private Boolean idWeb;

    /**
     * Whether mobile app based optical verification + nfc chip verification is
     * allowed.
     */
    @Nullable
    private Boolean rfidApp;

    /** Whether mobile app based optical verification is allowed. */
    @Nullable
    private Boolean idApp;

    /**
     * Whether web browser based optical verification is allowed.
     * 
     * @return whether web browser based optical verification is allowed
     */
    @Nullable
    public Boolean isIdWeb() {
        return idWeb;
    }

    /**
     * Set whether web browser based optical verification is allowed.
     * 
     * @param allowed Whether web browser based optical verification is allowed
     */
    public void setIdWeb(@Nullable Boolean allowed) {
        idWeb = allowed;
    }

    /**
     * Whether mobile app based optical verification + nfc chip verification is
     * allowed.
     * 
     * @return Whether mobile app based optical verification + nfc chip verification
     *         is allowed
     */
    @Nullable
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
    public void setRfidApp(@Nullable Boolean allowed) {
        rfidApp = allowed;
    }

    /**
     * Whether mobile app based optical verification is allowed.
     * 
     * @return Whether mobile app based optical verification is allowed
     */
    @Nullable
    public Boolean isIdApp() {
        return idApp;
    }

    /**
     * Set whether mobile app based optical verification is allowed.
     * 
     * @param idApp whether mobile app based optical verification is allowed.
     */
    public void setIdApp(@Nullable Boolean allowed) {
        idApp = allowed;
    }
}