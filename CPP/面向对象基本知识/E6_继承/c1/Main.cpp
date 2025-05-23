#include "Account.h"
#include <iostream>

using namespace std;

const double rate_s0s1 = 0.015;
const double rate_c0_debt = 0.0005;

int main()
{
    Date date_s0s1_0(2020, 11, 1); // s0s1创建账户日期

    Date date_s0_1(2020, 11, 5); // s0存入5000
    Date date_s0_2(2020, 12, 5); // s0存入5500

    Date date_s1_1(2020, 11, 25);  // s1存入10000
    Date date_s1_n1(2020, 12, 20); // s1取款4000

    Date date_c0_0(2020, 11, 1);  // c0创建账户时间
    Date date_c0_1(2020, 11, 15); // c0取款2000
    Date date_c0_2(2020, 12, 1);  // c0还清欠款

    Date date_all_settle(2021, 1, 1); // 结算利息

    SavingsAccount s0(1, date_s0s1_0, rate_s0s1);
    SavingsAccount s1(2, date_s0s1_0, rate_s0s1);
    CreditAccount c0(3, date_c0_0, rate_c0_debt, 10000, rate_c0_debt, 50);

    s0.deposit(date_s0_1, 5000);
    s0.deposit(date_s0_2, 5500);
    s1.deposit(date_s1_1, 10000);
    s1.withdraw(date_s1_n1, 4000);
    c0.withdraw(date_c0_1, 2000);
    // 打印现在c0的余额
    cout << "现在c0的余额为: " << c0.getBalance() << "元" << endl;
    c0.deposit(date_c0_2, c0.getDebt());

    s0.settle(date_all_settle);
    s1.settle(date_all_settle);
    c0.settle(date_all_settle);

    s0.show();
    s1.show();
    c0.show();
    cout << "所有账户总金额为: " << Account::getTotal() << "元" << endl;

    return 0;
}