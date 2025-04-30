% 先来生成测试图像看看霍夫变换到底是什么意思
f = zeros(101, 101);
f(1, 1) = 1; f(101, 1) = 1; f(1, 101) = 1;
f(101, 101) = 1; f(51, 51) = 1;

% 调用霍夫变换的函数，看看变换后的结果是什么
H = hough(f);
figure, imshow(H, [])
title('霍夫变换示例')

% 通过前面的例子发现，霍夫变换是通过点线对偶性来检测图像中的边缘
% 了解了霍夫变换的原理后，下面用'bld.png'这副图像来测试一下
I = imread('bld.png');
f = im2double(rgb2gray(I));

% 使用不同的算子进行边缘检测
[BW1, tp] = edge(f, 'prewitt');
[BW2, tc] = edge(f, 'canny', [0.04 0.10], 1.5);
[BW3, ts] = edge(f, 'sobel');
[BW4, tr] = edge(f, 'roberts');
[BW5, tl] = edge(f, 'log');

% 可视化对比不同边缘检测算子的结果
figure('Name', '边缘检测算子对比');
subplot(2, 3, 1);
imshow(f);
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

% 对每种边缘检测结果进行霍夫变换和线段检测
% Canny算子
[H_canny, theta_canny, rho_canny] = hough(BW2, 'ThetaResolution', 0.2);
figure, imshow(H_canny, [], 'XData', theta_canny, 'YData', rho_canny, 'InitialMagnification', 'fit');
xlabel('\theta'), ylabel('\rho')
title('Canny算子的霍夫变换')
axis on, axis normal

% 寻找5个有意义的霍夫变换的峰值
peaks_canny = houghpeaks(H_canny, 5, 'threshold', ceil(0.3*max(H_canny(:))));
x = theta_canny(peaks_canny(:, 2));
y = rho_canny(peaks_canny(:, 1));
hold on
plot(x, y, 's', 'color', 'red');

% 寻找线段
lines_canny = houghlines(BW2, theta_canny, rho_canny, peaks_canny);
figure, imshow(BW2), hold on
title('Canny算子的线段检测')
for k = 1:length(lines_canny)
    xy = [lines_canny(k).point1; lines_canny(k).point2];
    plot(xy(:, 1), xy(:, 2), 'lineWidth', 2, 'Color', [.8 .8 .8]);
    % 绘制线段端点
    plot(xy(1, 1), xy(1, 2), 'x', 'linewidth', 2, 'color', 'yellow');
    plot(xy(2, 1), xy(2, 2), 'x', 'linewidth', 2, 'color', 'red');
end

% Prewitt算子
[H_prewitt, theta_prewitt, rho_prewitt] = hough(BW1, 'ThetaResolution', 0.2);
figure, imshow(H_prewitt, [], 'XData', theta_prewitt, 'YData', rho_prewitt, 'InitialMagnification', 'fit');
xlabel('\theta'), ylabel('\rho')
title('Prewitt算子的霍夫变换')
axis on, axis normal

peaks_prewitt = houghpeaks(H_prewitt, 5, 'threshold', ceil(0.3*max(H_prewitt(:))));
x = theta_prewitt(peaks_prewitt(:, 2));
y = rho_prewitt(peaks_prewitt(:, 1));
hold on
plot(x, y, 's', 'color', 'red');

lines_prewitt = houghlines(BW1, theta_prewitt, rho_prewitt, peaks_prewitt);
figure, imshow(BW1), hold on
title('Prewitt算子的线段检测')
for k = 1:length(lines_prewitt)
    xy = [lines_prewitt(k).point1; lines_prewitt(k).point2];
    plot(xy(:, 1), xy(:, 2), 'lineWidth', 2, 'Color', [.8 .8 .8]);
    plot(xy(1, 1), xy(1, 2), 'x', 'linewidth', 2, 'color', 'yellow');
    plot(xy(2, 1), xy(2, 2), 'x', 'linewidth', 2, 'color', 'red');
end

% Sobel算子
[H_sobel, theta_sobel, rho_sobel] = hough(BW3, 'ThetaResolution', 0.2);
figure, imshow(H_sobel, [], 'XData', theta_sobel, 'YData', rho_sobel, 'InitialMagnification', 'fit');
xlabel('\theta'), ylabel('\rho')
title('Sobel算子的霍夫变换')
axis on, axis normal

peaks_sobel = houghpeaks(H_sobel, 5, 'threshold', ceil(0.3*max(H_sobel(:))));
x = theta_sobel(peaks_sobel(:, 2));
y = rho_sobel(peaks_sobel(:, 1));
hold on
plot(x, y, 's', 'color', 'red');

lines_sobel = houghlines(BW3, theta_sobel, rho_sobel, peaks_sobel);
figure, imshow(BW3), hold on
title('Sobel算子的线段检测')
for k = 1:length(lines_sobel)
    xy = [lines_sobel(k).point1; lines_sobel(k).point2];
    plot(xy(:, 1), xy(:, 2), 'lineWidth', 2, 'Color', [.8 .8 .8]);
    plot(xy(1, 1), xy(1, 2), 'x', 'linewidth', 2, 'color', 'yellow');
    plot(xy(2, 1), xy(2, 2), 'x', 'linewidth', 2, 'color', 'red');
end

% 对比可视化不同算子的线段检测结果
figure('Name', '不同算子的线段检测对比');
subplot(2, 2, 1);
imshow(f); title('原图');

subplot(2, 2, 2);
imshow(BW2); hold on;
for k = 1:length(lines_canny)
    xy = [lines_canny(k).point1; lines_canny(k).point2];
    plot(xy(:, 1), xy(:, 2), 'lineWidth', 2, 'Color', 'red');
end
title('Canny算子线段检测');

subplot(2, 2, 3);
imshow(BW1); hold on;
for k = 1:length(lines_prewitt)
    xy = [lines_prewitt(k).point1; lines_prewitt(k).point2];
    plot(xy(:, 1), xy(:, 2), 'lineWidth', 2, 'Color', 'green');
end
title('Prewitt算子线段检测');

subplot(2, 2, 4);
imshow(BW3); hold on;
for k = 1:length(lines_sobel)
    xy = [lines_sobel(k).point1; lines_sobel(k).point2];
    plot(xy(:, 1), xy(:, 2), 'lineWidth', 2, 'Color', 'blue');
end
title('Sobel算子线段检测');
