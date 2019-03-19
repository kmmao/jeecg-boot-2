package org.jeecg.modules.system.aspect;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.system.aspect.annotation.Dict;
import org.jeecg.modules.system.service.ISysDictService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: jeecg-boot
 * @Package: org.jeecg.common.aspect
 * @ClassName: DictAspect
 * @Description: 字典aop类
 * @Author: dangzhenghui
 * @CreateDate: 2019-3-17 21:50
 * @UpdateUser: 更新者
 * @UpdateDate: 2019-3-17 21:50
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
@Aspect
@Component
public class DictAspect {
    private static final Logger logger = LoggerFactory.getLogger(DictAspect.class);
    @Autowired
    private ISysDictService dictService;
    // 定义切点Pointcut
    @Pointcut("execution(public * org.jeecg.modules.*.*.*Controller.*(..))")
    public void excudeService() {
    }

    @Around("excudeService()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        Object result = pjp.proceed();
        parseDictText(result);
        return result;
    }

    /**
     * 本方法针对返回对象为Result 的IPage的分页列表数据进行动态字典注入
     * 字典注入实现 通过对实体类添加注解@dict 来标识需要的字典内容,字典分为单字典code即可 ，table字典 code table text配合使用与原来jeecg的用法相同
     * 示例为SysUser   字段为sex 添加了注解@Dict(dicCode = "sex") 会在字典服务立马查出来对应的text 然后在请求list的时候将这个字典text，已字段名称加_dictText形式返回到前端
     * 例输入当前返回值的就会多出一个sex_dictText字段
     * {
     *      sex:1,
     *      sex_dictText:"男"
     * }
     * 前端直接取值sext_dictText在table里面无需再进行前端的字典转换了
     *  customRender:function (text) {
     *               if(text==1){
     *                 return "男";
     *               }else if(text==2){
     *                 return "女";
     *               }else{
     *                 return text;
     *               }
     *             }
     *             目前vue是这么进行字典渲染到table上的多了就很麻烦了 这个直接在服务端渲染完成前端可以直接用
     * @param result
     */
    private void parseDictText(Object result) {
        if (result instanceof Result) {
            if (((Result) result).getResult() instanceof IPage) {
                List<JSONObject> items = new ArrayList<>();
                for (Object record : ((IPage) ((Result) result).getResult()).getRecords()) {
                    ObjectMapper mapper = new ObjectMapper();
                    String json="{}";
                    try {
                        //解决@JsonFormat注解解析不了的问题详见SysAnnouncement类的@JsonFormat
                         json = mapper.writeValueAsString(record);
                    } catch (JsonProcessingException e) {
                        logger.error("json解析失败"+e.getMessage());
                        e.printStackTrace();
                    }
                    JSONObject item = JSONObject.parseObject(json);
                    for (Field field : record.getClass().getDeclaredFields()) {
                        if (field.getAnnotation(Dict.class) != null) {
                            String code = field.getAnnotation(Dict.class).dicCode();
                            String text = field.getAnnotation(Dict.class).dicText();
                            String table = field.getAnnotation(Dict.class).dictTable();
                            String key = String.valueOf(item.get(field.getName()));
                            String textValue=null;
                            if (!StringUtils.isEmpty(table)){
                                textValue= dictService.queryTableDictTextByKey(table,text,code,key);
                            }else {
                                textValue = dictService.queryDictTextByKey(code, key);
                            }
                            item.put(field.getName() + "_dictText", textValue);
                        }
                    }
                    items.add(item);
                }
                ((IPage) ((Result) result).getResult()).setRecords(items);
            }

        }
    }

}
