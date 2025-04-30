% 读取图像
f = imread('rice.png');
% 如果是彩色图像，转为灰度图
if size(f, 3) > 1
    f = rgb2gray(f);
end

% 显示原始图像
figure;
subplot(2, 2, 1);
imshow(f);
title('原始图像');

% 使用不同的种子坐标和阈值进行区域生长
% 参数1：种子坐标(110, 60)，阈值30
g1 = region_growing(f, 110, 60, 30);
subplot(2, 2, 2);
imshow(g1);
title('种子(110,60)，阈值30');

% 参数2：种子坐标(150, 150)，阈值20
g2 = region_growing(f, 150, 150, 20);
subplot(2, 2, 3);
imshow(g2);
title('种子(150,150)，阈值20');

% 参数3：种子坐标(200, 100)，阈值40
g3 = region_growing(f, 200, 100, 40);
subplot(2, 2, 4);
imshow(g3);
title('种子(200,100)，阈值40');

% 分析结果
fprintf('不同参数的区域生长法分割结果分析：\n');
fprintf('1. 种子点位置的选择影响分割区域的起始位置\n');
fprintf('2. 阈值越大，区域生长的范围越广\n');
fprintf('3. 阈值越小，分割的区域越精细，但可能不完整\n');
