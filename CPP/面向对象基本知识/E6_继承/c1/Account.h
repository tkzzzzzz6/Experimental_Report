#ifndef ACCOUNT_H
#define ACCOUNT_H

#include "Date.h"

class Account
{
protected:
    int id;              // 帐号
    double balance;      // 余额
    static double total; // 所有账户的总金额
public:
    Account(int id, const Date &date); // 构造函数
    void show() const;                 // 输出账户信息
    inline int getId() const { return id; }
    inline double getBalance() const { return balance; }
    static inline double getTotal() { return total; } // 获取所有账户总金额
};

class SavingsAccount : public Account
{
private:
    double rate;         // 年利率
    Date lastDate;       // 上次变更余额的日期
    double accumulation; // 余额按日累加之和
public:
    SavingsAccount(int id, const Date &date, double rate); // 构造函数
    double accumulate(const Date &date);                   // 获得到指定日期为止的存款金额按日累积值
    void deposit(const Date &date, double amount);         // 存入现金，date为日期，amount为金额
    void withdraw(const Date &date, double amount);        // 取出现金
    void settle(const Date &date);                         // 结算利息，每年1月1日调用一次该函数
    inline double getRate() const { return rate; }
};

class CreditAccount : public Account
{
private:
    double rate;            // 年利率
    Date lastDate;          // 上次变更余额的日期
    double accumulation;    // 余额按日累加之和
    double credit;          // 信用额度
    double debtRate;        // 欠款的日利率
    double fee;             // 信用卡年费
public:
    double getDebt() const; // 获得欠款额
    CreditAccount(int id, const Date &date, double rate, double credit, double debtRate, double fee); // 构造函数
    double accumulate(const Date &date);                                                              // 获得到指定日期为止的存款金额按日累积值
    void deposit(const Date &date, double amount);                                                    // 存入现金，date为日期，amount为金额
    void withdraw(const Date &date, double amount);                                                   // 取出现金
    void settle(const Date &date);                                                                    // 结算利息，每年1月1日调用一次该函数
    double getAvailableCredit() const;                                                                // 获得可用信用额度
    inline double getRate() const { return rate; }
};

#endif
