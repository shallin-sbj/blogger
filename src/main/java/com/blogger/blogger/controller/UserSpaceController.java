package com.blogger.blogger.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 用户主页 控制器
 */
@Controller
@RequestMapping("/u")
public class UserSpaceController {

    @GetMapping("/{username}")
    public String userSpace(@PathVariable("username") String username) {
        System.out.println("username:" + username);
        return "/userspace/u";
    }

    /**
     * @param username
     * @param order    热门程度，创建时间
     * @param category 类型
     * @param keyword  关键字
     * @return
     */
    @GetMapping("/{username}/blogs")
    public String listBlogsByOrder(@PathVariable("username") String username,
                                   @RequestParam(value = "order", required = false, defaultValue = "new") String order,
                                   @RequestParam(value = "category", required = false, defaultValue = "new") Long category,
                                   @RequestParam(value = "keyword", required = false) String keyword) {

        System.out.println("username:" + username + "category:" + category + "keyword:" + keyword + "order:" + order);

        if (category != null) {
            return "/userspace/u";
        } else if (!StringUtils.isEmpty(keyword)) {
            return "/userspace/u";
        }
        return "/userspace/u";
    }

    @GetMapping("/{username}/blogs/{id}")
    public String listBlogsByOrder(@PathVariable("id") Long id) {
        System.out.println("id:" + id);

        return "/userspace/blogs/";
    }

    @GetMapping("/{username}/blogs/edit")
    public String editBlog() {
        return "/userspace/blogedit";
    }
}
