package com.ig.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Objects;

@XmlRootElement(name = "Order")
@XmlAccessorType(XmlAccessType.FIELD)
public class Order implements Serializable {

    private String accont;

    @JacksonXmlProperty(localName = "SubmittedAt")
    private Long submittedAt;

    @JacksonXmlProperty(localName = "ReceivedAt")
    private Long receivedAt;

    private String market;
    private String action;
    private Integer size;

    public Order(String accont, Long submittedAt, Long receivedAt, String market, String action, Integer size) {
        this.accont = accont;
        this.submittedAt = submittedAt;
        this.receivedAt = receivedAt;
        this.market = market;
        this.action = action;
        this.size = size;
    }

    public Order() {
    }

    public String getAccont() {
        return accont;
    }

    public void setAccont(String accont) {
        this.accont = accont;
    }

    public Long getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(Long submittedAt) {
        this.submittedAt = submittedAt;
    }

    public Long getReceivedAt() {
        return receivedAt;
    }

    public void setReceivedAt(Long receivedAt) {
        this.receivedAt = receivedAt;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(accont, order.accont) &&
                Objects.equals(submittedAt, order.submittedAt) &&
                Objects.equals(receivedAt, order.receivedAt) &&
                Objects.equals(market, order.market) &&
                Objects.equals(action, order.action) &&
                Objects.equals(size, order.size);
    }

    @Override
    public int hashCode() {

        return Objects.hash(accont, submittedAt, receivedAt, market, action, size);
    }

    @Override
    public String toString() {
        return "Order{" +
                "accont='" + accont + '\'' +
                ", submittedAt=" + submittedAt +
                ", receivedAt=" + receivedAt +
                ", market='" + market + '\'' +
                ", action='" + action + '\'' +
                ", size=" + size +
                '}';
    }
}
