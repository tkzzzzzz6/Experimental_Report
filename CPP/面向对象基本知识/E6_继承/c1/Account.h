#ifndef ACCOUNT_H
#define ACCOUNT_H

#include "Date.h"

class Account
{
protected:
    int id;              // �ʺ�
    double balance;      // ���
    static double total; // �����˻����ܽ��
public:
    Account(int id, const Date &date); // ���캯��
    void show() const;                 // ����˻���Ϣ
    inline int getId() const { return id; }
    inline double getBalance() const { return balance; }
    static inline double getTotal() { return total; } // ��ȡ�����˻��ܽ��
};

class SavingsAccount : public Account
{
private:
    double rate;         // ������
    Date lastDate;       // �ϴα����������
    double accumulation; // �����ۼ�֮��
public:
    SavingsAccount(int id, const Date &date, double rate); // ���캯��
    double accumulate(const Date &date);                   // ��õ�ָ������Ϊֹ�Ĵ������ۻ�ֵ
    void deposit(const Date &date, double amount);         // �����ֽ�dateΪ���ڣ�amountΪ���
    void withdraw(const Date &date, double amount);        // ȡ���ֽ�
    void settle(const Date &date);                         // ������Ϣ��ÿ��1��1�յ���һ�θú���
    inline double getRate() const { return rate; }
};

class CreditAccount : public Account
{
private:
    double rate;            // ������
    Date lastDate;          // �ϴα����������
    double accumulation;    // �����ۼ�֮��
    double credit;          // ���ö��
    double debtRate;        // Ƿ���������
    double fee;             // ���ÿ����
public:
    double getDebt() const; // ���Ƿ���
    CreditAccount(int id, const Date &date, double rate, double credit, double debtRate, double fee); // ���캯��
    double accumulate(const Date &date);                                                              // ��õ�ָ������Ϊֹ�Ĵ������ۻ�ֵ
    void deposit(const Date &date, double amount);                                                    // �����ֽ�dateΪ���ڣ�amountΪ���
    void withdraw(const Date &date, double amount);                                                   // ȡ���ֽ�
    void settle(const Date &date);                                                                    // ������Ϣ��ÿ��1��1�յ���һ�θú���
    double getAvailableCredit() const;                                                                // ��ÿ������ö��
    inline double getRate() const { return rate; }
};

#endif
