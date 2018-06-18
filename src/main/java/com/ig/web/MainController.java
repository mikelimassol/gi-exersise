package com.ig.web;

import com.ig.model.JmsDetails;
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

import java.io.IOException;

@Controller
public class MainController {

    @Autowired
    private JmsService jmsService;


    @Autowired
    public MainController(JmsService jmsService) {
        this.jmsService = jmsService;
    }

    @GetMapping("/")
    public String index(Model model) throws IOException {
        return "form";
    }

    @PostMapping("/")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes, @ModelAttribute JmsDetails jmsDetails) {

        jmsService.send(file, jmsDetails);

        redirectAttributes.addFlashAttribute("message",
                "You successfully send the content " + file.getOriginalFilename() + "!");

        return "redirect:/";
    }

}
