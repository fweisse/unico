package com.unico.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kjetland.dropwizard.activemq.ActiveMQSender;
import com.unico.dao.GcdDao;
import com.unico.json.PairParameters;

import javax.jms.*;
import java.math.BigInteger;
import java.util.List;

public class GcdService {

    private final GcdDao dao;
    private final ConnectionFactory jmsConnectionFactory;
    private final String queueName;
    private final int maxTime = 10000;
    private final ObjectMapper mapper;
    private final ActiveMQSender jmsSender;

    public GcdService(GcdDao dao, String queueName, ConnectionFactory jmsConnectionFactory, ObjectMapper mapper, ActiveMQSender jmsSender) {
        this.dao = dao;
        this.queueName = queueName;
        this.jmsConnectionFactory = jmsConnectionFactory;
        this.mapper = mapper;
        this.jmsSender = jmsSender;
    }

    public void saveParameters(PairParameters params) throws Exception {
        //This shoud be in a distributed transaction or support duplicates
        this.jmsSender.send(params);
        dao.insertParams(params.getA(), params.getB());
    }

    public List<Integer> getGcdHistory() {
        return dao.getCalculatedGCD();
    }

    public int getGcdHistorySum() {
        return dao.getCalculatedGCD().stream().reduce(0, (a, b) -> a + b );
    }
    private PairParameters getParametersFromQueue()  {
        try {
            Connection jmsCon = jmsConnectionFactory.createConnection();
            Session jmsSession = jmsCon.createSession(false, Session.CLIENT_ACKNOWLEDGE);
            MessageConsumer consumer = jmsSession.createConsumer(jmsSession.createQueue(queueName));
            jmsCon.start();
            TextMessage message = (TextMessage) consumer.receive(maxTime);
            PairParameters params = mapper.readValue(message.getText(), PairParameters.class);
            message.acknowledge();
            consumer.close();
            jmsSession.close();
            jmsCon.stop();
            jmsCon.close();
            return params;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int calculateCurrentGcd(){
        PairParameters pair = getParametersFromQueue();
        BigInteger a = BigInteger.valueOf(pair.getA());
        BigInteger b = BigInteger.valueOf(pair.getB());
        int gcd = a.gcd(b).intValue();
        dao.insertCalculatedGcd(gcd);
        return gcd;
    }

}
