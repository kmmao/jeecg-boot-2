package org.jeecg.modules.online.graphreport.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.online.graphreport.entity.OnlGraphreportHead;
import org.jeecg.modules.online.graphreport.service.IOnlGraphreportHeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Title: Controller
 * @Description: 图表报告
 * @author:jeecg-boot
 * @date: 2019-04-11
 * @version:V1.0
 */
@RestController
@RequestMapping("/online/graphreport/api")
// @Slf4j
public class OnlGraphreportApiController {

	@Autowired
	private IOnlGraphreportHeadService headService;

	/**
	 * 获取图表数据
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/getChartsData")
	public Result<?> getChartsData(@RequestParam(name = "id", required = true) String id) {
		OnlGraphreportHead head = headService.getById(id);
		if (head == null) {
			return Result.error("实体不存在");
		} else {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("head", head);
			String dataType = "sql";
			if (dataType.equals(head.getDataType())) {
				String sql = head.getCgrSql();
				// 执行SQL语句
				List<Map<?, ?>> listMap = headService.executeSelete(sql);
				resultMap.put("data", listMap);
			}
			return Result.ok(resultMap);
		}
	}

}
