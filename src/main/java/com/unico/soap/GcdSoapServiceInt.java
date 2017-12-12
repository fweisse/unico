package com.unico.soap;

import javax.jws.WebMethod;
import java.util.List;

public interface GcdSoapServiceInt {
    @WebMethod
    int gcd();

    @WebMethod
    List<Integer> list();

    @WebMethod
    int gcdSum();
}
