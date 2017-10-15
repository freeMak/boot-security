package com.boot.security.server.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Maps;
import com.zw.admin.server.annotation.LogAnnotation;
import com.zw.admin.server.dao.RoleDao;
import com.zw.admin.server.dto.RoleDto;
import com.zw.admin.server.model.Role;
import com.zw.admin.server.page.table.PageTableRequest;
import com.zw.admin.server.page.table.PageTableHandler;
import com.zw.admin.server.page.table.PageTableResponse;
import com.zw.admin.server.page.table.PageTableHandler.CountHandler;
import com.zw.admin.server.page.table.PageTableHandler.ListHandler;
import com.zw.admin.server.service.RoleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 角色相关接口
 * 
 * @author 小威老师
 *
 */
@Api(tags = "角色")
@RestController
@RequestMapping("/roles")
public class RoleController {

	@Autowired
	private RoleService roleService;
	@Autowired
	private RoleDao roleDao;

	@LogAnnotation
	@PostMapping
	@ApiOperation(value = "保存角色")
	@RequiresPermissions("sys:role:add")
	public void saveRole(@RequestBody RoleDto roleDto) {
		roleService.saveRole(roleDto);
	}

	@GetMapping
	@ApiOperation(value = "角色列表")
	@RequiresPermissions("sys:role:query")
	public PageTableResponse<Role> listRoles(PageTableRequest request) {
		return PageTableHandler.<Role> builder().countHandler(new CountHandler() {

			@Override
			public int count(PageTableRequest request) {
				return roleDao.count(request.getParams());
			}
		}).listHandler(new ListHandler<Role>() {

			@Override
			public List<Role> list(PageTableRequest request) {
				List<Role> list = roleDao.list(request.getParams(), request.getOffset(), request.getLimit());
				return list;
			}
		}).build().handle(request);
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "根据id获取角色")
	@RequiresPermissions("sys:role:query")
	public Role get(@PathVariable Long id) {
		return roleDao.getById(id);
	}

	@GetMapping("/all")
	@ApiOperation(value = "所有角色")
	@RequiresPermissions(value = { "sys:user:query", "sys:role:query" }, logical = Logical.OR)
	public List<Role> roles() {
		return roleDao.list(Maps.newHashMap(), null, null);
	}

	@GetMapping(params = "userId")
	@ApiOperation(value = "根据用户id获取拥有的角色")
	@RequiresPermissions(value = { "sys:user:query", "sys:role:query" }, logical = Logical.OR)
	public List<Role> roles(Long userId) {
		return roleDao.listByUserId(userId);
	}

	@LogAnnotation
	@DeleteMapping("/{id}")
	@ApiOperation(value = "删除角色")
	@RequiresPermissions(value = { "sys:role:del" })
	public void delete(@PathVariable Long id) {
		roleService.deleteRole(id);
	}
}
