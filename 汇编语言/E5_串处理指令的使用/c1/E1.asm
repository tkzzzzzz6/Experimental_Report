DATA SEGMENT 
     STRING DB 'THE DATA IS FEB&03'    ; 定义字符串数据
     N     DW   $-STRING              ; 计算字符串长度
DATA ENDS
CODE SEGMENT
      ASSUME DS: DATA, CS: CODE       ; 设置段寄存器
START:
        MOV  AX,DATA                  ; 初始化数据段
        MOV  DS,AX
        MOV Es,AX;需要加上这句
        MOV  AL,'&'                   ; 设置要查找的字符'&'(如果是字类型的话需要送入AX)
        MOV  CX,N                     ; 设置循环计数器为字符串长度
        LEA  DI, STRING               ; 加载字符串首地址到DI(目的串)
        CLD                           ; 清除方向标志，使串操作向高地址方向进行
        REPNE SCASB                   ; 重复扫描直到找到'&'或CX=0
        JNZ  NOT_FOUND                ; 如果没找到，跳过替换
        DEC  DI                        ;扫描后，DI 会指向"下一个"字节,所以DI-1
        ; MOV  BYTE PTR [DI],' '         ; 将找到的'&'替换为空格
        MOV STRING[DI],' ' 
NOT_FOUND:
        MOV AH, 4CH                   ; 设置DOS功能号
        INT 21H                       ; 调用DOS中断返回
CODE ENDS
     END START                        ; 程序结束
