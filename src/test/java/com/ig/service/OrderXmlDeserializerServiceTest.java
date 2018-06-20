package com.ig.service;

import com.ig.model.Order;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class OrderXmlDeserializerServiceTest {


    private OrderXmlDeserializerService orderXmlDeserializerService = new OrderXmlDeserializerService();


    @Test
    public void whenJavaGotFromXmlStr_thenCorrect() throws IOException {

        ClassPathResource resource = new ClassPathResource("interview-test-orders-1.xml");

        byte[] contentBytes = Files.readAllBytes(Paths.get(resource.getURI()));
        String content = new String(contentBytes);

        List<Order> orders = orderXmlDeserializerService.deserializeOrdersXml(content);
        assertThat(orders.size(), is(equalTo(2)));

    }

}
