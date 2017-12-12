package com.unico;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kjetland.dropwizard.activemq.ActiveMQConfig;
import com.kjetland.dropwizard.activemq.ActiveMQConfigHolder;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;


import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class Config extends Configuration implements ActiveMQConfigHolder {

    @JsonProperty
    @NotNull
    @Valid
    private ActiveMQConfig activeMQ;

    @JsonProperty
    @NotNull
    private String queueName;

    @Valid
    @NotNull
    @JsonProperty("database")
    private DataSourceFactory database = new DataSourceFactory();

    public DataSourceFactory getDataSourceFactory() {
        return database;
    }

    public ActiveMQConfig getActiveMQ() {
        return activeMQ;
    }

    public String getQueueName() {
        return queueName;
    }
}
