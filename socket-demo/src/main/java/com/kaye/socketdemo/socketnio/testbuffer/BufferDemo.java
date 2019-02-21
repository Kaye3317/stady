package com.kaye.socketdemo.socketnio.testbuffer;

import java.nio.IntBuffer;

/**
 * nio buffer 讲解
 */
public class BufferDemo {

    public static void main(String[] args) {
        //1.allocate
        IntBuffer intBuffer = IntBuffer.allocate(10);
        intBuffer.put(11);//position位置是从0开始 position为 0 -> 是第1个数据
        intBuffer.put(13);//position为 0 -> 是第2个数据
        intBuffer.put(56);//position为 0 -> 是第3个数据
        intBuffer.flip();//该方法是将position位置复位  首先将limit赋值为position 再将position赋值为0
        System.err.println("buffer的相关数据：" + intBuffer);
        System.err.println("容量：" + intBuffer.capacity());
        System.err.println("最大限制：" + intBuffer.limit());//position不复位的时候限制是容量大小，复位后是添加的数据数量
        intBuffer.put(1, 5);//将position为1的数据修改为5
        intBuffer.get(1);//将position为1的数据修改为5
        System.err.println("获取position为1的数据：" + intBuffer.get(1));
        System.err.println("覆盖数据或获取数据，position位置不发生变化：" + intBuffer);

        for (int i = 0; i < intBuffer.limit(); i++) {
            //get()会获取position位置的数据
            //没有flip时，limit为10循环时，get是从position为3开始取值，因为占用了3个位置，所以只能取7个数据，
            // 所以抛出异常BufferUnderflowException说取到底了
            //flip后循环的是添加的数据，因为limit被重新赋值
            System.err.println(intBuffer.get());
        }

        //2.warp
        int[] arr = new int[]{1, 2, 3};
        //容量是和数组容量一样
        //并且在修改buffer的数据时数组的值也会改变
        IntBuffer intBuffer1 = IntBuffer.wrap(arr);
        System.err.println("buffer1的相关数据：" + intBuffer1);
        //修改buffer里position为0的数据为11
        intBuffer1.put(0, 11);
        //从0开始选数组的2个数据
        IntBuffer intBuffer2 = IntBuffer.wrap(arr, 0, 2);
        System.err.println("buffer2的相关数据：" + intBuffer2);
        //数组的第一个数据被改变为11
        System.err.println(arr[0]);
        //buffer2的第一个数据也被改变
        System.err.println("buffer2的第一个数据：" + intBuffer2.get(0));

        //3.其他方法
        IntBuffer intBuffer3 = IntBuffer.allocate(10);
        int[] arr1 = new int[]{5,6,7};
        intBuffer3.put(arr1);
        System.err.println("buffer3的相关数据：" + intBuffer3);
        //复制一份buffer3的数据到buffer4
        IntBuffer intBuffer4 = intBuffer3.duplicate();
        System.err.println("buffer4的相关数据：" + intBuffer4);

        //设置position的位置 不会改变limit的值
        intBuffer4.position(1);
        System.err.println("buffer4的相关数据：" + intBuffer4);
        //可读数据是position位置到limit位置
        System.err.println("buffer4的可读数据：" + intBuffer4.remaining());

        int[] arr2 = new int[intBuffer4.remaining()];
        //把buffer4的数据复制给arr2
        //该数组的大小要和buffer的可读数据一样
        intBuffer4.get(arr2);
        for (int i : arr2
             ) {
            System.err.print(i + " ");
        }

    }

}
