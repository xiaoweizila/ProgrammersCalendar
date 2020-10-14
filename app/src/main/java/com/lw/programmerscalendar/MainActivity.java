package com.lw.programmerscalendar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.lw.programmerscalendar.utils.SimpleArrayListBuilder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView todayTxt;
    private List<String> weeks;
    private List<String> directions;

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