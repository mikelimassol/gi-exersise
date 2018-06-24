package com.ig.model;

import java.io.Serializable;
import java.util.Objects;

public class JmsDetailsForm implements Serializable{

        private String brokerConnection;
        private String brokerUsername;
        private String brokerPassword;
        private String destination;
        private Boolean sendToTopic;


    public JmsDetailsForm(String brokerConnection, String brokerUsername, String brokerPassword, String destination, Boolean sendToTopic) {
        this.brokerConnection = brokerConnection;
        this.brokerUsername = brokerUsername;
        this.brokerPassword = brokerPassword;
        this.destination = destination;
        this.sendToTopic = sendToTopic;
    }

    public JmsDetailsForm() {}

    public String getBrokerConnection() {
        return brokerConnection;
    }

    public void setBrokerConnection(String brokerConnection) {
        this.brokerConnection = brokerConnection;
    }

    public String getBrokerUsername() {
        return brokerUsername;
    }

    public void setBrokerUsername(String brokerUsername) {
        this.brokerUsername = brokerUsername;
    }

    public String getBrokerPassword() {
        return brokerPassword;
    }

    public void setBrokerPassword(String brokerPassword) {
        this.brokerPassword = brokerPassword;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Boolean getSendToTopic() {
        return sendToTopic;
    }

    public void setSendToTopic(Boolean isTopic) {
        this.sendToTopic = isTopic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JmsDetailsForm that = (JmsDetailsForm) o;
        return Objects.equals(brokerConnection, that.brokerConnection) &&
                Objects.equals(brokerUsername, that.brokerUsername) &&
                Objects.equals(brokerPassword, that.brokerPassword) &&
                Objects.equals(destination, that.destination) &&
                Objects.equals(sendToTopic, that.sendToTopic);
    }

    @Override
    public int hashCode() {

        return Objects.hash(brokerConnection, brokerUsername, brokerPassword, destination, sendToTopic);
    }

    @Override
    public String toString() {
        return "JmsDetails{" +
                "brokerConnection='" + brokerConnection + '\'' +
                ", brokerUsername='" + brokerUsername + '\'' +
                ", brokerPassword='" + brokerPassword + '\'' +
                ", destination='" + destination + '\'' +
                ", isTopic='" + sendToTopic + '\'' +
                '}';
    }
}
