DATA SEGMENT
    X DW 1234H, 5678H    ; 双精度数 x (低位在前，高位在后)
    Y DW 2345H, 6789H    ; 双精度数 y (低位在前，高位在后)
    Z DW 1111H, 2222H    ; 双精度数 z (低位在前，高位在后)
    W DW 0, 0            ; 结果存放位置 (低位在前，高位在后)
    ; W对应的位置应该为000C
    CONST_24 DW 24, 0    ; 常数 24 (双精度)
DATA ENDS

CODE SEGMENT
    ASSUME CS:CODE, DS:DATA
START:
    MOV AX, DATA
    MOV DS, AX
    
    ; 计算 X + Y
    MOV AX, X            ; 加载 X 的低位
    ADD AX, Y            ; 加上 Y 的低位
    MOV BX, AX           ; 保存低位结果
    
    MOV AX, X+2          ; 加载 X 的高位
    ADC AX, Y+2          ; 带进位加上 Y 的高位
    MOV DX, AX           ; 保存高位结果
    
    ; 计算 (X + Y) + 24
    MOV AX, BX           ; 恢复低位结果
    ADD AX, CONST_24     ; 加上 24 的低位
    MOV BX, AX           ; 保存低位结果
    
    MOV AX, DX           ; 恢复高位结果
    ADC AX, CONST_24+2   ; 带进位加上 24 的高位
    MOV DX, AX           ; 保存高位结果
    
    ; 计算 (X + Y + 24) - Z
    MOV AX, BX           ; 恢复低位结果
    SUB AX, Z            ; 减去 Z 的低位
    MOV W, AX            ; 保存结果的低位
    
    MOV AX, DX           ; 恢复高位结果
    SBB AX, Z+2          ; 带借位减去 Z 的高位
    MOV W+2, AX          ; 保存结果的高位
    
    MOV AH, 4CH
    INT 21H
CODE ENDS
END START
