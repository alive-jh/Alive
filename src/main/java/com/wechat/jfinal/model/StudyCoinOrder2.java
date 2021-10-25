package com.wechat.jfinal.model;

import com.wechat.jfinal.model.base.BaseStudyCoinOrder2;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class StudyCoinOrder2 extends BaseStudyCoinOrder2<StudyCoinOrder2> {
	public static final StudyCoinOrder2 dao = new StudyCoinOrder2().dao();

    public StudyCoinOrder2 findByOrderNumber(String out_trade_no) {
        return StudyCoinOrder2.dao.findFirst("SELECT * FROM `study_coin_order2` WHERE order_number = ?",out_trade_no);
    }
}