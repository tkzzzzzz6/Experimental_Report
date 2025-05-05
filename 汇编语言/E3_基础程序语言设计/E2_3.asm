; 定义数据段
DATA SEGMENT
    X DW 5        ; 16位带符号数 x
    Y DW 10       ; 16位带符号数 y
    Z DW 100      ; 16位带符号数 z
    V DW 1000     ; 16位带符号数 v
    CONST_540 DW 540  ; 常数 540
    RESULT DW ?   ; 存放计算结果
DATA ENDS

; 定义代码段
CODE SEGMENT
    ASSUME CS:CODE, DS:DATA

START:
    ; 初始化数据段寄存器
    MOV AX, DATA
    MOV DS, AX

    ; 计算 x*y
    MOV AX, X     ; 将x加载到AX
    IMUL Y        ; 有符号乘法，结果在DX:AX
    
    ; 计算 x*y + z
    ADD AX, Z     ; 将z加到结果的低16位
    ADC DX, 0     ; 处理可能的进位
    
    ; 计算 x*y + z - 540
    SUB AX, CONST_540  ; 从结果中减去540
    SBB DX, 0     ; 处理可能的借位
    
    ; 保存 x*y + z - 540 的结果到BX:CX
    MOV BX, DX
    MOV CX, AX
    
    ; 计算 v - (x*y + z - 540)
    MOV AX, V     ; 将v加载到AX
    SUB AX, CX    ; 减去之前计算的结果的低16位
    MOV CX, AX    ; 保存结果的低16位到CX
    
    MOV AX, 0     ; 高16位设为0（因为v是16位数）
    SBB AX, BX    ; 减去之前计算的结果的高16位，考虑借位
    MOV BX, AX    ; 保存结果的高16位到BX
    
    ; 计算 (v - (x*y + z - 540)) / x
    ; 准备被除数 BX:CX
    MOV AX, CX    ; 将低16位移到AX
    MOV DX, BX    ; 将高16位移到DX
    
    ; 执行有符号除法
    IDIV X        ; 有符号除法，结果在AX，余数在DX
    
    ; 保存结果
    MOV RESULT, AX
    
    ; 程序退出
    MOV AH, 4CH
    INT 21H
CODE ENDS
    END START
