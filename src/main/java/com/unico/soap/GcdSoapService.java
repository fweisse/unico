package com.unico.soap;

import com.unico.service.GcdService;

import javax.annotation.security.RolesAllowed;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.List;

@WebService(
        name = "GCDServicePortType",
        serviceName = "GcdSoapService",
        targetNamespace = "http://soap.unico.com/"
)
@SOAPBinding(
        style = SOAPBinding.Style.DOCUMENT,
        use = SOAPBinding.Use.LITERAL,
        parameterStyle = SOAPBinding.ParameterStyle.WRAPPED
)
public class GcdSoapService implements GcdSoapServiceInt {

  private final GcdService service;

    public GcdSoapService(GcdService service) {
        this.service = service;
    }

    @Override
    @WebMethod
    @RolesAllowed("EXECUTOR")
    public int gcd() {
        return service.calculateCurrentGcd();
    }

    @Override
    @WebMethod
    @RolesAllowed("READER")
    public List<Integer> list() {
        return service.getGcdHistory();

    }

    @Override
    @WebMethod
    @RolesAllowed("READER")
    public int gcdSum() {
        return service.getGcdHistorySum();
    }

}
