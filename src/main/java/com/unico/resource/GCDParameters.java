package com.unico.resource;

import com.unico.json.PairParameters;
import com.unico.service.GcdService;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;


@Path("GCDParameters")
public class GCDParameters {

    private final GcdService service;

    public GCDParameters(GcdService service) {
        this.service = service;
    }

    @POST
    @RolesAllowed("EXECUTOR")
    public Response push(PairParameters pairParams) {
        try {
            service.saveParameters(pairParams);
            return  Response.ok("{status: OK }").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        }

    }


}
