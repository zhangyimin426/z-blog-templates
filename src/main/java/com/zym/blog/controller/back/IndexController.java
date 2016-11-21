package com.zym.blog.controller.back;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Gavin
 * @date 2016-11-10
 */
@RestController
public class IndexController {

    @RequestMapping("/index")
    public ModelAndView indexPage() {
        return new ModelAndView("index");
    }

    @RequestMapping("/login")
    public ModelAndView loginPage() {
        return new ModelAndView("login");
    }
}
