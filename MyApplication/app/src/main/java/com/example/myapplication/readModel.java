package com.example.myapplication;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class readModel {
    public static byte[] serialize(Object obj) throws IOException {
        try(ByteArrayOutputStream b = new ByteArrayOutputStream()){
            try(ObjectOutputStream o = new ObjectOutputStream(b)){
                o.writeObject(obj);
            }
            return b.toByteArray();
        }
    }

    public static Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        try(ByteArrayInputStream b = new ByteArrayInputStream(bytes)){
            try(ObjectInputStream o = new ObjectInputStream(b)){
                return o.readObject();
            }
        }
    }
}


// Load model thong thuong
//package main;
//        import java.io.BufferedWriter;
//        import java.io.File;
////import java.io.FileReader;
//        import java.io.FileWriter;
////import weka.classifiers.*;
//        import weka.core.converters.ConverterUtils.DataSource;
////import weka.core.converters.ArffLoader.ArffReader;
//        import weka.core.Instance;
//        import weka.core.Instances;
////import weka.classifiers.evaluation.Evaluation;
////import java.util.*;
////import java.io.BufferedReader;
////import java.io.FileNotFoundException;
////import java.io.FileReader;
////import java.io.IOException;
////import java.lang.Math;
//
////import java.io.BufferedWriter;
////import java.io.File;
////import java.io.FileWriter;
//
//        import weka.classifiers.functions.MultilayerPerceptron;
//public class loadModel {
//
//    private static double actualValue;
//
//    public static void main(String[] args) throws Exception {
//        //load model
//        MultilayerPerceptron cls = (MultilayerPerceptron)weka.core.SerializationHelper.read("C:\\Users\\Administrator\\Desktop\\MLP\\src\\main\\mlp.model");
//        //load dataset
//        DataSource src = new DataSource("C:/Users/Administrator/Desktop/MLP/Test.arff");
//        Instances datatest = src.getDataSet();
//        //set class index to the last attribute
//        datatest.setClassIndex(datatest.numAttributes()-1);
//        //loop through the new dataset and make predictions
//        System.out.println(datatest.numInstances());
//        File file_result = new File("C://Users//Administrator//Desktop//Predict1.txt");
//        for (int i = 0; i < datatest.numInstances(); i++) {
//            FileWriter fr = new FileWriter(file_result, true);
//            BufferedWriter br = new BufferedWriter(fr);
//            actualValue = datatest.instance(i).classValue();
//            String actual = datatest.classAttribute().value((int) actualValue);
//            Instance newInst = datatest.instance(i);
//            double preValue = cls.classifyInstance(newInst);
//            String pre = datatest.classAttribute().value((int) preValue);
//            br.write(actualValue +" "+ pre);
//            // write breakline
//            br.write(System.getProperty("line.separator"));
//            br.close();
//            fr.close();
//            System.out.println("Hanh dong phat hien duoc la" + " " + pre);
//        }
//    }
//}
//  Vector<String> vec = new Vector<String>();
//  String csvFile = "C:\\Users\\Administrator\\Desktop\\test.csv";
//  BufferedReader br = null;
//  String line = "";
//  String cvsSplitBy = ",";
//  try {
//
//      br = new BufferedReader(new FileReader(csvFile));
//      while ((line = br.readLine()) != null) {
//          String[] csvtext = line.split(cvsSplitBy);
//          for (int i = 0; i < csvtext.length ; i++)
//          vec.add(csvtext[i].toString());
//      }
//  } catch (FileNotFoundException e) {
//      e.printStackTrace();
//  } catch (IOException e) {
//      e.printStackTrace();
//  } finally {
//      if (br != null) {
//          try {
//              br.close();
//          } catch (IOException e) {
//              e.printStackTrace();
//          }
//      }
//  }


