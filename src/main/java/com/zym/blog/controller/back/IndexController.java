package com.zym.blog.controller.back;

import com.zym.blog.controller.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Gavin
 * @date 2016-11-10
 */
@RestController
public class IndexController extends BaseController {

    /**
     * 跳转到登录页面
     *
     * @return
     */
    @RequestMapping("/login")
    public ModelAndView loginPage() {
        return new ModelAndView("login");
    }

    /**
     * 跳转到首页
     *
     * @return
     */
    @RequestMapping("/index")
    public ModelAndView indexPage() {
        return new ModelAndView("index");
    }

}
