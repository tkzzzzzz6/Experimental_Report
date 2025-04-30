function v=split_test(B,mindim,fun)
 K=size(B,3);%B就是qtdecomp函数传过来的，代表当前size(B,3)返回的是B的层数，就是B是几维的，这里实际上就是有几个B这样大小的图像块
 %这句代码的意思是从qtdecomp函数传过来的B，是当前分解成的K块的m*m的图像块，K表示有多少个这样大小的图像块
 v(1:K)=false;
 for I=1:K
     quadregion=B(:,:,I);
     if size(quadregion,1)<=mindim%如果分的块的大小小于mindim就直接结束
         v(I)=false;
         continue
     end
     flag=feval(fun,quadregion);%quadregion是fun函数的参数,fun(quadregion)
     if flag%如果flag是1，代表需要再分
         v(I)=true;%这里就相当于split_test是起一个调用predicate的作用，返回的就是ppredicate的值
     end
 end
end
