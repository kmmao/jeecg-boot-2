package org.jeecg.common.jsonschema;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonschemaUtil {
	
	/**
	 * 生成JsonSchema
	 * 
	 * @param descrip
	 * @param propertyList
	 * @return
	 */
	public static JSONObject getJsonSchema(JsonSchemaDescrip descrip, List<CommonProperty> propertyList) {
		JSONObject obj = new JSONObject();
		obj.put("$schema", descrip.get$schema());
		obj.put("type", descrip.getType());
		obj.put("title", descrip.getTitle());

		List<String> requiredArr = descrip.getRequired();
		obj.put("required", requiredArr);

		JSONObject properties = new JSONObject();
		for (CommonProperty commonProperty : propertyList) {
			Map<String, Object> map = commonProperty.getPropertyJson();
			properties.put(map.get("key").toString(), map.get("prop"));
		}
		obj.put("properties", properties);
		log.info("---JSONSchema--->"+obj.toString());
		return obj;
	}

}
