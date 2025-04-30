% 读取图像
f = imread('dian.tif');

% 定义拉普拉斯模板
w = [-1 -1 -1; -1 8 -1; -1 -1 -1];

% 滤波并取绝对值
g = abs(imfilter(im2double(f), w));

% 设置最大值为阈值
T = max(g(:));

% 找出响应大于等于阈值的点
g = g >= T;

% 显示原图和检测结果
figure;
subplot(1,2,1), imshow(f), title('原图');
subplot(1,2,2), imshow(g), title('孤立点检测结果');
