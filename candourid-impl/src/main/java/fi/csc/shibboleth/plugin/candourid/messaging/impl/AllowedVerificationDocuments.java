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
 * Class implementing fields for allowed verification documents.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AllowedVerificationDocuments {

    /** Whether passport is allowed as verification document. */
    @Nullable
    private Boolean passport;
    /** Whether national id card is allowed as verification document. */
    @Nullable
    private Boolean idCard;

    /**
     * Set whether passport is allowed as verification document.
     * 
     * @param allowed whether passport is allowed as verification document.
     */
    public void setPassport(@Nullable Boolean allowed) {
        passport = allowed;
    }

    /**
     * Whether passport is allowed as verification document.
     * 
     * @return Whether passport is allowed as verification document
     */
    @Nullable
    public Boolean isPassport() {
        return passport;
    }

    /**
     * Set whether national id card is allowed as verification document.
     * 
     * @param allowed Whether national id card is allowed as verification document
     */
    public void setIdCard(@Nullable Boolean allowed) {
        idCard = allowed;
    }

    /**
     * Whether national id card is allowed as verification document.
     * 
     * @return Whether national id card is allowed as verification document
     */
    @Nullable
    public Boolean isIdCard() {
        return idCard;
    }
}
