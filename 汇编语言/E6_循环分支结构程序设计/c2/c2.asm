data segment
   db '1. display......'    ; 定义数据段，存储字符串
data ends
code segment
assume cs:code,ds:data      ; 设置代码段和数据段
start:   mov ax,data        ; 初始化数据段
       mov ds,ax
       mov bx,0             ; bx置0，作为基址
       mov cx,4             ; 设置循环次数为4
	   mov si,0             ; si置0，作为偏移量
  s:   mov al,[bx+si+3]     ; 取字符串中的字符到al
       and al,11011111b     ; 将字符转换为大写字母（清除第5位）
       mov [bx+si+3],al     ; 将转换后的字符存回原位置
       inc si               ; 偏移量加1
       loop s               ; 循环处理下一个字符
       
       mov ax,4c00h         ; 程序返回DOS
       int 21h  
code ends
end start
