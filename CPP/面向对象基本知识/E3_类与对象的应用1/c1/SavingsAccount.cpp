#include <iostream>

using namespace std;

const double rate = 0.015;

class SavingsAccount
{
    private:
        int id;                      // �ʺ�
        double balance;              // ���
        double rate;                 // ������
        int lastDate;                // �ϴα����������
        double accumulation;         // �����ۼ�֮��
    public:
        SavingsAccount(int id, int date, double rate); // ���캯��
        double accumulate(int date); // ��õ�ָ������Ϊֹ�Ĵ������ۻ�ֵ
        void deposit(int date, double amount);         // �����ֽ�dateΪ���ڣ�amountΪ���
        void withdraw(int date, double amount);        // ȡ���ֽ�
        void settle(int date);                         // ������Ϣ��ÿ��1��1�յ���һ�θú���
        void show();                                   // ����˻���Ϣ
        int getId() { return id; }
        double getBalance() { return balance; }
        double getRate() { return rate; }
};
SavingsAccount::SavingsAccount(int _id, int _lastDate,double _rate) : id(_id), balance(0), rate(_rate), lastDate(_lastDate), accumulation(0){};

double SavingsAccount::accumulate(int date){
    accumulation += balance * (date - lastDate);
    lastDate = date;
    return accumulation;
}

void SavingsAccount::deposit(int date, double amount){
    accumulate(date);
    balance += amount;
    cout << date << "��" << "�Ѵ���" << amount << "Ԫ" << endl;
    cout << "��ǰ���Ϊ" << balance << "Ԫ" << endl;
}

void SavingsAccount::withdraw(int date, double amount){
    accumulate(date);
    balance -= amount;
    cout << date << "��" << "��ȡ��" << amount << "Ԫ" << endl;
    cout << "��ǰ���Ϊ" << balance << "Ԫ" << endl;
}

void SavingsAccount::settle(int date){
    double interest = (accumulate(date)/365) * rate;
    cout << "��ǰ��ϢΪ" << interest << "Ԫ" << endl;
}

void SavingsAccount::show(){
    cout << "�˺�:" << id << endl;
    cout << "��ǰ���Ϊ:" << balance << "Ԫ" << endl;
}

int main(){
    SavingsAccount sa0(1, 0, rate);
    SavingsAccount sa1(2, 0, rate);
    sa0.deposit(5, 5000);
    sa0.deposit(45, 5500);
    sa1.deposit(25, 10000);
    sa1.withdraw(60, 4000);
    sa0.settle(90);
    sa1.settle(90);
    sa0.show();
    sa1.show();
    return 0;
}