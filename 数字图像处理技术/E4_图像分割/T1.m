%% 图像分割实验
% 本实验包含点检测和线检测两部分

%% 点检测
% 读取图像
f_point = imread('dian.tif');
% 拉普拉斯算子（点检测模板）
w_point = [-1 -1 -1; -1 8 -1; -1 -1 -1];
% 应用滤波器并取绝对值
g_point = abs(imfilter(im2double(f_point), w_point));
% 设置阈值（使用最大值作为阈值）
T = max(g_point(:));
% 阈值处理
g_point_binary = g_point >= T;

%% 线检测
% 读取图像
f_line = im2double(imread('wirebond_mask.png'));
% 生成45°线检测模板
w_line_45 = [2 -1 -1; -1 2 -1; -1 -1 2];
% 应用滤波器到正确的图像
g_line_45 = imfilter(f_line, w_line_45);

% 添加其他方向的线检测模板
w_line_0 = [-1 -1 -1; 2 2 2; -1 -1 -1];    % 水平线检测
w_line_90 = [-1 2 -1; -1 2 -1; -1 2 -1];   % 垂直线检测
w_line_135 = [-1 -1 2; -1 2 -1; 2 -1 -1];  % 135°线检测

% 应用其他方向的滤波器
g_line_0 = imfilter(f_line, w_line_0);
g_line_90 = imfilter(f_line, w_line_90);
g_line_135 = imfilter(f_line, w_line_135);

% 计算所有方向的最大响应
g_line_max = max(cat(3, g_line_0, g_line_45, g_line_90, g_line_135), [], 3);

%% 显示结果
% 点检测结果显示
figure('Name', '点检测结果');
subplot(1, 2, 1);
imshow(f_point);
title('点检测原图');
subplot(1, 2, 2);
imshow(g_point_binary);
title('点检测结果');

% 线检测结果显示 - 所有结果放在一个图中
figure('Name', '线检测结果');
subplot(2, 3, 1);
imshow(f_line);
title('线检测原图');
subplot(2, 3, 2);
imshow(g_line_0, []);
title('线检测(0°)');
subplot(2, 3, 3);
imshow(g_line_45, []);
title('线检测(45°)');
subplot(2, 3, 4);
imshow(g_line_90, []);
title('线检测(90°)');
subplot(2, 3, 5);
imshow(g_line_135, []);
title('线检测(135°)');
subplot(2, 3, 6);
imshow(g_line_max, []);
title('所有方向最大响应');
