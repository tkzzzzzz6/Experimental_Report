function segmented = region_growing(img, seed_row, seed_col, threshold)
    % 获取图像尺寸
    [rows, cols] = size(img);
    
    % 初始化结果矩阵
    segmented = false(rows, cols);
    
    % 创建图形窗口用于动态显示
    figure;
    f_h = imshow(segmented);
    
    % 初始化待检查列表与种子点的灰度值
    pixel_list = [seed_row, seed_col];
    seed_value = img(seed_row, seed_col);
    
    % 标记已访问的像素
    visited = false(rows, cols);
    visited(seed_row, seed_col) = true;
    
    % 当列表不为空时继续处理
    while ~isempty(pixel_list)
        % 获取并移除当前处理的像素
        current_pixel = pixel_list(1, :);
        pixel_list(1, :) = [];
        
        % 获取当前像素的坐标
        current_row = current_pixel(1);
        current_col = current_pixel(2);
        
        % 标记当前像素
        segmented(current_row, current_col) = 1;
        set(f_h, 'CData', segmented);
        drawnow;
        
        % 检查八邻域像素
        for x = -1:1:1
            for y = -1:1:1
                % 忽略自身
                if x == 0 && y == 0
                    continue;
                end
                
                % 计算新像素坐标
                new_row = current_row + x;
                new_col = current_col + y;
                
                % 检查边界条件
                if new_row > 0 && new_row <= rows && new_col > 0 && new_col <= cols
                    % 检查是否满足区域生长条件
                    if ~visited(new_row, new_col) && ...
                       abs(double(img(new_row, new_col)) - double(seed_value)) <= threshold
                        % 将新像素加入待处理列表
                        pixel_list = [pixel_list; new_row, new_col];
                        visited(new_row, new_col) = true;
                    end
                end
            end
        end
    end
end
