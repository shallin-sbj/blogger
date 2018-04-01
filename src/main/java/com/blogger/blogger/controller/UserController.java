package com.blogger.blogger.controller;

import com.blogger.blogger.domain.User;
import com.blogger.blogger.service.AuthorityService;
import com.blogger.blogger.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
     * @return
     */
    @GetMapping
    public ModelAndView list(@RequestParam(value="async",required=false) boolean async,
                             @RequestParam(value="pageIndex",required=false,defaultValue="0") int pageIndex,
                             @RequestParam(value="pageSize",required=false,defaultValue="10") int pageSize,
                             @RequestParam(value="name",required=false,defaultValue="") String name,
                             Model model) {

        Pageable pageable = new PageRequest(pageIndex, pageSize);

        Page<User> page = userService.listUsersByNameLike(name, pageable);
        List<User> list = page.getContent();	// 当前所在页面数据列表

        model.addAttribute("page", page);
        model.addAttribute("userList", list);
        return new ModelAndView(async==true?"users/list :: #mainContainerRepleace":"users/list", "userModel", model);
    }


    /**
     * 根据id查询用户
     *
     * @param
     * @return
     */
    @GetMapping("{id}")
    public ModelAndView view(@PathVariable("id") Long id, Model model) {
        User user =  userService.queryUserById(id);
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
    public ModelAndView createForm(Model model) {
        model.addAttribute("user", new User());
        return new ModelAndView("users/add", "userModel", model);
    }

    /**
     * 新建用户
     *
     * @param user
     * @param
     * @param
     * @return
     */
    @PostMapping
    public ModelAndView create(User user) {
        User addUser = userService.saveOrUpdateUser(user);
        return new ModelAndView("redirect:/users");
    }

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public ModelAndView delete(@PathVariable("id") Long id, Model model) {
       userService.deleteUserById(id);
        model.addAttribute("userList", getUserlist());
        return new ModelAndView("users/list", "userModel", model);
    }

    /**
     * 修改用户
     *
     * @param
     * @return
     */
    @GetMapping(value = "edit/{id}")
    public ModelAndView modifyForm(@PathVariable("id") Long id, Model model) {
        User user = userService.queryUserById(id);
        model.addAttribute("user", user);
        return new ModelAndView("users/edit", "userModel", model);
    }

}
