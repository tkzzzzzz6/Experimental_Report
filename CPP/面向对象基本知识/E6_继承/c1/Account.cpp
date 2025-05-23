#include "Account.h"
#include <cmath>

using namespace std;

// Account��
double Account::total = 0;

Account::Account(int _id, const Date &_date)
    : id(_id), balance(0) {};

void Account::show() const
{
    cout << "�˺�:" << id << endl;
    cout << "��ǰ���Ϊ:" << balance << "Ԫ" << endl;
}

// SavingsAccount��
SavingsAccount::SavingsAccount(int _id, const Date &_date, double _rate)
    : Account(_id, _date), rate(_rate), lastDate(_date), accumulation(0) {};

double SavingsAccount::accumulate(const Date &date)
{
    int days = date.distance(lastDate);
    // ȷ������������
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
    cout << "SavingsAccount" + to_string(id) + ":�Ѵ���" << amount << "Ԫ" << "->" << "��ǰ���Ϊ" << balance << "Ԫ" << endl;
}

void SavingsAccount::withdraw(const Date &date, double amount)
{
    accumulate(date);
    balance -= amount;
    total -= amount;
    date.show();
    cout << "SavingsAccount" + to_string(id) + ":��ȡ��" << amount << "Ԫ" << "->" << "��ǰ���Ϊ" << balance << "Ԫ" << endl;
}

void SavingsAccount::settle(const Date &date)
{
    // �����վ����
    double dailyBalance = accumulate(date) / (date.isLeapYear() ? 366 : 365);
    // ������Ϣ
    double interest = dailyBalance * rate;
    balance += interest;
    total += interest;
    accumulation = 0;
    cout << "SavingsAccount" + to_string(id) + ":��ǰ��ϢΪ" << interest << "Ԫ" << endl;
}

// CreditAccount��
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
    // �Ƚ��㵽��ǰ�յ���Ϣ
    if (balance < 0)
    {
        int days = date.distance(lastDate);
        if (days > 0)
        {
            double interest = -balance * days * debtRate; // ȡ����ֵ
            balance -= interest;
            total -= interest;
        }
    }
    accumulate(date); // ���� accumulation �� lastDate
    balance += amount;
    total += amount;
    date.show();
    cout << "CreditAccount" + to_string(id) + ":�Ѵ���" << amount << "Ԫ" << "->" << "��ǰ���Ϊ" << balance << "Ԫ" << endl;
}

void CreditAccount::withdraw(const Date &date, double amount)
{
    accumulate(date);
    balance -= amount;
    total -= amount;
    date.show();
    cout << "CreditAccount" + to_string(id) + ":��ȡ��" << amount << "Ԫ" << "->" << "��ǰ���Ϊ" << balance << "Ԫ" << endl;
}

void CreditAccount::settle(const Date &date)
{
    double dailyBalance = accumulate(date) / (date.isLeapYear() ? 366 : 365);
    double interest = 0;
    if (balance < 0)
    {
        interest = dailyBalance * debtRate;
        balance -= interest; // Ƿ����Ϣ
        total -= interest;
    }
    balance -= fee; // ���
    total -= fee;
    accumulation = 0;
    cout << "CreditAccount" << to_string(id) << ":������ϢΪ" << interest << "Ԫ, ���" << fee << "Ԫ" << endl;
}

double CreditAccount::getAvailableCredit() const
{
    return credit - balance;
}
