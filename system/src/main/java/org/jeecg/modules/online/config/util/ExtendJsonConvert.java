package org.jeecg.modules.online.config.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.jeecg.common.util.oConvertUtils;

import com.alibaba.fastjson.JSONObject;


/**
 * 
 * 将json转换为普通的html样式
 */
public class ExtendJsonConvert {
	
	//json转换中的系统保留字
	protected static Map<String,String> syscode = new HashMap<String,String>();
	static{
		syscode.put("class", "clazz");
	}
	
	//TODO 
	/*public static String json2Html(String json){
		return extendAttribute(json);
	}*/
	
//	/**
//	 * 生成扩展属性
//	 * @param field
//	 * @return
//	 */
//	private static String extendAttribute(String field) {
//		StringBuffer sb = new StringBuffer();
//		//增加扩展属性
//		if (!StringUtils.isBlank(field)) {
//			Gson gson = new Gson();
//			Map<String, String> mp = gson.fromJson(field, Map.class);
//			for(Map.Entry<String, String> entry: mp.entrySet()) { 
//				sb.append(entry.getKey()+"=" + gson.toJson(entry.getValue())+" ");
//			} 
//		}
//		return sb.toString();
//	}
	
/*	public static void json2HtmlForList(List<Map<String,Object>> list,String kye){
		if(list!=null && list.size()>0){
			for(Map<String,Object> map : list){
				json2HtmlForMap(map,kye);
			}
		}
	}*/
	
	
/*	public static void json2HtmlForMap(Map<String,Object> map,String kye){
		String extendJson = (String) map.get(kye);
		map.put(kye, ExtendJsonConvert.json2Html(extendJson));
	}*/
	
	/**
	 * 生成扩展属性
	 * @param field
	 * @return
	 */
	/*private static String extendAttribute(String field) {
		if(oConvertUtils.isEmpty(field)){
			
			return "";
		}
		field = dealSyscode(field,1);
		StringBuilder re = new StringBuilder();
		try{
			JSONObject obj = JSONObject.parseObject(field);
			
			Iterator it = obj.keys();
			while(it.hasNext()){
				String key = String.valueOf(it.next());
				JSONObject nextObj = null;
				try {
					 nextObj =((JSONObject)obj.get(key));
					 Iterator itvalue =nextObj.keys();
						re.append(key+"="+"\"");
						if(nextObj.size()<=1){
							String onlykey = String.valueOf(itvalue.next());
							if("value".equals(onlykey)){
								re.append(nextObj.get(onlykey)+"");
							}else{
								re.append(onlykey+":"+nextObj.get(onlykey)+"");
							}
						}else{
							while(itvalue.hasNext()){
								String multkey = String.valueOf(itvalue.next());
								String multvalue = nextObj.getString(multkey);
								re.append(multkey+":"+multvalue+",");
							}
							re.deleteCharAt(re.length()-1);
						}
						re.append("\" ");
				} catch (Exception e) {
					//add-begin--Author:Yandong  Date:20180521 for：TASK #2723 【bug】online扩展参数用法问题
					re.append(key+"="+"\"");
					re.append(obj.get(key)+"\" ");//为了正则匹配 加上空格
					//re.append("\" ");
					//add-end--Author:Yandong  Date:20180521 for：TASK #2723 【bug】online扩展参数用法问题
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		return dealSyscode(re.toString(), 2);
	}*/
	
	/**
	 * 处理否含有json转换中的保留字
	 * @param field
	 * @param flag 1:转换 2:还原
	 * @return
	 */
	private static String dealSyscode(String field,int flag) {
		String change = field;
		Iterator it = syscode.keySet().iterator();
		while(it.hasNext()){
			String key = String.valueOf(it.next());
			String value = String.valueOf(syscode.get(key));
			if(flag==1){
				change = field.replaceAll(key, value);
			}else if(flag==2){
				change = field.replaceAll(value, key);
			}
		}
		return change;
	}
}
