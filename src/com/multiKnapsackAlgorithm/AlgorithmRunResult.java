package com.multiKnapsackAlgorithm;

public class AlgorithmRunResult {
    int tasksNm=100;
    private int instanceNo;
    private String testCaseName;
    private String algorithmName;
    private long elapsedTime;
    private long elapsedTimeR;// for DSTAR
    private Double totalProfit;
    private Integer totalDataSize;
    private Integer totalDataSizeSet;
    private Integer deletedTasks;// deleted tasks after local search from DSTA
    private String[] serverAssignment;
    private Integer throughput;
    private Double phase2totalProfit;
    private String[] phase2serverAssignment;
    private Integer phase2throughput;
    private Double maxProfit;
    private Double minDDSTA;
    private Double minDLS;

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

    public long getElapsedTimeR() { // for DSTAR
        return elapsedTimeR;
    }

    public void setElapsedTimeR(long elapsedTime) {
        this.elapsedTimeR = elapsedTime;
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

    public void setDeletedTasks(Integer taskCount) {
        this.deletedTasks = taskCount;
    }
    public Integer getDeletedTasks() {
       return deletedTasks;
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




    public void setThroughput(Integer thr) {
        this.throughput = thr;
    }

    public float getThroughput() {
        float th=((float)throughput/tasksNm);
        th=  Math.round(th*100);// round up to 2 decimal
        th= th/100;
        return th;
    }



    public void setThroughputphase2(Integer thr) {
        this.phase2throughput = thr;
    }

    public float getThroughputphase2() {

        float th= Math.round(throughput*100);
        th= (float)phase2throughput/tasksNm;
        th=  Math.round(th*100);// round up to 2 decimal
        th= th/100;
        return th;


    }

    public int getAddedTasksphase2() {
        return phase2throughput-throughput+deletedTasks;
    }


    public void setMaxProfit(double maxP) {
        this.maxProfit = maxP;
    }

    public double getMaxProfit() {
        return maxProfit;
    }


    public void setminDataSize_DSTA(double minD) {
        this.minDDSTA = minD;
    }

    public double getminDataSize_DSTA() {
        return minDDSTA;// min data size for DSTA if we assign all the tasks to one server
    }

    public void setminDataSize_LS(double minD) {
        this.minDLS = minD;
    }

    public double getminDataSize_LS() {
        return minDLS;
    }


}
