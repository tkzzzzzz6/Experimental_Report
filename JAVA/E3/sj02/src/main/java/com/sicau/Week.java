package com.sicau;
// 这段代码外层做防御,内层不做防御

public class Week {
    // private final String data[] = { "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日" };

    // public String getDays(int index) {
    //     return data[index];
    // }

    // public String toString() {
    //     return data[index];
    // }

    // 采用枚举
    private enum Days {
        星期一, 星期二, 星期三, 星期四, 星期五, 星期六, 星期日
    }

    public String getDays(int index) {
        return Days.values()[index].toString();
    }

    private int index;

    public String toString() {
        return Days.values()[index].toString();
    }

}
// 外层做防御,内层不做
// 一般web开发常用

// 外层和内层都做
// 高可靠性,资源开销大,例如金融系统

// 内层做防御,外层不做
// 系统级底层开发常用,但是内层极其复杂
