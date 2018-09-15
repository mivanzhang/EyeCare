package com.aiyan.product.controller;

import com.aiyan.product.bean.Query;
import com.aiyan.product.bean.School;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
public class HelloController {

    @RequestMapping("/hello")
    public String index() {
        return "Hello World";
    }

    @RequestMapping("/")
    public String index(ModelMap map) {
        // 加入一个属性，用来在模板中读取
        map.addAttribute("host", "http://blog.didispace.com");
        map.addAttribute("today", new Date());
        // return模板文件的名称，对应src/main/resources/templates/index.html
        return "index";
    }



    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public String query(ModelMap map, @ModelAttribute(value = "query") Query query) {
        // 加入一个属性，用来在模板中读取
        // return模板文件的名称，对应src/main/resources/templates/index.html
        return "index";
    }

    @RequestMapping(value = "/getSmsCode", method = RequestMethod.GET)
    public String sendSMSCode(ModelMap map, String managerPhoneNumber) {
        // 加入一个属性，用来在模板中读取
        // return模板文件的名称，对应src/main/resources/templates/index.html
        return "index";
    }
}
