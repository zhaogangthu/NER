/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import edu.stanford.nlp.classify.LinearClassifier;
import gmm.GMMD1Diag;
import gmm.GMMDiag;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import linearclassifier.AnalyzeLClassifier;
import static linearclassifier.AnalyzeLClassifier.TESTFILE;
import linearclassifier.Margin;
import tools.CNConstants;

/**
 *
 * @author synalp
 */
public class TestingGMM {

        public TestingGMM(){
            AnalyzeLClassifier analyzing = new AnalyzeLClassifier();
            //final float[] priors = computePriors(sclassifier,model);
            List<List<Integer>> featsperInst = new ArrayList<>(); 
            List<Integer> labelperInst = new ArrayList<>();        
            final float[] priors = {0.9f,0.1f};
            String sclass=CNConstants.PRNOUN;

            analyzing.trainOneNERClassifier(sclass,false);
            
            LinearClassifier model = analyzing.getModel(sclass);
            analyzing.getValues(TESTFILE.replace("%S", sclass),model,featsperInst,labelperInst);
            Margin margin = analyzing.getMargin(sclass);
            margin.setFeaturesPerInstance(featsperInst);
            margin.setLabelPerInstance(labelperInst);
    
            
            
            int numinst=labelperInst.size();
            //Margin margin = new Margin();
            //numinst=50;
            //analyzing.setNumberOfInstances(numinst);
            
            Margin.GENERATEDDATA=true;
            margin.generateBinaryRandomScore(numinst);
            /*
            System.out.println("x=[");
            for(int i=0;i<numinst;i++){
                System.out.println(margin.getGenScore(i, 0)+","+margin.getGenScore(i, 1)+";");
            }*/
            System.out.println("]");
            System.out.println("******  ONE DIMENSIONAL GMM ********");
            // get scores
            GMMD1Diag gmm = new GMMD1Diag(2, priors);
            gmm.train(margin);
            System.out.println("mean=[ "+gmm.getMean(0)+" , "+-gmm.getMean(0)+";\n"+
            +gmm.getMean(1)+" , "+-gmm.getMean(1)+"]");
            System.out.println("var=[ "+gmm.getVar(0)+" , "+gmm.getVar(0)+";\n"+
            +gmm.getVar(1)+" , "+gmm.getVar(1));
            System.out.println("GMM trained");      
            System.out.println("******  MULTIDIMENSIONAL GMM ********");
            GMMDiag gmmMD = new GMMDiag(2, priors);
            gmmMD.train(margin);
            System.out.println("mean=[ "+gmmMD.getMean(0,0)+" , "+gmmMD.getMean(0,1)+";\n"+
            +gmmMD.getMean(1,0)+" , "+gmmMD.getMean(1,1)+"]");
            System.out.println("var=[ "+gmmMD.getVar(0,1,1)+" , "+gmmMD.getVar(0,1,1)+";\n"+
            +gmmMD.getVar(1,0,0)+" , "+gmmMD.getVar(1,1,1));
            System.out.println("GMM trained");
    }
    
    public static void main(String[] args){
        TestingGMM test = new TestingGMM();
    }
}
