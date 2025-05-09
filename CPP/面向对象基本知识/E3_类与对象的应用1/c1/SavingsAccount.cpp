#include <iostream>

using namespace std;

const double rate = 0.015;

class SavingsAccount
{
    private:
        int id;                      // 帐号
        double balance;              // 余额
        double rate;                 // 年利率
        int lastDate;                // 上次变更余额的日期
        double accumulation;         // 余额按日累加之和
    public:
        SavingsAccount(int id, int date, double rate); // 构造函数
        double accumulate(int date); // 获得到指定日期为止的存款金额按日累积值
        void deposit(int date, double amount);         // 存入现金，date为日期，amount为金额
        void withdraw(int date, double amount);        // 取出现金
        void settle(int date);                         // 结算利息，每年1月1日调用一次该函数
        void show();                                   // 输出账户信息
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
    cout << date << "日" << "已存入" << amount << "元" << endl;
    cout << "当前余额为" << balance << "元" << endl;
}

void SavingsAccount::withdraw(int date, double amount){
    accumulate(date);
    balance -= amount;
    cout << date << "日" << "已取出" << amount << "元" << endl;
    cout << "当前余额为" << balance << "元" << endl;
}

void SavingsAccount::settle(int date){
    double interest = (accumulate(date)/365) * rate;
    cout << "当前利息为" << interest << "元" << endl;
}

void SavingsAccount::show(){
    cout << "账号:" << id << endl;
    cout << "当前余额为:" << balance << "元" << endl;
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