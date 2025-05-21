
DATA  SEGMENT
    ORG 0100H
  AA_1    DB     1,3,5,7,2,4,6
    ORG 0150H
  BB_1    DB     7 dup(?) 
  COUNT  DW     7 ;给7设置别名
DATA  ENDS
CSEG  SEGMENT
      ASSUME CS: CSEG,DS:DATA
START:MOV    AX, DATA
      MOV    DS, AX
      MOV    CX, COUNT
      LEA    SI, AA_1    ;取偏移地址(或者使用offset) 
      LEA    DI, BB_1     
LP1:  MOV    AL, [SI]   ;寄存器间接寻址方式可以改成相对寻址方式 
      ADD    AL,2       
      MOV    [DI], AL       
      INC SI    ;SI+1        
      INC DI    ;DI+1           
      LOOP  LP1 ;不是0就转到标号LP1
      LEA   SI, BB_1    
      MOV CX, COUNT
DISP:  MOV  DL, [SI]
ADD  DL, 30H        
MOV    AH,02    ;显示输出(要背下来)
INT    21H     
      MOV DL,' '   ;每显示输出一个数后，输出一个空格  
      MOV AH,2   
      INT 21H    
      INC   SI
      LOOP  DISP     
MOV    AH,4CH   
      INT    21H
CSEG  ENDS
      END    START
