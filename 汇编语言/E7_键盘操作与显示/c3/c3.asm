DATA SEGMENT
    DATAX DB 5      ; 带符号字节数据，负数
    DATAY DB -1      ; 带符号字节数据，正数
    RESULT DB 0
    BUF DB 5 DUP(?)
DATA ENDS

CSEG SEGMENT
    ASSUME CS:CSEG,DS:DATA
    ORG 100H
START:
      MOV AX,DATA
      MOV DS,AX

      ;从键盘接收+,-,*,/
      MOV AH,1
      INT 21H

      CMP AL,'+'
      JE ADD_OP
      CMP AL,'-'
      JE SUB_OP
      CMP AL,'*'
      JE MUL_OP
      CMP AL,'/'
      JE DIV_OP

ADD_OP:
      MOV AL,DATAX
      MOV BL,DATAY
      ADD AL,BL
      MOV RESULT,AL
      JMP SHOW_RESULT  
      
SUB_OP:
      MOV AL,DATAX
      MOV BL,DATAY
      SUB AL,BL
      MOV RESULT,AL
      JMP SHOW_RESULT  

MUL_OP:
; 字节乘法： 
; （AL）*（OPS8）→AX
      MOV AL,DATAX
      MOV BL,DATAY
      IMUL BL
      MOV RESULT,AL
      JMP SHOW_RESULT  

DIV_OP:
      MOV AL,DATAX
      MOV BL,DATAY
      CBW ;将AL符号扩展到AX
      IDIV BL
      MOV RESULT,AL
      JMP SHOW_RESULT  

;难点在打印,将每次除以10,把余数入栈,然后出栈,打印
SHOW_RESULT:
    MOV AL, RESULT
    CBW                 ; 符号扩展到AX
    CMP AX, 0           ; 只比较 AL
    JGE SHOW
    MOV DL, '-'
    MOV AH, 2
    INT 21H
    NEG AX             

SHOW:
    MOV DI,0            ; DI为BUF索引
    MOV BX,10

CONV_LOOP:
    MOV DX,0
    DIV BX
    ADD DL,'0'
    MOV BUF[DI],DL
    INC DI
    CMP AX,0
    JNZ CONV_LOOP

PRINT_LOOP:
    DEC DI
    MOV DL,BUF[DI]
    MOV AH,2
    INT 21H
    CMP DI,0
    JNZ PRINT_LOOP
    JMP EXIT

EXIT:
      MOV AH,4CH
      INT 21H
      
CSEG ENDS
      END START
