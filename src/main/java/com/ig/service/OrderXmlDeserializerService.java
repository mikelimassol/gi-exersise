package com.ig.service;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.ig.model.Order;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class OrderXmlDeserializerService {


    private static final String ORDER_SEPERATOR = "<Order>";

    public List<Order> deserializeXmlOrders(String content) throws IOException {

        if(StringUtils.isEmpty(content)){
            return new ArrayList<>();
        }

        content = sanitizeContent(content);

        XmlMapper xmlMapper = new XmlMapper();

        String[] orderXmlList = content.split(ORDER_SEPERATOR);

        List<Order> orderList = new ArrayList<>();
        for (String orderXml : orderXmlList) {
            if(!orderXml.equalsIgnoreCase("")) {
                Order order = xmlMapper.readValue(ORDER_SEPERATOR + orderXml, Order.class);
                orderList.add(order);
            }

        }

        return orderList;

    }

    private String sanitizeContent(String content){
        return content.replaceAll("(<\\?xml.*?\\?>)","").replaceAll("\\s+","");
    }


}
