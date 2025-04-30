% 读取图像
f = imread('soceer');

% 使用分裂合并法进行图像分割
% 尝试不同的最小块大小参数
g1 = splitmerge(f, 8, @predicate);  % 最小块大小为8
g2 = splitmerge(f, 16, @predicate); % 最小块大小为16
g3 = splitmerge(f, 32, @predicate); % 最小块大小为32

% 显示原始图像和分割结果
figure;
subplot(2,2,1), imshow(f), title('原始图像');
subplot(2,2,2), imshow(g1), title('最小块大小=8');
subplot(2,2,3), imshow(g2), title('最小块大小=16');
subplot(2,2,4), imshow(g3), title('最小块大小=32');
