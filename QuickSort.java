import java.util.Arrays;
import java.util.Random;
/*
此代码为第一版代码
 */
//写代码，以后，统一使用左闭右闭
public class QuickSort {
    //最最原始的一种快速排序算法
    //基于https://blog.csdn.net/weixin_38429450/article/details/113359398
    /*
        每一步执行
        <pivot:[l:j]
        >=pivot:[j+1,k-1]
        结果:
        <pivot:[l,j-1]
        >=pivot:[j,r]
     */
    public static void quick_old(int[] a,int l,int r)
    {
        if(l<r) {
            int pivot = a[0];
            int j = l;
            int k;
            for (k = l + 1; k <= r; k++) {
                if (a[k] < pivot) {
                    swap(a, k, j + 1);
                    j += 1;
                }
            }
            swap(a, l, j);
            quick_old(a,l,j-1);
            quick_old(a,j+1,r);
        }
    }
    //填坑方法，也叫双路快排的一种实现方式，因为双路快排不考虑等号的问题，为了避免更改等值的分布，等号直接略去
    public static void quickSort_fill_in_pit(int[] a, int l, int r) {
        if (l < r) {
            int i = l;
            int j = r;
            int pivot = a[i];
            while (i < j) {
                while (i < j && a[j] >= pivot) {
                    j--;
                }
                if (i < j) {
                    a[i++] = a[j];
                }
                while (i < j && a[i] <= pivot) {
                    i++;
                }
                if (i < j) {
                    a[j--] = a[i];
                }
            }
            a[i] = pivot;
            quickSort_fill_in_pit(a, l, i - 1);
            quickSort_fill_in_pit(a, i + 1, l);
        }
    }

    //交换的方法,也就是双路快排的一种实现方式，主要解决的问题是当重复元素过多的时候，会导致快速排序的两方不平衡
    public static void quickSort_swap(int[] a, int l, int r) {
        if (l < r) {
            int i = l;
            int j = r;
            int pivot = a[i];
            while (i < j) {
                while (i < j && a[j] >= pivot) {//注意,这里的等号非常关键
                    j--;
                }
                while (i < j && a[i] <= pivot) {//注意,这里必须带等号
                    i++;
                }
                //三数交换
                if (i < j) {
                    int t = a[i];
                    a[i] = a[j];
                    a[j] = t;
                }

            }
            //
            a[l] = a[i];
            a[i] = pivot;
            quickSort_swap(a, l, i - 1);
            quickSort_swap(a, i + 1, r);
        }
    }

    //random的方法
    //随机+双路快排交换法

    public static void quickSort_random(int[] a, int l, int r) {
        Random rand = new Random();
        int choice_index = rand.nextInt() % a.length;
        int temp = a[l];
        a[l] = a[choice_index];
        a[choice_index] = temp;
        quickSort_swap(a, l, r);
    }

    //三数取中+双路快排交换法
    public static void quickSort_median_of_three(int[] a, int l, int r)
    {
        int left = a[l];
        int right = a[r];
        int mid = a[(int)(l+r)/2];
        int temp[] = new int[]{left,right,mid};
        Arrays.sort(temp);
        int pivot = temp[0];
        int t = pivot;
        pivot = a[l];
        a[l] = t;
        quickSort_swap(a,l,r);
    }

    //最小优化到插入排序总函数
    //插入优化+三数取中+双路快排交换
    public static void quickSort_insert_all(int[] a,int l,int r)
    {
        //选取k的讨论选取k值
        if(l<r){
          int k= (int)Math.log(l-r+1);

          //限制k在5到15之间
          if(k>=15)
          {
              quickSort_insert(a,l,r,15);
          }
          else if(k<5)
          {
              quickSort_insert(a,l,r,5);
          }
          else{
              quickSort_insert(a,l,r,k);
          }
        }
    }

    //最小优化到插入排序的子函数
    public static void quickSort_insert(int[] a,int l,int r,int k)
    {

        //元素小到一定程度的时候使用插入排序
        if(r-l+1<k)
        {
            insert_sort(a,l,r);
            return;//排好顺序就不用继续

        }
        //这个地方还是使用快速排序
        int left = a[l];
        int right = a[r];
        int mid = a[(int)(l+r)/2];
        int temp[] = new int[]{left,right,mid};
        Arrays.sort(temp);
        int pivot = temp[0];
        int t = pivot;
        pivot = a[l];
        a[l] = t;

        if (l < r) {
            int i = l;
            int j = r;
            int pivot_new = a[i];
            while (i < j) {
                while (i < j && a[j] > pivot_new) {
                    j--;
                }
                while (i < j && a[i] < pivot_new) {
                    i++;
                }
                //三数交换
                if (i < j) {
                    int tem = a[i];
                    a[i] = a[j];
                    a[j] = tem;
                }

            }

            a[l] = a[i];
            a[i] = pivot_new;
            quickSort_insert(a, l, i - 1,k);
            quickSort_insert(a, i + 1, r,k);
        }
    }

    //插入排序
    public static void insert_sort(int[] a,int l,int r)
    {

        //原本的思想是找到先找到位置，然后再插入(插入时数组移位)，但是这样会是两重循环
        //为了提高效率，我们边搜索，边移动位，变成了一轮循环
        for(int i = l+1;i<=r;i++)
        {
            int temp = a[i];
            int j;
            for(j=i-1;j>=0;j--)
            {
                if(a[j]>temp)
                {
                    a[j+1] = a[j];
                }
                else
                    break;
            }
            a[j+1] =temp;
        }
    }

    //可以专门建立一个工具类
    public static  void swap(int[]a,int i,int j)
    {

        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;

    }
    //三路快排+三数取中+插入优化
    //不进行最后交换的结果
    //<pivot:[l+1,j]
    //=pivot:[j+1,i-1]
    //>pivot:[k,r]
    //最后进行交换的结果后
    /*
    <pivot:[l,j-1]
    =pivot:[j,i-1]
    >pivot:[k,r]
             */
    public static void three_way_quick_sort(int[]a,int l,int r)
    {


        if (l<r) {
            //判断插入排序
            int threshold= (int)Math.log(l-r+1);
            //限制k在5到15之间
            if(threshold>=15)
            {
                threshold=15;
            }
            else if(threshold<5)
            {
                threshold=5;
            }
            else{
                ;
            }
            //判断插入排序
            if(r-l+1<threshold)
            {
                insert_sort(a,l,r);
                return;//排好顺序就不用继续
            }

            //快速排序
            int left = a[l];
            int right = a[r];
            int mid = a[(int) (l + r) / 2];
            int temp[] = new int[]{left, right, mid};
            Arrays.sort(temp);
            int pivot = temp[0];
            int t = pivot;
            pivot = a[l];
            a[l] = t;
            int i = l + 1;//这里一定不能按照双路快排一样，把初始化的索引设置成l,因为a[l]是pivot
            int j = l;//一开始默认j是没有
            int k = r + 1;//因为如果k为r就默认一定会有大于pivot的值
            while(i < k) {
                if (a[i] < pivot)
                {
                    swap(a,i,j+1);//先交换，再更改索引，因为是全闭的区间
                    j+=1;
                    i+=1;
                }else if(a[i]>pivot){
                    swap(a,i,k-1);//先交换，再更改索引，因为是全闭的区间
                    k-=1;
                }else{
                    i+=1;
                }
            }
            //交换
            swap(a,l,j);
            //最后
            /*
            <pivot:[l,j-1]
            =pivot:[j,i-1]
            >pivot:[k,r]
             */
            //j,k
            three_way_quick_sort(a,l,j-1);
            three_way_quick_sort(a,k,r);
        }
    }

    //多线程优化+三路快排+三数取中+插入优化
    public static void mutil_thread_quick_sort(int[] a,int l,int r)
    {

    }
    //加一个Test类,可以用Junit包进行测试
    //测试1
    //state用来标记是之前传进来的还是之后传进来的
    public static void  test(int[] a,boolean state){
        if(state) {
            System.out.println("排序前:");
            for (int i : a) {
                System.out.print(i);
                System.out.print(' ');
            }
            System.out.println();
        }else{
            System.out.println("排序后:");
            for (int i : a) {
                System.out.print(i);
                System.out.print(' ');
            }
            System.out.println();
        }
    }
    //测试2
    //以库函数为基准，测试自己写的各个函数的正确性
    public static void test()
    {
        //随机生成很多数据,而后进行测试


    }

    public static void main(String[] args) {

    }
}