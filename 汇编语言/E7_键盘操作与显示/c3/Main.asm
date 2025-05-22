DATA SEGMENT
    DATAX DB 6      ; 带符号字节数据，负数
    DATAY DB -2      ; 带符号字节数据，正数
    RESULT DB 0
    BUF DB 4 DUP(?)
DATA ENDS

CSEG SEGMENT
    ASSUME CS:CSEG,DS:DATA
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
    CBW
    CMP AL, 0
    JGE SHOW
    PUSH AX           ; 保存原始AL
    MOV DL, '-'
    MOV AH, 2
    INT 21H
    POP AX            ; 恢复AL
    NEG AL
    CBW

SHOW:
    MOV SI,0            ; SI为BUF索引
    MOV BX,10

CONV_LOOP:
    MOV DX,0
    DIV BX
    ADD DL,'0'
    MOV BUF[SI],DL
    INC SI
    CMP AX,0
    JNZ CONV_LOOP

PRINT_LOOP:
    DEC SI
    MOV DL,BUF[SI]
    MOV AH,2
    INT 21H
    CMP SI,0
    JNZ PRINT_LOOP
    JMP EXIT

EXIT:
      MOV AH,4CH
      INT 21H
      
CSEG ENDS
      END START
