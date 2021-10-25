package com.wechat.jfinal.api;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.wechat.jfinal.common.Rt;
import com.wechat.jfinal.common.StandardRsp;
import com.wechat.jfinal.common.utils.web.JsonResult;
import com.wechat.jfinal.common.utils.xx;
import com.wechat.jfinal.model.Memberaccount;
import com.wechat.jfinal.service.MemberService;
import com.wechat.jfinal.service.MemberaccountService;
import com.wechat.jfinal.service.RfidService;

import java.util.HashMap;
import java.util.Map;

public class SysCtr extends Controller {

    private static final MemberService memberService = new MemberService();
    private static final MemberaccountService memberAccountService = new MemberaccountService();
    private RfidService rfidService = new RfidService();

    public void isOutdate(){
        StandardRsp rsp = new StandardRsp();
        try {
            //获得软件版本
            String environment = getPara("environment","test");
            Record r = Db.findFirst("select is_outdate isOutdate ,version from `is_outdate` where environment = ?",  environment);
            rsp.setCode(200);
            rsp.setData(r);
        } catch (Exception e) {
            rsp.setCode(500);
            e.printStackTrace();
            renderJson(rsp.getContext());
        }
        renderJson(rsp.getContext());
    }

    public void isRegister(){
        String mobile = getPara("mobile");
        if(xx.isEmpty(mobile)){
            renderJson(JsonResult.JsonResultError(305));
        }
        Map<String,Object> result = new HashMap<>();
//        Member member =memberService.getBymobile(mobile);

        Memberaccount account = memberAccountService.getByMobile(mobile);

        if (account == null) {
            result.put("registMemberID",0);
        }else
            result.put("registMemberID",account.getMemberid());
        renderJson(JsonResult.OK(result));
    }

    public void rfidIsLegal(){
        String cardId = getPara("cardId","");
        if(xx.isEmpty(cardId)){
            renderJson(Rt.paraError());
        }
        switch (rfidService.isLegal(cardId)){
            case 0:
                renderJson(Rt.showMsg("卡号不合法"));
                return;
            case 1:
                renderJson(Rt.success());
                return;
            case 2:
                renderJson(Rt.showMsg("卡号已被使用"));
                return;
        }
    }
}
