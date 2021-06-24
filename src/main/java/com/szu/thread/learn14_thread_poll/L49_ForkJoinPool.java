package com.szu.thread.learn14_thread_poll;
/*
 * @Author 郭学胤
 * @University 深圳大学
 * @Description
 * @Date 2021/2/18 16:58
 */

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class L49_ForkJoinPool {

    int[] nums;

    public L49_ForkJoinPool(int dimension, Random random) {
        nums = new int[dimension];
        int sum = 0;
        for (int i = 0; i < dimension; i++) {
            nums[i] = random.nextInt(20);
            sum += nums[i];
        }
        System.out.println("-- "+sum);
    }

    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        int dimension = 10000000;
        L49_ForkJoinPool test = new L49_ForkJoinPool(dimension, new Random());
        Task task = new Task(0, dimension - 1, test.nums);
        forkJoinPool.execute(task);
        Long join = task.join();
        System.out.println("++ "+join);
    }


}

class Task extends RecursiveTask<Long> {
    int nums[];
    int  end;
    int start;
    final int MAX = 50000;


    public Task(int start, int end, int[] nums) {
        this.end = end;
        this.start = start;
        this.nums = nums;
    }



    @Override
    protected Long compute() {

        if (end - start <= MAX){
            long sum = 0;
            for (int i = start; i < end; i++) {
                sum += nums[ i];
            }
            return sum;
        }

        int mid = start  + (end - start)/2;
        Task task1 = new Task(start, mid, nums);
        Task task2= new Task(mid, end, nums);
        task1.fork();
        task2.fork();
        return task1.join() + task2.join();
    }
}
