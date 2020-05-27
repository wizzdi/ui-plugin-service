package com.flexicore.ui.rest;

import com.flexicore.annotations.OperationsInside;
import com.flexicore.annotations.ProtectedREST;
import com.flexicore.annotations.plugins.PluginInfo;
import com.flexicore.data.jsoncontainers.PaginationResponse;
import com.flexicore.interfaces.RestServicePlugin;
import com.flexicore.security.SecurityContext;
import com.flexicore.ui.model.UIPlugin;
import com.flexicore.ui.request.UIPluginFilter;
import com.flexicore.ui.request.UIPluginCreate;
import com.flexicore.ui.request.UIPluginUpdate;
import com.flexicore.ui.service.UIPluginService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;

/**
 * Created by Asaf on 04/06/2017.
 */


@PluginInfo(version = 1)
@OperationsInside
@ProtectedREST
@Path("plugins/UIPlugins")
@OpenAPIDefinition(tags = {
        @Tag(name = "UIPlugins", description = "UIPlugins Services")
})
@Tag(name = "UIPlugins")

public class UIPluginRESTService implements RestServicePlugin {

    @Inject
    @PluginInfo(version = 1)
    private UIPluginService service;


    @POST
    @Produces("application/json")
    @Operation(summary = "getAllUIPlugins", description="returns all UIPlugins")
    @Path("getAllUIPlugins")
    public PaginationResponse<UIPlugin> getAllUIPlugins(
            @HeaderParam("authenticationKey") String authenticationKey,
            UIPluginFilter uiPluginFilter, @Context SecurityContext securityContext) {
        return service.getAllUIPlugins(uiPluginFilter, securityContext);

    }


    @PUT
    @Produces("application/json")
    @Operation(summary = "UIPluginUpdate", description="Updates UIPlugin")
    @Path("UIPluginUpdate")
    public UIPlugin UIPluginUpdate(
            @HeaderParam("authenticationKey") String authenticationKey,
            UIPluginUpdate uiPluginUpdate, @Context SecurityContext securityContext) {
        String id = uiPluginUpdate.getId();
        UIPlugin uiPlugin = id != null ? service.getByIdOrNull(id, UIPlugin.class, null, securityContext) : null;
        if (uiPlugin == null) {
            throw new BadRequestException("no UIPlugin with id  " + id);
        }
        uiPluginUpdate.setUIPlugin(uiPlugin);

        return service.UIPluginUpdate(uiPluginUpdate, securityContext);

    }


    @POST
    @Produces("application/json")
    @Operation(summary = "UIPluginCreate", description="Creates UIPlugin ")
    @Path("UIPluginCreate")
    public UIPlugin UIPluginCreate(
            @HeaderParam("authenticationKey") String authenticationKey,
            UIPluginCreate uiPluginCreate, @Context SecurityContext securityContext) {
        service.validate(uiPluginCreate, securityContext);
        return service.UIPluginCreate(uiPluginCreate, securityContext);

    }


}

