DATA  SEGMENT
  X  DW  6    ; 定义变量x，初始值为4
  Y  DW  6    ; 定义变量y，初始值为1
  Z  DW  6    ; 定义变量z，初始值为0
  W  DW  0    ; 定义变量w，初始值为0
DATA  ENDS

CODE  SEGMENT
  ASSUME CS:CODE,DS:DATA    ; 设置代码段和数据段
START: 
  MOV  AX,DATA              ; 初始化数据段
  MOV  DS,AX
  MOV  AX,X                 ; 将x的值送入ax
  CMP  AX,0                 ; 比较x是否为0
  JZ   HASZERO             ; 如果x为0，跳转到HASZERO
  MOV  AX,Y                 ; 将y的值送入ax
  CMP  AX,0                 ; 比较y是否为0
  JZ   HASZERO             ; 如果y为0，跳转到HASZERO
  MOV  AX,Z                 ; 将z的值送入ax
  CMP  AX,0                 ; 比较z是否为0
  JZ   HASZERO             ; 如果z为0，跳转到HASZERO
  MOV  AX,W                 ; 将w的值送入ax
  ADD  AX,Z                 ; w = w + z
  ADD  AX,Y                 ; w = w + y
  ADD  AX,X                 ; w = w + x
  MOV  W,AX                 ; 将计算结果存回w
  JMP  TOEND                  ; 跳转到程序结束
HASZERO:
  MOV  AX,0                 ; 将0送入ax
  MOV  X,AX                 ; x = 0
  MOV  Y,AX                 ; y = 0
  MOV  Z,AX                 ; z = 0
TOEND:
  MOV  AX,4C00H             ; 程序返回DOS
  INT  21H  
CODE  ENDS
  END  START