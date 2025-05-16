#include "SavingsAccount.h"
#include <iostream>

using namespace std;

const double rate = 0.015;

int main()
{
    Date date1(2020, 11, 1);  // �����˻�����
    Date date2(2020, 11, 5);  // s0��һ�δ������
    Date date3(2020, 12, 5);  // s0�ڶ��δ������
    Date date4(2020, 11, 25); // s1��һ�δ������
    Date date5(2020, 12, 20); // s1ȡ������
    Date date6(2021, 1, 1);   // ��Ϣ����

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
    cout << "�����˻��ܽ��Ϊ: " << SavingsAccount::getTotal() << "Ԫ" << endl;

    return 0;
}