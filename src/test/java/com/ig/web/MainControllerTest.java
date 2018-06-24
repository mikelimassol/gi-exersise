package com.ig.web;


import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ig.model.JmsDetailsForm;
import com.ig.service.EmptyOrdersException;
import com.ig.service.JmsService;
import org.junit.Before;
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

    private JmsDetailsForm defaultJmsDetails;
    private JmsDetailsForm editedJmsDetails;

    @Before
    public void init(){
        defaultJmsDetails = new JmsDetailsForm("tcp://localhost:61616", "admin", "admin", "nterview-1",false );
        editedJmsDetails = new JmsDetailsForm("tcp://127.0.0:61616", "test", "test", "nterview-2",true );
    }

    @Test
    public void shouldLoadDefaultJmsDetails() throws Exception {

        this.mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(defaultJmsDetails.getBrokerConnection())))
                .andExpect(content().string(containsString(defaultJmsDetails.getBrokerUsername())))
                .andExpect(content().string(containsString(defaultJmsDetails.getBrokerPassword())))
                .andExpect(content().string(containsString(defaultJmsDetails.getDestination())));

    }

    @Test
    public void shouldUploadAndSendFile() throws Exception, EmptyOrdersException {

        //given
        ClassPathResource resource = new ClassPathResource("interview-test-orders-1.xml");

        MockMultipartFile multipartFile = new MockMultipartFile("file", "interview-test-orders-1.xml",
                "text/xml", resource.getInputStream());

        //when
        this.mvc.perform(fileUpload("/")
                .file(multipartFile)
                .param("brokerConnection", editedJmsDetails.getBrokerConnection())
                .param("brokerUsername", editedJmsDetails.getBrokerUsername())
                .param("brokerPassword", editedJmsDetails.getBrokerPassword())
                .param("destination", editedJmsDetails.getDestination())
                .param("sendToTopic", editedJmsDetails.getSendToTopic().toString())
        )
                .andExpect(status().isFound())
                .andExpect(header().string("Location", "/"))
                .andExpect(redirectedUrl("/"))
                .andExpect(flash().attribute("jmsDetailsForm", editedJmsDetails))
                .andExpect(flash().attribute("message", "You successfully send the content interview-test-orders-1.xml!"));

        //then
        then(this.jmsService).should().send(multipartFile, editedJmsDetails);

    }






}
