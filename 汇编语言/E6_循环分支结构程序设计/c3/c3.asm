data segment
   db 'ibm             '    ; 定义数据段，存储4个公司名称，每个占16字节
   db 'dec             '
   db 'dos             '
   db 'vax             '
data ends
code segment
assume cs:code,ds:data      ; 设置代码段和数据段
start: mov ax,data          ; 初始化数据段
       mov ds,ax
       mov bx,0             ; bx为基址
       mov cx,4             ; 外层循环4次（4个字符串）
s0:    mov si,0             ; si为偏移量
       mov dx,3             ; dx为内层循环计数器（每个字符串前3个字符）
s:     mov al,[bx+si]       ; 取字符串中的字符到al
       and al,11011111b     ; 将字符转换为大写字母（清除第5位）
       mov [bx+si],al       ; 将转换后的字符存回原位置
       inc si               ; 偏移量加1
       dec dx               ; 内层循环计数器减1
       jnz s                ; dx不为0则继续内层循环
       add bx,16            ; 基址加16，指向下一个字符串
       loop s0              ; 外层循环
       mov ax,4c00h         ; 程序返回DOS
       int 21h  
code ends
end start
