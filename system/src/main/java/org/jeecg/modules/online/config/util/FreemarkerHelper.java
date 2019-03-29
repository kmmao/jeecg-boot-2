package org.jeecg.modules.online.config.util;

import java.io.StringWriter;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
/**
 * 
 * @description:Freemarker引擎工具类
 * @author scott
 * @date 2019-03-22
 * @version V1.0
 */
public class FreemarkerHelper {
	private static Configuration _tplConfig = new Configuration(Configuration.VERSION_2_3_28);
	static{
		//模板对于数字超过1000，会自动格式为1,,000(禁止转换)
		_tplConfig.setNumberFormat("0.#####################");
		_tplConfig.setClassForTemplateLoading(FreemarkerHelper.class, "/");
	}

	/**
	 * 解析ftl
	 * @param tplName 模板名
	 * @param encoding 编码
	 * @param paras 参数
	 * @return
	 */
	public static String parseTemplate(String tplName, String encoding,
			Map<String, Object> paras) {
		try {
			StringWriter swriter = new StringWriter();
			Template mytpl = null;
			mytpl = _tplConfig.getTemplate(tplName, encoding);
			mytpl.process(paras, swriter);
			return swriter.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return e.toString();
		}

	}
	public static String parseTemplate(String tplName, Map<String, Object> paras) {
		return parseTemplate(tplName, "utf-8", paras);
	}
	
	public static void main(String[] args) {
		String html = FreemarkerHelper.parseTemplate("org/jeecg/modules/online/config/engine/tableTemplate.ftl", null);
		System.out.println(html);
	}
}