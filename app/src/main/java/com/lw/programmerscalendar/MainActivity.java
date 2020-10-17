package com.lw.programmerscalendar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;

import com.lw.programmerscalendar.model.DataModel;
import com.lw.programmerscalendar.model.SpecialModel;
import com.lw.programmerscalendar.utils.SimpleArrayListBuilder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    private TextView todayTxt, directionsTxt, drinksTxt, tps;
    private LinearLayout suitableRightLL;
    private LinearLayout noSuitableRightLL;
    private List<String> weeks = new ArrayList<>();
    private List<String> directions = new ArrayList<>();
    private List<String> tools = new ArrayList<>();
    private List<String> varNames = new ArrayList<>();
    private List<String> drinks = new ArrayList<>();
    private List<DataModel> dataModelList = new ArrayList<>();
    private List<SpecialModel> specials = new ArrayList<>();
    private int week = -1;
    private int iday = 0;
    private RatingBar ratingBar;

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
        suitableRightLL = findViewById(R.id.suitableRightLL);
        noSuitableRightLL = findViewById(R.id.noSuitableRightLL);
        ratingBar = findViewById(R.id.ratingBar);
        directionsTxt = findViewById(R.id.directionsTxt);
        drinksTxt = findViewById(R.id.drinksTxt);
        tps = findViewById(R.id.tps);
        suitableRightLL.removeAllViews();
        noSuitableRightLL.removeAllViews();
    }

    public void initData() {
        todayTxt.setText(getTodayString());
        pickTodaysLuck();
        directionsTxt.setText("座位朝向：面向" + directions.get(random(iday, 2) % directions.size()) + "写程序，BUG 最少。");
        drinksTxt.setText("今日宜饮：" + pickDrink(drinks, 2));
        ratingBar.setRating(random(iday, 6) % 5 + 1);
        tps.setText("本老黄历借鉴于http://www.liweicg.cn，作者随时会修改，所以如果上午看到的内容跟下午不同，请勿惊慌；\n" +
                "本老黄历仅面向程序员；\n" +
                "本老黄历内容是程序生成的，因为只有这样程序员才会信。");
    }

    public int random(int dayseed, int indexseed) {
        int n = dayseed % 11117;
        for (int i = 0; i < 100 + indexseed; i++) {
            n = n * n;
            n = n % 11117;   // 11117 是个质数
        }
        return n;
    }

    public void pickTodaysLuck() {
        int numGood = random(iday, 98) % 3 + 2;
        int numBad = random(iday, 87) % 3 + 2;
        List<DataModel> eventArr = pickRandomActivity(filter(), numGood + numBad);
        for (int i = 0; i < numGood; i++) {
            addToGood(eventArr.get(i));
        }
        for (int i = 0; i < numBad; i++) {
            addToBad(eventArr.get(numGood + i));
        }
    }

    public void addToGood(DataModel model) {
        if (model != null) {
            View view = LayoutInflater.from(this).inflate(R.layout.item_layout, null);
            TextView title = view.findViewById(R.id.title);
            TextView content = view.findViewById(R.id.content);
            title.setText(model.name);
            content.setText(model.good);
            suitableRightLL.addView(view);
        }
    }

    public void addToBad(DataModel model) {
        if (model != null) {
            View view = LayoutInflater.from(this).inflate(R.layout.item_layout, null);
            TextView title = view.findViewById(R.id.title);
            TextView content = view.findViewById(R.id.content);
            title.setText(model.name);
            content.setText(model.bad);
            noSuitableRightLL.addView(view);
        }
    }

    // 从 activities 中随机挑选 size 个
    public List<DataModel> pickRandomActivity(List<DataModel> activities, int size) {
        List<DataModel> picked_events = pickRandom(activities, size);
        for (int i = 0; i < picked_events.size(); i++) {
            picked_events.set(i, parse(picked_events.get(i)));
        }
        return picked_events;
    }

    public List<DataModel> pickRandom(List<DataModel> array, int size) {
        List<DataModel> result = new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            result.add(array.get(i));
        }
        for (int j = 0; j < array.size() - size; j++) {
            int index = random(iday, j) % result.size();
            result.remove(index);
        }
        return result;
    }

    public String pickDrink(List<String> array, int size) {
        List<String> result = new ArrayList<>();
        String data = "";
        for (int i = 0; i < array.size(); i++) {
            result.add(array.get(i));
        }
        for (int j = 0; j < array.size() - size; j++) {
            int index = random(iday, j) % result.size();
            result.remove(index);
        }
        for (int i = 0; i < result.size(); i++) {
            if (i == result.size()) {
                data = data + result.get(i);
            } else {
                data = data + result.get(i) + ",";
            }
        }
        return data;
    }

    // 解析占位符并替换成随机内容
    public DataModel parse(DataModel event) {
        DataModel result = new DataModel(event.name, event.good, event.bad, false);  // clone
        if (result.name.indexOf("%v") != -1) {
            result.name = result.name.replace("%v", varNames.get(random(iday, 12) % varNames.size()));
        }
        if (result.name.indexOf("%t") != -1) {
            result.name = result.name.replace("%t", tools.get(random(iday, 11) % tools.size()));
        }
        if (result.name.indexOf("%l") != -1) {
            result.name = result.name.replace("%l", (random(iday, 12) % 247 + 30) + "");
        }
        return result;
    }

    public List<DataModel> filter() {
        List<DataModel> result = new ArrayList<>();
        if (isWeekend()) {
            if (dataModelList != null && dataModelList.size() > 0) {
                for (int i = 0; i < dataModelList.size(); i++) {
                    if (dataModelList.get(i).weekend) {
                        result.add(dataModelList.get(i));
                    }
                }
            }
            return result;
        }
        return dataModelList;
    }

    public boolean isWeekend() {//是否是周末
        if (week == 1 || week == 7) {//1和7代表周末
            return true;
        } else {
            return false;
        }
    }

    public String getTodayString() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);//获取年份
        int month = cal.get(Calendar.MONTH);//获取月份
        int day = cal.get(Calendar.DATE);//获取日
        String WeekOfYear = getWeek(cal.get(Calendar.DAY_OF_WEEK));//一周的第几天
        week = cal.get(Calendar.DAY_OF_WEEK);
        iday = year * 10000 + month * 100 + day;
        return "今天是" + year + "年" + (month + 1) + "月" + day + "日 " + "星期" + WeekOfYear;
    }

    public void initList() {
        weeks = new SimpleArrayListBuilder<>().adds("日").adds("一").adds("二").adds("三").adds("四").adds("五").adds("六");
        directions = new SimpleArrayListBuilder<>().adds("北方").adds("东北方").adds("东方").adds("东南方").adds("南方").adds("西南方").adds("西方").adds("西北方");
        tools = new SimpleArrayListBuilder<>().adds("Eclipse写程序").adds("MSOffice写文档").adds("记事本写程序").adds("Windows8").adds("Linux").adds("MacOS").adds("IE").adds("Android设备").adds("iOS设备");
        varNames = new SimpleArrayListBuilder<>().adds("jieguo").adds("huodong").adds("pay").adds("expire").adds("zhangdan").adds("every").adds("free").adds("i1")
                .adds("a").adds("virtual").adds("ad").adds("spider").adds("mima").adds("pass").adds("ui");
        drinks = new SimpleArrayListBuilder<>().adds("水").adds("茶").adds("红茶").adds("绿茶").adds("咖啡").adds("奶茶").adds("可乐").adds("鲜奶")
                .adds("豆奶").adds("果汁").adds("果味汽水").adds("苏打水").adds("运动饮料").adds("酸奶").adds("酒");
        specials.add(new SpecialModel(20140214, "bad", "待在男（女）友身边", "脱团火葬场，入团保平安。"));
        dataModelList.add(new DataModel("洗澡", "你几天没洗澡了", "会把设计方面的灵感洗掉", true));
        dataModelList.add(new DataModel("锻炼一下身体", "多锻炼有助于健康", "能量没消耗多少，吃得却更多", true));
        dataModelList.add(new DataModel("抽烟", "抽烟有利于提神，增加思维敏捷", "除非你活够了，死得早点没关系", true));
        dataModelList.add(new DataModel("白天上线", "今天白天上线是安全的", "可能导致灾难性后果", false));
        dataModelList.add(new DataModel("使用\"%t\"", "你看起来更有品位", "别人会觉得你在装逼", false));
        dataModelList.add(new DataModel("跳槽", "该放手时就放手", "鉴于当前的经济形势，你的下一份工作未必比现在强", false));
        dataModelList.add(new DataModel("招人", "你面前这位有成为牛人的潜质", "这人会写程序吗？", false));
        dataModelList.add(new DataModel("面试", "面试官今天心情很好", "面试官不爽，会拿你出气", false));
        dataModelList.add(new DataModel("提交辞职申请", "公司找到了一个比你更能干更便宜的家伙，巴不得你赶快滚蛋", "鉴于当前的经济形势，你的下一份工作未必比现在强", false));
        dataModelList.add(new DataModel("申请加薪", "老板今天心情很好", "公司正在考虑裁员", false));
        dataModelList.add(new DataModel("晚上加班", "晚上是程序员精神最好的时候", "", true));
        dataModelList.add(new DataModel("在妹子面前吹牛", "改善你矮穷挫的形象", "会被识破", true));
        dataModelList.add(new DataModel("撸管", "避免缓冲区溢出", "强撸灰飞烟灭", true));
        dataModelList.add(new DataModel("浏览成人网站", "重拾对生活的信心", "你会心神不宁", true));
        dataModelList.add(new DataModel("命名变量\"%v\"", "", "", false));
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

    private String getWeek(int week) {
        if (week == 1) {
            return "日";
        } else if (week == 2) {
            return "一";
        } else if (week == 3) {
            return "二";
        } else if (week == 4) {
            return "三";
        } else if (week == 5) {
            return "四";
        } else if (week == 6) {
            return "五";
        } else if (week == 7) {
            return "六";
        }
        return null;
    }
}