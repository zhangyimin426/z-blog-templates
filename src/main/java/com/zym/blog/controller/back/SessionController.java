package com.zym.blog.controller.back;

import com.fasterxml.jackson.databind.deser.Deserializers;
import com.zym.blog.constant.BaseConstant;
import com.zym.blog.controller.BaseController;
import com.zym.blog.domain.dto.AdminMenuRightDto;
import com.zym.blog.model.Admin;
import com.zym.blog.model.AdminLoginHistory;
import com.zym.blog.service.AdminLoginHistoryService;
import com.zym.blog.service.AdminService;
import com.zym.blog.service.RightService;
import com.zym.blog.constant.statuscode.GlobalResultStatus;
import com.zym.blog.utils.DateUtil;
import com.zym.blog.utils.JsonResult;
import com.zym.blog.utils.RequestUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * 管理后台登录和登出
 *
 * @author Gavin
 * @date 2016-10-09
 */
@RestController
@RequestMapping("session")
public class SessionController extends BaseController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private AdminLoginHistoryService adminLoginHistoryService;

    @Autowired
    private RightService rightService;

    private static final Logger log = LoggerFactory.getLogger(SessionController.class);

    /**
     * 创建会话（登录）
     *
     * @param loginName 登录用户名
     * @param password  密码
     * @param request   http请求
     * @return
     */
    @RequestMapping(value = "/toLogin", method = RequestMethod.POST)
    public Object toLogin(String loginName, String password, HttpServletRequest request) {

        //查询帐号信息
        Admin adminResult = adminService.getByLoginName(loginName);
        if (adminResult == null) {
            return JsonResult.fail(GlobalResultStatus.USER_NOT_EXIST);
        }

        //检查密码安全
        if (!DigestUtils.md5Hex(password).equals(adminResult.getPassword())) {
            return JsonResult.fail(GlobalResultStatus.USER_LOGIN_FAILED);
        }

        List<AdminMenuRightDto> menuRightDtos = rightService.getByAdminId(adminResult.getAdminId());

        request.getSession().setAttribute(BaseConstant.ADMIN_SESSION, adminResult);
        request.getSession().setAttribute(BaseConstant.ADMIN_RIGHT, menuRightDtos);

        log.info("-------zblog-------write：session_id:" + request.getSession().getId());
        try {
            recordLoginHistory(request, adminResult.getAdminId());
        } catch (Exception e) {
            log.info("账号【" + loginName + "】记录登录日志失败", e);
        }
        return JsonResult.success();
    }

    /**
     * 记录登录日志
     *
     * @param request
     * @param adminId
     */
    private void recordLoginHistory(HttpServletRequest request, Integer adminId) {
        AdminLoginHistory history = new AdminLoginHistory();
        history.setAdminId(adminId);
        history.setLoginTime(DateUtil.getLocalDate(new Date()));
        history.setRemoteIp(RequestUtil.getRemoteAddr(request));
        adminLoginHistoryService.insert(history);
    }

    /**
     * 销毁当前会话（登出）
     *
     * @param session 会话
     * @return
     */
    @RequestMapping(value = "/session/logout")
    public Object logout(HttpSession session) {
        session.removeAttribute(BaseConstant.ADMIN_SESSION);
        return JsonResult.success();
    }
}
