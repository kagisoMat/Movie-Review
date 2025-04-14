package com.fmtalitech.movies;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FrontendController {

    @RequestMapping(value = "/{path:^(?!api).*$}")
    public String forwardToFrontend() {
        return "forward:/index.html";
    }
}

