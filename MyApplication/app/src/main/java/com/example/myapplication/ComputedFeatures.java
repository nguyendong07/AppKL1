package com.example.myapplication;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.stat.correlation.Covariance;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;

import java.util.ArrayList;

public class ComputedFeatures {

    private static int WINDOWN_LENGTH_IN_SECOND ;
    private static FastFourierTransformer fastFourierTransformer;
    private static DescriptiveStatistics statisticX;
    private static DescriptiveStatistics statisticY;
    private static DescriptiveStatistics statisticZ;
    private static Covariance covariance;
    private static ArrayList<Double> ListFeature = new ArrayList<Double>();

    public static void main(String[] args) {

    }

    //caculate feature
    public static ArrayList<Double> getAllFeature(double[] X, double[] Y , double[] Z , double[] T, int i  ) {
        double ARA = getWindowARA(X,Y,Z);
        double MeanX = getAverage(X);
        double MeanY = getAverage(Y);
        double MeanZ = getAverage(Z);
        double MeanXYZ = getMeanXYZ(X,Y,Z);
        double VarX = getVariance(X);
        double VarY = getVariance(Y);
        double VarZ = getVariance(Z);
        double DiffX = getDistribution(X);
        double DiffY = getDistribution(Y);
        double DiffZ = getDistribution(Z);
        double sdX = getDistribution(X);
        double sdY = getDistribution(Y);
        double sdZ = getDistribution(Z);
        double CovarianceXY = getCovariance(X,Y);
        double CovarianceYZ = getCovariance(Y,Z);
        double CovarianceZX = getCovariance(Z,X);
        double ZeroCrossingX = getZeroCrossRate(X);
        double ZeroCrossingY = getZeroCrossRate(Y);
        double ZeroCrossingZ = getZeroCrossRate(Z);
        double Activity = getACTIVITY();
        double Complexity = getCOMPLEXITY();
        double Mobility = getMOBILITY();
        double Energy = getEnergy(X,Y,Z);
        double xFFTEnergy = getFFTEnergy(X);
        double yFFTEnergy = getFFTEnergy(Y);
        double zFFTEnergy = getFFTEnergy(Z);
        double xFFTEntropy = getFFTEntropy(X);
        double yFFTEntropy = getFFTEntropy(Y);
        double zFFTEntropy = getFFTEntropy(Z);
        double xPAR = getPAR(X);
        double yPAR = getPAR(Y);
        double zPAR = getPAR(Z);
        double xSMA = getSMAchild(X, T);
        double ySMA = getSMAchild(Y, T);
        double zSMA = getSMAchild(Z, T);
        double SMA = getSMA(X,Y,Z,T);
        double SMVD = getDSVM(X,Y,Z);
        double xActivity = getActivity(X);
        double yActivity = getActivity(Y);
        double zActivity = getActivity(Z);
        double pActivity = getActivityP(X,Y,Z);
        double tActivity = getActivityT(Y,Z);
        double xMobility = getMobility(X);
        double yMobility = getMobility(Y);
        double zMobility = getMobility(Z);
        double pMobility = getMobilityP(X,Y,Z);
        double tMobility = getMobilityT(Y,Z);
        double xComplexity  = getComplexity(X);
        double yComplexity  = getComplexity(Y);
        double zComplexity  = getComplexity(Z);
        double pComplexity  = getComplexityP(X,Y,Z);
        double tComplexity  = getComplexityT(Y,Z);
        double meanPhi = getMeanPhi(i,X,Y,Z);
        double meanTheta = getMeanTheta(i,Y,Z);
        double varPhi = getVariancePhi(i,X,Y,Z);
        double varTheta = getVarianceTheta(i,Y,Z);
        ListFeature.add(ARA);
        ListFeature.add(MeanX);
        ListFeature.add(MeanY);
        ListFeature.add(MeanZ);
        ListFeature.add(MeanXYZ);
        ListFeature.add(VarX);
        ListFeature.add(VarY);
        ListFeature.add(VarZ);
        ListFeature.add(DiffX);
        ListFeature.add(DiffY);
        ListFeature.add(DiffZ);
        ListFeature.add(sdX);
        ListFeature.add(sdY);
        ListFeature.add(sdZ);
        ListFeature.add(CovarianceXY);
        ListFeature.add(CovarianceYZ);
        ListFeature.add(CovarianceZX);
        ListFeature.add(ZeroCrossingX);
        ListFeature.add(ZeroCrossingY);
        ListFeature.add(ZeroCrossingZ);
        ListFeature.add(Activity);
        ListFeature.add(Complexity);
        ListFeature.add(Mobility);
        ListFeature.add(Energy);
        ListFeature.add(xFFTEnergy);
        ListFeature.add(yFFTEnergy);
        ListFeature.add(zFFTEnergy);
        ListFeature.add(xFFTEntropy);
        ListFeature.add(yFFTEntropy);
        ListFeature.add(zFFTEntropy);
        ListFeature.add(xPAR);
        ListFeature.add(yPAR);
        ListFeature.add(zPAR);
        ListFeature.add(xSMA);
        ListFeature.add(ySMA);
        ListFeature.add(zSMA);
        ListFeature.add(SMA);
        ListFeature.add(SMVD);
        ListFeature.add(xActivity);
        ListFeature.add(yActivity);
        ListFeature.add(zActivity);
        ListFeature.add(pActivity);
        ListFeature.add(tActivity);
        ListFeature.add(xMobility);
        ListFeature.add(yMobility);
        ListFeature.add(zMobility);
        ListFeature.add(pMobility);
        ListFeature.add(tMobility);
        ListFeature.add(xComplexity);
        ListFeature.add(yComplexity);
        ListFeature.add(zComplexity);
        ListFeature.add(pComplexity);
        ListFeature.add(tComplexity);
        ListFeature.add(meanPhi);
        ListFeature.add(meanTheta);
        ListFeature.add(varPhi);
        ListFeature.add(varTheta);
        //ListFeature.add();
        //ListFeature.add();
        return ListFeature;
    }

    // Sliding window function
    public static void SW() {

    }


    // all function calculate features
    public static Complex[] getFft(double[] S){
        int n = S.length;
         Complex[] FFT = new Complex[n];
        for (int  i = 0; i < n; i++) {
            FFT[i] = new Complex(S[i]);
        }
        FFT = fastFourierTransformer.transform(FFT,TransformType.FORWARD);
        return FFT;
    };

    public static double getFFTEnergy(double[] S){
        int length = S.length;
        Complex[] FFT = getFft(S);
        double sumFFEnergy = 0;
        for (int i = 0; i < length; i++){
            sumFFEnergy += FFT[i].getReal()*FFT[i].getReal();
        }
        return sumFFEnergy/length;
    }

    public static double getFFTEntropy(double[] S ){
        int length = S.length;
        Complex[] FFT = getFft(S);
        double sumFFTComponents = 0;
        for (int i = 0; i < length; i++){
            sumFFTComponents += FFT[i].getReal();
        }
        double sumFFTEntropy = 0;
        double pi;
        for (int i = 0; i < length; i++){
            pi = FFT[i].getReal()/sumFFTComponents;
            sumFFTEntropy += pi*Math.log(Math.abs(pi));
        }
        return -(sumFFTEntropy/length);
    }

    public static double getWindowARA(double[] x, double[] y, double[] z){
        int length = x.length;
        double sumSQRT = 0.0;
        for (int i = 0; i < length; i++){
            sumSQRT += Math.sqrt((x[i] * x[i]) + (y[i] * y[i]) + (z[i] * z[i]));
        }
        return sumSQRT/length;
    }

    // caculate avarage and standDeviation
    public static double getAverage(double[] S) {
        double  s = 0;
        for (double v : S) {
            s = +v;
        }
        return s/S.length;
    };

    public static double getStandDeviation(double[] S){
        double sd = 0;
        for (double v : S) {
            sd += Math.pow(v - getAverage(S), 2);
        }
        return sd;
    };


    //find mind, max value and caculate Distribution
    public static double Max(double[] S){
        double Max = S[0];
        for (int i = 0; i < S.length; i++) {
           if (S[i] > Max){
               Max = S[i];
           }
        }
        return Max;
    };

    public static double Min(double[] S){
        double Min = S[0];
        for (int i = 0; i < S.length; i++) {
            if (S[i] < Min){
                Min = S[i];
            }
        }
        return Min;
    };

    public static double getDistribution(double[] S){
        return Max(S) - Min(S);
    }


    public static double getVariance(double S[]) {
        double var =  0;
        for (double v : S) {
            var += Math.pow(v - getAverage(S), 2);
        }
        return var/S.length;
    };


    public static double getCovariance(double[] a, double[] b){
        double cov = 0;
        for (int i = 0; i < a.length; i++) {
            cov =+ ((a[i] - getAverage(a)) * (b[i] - getAverage(b)));
        }
        return cov/(a.length-1);
    }


    public static double getZeroCrossRate(double[] S){
        int numZC=0;
        int size=S.length;

        for (int i=0; i<size-1; i++){
            if((S[i]>=0 && S[i+1]<0) || (S[i]<0 && S[i+1]>=0)){
                numZC++;
            }
        }
        return (double)numZC/WINDOWN_LENGTH_IN_SECOND;
    }
    public static double getACTIVITY() {
        return 0;
    }
    public static double getCOMPLEXITY() {
        return 0;
    }

    public static double getMOBILITY() {
        return 0;
    }

    public static double getEnergy (double[] a, double[] b, double[] c) {
        int length = a.length;
        Complex[] aFFT = getFft(a);
        Complex[] bFFT = getFft(b);
        Complex[] cFFT = getFft(c);
        double sumFFEnergy = 0;
        for (int i = 0; i < length; i++){
            sumFFEnergy += aFFT[i].getReal()*aFFT[i].getReal()+bFFT[i].getReal()*bFFT[i].getReal()+cFFT[i].getReal()*cFFT[i].getReal();
        }
        return sumFFEnergy/length;
    }
    public static double getMeanXYZ(double[] a, double[] b, double[] c){
        double[] abc = new double[a.length];
        int length = abc.length;
        for (int i = 0; i < length; i++){
            abc[i] = a[i] + b[i] + c[i];
        }
        return new DescriptiveStatistics(abc).getMean();
    }

    // New feature 20170802 adding 8 feature
    public static  double getPAR(double[] S){
        double PAR = 0;
        double avg = getAverage(S);
        double m = Max(S);
        if (avg != 0) {
            PAR = m/avg;
        }
        return PAR;
    };


    public static double getSMA(double[] a, double[] b, double[] c, double[] t) {

        double total = 0;
        int windowsize = a.length;
        for (int i = 2; i < windowsize; i++) {

            double firstArg = Math.abs(a[i-1]) + Math.abs(a[i])
                    + Math.abs(b[i-1]) + Math.abs(b[i])
                    + Math.abs(c[i-1]) + Math.abs(c[i]);
            double secondArg = t[i] - t[i-1];
            total = total + firstArg*secondArg;
        }
        return ((1 * total) / (2*windowsize));
    }

    public static double getSMAchild(double[] S, double[] t) {
        double total = 0;
        int windowsize = S.length;
        for (int i = 2; i < windowsize; i++) {
            double firstArg = Math.abs(S[i-1]) + Math.abs(S[i]);
            double secondArg = t[i] - t[i-1];
            total = total + firstArg*secondArg ;
        }
        return ((1 ) / (2*windowsize* total));
    }

    public static double getDSVM(double[] a, double[] b, double[] c) {
        int windowsize = a.length;
        double total = 0;
        for (int i = 2; i < windowsize; i++) {
            double ax = Math.abs(a[i] - a[i-1]);
            double ay = Math.abs(b[i] - b[i-1]);
            double az = Math.abs(c[i] - c[i-1]);
            total += ax + ay + az;
        }
        return (total / windowsize);
    }

    // calculate Hjorth parameter values Activity Mobility and Complexity

    public static double getActivity(double[] S) {
        int windowsize = S.length;
        double A = 0;
        double d = 0;
        double tmp = 0;
        for  (int i = 0 ; i < windowsize; i++) {
            tmp = S[i+1] - S[i];
            d = d + Math.pow(tmp,2);
        }
        A = d/(windowsize-1);
        return  A;
    };

    public static double getActivityP(double[] a, double[] b, double[] c) {
        int windowsize = a.length;
        double M = 0;
        double m = 0;
        double tmp = 0;
        for (int i = 0; i< windowsize; i++){
            tmp = phiAngle(i+1,a,b,c) - phiAngle(i,a,b,c);
            m = m + Math.pow(tmp, 2);
        }
        m= m/(windowsize-2);
        double ac = getActivity(a);
        if(ac !=0){
            M = Math.sqrt(m/ac);
        }
        return  M;
    }

    public static double getActivityT(double[] b, double[] c) {
        int windowsize = b.length;
        double M = 0;
        double m = 0;
        double tmp = 0;
        for (int i = 0; i< windowsize; i++){
            tmp = thetaAngle(i+1,b,c) - thetaAngle(i,b,c);
            m = m + Math.pow(tmp, 2);
        }
        m= m/(windowsize-2);
        double ac = getActivity(b);
        if(ac !=0){
            M = Math.sqrt(m/ac);
        }
        return  M;
    }

    public static double getMobility(double[] S){
        int windowsize = S.length;
        double M = 0;
        double m = 0;
        double tmp = 0;
        for (int i = 0; i< windowsize; i++){
            tmp = S[i+2] + S[i];
            m = m + Math.pow(tmp, 2);
        }
        m= m/(windowsize-2);
        double ac = getActivity(S);
        if(ac !=0){
            M = Math.sqrt(m/ac);
        }
        return  M;
    };

    public static double getMobilityP(double[] a, double[] b, double[] c) {
        int windowsize = a.length;
        double M = 0;
        double m = 0;
        double tmp = 0;
        for (int i = 0; i< windowsize; i++){
            tmp = phiAngle(i+2,a,b,c) + phiAngle(i,a,b,c);
            m = m + Math.pow(tmp, 2);
        }
        m= m/(windowsize-2);
        double ac = getActivity(a);
        if(ac !=0){
            M = Math.sqrt(m/ac);
        }
        return  M;
    }

    public static double getMobilityT(double[] b, double[] c) {
        int windowsize = b.length;
        double M = 0;
        double m = 0;
        double tmp = 0;
        for (int i = 0; i< windowsize; i++){
            tmp = thetaAngle(i+2,b,c) + thetaAngle(i,b,c);
            m = m + Math.pow(tmp, 2);
        }
        m= m/(windowsize-2);
        double ac = getActivity(b);
        if(ac !=0){
            M = Math.sqrt(m/ac);
        }
        return  M;
    }

    // Get Complexity function
    public static double getComplexity(double[] S) {
        int windowsize = S.length;
        double M = 0;
        double m = 0;
        double tmp = 0;
        for (int i = 0; i < windowsize - 3; i++) {
            tmp = S[i + 3] - S[i + 2] + S[i + 1] - S[i];
            m = m + Math.pow(tmp, 2);
        }
        m = m / (windowsize - 3);
        double mb = getMobility(S);
        if (mb != 0) {
            M = Math.sqrt(m / mb);
        }
        return M;
    };

    public static double getComplexityP(double[] a, double[] b, double[] c) {
        int windowsize = a.length;
        double M = 0;
        double m = 0;
        double tmp = 0;
        for (int i = 0; i < windowsize - 3; i++) {
            tmp = phiAngle(i + 3,a,b,c) - phiAngle(i+2,a,b,c) + phiAngle(i+1,a,b,c) - phiAngle(i,a,b,c);
            m = m + Math.pow(tmp, 2);
        }
        m = m / (windowsize - 3);
        double mb = getMobility(a);
        if (mb != 0) {
            M = Math.sqrt(m / mb);
        }
        return M;
    };

    public static double getComplexityT(double[] b, double[] c) {
        int windowsize = b.length;
        double M = 0;
        double m = 0;
        double tmp = 0;
        for (int i = 0; i < windowsize - 3; i++) {
            tmp = thetaAngle(i+3,b,c) -thetaAngle(i+2,b,c) + thetaAngle(i+1,b,c)-thetaAngle(i,b,c);
            m = m + Math.pow(tmp, 2);
        }
        m = m / (windowsize - 3);
        double mb = getMobility(b);
        if (mb != 0) {
            M = Math.sqrt(m / mb);
        }
        return M;
    };

    //************************The values of angle************************************//
    public static double phiAngle(int i, double[] a, double[] b, double[] c){ // return value of phi at k
        double phi = 0;
        if(Math.sqrt(Math.pow(a[i], 2)+ Math.pow(c[i],2 ))!=0){
            phi = Math.tan(b[i] / Math.sqrt(Math.pow(a[i], 2)+ Math.pow(c[i],2 )));
        }
        if(phi != 0){
            phi = 1/phi;
        }
        return phi;
    }
    public static double igPhi(int i, double[] a, double[] b, double[] c, double[] t){// prepared  for getIgPhi function
        double iphi =0;
        if ((phiAngle(i, a, b, c) + phiAngle(i - 1, a, b, c)) * (t[i] - t[i - 1]) != 0) {
            iphi = 1 / (2 * (phiAngle(i, a, b, c) + phiAngle(i - 1, a, b, c)) * (t[i] - t[i - 1]));
        }
        return iphi;
    }

    // value of theta

    public static double thetaAngle(int i, double[] b, double[] c){ // return value of phi at k
        double theta = 0;
        if(c[i]!=0){
            theta = Math.tan(b[i] / c[i]);
        }
        if(theta != 0){
            theta = 1/theta;
        }
        return theta;
    }

    public static double igTheta(int i, double[] b, double[] c ,double[] t){// prepared  for getIgTheta function
        double iTheta =0;
        if ((thetaAngle(i,b,c ) + thetaAngle(i - 1, b,c)) * (t[i] - t[i - 1]) != 0) {
            iTheta = 1 / (2 * (thetaAngle(i, b, c) + thetaAngle(i - 1,b,c)) * (t[i] - t[i - 1]));
        }
        return iTheta;
    }


    public static double getMeanPhi(int i, double[] a, double[] b , double[] c){
        double mphi =0;
        int wsize = a.length;
        mphi = mphi + phiAngle(i,a,b,c);
        return mphi/wsize;
    }


    public static double getMeanTheta(int i, double[] b, double[] c){
        double mtheta =0;
        int wsize = b.length;
        mtheta = mtheta + thetaAngle(i,b,c);
        return mtheta/wsize;
    }


    public static double getVariancePhi(int i, double[] a,  double[] b, double[] c){
        double vphi =0;
        double tmp = 0;
        int wsize = a.length;
        tmp = tmp + Math.pow(phiAngle(i,a,b,c) + getMeanPhi(i,a,b,c), 2) ;
        vphi = tmp/wsize;
        return vphi;
    }

    public static double getVarianceTheta(int i , double[] b, double[] c){
        double vtheta =0;
        double tmp = 0;
        int wsize = b.length;
        tmp = tmp + Math.pow(thetaAngle(i,b,c) + getMeanTheta(i,b,c), 2) ;
        vtheta = tmp/wsize;
        return vtheta;
    }

}
