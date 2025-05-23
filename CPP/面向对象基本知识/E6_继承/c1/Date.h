#ifndef DATE_H
#define DATE_H

#include <iostream>

class Date
{
private:
    int year, month, day;
    int totalDays;             // �������Ǵӹ�ԪԪ��1��1�տ�ʼ�ĵڼ���
    static int SumOfMonth[12]; // ÿ��1�յ�1��1�յ�����
public:
    Date(int year, int month, int day);
    inline int getYear() const { return year; }
    inline int getMonth() const { return month; }
    inline int getDay() const { return day; }
    void show() const;                    // �����ǰ����
    bool isLeapYear() const;              // �жϵ����Ƿ�Ϊ����
    int distance(const Date &date) const; // ���㵱ǰ������ָ������֮���������
};

#endif
