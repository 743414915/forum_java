package com.forum.controller;

import com.forum.entity.vo.ResponseVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/account")
public class AccountController extends ABaseController {

    @RequestMapping("/test")
    public ResponseVO test(Integer userId) {
        Map<String ,Object> test = new HashMap<>();
        test.put("name","张三");
        test.put("age",userId);
        Integer a = null;
        return getSuccessResponseVO(a.equals("test"));
    }
}
