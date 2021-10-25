package com.wechat.jfinal.api.coin;

import com.jfinal.core.Controller;
import com.jfinal.kit.HttpKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.wechat.entity.UnifiedOrderModel;
import com.wechat.jfinal.annotation.EmptyParaValidate;
import com.wechat.jfinal.common.Rt;
import com.wechat.jfinal.common.utils.web.Result;
import com.wechat.jfinal.model.*;
import com.wechat.jfinal.service.WechatPayService;
import com.wechat.pay.util.XMLUtil;
import com.wechat.util.CommonUtils;
import com.wechat.util.IpKit;
import com.wechat.util.Keys;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.jdom.JDOMException;

import java.io.IOException;
import java.lang.reflect.Array;
import java.security.Key;
import java.util.*;

public class CoinCtr extends Controller {

    public void coinPack() {

        List<StudyCoinPack> studyCoinPacks = StudyCoinPack.dao.find("SELECT * FROM `study_coin_pack`");
        Result.ok(studyCoinPacks, this);

    }

    @EmptyParaValidate(params = {"pack", "account"})
    public void createOrder() {

        StudyCoinPack coinPack = StudyCoinPack.dao.findById(getParaToInt("pack"));

        if (coinPack == null) {
            Result.error(20501, "创建订单失败", this);
            return;
        }

        StudyCoinOrder2 studyCoinOrder = new StudyCoinOrder2();
        studyCoinOrder.setOrderNumber(CommonUtils.getRandomNumber(12));
        studyCoinOrder.setStudyCoinPackId(coinPack.getId());
        studyCoinOrder.setTotalPrice(coinPack.getTotalPrice());
        studyCoinOrder.setAccountId(getParaToInt("account"));
        studyCoinOrder.save();

        Result.ok(studyCoinOrder, this);

    }

    @EmptyParaValidate(params = {"account", "pack"})
    public void order() throws JDOMException, IOException {

        int account = getParaToInt("account");
        int pack = getParaToInt("pack");

        String notifyUrl = "http://test.fandoutech.com.cn/snp/v2/coin/studyCoinNPayCallBack";

        StudyCoinPack coinPack = StudyCoinPack.dao.findById(pack);

        if (coinPack == null) {
            Result.error(20501, "创建订单失败", this);
            return;
        }

        StudyCoinOrder2 studyCoinOrder = new StudyCoinOrder2();
        studyCoinOrder.setOrderNumber(CommonUtils.getRandomNumber(12));
        studyCoinOrder.setStudyCoinPackId(coinPack.getId());
        studyCoinOrder.setTotalPrice(coinPack.getTotalPrice());
        studyCoinOrder.setAccountId(account);
        studyCoinOrder.save();

        WechatPayService payService = new WechatPayService();

        String remoteIp = IpKit.getRealIp(getRequest());
        //String remoteIp = "61.145.190.131";

        UnifiedOrderModel model = new UnifiedOrderModel("wxc75146692beb9db9", "1561166631",
                "shaonianpai16888zqbxzmzgz2964079", coinPack.getName(), studyCoinOrder.getOrderNumber(),
                studyCoinOrder.getBigDecimal("total_price").toString(), remoteIp, notifyUrl, "APP");

        Map<String, String> result = payService.unifiedorder(model);
        System.out.println(result.toString());

        if (result.get("return_code").equalsIgnoreCase("SUCCESS")) {

            if (result.get("result_code").equalsIgnoreCase("SUCCESS")) {

                SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
                parameters.put("appid", result.get("appid"));
                parameters.put("partnerid", result.get("mch_id"));
                parameters.put("prepayid", result.get("prepay_id"));
                parameters.put("package", "Sign=WXPay");
                parameters.put("timestamp", System.currentTimeMillis() / 1000);
                parameters.put("noncestr", CommonUtils.getRandomNumber(32).toString());
                parameters.put("sign", payService.createSign("UTF-8", parameters, "shaonianpai16888zqbxzmzgz2964079"));
                parameters.put("orderNumber", studyCoinOrder.getOrderNumber());
                parameters.put("order", studyCoinOrder.getId());
                parameters.put("_package", "Sign=WXPay");

                Result.ok(parameters, this);

            } else {
                Result.error(20502, "统一下单失败", this);
            }

        } else {
            Result.error(20502, "统一下单失败", this);
        }

    }


    public void checkrOder() {

        String receipt = getPara("receipt");
        String transactionId = getPara("transaction");
        int accountId = getParaToInt("account");

        JSONObject json = new JSONObject();
        json.put("receipt-data", receipt);
        String resultStr = HttpKit.post("https://buy.itunes.apple.com/verifyReceipt", json.toString());

        JSONObject result = JSONObject.fromObject(resultStr);

        if (result.getString("status").equals("0")) {
            JSONArray inAPP = result.getJSONObject("receipt").getJSONArray("in_app");
            int count = 0;
            String productId = null;

            boolean flag = false;

            for (int i = 0; i < inAPP.size(); i++) {
                if (transactionId.equals(inAPP.getJSONObject(i).getString("transaction_id"))) {
                    count = Integer.parseInt(inAPP.getJSONObject(i).getString("quantity"));
                    productId = inAPP.getJSONObject(i).getString("product_id");
                    flag = true;
                }
            }

            if (flag) {

                StudyCoinPack scoinPackage = StudyCoinPack.dao.findFirst("select * from study_coin_pack where apple_id = ?", productId);
                StudyCoinRecord studyCoinRecord = new StudyCoinRecord();
                studyCoinRecord.setAction(1);
                studyCoinRecord.setCount(count * scoinPackage.getCount());
                studyCoinRecord.setAccountId(accountId);
                studyCoinRecord.setRemark("充值");
                studyCoinRecord.save();
                Result.ok(this);

            } else {
                Result.error(20502, "没有匹配的订单", this);
            }

        } else {
            Result.error(20501, "未支付", this);
        }


    }

    public void studyCoinNPayCallBack() throws JDOMException, IOException {
        String data = HttpKit.readData(getRequest());

        Map<String, String> result = XMLUtil.doXMLParse(data);

        if (result.get("return_code").equalsIgnoreCase("SUCCESS")
                && result.get("result_code").equalsIgnoreCase("SUCCESS")) {

            StudyCoinOrder2 studyCoinOrder = StudyCoinOrder2.dao.findByOrderNumber(result.get("out_trade_no"));

            if (studyCoinOrder != null) {
                studyCoinOrder.setStatus(1).update();
                StudyCoinPack coinPack = StudyCoinPack.dao.findById(studyCoinOrder.getStudyCoinPackId());
                StudyCoinRecord studyCoinRecord = new StudyCoinRecord();
                studyCoinRecord.setAction(1);
                studyCoinRecord.setRemark("充值");
                studyCoinRecord.setCount(coinPack.getCount());
                studyCoinRecord.setAccountId(studyCoinOrder.getAccountId());
                studyCoinRecord.save();
            }

        }

        renderText("<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>");
    }

    @EmptyParaValidate(params = {"account"})
    public void myCoin() {

        int accountId = getParaToInt("account");
        int count = 0;

        List<StudyCoinRecord> studyCoinRecords = StudyCoinRecord.dao.findByAcccount(accountId);

        for (StudyCoinRecord studyCoinRecord : studyCoinRecords) {
            count += studyCoinRecord.getCount();
        }

        JSONObject json = new JSONObject();
        json.put("count", count);

        Result.ok(json, this);
    }

    public void coinRecord() {

        int account = getParaToInt("account");

        List<StudyCoinRecord> studyCoinRecords = StudyCoinRecord.dao.findByAcccount(account);

        JSONObject json = new JSONObject();
        json.put("studyCoinRecords", Result.makeupList(studyCoinRecords));

        Result.ok(json, this);

    }

    @EmptyParaValidate(params = {"account", "card"})
    public void card() {
        StudyCoinCard studyCoinCard = StudyCoinCard.dao.findFirst("SELECT a.*,b.coin_count FROM `study_coin_card` a,study_coin_card_category b  WHERE a.code = ? AND a.category_id = b.id", getPara("card"));

        if (studyCoinCard == null || studyCoinCard.getStatus() == 1) {
            Result.error(20501, "豆币卡无效", this);
            return;
        }

        Memberaccount memberaccount = Memberaccount.dao.findFirst("select * from memberaccount where account = ?", getParaToInt("account"));

        StudyCoinRecord studyCoinRecord = new StudyCoinRecord();
        studyCoinRecord.setAccountId(memberaccount.getId());
        studyCoinRecord.setAction(1);
        studyCoinRecord.setCount(studyCoinCard.getInt("coin_count"));
        studyCoinRecord.setRemark("豆币卡充值").save();

        studyCoinCard.setStatus(1);
        studyCoinCard.setAccount(memberaccount.getAccount()).update();


        Result.ok(this);

    }

    @EmptyParaValidate(params = {"id"})
    public void coinOrder() {

        Result.ok(StudyCoinOrder.dao.findById(getParaToInt("id")), this);

    }

    @EmptyParaValidate(params = {"accountId"})
    public void coinTask() {
        Integer accountId = getParaToInt("accountId");
        System.out.println(accountId);
        List<Record> coinGiftList = Db.find("select * from coin_gift where id in (1,9,11,13,15,17)");
        List<List<Record>> interactTask = new ArrayList<>();
        List<List<Record>> otherTask = new ArrayList<>();
        int[] goodTaskIds = new int[]{13, 15, 17};
        List<Record> goodTask = new ArrayList<>();
        List<Record> data = new ArrayList<>();
        coinGiftList.forEach((v) -> {
            List<Record> tempTask = new ArrayList<>();
            if (Arrays.binarySearch(goodTaskIds, v.getInt("id")) > -1) {
                goodTask.add(v);
            } else if (v.getInt("id") == 9) {
                v.set("type", "interactTask");
                data.add(v);
            } else if (v.getInt("id") == 11) {  //填写邀请码
                AccountInvitationRecord accountInvitationRecord = AccountInvitationRecord.dao.findFirst("select * from account_invitation_record where account_id = ?", accountId);
                if (accountInvitationRecord != null) {
                    v.set("completed", 1);
                } else {
                    v.set("completed", 0);
                }
                v.set("type", "newBieTask");
                data.add(v);
            } else {
                v.set("type", "newBieTask");
                data.add(v);
            }
        });
        data.add(new Record().set("list", goodTask).set("type", "interactTask").set("name", goodTask.get(0).get("name")));
        renderJson(Rt.success(data));
    }


}
