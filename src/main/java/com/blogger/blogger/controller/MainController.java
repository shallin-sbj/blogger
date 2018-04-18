package com.blogger.blogger.controller;

import com.blogger.blogger.aop.SystemControllerAnnotation;
import com.blogger.blogger.domain.Authority;
import com.blogger.blogger.domain.User;
import com.blogger.blogger.service.AuthorityService;
import com.blogger.blogger.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * 主页控制器
 */
@Controller
public class MainController {
    /**
     * 用户（博主）权限
     */
    private static final Long ROLE_USER_AUTHORITY_ID = 2L;

    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private UserService userService;


    @GetMapping("/")
    public String root() {
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    @SystemControllerAnnotation(description = "登录")
    public String login() {
        return "login";
    }

    @GetMapping("/login-error")
    @SystemControllerAnnotation(description = "登录错误，从新登录")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        model.addAttribute("errorMsg", "登录错误，账号或密码错误！");
        return "login";
    }

    @GetMapping("/register")
    @SystemControllerAnnotation(description = "注册")
    public String reqister() {
        return "register";
    }

    /**
     * 注册用户
     *
     * @param user
     * @return
     */
    @PostMapping("/register")
    @SystemControllerAnnotation(description = "保存新用户信息")
    public String registerUser(User user) {
        List<Authority> authorities = new ArrayList<>();
        authorities.add(authorityService.getAuthorityById(ROLE_USER_AUTHORITY_ID));
        user.setAuthorities(authorities);
        userService.saveOrUpdateUser(user);
        return "redirect:/login";
    }

    @GetMapping("/search")
    public String search() {
        return "search";
    }

}
