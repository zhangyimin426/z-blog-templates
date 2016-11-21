package com.zym.blog.controller.back;

import com.zym.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Gavin
 * @date 2016-09-05
 */
@RestController
@RequestMapping("/article")
public class BlogController {
    @Autowired
    private BlogService blogService;

    @RequestMapping("/list")
    public ModelAndView getList() {
        return new ModelAndView("blog/list");
    }

    @RequestMapping(value = "/{blogId}", method = RequestMethod.GET)
    public Object get(@PathVariable("blogId") int id) {
        return blogService.getById(id);
    }


    @RequestMapping(value = "", method = RequestMethod.GET)
    public Object getAll(Integer page, Integer perPage) {
        if (page == null) {
            page = 1;
        }
        if (perPage == null) {
            perPage = 4;
        }
        return blogService.getAll(page, perPage);
    }
}
