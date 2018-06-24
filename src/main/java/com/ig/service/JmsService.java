package com.ig.service;

import com.ig.model.JmsDetailsForm;
import org.springframework.web.multipart.MultipartFile;

import javax.jms.JMSException;
import java.io.IOException;

public interface JmsService {

    void send(MultipartFile file, JmsDetailsForm jmsDetails) throws JMSException, IOException, EmptyOrdersException;

}
