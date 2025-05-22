DATA SEGMENT
    num DB -6         ; 8位有符号数
    buf DB 4 DUP(?)   ; 最多3位+1
DATA ENDS

CODE SEGMENT
    ASSUME CS:CODE, DS:DATA
START:
    MOV AX, DATA
    MOV DS, AX

    MOV AL, num       ; 取数
    CBW               ; 符号扩展到AX

    CMP AL, 0
    JGE PRINT_DEC     ; 如果是正数，直接打印
    NEG AL            ; 取绝对值
    CBW

PRINT_DEC:
    ; AX中为正数，转十进制
    MOV SI, 0
    MOV BX, 10

CONV_LOOP:
    XOR DX, DX
    DIV BX            ; AX / 10, 商->AX, 余数->DX
    ADD DL, '0'
    MOV buf[SI], DL
    INC SI
    CMP AX, 0
    JNZ CONV_LOOP

PRINT_LOOP:
    DEC SI
    MOV DL, buf[SI]
    MOV AH, 2
    INT 21H
    CMP SI, 0
    JNZ PRINT_LOOP

    MOV AH, 4CH
    INT 21H
CODE ENDS
    END START 