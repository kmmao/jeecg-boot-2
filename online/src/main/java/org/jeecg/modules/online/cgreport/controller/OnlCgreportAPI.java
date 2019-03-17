package org.jeecg.modules.online.cgreport.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.online.cgreport.entity.OnlCgreportHead;
import org.jeecg.modules.online.cgreport.entity.OnlCgreportItem;
import org.jeecg.modules.online.cgreport.service.IOnlCgreportHeadService;
import org.jeecg.modules.online.cgreport.service.IOnlCgreportItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: Controller
 * @Description: 在线报表配置
 * @author: jeecg-boot
 * @date: 2019-03-08
 * @version: V1.0
 */
@RestController
@RequestMapping("/online/cgreport/api")
public class OnlCgreportAPI {

	@Autowired
	private IOnlCgreportHeadService onlCgreportHeadService;

	@Autowired
	private IOnlCgreportItemService onlCgreportItemService;

	/**
	 * 通过 head code 获取 所有的 item，并生成 columns 所需的 json
	 * 
	 * @param code
	 *            head code
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/getColumns/{code}")
	public Result<?> getColumns(@PathVariable("code") String code) {

		QueryWrapper<OnlCgreportItem> queryWrapper = new QueryWrapper<OnlCgreportItem>();
		queryWrapper.eq("cgrhead_id", code);
		queryWrapper.eq("is_show", 1);
		queryWrapper.orderByAsc("order_num");

		List<OnlCgreportItem> list = onlCgreportItemService.list(queryWrapper);

		List<Map<String, String>> array = new ArrayList<Map<String, String>>();
		for (OnlCgreportItem item : list) {
			Map<String, String> column = new HashMap<String, String>(3);
			column.put("title", item.getFieldTxt());
			column.put("dataIndex", item.getFieldName());

			column.put("align", "center");

			array.add(column);
		}

		Map<String, Object> result = new HashMap<String, Object>(1);
		result.put("columns", array);

		return Result.ok(result);
	}

	/**
	 * 通过 head code 获取 sql语句，并执行该语句返回查询数据
	 * 
	 * @param code
	 * @return
	 */
	@GetMapping(value = "/getData/{code}")
	public Result<?> getData(@PathVariable("code") String code) {
		OnlCgreportHead head = onlCgreportHeadService.getById(code);
		if (head == null) {
			return Result.error("实体不存在");
		}
		String sql = head.getCgrSql();
		try {
			List<Map<?, ?>> listMap = onlCgreportHeadService.executeSelete(sql);
			return Result.ok(listMap);
		} catch (Exception e) {
			return Result.error("SQL执行失败：" + e.getMessage());
		}

	}

}
