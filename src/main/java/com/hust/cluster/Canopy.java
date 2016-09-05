package com.hust.cluster;

import java.util.ArrayList;
import java.util.List;

import com.hust.contrant.Constrants;
import com.hust.similarity.CosSimilarity;

public class Canopy extends Cluster {

    @Override
    public void clustering() {
        if (null == vectors) {
            return;
        }
        if (null == similarity) {
            similarity = new CosSimilarity();
        }
        resultIndex = new ArrayList<List<Integer>>();
        resultVector = new ArrayList<List<double[]>>();
        for (int i = 0; i < vectors.size(); i++) {
            double[] vector = vectors.get(i);
            if (i == 0) {
                List<Integer> setIndex = new ArrayList<Integer>();
                setIndex.add(i);
                resultIndex.add(setIndex);
                List<double[]> setVector = new ArrayList<double[]>();
                setVector.add(vector);
                resultVector.add(setVector);
                continue;
            }
            boolean findSet = false;
            for (int j = 0; j < resultVector.size(); j++) {
                List<double[]> tmpVector = resultVector.get(j);
                double sim = similarity.calculate(vector, tmpVector);
                if (sim >= Constrants.THRESHOLD) {
                    List<Integer> tmpIndex = resultIndex.get(j);
                    tmpIndex.add(i);
                    tmpVector.add(vector);
                    findSet = true;
                    break;
                }
            }
            if (!findSet) {
                List<Integer> setIndex = new ArrayList<Integer>();
                setIndex.add(i);
                resultIndex.add(setIndex);
                List<double[]> setVector = new ArrayList<double[]>();
                setVector.add(vector);
                resultVector.add(setVector);
            }
        }
    }
}
