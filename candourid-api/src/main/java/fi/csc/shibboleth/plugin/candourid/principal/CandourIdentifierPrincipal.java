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
package fi.csc.shibboleth.plugin.candourid.principal;

import javax.annotation.Nonnull;

import com.google.common.base.MoreObjects;

import net.shibboleth.idp.authn.principal.CloneablePrincipal;
import net.shibboleth.shared.annotation.ParameterName;
import net.shibboleth.shared.annotation.constraint.NotEmpty;
import net.shibboleth.shared.logic.Constraint;
import net.shibboleth.shared.primitive.StringSupport;


/** Principal based on candour response claim.*/
public class CandourIdentifierPrincipal implements CloneablePrincipal {
    
    /** The subject. */
    @Nonnull @NotEmpty private String subject;
    
    /**
     * Constructor.
     * 
     * @param sub the subject
     */
    public CandourIdentifierPrincipal(@Nonnull @NotEmpty @ParameterName(name="name") final String sub) {
        subject = Constraint.isNotNull(StringSupport.trimOrNull(sub), "Subject cannot be null or empty");
    }

    /** {@inheritDoc} */
    @Override
    @Nonnull @NotEmpty public String getName() {
        return subject;
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        return subject.hashCode();
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(final Object other) {
        if (other == null) {
            return false;
        }

        if (this == other) {
            return true;
        }

        if (other instanceof CandourIdentifierPrincipal principal) {
            return subject.equals(principal.getName());
        }

        return false;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("subject", subject).toString();
    }
    
    /** {@inheritDoc} */
    @Override
    public CandourIdentifierPrincipal clone() throws CloneNotSupportedException {
        final CandourIdentifierPrincipal copy = (CandourIdentifierPrincipal) super.clone();
        copy.subject = subject;
        return copy;
    }
    

}
