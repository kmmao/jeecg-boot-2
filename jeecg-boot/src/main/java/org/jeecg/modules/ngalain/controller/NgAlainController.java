package org.jeecg.modules.ngalain.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.ngalain.service.NgAlainService;
import org.jeecg.modules.system.entity.SysDict;
import org.jeecg.modules.system.entity.SysUser;
import org.jeecg.modules.system.model.SysDictTree;
import org.jeecg.modules.system.service.ISysDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/sys/ng-alain")
public class NgAlainController {
    @Autowired
    private NgAlainService ngAlainService;
    @Autowired
    private ISysDictService sysDictService;

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
    /**
     * @功能：获取树形字典数据
     * @param sysDict
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/treeList", method = RequestMethod.GET)
    public Result<IPage<SysDict>> treeList(SysDict sysDict, @RequestParam(name="current", defaultValue="1") Integer pageNo,
                                           @RequestParam(name="pageSize", defaultValue="10") Integer pageSize, HttpServletRequest req) {
        Result<IPage<SysDict>> result = new Result<>();
        LambdaQueryWrapper<SysDict> query = new LambdaQueryWrapper<>();
        Page<SysDict> page = new Page<SysDict>(pageNo,pageSize);
        // 构造查询条件
        String dictName = sysDict.getDictName();
        if(oConvertUtils.isNotEmpty(dictName)) {
            query.like(true, SysDict::getDictName, dictName);
        }
        query.eq(true, SysDict::getDelFlag, "1");
        query.orderByDesc(true, SysDict::getCreateTime);
        IPage<SysDict> pageList = sysDictService.page(page, query);
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }
}