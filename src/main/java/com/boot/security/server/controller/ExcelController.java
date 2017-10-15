package com.boot.security.server.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zw.admin.server.annotation.LogAnnotation;
import com.zw.admin.server.utils.ExcelUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "excel下载")
@RestController
@RequestMapping("/excels")
public class ExcelController {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@ApiOperation("校验sql，并返回sql返回的数量")
	@PostMapping("/sql-count")
	public Integer checkSql(String sql) {
		sql = getAndCheckSql(sql);

		Integer count = 0;
		try {
			count = jdbcTemplate.queryForObject("select count(1) from (" + sql + ") t", Integer.class);
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage());
		}

		return count;
	}

	private String getAndCheckSql(String sql) {
		sql = sql.trim().toLowerCase();
		if (sql.endsWith(";") || sql.endsWith("；")) {
			sql = sql.substring(0, sql.length() - 1);
		}
		if (!sql.startsWith("select")) {
			throw new IllegalArgumentException("仅支持select语句");
		}
		return sql;
	}

	@LogAnnotation
	@ApiOperation("根据sql导出excel")
	@PostMapping
	@RequiresPermissions("excel:down")
	public void downloadExcel(String sql, String fileName, HttpServletResponse response) {
		sql = getAndCheckSql(sql);
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);

		if (!CollectionUtils.isEmpty(list)) {
			Map<String, Object> map = list.get(0);

			String[] headers = new String[map.size()];
			int i = 0;
			for (String key : map.keySet()) {
				headers[i++] = key;
			}

			List<Object[]> datas = new ArrayList<>(list.size());
			for (Map<String, Object> m : list) {
				Object[] objects = new Object[headers.length];
				for (int j = 0; j < headers.length; j++) {
					objects[j] = m.get(headers[j]);
				}

				datas.add(objects);
			}

			ExcelUtil.excelExport(
					fileName == null || fileName.trim().length() <= 0 ? DigestUtils.md5Hex(sql) : fileName, headers,
					datas, response);
		}
	}

	@LogAnnotation
	@ApiOperation("根据sql在页面显示结果")
	@PostMapping("/show-datas")
	@RequiresPermissions("excel:show:datas")
	public List<Object[]> showData(String sql) {
		sql = getAndCheckSql(sql);
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);

		if (!CollectionUtils.isEmpty(list)) {
			Map<String, Object> map = list.get(0);

			String[] headers = new String[map.size()];
			int i = 0;
			for (String key : map.keySet()) {
				headers[i++] = key;
			}

			List<Object[]> datas = new ArrayList<>(list.size());
			datas.add(headers);
			for (Map<String, Object> m : list) {
				Object[] objects = new Object[headers.length];
				for (int j = 0; j < headers.length; j++) {
					objects[j] = m.get(headers[j]);
				}

				datas.add(objects);
			}

			return datas;
		}

		return Collections.emptyList();
	}

}
