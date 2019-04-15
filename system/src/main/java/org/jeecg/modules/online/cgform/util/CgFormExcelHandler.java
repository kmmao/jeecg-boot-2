package org.jeecg.modules.online.cgform.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeecg.modules.online.cgform.entity.OnlCgformField;
import org.jeecgframework.poi.handler.impl.ExcelDataHandlerDefaultImpl;
import org.jeecgframework.poi.util.PoiPublicUtil;

public class CgFormExcelHandler extends ExcelDataHandlerDefaultImpl {

	Map<String, OnlCgformField> fieldMap;

	public CgFormExcelHandler(List<OnlCgformField> lists) {
		fieldMap = convertDate(lists);
	}

	private Map<String, OnlCgformField> convertDate(List<OnlCgformField> lists) {
		Map<String, OnlCgformField> maps = new HashMap<String, OnlCgformField>();

		for (OnlCgformField cgFormFieldEntity : lists) {
			maps.put(cgFormFieldEntity.getDbFieldTxt(), cgFormFieldEntity);
		}
		return maps;
	}


	@Override
	public void setMapValue(Map<String, Object> map, String originKey, Object value) {
		if (value instanceof Double) {
			map.put(getRealKey(originKey), PoiPublicUtil.doubleToString((Double) value));
		} else {
			map.put(getRealKey(originKey), value.toString());
		}
	}
	private String getRealKey(String originKey) {
		if (fieldMap.containsKey(originKey)) {
			//主表字段
			return "$mainTable$"+fieldMap.get(originKey).getDbFieldName();
		}
		//子表字段
		return "$subTable$"+originKey;
	}

}