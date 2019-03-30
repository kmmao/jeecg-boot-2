package org.jeecg.modules.demo.mock;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MockController {

	/**
	 * 通用json访问接口
	 * 格式： http://localhost:8080/jeecg-boot/api/json/{filename}
	 * @param filename
	 * @return
	 */
	@RequestMapping(value = "/json/{filename}", method = RequestMethod.GET)
	public String getJsonData(@PathVariable String filename) {
		String jsonpath = "classpath:org/jeecg/modules/demo/mock/json/"+filename+".json";
		return readJson(jsonpath);
	}
	
	@GetMapping(value = "/user")
	public String user() {
		return readJson("classpath:org/jeecg/modules/demo/mock/json/user.json");
	}
	
	/**
	 * 老的登录获取用户信息接口
	 * @return
	 */
	@GetMapping(value = "/user/info")
	public String userInfo() {
		return readJson("classpath:org/jeecg/modules/demo/mock/json/user_info.json");
	}

	@GetMapping(value = "/role")
	public String role() {
		return readJson("classpath:org/jeecg/modules/demo/mock/json/role.json");
	}

	@GetMapping(value = "/service")
	public String service() {
		return readJson("classpath:org/jeecg/modules/demo/mock/json/service.json");
	}

	@GetMapping(value = "/permission")
	public String permission() {
		return readJson("classpath:org/jeecg/modules/demo/mock/json/permission.json");
	}

	@GetMapping(value = "/permission/no-pager")
	public String permission_no_page() {
		return readJson("classpath:org/jeecg/modules/demo/mock/json/permission_no_page.json");
	}
	
	/**
	 * 省市县
	 */
	@GetMapping(value = "/area")
	public String area() {
		return readJson("classpath:org/jeecg/modules/demo/mock/json/area.json");
	}
	
	//-------------------------------------------------------------------------------------------
	/**
	 * 工作台首页的数据
	 * @return
	 */
	@GetMapping(value = "/list/search/projects")
	public String projects() {
		return readJson("classpath:org/jeecg/modules/demo/mock/json/workplace_projects.json");
	}

	@GetMapping(value = "/workplace/activity")
	public String activity() {
		return readJson("classpath:org/jeecg/modules/demo/mock/json/workplace_activity.json");
	}
	
	@GetMapping(value = "/workplace/teams")
	public String teams() {
		return readJson("classpath:org/jeecg/modules/demo/mock/json/workplace_teams.json");
	}
	
	@GetMapping(value = "/workplace/radar")
	public String radar() {
		return readJson("classpath:org/jeecg/modules/demo/mock/json/workplace_radar.json");
	}
	
	@GetMapping(value = "/task/process")
	public String taskProcess() {
		return readJson("classpath:org/jeecg/modules/demo/mock/json/task_process.json");
	}
	//-------------------------------------------------------------------------------------------
	
	//author:lvdandan-----date：20190315---for:添加数据日志json----
	public String sysDataLogJson() {
		return readJson("classpath:org/jeecg/modules/demo/mock/json/sysdatalog.json");
	}
	//author:lvdandan-----date：20190315---for:添加数据日志json----
	
	/**
	 * 读取json格式文件
	 * @param jsonSrc
	 * @return
	 */
	private String readJson(String jsonSrc) {
		String json = "";
		try {
			//File jsonFile = ResourceUtils.getFile(jsonSrc);
			//json = FileUtils.re.readFileToString(jsonFile);
			//换个写法，解决springboot读取jar包中文件的问题
			InputStream stream = getClass().getClassLoader().getResourceAsStream(jsonSrc.replace("classpath:", ""));
			json = IOUtils.toString(stream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json;
	}

}
