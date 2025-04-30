function flag=predicate(region)%和图像标准差和均值有关
sd=std2(region);
m=mean2(region);
flag=(sd>22)&(m>5)&(m<255);%参数自己根据实际情况调整
end

