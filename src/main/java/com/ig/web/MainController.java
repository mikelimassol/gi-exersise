package com.ig.web;

import com.ig.model.JmsDetailsForm;
import com.ig.service.EmptyOrdersException;
import com.ig.service.JmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.jms.JMSException;
import java.io.IOException;

@Controller
public class MainController {

    private static final String BROKER_CONNECTION = "tcp://localhost:61616";
    private static final String BROKER_USERNAME = "admin";
    private static final String BROKER_PASSWORD = "admin";
    private static final String DESTINATION = "nterview-1";
    private static final boolean SEND_TO_TOPIC = false;

    private static final String YOU_SUCCESSFULLY_SEND_THE_CONTENT = "You successfully send the content ";
    private static final String SOMETHING_GET_WRONG_PLEASE_CHECK_JMS_CONNECTION_DETAILS = "Something get wrong! Please check Jms connection details";
    private static final String JMS_DETAILS_FORM = "jmsDetailsForm";
    private static final String MESSAGE = "message";

    @Autowired
    private JmsService jmsService;

    @Autowired
    public MainController(JmsService jmsService) {
        this.jmsService = jmsService;
    }

    @GetMapping("/")
    public String index(Model model) throws IOException {

        if(!model.containsAttribute(JMS_DETAILS_FORM)){

            JmsDetailsForm jmsDetailsForm = new JmsDetailsForm(BROKER_CONNECTION, BROKER_USERNAME, BROKER_PASSWORD, DESTINATION, SEND_TO_TOPIC);

            model.addAttribute(JMS_DETAILS_FORM, jmsDetailsForm);

        }

        return "form";
    }

    @PostMapping("/")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes, @ModelAttribute JmsDetailsForm jmsDetailsForm) throws IOException, JMSException {

        String message = "";

        try {
            jmsService.send(file, jmsDetailsForm);

            message = YOU_SUCCESSFULLY_SEND_THE_CONTENT + file.getOriginalFilename() + "!";

        } catch (Exception ex){

            message = SOMETHING_GET_WRONG_PLEASE_CHECK_JMS_CONNECTION_DETAILS;

        } catch (EmptyOrdersException e) {

            message = e.getMessage();

        }

        redirectAttributes.addFlashAttribute(MESSAGE,
                message).addFlashAttribute(JMS_DETAILS_FORM, jmsDetailsForm);

        return "redirect:/";
    }
}
