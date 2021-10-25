package com.wechat.jfinal;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.redis.RedisPlugin;
import com.jfinal.render.ViewType;
import com.jfinal.template.Engine;
import com.wechat.jfinal.Oauth2.Oauth2Ctr;
import com.wechat.jfinal.Oauth2.QYOauth2Ctr;
import com.wechat.jfinal.api.SysCtr;
import com.wechat.jfinal.api.agent.AgentCtr;
import com.wechat.jfinal.api.audio.AudioInfo;
import com.wechat.jfinal.api.book.BookCtr;
import com.wechat.jfinal.api.bookShop.bookShop;
import com.wechat.jfinal.api.chaozhiduoduo.ChaoZhiCtr;
import com.wechat.jfinal.api.classCourse.ClassCourseHandle;
import com.wechat.jfinal.api.classLive.ClassLiveCtr;
import com.wechat.jfinal.api.classRoom.ClassCourseRecord;
import com.wechat.jfinal.api.classRoom.ClassProductCtr;
import com.wechat.jfinal.api.classRoom.ClassRoomHandle;
import com.wechat.jfinal.api.classRoom.ClassRoomPackageCtr;
import com.wechat.jfinal.api.classRoom.Comment;
import com.wechat.jfinal.api.coin.CoinCtr;
import com.wechat.jfinal.api.device.DeviceHandle;
import com.wechat.jfinal.api.device.UploadHandle;
import com.wechat.jfinal.api.gradeI.GradeStatisticalCtr;
import com.wechat.jfinal.api.groupchat.GroupChatCtr;
import com.wechat.jfinal.api.lesson.LessonHandle;
import com.wechat.jfinal.api.mall.MallCtr;
import com.wechat.jfinal.api.mall.SnpMallCtr;
import com.wechat.jfinal.api.message.MessageCtr;
import com.wechat.jfinal.api.miniapp.LessonProductCtr;
import com.wechat.jfinal.api.miniapp.MiniAPP;
import com.wechat.jfinal.api.push.SourcePush;
import com.wechat.jfinal.api.question.BulletinQuestionCtr;
import com.wechat.jfinal.api.qy.ExhibitionCtr;
import com.wechat.jfinal.api.qy.QYClassHandle;
import com.wechat.jfinal.api.qy.QYDeviceHandle;
import com.wechat.jfinal.api.qy.QYPunchActionHandle;
import com.wechat.jfinal.api.qy.QYPunchRecord;
import com.wechat.jfinal.api.qy.QYStudentHandle;
import com.wechat.jfinal.api.qy.QYTeacherCtrl;
import com.wechat.jfinal.api.resource.CategoryCtrl;
import com.wechat.jfinal.api.sell.SellCtr;
import com.wechat.jfinal.api.stdI.CourseCtr;
import com.wechat.jfinal.api.stdI.StdStatisticalCtr;
import com.wechat.jfinal.api.student.StdGradeCtr;
import com.wechat.jfinal.api.student.StudentAccountHandle;
import com.wechat.jfinal.api.system.CZDD;
import com.wechat.jfinal.api.system.Fandou;
import com.wechat.jfinal.api.teacher.ClassGradesHandle;
import com.wechat.jfinal.api.teacher.ClassTeacherHandle;
import com.wechat.jfinal.api.teacher.T_ClassRoomHandle;
import com.wechat.jfinal.api.test.Test;
import com.wechat.jfinal.api.train.training;
import com.wechat.jfinal.api.user.UserHandler;
import com.wechat.jfinal.api.video.VideoHandle;
import com.wechat.jfinal.api.wellLanguage.WellCtr;
import com.wechat.jfinal.apiAjax.AjaxClassRoomHandle;
import com.wechat.jfinal.apiAjax.StatisticalCtr;
import com.wechat.jfinal.apiRenderPage.RouteCtr;
import com.wechat.jfinal.apiRenderPage.bookShop.BookShopCtr;
import com.wechat.jfinal.apiRenderPage.classRoom.Article;
import com.wechat.jfinal.apiRenderPage.evaluation.EvaluationHandle;
import com.wechat.jfinal.apiRenderPage.userVideo.GoodVideoCtr;
import com.wechat.jfinal.interceptor.ActionInterceptor;
import com.wechat.jfinal.interceptor.ParaValidateInterceptor;
import com.wechat.jfinal.model._MappingKit;


public class WechatConfig extends JFinalConfig {
    @Override
    public void configConstant(Constants me) {
        // 加载少量必要配置，随后可用PropKit.get(...)获取值
        PropKit.use("jfinalConfig.txt");
        me.setDevMode(PropKit.getBoolean("devMode", false));
        me.setViewType(ViewType.JSP);
    }

    @Override
    public void configRoute(Routes me) {
        me.add("/v2/hello", Test.class);
        me.add("/v2/icourse", CourseCtr.class);//兼容之前的接口
        me.add("/v2/std/course", CourseCtr.class);

        me.add("/v2/std/statistical", StdStatisticalCtr.class);
        me.add("/v2/grade/statistical", GradeStatisticalCtr.class);

        me.add("/v2/sourcePush", SourcePush.class);
        me.add("/v2/sys", SysCtr.class);
        me.add("/v2/res/category", CategoryCtrl.class);


        me.add("/v2/student", StudentAccountHandle.class);
        me.add("/v2/student/grade", StdGradeCtr.class);

        me.add("/v2/teacher/classGradesHandle", ClassGradesHandle.class);
        me.add("/v2/teacher/classRoomHandle", T_ClassRoomHandle.class);
        me.add("/v2/teacher/classTeacherHandle", ClassTeacherHandle.class);

        me.add("/v2/device/uploadHandle", UploadHandle.class);
        me.add("/v2/device/deviceHandle", DeviceHandle.class);

        me.add("/v2/book", BookCtr.class);

        me.add("/v2/classRoom/ClassRoomHandle", ClassRoomHandle.class);

        me.add("/v2/user", UserHandler.class);
        me.add("/v2/train", training.class);

        //h5页面渲染请求,类在包apiRenderPage内
        me.add("/h5/route", RouteCtr.class);
        me.add("/h5/bookShop", BookShopCtr.class);
        me.add("/h5/userVideo", GoodVideoCtr.class);


        //H5页面内ajax请求，类在包apiAjax内
        me.add("/ajax/statistical", StatisticalCtr.class);
        me.add("/ajax/classRoom", AjaxClassRoomHandle.class);
        me.add("/ajax/user", UserHandler.class);

        //清园机器人操作
        me.add("/v2/qy/punch", QYPunchRecord.class);
        me.add("/v2/qy/device", QYDeviceHandle.class);
        me.add("/v2/qy/class", QYClassHandle.class);
        me.add("/v2/qy/student", QYStudentHandle.class);
        me.add("/v2/qy/punchAction", QYPunchActionHandle.class);
        me.add("/v2/qy/teacher", QYTeacherCtrl.class);

        me.add("/v2/lesson", LessonHandle.class);


        me.add("/v2/bookShop", bookShop.class);

        me.add("/v2/audio", AudioInfo.class);

        me.add("/v2/message", MessageCtr.class);

        me.add("/h5/user", com.wechat.jfinal.apiRenderPage.user.UserHandler.class);

        me.add("/h5/evaluation", EvaluationHandle.class);

        me.add("/h5/article", Article.class);

        me.add("/v2/video", VideoHandle.class);

        me.add("/ajax/chaozhi", ChaoZhiCtr.class);

        me.add("/ajax/lessonPack", ClassRoomPackageCtr.class);

        me.add("/v2/system/czdd", CZDD.class);

        me.add("/v2/fandou", Fandou.class);

        me.add("/v2/classRoom/comment", Comment.class);

        me.add("/v2/miniapp", MiniAPP.class);

        me.add("/v2/classCourse", ClassCourseHandle.class);

        me.add("/v2/classRoom/record", ClassCourseRecord.class);

        me.add("/v2/mall", MallCtr.class);

        me.add("/v2/agent", AgentCtr.class);

        me.add("/v2/bulletinQuestion", BulletinQuestionCtr.class);

        me.add("/v2/classproduct", ClassProductCtr.class);

        me.add("/v2/oauth2", Oauth2Ctr.class);

        me.add("/v2/lessonProduct", LessonProductCtr.class);

        me.add("/v2/qyExhibition", ExhibitionCtr.class);

        me.add("/v2/groupChat", GroupChatCtr.class);

        me.add("/v2/qyoauth2", QYOauth2Ctr.class);

        me.add("/v2/well", WellCtr.class);

        me.add("/v2/live", ClassLiveCtr.class);

        me.add("/v2/coin", CoinCtr.class);

        me.add("/v2/snpmall", SnpMallCtr.class);

    }

    @Override
    public void configEngine(Engine engine) {

    }

    public static DruidPlugin createDruidPlugin() {
        return new DruidPlugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password").trim());
    }

    @Override
    public void configPlugin(Plugins me) {
        // 配置C3p0数据库连接池插件
        DruidPlugin druidPlugin = createDruidPlugin();
        druidPlugin.setConnectionInitSql("set names utf8mb4;");
        me.add(druidPlugin);

        // 配置ActiveRecord插件
        ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
        // 是否显示SQL
        arp.setShowSql(true);
        arp.setBaseSqlTemplatePath(PathKit.getRootClassPath());
        arp.addSqlTemplate("sql/all.sql");
        // 所有映射在 MappingKit 中自动化搞定
        _MappingKit.mapping(arp);
        me.add(arp);
        // redis服务
        RedisPlugin redis = new RedisPlugin("redis", "8.129.31.226", 6379, 3000, "de5Vq9XdadHr8");
        me.add(redis);
    }

    @Override
    public void configInterceptor(Interceptors me) {
        // 添加控制层全局拦截器
        me.addGlobalActionInterceptor(new ActionInterceptor());
        me.add(new ParaValidateInterceptor());
    }

    @Override
    public void configHandler(Handlers me) {
        me.add(new SkipOldUrlHandler());
    }
}
