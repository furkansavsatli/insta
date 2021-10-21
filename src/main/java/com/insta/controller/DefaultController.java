package com.insta.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@ApiIgnore
public class DefaultController {

    @RequestMapping(value = "/insta")
    public void redirectInsta(HttpServletResponse response) throws IOException {
        response.sendRedirect("/swagger-ui.html");
    }
}