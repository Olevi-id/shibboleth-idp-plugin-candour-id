package fi.csc.shibboleth.plugin.candourid;

import java.io.IOException;

import net.shibboleth.idp.module.PropertyDrivenIdPModule;

/**
 * {@link IdPModule} implementation.
 */
public final class Module extends PropertyDrivenIdPModule {

    /**
     * Constructor.
     * 
     * @throws IOException     on error
     * @throws net.shibboleth.profile.module.ModuleException 
     */
    public Module() throws IOException, net.shibboleth.profile.module.ModuleException {
        super(Module.class);
    }

}