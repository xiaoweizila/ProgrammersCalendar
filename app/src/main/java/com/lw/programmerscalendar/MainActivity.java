package com.lw.programmerscalendar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.lw.programmerscalendar.model.DataModel;
import com.lw.programmerscalendar.utils.SimpleArrayListBuilder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView todayTxt;
    private List<String> weeks;
    private List<String> directions;
    private List<String> tools;
    private List<String> varNames;
    private List<String> drinks;
    private List<DataModel> dataModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        initList();
        initData();
    }

    public void findViews() {
        todayTxt = findViewById(R.id.todayTxt);
    }

    public void initData() {
        todayTxt.setText(getTodayString());
    }

    public void initList() {
        weeks = new SimpleArrayListBuilder<>().adds("日").adds("一").adds("二").adds("三").adds("四").adds("五").adds("六");
        directions = new SimpleArrayListBuilder<>().adds("北方").adds("东北方").adds("东方").adds("东南方").adds("南方").adds("西南方").adds("西方").adds("西北方");
        tools = new SimpleArrayListBuilder<>().adds("Eclipse写程序").adds("MSOffice写文档").adds("记事本写程序").adds("Windows8").adds("Linux").adds("MacOS").adds("IE").adds("Android设备").adds("iOS设备");
        varNames = new SimpleArrayListBuilder<>().adds("jieguo").adds("huodong").adds("pay").adds("expire").adds("zhangdan").adds("every").adds("free").adds("i1")
                .adds("a").adds("virtual").adds("ad").adds("spider").adds("mima").adds("pass").adds("ui");
        drinks = new SimpleArrayListBuilder<>().adds("水").adds("茶").adds("红茶").adds("绿茶").adds("咖啡").adds("奶茶").adds("可乐").adds("鲜奶")
                .adds("豆奶").adds("果汁").adds("果味汽水").adds("苏打水").adds("运动饮料").adds("酸奶").adds("酒");
        dataModelList.add(new DataModel("写单元测试", "写单元测试将减少出错", "写单元测试会降低你的开发效率", false));
        dataModelList.add(new DataModel("洗澡", "你几天没洗澡了", "会把设计方面的灵感洗掉", true));
        dataModelList.add(new DataModel("锻炼一下身体", "", "能量没消耗多少，吃得却更多", true));
        dataModelList.add(new DataModel("抽烟", "抽烟有利于提神，增加思维敏捷", "除非你活够了，死得早点没关系", true));
        dataModelList.add(new DataModel("白天上线", "今天白天上线是安全的", "可能导致灾难性后果", false));
        dataModelList.add(new DataModel("使用%t", "你看起来更有品位", "别人会觉得你在装逼", false));
        dataModelList.add(new DataModel("跳槽", "该放手时就放手", "鉴于当前的经济形势，你的下一份工作未必比现在强", false));
        dataModelList.add(new DataModel("招人", "你面前这位有成为牛人的潜质", "这人会写程序吗？", false));
        dataModelList.add(new DataModel("面试", "面试官今天心情很好", "面试官不爽，会拿你出气", false));
        dataModelList.add(new DataModel("提交辞职申请", "公司找到了一个比你更能干更便宜的家伙，巴不得你赶快滚蛋", "鉴于当前的经济形势，你的下一份工作未必比现在强", false));
        dataModelList.add(new DataModel("申请加薪", "老板今天心情很好", "公司正在考虑裁员", false));
        dataModelList.add(new DataModel("晚上加班", "晚上是程序员精神最好的时候", "", true));
        dataModelList.add(new DataModel("在妹子面前吹牛", "改善你矮穷挫的形象", "会被识破", true));
        dataModelList.add(new DataModel("撸管", "避免缓冲区溢出", "强撸灰飞烟灭", true));
        dataModelList.add(new DataModel("浏览成人网站", "重拾对生活的信心", "你会心神不宁", true));
        dataModelList.add(new DataModel("命名变量\\\"%v\\\"", "", "", false));
        dataModelList.add(new DataModel("写超过%l行的方法", "你的代码组织的很好，长一点没关系", "你的代码将混乱不堪，你自己都看不懂", false));
        dataModelList.add(new DataModel("提交代码", "遇到冲突的几率是最低的", "你遇到的一大堆冲突会让你觉得自己是不是时间穿越了", false));
        dataModelList.add(new DataModel("代码复审", "发现重要问题的几率大大增加", "你什么问题都发现不了，白白浪费时间", false));
        dataModelList.add(new DataModel("开会", "写代码之余放松一下打个盹，有益健康", "小心被扣屎盆子背黑锅", false));
        dataModelList.add(new DataModel("打DOTA", "你将有如神助", "你会被虐的很惨", true));
        dataModelList.add(new DataModel("晚上上线", "晚上是程序员精神最好的时候", "你白天已经筋疲力尽了", false));
        dataModelList.add(new DataModel("修复BUG", "你今天对BUG的嗅觉大大提高", "新产生的BUG将比修复的更多", false));
        dataModelList.add(new DataModel("设计评审", "设计评审会议将变成头脑风暴", "人人筋疲力尽，评审就这么过了", false));
        dataModelList.add(new DataModel("需求评审", "", "", false));
        dataModelList.add(new DataModel("上微博", "今天发生的事不能错过", "今天的微博充满负能量", true));
        dataModelList.add(new DataModel("上AB站", "还需要理由吗", "满屏兄贵亮瞎你的眼", true));
        dataModelList.add(new DataModel("玩FlappyBird", "今天破纪录的几率很高", "除非你想玩到把手机砸了", true));
    }

    public String getTodayString() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);//获取年份
        int month = cal.get(Calendar.MONTH);//获取月份
        int day = cal.get(Calendar.DATE);//获取日
        int WeekOfYear = cal.get(Calendar.DAY_OF_WEEK);//一周的第几天
        return "今天是" + year + "年" + month + "月" + day + "日 " + "星期" + WeekOfYear;
    }
}