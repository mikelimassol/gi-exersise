package com.ig.service;

import com.ig.model.JmsDetailsForm;
import com.ig.model.Order;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.mock.web.MockMultipartFile;

import javax.jms.JMSException;
import java.io.IOException;
import java.util.Arrays;

import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class JmsServiceTest {

    @Mock
    private OrderXmlDeserializerService orderXmlDeserializerService;

    @Mock
    private JmsTemplate jmsTempate;

    @InjectMocks
    private JmsServiceImpl jmsService;

    private JmsDetailsForm defaultJmsDetails;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        jmsService = new JmsServiceImpl(orderXmlDeserializerService, jmsTempate);
        defaultJmsDetails = new JmsDetailsForm("tcp://localhost:61616", "admin", "admin", "nterview-1", false);

    }


    @Test
    public void send_shouldSendOrdersToAmq() throws IOException, JMSException, EmptyOrdersException {

        //given
        ClassPathResource orderFile = new ClassPathResource("interview-test-orders-1.xml");
        MockMultipartFile multipartFile = new MockMultipartFile("file", "interview-test-orders-1.xml",
                "text/xml", orderFile.getInputStream());

        when(this.orderXmlDeserializerService.deserializeXmlOrders(new String(multipartFile.getBytes()))).thenReturn(Arrays.asList(new Order(), new Order()));

        jmsService.send(multipartFile, defaultJmsDetails);

        //then
        then(this.orderXmlDeserializerService).should().deserializeXmlOrders(new String(multipartFile.getBytes()));

    }

    @Test(expected = EmptyOrdersException.class)
    public void send_shouldGetErrorMessageIfFileIsMissing() throws Exception, EmptyOrdersException {

        //given
        MockMultipartFile multipartFile = new MockMultipartFile("file", "interview-test-orders-1.xml", "text/xml", "".getBytes());

        //when
        jmsService.send(multipartFile, defaultJmsDetails);

    }

}
