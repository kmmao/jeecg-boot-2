package org.jeecg.modules.ngalain.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.ngalain.service.NgAlainService;
import org.jeecg.modules.system.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/sys/ng-alain")
public class NgAlainController {
    @Autowired
    private NgAlainService ngAlainService;

    @RequestMapping(value = "/getAppData")
    @ResponseBody
    public JSONObject getAppData(HttpServletRequest request) throws Exception {
       String token=request.getHeader("X-Access-Token");
        JSONObject j = new JSONObject();
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        JSONObject userObjcet = new JSONObject();
        userObjcet.put("name", user.getUsername());
        userObjcet.put("avatar", "./assets/tmp/img/avatar.jpg");
        userObjcet.put("email", user.getEmail());
        userObjcet.put("token", token);
        j.put("user", userObjcet);
        j.put("menu",ngAlainService.getMenu(user.getUsername()));
        JSONObject app = new JSONObject();
        app.put("name", "jee-ngAlain");
        app.put("description", "jeecg+ng-alain整合版本");
        j.put("app", app);
        return j;
    }
}
