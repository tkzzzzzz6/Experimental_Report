CSEG SEGMENT
    ASSUME CS:CSEG
    ORG 100H
START:
      ; 从键盘接收一个小写字母
      MOV AH,1
      INT 21H

      MOV DL,AL
      DEC DL
      
      ; 设置循环次数
      MOV CX,3       ; 设置循环次数
      
LOOP1:
      ; 检查输入是否为小写字母
      CMP DL,'a'
      JB EXIT        ; 小于'a'就退出
      CMP DL,'z'
      JA EXIT        ; 大于'z'就退出
      
      MOV AH,2
      INT 21H
      
      ADD DL,1

      LOOP LOOP1     ; CX减1，若不为0则跳转到LOOP1继续循环
      
EXIT:
      MOV AH,4CH
      INT 21H
      
CSEG ENDS
      END START
