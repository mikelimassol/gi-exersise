package com.ig;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface JmsService {

    void send(MultipartFile file);

}
