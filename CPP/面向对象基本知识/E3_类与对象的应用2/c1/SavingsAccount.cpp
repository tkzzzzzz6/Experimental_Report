#include "SavingsAccount.h"

using namespace std;

double SavingsAccount::total = 0;

SavingsAccount::SavingsAccount(int _id, const Date &_date, double _rate)
    : id(_id), balance(0), rate(_rate), lastDate(_date), accumulation(0) {};

double SavingsAccount::accumulate(const Date &date)
{
    int days = date.distance(lastDate);
    // 确保天数是正的
    days = abs(days);
    accumulation += balance * days;
    lastDate = date;
    return accumulation;
}

void SavingsAccount::deposit(const Date &date, double amount)
{
    accumulate(date);
    balance += amount;
    total += amount;
    date.show();
    cout << " 已存入" << amount << "元" << endl;
    cout << "当前余额为" << balance << "元" << endl;
}

void SavingsAccount::withdraw(const Date &date, double amount)
{
    accumulate(date);
    balance -= amount;
    total -= amount;
    date.show();
    cout << " 已取出" << amount << "元" << endl;
    cout << "当前余额为" << balance << "元" << endl;
}

void SavingsAccount::settle(const Date &date)
{
    // 计算日均余额
    double dailyBalance = accumulate(date) / (date.isLeapYear() ? 366 : 365);
    // 计算利息
    double interest = dailyBalance * rate;
    balance += interest;
    total += interest;
    accumulation = 0;
    cout << "当前利息为" << interest << "元" << endl;
}

void SavingsAccount::show() const
{
    cout << "账号:" << id << endl;
    cout << "当前余额为:" << balance << "元" << endl;
}
