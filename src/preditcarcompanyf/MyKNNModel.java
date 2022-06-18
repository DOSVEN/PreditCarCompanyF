/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package preditcarcompanyf;
import java.io.BufferedWriter;
import java.io.FileWriter;
import weka.classifiers.Evaluation;
import weka.classifiers.lazy.IBk;
import weka.core.Attribute;
import weka.core.Debug;
import weka.core.Debug.Random;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;
import weka.core.converters.ConverterUtils.DataSource;


/**
 *
 * @author ADMIN
 */
public class MyKNNModel extends MyKnowledgeModel {
    IBk knn;
    Evaluation eval;

    public MyKNNModel(String filename, String m_opts, String d_opts) throws Exception {
        super(filename, m_opts, d_opts);
    }
    
    public void buildkNN(String filename) throws Exception{
        setTrainset(filename);
        this.trainset.setClassIndex(this.trainset.numAttributes() - 1);
        this.knn = new IBk();
        knn.setOptions(model_options);
        knn.buildClassifier(this.trainset);
    }
    
    public String evalutekNN(String filename) throws Exception{
        setTestset(filename);
        this.testset.setClassIndex(this.testset.numAttributes() - 1);
        Random rnd = new Random(1); 
        int folds = 10;
        eval = new Evaluation(this.trainset);
        eval.crossValidateModel(knn, this.testset, folds, rnd);
       return eval.toSummaryString("\nKet qua danh gia mo hinh 10-fold cross-validation\n-----\n",false);
        
    }
    
    public Instances predictClassLabel(String fileIn) throws Exception{
        DataSource ds = new DataSource(fileIn);
        Instances unlabel = ds.getDataSet();
        unlabel.setClassIndex(unlabel.numAttributes()-1);
        for(int i = 0;i<unlabel.numInstances();i++){
            double predict = knn.classifyInstance(unlabel.instance(i));
            unlabel.instance(i).setClassValue(predict);
          
    }
       return unlabel;
    
    
}

    @Override
    public String toString() {
        return eval.toString(); //To change body of generated methods, choose Tools | Templates.
    }
    
}
