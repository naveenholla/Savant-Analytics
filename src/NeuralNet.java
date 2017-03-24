import org.datavec.api.records.reader.RecordReader;
import org.datavec.api.records.reader.impl.csv.CSVRecordReader;
import org.datavec.api.split.FileSplit;
import org.datavec.api.util.ClassPathResource;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.weights.WeightInit;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.lossfunctions.LossFunctions;
import java.io.IOException;

/**
 * Created by Arnav_Gudibande on 3/8/17.
 */

public class NeuralNet {

    private static MultiLayerConfiguration conf;
    private int seed = 6;
    private int iterations = 1000;
    private int numInputs = 3;
    private int outputNum = 3;

    public NeuralNet() {
        conf = new NeuralNetConfiguration.Builder()
                .seed(seed)
                .iterations(iterations)
                .activation(Activation.TANH)
                .weightInit(WeightInit.XAVIER)
                .learningRate(0.1)
                .regularization(true).l2(1e-4)
                .list()
                .layer(0, new DenseLayer.Builder().nIn(numInputs).nOut(3)
                    .build())
                .layer(1, new DenseLayer.Builder().nIn(3).nOut(3)
                        .build())
                .layer(2, new OutputLayer.Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
                        .activation(Activation.SIGMOID)
                        .nIn(3).nOut(outputNum).build())
                .backprop(true).pretrain(false)
                .build();
    }

    public static void trainNeuralNet() throws IOException, InterruptedException {
        int numClasses = 2;
        int batchSizeTraining = 244;

        DataSet trainingData = readCSVDataset("/in/stock_train.csv", batchSizeTraining, numClasses);

    }

    public static DataSet readCSVDataset(String filepath, int batchsize, int numClasses) throws IOException, InterruptedException {
        RecordReader rr = new CSVRecordReader();
        rr.initialize(new FileSplit(new ClassPathResource(filepath).getFile()));
        DataSetIterator iterator = new RecordReaderDataSetIterator(rr, batchsize, 0,numClasses);
        return iterator.next();
    }

}
