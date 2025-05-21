; 简单计算器程序 - 对DATAX和DATAY中的有符号字节数据进行四则运算
; 支持加(+)、减(-)、乘(*)、除(/)四种运算符

.MODEL SMALL
.STACK 100H

.DATA
DATAX   DB  ?           ; 第一个操作数
DATAY   DB  ?           ; 第二个操作数
MSG1    DB  'Input first number: $'
MSG2    DB  0DH, 0AH, 'Input second number: $'
MSG3    DB  0DH, 0AH, 'Input operator (+, -, *, /): $'
MSG4    DB  0DH, 0AH, 'Result: $'
MSG5    DB  0DH, 0AH, 'Division by zero! $'
TEMP    DW  ?           ; 临时存储乘法或除法结果
NEG_FLAG DB  0           ; 负数标志 (1表示结果为负)
BUF     DB  6 DUP(?)    ; 用于存储结果字符串
NEG_INPUT DB 0          ; 输入负号标志
VALUE     DB 0          ; 输入值

.CODE
MAIN PROC
    MOV AX, @DATA       ; 初始化数据段
    MOV DS, AX

    ; 显示第一条提示消息
    LEA DX, MSG1
    MOV AH, 09H
    INT 21H

    ; 输入第一个数字
    CALL INPUT_SIGNED_BYTE
    MOV DATAX, AL

    ; 显示第二条提示消息
    LEA DX, MSG2
    MOV AH, 09H
    INT 21H

    ; 输入第二个数字
    CALL INPUT_SIGNED_BYTE
    MOV DATAY, AL

    ; 显示操作符提示消息
    LEA DX, MSG3
    MOV AH, 09H
    INT 21H

    ; 输入操作符
    MOV AH, 01H
    INT 21H
    MOV BL, AL          ; 保存操作符在BL中

    ; 显示结果提示消息
    LEA DX, MSG4
    MOV AH, 09H
    INT 21H

    ; 根据操作符执行相应运算
    CMP BL, '+'         ; 检查是否为加法
    JE  DO_ADD
    CMP BL, '-'         ; 检查是否为减法
    JE  DO_SUB
    CMP BL, '*'         ; 检查是否为乘法
    JE  DO_MUL
    CMP BL, '/'         ; 检查是否为除法
    JE  DO_DIV
    JMP EXIT            ; 如果不是有效操作符，直接退出

DO_ADD:
    MOV AL, DATAX       ; 加法运算
    ADD AL, DATAY
    JMP DISPLAY_RESULT

DO_SUB:
    MOV AL, DATAX       ; 减法运算
    SUB AL, DATAY
    JMP DISPLAY_RESULT

DO_MUL:
    MOV AL, DATAX       ; 乘法运算
    MOV BL, DATAY
    CALL SIGNED_MUL
    JMP DISPLAY_AX

DO_DIV:
    MOV AL, DATAX       ; 除法运算
    MOV BL, DATAY
    CALL SIGNED_DIV
    JMP DISPLAY_AX

DISPLAY_RESULT:
    ; 结果在AL中，转换为字符串并显示
    MOV AH, 0           ; 清零AH，结果扩展到AX
    CMP AL, 0
    JGE POSITIVE
    NEG AL              ; 如果为负，取绝对值
    MOV NEG_FLAG, 1     ; 设置负数标志
    JMP CONTINUE
POSITIVE:
    MOV NEG_FLAG, 0     ; 清除负数标志
CONTINUE:
    MOV AX, AX          ; AX中现在是结果的绝对值

DISPLAY_AX:
    ; 显示AX中的有符号结果
    CALL DISPLAY_SIGNED_NUM
    
EXIT:
    ; 程序结束
    MOV AH, 4CH
    INT 21H
MAIN ENDP

; 输入有符号字节的过程
INPUT_SIGNED_BYTE PROC
    ; 检查第一个字符是否为负号
    MOV AH, 01H
    INT 21H
    CMP AL, '-'
    JNE CHECK_DIGIT1
    MOV NEG_INPUT, 1    ; 设置负号标志
    MOV AH, 01H         ; 再次读取一个字符
    INT 21H
    
CHECK_DIGIT1:
    ; 检查输入字符是否为数字
    CMP AL, '0'
    JL EXIT_INPUT       ; 如果小于'0'，不是数字
    CMP AL, '9'
    JG EXIT_INPUT       ; 如果大于'9'，不是数字
    
    ; 转换为数值并保存
    SUB AL, '0'
    MOV VALUE, AL
    
    ; 读取可能存在的第二个数字
    MOV AH, 01H
    INT 21H
    
    ; 检查第二个字符是否为数字
    CMP AL, '0'
    JL EXIT_INPUT2      ; 如果小于'0'，不是数字
    CMP AL, '9'
    JG EXIT_INPUT2      ; 如果大于'9'，不是数字
    
    ; 处理第二个数字
    SUB AL, '0'
    MOV BL, VALUE       ; 将第一个数字移到BL
    MOV BH, 0           ; 清零BH
    MOV CX, 10
    MUL CX              ; 将BX乘以10
    ADD BL, AL          ; 加上第二个数字
    MOV VALUE, BL
    JMP EXIT_INPUT
    
EXIT_INPUT2:
    ; 如果第二个字符不是数字，将它放回缓冲区(模拟未读取)
    MOV DL, AL
    MOV AH, 02H
    INT 21H
    
EXIT_INPUT:
    ; 返回结果
    MOV AL, VALUE
    CMP NEG_INPUT, 1
    JNE RETURN_INPUT
    NEG AL              ; 如果有负号标志，取负值
    
RETURN_INPUT:
    RET
INPUT_SIGNED_BYTE ENDP

; 有符号乘法过程 - 结果在AX中
SIGNED_MUL PROC
    ; 保存符号
    MOV CL, AL          ; 保存第一个操作数
    MOV CH, BL          ; 保存第二个操作数
    
    ; 取绝对值
    CMP AL, 0
    JGE ABS1_DONE
    NEG AL
ABS1_DONE:
    CMP BL, 0
    JGE ABS2_DONE
    NEG BL
ABS2_DONE:
    
    ; 执行无符号乘法
    MUL BL              ; AX = AL * BL
    
    ; 确定结果符号
    MOV BH, 0           ; 清零BH
    MOV BL, 0           ; 假设结果为正
    
    CMP CL, 0           ; 检查第一个操作数符号
    JGE CHECK_OP2_MUL
    XOR BL, 1           ; 翻转符号位
    
CHECK_OP2_MUL:
    CMP CH, 0           ; 检查第二个操作数符号
    JGE APPLY_SIGN_MUL
    XOR BL, 1           ; 翻转符号位
    
APPLY_SIGN_MUL:
    CMP BL, 1           ; 检查是否需要取负
    JNE RETURN_MUL
    NEG AX              ; 对结果取负
    
RETURN_MUL:
    RET
SIGNED_MUL ENDP

; 有符号除法过程 - 结果在AX中
SIGNED_DIV PROC
    ; 检查除数是否为零
    CMP BL, 0
    JNE NOT_ZERO_DIV
    
    ; 除数为零处理
    LEA DX, MSG5        ; 显示除零错误消息
    MOV AH, 09H
    INT 21H
    MOV AX, 0           ; 返回0
    RET
    
NOT_ZERO_DIV:
    ; 保存符号
    MOV CL, AL          ; 保存第一个操作数
    MOV CH, BL          ; 保存第二个操作数
    
    ; 取绝对值
    CMP AL, 0
    JGE ABS1_DIV_DONE
    NEG AL
ABS1_DIV_DONE:
    CMP BL, 0
    JGE ABS2_DIV_DONE
    NEG BL
ABS2_DIV_DONE:
    
    ; 执行无符号除法
    MOV AH, 0           ; 扩展AL到AX
    DIV BL              ; AL = AX / BL, AH = AX % BL
    MOV AH, 0           ; 清零AH(我们只关心商)
    
    ; 确定结果符号
    MOV BH, 0           ; 清零BH
    MOV BL, 0           ; 假设结果为正
    
    CMP CL, 0           ; 检查第一个操作数符号
    JGE CHECK_OP2_DIV
    XOR BL, 1           ; 翻转符号位
    
CHECK_OP2_DIV:
    CMP CH, 0           ; 检查第二个操作数符号
    JGE APPLY_SIGN_DIV
    XOR BL, 1           ; 翻转符号位
    
APPLY_SIGN_DIV:
    CMP BL, 1           ; 检查是否需要取负
    JNE RETURN_DIV
    NEG AX              ; 对结果取负
    
RETURN_DIV:
    RET
SIGNED_DIV ENDP

; 显示有符号数字
DISPLAY_SIGNED_NUM PROC
    ; 检查符号
    CMP AX, 0
    JGE POS_NUM
    
    ; 显示负号
    MOV DL, '-'
    MOV AH, 02H
    INT 21H
    NEG AX              ; 取绝对值
    
POS_NUM:
    ; 将数字转换为字符串并显示
    MOV CX, 0           ; 初始化计数器
    MOV BX, 10          ; 基数(十进制)
    
    ; 将数字转换为字符串(逆序)
CONVERT_LOOP:
    MOV DX, 0           ; 清零DX
    DIV BX              ; AX / 10, 商在AX，余数在DX
    PUSH DX             ; 保存余数
    INC CX              ; 增加计数器
    CMP AX, 0           ; 检查商是否为0
    JNE CONVERT_LOOP    ; 如果不是0，继续转换
    
    ; 显示字符串(正序)
DISPLAY_LOOP:
    POP DX              ; 取出一个数字
    ADD DL, '0'         ; 转换为ASCII
    MOV AH, 02H         ; 显示字符功能
    INT 21H
    LOOP DISPLAY_LOOP   ; 循环直到所有数字显示完毕
    
    RET
DISPLAY_SIGNED_NUM ENDP

END MAIN