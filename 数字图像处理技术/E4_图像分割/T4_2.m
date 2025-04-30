% 读取图像
f = imread('polymersomes.tif');

% 显示原始图像
figure;
subplot(2, 2, 1);
imshow(f);
title('原始图像');

% 方法一：使用全局阈值方法
% 选择一个固定阈值（例如，图像灰度级的中间值）
T1 = 128;
g1 = f > T1;

% 显示全局阈值分割结果
subplot(2, 2, 2);
imshow(g1);
title(['全局阈值分割 (T = ', num2str(T1), ')']);

% 方法二：使用Otsu's最佳全局阈值方法
[T2, SM] = graythresh(f); % 计算Otsu's阈值
g2 = im2bw(f, T2);

% 显示Otsu's方法分割结果
subplot(2, 2, 3);
imshow(g2);
title(['Otsu''s方法分割 (T = ', num2str(T2), ')']);

% 对比两种方法
subplot(2, 2, 4);
imshow(abs(g1 - g2));
title('两种方法的差异');

% 输出阈值信息
fprintf('全局阈值: %d\n', T1);
fprintf('Otsu''s阈值: %f (归一化值)\n', T2);
fprintf('Otsu''s阈值对应灰度值: %d\n', round(T2*255));
fprintf('Otsu''s方法的分离度量: %f\n', SM);

% 添加总标题
sgtitle('全局阈值与Otsu''s方法对比');
