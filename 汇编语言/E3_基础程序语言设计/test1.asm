.MODEL SMALL
.STACK 100h

.DATA
    message DB 'Hello, Assembly! Environment test successful!$'

.CODE
MAIN PROC
    ; 设置数据段地址
    MOV AX, @DATA
    MOV DS, AX
    
    ; 显示消息
    MOV AH, 09h      ; DOS功能：显示字符串
    LEA DX, message  ; 加载消息地址
    INT 21h          ; 调用DOS中断
    
    ; 返回DOS
    MOV AH, 4Ch      ; DOS功能：结束程序
    INT 21h          ; 调用DOS中断
MAIN ENDP
END MAIN