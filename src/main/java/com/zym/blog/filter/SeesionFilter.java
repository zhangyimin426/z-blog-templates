package com.zym.blog.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zym.blog.constant.BaseConstant;
import com.zym.blog.model.Admin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * session管理（使用注解标注过滤器）
 *
 * @author Gavin
 * @WebFilter将一个实现了javax.servlet.Filter接口的类定义为过滤器 属性filterName声明过滤器的名称, 可选
 * 属性urlPatterns指定要过滤的URL模式,也可使用属性value来声明.(指定要过滤的URL模式是必选属性)
 * @date 2016-10-17
 */


@WebFilter(filterName = "sessionFilter", urlPatterns = "/*")
public class SeesionFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(SeesionFilter.class);

    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("初始化session过滤器");
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String url = request.getRequestURI();
        String contentPath = request.getContextPath();
        HttpSession session = request.getSession();

        Admin admin = (Admin) session.getAttribute(BaseConstant.ADMIN_SESSION);

        log.info("-------zblog-------read：session_id:" + request.getSession().getId());

        if (admin != null) {
            log.info("Admin:" + admin.toString());
        } else {
            log.debug("-------zblog-------admin is null:" + request.getSession().getId());
            if (!checkFilterUrls(url, contentPath) && !url.endsWith("login")) {
                log.info("拦截的url：" + url);
                response.sendRedirect(contentPath + "/login");
                return;
            }
        }
        chain.doFilter(request, response);
    }

    /**
     * 检查是否需要放过的资源
     *
     * @param requestUrl  请求的url
     * @param contentPath 上下文
     * @return
     */
    public boolean checkFilterUrls(String requestUrl, String contentPath) {
        /**
         * 增加非过滤的url，主要是静态资源
         */
        List<String> urls = new ArrayList<String>();
        urls.add(contentPath + "/zblog");
        urls.add(contentPath + "/login");
        urls.add(contentPath + "/session");
        urls.add(contentPath + "/test");
        for (String url : urls) {
            if (requestUrl.startsWith(url)) {
                return true;//true放过
            }
        }
        return false;
    }

    private void writeResponse(HttpServletResponse response, Object result) {
        response.setContentType("application/json;charset=UTF-8");
        ObjectMapper mapper = new ObjectMapper();
        try {
            response.getWriter().write(mapper.writeValueAsString(result));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public void destroy() {
        log.info("摧毁session过滤器");
    }

}
