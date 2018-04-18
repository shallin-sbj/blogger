package com.blogger.blogger.controller;

import com.blogger.blogger.aop.SystemControllerAnnotation;
import com.blogger.blogger.domain.Blog;
import com.blogger.blogger.domain.User;
import com.blogger.blogger.service.BlogService;
import com.blogger.blogger.service.UserService;
import com.blogger.blogger.utils.ConstraintViolationExceptionHandler;
import com.blogger.blogger.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.ConstraintViolationException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * 用户主页 控制器
 */
@Controller
@RequestMapping("/u")
public class UserSpaceController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;
    @Autowired
    private BlogService blogService;

    //    @Autowired
//    private MyConfig config;
    private String fileServiceUrl = "http://localhost:8081/upload";

    /**
     * 获取个人信息
     *
     * @param username
     * @param model
     * @return
     */
    @GetMapping("/{username}/profile")
    @PreAuthorize("authentication.name.equals(#username)")
    @SystemControllerAnnotation(description = "获取个人信息")
    public ModelAndView profile(@PathVariable("username") String username, Model model) {
        User user = (User) userDetailsService.loadUserByUsername(username);
        model.addAttribute("user", user);
        return new ModelAndView("/userspace/profile", "userModel", model);
    }

    /**
     * 保存个人设置
     * 这里因为请求的url 中默认带:/u/adim/profile 所以说被拦截了
     *
     * @param user
     * @return
     */
    @PostMapping("/{username}/profile")
    @PreAuthorize("authentication.name.equals(#username)")
    @SystemControllerAnnotation(description = "保存个人设置")
    public String saveProfile(@PathVariable("username") String username, User user) {
        User originalUser = userService.queryUserById(user.getId());
        originalUser.setEmail(user.getEmail());
        originalUser.setName(user.getName());

        // 判断密码是否做了变更
        String rawPassword = originalUser.getPassword();
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodePasswd = encoder.encode(user.getPassword());
        boolean isMatch = encoder.matches(rawPassword, encodePasswd);
        if (!isMatch) {
            originalUser.setEncodePassword(user.getPassword());
        }

        userService.saveOrUpdateUser(originalUser);
        return "redirect:/u/" + username + "/profile";
    }

    /**
     * 获取编辑头像的界面
     *
     * @param username
     * @param model
     * @return
     */
    @GetMapping("/{username}/avatar")
    @PreAuthorize("authentication.name.equals(#username)")
    @SystemControllerAnnotation(description = "获取编辑头像")
    public ModelAndView avatar(@PathVariable("username") String username, Model model) {
        User user = (User) userDetailsService.loadUserByUsername(username);
        model.addAttribute("user", user);
        return new ModelAndView("/userspace/avatar", "userModel", model);
    }


    /**
     * 保存头像
     *
     * @param username
     * @return
     */
    @PostMapping("/{username}/avatar")
    @PreAuthorize("authentication.name.equals(#username)")
    @SystemControllerAnnotation(description = "保存头像")
    public ResponseEntity<Response> saveAvatar(@PathVariable("username") String username, @RequestBody User user) {
        String avatarUrl = user.getAvatar();

        User originalUser = userService.queryUserById(user.getId());
        originalUser.setAvatar(avatarUrl);
        userService.saveOrUpdateUser(originalUser);

        return ResponseEntity.ok().body(new Response(true, "处理成功", avatarUrl));
    }


    /**
     * 用户主页
     *
     * @param username
     * @return
     */

    @GetMapping("/{username}")
    @SystemControllerAnnotation(description = "用户主页")
    public String userSpace(@PathVariable("username") String username, Model model) {
        User user = (User) userDetailsService.loadUserByUsername(username);
        model.addAttribute("user", user);
        return "redirect:/u/" + username + "/blogs";
    }

//    /**
//     * @param username  用户名
//     * @param order     内容
//     * @param category  资源类型
//     * @param keyword   关键字
//     * @param async
//     * @param pageIndex
//     * @param pageSize
//     * @param model
//     * @return
//     */
//    @GetMapping("/{username}/blogs")
//    @SystemControllerAnnotation(description = "获取用户博客列表")
//    public String listBlogsByOrder(
//            @PathVariable("username") String username,
//            @RequestParam(value = "order", required = false, defaultValue = "new") String order,
//            @RequestParam(value = "category", required = false) Long category,
//            @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
//            @RequestParam(value = "async", required = false) boolean async,
//            @RequestParam(value = "pageIndex", required = false, defaultValue = "0") int pageIndex,
//            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
//            Model model) {
//        User user = (User) userDetailsService.loadUserByUsername(username);
//        model.addAttribute("user", user);
//
//        if (category != null) {
//            System.out.print("category:" + category);
//            System.out.print("selflink:" + "redirect:/u/" + username + "/blogs?category=" + category);
//            return "/u";
//        }
//        Page<Blog> page = null;
//        if (order.equals("hot")) { // 最热查询
//            Sort sort = new Sort(Sort.Direction.DESC, "reading", "comments", "likes");
//            Pageable pageable = new PageRequest(pageIndex, pageSize, sort);
//            page = blogService.listBlogsByTitleLikeAndSort(user, keyword, pageable);
//        }
//        if (order.equals("new")) { // 最新查询
//            Pageable pageable = new PageRequest(pageIndex, pageSize);
//            page = blogService.listBlogsByTitleLike(user, keyword, pageable);
//        }
//        List<Blog> list = page.getContent();    // 当前所在页面数据列表
//        model.addAttribute("order", order);
//        model.addAttribute("page", page);
//        model.addAttribute("blogList", list);
//        return (async == true ? "/userspace/u :: #mainContainerRepleace" : "/userspace/u");
//    }

    @GetMapping("/{username}/blogs")
    @SystemControllerAnnotation(description = "获取个人主页信息")
    public String listBlogsByOrder(@PathVariable("username") String username,
                                   @RequestParam(value = "order", required = false, defaultValue = "new") String order,
                                   @RequestParam(value = "category", required = false) Long category,
                                   @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
                                   @RequestParam(value = "async", required = false) boolean async,
                                   @RequestParam(value = "pageIndex", required = false, defaultValue = "0") int pageIndex,
                                   @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                                   Model model) {
        User user = (User) userDetailsService.loadUserByUsername(username);
        model.addAttribute("user", user);

        if (category != null) {

            System.out.print("category:" + category);
            System.out.print("selflink:" + "redirect:/u/" + username + "/blogs?category=" + category);
            return "/u";

        }
        Page<Blog> page = null;
        if (order.equals("hot")) { // 最热查询
            Sort sort = new Sort(Sort.Direction.DESC, "reading", "comments", "likes");
            Pageable pageable = new PageRequest(pageIndex, pageSize, sort);
            page = blogService.listBlogsByTitleLikeAndSort(user, keyword, pageable);
        }
        if (order.equals("new")) { // 最新查询
            Pageable pageable = new PageRequest(pageIndex, pageSize);
            page = blogService.listBlogsByTitleLike(user, keyword, pageable);
        }


        List<Blog> list = page.getContent();    // 当前所在页面数据列表

        model.addAttribute("order", order);
        model.addAttribute("page", page);
        model.addAttribute("blogList", list);
        return (async == true ? "/userspace/u :: #mainContainerRepleace" : "/userspace/u");
    }

    /**
     * 获取博客展示页面
     */
    @GetMapping("/{username}/blogs/{id}")
    @SystemControllerAnnotation(description = "获取博客展示页面")
    public String getBlogById(@PathVariable("username") String username, @PathVariable("id") Long id, Model model) {
        // 每次读取，简单的可以认为阅读量增加1次
        blogService.readingIncrease(id);
        boolean isBlogOwner = false;
        // 判断操作用户是否是博客的所有者
        if (SecurityContextHolder.getContext().getAuthentication() != null
                && SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
                && !SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().equals("anonymousUser")) {
            User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal != null && username.equals(principal.getUsername())) {
                isBlogOwner = true;
            }
        }
        model.addAttribute("isBlogOwner", isBlogOwner);
        model.addAttribute("blogModel", blogService.getBlogById(id));
        return "/userspace/blog";
    }

    /**
     * 删除博客
     */
    @DeleteMapping("/{username}/blogs/{id}")
    @PreAuthorize("authentication.name.equals(#username)")
    @SystemControllerAnnotation(description = "删除博客")
    public ResponseEntity<Response> deletBlog(@PathVariable("username") String username, @PathVariable("id") Long id) {
        try {
            blogService.removeBlog(id);
        } catch (Exception e) {
            return ResponseEntity.ok().body(new Response(false, e.getMessage()));
        }

        String redirectUrl = "/u/" + username + "/blogs";
        return ResponseEntity.ok().body(new Response(true, "处理成功！", redirectUrl));
    }

    /**
     * 获取新增博客的界面
     */
    @GetMapping("/{username}/blogs/edit")
    @SystemControllerAnnotation(description = "获取新增博客的界面")
    public ModelAndView createBlog(Model model) {
        model.addAttribute("blog", new Blog());
        model.addAttribute("fileServerUrl", fileServiceUrl);
        return new ModelAndView("/userspace/blogedit", "blogModel", model);
    }

    /**
     * 获取编辑博客的界面
     *
     * @param username
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/{username}/blogs/edit/{id}")
    @SystemControllerAnnotation(description = "编辑博客")
    public ModelAndView editBlog(@PathVariable("username") String username, @PathVariable("id") Long id, Model model) {
        model.addAttribute("blog", blogService.getBlogById(id));
        model.addAttribute("fileServerUrl", fileServiceUrl);
        return new ModelAndView("userspace/blogedit", "blogModel", model);
    }

    /**
     * 保存博客
     *
     * @param username
     * @param blog
     * @return
     */
    @PostMapping("/{username}/blogs/edit")
    @PreAuthorize("authentication.name.equals(#username)")
    @SystemControllerAnnotation(description = "保存博客")
    public ResponseEntity<Response> saveBlog(@PathVariable("username") String username, @RequestBody Blog blog) {
        User user = (User) userDetailsService.loadUserByUsername(username);
        blog.setUser(user);
        blog.setCreateTime(new Timestamp(new Date().getTime()));
        try {
            blogService.saveBlog(blog);
        } catch (ConstraintViolationException e) {
            return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new Response(false, e.getMessage()));
        }

        String redirectUrl = "/u/" + username + "/blogs/" + blog.getId();
        return ResponseEntity.ok().body(new Response(true, "处理成功", redirectUrl));
    }

}
