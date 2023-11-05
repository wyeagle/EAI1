package com.wang.study.ai;

import com.wang.study.ai.network.NetworkTest00;
import com.wang.study.ai.perceptron.PerceptronTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses(
        {
                PerceptronTest.class,
                NetworkTest00.class
        })
public class AllTests {
}
