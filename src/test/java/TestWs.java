import com.roskart.dropwizard.jaxws.ClientBuilder;
import com.roskart.dropwizard.jaxws.JAXWSBundle;
import com.unico.soap.GcdSoapServiceInt;
import org.junit.Test;

public class TestWs {


    @Test
    public void testWs() {
        JAXWSBundle jaxwsBundle = new JAXWSBundle();
        GcdSoapServiceInt cl = (GcdSoapServiceInt) jaxwsBundle.getClient(new ClientBuilder(GcdSoapServiceInt.class,
                "http://localhost:8080/soap/gcd"));
        System.out.println("RET: " + cl.gcd());
    }
}
