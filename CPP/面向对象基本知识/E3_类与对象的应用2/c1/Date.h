#ifndef DATE_H
#define DATE_H

#include <iostream>

class Date
{
private:
    int year, month, day;
    int totalDays;             // 该日期是从公元元年1月1日开始的第几天
    static int SumOfMonth[12]; // 每月1日到1月1日的天数
public:
    Date(int year, int month, int day);
    inline int getYear() const { return year; }
    inline int getMonth() const { return month; }
    inline int getDay() const { return day; }
    void show() const;                    // 输出当前日期
    bool isLeapYear() const;              // 判断当年是否为闰年
    int distance(const Date &date) const; // 计算当前日期与指定日期之间相差天数
};

#endif
