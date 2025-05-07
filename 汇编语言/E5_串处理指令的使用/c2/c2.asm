DATA SEGMENT
STRING1  DB  'Visual C++  '    ;定义第一个字符串
STRING2  DB  'Visual Basic'    ;定义第二个字符串
COUNT   EQU  $-STRING2         ;计算串长度,EQU表示给COUNT赋值为STRING2的长度,伪指令
MESS1   DB  'MATCH!',13,10,'$' ;定义匹配成功时的提示信息,13->回车,10->换行.$->字符串结束标志
MESS2   DB  'NO MATCH!',13,10,'$' ;定义匹配失败时的提示信息
DATA ENDS     
CODE SEGMENT 
ASSUME CS:CODE,DS:DATA,ES:DATA ;设置段寄存器
MAIN PROC FAR                   ;远调用,把主程序部分以过程的形式定义,有多个子程序的时候可以使用上面的方法
START:
   PUSH DS                      ;压栈,保存寄存器
   SUB AX, AX                   ;AX=0,清空AX
   PUSH AX                      ;压栈,保存寄存器
   MOV AX, DATA                 ;初始化数据段
   MOV DS, AX                   ;设置DS指向数据段
   MOV ES, AX                   ;设置ES指向数据段
; MAIN PROGRAM
   MOV CX, COUNT                ;将字符串长度送入CX
   LEA SI, STRING1              ;将STRING1的偏移地址送入SI
   LEA DI, STRING2              ;将STRING2的偏移地址送入DI
   CLD                          ;清除方向标志位,使串操作按递增方向进行
   REPZ  CMPSB                  ;重复比较两个字符串,直到不相等或CX=0
   JZ MATCH                     ;如果ZF=1(字符串相等),跳转到MES1
   MOV DX, OFFSET MESS2         ;否则,将MESS1的偏移地址送入DX
   JMP DISP0                    ;跳转到DISP0
MATCH:  MOV DX, OFFSET MESS1     ;将MESS2的偏移地址送入DX
DISP0:  MOV AH,9                ;设置DOS功能号9(显示字符串)
   INT 21H                      ;调用DOS中断
EXIT:   RET                     ;返回DOS,程序结束
MAIN ENDP
CODE ENDS
   END START                    ;程序结束,从START开始执行
