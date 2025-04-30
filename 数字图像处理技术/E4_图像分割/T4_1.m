clear all,close all, clc
f=imread('fingerprint.tif');
%用全局阈值法分割
count=0;
T=mean2(f);
done=false;
while ~done
    count =count + 1;
    g=f>T;
    Tnext=0.5*(mean(f(g))+mean(f(~g)));
    done=abs(T-Tnext)<0.5;
    T=Tnext;
end
g1=im2bw(f,T/255);

% 显示原图和分割结果
figure('Name', '基本全局阈值分割');
subplot(1, 2, 1);
imshow(f);
title('原图');
subplot(1, 2, 2);
imshow(g1);
title(['分割结果 (阈值 T = ' num2str(T) ')']);

% 显示迭代次数
disp(['迭代次数: ' num2str(count)]);
disp(['最终阈值: ' num2str(T)]);