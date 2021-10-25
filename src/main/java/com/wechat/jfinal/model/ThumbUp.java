package com.wechat.jfinal.model;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.wechat.jfinal.model.base.BaseThumbUp;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class ThumbUp extends BaseThumbUp<ThumbUp> {
	public static final ThumbUp dao = new ThumbUp().dao();

    public ThumbUp findRecord(Integer momentId, Integer userId) {
        return findFirst("select * from thumb_up where user_id=? and moment_id=?",userId,momentId);
    }

    public int findGoodsCount(Integer id) {
        return  Db.findFirst("select count(*) as count from moment where id=?",id).getInt("count");
    }

    public int findGoodsStatus(Integer id, Integer student_id) {

        Record record = Db.findFirst("select a.status from thumb_up a  where moment_id=? and user_id=?", id, student_id);

        return record == null?0:Integer.parseInt(record.get("status").toString());

    }
}