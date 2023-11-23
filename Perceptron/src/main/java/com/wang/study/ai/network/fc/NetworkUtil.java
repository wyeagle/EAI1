package com.wang.study.ai.network.fc;

import com.wang.study.ai.function.activation.ActivationFunction;
import com.wang.study.ai.function.cost.CostFunction;
import com.wang.study.ai.util.JsonMapper;
import com.wang.study.ai.util.NumUtil;

import java.util.ArrayList;
import java.util.List;

public class NetworkUtil {

    public static NetworkConfig network2config(Network network){
        NetworkConfig config = new NetworkConfig();

        config.setXnumber(network.getXSize());
        config.setBatchSize(network._batchSize);
        config.setCostFunc(network._cf.getClass().getName());
        config.setDelta(network._delta);
        config.setEpoch(network._epoch);
        config.setRate(network._rate);
        config.setMaxAdjustCount(network._memory._maxAdjustCount);
        int[] neuronNumOfLayers = new int[network.getLayerList().size()];
        List<String> afList = new ArrayList<>();
        List<double[]> wList = new ArrayList<>();

        List<Layer> layerList = network.getLayerList();
        for(int i=0;i<layerList.size();i++){
            Layer layer = layerList.get(i);
            neuronNumOfLayers[i] = layer.getNeuronList().size();
            afList.add(layer._af.getClass().getName());

            for(Neuron n:layer.getNeuronList()){
                wList.add(NumUtil.clone(n._w));
            }
        }

        config.setNeuronNumOfLayers(neuronNumOfLayers);
        config.setActivationFuncs(afList);
        config.setWeights(wList);

        return config;
    }

    private static Network config2Network(NetworkConfig config) throws Exception{
        //int xNumber,int[] neuronNumOfLayers,double delta,double rate,int epoch, int batchSize
        Network network = Network.build(config.getXnumber(),config.getNeuronNumOfLayers(),config.getDelta(),config.getRate(),config.getEpoch(),config.getBatchSize(),config.getMaxAdjustCount());
        network.configCostFunc((CostFunction)Class.forName(config.getCostFunc()).newInstance());

        List<Layer> layerList = network.getLayerList();
        int index = 0;
        for(int i=0;i<layerList.size();i++) {
            Layer layer = layerList.get(i);
            network.configActivationFunc(i,(ActivationFunction)Class.forName(config.getActivationFuncs().get(i)).newInstance());
            for(Neuron n:layer.getNeuronList()){
                n.assignWeight(config.getWeights().get(index++));
            }
            network._assignWeightFlag = true;
        }
        return network;
    }

    public static String network2Json(Network network){
        NetworkConfig config = network2config(network);

        String result = JsonMapper.nonEmptyMapper().toJson(config);

        return result;
    }

    public static Network json2Network(String json) throws Exception{
        NetworkConfig config = (NetworkConfig)JsonMapper.nonEmptyMapper().fromJson(json, NetworkConfig.class);
        return config2Network(config);
    }
}
