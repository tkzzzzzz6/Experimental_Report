#include "Date.h"

using namespace std;

int Date::SumOfMonth[12] = {0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334};

Date::Date(int year, int month, int day) : year(year), month(month), day(day), totalDays(0) {};

void Date::show() const
{
    cout << year << "年" << month << "月" << day << "日";
}

bool Date::isLeapYear() const
{
    return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
}

int Date::distance(const Date &date) const
{
    // 计算从基准日期（第1年）到当前日期的天数
    int current_days = (year - 1) * 365;
    for (int i = 1; i < year; i++)
    {
        if ((i % 4 == 0 && i % 100 != 0) || (i % 400 == 0))
            current_days++;
    }
    current_days += SumOfMonth[month - 1] + day;
    if (isLeapYear() && month > 2)
        current_days++;

    // 计算从基准日期（第1年）到参数日期的天数
    int param_days = (date.year - 1) * 365;
    for (int i = 1; i < date.year; i++)
    {
        if ((i % 4 == 0 && i % 100 != 0) || (i % 400 == 0))
            param_days++;
    }
    param_days += SumOfMonth[date.month - 1] + date.day;
    if (date.isLeapYear() && date.month > 2)
        param_days++;

    // 返回两个日期之间的差值
    return param_days - current_days;
}