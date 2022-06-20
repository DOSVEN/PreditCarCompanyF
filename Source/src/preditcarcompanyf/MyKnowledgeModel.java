/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package preditcarcompanyf;

import java.io.File;
import java.io.IOException;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVSaver;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.unsupervised.instance.RemovePercentage;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;


/**
 *
 * @author ADMIN
 */
public class MyKnowledgeModel {
    DataSource source;
    Instances dataset;
    String[] model_options;
    String[] data_options;
    Instances trainset;
    Instances testset;

    public MyKnowledgeModel() {
    }
    
     public MyKnowledgeModel(String filename,String m_opts,String d_opts) throws Exception{
     if(!filename.isEmpty()){
      this.source = new DataSource(filename);
      this.dataset = source.getDataSet();  
     }
     
     if(m_opts != null){
          this.model_options = weka.core.Utils.splitOptions(m_opts);
     }
    if(d_opts != null){
      this.data_options = weka.core.Utils.splitOptions(d_opts);  
    }
      }
     
       public void saveData(String filename,Instances dataset) throws IOException{
        ArffSaver outData = new ArffSaver();
        outData.setInstances(dataset);
        outData.setFile(new File(filename));
        outData.writeBatch();
        System.out.println("ss");
        
    }
    
    public void saveData2CSV(String filename) throws IOException{
        CSVSaver outData = new CSVSaver();
        outData.setInstances(this.dataset);
        outData.setFile(new File(filename));
        outData.writeBatch();
         System.out.println("ss");
        
    }
     public void saveData2CSVByInstance(String filename, Instances data) throws IOException {
        CSVSaver outData = new CSVSaver();
        outData.setInstances(data);
        outData.setFile(new File(filename));
        outData.writeBatch();
        System.out.println("ss");
    }
    
    public Instances divideTrainTest(Instances originalSet, double percent , boolean isTest) throws Exception{
        RemovePercentage rp = new RemovePercentage();
        rp.setPercentage(percent);
        rp.setInvertSelection(isTest);
        rp.setInputFormat(originalSet);
        return Filter.useFilter(originalSet, rp);
    }

    @Override
    public String toString() {
        return dataset.toSummaryString();
    }

   public void setTrainset(String filename) throws Exception{
       DataSource trainSource = new DataSource(filename);
       this.trainset = trainSource.getDataSet();
   }
   
    public void setTestset(String filename) throws Exception{
       DataSource testSource = new DataSource(filename);
       this.testset = testSource.getDataSet();
   }
    
    public void savemodel(String filename, Object model) throws Exception {
        weka.core.SerializationHelper.write(filename, model);
    }

    public Object loadmodel(String filename) throws Exception {
        return weka.core.SerializationHelper.read(filename);
    }
}
