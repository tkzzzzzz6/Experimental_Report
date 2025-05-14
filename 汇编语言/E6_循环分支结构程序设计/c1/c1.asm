DATA  SEGMENT
  BUF  DB     70H,58H,50H,91H,99H,62H,75H,82H,74H,60H  ; 定义数据缓冲区，存储10个十六进制数
ORG  0020H 
P1  DB  0    ; 存储大于等于90H的数的个数
ORG  0030H
  P2  DB  0  ; 存储60H到90H之间的数的个数
ORG  0040H
  P3  DB  0  ; 存储小于60H的数的个数
ORG  0050H
COUNT   DW     10  ; 定义计数器，表示要处理的数据个数
DATA  ENDS


CSEG  SEGMENT
      ASSUME CS:CSEG,DS:DATA  ; 设置段寄存器
      
START:MOV    AX,DATA  ; 初始化数据段
      MOV    DS,AX
      MOV    CX,COUNT  ; 设置循环计数器
      MOV    SI,0      ; 初始化源变址寄存器
LP1:  ; 主循环开始
      MOV  AL,BUF[SI]  ; 取一个数据到AL
      CMP  AL,90H      ; 与90H比较
      JNB  LP2         ; 如果大于等于90H，跳转到LP2
      CMP   AL,60H     ; 与60H比较
      JC    LP3        ; 如果小于60H，跳转到LP3
      INC   P2         ; 60H到90H之间的数，P2加1
      JMP   LP5        ; 跳转到LP5
LP3:  INC   P3         ; 小于60H的数，P3加1
      JMP   LP5        ; 跳转到LP5
LP2:  INC   P1         ; 大于等于90H的数，P1加1
   
LP5:  INC   SI         ; 指向下一个数据
      LOOP  LP1        ; 循环处理下一个数据

      MOV    AH,4CH    ; 程序结束
      INT    21H
CSEG  ENDS
      END    START
