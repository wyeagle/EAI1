package com.wang.study.ai;

import com.wang.study.ai.network.fc.NetworkTest00;
import com.wang.study.ai.network.fc.NetworkTest10;
import com.wang.study.ai.network.perceptron.PerceptronTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses(
        {
                PerceptronTest.class,
                NetworkTest00.class,
                NetworkTest10.class
        })
public class AllTests {
}
