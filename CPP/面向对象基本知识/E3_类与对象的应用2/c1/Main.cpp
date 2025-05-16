#include "SavingsAccount.h"
#include <iostream>

using namespace std;

const double rate = 0.015;

int main()
{
    Date date1(2020, 11, 1);  // 创建账户日期
    Date date2(2020, 11, 5);  // s0第一次存款日期
    Date date3(2020, 12, 5);  // s0第二次存款日期
    Date date4(2020, 11, 25); // s1第一次存款日期
    Date date5(2020, 12, 20); // s1取款日期
    Date date6(2021, 1, 1);   // 计息日期

    SavingsAccount s0(1, date1, rate);
    SavingsAccount s1(2, date1, rate);

    s0.deposit(date2, 5000);
    s0.deposit(date3, 5500);
    s1.deposit(date4, 10000);
    s1.withdraw(date5, 4000);

    s0.settle(date6);
    s1.settle(date6);

    s0.show();
    s1.show();
    cout << "所有账户总金额为: " << SavingsAccount::getTotal() << "元" << endl;

    return 0;
}