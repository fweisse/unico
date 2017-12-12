package com.unico.soap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unico.json.PairParameters;
import com.unico.service.GcdService;

import javax.jms.*;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.io.IOException;
import java.util.ArrayList;
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
    public int gcd() {
        return service.calculateCurrentGcd();
    }

    @Override
    @WebMethod
    public List<Integer> list() {
        return service.getGcdHistory();

    }

    @Override
    @WebMethod
    public int gcdSum() {
        return service.getGcdHistorySum();
    }

}
