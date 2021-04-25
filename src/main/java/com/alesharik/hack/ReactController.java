package com.alesharik.hack;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ReactController {
    @RequestMapping({"/login", "/admin", "/admin/**"})
    public String index() {
        return "/index.html";
    }
}
