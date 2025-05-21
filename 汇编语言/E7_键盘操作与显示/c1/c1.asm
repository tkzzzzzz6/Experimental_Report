DATA  SEGMENT                    ; 数据段定义开始
    ORG 0100H                    ; 设置数据段起始地址为0100H
  AA_1    DB     1,3,5,7,2,4,6   ; 定义源数组AA_1，包含7个字节数据
    ORG 0150H                    ; 设置后续数据的起始地址为0150H
  BB_1    DB     7 dup(?)        ; 定义目标数组BB_1，预留7个字节空间
  COUNT  DW     7                ; 定义常量COUNT为7，表示数组长度
DATA  ENDS                       ; 数据段定义结束

CSEG  SEGMENT                    ; 代码段定义开始
      ASSUME CS: CSEG,DS:DATA    ; 设置段寄存器关联
START:MOV    AX, DATA            ; 将数据段地址送入AX
      MOV    DS, AX              ; 初始化数据段寄存器DS
      MOV    CX, COUNT           ; 将循环次数7送入CX寄存器
      LEA    SI, AA_1            ; 将源数组AA_1的偏移地址送入SI
      LEA    DI, BB_1            ; 将目标数组BB_1的偏移地址送入DI
LP1:  MOV    AL, [SI]            ; 将源数组元素送入AL
      ADD    AL,2                ; 将AL中的值加2
      MOV    [DI], AL            ; 将修改后的值存入目标数组
      INC SI                     ; SI指针加1，指向下一个源数组元素
      INC DI                     ; DI指针加1，指向下一个目标数组位置
      LOOP  LP1                  ; CX减1，若不为0则跳转到LP1继续循环
      LEA   SI, BB_1             ; 将目标数组BB_1的偏移地址送入SI，准备显示
      MOV CX, COUNT              ; 重新设置循环计数器为7
DISP:  MOV  DL, [SI]             ; 将目标数组元素送入DL，准备显示
ADD  DL, 30H                     ; 将数字转换为ASCII码（加30H转为对应的字符）
MOV    AH,02                     ; 设置功能号02H，表示显示输出
INT    21H                       ; 调用DOS中断，显示DL中的字符
      MOV DL,' '                 ; 将空格字符送入DL
      MOV AH,2                   ; 设置功能号02H，表示显示输出
      INT 21H                    ; 调用DOS中断，显示空格
      INC   SI                   ; SI指针加1，指向下一个要显示的元素
      LOOP  DISP                 ; CX减1，若不为0则跳转到DISP继续显示
MOV    AH,4CH                    ; 设置功能号4CH，表示程序终止
      INT    21H                 ; 调用DOS中断，返回操作系统
CSEG  ENDS                       ; 代码段定义结束
      END    START               ; 程序结束，指定入口点为START
