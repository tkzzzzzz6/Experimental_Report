; 定义数据段
DATA SEGMENT
    ARRAY DW 23, 36, 2, 100, 32000, 54, 11  ; 七个字数据的数组
    ZERO  DW ?                              ; 未初始化的字单元
DATA ENDS

; 定义代码段
CODE SEGMENT
    ASSUME CS:CODE, DS:DATA

START:
    ; 初始化数据段寄存器
    MOV AX, DATA
    MOV DS, AX

    ; (1) 如果BX包含数组ARRAY的初始地址
    LEA BX, ARRAY           ; 将ARRAY的地址加载到BX
    MOV AX, [BX+12]         ; 获取ARRAY中偏移量为12的数据(即11)
    MOV ZERO, AX            ; 将11存储到ZERO单元

    ; 可以添加一些指令来查看结果
    MOV AX, ZERO            ; 将ZERO的值加载到AX

    ; (2) 如果BX包含数据11在数组中的偏移量
    MOV BX, 12              ; 11在数组中的偏移量是12(6*2=12，因为每个DW占2字节)
    MOV AX, ARRAY[BX]       ; 获取ARRAY中偏移量为BX的数据(即11)
    MOV ZERO, AX            ; 将11存储到ZERO单元

    ; 程序退出
    MOV AH, 4CH
    INT 21H
CODE ENDS
    END START
