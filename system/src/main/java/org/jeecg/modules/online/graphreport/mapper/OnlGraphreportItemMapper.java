package org.jeecg.modules.online.graphreport.mapper;

import java.util.List;
import org.jeecg.modules.online.graphreport.entity.OnlGraphreportItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 图表报告项
 * @author:jeecg-boot
 * @date: 2019-04-10
 * @version:V1.0
 */
public interface OnlGraphreportItemMapper extends BaseMapper<OnlGraphreportItem> {

	/**
	 * 根据父id删除子表
	 * 
	 * @param mainId
	 * @return
	 */
	public boolean deleteByMainId(String mainId);

	/**
	 * 根据父id查询子表
	 * 
	 * @param mainId
	 * @return
	 */
	public List<OnlGraphreportItem> selectByMainId(String mainId);
}
