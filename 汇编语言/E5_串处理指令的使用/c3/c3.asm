DATA SEGMENT
CONAME        DB    'SPACE EXPLORERS INC.'
PRLINE        DB    20 DUP (' ')
STUDENT_NAME  DB    'TanKe',25 DUP(' ')
STUDENT_ADDR  DB    '202306630'
PRINT_LINE    DB    50 DUP(' '), '$'  ; 初始化为空格并添加结束符
DATA ENDS

CODE SEGMENT
ASSUME CS:CODE, DS:DATA
MAIN PROC FAR
START:
    ; 设置数据段
    PUSH DS
    SUB AX, AX
    PUSH AX
    MOV AX, DATA
    MOV DS, AX
    MOV ES, AX

    ; (1) 从右到左把CONAME中的字符串传送到PRLINE
    LEA DI, PRLINE+19
    LEA SI, CONAME+19
    MOV CX, 20
    STD           ; 设置方向标志为递减
    REP MOVSB
    CLD           ; 恢复方向标志为递增

    ; (2) 把CONAME中的第3个字节装入AX
    MOV AL, CONAME+2
    MOV AH, 0

    ; (3) 把STUDENT_NAME移到PRINT_LINE的前30个字节中
    LEA SI, STUDENT_NAME
    LEA DI, PRINT_LINE
    MOV CX, 30    ; 移动30个字节
    CLD           ; 确保方向是向前的
    REP MOVSB

    ; 把STUDENT_ADDR移到PRINT_LINE的后9个字节中
    LEA SI, STUDENT_ADDR
    LEA DI, PRINT_LINE+41  ; 50-9=41，从PRINT_LINE的第41个位置开始放STUDENT_ADDR
    MOV CX, 9     ; STUDENT_ADDR长度为9
    CLD           ; 确保方向是向前的
    REP MOVSB

    ; 打印PRINT_LINE
    LEA DX, PRINT_LINE
    MOV AH, 9
    INT 21H

EXIT:
    RET
MAIN ENDP
CODE ENDS
END START