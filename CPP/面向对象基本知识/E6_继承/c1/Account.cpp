#include "Account.h"
#include <cmath>

using namespace std;

// Account类
double Account::total = 0;

Account::Account(int _id, const Date &_date)
    : id(_id), balance(0) {};

void Account::show() const
{
    cout << "账号:" << id << endl;
    cout << "当前余额为:" << balance << "元" << endl;
}

// SavingsAccount类
SavingsAccount::SavingsAccount(int _id, const Date &_date, double _rate)
    : Account(_id, _date), rate(_rate), lastDate(_date), accumulation(0) {};

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
    cout << "SavingsAccount" + to_string(id) + ":已存入" << amount << "元" << "->" << "当前余额为" << balance << "元" << endl;
}

void SavingsAccount::withdraw(const Date &date, double amount)
{
    accumulate(date);
    balance -= amount;
    total -= amount;
    date.show();
    cout << "SavingsAccount" + to_string(id) + ":已取出" << amount << "元" << "->" << "当前余额为" << balance << "元" << endl;
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
    cout << "SavingsAccount" + to_string(id) + ":当前利息为" << interest << "元" << endl;
}

// CreditAccount类
double CreditAccount::getDebt() const
{
    return balance < 0 ? -balance : 0;
}

CreditAccount::CreditAccount(int _id, const Date &_date, double _rate, double _credit, double _debtRate, double _fee)
    : Account(_id, _date), rate(_rate), lastDate(_date), accumulation(0), credit(_credit), debtRate(_debtRate), fee(_fee) {}

double CreditAccount::accumulate(const Date &date)
{
    int days = date.distance(lastDate);
    days = abs(days);
    accumulation += balance * days;
    lastDate = date;
    return accumulation;
}

void CreditAccount::deposit(const Date &date, double amount)
{
    // 先结算到当前日的利息
    if (balance < 0)
    {
        int days = date.distance(lastDate);
        if (days > 0)
        {
            double interest = -balance * days * debtRate; // 取绝对值
            balance -= interest;
            total -= interest;
        }
    }
    accumulate(date); // 更新 accumulation 和 lastDate
    balance += amount;
    total += amount;
    date.show();
    cout << "CreditAccount" + to_string(id) + ":已存入" << amount << "元" << "->" << "当前余额为" << balance << "元" << endl;
}

void CreditAccount::withdraw(const Date &date, double amount)
{
    accumulate(date);
    balance -= amount;
    total -= amount;
    date.show();
    cout << "CreditAccount" + to_string(id) + ":已取出" << amount << "元" << "->" << "当前余额为" << balance << "元" << endl;
}

void CreditAccount::settle(const Date &date)
{
    double dailyBalance = accumulate(date) / (date.isLeapYear() ? 366 : 365);
    double interest = 0;
    if (balance < 0)
    {
        interest = dailyBalance * debtRate;
        balance -= interest; // 欠款利息
        total -= interest;
    }
    balance -= fee; // 年费
    total -= fee;
    accumulation = 0;
    cout << "CreditAccount" << to_string(id) << ":本期利息为" << interest << "元, 年费" << fee << "元" << endl;
}

double CreditAccount::getAvailableCredit() const
{
    return credit - balance;
}
