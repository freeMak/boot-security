package com.boot.security.server.page.table;

import java.io.Serializable;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 分页查询返回
 * 
 * @author 小威老师
 *
 */
@Getter
@Setter
@Builder
public class PageTableResponse<T> implements Serializable {

	private static final long serialVersionUID = 620421858510718076L;

	private Integer recordsTotal;
	private Integer recordsFiltered;
	private List<T> data;

}