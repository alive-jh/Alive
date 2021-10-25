package com.wechat.jfinal.model;

import com.wechat.jfinal.model.base.BaseStudyCoinRecord;

import java.util.List;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class StudyCoinRecord extends BaseStudyCoinRecord<StudyCoinRecord> {
	public static final StudyCoinRecord dao = new StudyCoinRecord().dao();

    public List<StudyCoinRecord> findByAcccount(int accountId) {
        return StudyCoinRecord.dao.find("select * from study_coin_record where account_id = ? and count != 0 order by id desc",accountId);
    }
}
