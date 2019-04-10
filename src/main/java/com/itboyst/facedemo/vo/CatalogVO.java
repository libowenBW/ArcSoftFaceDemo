package com.itboyst.facedemo.vo;

import java.io.Serializable;

import com.itboyst.facedemo.domain.Catalog;

/**
 * Catalog VO.
 * 
 * @since 1.0.0 2017年6月7日
 * @author <a href="https://waylau.com">Way Lau</a>
 */
public class CatalogVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String username;
	private Catalog catalog;

	public CatalogVO() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Catalog getCatalog() {
		return catalog;
	}

	public void setCatalog(Catalog catalog) {
		this.catalog = catalog;
	}

}
