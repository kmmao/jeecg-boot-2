package org.jeecg.modules.ngalain.service;

import com.alibaba.fastjson.JSONArray;

public interface NgAlainService {
    public JSONArray getMenu(String id) throws Exception;
    public JSONArray getJeecgMenu(String id) throws Exception;
}
