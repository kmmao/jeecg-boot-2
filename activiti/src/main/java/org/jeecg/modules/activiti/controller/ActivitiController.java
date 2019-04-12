package org.jeecg.modules.activiti.controller;

import org.activiti.api.task.runtime.TaskRuntime;
import org.apache.catalina.security.SecurityUtil;
import org.jeecg.modules.activiti.service.IActivitiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ActivitiController {
    @Autowired
    private TaskRuntime taskRuntime;


    @Autowired
    private IActivitiService activitiService;

}
