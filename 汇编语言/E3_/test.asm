; ; Debug commands for the program
; -r                  ; Display registers
; -d ds:0 l 10        ; Display memory in data segment
; -t                  ; Trace execution
; -g                  ; Go (run program)
; 顺序结构程序设计

DATA SEGMENT
   A  DB  0         ; Variable A initialized to 0
   B  DB  0         ; Variable B initialized to 0
   CC  DB  30H,40H,50H  ; Array CC with 3 elements: 30H, 40H, 50H
;    每个变量定义的都是字节型的,在内存中占一个字节
DATA  ENDS
; 没有用到堆栈,所以没有用到SS寄存器
CODE  SEGMENT
     ASSUME  CS:CODE,DS:DATA  ; Set segment registers
START:
       MOV AX, DATA  ; Load DATA segment address to AX
       MOV DS, AX    ; Initialize DS register with DATA segment
       MOV AL,CC+1   ; Load second element of CC (40H) into AL
       ADD AL,CC     ; Add first element of CC (30H) to AL
       MOV A,AL      ; Store result in variable A
       MOV AL,CC+2   ; Load third element of CC (50H) into AL
       SUB AL,CC+1   ; Subtract second element (40H) from AL
       MOV B,AL      ; Store result in variable B
       ADD CC,10H    ; Add 10H to first element of CC
       ADD CC+1,20H  ; Add 20H to second element of CC
       MOV AL, A     ; Load value of A into AL
       ADD CC+2,AL   ; Add AL to third element of CC
EXIT:    MOV AH,4CH  ; DOS function: terminate program 
       INT 21H       ; Call DOS interrupt
    ;    程序的退出
CODE ENDS
       END START     ; End of program, execution starts at START
