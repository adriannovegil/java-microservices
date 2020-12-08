package com.devcircus.java.microservices.mtsa.twitterrankcrawlerservice.math;

public class Statistics {

    /**
     * 
     * @param numArray
     * @return 
     */
    public static double standardDeviation(double[] numArray) {
        double sum = 0.0, standardDeviation = 0.0;
        int length = numArray.length;
        for (double num : numArray) {
            sum += num;
        }
        double mean = sum / length;
        for (double num : numArray) {
            standardDeviation += Math.pow(num - mean, 2);
        }
        return Math.sqrt(standardDeviation / length);
    }

    /**
     * 
     * @param m
     * @return 
     */
    public static double mean(double[] m) {
        double sum = 0;
        for (double v : m) {
            sum += v;
        }
        return sum / m.length;
    }
}
