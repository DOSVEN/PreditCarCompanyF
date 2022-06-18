/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package preditcarcompanyf;

import java.io.BufferedWriter;
import java.io.FileWriter;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.SMO;
import weka.core.Attribute;
import weka.core.Debug;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

/**
 *
 * @author tranluan
 */
public class MySVMModel extends MyKnowledgeModel {

    public SMO svm;
    Evaluation eval;

    public MySVMModel() {
        super();
    }

    public MySVMModel(String filename, String m_opts, String d_opts) throws Exception {
        super(filename, m_opts, d_opts);
    }

    public void buildSVM(String filename) throws Exception {
        //Doc train test vao bo nho
        setTrainset(filename);
        this.trainset.setClassIndex(this.trainset.numAttributes() - 1);
        //Huan luyen mo hinh mang SVM
        this.svm = new SMO();
        svm.setOptions(this.model_options);
        svm.buildClassifier(this.trainset);
    }

    public String evaluateSVM(String filename) throws Exception {
        setTestset(filename);
        this.testset.setClassIndex(this.testset.numAttributes() - 1);
        Debug.Random rnd = new Debug.Random(1);
        int folds = 10;
        eval = new Evaluation(this.trainset);
        eval.crossValidateModel(svm, this.testset, folds, rnd);
        String str = eval.toSummaryString("\nSVMModel 10-fold Cross-validation\n-----------------"
                + "-------------------------------------------\n", false);
        return str;
    }

    public Instances predictClassLabel(String fileIn) throws Exception {
        //Doc du lieu can du doan vao bo nho: file unlabel
        ConverterUtils.DataSource ds = new ConverterUtils.DataSource(fileIn);
        Instances unlabel = ds.getDataSet();
        unlabel.setClassIndex(unlabel.numAttributes() - 1);
        //Du doan classLabel cho tung instances
        for (int i = 0; i < unlabel.numInstances(); i++) {
            double predict = svm.classifyInstance(unlabel.instance(i));
            unlabel.instance(i).setClassValue(predict);
            Attribute quality = unlabel.instance(i).attribute(11);
            //System.out.println(unlabel.instance(i).toString(quality));
            System.out.println(quality);
        }
        return unlabel;
    }

    

    @Override
    public String toString() {
        return eval.toString(); //To change body of generated methods, choose Tools | Templates.
    }

}
