function flag=predicate(region)%��ͼ���׼��;�ֵ�й�
sd=std2(region);
m=mean2(region);
flag=(sd>22)&(m>5)&(m<255);%�����Լ�����ʵ���������
end

