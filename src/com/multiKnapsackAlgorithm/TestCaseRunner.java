package com.multiKnapsackAlgorithm;

import com.multiKnapsackAlgorithm.PGreedy.PGreedyAlgorithm;
import com.multiKnapsackAlgorithm.hm.ResultWriter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestCaseRunner {
    private String instanceDirectoryName;
    private int numberOfInstances;
    private String[] inputFilePaths;
    private String currentInputFilePath;
    private GreedyAlgorithmBase[] greedyAlgorithmInstances;
    private ResultWriter resultWriter;
    {
        // running on multiple files
         instanceDirectoryName="instance";
        // numberOfInstances=10;

        //instanceDirectoryName="init";
        numberOfInstances=5;
        inputFilePaths=new String[]{
               // "testCase00.txt"
       // };
      //  /*nill -> running on multiple files
        "1.LowDemand-LowSharing.txt"
        ,"2.LowDemand-AverageSharing.txt","3.LowDemand-HighSharing.txt",
                "4.AverageDemand-LowSharing.txt","5.AverageDemand-AverageSharing.txt","6.AverageDemand-HighSharing.txt",
                "7.HighDemand-LowSharing.txt", "8.HighDemand-AverageSharing.txt","9.HighDemand-HighSharing.txt"
               }; //
//*/
        currentInputFilePath=inputFilePaths[0];
        resultWriter=ResultWriter.getInstance();
    }

    public TestCaseRunner(String instanceDirectoryName, int numberOfInstances, String[] inputFilePaths) {
        this.instanceDirectoryName = instanceDirectoryName;
        this.numberOfInstances = numberOfInstances;
        this.inputFilePaths = inputFilePaths;
    }

    public ArrayList<AlgorithmRunResult> runAlgorithms(String testCaseFilePath){
        ArrayList<AlgorithmRunResult> algorithmsRunResults=new ArrayList<>();
        System.out.println("----------------------------------------------------------");
        System.out.println("TestCaseFilePath: "+testCaseFilePath +"\t-> Begin");
        System.out.println("----------------------------------------------------------");


        greedyAlgorithmInstances= new GreedyAlgorithmBase[]{
                  new GreedyAlgorithms(testCaseFilePath),new PGreedyAlgorithm(testCaseFilePath),
        };




        for (GreedyAlgorithmBase oGreedyAlgorithmItem:greedyAlgorithmInstances) {
            AlgorithmRunResult algorithmRunResult=new AlgorithmRunResult();
            System.out.println("----------------------------------------------------------");
            String algorithmName=oGreedyAlgorithmItem.getClass().getSimpleName();
            System.out.println("Algorithm: "+algorithmName);
            algorithmRunResult.setAlgorithmName(algorithmName);

            if(!algorithmName.equals("GreedyAlgorithms")) { // for DSTA and DSTAR is diff
                //Instant runStart = Instant.now();
                oGreedyAlgorithmItem.run();
               // Instant runEnd = Instant.now();
               // long runTimeElapsedMillis = Duration.between(runStart, runEnd).toMillis();
                algorithmRunResult.setElapsedTime(oGreedyAlgorithmItem.getETimePG());

             //   System.out.println("Run: TimeElapsed: " + runTimeElapsedMillis);
               // algorithmRunResult.setElapsedTime(runTimeElapsedMillis);
            }
            else{// for DSTA & DSTAR
                oGreedyAlgorithmItem.run();
                System.out.println("Run: TimeElapsed DSTA: " + oGreedyAlgorithmItem.getETimeDSTA());
                algorithmRunResult.setElapsedTime(oGreedyAlgorithmItem.getETimeDSTA());
                System.out.println("Run: TimeElapsed DSTAR portion: " + oGreedyAlgorithmItem.getETimeDSTAR());
                algorithmRunResult.setElapsedTimeDstar(oGreedyAlgorithmItem.getETimeDSTAR());

            }


            System.out.println("TotalProfit: "+oGreedyAlgorithmItem.getTotalProfit());
            algorithmRunResult.setTotalProfit(oGreedyAlgorithmItem.getTotalProfit());

            System.out.println("TotalDataSize: "+oGreedyAlgorithmItem.getTotalDataSize());
            algorithmRunResult.setTotalDataSize(oGreedyAlgorithmItem.getTotalDataSize());


            //System.out.println(oGreedyAlgorithmItem.toString());
            System.out.println("----------------------------------------------------------");
            System.out.println();

            //System.out.println("ServerAssignment: "+oGreedyAlgorithmItem.getserversAssignment());
            //nil
            String []serverDSTA= oGreedyAlgorithmItem.getserversAssignment( oGreedyAlgorithmItem.getTaskItems());
           List<String> sDSTA= Arrays.asList(serverDSTA);
            algorithmRunResult.setServerAssignment(serverDSTA);
            algorithmRunResult.setThroughput(oGreedyAlgorithmItem.getThroughput(oGreedyAlgorithmItem.getTaskItems())); // gets tasks


            /// for DSTAR reallocation  results:
            if(algorithmName.equals("GreedyAlgorithms")) {
                algorithmRunResult.setTotalProfitDstar(oGreedyAlgorithmItem.getTotalProfitset());
                algorithmRunResult.setTotalDataSizeDstar(oGreedyAlgorithmItem.getTotalDataSizeSet());
                String[] serverDstar = oGreedyAlgorithmItem.getserversSetAssignment(oGreedyAlgorithmItem.getSetItems());
                List<String> sDstar = Arrays.asList(serverDstar);
                int count = checkDataSharing(sDSTA, sDstar);
                algorithmRunResult.setServerAssignmentDstar(serverDstar);
                algorithmRunResult.setDeletedTasks(count);
                algorithmRunResult.setThroughputDstar(oGreedyAlgorithmItem.getSetThroughput(oGreedyAlgorithmItem.getSetItems())); // gets set
                algorithmRunResult.setMaxProfit(oGreedyAlgorithmItem.getMaxProfit());
                //algorithmRunResult.setminDataSize_DSTA(oGreedyAlgorithmItem.getMinTotDatDSTA());
               // algorithmRunResult.setminDataSize_LS(oGreedyAlgorithmItem.getMinTotDatLS());
            }

            algorithmsRunResults.add(algorithmRunResult);

        }
        System.out.println("----------------------------------------------------------");
        System.out.println("TestCaseFilePath: "+testCaseFilePath+"\t-> End");
        System.out.println("----------------------------------------------------------");
        return algorithmsRunResults;
    }


    public int checkDataSharing(List<String> sDSTA, List<String> sDstar){// returns count of tasks that are not preserved from DSTA
        // we use this function to see if all data from DSTA is preserved, we are sure that the increase in datasize is for increase in overall throughput

        int count=0;
        for(int i=0;i< sDSTA.size();i++)
        {
            String val=sDSTA.get(i);
            if(val!="null" && !sDstar.contains(val))
                count++;

        }
        return count;
    }

    public ArrayList<AlgorithmRunResult> runTestCases(String  instanceDirectoryName,int currentInstanceNumber,String[] inputFilePaths) {
        ArrayList<AlgorithmRunResult> algorithmsRunResults = new ArrayList<>();
        instanceDirectoryName = "data\\" + instanceDirectoryName + currentInstanceNumber + "\\";

        // this is for DSTA and DSTAR
       // String[] algNameAr = new String[]{"DSTA", "PGreedy"};
       // for(String algName: algNameAr){
        for (String currentTestCaseInputFileName : inputFilePaths) {
            String currentTestCaseInputFilePath = inputFilePathBuilder(instanceDirectoryName, currentTestCaseInputFileName);
            ArrayList<AlgorithmRunResult> currentInputFileAlgorithmsRunResult = runAlgorithms(currentTestCaseInputFilePath);
            // ArrayList<AlgorithmRunResult>  currentInputFileAlgorithmsRunResult2= runAlgorithms(currentTestCaseInputFilePath,"PGreedy");

            currentInputFileAlgorithmsRunResult.forEach(current -> {
                current.setTestCaseName(currentTestCaseInputFileName);
            });
            algorithmsRunResults.addAll(currentInputFileAlgorithmsRunResult);

//            currentInputFileAlgorithmsRunResult2.forEach(current->{
//                current.setTestCaseName(currentTestCaseInputFileName);
//            });
//            algorithmsRunResults.addAll(currentInputFileAlgorithmsRunResult2);
        }
    //}



        algorithmsRunResults.forEach(current->{
            current.setInstanceNo(currentInstanceNumber);
        });
        return algorithmsRunResults;
    }
    public ArrayList<AlgorithmRunResult> executeForAllInstances(String instanceDirectoryName,int numberOfInstances,String[] inputFilePaths){
        ArrayList<AlgorithmRunResult> algorithmRunResults=new ArrayList<>();
        for (int currentInstanceNumber = 0; currentInstanceNumber < numberOfInstances; currentInstanceNumber++) {
            ArrayList<AlgorithmRunResult> testCasesRunResults=new ArrayList<>();
            System.out.println("**********************************************************");
            System.out.println("Instance: #"+currentInstanceNumber+"\t-> End");
            System.out.println("**********************************************************");

            testCasesRunResults= runTestCases(instanceDirectoryName,currentInstanceNumber,inputFilePaths);
            algorithmRunResults.addAll(testCasesRunResults);

            System.out.println("**********************************************************");
            System.out.println("Instance: #"+currentInstanceNumber+"\t-> End");
            System.out.println("**********************************************************");
        }
        return algorithmRunResults;
    }

    private String inputFilePathBuilder(String instanceDirectoryName,String inputFileName) {

            String inputFilePath=instanceDirectoryName+inputFileName;
            return inputFilePath;
    }

    public void executeAutomation(){

        resultWriter.data=new ArrayList<>(){{
            add("#Instance");
            add("TestCaseName");
            add("Algorithm");
            add("ExecutionTime");
            add("TotalProfit");
            add("TotalDataSize");
            add("ServerAssignment");
            add("Throughput");

            add("LSTotalProfit");
            add("LSTotalDataSize");
            add("LSServerAssignment");
            add("LSThroughput");
           // add("DeletedTasksFromDSTA");
            add("LSAddedTasks");
            add("MaxProfit");
         //   add("minDataSize_DSTA");
        //    add("minDataSize_LS");
            add("ExecutionTimeDSTAR");

        }};
        resultWriter.write();
        ArrayList<AlgorithmRunResult> runResults= executeForAllInstances(instanceDirectoryName,numberOfInstances, inputFilePaths);
        ArrayList<String> result=null;
        for (AlgorithmRunResult runResult:runResults) {// each runResult has output for either of algorithms and Demand-sharing set in order


            result=new ArrayList<>(){{
                add(String.valueOf(runResult.getInstanceNo()));
                add(runResult.getTestCaseName());
                add(runResult.getAlgorithmName());
                add(String.valueOf( runResult.getElapsedTime()));
                add(String.valueOf( runResult.getTotalProfit()));
                add(String.valueOf(runResult.getTotalDataSize()));
                add(Arrays.toString(runResult.getServerAssignment()));
                add(String.valueOf(runResult.getThroughput()));
                if(runResult.getAlgorithmName().equals("GreedyAlgorithms")) {
                    add(String.valueOf(runResult.getTotalProfitDstar()));// DSTAR profit
                    add(String.valueOf(runResult.getTotalDataSizeDstar()));// DSTAR datasharing
                    add(Arrays.toString(runResult.getServerAssignmentDstar())); // DSTAR  server assignment
                    add(String.valueOf(runResult.getThroughputDstar())); //DSTAR allocated tasks
                    //add(String.valueOf(runResult.getDeletedTasks()));
                    add(String.valueOf(runResult.getAddedTasksDstar()));
                    add(String.valueOf(runResult.getMaxProfit()));
                    //add(String.valueOf(runResult.getminDataSize_DSTA()));
                    //add(String.valueOf(runResult.getminDataSize_LS()));
                    add(String.valueOf(runResult.getElapsedTimeDstar()));
                }

            }};
            resultWriter.data=result;
            resultWriter.write();
        }

        resultWriter.close();
    }

}
