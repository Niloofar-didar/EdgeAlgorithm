package com.multiKnapsackAlgorithm;

public class AlgorithmRunResult {
    private int instanceNo;
    private String testCaseName;
    private String algorithmName;
    private long elapsedTime;
    private Double totalProfit;
    private Integer totalDataSize;
    private Integer totalDataSizeSet;
    private String[] serverAssignment;
    private float throughput;
    private Double phase2totalProfit;
    private String[] phase2serverAssignment;
    private float phase2throughput;

    public AlgorithmRunResult(){

    }
    public int getInstanceNo() {
        return instanceNo;
    }

    public void setInstanceNo(int instanceNo) {
        this.instanceNo = instanceNo;
    }
    public String getTestCaseName() {
        return testCaseName;
    }

    public void setTestCaseName(String testCaseName) {
        this.testCaseName = testCaseName;
    }
    public String getAlgorithmName() {
        return algorithmName;
    }

    public void setAlgorithmName(String algorithmName) {
        this.algorithmName = algorithmName;
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public Double getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(Double totalProfit) {
        this.totalProfit = totalProfit;
    }


    public Double getTotalProfitphase2() {
        return phase2totalProfit;
    }

    public void setTotalProfitphase2(Double totalProfit) {
        this.phase2totalProfit = totalProfit;
    }

    public Integer getTotalDataSizephase2() {
        return totalDataSizeSet;
    }

    public void setTotalDataSizephase2(Integer totalDataSize) {
        this.totalDataSizeSet = totalDataSize;
    }

    public Integer getTotalDataSize() {
        return totalDataSize;
    }

    public void setTotalDataSize(Integer totalDataSize) {
        this.totalDataSize = totalDataSize;
    }

    public void setServerAssignment(String [] servers) {
        this.serverAssignment = servers;

    }

    public String[] getServerAssignment() {
        return serverAssignment;

    }

    public void setServerAssignmentphase2(String [] servers) {
        this.phase2serverAssignment = servers;

    }
    public String[] getServerAssignmentphase2() {
        return phase2serverAssignment;

    }




    public void setThroughput(Float thr) {
        this.throughput = thr;
    }

    public float getThroughput() {
       return throughput;
    }



    public void setThroughputphase2(Float thr) {
        this.phase2throughput = thr;
    }

    public float getThroughputphase2() {
        return phase2throughput;
    }



}
