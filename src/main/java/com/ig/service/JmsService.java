package com.ig.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

public interface JmsService {

    void send(MultipartFile file);

}
