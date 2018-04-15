package com.blogger.blogger.controller;

import com.blogger.blogger.domain.User;
import com.blogger.blogger.service.UserService;
import com.blogger.blogger.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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



    /**
     * 获取个人信息
     *
     * @param username
     * @param model
     * @return
     */
    @GetMapping("/{username}/profile")
    @PreAuthorize("authentication.name.equals(#username)")
    public ModelAndView profile(@PathVariable("username") String username, Model model) {
        User user = (User) userDetailsService.loadUserByUsername(username);
        model.addAttribute("user", user);
        return new ModelAndView("/userspace/profile", "userModel", model);
    }

    /**
     * 保存个人设置
     *  这里因为请求的url 中默认带:/u/adim/profile 所以说被拦截了
     * @param user
     * @return
     */
    @PostMapping("/{username}/profile")
    @PreAuthorize("authentication.name.equals(#username)")
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
    public ResponseEntity<Response> saveAvatar(@PathVariable("username") String username, @RequestBody User user) {
        String avatarUrl = user.getAvatar();

        User originalUser = userService.queryUserById(user.getId());
        originalUser.setAvatar(avatarUrl);
        userService.saveOrUpdateUser(originalUser);

        return ResponseEntity.ok().body(new Response(true, "处理成功", avatarUrl));
    }


//    @GetMapping("/{username}")
//    public String userSpace(@PathVariable("username") String username) {
//        System.out.println("username:" + username);
//        return "/userspace/u";
//    }
//
//    /**
//     * @param username
//     * @param order    热门程度，创建时间
//     * @param category 类型
//     * @param keyword  关键字
//     * @return
//     */
//    @GetMapping("/{username}/blogs")
//    public String listBlogsByOrder(@PathVariable("username") String username,
//                                   @RequestParam(value = "order", required = false, defaultValue = "new") String order,
//                                   @RequestParam(value = "category", required = false, defaultValue = "new") Long category,
//                                   @RequestParam(value = "keyword", required = false) String keyword) {
//
//        System.out.println("username:" + username + "category:" + category + "keyword:" + keyword + "order:" + order);
//
//        if (category != null) {
//            return "/userspace/u";
//        } else if (!StringUtils.isEmpty(keyword)) {
//            return "/userspace/u";
//        }
//        return "/userspace/u";
//    }
//
//    @GetMapping("/{username}/blogs/{id}")
//    public String listBlogsByOrder(@PathVariable("id") Long id) {
//        System.out.println("id:" + id);
//
//        return "/userspace/blogs/";
//    }
//
//    @GetMapping("/{username}/blogs/edit")
//    public String editBlog() {
//        return "/userspace/blogedit";
//    }
}
