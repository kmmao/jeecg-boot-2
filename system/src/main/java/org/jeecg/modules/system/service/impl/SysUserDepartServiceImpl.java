package org.jeecg.modules.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.jeecg.modules.system.entity.SysDepart;
import org.jeecg.modules.system.entity.SysUserDepart;
import org.jeecg.modules.system.mapper.SysUserDepartMapper;
import org.jeecg.modules.system.model.DepartIdModel;
import org.jeecg.modules.system.model.SysUserDepartsVO;
import org.jeecg.modules.system.service.ISysDepartService;
import org.jeecg.modules.system.service.ISysUserDepartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * <P>
 * 用户部门表实现类
 * <p/>
 * @author ZhiLin
 *@since 2019-02-22
 */
@Service
public class SysUserDepartServiceImpl extends ServiceImpl<SysUserDepartMapper, SysUserDepart> implements ISysUserDepartService {
	
	@Autowired
	private ISysUserDepartService userDepartService;
	@Autowired
	private ISysDepartService sysDepartService;

	/**
	 *根据用户id添加部门信息
	 */
	@Override
	public boolean addSysUseWithrDepart(SysUserDepartsVO sysUserDepartsVO) {
		LambdaQueryWrapper<SysUserDepart> query = new LambdaQueryWrapper<SysUserDepart>();
		if(sysUserDepartsVO != null) {
			String userId = sysUserDepartsVO.getUserId();
			List<String> departIdList = sysUserDepartsVO.getDepartIdList();
			if(departIdList != null && departIdList.size() > 0) {
				for(String depId : departIdList) {
					query.eq(SysUserDepart::getDepId, depId);
					query.eq(SysUserDepart::getUserId, userId);
					List<SysUserDepart> uDepList = userDepartService.list(query);
					if(uDepList == null || uDepList.size() == 0) {
						userDepartService.save(new SysUserDepart("",userId,depId));
					}
				}
			}
			return true;
		}else {
			return false;
		}
		
	}

	/**
	 * 根据用户id查询部门信息
	 */
	@Override
	public List<DepartIdModel> queryDepartIdsOfUser(String userId) {
		LambdaQueryWrapper<SysUserDepart> queryUDep = new LambdaQueryWrapper<SysUserDepart>();
		LambdaQueryWrapper<SysDepart> queryDep = new LambdaQueryWrapper<SysDepart>();
		try {
			queryUDep.eq(SysUserDepart::getUserId, userId);
			List<String> depIdList = new ArrayList<>();
			List<DepartIdModel> depIdModelList = new ArrayList<>();
			List<SysUserDepart> userDepList = userDepartService.list(queryUDep);
			System.out.println("userDepList=============================>>>>" + userDepList.size());
			if(userDepList != null && userDepList.size() > 0) {
			for(SysUserDepart userDepart : userDepList) {
					depIdList.add(userDepart.getDepId());
				}
			queryDep.in(SysDepart::getId, depIdList);
			List<SysDepart> depList = sysDepartService.list(queryDep);
			if(depList != null || depList.size() > 0) {
				for(SysDepart depart : depList) {
					System.out.println("depart=========================>>>" + depart.getId());
					depIdModelList.add(new DepartIdModel().convertByUserDepart(depart));
				}
			}
			return depIdModelList;
			}
		}catch(Exception e) {
			e.fillInStackTrace();
			System.out.println("捕获的的异常============================>>>"+e.getMessage());
		}
		return null;
		
		
	}

	/**
	 * 根据用户id修改部门信息
	 */
	@Override
	public boolean editSysUserWithDepart(SysUserDepartsVO sysUserDepartsVO) {
		LambdaQueryWrapper<SysUserDepart> queryDep = new LambdaQueryWrapper<SysUserDepart>();
		List<String> depIdList = sysUserDepartsVO.getDepartIdList();
		if(depIdList != null && depIdList.size() > 0) {
			queryDep.eq(SysUserDepart::getUserId, sysUserDepartsVO.getUserId());	
			boolean ok = userDepartService.remove(queryDep);
			if(ok) {
				for(String str : depIdList) {
					userDepartService.save(new SysUserDepart("", sysUserDepartsVO.getUserId(), str));
				}
			return ok;
			}
		}
		queryDep.eq(SysUserDepart::getUserId, sysUserDepartsVO.getUserId());
		boolean ok = userDepartService.remove(queryDep);
		return ok;
	}
	
}
