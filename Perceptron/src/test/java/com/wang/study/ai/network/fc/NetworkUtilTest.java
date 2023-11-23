package com.wang.study.ai.network.fc;

import com.wang.study.ai.util.TestUtil;
import org.junit.Assert;
import org.junit.Test;

public class NetworkUtilTest {

    @Test
    public void network2Json() {
    }

    @Test
    public void Test01() throws Exception {
        String json = TestUtil.file2Str("Network/NetworkJson01.txt");

        Network network = NetworkUtil.json2Network(json);

        String json2 = NetworkUtil.network2Json(network);

        Assert.assertEquals(json.trim(),json2.trim());
    }
}