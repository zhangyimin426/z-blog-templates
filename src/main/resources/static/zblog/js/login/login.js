/**
 * Created by gavin on 2016-11-09.
 */

$(function () {
    /**
     * 绑定登录按钮
     */
    $("#loginBtn").click(function () {
        var $loginNameVal = $("#loginName").val();
        var $passwordVal = $("#password").val();
        if (checkReqParam($loginNameVal, $passwordVal)) {
            login();
        }
    });

    /**
     * 绑定登录按钮
     */
    $("#logoutBtn").click(function () {
        logout();
    });
});

/**
 * 检查参数
 * @param loginName
 * @param password
 * @returns {boolean}
 */
var checkReqParam = function (loginName, password) {
    if ($.trim(loginName) == "" || loginName == null || loginName == undefined) {
        $("#loginName").focus();
        alert("请输入登录名");
        return false;
    } else if ($.trim(password) == "" || password == null || password == undefined) {
        $("#password").focus();
        alert("请输入密码");
        return false;
    }
    return true;
};

/**
 * 登录
 * @param loginName
 * @param password
 */
var login = function () {
    $.ajax({
        url: "session/toLogin",
        data: $("#loginForm").serialize(),
        type: 'post',
        cache: false,
        success: function (data) {
            if (data.code == 0) {
                window.location.href = "index";
            }else {
                alert(JSON.stringify(data));
            }
        },
        error: function () {
            alert("登录异常！");
        }
    });
};

/**
 * 登出
 */
var logout = function () {
    $.ajax({
        url: "session/logout",
        type: 'post',
        cache: false,
        success: function (data) {
            if (data.code == 0) {
                window.location.href = "login";
            } else {
                alert(JSON.stringify(data));
            }
        },
        error: function () {
            alert("登出异常！");
        }
    });
}