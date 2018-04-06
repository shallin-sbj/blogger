package com.blogger.blogger.controller;

import com.blogger.blogger.aop.SystemControllerAnnotation;
import com.blogger.blogger.domain.User;
import com.blogger.blogger.service.AuthorityService;
import com.blogger.blogger.service.UserService;
import com.blogger.blogger.utils.ConstraintViolationExceptionHandler;
import com.blogger.blogger.vo.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户控制器.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthorityService authorityService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    /**
     * 从 用户存储库 获取用户列表
     *
     * @return
     */
    private List<User> getUserlist() {
        Iterable<User> users = userService.queryAllUser();
        List<User> list = new ArrayList<>();
        users.forEach(user -> list.add(user));
        return list;
    }

    /**
     * 查询所用用户
     *
     * @return
     */
    @GetMapping
    @SystemControllerAnnotation(description = "查询所用用户")
    public ModelAndView list(@RequestParam(value = "async", required = false) boolean async,
                             @RequestParam(value = "pageIndex", required = false, defaultValue = "0") int pageIndex,
                             @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                             @RequestParam(value = "name", required = false, defaultValue = "") String name,
                             Model model) {

        Pageable pageable = new PageRequest(pageIndex, pageSize);

        Page<User> page = userService.listUsersByNameLike(name, pageable);
        List<User> list = page.getContent();    // 当前所在页面数据列表

        model.addAttribute("page", page);
        model.addAttribute("userList", list);
        return new ModelAndView(async == true ? "users/list :: #mainContainerRepleace" : "users/list", "userModel", model);
    }


    /**
     * 根据id查询用户
     *
     * @param
     * @return
     */
    @GetMapping("{id}")
    @SystemControllerAnnotation(description = "根据ID查询用户")
    public ModelAndView view(@PathVariable("id") Long id, Model model) {
        User user = userService.queryUserById(id);
        model.addAttribute("user", user);
        return new ModelAndView("users/edit", "userModel", model);
    }

    /**
     * 获取 form 表单页面
     *
     * @param
     * @return
     */
    @GetMapping("/add")
    @SystemControllerAnnotation(description = "获取 form 表单页面")
    public ModelAndView createForm(Model model) {
        model.addAttribute("user", new User(null,null,null,null));
        return new ModelAndView("users/add", "userModel", model);
    }

    /**
     * 新建用户
     *
     * @param
     * @param
     * @param user
     * @return
     */
    @PostMapping
    @SystemControllerAnnotation(description = "新建客户")
    public ResponseEntity<Response> create(User user, Long authorityId) {
        try {
            User addUser = userService.saveOrUpdateUser(user);
        } catch (ConstraintViolationException e) {
            Response response = new Response(false, ConstraintViolationExceptionHandler.getMessage(e));
            return ResponseEntity.ok().body(response);
        }
        return ResponseEntity.ok().body(new Response(true, "处理成功", user));
//        List<Authority> authorities = new ArrayList<>();
//        authorities.add(authorityService.getAuthorityById(authorityId));
//        user.setAuthorities(authorities);
//
//        if(user.getId() == null) {
//            user.setEncodePassword(user.getPassword()); // 加密密码
//        }else {
//            // 判断密码是否做了变更
//            User originalUser = userService.getUserById(user.getId());
//            String rawPassword = originalUser.getPassword();
//            PasswordEncoder  encoder = new BCryptPasswordEncoder();
//            String encodePasswd = encoder.encode(user.getPassword());
//            boolean isMatch = encoder.matches(rawPassword, encodePasswd);
//            if (!isMatch) {
//                user.setEncodePassword(user.getPassword());
//            }else {
//                user.setPassword(user.getPassword());
//            }
//        }
//
//        try {
//            userService.saveUser(user);
//        }  catch (ConstraintViolationException e)  {
//            return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
//        }
//
//        return ResponseEntity.ok().body(new Response(true, "处理成功", user));
    }

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    @SystemControllerAnnotation(description = "删除用户")
    public ResponseEntity<Response> delete(@PathVariable("id") Long id, Model model) {
        try {
            userService.deleteUserById(id);
        } catch (ConstraintViolationException e) {
            Response response = new Response(false, ConstraintViolationExceptionHandler.getMessage(e));
            return ResponseEntity.ok().body(response);
        }
        return  ResponseEntity.ok().body( new Response(true, "处理成功"));
    }

    /**
     * 修改用户
     *
     * @param
     * @return
     */
    @GetMapping(value = "edit/{id}")
    @SystemControllerAnnotation(description = "修改用户")
    public ModelAndView modifyForm(@PathVariable("id") Long id, Model model) {
        User user = userService.queryUserById(id);
        model.addAttribute("user", user);
        return new ModelAndView("users/edit", "userModel", model);
    }

}
