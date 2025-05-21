DATAS SEGMENT
    data db -6          ; 定义一个字节，值为-6
    buf db 5 dup(?)     ; 定义一个5字节的缓冲区，用于存储ASCII码数字
DATAS ENDS

CODES SEGMENT
    ASSUME CS:CODES,DS:DATAS    ; 指定代码段和数据段寄存器
START:
    MOV AX,DATAS        ; 将数据段地址加载到AX寄存器
    MOV DS,AX           ; 初始化DS寄存器为数据段地址
    mov di,0            ; 初始化DI为buf的索引（从0开始）
    mov al,data         ; 将data的值（-6）加载到AL寄存器
    cbw                 ; 将AL符号扩展到AX
    cmp al,0            ; 比较AL与0，检查是否为负数
    jg  lng             ; 如果AL > 0，跳转到lng（跳过取绝对值）
    neg al              ; 如果AL < 0，取AL的绝对值
    mov ah,0            ; 清除AH寄存器
lng:
    mov dx,0            ; 清除DX寄存器，为除法准备（被除数高位）
    mov bx,10           ; 设置BX为10，用于十进制除法
    div bx              ; 用AX除以10，商在AX，余数在DX
    add dl,30h          ; 将余数转换为ASCII码数字（加'0'的ASCII值）
    mov buf[di],dl      ; 将ASCII码数字存储到buf[di]
    inc di              ; 增加DI索引，指向下一个buf位置
    cmp ax,0            ; 检查商是否为0
    jnz lng             ; 如果商不为0，继续循环转换下一位
fpx:
    dec di              ; 减少DI索引，指向最后一个存储的数字
    mov dl,buf[di]      ; 将buf[di]中的ASCII码数字加载到DL
    mov ah,02h          ; 设置AH为02h，调用DOS中断输出单个字符
    int 21h             ; 调用DOS中断，打印DL中的字符
    cmp di,0            ; 检查DI是否为0（是否打印完所有数字）
    jnz fpx             ; 如果DI不为0，继续循环打印前一个数字
    mov dl,' '          ; 设置DL为逗号的ASCII码（2Ch）
    mov ah,02h          ; 设置AH为02h，调用DOS中断输出逗号
    int 21h             ; 调用DOS中断，打印逗号
    MOV AH,4CH          ; 设置AH为4Ch，调用DOS中断退出程序
    INT 21H             ; 调用DOS中断，终止程序
CODES ENDS
    END START