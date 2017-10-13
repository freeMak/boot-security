package com.boot.security.server.page.table;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;

/**
 * 分页查询处理器
 * 
 * @author 小威老师
 *
 */
@Builder
public class PageTableHandler<T> {

	private CountHandler countHandler;
	private ListHandler<T> listHandler;

	public PageTableResponse<T> handle(PageTableRequest dtRequest) {
		int count = 0;
		List<T> list = null;

		count = this.countHandler.count(dtRequest);
		if (count > 0) {
			list = this.listHandler.list(dtRequest);
		}

		if (list == null) {
			list = new ArrayList<T>();
		}

		return PageTableResponse.<T> builder().recordsTotal(count).recordsFiltered(count).data(list).build();
	}

	public interface ListHandler<T> {
		List<T> list(PageTableRequest request);
	}

	public interface CountHandler {
		int count(PageTableRequest request);
	}
}