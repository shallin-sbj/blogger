package com.blogger.blogger.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *  后台管理控制器
 */
@Controller
@RequestMapping("/admins")
public class AdminController {

    /**
     * 获取后台管理界面
     * @param model
     * @return
     */
    @GetMapping()
    public ModelAndView userSpace(Model model) {
        return new ModelAndView("admin/index","menuList",model);
    }


}
