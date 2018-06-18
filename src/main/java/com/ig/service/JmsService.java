package com.ig.service;

import com.ig.model.JmsDetails;
import org.springframework.web.multipart.MultipartFile;

public interface JmsService {

    void send(MultipartFile file, JmsDetails jmsDetails);

}
