function g=splitmerge(f,mindim,fun)%f�Ǵ��ָ��ԭͼ��mindim�Ƕ���ֽ������������С�Ŀ飬������2������������
 Q=2^nextpow2(max(size(f)));
 [M,N]=size(f);
 f=padarray(f,[Q-M,Q-N],'post');%�����ͼ���������顣f������ͼ������������ͼ���Ƚ�ͼ����䵽2���ݴ���ʹ����ķֽ����
 %Ȼ��������������������post����ʾ��ÿһά�����һ��Ԫ�غ����,B = padarray(A,padsize,padval,direction)
 %����padval����0���,Q��������ͼ��Ĵ�С��
 S=qtdecomp(f,@split_test,mindim,fun);%f����split_test��qtdecomp divides a square image into four
% equal-sized square blocks, and then tests each block to see if it
% meets some criterion of homogeneity. If a block meets the criterion,
% it is not divided any further. If it does not meet the criterion,
% it is subdivided again into four blocks, and the test criterion is
% applied to those blocks. This process is repeated iteratively until
% each block meets the criterion. The result can have blocks of several
% different sizes.S�ǰ����Ĳ����ṹ��ϡ����󣬴洢��ֵ�ǿ�Ĵ�С�����꣬��ϡ�������ʽ�洢
 Lmax=full(max(S(:)));%����ϡ�����洢��ʽ�洢�ľ���任������ͨ����full matrix����ʽ�洢��full��sparseֻ�Ǵ洢��ʽ�Ĳ�ͬ
 g=zeros(size(f));
 MARKER=zeros(size(f));
 
 for k=1:Lmax
     [vals,r,c]=qtgetblk(f,S,k);%vals��һ�����飬����f���Ĳ����ֽ��д�СΪk*k�Ŀ��ֵ����һ��k*k*�����ľ���
     %������ָS���ж��ٸ�������С�Ŀ飬f�Ǳ��Ĳ����ֵ�ԭͼ��r��c�Ƕ�Ӧ�����Ͻǿ��������2*2�飬����������Ͻǿ�ʼ�������
     
     if ~isempty(vals)
         for I=1:length(r)
             xlow=r(I);
             ylow=c(I);
             xhigh=xlow+k-1;
             yhigh=ylow+k-1;
             region=f(xlow:xhigh,ylow:yhigh);%�ҵ���Ӧ������
             flag=feval(fun,region);%evaluates the function handle, fhandle,using arguments x1 through xn.ִ�к���fun��region�ǲ���
             if flag%������ص���1������б��
                 g(xlow:xhigh,ylow:yhigh)=1;%Ȼ�󽫶�Ӧ��������1
                 MARKER(xlow,ylow)=1;%MARKER�����Ӧ�����Ͻ�������1
             end
         end
     end
 end
 
 g=bwlabel(imreconstruct(MARKER,g));%imreconstructĬ��2Dͼ��8��ͨ���������������ϵ�����
 g=g(1:M,1:N);%����ԭͼ��Ĵ�С
end

