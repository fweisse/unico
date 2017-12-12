package com.unico;

import com.kjetland.dropwizard.activemq.ActiveMQBundle;
import com.kjetland.dropwizard.activemq.ActiveMQSender;
import com.roskart.dropwizard.jaxws.BasicAuthentication;
import com.roskart.dropwizard.jaxws.EndpointBuilder;
import com.roskart.dropwizard.jaxws.JAXWSBundle;
import com.unico.auth.UnicoAuthenticator;
import com.unico.auth.UnicoAuthorizer;
import com.unico.auth.User;
import com.unico.dao.GcdDao;
import com.unico.resource.GCDParameters;
import com.unico.service.GcdService;
import com.unico.soap.GcdSoapService;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.skife.jdbi.v2.DBI;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.net.URL;
import java.util.EnumSet;

public class Application extends io.dropwizard.Application<Config> {
    private JAXWSBundle jaxwsBundle = new JAXWSBundle();
    private ActiveMQBundle activeMQBundle = new ActiveMQBundle();
    private ActiveMQConnectionFactory amq = new ActiveMQConnectionFactory("vm://localhost?broker.persistent=false");


    public static  void main (String [] args) throws Exception  {
        URL configYml = Application.class.getResource("/config.yml");
        String configUrl = configYml.getPath();
        new Application().run("server", configUrl);
    }




    @Override
    public void initialize(Bootstrap<Config> bootstrap) {
        bootstrap.addBundle(jaxwsBundle);
        bootstrap.addBundle(activeMQBundle);
    }

    @Override
    public void run(Config configuration, Environment environment) throws Exception {
        enableCors(environment);
        enableSecurity(environment);
        //DATABASE
        final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory.build(environment, configuration.getDataSourceFactory(), "h2");
        //JMS QUEUE NAME
        final String queueName = configuration.getQueueName();
        // Set up the sender for our queue
        ActiveMQSender amqSender = activeMQBundle.createSender( queueName, false);
        //DAO
        GcdDao dao = jdbi.onDemand(GcdDao.class);
        //SERVICE
        GcdService service = new GcdService(dao, configuration.getQueueName(),amq , environment.getObjectMapper(), amqSender);

        // Create resources
        environment.jersey()
                .register(new GCDParameters(service));

        //Create soap endpoints
        jaxwsBundle.publishEndpoint(
               new EndpointBuilder("/gcd",
                new GcdSoapService(service))
                .authentication(new BasicAuthentication(new UnicoAuthenticator(), "BASIC-AUTH-REALM")
                )
        );
    }

    private void enableSecurity(final Environment environment) {
        environment.jersey().register(new AuthDynamicFeature(
                new BasicCredentialAuthFilter.Builder<User>()
                        .setAuthenticator(new UnicoAuthenticator())
                        .setAuthorizer(new UnicoAuthorizer())
                        .setRealm("BASIC-AUTH-REALM")
                        .buildAuthFilter()));
        environment.jersey().register(RolesAllowedDynamicFeature.class);
        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));
    }

    private void enableCors(final Environment environment) {
        final FilterRegistration.Dynamic cors = environment.servlets().addFilter("CORS", CrossOriginFilter.class);
        cors.setInitParameter("allowedOrigins", "*");
        cors.setInitParameter("allowedHeaders", "Origin,Authorization,Content-Type,Accept");
        cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");

    }

}
