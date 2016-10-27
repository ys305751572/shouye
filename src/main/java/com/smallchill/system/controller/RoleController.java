/**
 * Copyright (c) 2015-2016, Chill Zhuang 庄骞 (smallchill@163.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.smallchill.system.controller;

import com.smallchill.system.model.Demo;
import com.smallchill.system.model.RoleGroup;
import com.smallchill.system.service.DemoService;
import com.smallchill.system.service.RoleGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smallchill.common.base.BaseController;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.shiro.ShiroKit;
import com.smallchill.core.toolbox.Func;
import com.smallchill.core.toolbox.Record;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.kit.CacheKit;
import com.smallchill.core.toolbox.kit.JsonKit;
import com.smallchill.core.toolbox.kit.StrKit;
import com.smallchill.system.meta.intercept.RoleIntercept;
import com.smallchill.system.model.Role;
import com.smallchill.system.service.RoleService;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/role")
public class RoleController extends BaseController{
	private static String LIST_SOURCE = "Role.list";
	private static String BASE_PATH = "/system/role/";
	private static String CODE = "role";
	private static String PERFIX = "tfw_role";
	
	@Autowired
	RoleService service;

	@Autowired
	DemoService demoService;

	@Autowired
	RoleGroupService roleGroupService;

	@RequestMapping("/")
	public String index(ModelMap mm, Model model) {
		List<Demo> demoList = demoService.findAll();
		mm.put("demoList", JsonKit.toJson(demoList));
		mm.put("code", CODE);
		return BASE_PATH + "role.html";
	}
	
	@ResponseBody
	@RequestMapping(KEY_LIST)
	public Object list() {
		return paginate(LIST_SOURCE, new RoleIntercept());
	}
	
	@RequestMapping(KEY_ADD)
	public String add(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "role_add.html";
	}
	
	@RequestMapping(KEY_ADD + "/{id}")
	public String add(@PathVariable String id, ModelMap mm) {
		if (StrKit.notBlank(id)) {
			mm.put("pId", id);
			mm.put("num", service.findLastNum(id));
		}
		mm.put("code", CODE);
		return BASE_PATH + "role_add.html";
	}
	
	@RequestMapping(KEY_EDIT + "/{id}")
	public String edit(@PathVariable String id, ModelMap mm) {
		Role role = service.findById(id);
		mm.put("model", JsonKit.toJson(role));
		mm.put("code", CODE);
		return BASE_PATH + "role_edit.html";
	}

	@RequestMapping(KEY_VIEW + "/{id}")
	public String view(@PathVariable String id, ModelMap mm) {
		Role role = service.findById(id);
		Role parent = service.findById(role.getPid());
		String pName = (null == parent) ? "" : parent.getName();
		Record rd = Record.parse(role);
		rd.set("deptName", Func.getDeptName(role.getDeptid()))
			.set("pName", pName);
		mm.put("model", JsonKit.toJson(rd));
		mm.put("code", CODE);
		return BASE_PATH + "role_view.html";
	}

	@RequestMapping("/demo")
	public String demo(ModelMap mm) {
		List<Demo> demoList = demoService.findAll();
		mm.put("demoList", JsonKit.toJson(demoList));
		mm.put("code", CODE);
		return BASE_PATH + "role_demo.html";
	}


	@ResponseBody
	@RequestMapping("/demo_save")
	public AjaxResult demo_save(String vals) {
		String[] ss = JsonKit.parse(vals,String[].class);
		boolean index = true;
		for(String s : ss){
			Demo demo = new Demo();
			demo.setGroups(s);
			boolean temp = demoService.save(demo);
			if(!temp){
				index = false;
			}
		}
		if (index) {
			return success(SAVE_SUCCESS_MSG);
		} else {
			return error(SAVE_FAIL_MSG);
		}
	}

	@ResponseBody
	@RequestMapping("/demo_del")
	public AjaxResult demo_del(Integer id) {
		if(id==null) return error(DEL_FAIL_MSG);
		List<RoleGroup> roleGroups = roleGroupService.findBy("groupid = "+id,RoleGroup.class);
		for(RoleGroup roleGroup : roleGroups){
			roleGroupService.delete(roleGroup.getId());
		}
		Integer index = demoService.delete(id);
		if (index!=0) {
			return success(DEL_SUCCESS_MSG);
		} else {
			return error(DEL_FAIL_MSG);
		}
	}


	@ResponseBody
	@RequestMapping("/groups_del")
	public AjaxResult groups_del(String group,Integer roleid) {
		Demo demo = demoService.findFirstBy("groups = '"+group.trim()+"'",Demo.class);
		RoleGroup roleGroup = roleGroupService.findFirstBy("groupid = "+demo.getId() +" AND roleid = "+roleid,RoleGroup.class);
		Integer index = roleGroupService.delete(roleGroup.getId());
		if (index!=0) {
			return success(DEL_SUCCESS_MSG);
		} else {
			return error(DEL_FAIL_MSG);
		}
	}
	@ResponseBody
	@RequestMapping("/groups_add")
	public AjaxResult groups_add(Integer groupid,Integer roleid) {
		RoleGroup roleGroup = new RoleGroup();
		List<RoleGroup> _r = roleGroupService.findBy("groupid = "+groupid +" AND roleid = "+roleid,RoleGroup.class);
		if(!_r.isEmpty()){
			return error("已存在");
		}
		roleGroup.setGroupid(groupid);
		roleGroup.setRoleid(roleid);
		boolean index = roleGroupService.save(roleGroup);
		if (index) {
			return success(SAVE_SUCCESS_MSG);
		} else {
			return error(SAVE_FAIL_MSG);
		}
	}




	@RequestMapping("/authority/{roleId}/{roleName}")
	public String authority(@PathVariable String roleId, @PathVariable String roleName, ModelMap mm) {
		if(!ShiroKit.hasAnyRoles(ConstShiro.ADMINISTRATOR + "," + ConstShiro.ADMIN)){
			return "redirect:/unauth";
		}
		mm.put("roleId", roleId);
		mm.put("roleName", Func.decodeUrl(roleName));
		return BASE_PATH + "role_authority.html";
	}
	
	@ResponseBody
	@RequestMapping("/saveAuthority")
	public AjaxResult saveAuthority(@RequestParam String ids, @RequestParam String roleId) {
		String[] id = ids.split(",");
		if (id.length <= 1) {
			CacheKit.removeAll(ROLE_CACHE);
			CacheKit.removeAll(MENU_CACHE);
			return success("设置成功");
		}
		boolean temp = service.saveAuthority(ids, roleId);
		if (temp) {
			CacheKit.removeAll(ROLE_CACHE);
			CacheKit.removeAll(MENU_CACHE);
			return success("设置成功");
		} else {
			return error("设置失败");
		}
	}
	
	@ResponseBody
	@RequestMapping(KEY_SAVE)
	public AjaxResult save() {
		Role role = mapping(PERFIX, Role.class);
		boolean temp = service.save(role);
		if (temp) {
			CacheKit.removeAll(ROLE_CACHE);
			CacheKit.removeAll(MENU_CACHE);
			return success(SAVE_SUCCESS_MSG);
		} else {
			return error(SAVE_FAIL_MSG);
		}
	}


	@ResponseBody
	@RequestMapping(KEY_UPDATE)
	public AjaxResult update() {
		Role role = mapping(PERFIX, Role.class);
		role.setVersion(getParameterToInt("VERSION", 0) + 1);
		boolean temp = service.update(role);
		if (temp) {
			CacheKit.removeAll(ROLE_CACHE);
			CacheKit.removeAll(MENU_CACHE);
			return success(UPDATE_SUCCESS_MSG);
		} else {
			return error(UPDATE_FAIL_MSG);
		}
	}
	
	@ResponseBody
	@RequestMapping(KEY_REMOVE)
	public AjaxResult remove(@RequestParam String ids) {
		int cnt = service.deleteByIds(ids);
		if (cnt > 0) {
			CacheKit.removeAll(ROLE_CACHE);
			CacheKit.removeAll(MENU_CACHE);
			return success(DEL_SUCCESS_MSG);
		} else {
			return error(DEL_FAIL_MSG);
		}
	}

	@ResponseBody
	@RequestMapping("/getPowerById")
	public AjaxResult getPowerById(@RequestParam String id) {
		int cnt = service.getParentCnt(id);
		if (cnt > 0) {
			return success("success");
		} else {
			return error("error");
		}
	}
	
}
