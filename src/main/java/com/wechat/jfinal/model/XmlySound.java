package com.wechat.jfinal.model;

import com.wechat.jfinal.model.base.BaseXmlySound;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class XmlySound extends BaseXmlySound<XmlySound> {
	public static final XmlySound dao = new XmlySound().dao();


    public List<XmlySound> findStatusById(Set<String> ids) {

        if  (ids  ==  null || ids.size()  == 0){
            return new ArrayList<XmlySound>();
        }

        StringBuffer sql = new StringBuffer();
        sql.append("select * from xmly_sound where status =1 and id in (");

        for (String id : ids) {
            sql.append(id+",");
        }
        
        String sqlfinal = sql.substring(0, sql.length() - 1);

        return  find(sqlfinal+")");
    }
}
