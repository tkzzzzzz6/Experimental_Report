#include "Account.h"
#include <iostream>

using namespace std;

const double rate_s0s1 = 0.015;
const double rate_c0_debt = 0.0005;

int main()
{
    Date date_s0s1_0(2020, 11, 1); // s0s1�����˻�����

    Date date_s0_1(2020, 11, 5); // s0����5000
    Date date_s0_2(2020, 12, 5); // s0����5500

    Date date_s1_1(2020, 11, 25);  // s1����10000
    Date date_s1_n1(2020, 12, 20); // s1ȡ��4000

    Date date_c0_0(2020, 11, 1);  // c0�����˻�ʱ��
    Date date_c0_1(2020, 11, 15); // c0ȡ��2000
    Date date_c0_2(2020, 12, 1);  // c0����Ƿ��

    Date date_all_settle(2021, 1, 1); // ������Ϣ

    SavingsAccount s0(1, date_s0s1_0, rate_s0s1);
    SavingsAccount s1(2, date_s0s1_0, rate_s0s1);
    CreditAccount c0(3, date_c0_0, rate_c0_debt, 10000, rate_c0_debt, 50);

    s0.deposit(date_s0_1, 5000);
    s0.deposit(date_s0_2, 5500);
    s1.deposit(date_s1_1, 10000);
    s1.withdraw(date_s1_n1, 4000);
    c0.withdraw(date_c0_1, 2000);
    // ��ӡ����c0�����
    cout << "����c0�����Ϊ: " << c0.getBalance() << "Ԫ" << endl;
    c0.deposit(date_c0_2, c0.getDebt());

    s0.settle(date_all_settle);
    s1.settle(date_all_settle);
    c0.settle(date_all_settle);

    s0.show();
    s1.show();
    c0.show();
    cout << "�����˻��ܽ��Ϊ: " << Account::getTotal() << "Ԫ" << endl;

    return 0;
}