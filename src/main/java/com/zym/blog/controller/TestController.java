package com.zym.blog.controller;

import com.zym.blog.model.Blog;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * @author Gavin
 * @date 2016-09-01
 */
@RestController
public class TestController extends BaseController {

    @Value("${spring.application.name}")
    private String appName;

    @RequestMapping("/")
    Object index() {
        Blog blog = new Blog();
        blog.setBlogId(1);
        blog.setBlogTitle("技术211111");
        blog.setBlogContent("真的很好");
        blog.setAuthor("zhangyimin");
        return blog;
    }

    @RequestMapping("/helloJsp")
    public String helloJsp(Map<String, Object> map) {
        map.put("hello", appName);
        return "/hello.jsp";
    }

    @RequestMapping("/test")
    public ModelAndView index2(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("test");
        modelAndView.addObject("name", "zhangyiminowen");
        return modelAndView;
    }
}
