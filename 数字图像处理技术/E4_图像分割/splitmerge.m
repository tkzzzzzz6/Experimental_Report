function g=splitmerge(f,mindim,fun)%f是待分割的原图，mindim是定义分解中所允许的最小的块，必须是2的正整数次幂
 Q=2^nextpow2(max(size(f)));
 [M,N]=size(f);
 f=padarray(f,[Q-M,Q-N],'post');%：填充图像或填充数组。f是输入图像，输出是填充后的图像，先将图像填充到2的幂次以使后面的分解可行
 %然后是填充的行数和列数，post：表示在每一维的最后一个元素后填充,B = padarray(A,padsize,padval,direction)
 %不含padval就用0填充,Q代表填充后图像的大小。
 S=qtdecomp(f,@split_test,mindim,fun);%f传给split_test，qtdecomp divides a square image into four
% equal-sized square blocks, and then tests each block to see if it
% meets some criterion of homogeneity. If a block meets the criterion,
% it is not divided any further. If it does not meet the criterion,
% it is subdivided again into four blocks, and the test criterion is
% applied to those blocks. This process is repeated iteratively until
% each block meets the criterion. The result can have blocks of several
% different sizes.S是包含四叉树结构的稀疏矩阵，存储的值是块的大小及坐标，以稀疏矩阵形式存储
 Lmax=full(max(S(:)));%将以稀疏矩阵存储形式存储的矩阵变换成以普通矩阵（full matrix）形式存储，full，sparse只是存储形式的不同
 g=zeros(size(f));
 MARKER=zeros(size(f));
 
 for k=1:Lmax
     [vals,r,c]=qtgetblk(f,S,k);%vals是一个数组，包含f的四叉树分解中大小为k*k的块的值，是一个k*k*个数的矩阵，
     %个数是指S中有多少个这样大小的块，f是被四叉树分的原图像，r，c是对应的左上角块的坐标如2*2块，代表的是左上角开始块的坐标
     
     if ~isempty(vals)
         for I=1:length(r)
             xlow=r(I);
             ylow=c(I);
             xhigh=xlow+k-1;
             yhigh=ylow+k-1;
             region=f(xlow:xhigh,ylow:yhigh);%找到对应的区域
             flag=feval(fun,region);%evaluates the function handle, fhandle,using arguments x1 through xn.执行函数fun，region是参数
             if flag%如果返回的是1，则进行标记
                 g(xlow:xhigh,ylow:yhigh)=1;%然后将对应的区域置1
                 MARKER(xlow,ylow)=1;%MARKER矩阵对应的左上角坐标置1
             end
         end
     end
 end
 
 g=bwlabel(imreconstruct(MARKER,g));%imreconstruct默认2D图像8连通，这个函数就是起合的作用
 g=g(1:M,1:N);%返回原图像的大小
end

