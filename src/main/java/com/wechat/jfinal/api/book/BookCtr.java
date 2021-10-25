package com.wechat.jfinal.api.book;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.wechat.jfinal.common.Rt;
import com.wechat.util.Keys;

public class BookCtr extends Controller {


    public void myBook() {

        int pageSize = getParaToInt("pageSize", 10);
        int pageNumber = getParaToInt("page", 1);

        Integer memberId = getParaToInt("memberId");

        Page<Record> data = Db.paginate(pageNumber, pageSize, "select b.id,a.id productId,a.name,a.logo1,a.mp3,a.mp3type,c.cat_name,c.parent_id ", "from mallproduct a,membermp3 b,product_category c where a.id = b.productid and b.memberid=? and a.mp3type=2 and c.cat_id = a.cat_id order by id desc", memberId);
        for (Record r : data.getList()) {
            r.set("logo", r.get("logo1").toString().indexOf("http://") < 0 ? Keys.STAT_NAME + "wechat/wechatImages/mall/" + r.get("logo1") : r.get("logo1"));
            r.remove("logo1");
        }
        renderJson(Rt.success(data));
    }

}
