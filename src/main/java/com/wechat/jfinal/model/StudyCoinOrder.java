package com.wechat.jfinal.model;

import com.wechat.jfinal.model.base.BaseStudyCoinOrder;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class StudyCoinOrder extends BaseStudyCoinOrder<StudyCoinOrder> {
	public static final StudyCoinOrder dao = new StudyCoinOrder().dao();

    public StudyCoinOrder findByOrderNumber(String out_trade_no) {
        return StudyCoinOrder.dao.findFirst("SELECT * FROM `study_coin_record` WHERE order_number = ?",out_trade_no);
    }
}
