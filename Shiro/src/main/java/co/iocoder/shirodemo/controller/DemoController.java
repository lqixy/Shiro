package co.iocoder.shirodemo.controller;

import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @RequiresGuest
    @GetMapping("/echo")
    public String demo(){
        return "示例";
    }

    @GetMapping("/home")
    public String home(){
        return "首页";
    }

    @RequiresRoles("ADMIN")
    @GetMapping("/admin")
    public String admin(){
        return "管理员";
    }

    @RequiresRoles("NORMAL")
    @GetMapping("/normal")
    public String normal() {
        return "我是普通用户";
    }
}
