package com.boot.security.server.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.zw.admin.server.dao.RoleDao;
import com.zw.admin.server.dto.RoleDto;
import com.zw.admin.server.model.Role;
import com.zw.admin.server.service.RoleService;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "adminLogger")
@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDao roleDao;

	@Override
	@Transactional
	public void saveRole(RoleDto roleDto) {
		Role role = roleDto;

		if (role.getId() != null) {// 修改
			Role r = roleDao.getRole(role.getName());
			if (r != null && r.getId() != role.getId()) {
				throw new IllegalArgumentException(role.getName() + "已存在");
			}

			roleDao.update(role);
		} else {// 新增
			Role r = roleDao.getRole(role.getName());
			if (r != null) {
				throw new IllegalArgumentException(role.getName() + "已存在");
			}

			roleDao.save(role);

			log.debug("新增角色{}", role.getName());
		}

		saveRolePermission(role.getId(), roleDto.getPermissionIds());
	}

	private void saveRolePermission(Long roleId, List<Long> permissionIds) {
		roleDao.deleteRolePermission(roleId);
		permissionIds.remove(0L);
		if (!CollectionUtils.isEmpty(permissionIds)) {
			roleDao.saveRolePermission(roleId, permissionIds);
		}
	}

	@Override
	@Transactional
	public void deleteRole(Long id) {
		roleDao.deleteRolePermission(id);
		roleDao.delete(id);

		log.debug("删除角色id:{}", id);
	}

}
