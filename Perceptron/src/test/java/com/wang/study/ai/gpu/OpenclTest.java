package com.wang.study.ai.gpu;


import org.junit.Test;

public class OpenclTest {
    @Test
    public void test_01() {
        /*long start = 0l, end = 0l;
        Mat image = imread("hue.png"); // 随便找了一张图片
        Mat src0 = new Mat(20000, 20000, CV_8UC3); // 将图片大小放大到20000×20000
        resize(image, src0, new Size(20000, 20000));
        UMat src1 = new UMat(20000, 20000, CV_8UC3);
        src0.copyTo(src1);

        // Mat
        start = System.currentTimeMillis();
        blur(src0, src0, new Size(1, 1));
        end = System.currentTimeMillis();
        System.out.println("Mat running times：" + (end - start) + "ms");

        // UMat
        start = end = 0l;
        start = System.currentTimeMillis();
        blur(src1, src1, new Size(1, 1));
        end = System.currentTimeMillis();
        System.out.println("UMat running times：" + (end - start) + "ms");*/
    }
}
