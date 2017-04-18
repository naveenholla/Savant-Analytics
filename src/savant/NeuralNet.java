package savant;

import org.datavec.api.records.reader.RecordReader;
import org.datavec.api.records.reader.impl.csv.CSVRecordReader;
import org.datavec.api.split.FileSplit;
import org.datavec.api.util.ClassPathResource;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.deeplearning4j.eval.Evaluation;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.dataset.api.preprocessor.DataNormalization;
import org.nd4j.linalg.dataset.api.preprocessor.NormalizerStandardize;
import org.nd4j.linalg.lossfunctions.LossFunctions;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Arnav_Gudibande on 3/8/17.
 */

public class NeuralNet {
    private static Map<Integer, String> classifiers = new HashMap<>();

    public void runNetwork() {

        try {

            int labelIndex = 4;
            int numClasses = 2;
            int batchSizeTraining = 391;

            DataSet training = readCSVDataSet("/in/stock_train.csv", batchSizeTraining, labelIndex, numClasses);
            DataSet testing = readCSVDataSet("/in/stock_test.csv", batchSizeTraining, labelIndex, numClasses);

            Map<Integer, Map<String, Object>> stocks = makeStocksForTesting(testing);

            DataNormalization normalizer = new NormalizerStandardize();
            normalizer.fit(training);
            normalizer.transform(training);
            normalizer.transform(testing);

            final int numInputs = 3;
            int outputNum = 3;
            int iterations = 1000;
            long seed = 6;

            MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
                    .seed(seed)
                    .iterations(iterations)
                    .activation(Activation.TANH)
                    .weightInit(WeightInit.XAVIER)
                    .learningRate(0.1)
                    .regularization(true).l2(1e-4)
                    .list()
                    .layer(0, new DenseLayer.Builder().nIn(numInputs).nOut(3).build())
                    .layer(1, new DenseLayer.Builder().nIn(numInputs).nOut(3).build())
                    .layer(2, new OutputLayer.Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
                            .activation(Activation.SOFTMAX).nIn(3).nOut(outputNum).build())
                    .backprop(true).pretrain(false)
                    .build();

            MultiLayerNetwork model = new MultiLayerNetwork(conf);
            model.init();
            model.setListeners(new ScoreIterationListener(100));

            model.fit(training);

            Evaluation eval = new Evaluation(2);
            INDArray output = model.output(testing.getFeatureMatrix());

            eval.eval(testing.getLabels(), output);

            setFittedClassifiers(output, stocks);
            logStocks(stocks);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void logStocks(Map<Integer,Map<String,Object>> animals){
        for(Map<String,Object> a:animals.values())
            System.out.println(a.toString());
    }

    public static void setFittedClassifiers(INDArray output, Map<Integer,Map<String,Object>> stocks){
        for (int i = 0; i < output.rows() ; i++) {

            // set the classification from the fitted results
            stocks.get(i).put("classifier",
                    classifiers.get(maxIndex(getFloatArrayFromSlice(output.slice(i)))));

        }

    }


    /**
     * This method is to show how to convert the INDArray to a float array. This is to
     * provide some more examples on how to convert INDArray to types that are more java
     * centric.
     *
     * @param rowSlice
     * @return
     */
    public static float[] getFloatArrayFromSlice(INDArray rowSlice){
        float[] result = new float[rowSlice.columns()];
        for (int i = 0; i < rowSlice.columns(); i++) {
            result[i] = rowSlice.getFloat(i);
        }
        return result;
    }

    /**
     * find the maximum item index. This is used when the data is fitted and we
     * want to determine which class to assign the test row to
     *
     * @param vals
     * @return
     */
    public static int maxIndex(float[] vals){
        int maxIndex = 0;
        for (int i = 1; i < vals.length; i++){
            float newnumber = vals[i];
            if ((newnumber > vals[maxIndex])){
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    public static Map<Integer, Map<String, Object>> makeStocksForTesting(DataSet test) {
        Map<Integer, Map<String, Object>> stocks = new HashMap<>();
        INDArray features = test.getFeatureMatrix();
        for(int i=0; i<features.rows(); i++) {
            INDArray slice = features.slice(i);
            Map<String, Object> stock = new HashMap<>();

            // setting attributes
            stock.put("price", slice.getInt(1));
            stock.put("vwap", slice.getInt(2));
            stock.put("twap", slice.getInt(3));

            stocks.put(i, stock);
        }
        return stocks;
    }

    private static DataSet readCSVDataSet(String path, int batchSize, int labelIndex, int numClasses) throws IOException, InterruptedException {
        RecordReader rr = new CSVRecordReader();
        rr.initialize(new FileSplit(new ClassPathResource(path).getFile()));
        DataSetIterator iterator = new RecordReaderDataSetIterator(rr, batchSize, labelIndex,numClasses);
        return iterator.next();
    }
}
