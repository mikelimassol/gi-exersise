package com.ig.web;


import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ig.model.JmsDetails;
import com.ig.service.JmsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class MainControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private JmsService jmsService;

    @Test
    public void shouldUploadAndSendFile() throws Exception {

        ClassPathResource resource = new ClassPathResource("interview-test-orders-1.xml");

        JmsDetails jmsDetails = new JmsDetails("tcp://localhost:61616", "admin", "admin", "nterview-1","queue" );

        MockMultipartFile multipartFile = new MockMultipartFile("file", "interview-test-orders-1.xml",
                "text/xml", resource.getInputStream());

        this.mvc.perform(fileUpload("/")
                .file(multipartFile)
                .param("brokerConnection", jmsDetails.getBrokerConnection())
                .param("brokerUsername", jmsDetails.getBrokerUsername())
                .param("brokerPassword", jmsDetails.getBrokerPassword())
                .param("destination", jmsDetails.getDestination())
                .param("destinationType", jmsDetails.getDestinationType())
        )
                .andExpect(status().isFound())
                .andExpect(header().string("Location", "/"))
                .andExpect(redirectedUrl("/"))
                .andExpect(flash().attribute("message", "You successfully send the content interview-test-orders-1.xml!"));

        then(this.jmsService).should().send(multipartFile, jmsDetails);

    }



}
