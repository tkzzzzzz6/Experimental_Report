function v=split_test(B,mindim,fun)
 K=size(B,3);%B����qtdecomp�����������ģ�����ǰsize(B,3)���ص���B�Ĳ���������B�Ǽ�ά�ģ�����ʵ���Ͼ����м���B������С��ͼ���
 %���������˼�Ǵ�qtdecomp������������B���ǵ�ǰ�ֽ�ɵ�K���m*m��ͼ��飬K��ʾ�ж��ٸ�������С��ͼ���
 v(1:K)=false;
 for I=1:K
     quadregion=B(:,:,I);
     if size(quadregion,1)<=mindim%����ֵĿ�Ĵ�СС��mindim��ֱ�ӽ���
         v(I)=false;
         continue
     end
     flag=feval(fun,quadregion);%quadregion��fun�����Ĳ���,fun(quadregion)
     if flag%���flag��1��������Ҫ�ٷ�
         v(I)=true;%������൱��split_test����һ������predicate�����ã����صľ���ppredicate��ֵ
     end
 end
end
