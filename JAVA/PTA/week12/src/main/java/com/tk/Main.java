package com.tk;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

/**
 * 主类，用于处理日程安排的输入和查找最紧急的日程
 */
public class Main {
    public static void main(String[] args) {
        try (Scanner in = new Scanner(System.in)) {
            ArrayList<Schedule> schedules = new ArrayList<>();
            while (true) {
            int index = in.nextInt();
            if (index == 0) break;  // 输入0表示结束输入
            else {
                schedules.add(new Schedule(index, in.next(), in.next()));  // 创建新的日程并添加到列表
            }
            }
            // 查找最紧急的日程
            Schedule mostUrgent = schedules.get(0);
            for (int i = 1; i < schedules.size(); i++) {
            Schedule s = schedules.get(i);
            if (s.isUrgentThan(mostUrgent)) {
                mostUrgent = s;
            }
            }
            System.out.println("The urgent schedule is " + mostUrgent);
        }
    }
}

/**
 * 日期类，存储年、月、日信息
 */
class Date {

    protected final int year;   // 年
    protected final int month;  // 月
    protected final int day;    // 日

    /**
     * 构造函数，初始化日期
     * @param year 年
     * @param month 月
     * @param day 日
     */
    public Date(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }
}

/**
 * 时间类，存储时、分、秒信息
 */
class Time {
    protected final int hour;    // 时
    protected final int minute;  // 分
    protected final int second;  // 秒

    /**
     * 构造函数，初始化时间
     * @param hour 时
     * @param minute 分
     * @param second 秒
     */
    public Time(int hour, int minute, int second) {
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }
}

/**
 * 日程类，包含ID、日期和时间信息
 */
class Schedule {
    private final int id;           // 日程ID
    private final InnerDate date;   // 日期
    private final InnerTime time;   // 时间

    // 使用内部类InnerDate和InnerTime来实现多重继承
    // 用数组作参数只是为了方便
    /**
     * 内部日期类，继承自Date
     */
    private static class InnerDate extends Date {
        public InnerDate(int[] date) {
        super(date[0], date[1], date[2]);
        }
    }

    /**
     * 内部时间类，继承自Time
     */
    private static class InnerTime extends Time {
        public InnerTime(int[] time) {
        super(time[0], time[1], time[2]);
        }
    }

    /**
     * 构造函数，初始化日程
     * @param id 日程ID
     * @param date 日期字符串，格式为"年/月/日"
     * @param time 时间字符串，格式为"时:分:秒"
     */
    public Schedule(int id, String date, String time) {
        this.id = id;
        this.date = new InnerDate(toNumParams(date.split("/")));
        this.time = new InnerTime(toNumParams(time.split(":")));
    }

    /**
     * 重写toString方法，返回日程的字符串表示
     * @return 日程的字符串表示
     */
    @Override
    public String toString() {
        return "No." + this.id + ": " +
            this.date.year + "/" + this.date.month + "/" + this.date.day + " " +
            this.time.hour + ":" + this.time.minute + ":" + this.time.second;
    }

    // Java无法重载运算符，就只能用函数了
    /**
     * 比较当前日程是否比另一个日程更紧急
     * 比较顺序：年、月、日、时、分、秒
     * @param other 另一个日程
     * @return 如果当前日程更紧急则返回true，否则返回false
     */
    public boolean isUrgentThan(Schedule other) {
        if (this.date.year < other.date.year) return true;
        if (this.date.year > other.date.year) return false;
        if (this.date.month < other.date.month) return true;
        if (this.date.month > other.date.month) return false;
        if (this.date.day < other.date.day) return true;
        if (this.date.day > other.date.day) return false;
        if (this.time.hour < other.time.hour) return true;
        if (this.time.hour > other.time.hour) return false;
        if (this.time.minute < other.time.minute) return true;
        if (this.time.minute > other.time.minute) return false;
        if (this.time.second < other.time.second) return true;
        return this.time.second <= other.time.second;
    }

    //把字符串数组转化为整型数组，便于传参
    /**
     * 将字符串数组转换为整型数组
     * @param s 字符串数组
     * @return 转换后的整型数组
     */
    private static int[] toNumParams(String[] s) {
        int length = s.length;
        int[] result = new int[length];
        for (int i = 0; i < length; i++) {
        result[i] = Integer.parseInt(s[i]);
        }
        return result;
    }
}
