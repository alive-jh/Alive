package com.wechat.jfinal.model;

import java.util.List;

import com.wechat.jfinal.model.base.BaseLabel;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class Label extends BaseLabel<Label> {
	public static final Label dao = new Label().dao();

	public List<Label> findAll() {
		return this.find("select * from label where 1=1");
	}
}
