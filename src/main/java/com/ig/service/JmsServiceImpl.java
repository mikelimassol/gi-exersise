package com.ig.service;

import com.ig.model.JmsDetailsForm;
import com.ig.model.Order;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.jms.*;
import java.io.IOException;
import java.util.List;

@Service
public class JmsServiceImpl implements JmsService {

    private final OrderXmlDeserializerService orderXmlDeserializerService;

    private final JmsTemplate jmsTemplate;



    @Autowired
    public JmsServiceImpl(OrderXmlDeserializerService orderXmlDeserializerService, JmsTemplate jmsTempate) {
        this.orderXmlDeserializerService = orderXmlDeserializerService;
        this.jmsTemplate = jmsTempate;
    }

    @Override
    public void send(MultipartFile file, JmsDetailsForm jmsDetails) throws EmptyOrdersException, IOException, JMSException {
        ConnectionFactory connectionFactory = getConnectionFactory(jmsDetails);
        JmsTemplate jmsTemplate = setAndGetJmsTemplate(jmsDetails, connectionFactory);
        processAndSendOrders(file, jmsDetails, jmsTemplate);

    }

    private void processAndSendOrders(MultipartFile file, JmsDetailsForm jmsDetails, JmsTemplate jmsTemplate) throws IOException, EmptyOrdersException {
        List<Order> orders = orderXmlDeserializerService.deserializeXmlOrders(new String(file.getBytes()));

        if(orders.isEmpty()){
            throw new EmptyOrdersException("Orders file is empty or has invalid data!");
        }

        for(Order order: orders){
            jmsTemplate.convertAndSend(jmsDetails.getDestination(), order);
        }
    }

    private JmsTemplate setAndGetJmsTemplate(JmsDetailsForm jmsDetailsForm,  ConnectionFactory connectionFactory) {

        jmsTemplate.setConnectionFactory(connectionFactory);

        if(jmsDetailsForm.getSendToTopic() == true){
            jmsTemplate.setPubSubDomain(true);
        } else {
            jmsTemplate.setPubSubDomain(false);
        }

        return jmsTemplate;
    }


    private ConnectionFactory getConnectionFactory(JmsDetailsForm jmsDetails) throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(jmsDetails.getBrokerConnection());
        connectionFactory.createConnection(jmsDetails.getBrokerUsername(), jmsDetails.getBrokerPassword());
        return connectionFactory;
    }


}

