% 边缘检测算子性能对比实验

% 1）读图像，并进行数据类型的转换
I = imread('bld.png');
I = im2double(rgb2gray(I));

% 2）用不同的算子进行边缘检测
[BW1, tp] = edge(I, 'prewitt');
[BW2, tc] = edge(I, 'canny');
[BW3, ts] = edge(I, 'sobel');
[BW4, tr] = edge(I, 'roberts');
[BW5, tl] = edge(I, 'log');

% 3）显示结果 - 使用子图进行更好的可视化对比
figure('Name', '边缘检测算子对比', 'Position', [100, 100, 1000, 600]);

subplot(2, 3, 1);
imshow(I);
title('原图');

subplot(2, 3, 2);
imshow(BW1);
title('Prewitt算子');

subplot(2, 3, 3);
imshow(BW2);
title('Canny算子');

subplot(2, 3, 4);
imshow(BW3);
title('Sobel算子');

subplot(2, 3, 5);
imshow(BW4);
title('Roberts算子');

subplot(2, 3, 6);
imshow(BW5);
title('LoG算子');

% 4）算子性能分析
% 计算每种算子检测到的边缘像素数量
prewitt_pixels = sum(BW1(:));
canny_pixels = sum(BW2(:));
sobel_pixels = sum(BW3(:));
roberts_pixels = sum(BW4(:));
log_pixels = sum(BW5(:));

% 显示边缘像素数量对比
figure('Name', '边缘检测算子性能对比');
bar([prewitt_pixels, canny_pixels, sobel_pixels, roberts_pixels, log_pixels]);
set(gca, 'XTickLabel', {'Prewitt', 'Canny', 'Sobel', 'Roberts', 'LoG'});
title('各算子检测到的边缘像素数量');
ylabel('边缘像素数量');


