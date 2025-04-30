function segmented = region_growing(img, seed_row, seed_col, threshold)
    % ��ȡͼ��ߴ�
    [rows, cols] = size(img);
    
    % ��ʼ���������
    segmented = false(rows, cols);
    
    % ����ͼ�δ������ڶ�̬��ʾ
    figure;
    f_h = imshow(segmented);
    
    % ��ʼ��������б������ӵ�ĻҶ�ֵ
    pixel_list = [seed_row, seed_col];
    seed_value = img(seed_row, seed_col);
    
    % ����ѷ��ʵ�����
    visited = false(rows, cols);
    visited(seed_row, seed_col) = true;
    
    % ���б�Ϊ��ʱ��������
    while ~isempty(pixel_list)
        % ��ȡ���Ƴ���ǰ���������
        current_pixel = pixel_list(1, :);
        pixel_list(1, :) = [];
        
        % ��ȡ��ǰ���ص�����
        current_row = current_pixel(1);
        current_col = current_pixel(2);
        
        % ��ǵ�ǰ����
        segmented(current_row, current_col) = 1;
        set(f_h, 'CData', segmented);
        drawnow;
        
        % ������������
        for x = -1:1:1
            for y = -1:1:1
                % ��������
                if x == 0 && y == 0
                    continue;
                end
                
                % ��������������
                new_row = current_row + x;
                new_col = current_col + y;
                
                % ���߽�����
                if new_row > 0 && new_row <= rows && new_col > 0 && new_col <= cols
                    % ����Ƿ�����������������
                    if ~visited(new_row, new_col) && ...
                       abs(double(img(new_row, new_col)) - double(seed_value)) <= threshold
                        % �������ؼ���������б�
                        pixel_list = [pixel_list; new_row, new_col];
                        visited(new_row, new_col) = true;
                    end
                end
            end
        end
    end
end
