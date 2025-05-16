#include "SavingsAccount.h"

using namespace std;

double SavingsAccount::total = 0;

SavingsAccount::SavingsAccount(int _id, const Date &_date, double _rate)
    : id(_id), balance(0), rate(_rate), lastDate(_date), accumulation(0) {};

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
    cout << " �Ѵ���" << amount << "Ԫ" << endl;
    cout << "��ǰ���Ϊ" << balance << "Ԫ" << endl;
}

void SavingsAccount::withdraw(const Date &date, double amount)
{
    accumulate(date);
    balance -= amount;
    total -= amount;
    date.show();
    cout << " ��ȡ��" << amount << "Ԫ" << endl;
    cout << "��ǰ���Ϊ" << balance << "Ԫ" << endl;
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
    cout << "��ǰ��ϢΪ" << interest << "Ԫ" << endl;
}

void SavingsAccount::show() const
{
    cout << "�˺�:" << id << endl;
    cout << "��ǰ���Ϊ:" << balance << "Ԫ" << endl;
}
