package com.multiKnapsackAlgorithm;

import com.multiKnapsackAlgorithm.hm.Logger;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

public abstract class GreedyAlgorithmBase {
    private ArrayList<Task> taskItems;
    private ArrayList<Task> setItems;//nil
    private Double[] requestItems;
    private Double[] profitItems;
    private Integer taskCount;
    private Integer[][] taskDataTypeMatrix;
    private Integer knapsackCount;
    private Knapsack[] knapsackItems;
    private DataType[] dataTypeItems;
    private int[] dataSize;
    private int dataTypeItemsCount;
    private Integer[] sum;
    private Byte[] sumPrime;
    private ArrayList<Task> candidTaskItems;
    private ArrayList<DataType> candidDataTypeItems;
    private Double totalProfit;
    private Double settotalProfit;
    private Integer totalDataSize;
    private Integer totalDataSizeSet;
    public String serverInf;
    private Integer mintotDataLocalS;
    private Integer mintotDataDSTA;
    long DSTAeTime;
    long DSTAReTime;
    long PGeTime;




    /**
     * @return the execution time for DSTA
     */
    public long getETimeDSTA() {
        return DSTAeTime;
    }

    /**
     * @param set the execution time DSTA
     */
    public void setETimeDSTA(long time) {
        this.DSTAeTime = time;
    }


    public long getETimeDSTAR() {
        return DSTAReTime;
    }
    /**
     * @param set the execution time DSTAR
     */
    public void setETimeDSTAR(long time) {
        this.DSTAReTime = time;
    }

    public long getETimePG() {
        return PGeTime;
    }
    /**
     * @param set the execution time DSTAR
     */
    public void setETimePG(long time) {
        this.PGeTime = time;
    }

    /**
	 * @return the totalDataSize
	 */
	public Integer getTotalDataSize() {
		return totalDataSize;
	}

	/**
	 * @param totalDataSize the totalDataSize to set
	 */
	public void setTotalDataSize(Integer totalDataSize) {
		this.totalDataSize = totalDataSize;
	}


    public Integer getTotalDataSizeSet() {
        return totalDataSizeSet;
    }


    /**
     * @param totalDataSize the totalDataSize to set
     */
    public void setTotalDataSizeSet(Integer totalDataSize) {
        this.totalDataSizeSet = totalDataSize;
    }



    /**
     * @return the MintotalDataSize : for DSTA and LocalSerarch
     */
    public Integer getMinTotDatDSTA() {// min data size for DSTA if we assign the tasks to one server
        return mintotDataDSTA;
    }

    public void setMinTotDatDSTA(Integer minDSTA) {
        this.mintotDataDSTA = minDSTA;
    }

    public Integer getMinTotDatLS() {// min data size for Local search if we assign the tasks to one server
        return mintotDataLocalS;
    }

    public void setMinTotDatLS(Integer minLS) {
        this.mintotDataLocalS = minLS;
    }


	
	private Double candidDataTypeItemsProportionValue;
    {
        taskItems=new ArrayList<Task>();
        setItems=new ArrayList<Task>();
        taskCount=Integer.MIN_VALUE;
        knapsackCount=Integer.MIN_VALUE;
        candidDataTypeItems=new ArrayList<DataType>();
    }

    public Double[] getProfitItems() {
        return profitItems;
    }

    public void setProfitItems(Double[] profitItems) {
        this.profitItems = profitItems;
    }

    public Double[] getRequestItems() {
        return requestItems;
    }

    public void setRequestItems(Double[] requestItems) {
        this.requestItems = requestItems;
    }

    public Knapsack[] getKnapsackItems() {
        return knapsackItems;
    }

    public void setKnapsackItems(Knapsack[] knapsackItems) {
        this.knapsackItems = knapsackItems;
    }
    public void setKnapsackItems(String line,String splitter){
        Double[] knapsackItemsCapacity= Helper.readDoubleArrayFromString(line,0,",");
        List<Knapsack> knapsackList=new ArrayList<>();
//        Arrays.asList(knapsackItemsCapacity).forEach(current->{
//            knapsackList.add(new Knapsack(index, current));
//            index++;
//        });
        List<Double> knapsackItemsCapacityArrayList=new ArrayList<>();
        knapsackItemsCapacityArrayList= Arrays.asList(knapsackItemsCapacity);

        for (int index=0;index<knapsackItemsCapacityArrayList.size();index++){
            knapsackList.add(new Knapsack(index, knapsackItemsCapacity[index]));
        }
        this.knapsackItems=knapsackList.toArray(new Knapsack[knapsackList.size()]);
    }
    public ArrayList<Task> getCandidTaskItems() {
        return candidTaskItems;
    }

    public ArrayList<DataType> getCandidDataTypeItems() {
        return candidDataTypeItems;
    }

    public void setCandidDataTypeItems(ArrayList<DataType> candidDataTypeItems) {
        this.candidDataTypeItems = candidDataTypeItems;
    }

    public Double getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(Double totalProfit) {
        this.totalProfit = totalProfit;
    }


    public void setTotalProfitset(Double totalProfit) {
        this.settotalProfit = totalProfit;
    }

    public Double getTotalProfitset() {
        return settotalProfit;
    }

    public Integer[] getSum() {
        return sum;
    }

    public void setSum(Integer[] sum) {
        this.sum = sum;
    }
    public void setSum(){
        for (int index = 0; index < dataTypeItems.length; index++) {
            DataType currentDataType= dataTypeItems[index];
            sum[index]= setSum(currentDataType.getDataTypeName());
        }
    }

    public Byte[] getSumPrime() {
        return sumPrime;
    }

    public ArrayList<Task> getTaskItems() {
        return taskItems;
    }


    public ArrayList<Task> getSetItems() {
        return setItems;
    }


    public int[] getDataSize(){ return dataSize;}

    public void setTaskItems(ArrayList<Task> taskItems) {
        this.taskItems = taskItems;
    }





    public DataType[] getDataTypeItems() {
        return dataTypeItems;
    }

    public void setDataTypeItems(DataType[] dataTypeItems) {
        this.dataTypeItems = dataTypeItems;
    }

// Nill hint : in this lines, we get information of 10 files within each instances, eg, `-lowdemandLowsharing, 2- lowD-highSharing,....
    public GreedyAlgorithmBase(String inputFilePath){
        try{
            int currentLine=0;
            List<String> lines= Helper.readLinesFromInputFile(inputFilePath);
            int taskDataTypeMatrixSize=Integer.valueOf(  lines.get(currentLine));
            Logger.message(taskDataTypeMatrixSize);

            //stores task-data matrix information
            this.taskDataTypeMatrix=Helper.read2DIntegerArrayFromStringList(lines,taskDataTypeMatrixSize," ");
            currentLine+=taskDataTypeMatrixSize+1;
            Logger.message(Arrays.deepToString( taskDataTypeMatrix));
            // stores resources needed by each task
            this.requestItems=Helper.readDoubleArrayFromString(lines.get(currentLine),taskDataTypeMatrixSize,",");
            currentLine++;
            Logger.message(Arrays.deepToString(requestItems));
            // stores task profit
            this.profitItems=Helper.readDoubleArrayFromString(lines.get(currentLine),taskDataTypeMatrixSize,",");
            currentLine++;
            Logger.message(Arrays.deepToString(profitItems));

            this.initialize();
            // stores server's capacity information
            serverInf=lines.get(currentLine);
            this.setKnapsackItems(serverInf,",");
            Logger.message(Arrays.deepToString( knapsackItems));

        }catch (IOException ioException){
            ioException.printStackTrace();
        }catch (SecurityException securityException){
            securityException.printStackTrace();
        }
    }


    public void initialize(){

        //nil fixes this-> it is wrong, just gets first row of data as dataTypeItems, we need an arrray of all data typr with their corr size
        this.dataTypeItems=extractDataTypeItemsFromMatrixRow(taskDataTypeMatrix[0]);
        this.dataTypeItemsCount=this.dataTypeItems.length;
        this.sum=new Integer[this.dataTypeItemsCount];

        int [] dataSize = new int[dataTypeItemsCount];


        for (int row=0;row<taskDataTypeMatrix.length;row++) {
            Task oTask=new Task(row);

            Integer[] taskRow= taskDataTypeMatrix[row];// each row is an array of task required data
            Set<Integer> requiredDataList= new HashSet<>();
            for (int i=0; i< taskRow.length;i++)
            {
                if(taskRow[i]!=0) {
                    requiredDataList.add(i); // adds all the data indexes that is needed by each task
                    if( dataSize[i]==0)
                      dataSize[i]=taskRow[i];
                }
            }

            oTask.setReqDataList(requiredDataList);

            DataType[] dataTypeItems= extractDataTypeItemsFromMatrixRow(taskRow);
            oTask.setDataTypeItems(dataTypeItems);

            Double profit=profitItems[row];
            oTask.setProfit(profit);

            Double request=requestItems[row];
            oTask.setRequest(request);



            addTaskItem(oTask);
        }
        this.dataSize=dataSize;
    }


    public void initializeSetItems(HashMap<Integer, List<Integer>> taskSet) {//nil

        int size= taskSet.size();
        for (int i=0; i<size; i++){ // runs per set that might have one or more tasks


            Task oTask=new Task(i); // to the number of set we have tasks

            double totProfit=0;
            double totRcr= 0;
            List<Integer> tempTasks= taskSet.get(i);
            Set<Integer> setDataIndex= new HashSet<>();// for each set we have dataIndex set-> stores all the data within each set of tasks
            for (Integer t : tempTasks){// all the task index within a set
                oTask.subsetTask.add(t);
                totProfit+= taskItems.get(t).getProfit();// calculate overall profit and req per set
                totRcr+=taskItems.get(t).getRequest();


               for(Integer requiredDataIndex: getTaskItems().get(t).getReqDataList())
                   setDataIndex.add(requiredDataIndex);


            }
            oTask.setReqDataList(setDataIndex);

            Double profit=totProfit;
            oTask.setProfit(profit);

            Double request=totRcr;
            oTask.setRequest(request);

            double efficiency= getEfficiency(totProfit,totRcr );

            oTask.setEfficiency(efficiency);

            addSetItem(oTask);


        }


    }


    public GreedyAlgorithmBase(Integer[][] taskDataTypeMatrix, Double[] requestItems, Double[] profitItems) {

        this.taskDataTypeMatrix = taskDataTypeMatrix;
        this.requestItems=requestItems;
        this.profitItems=profitItems;
        this.initialize();
        

    }
    public GreedyAlgorithmBase(Integer[][] taskDataTypeMatrix,Double[] requestItems,Double[] profitItems, Knapsack[] knapsackItems) {
        this(taskDataTypeMatrix,requestItems,profitItems);
        this.knapsackItems = knapsackItems;
    }
    private DataType[] extractDataTypeItemsFromMatrixRow(Integer[] taskRow) {
        //DataType[] dataTypeItems=null;
        ArrayList<DataType> dataTypeItemsArrayList= new ArrayList<DataType>();
        for (int index=0;index<taskRow.length;index++) {
            String dataTypeName=String.valueOf(index) ;
            DataType oDataType=new DataType(dataTypeName,taskRow[index]);
            dataTypeItemsArrayList.add(oDataType);
        }

        DataType[] dataTypeItems=new DataType[dataTypeItemsArrayList.size()];
        dataTypeItems=	dataTypeItemsArrayList.toArray(dataTypeItems);
        return dataTypeItems;
    }
    private void addTaskItem(Task oTask) {
        taskItems.add(oTask);
    }


    private void addSetItem(Task oTask) {
        setItems.add(oTask);
    }


    public void setCandidTaskItems(ArrayList<Task> candidTaskItems) {
        this.candidTaskItems = candidTaskItems;
    }
    public ArrayList<Task> updateTaskItemsEfficiency(int jTilda){// calculates the efficiency of the tasks with data of D[jtlda]
        for (Task taskItem:taskItems) {
            taskItem.setEfficiency(0.0);
            if (isTaskFeasibleForEfficiencyUpdate(taskItem,jTilda)){// if task has data[jtilda] with not-zero size (uses the data)
                taskItem.setEfficiency(knapsackItems);
            }
        }
        return taskItems;
    }
//nil
    public HashMap<Integer, List<Integer>> TaskSetMap(){// calculates the efficiency of set after one round of DSTA ALG

        HashMap<Integer, List<Integer>> serverTask= new HashMap<>();
        for (int k=0;k<knapsackItems.length; k++)
            serverTask.put(k, new ArrayList<>());

        Integer[] servers= getTaskserversAssignment(taskItems);

        for(int i=0; i<servers.length;i++){
            if(servers[i]!= -1) // task belongs to a set of assigned tasks from DSTA
                serverTask.get(servers[i]).add(i); //
            else
            {
                int lastIndex= serverTask.size();
                serverTask.put(lastIndex, new ArrayList<>());
                serverTask.get(lastIndex).add(i);
            }
        }
        HashMap<Integer, List<Integer>> fserverTask= new HashMap<>();
        int j=0;
        for(int i=0; i<serverTask.size();i++) {
        if(serverTask.get(i).size()!=0)
        {
            fserverTask.put(j, new ArrayList<>(serverTask.get(i)));
            j++;
        }
        }

        return fserverTask;
    }

/*
    public double[] calEfficiencySet(HashMap<Integer, List<Integer>> taskSet){ // calculates efficiency of set of assigned and un assigned tasks

        int size= taskSet.size();
        double [] efficiency = new double[size];
        for(int i=0; i<size;i++) {

            float totProfit=0;
            float totRcr= 0;
            List<Integer> tempTasks= taskSet.get(i);
            for (Integer t : tempTasks){

                totProfit+= taskItems.get(t).getProfit();
                totRcr+=taskItems.get(t).getRequest();
            }

            efficiency[i]=getEfficiency(totProfit,totRcr );

        }
        return efficiency;

    }*/


    public double getEfficiency( double profit, double request ) {// gets overal profit and required resource of the taskSet and returns Efficiency


        if (knapsackItems==null)
            throw new NullPointerException("Knapsack Items has not been sent to input");
        Double efficiencyDbl=0.0;
        Double knapsackItemsCapacitySum=0.0;

        for (Knapsack knapsack:knapsackItems) {
            knapsackItemsCapacitySum+=knapsack.getCapacity();// all the available capacity
        }
        efficiencyDbl= profit/ Math.sqrt(request/knapsackItemsCapacitySum);

        return efficiencyDbl;
    }


    //nil

    boolean isTaskFeasibleForEfficiencyUpdate(Task taskItem,int jTilda){
        boolean isFeasible= taskItem.getDataTypeItems()[jTilda].getSize()!=0;
        return isFeasible;
    }

    public int getArgMax(Integer[] product){
        int max = Arrays.stream(product).max(Integer::compareTo).get();
        //return max;
        for (int i=0;i<product.length;i++){
            if (product[i]==max){
                return i;
            }
        }

        return 0;
    }
    /*Helper methods for DGreedyAlgorithm*/
    public ArrayList<Task> updateTaskItemsByDataTypeNomination(int jTilda){
        for (Task taskItem:taskItems) {
            taskItem.setDataTypeNominated(false);
            if (isTaskFeasibleForNomiationUpdate(taskItem,jTilda)){
                taskItem.setDataTypeNominated(true);
            }
        }
        return taskItems;
    }
    boolean isTaskFeasibleForNomiationUpdate(Task taskItem,int jTilda){
        boolean isNominated= taskItem.getDataTypeItems()[jTilda].getSize()!=0;
        return isNominated;
    }
    /*End: Helper methods for DGreedyAlgorithm*/
    ///Greedy Algorithm 3: without sharing Data
    public  ArrayList<Integer> getArgsMax(Double[] product){
        double max = Arrays.stream(product).max(Double::compareTo).get();
        ArrayList<Integer> maxArray=new ArrayList<>();
        for (int i=0;i<product.length;i++){
            if (product[i]==max){
                maxArray.add(i);
            }
        }
        return maxArray;
    }
    ///End: Greedy Algorithm 3: without sharing Data
/// Revision for getting args max in set format
    /// Greedy Algorithm: Version 2: line 20
public  ArrayList<Integer> getArgsMax(Integer[] product){
    int max = Arrays.stream(product).max(Integer::compareTo).get();
    ArrayList<Integer> maxArray=new ArrayList<>();
    for (int i=0;i<product.length;i++){
        if (product[i]==max){
            maxArray.add(i);
        }
    }
    return maxArray;
}
    /// Greedy Algorithm: Version 2: line 18
    public ArrayList<Integer> getArgsMax(Integer[] sum, Byte[] sumPrime ){
        Integer[] product=getProductArray( sum,sumPrime);
        Logger.message("productArray(sum,sumPrime): "+ Arrays.toString(product));
        return getArgsMax(product);
    }
/// End: Revision for getting args max in set format

    public int getArgMax(Integer[] sum, Byte[] sumPrime){
        Integer[] product=getProductArray( sum,sumPrime);
        Logger.message("productArray(sum,sumPrime): "+ Arrays.toString(product));
        return getArgMax(product);

    }
    private Integer[] getProductArray(Integer[] sum,Byte[] sumPrime){
        Integer[] product=new Integer[this.dataTypeItemsCount];
        Arrays.setAll(product,current->sum[current]*sumPrime[current]);
        return product;
    }
    public int getSumPrimeSupport(){
        int sum= 0;
        for (byte item:sumPrime) {
            sum+=item;
        }

        return sum;
    }
    /// Greedy Algorithm: Version 2: line 22
    public int getSelectedDataTypeItemsSupport(ArrayList<Integer> jTildaArray){// gets an array of all the high-priority tasks
            Map<Integer,Integer> supports=new TreeMap<Integer,Integer>();

            for (Integer jTildaArrayItem:jTildaArray) {
                supports.put(jTildaArrayItem, getSelectedDataTypeItemSupport(jTildaArrayItem));// creates map of data index and data frequency for this data among all the tasks
            }
            int max= supports.values().stream().max(Integer::compareTo).get();// gets the index of data with maximum frequency

            for(Integer item:supports.keySet()){ // search among the data-freq map tp find data with highest freq
                if(supports.get(item)==max)
                    return item;
            }
            return  max;
    }
    /// Greedy Algorithm: Version 2: line 22
    public int getSelectedDataTypeItemSupport(int dataTypeItemIndex){

            DataType currentDataType= dataTypeItems[dataTypeItemIndex];
            int nonZeroElementCount=0;
            for (Task taskItem:taskItems) {
                for (DataType dataTypeItem : taskItem.getDataTypeItems()) {
                    if(  dataTypeItem.getDataTypeName().equals(currentDataType.getDataTypeName())){
                        nonZeroElementCount++;
                    }
                }
            }
        return nonZeroElementCount;
    }
    public void setSumPrimeByCandidTaskItems(){ // line 11-15 DSTA: updates s'k of the candidate tasks
        for (Task taskItem:taskItems) { // s'k =1 if we have some candidate tasks whose data item k is not assigned yet
            if (taskItem.belongsTo(candidTaskItems)){
                setSumPrime(candidDataTypeItems,taskItem);
            }
        }
    }

    public boolean isTaskItemsEmpty(){
        if(taskItems.size()==0)
            return true;
        return false;
    }
    protected boolean isTaskItemsAllCandid(){
        for (Task current:taskItems) {
            if (!current.isCandid())
                return false;
        }
        return true;
    }
    private boolean isCandidTaskItemsEmpty() {
        if(candidTaskItems.size()==0)
            return true;
        return false;
    }



    public abstract void run();

    public void setSumPrimeByZero() {
        this.sumPrime=new Byte[dataTypeItemsCount];
        Arrays.fill(this.sumPrime, (byte)0);
    }

    ///Calculate DataType Summation by getting datatype name
    private Integer setSum(String dataTypeName){
        int sum=0;
        for (Task taskItem:taskItems) {
            for (DataType dataTypeItem : taskItem.getDataTypeItems()) {
                if(  dataTypeItem.getDataTypeName().equals(dataTypeName)){
                    sum+= dataTypeItem.getSize();
//					break;
                }
            }
        }
        return sum;
    }
//starts with the candidate tasks, then loops over the corresponding data of that to see if there exist a data that has
    //non zero size and it is not assigned yet, we make s'k =1 means that we need to consider it for the efficiency comparison

    private void setSumPrime(ArrayList<DataType> candidDataTypeItems,Task  currentTaskItem){     for (int index = 0; index < dataTypeItemsCount; index++) {
            setSumPrimeByIndex(index,candidDataTypeItems,currentTaskItem);
        }
    }
    private void setSumPrimeByIndex(int index, ArrayList<DataType> candidDataTypeItems,Task  currentTaskItem){
        if (!currentTaskItem.getDataTypeItems()[index].belongsTo(candidDataTypeItems)
                && currentTaskItem.getDataTypeItems()[index].getSize()!=0)
            sumPrime[index]=(byte)1;
    }
    public Double getCandidDataTypeItemsProportionValue() {
        return candidDataTypeItemsProportionValue;
    }

    public void setCandidDataTypeItemsProportionValue() {
        ArrayList<DataType> candidDataTypeItems= getCandidDataTypeItems();

        for (Task current:getTaskItems()) {
            setDataTypeItemsDegreeByTaskItem(current);
        }
        for (Task current:getCandidTaskItems()) {
            setDataTypeItemsDegreeByTaskItem(current);
        }
//        getTaskItems().forEach(current->{
//            DataType[] currentTaskDataTypeItems= current.getDataTypeItems();
//            for (int dataTypeIndex=0; dataTypeIndex<currentTaskDataTypeItems.length;dataTypeIndex++) {
//                if (currentTaskDataTypeItems[dataTypeIndex].getSize()>0){
//                    Integer degree= getDataTypeItems()[dataTypeIndex].getDegree()+1;
//                    getDataTypeItems()[dataTypeIndex]
//                                    .setDegree(degree);
//                }
//            }
//        });
        double dataTypeItemsDegreeSum=0.0;
        double numberOfSharedCandidDataTypeItems=0.0;
        for (int dataTypeIndex = 0; dataTypeIndex <dataTypeItems.length ; dataTypeIndex++) {

            if (dataTypeItems[dataTypeIndex].getDegree()>1){
                dataTypeItemsDegreeSum+=1.0;
                if (isSharedCandidDataType(dataTypeItems[dataTypeIndex])){
                    numberOfSharedCandidDataTypeItems+=1;
                }
            }
        }

        //double candidDataTypeItemsSize= getCandidDataTypeItems().size();
        //this.candidDataTypeItemsProportionValue =  candidDataTypeItemsSize/dataTypeItemsDegreeSum;
        this.candidDataTypeItemsProportionValue =  numberOfSharedCandidDataTypeItems/dataTypeItemsDegreeSum;
    }

    private boolean isSharedCandidDataType(DataType currentDataTypeItem){
        ArrayList<DataType> candidDataTypeItems=getCandidDataTypeItems();
        for (DataType currentCandidDataTypeItem:candidDataTypeItems) {
            if(currentDataTypeItem.getDataTypeName().equals(currentCandidDataTypeItem.getDataTypeName()))
                return true;
        }
        return false;
    }

    private void setDataTypeItemsDegreeByTaskItem(Task current){
        DataType[] currentTaskDataTypeItems= current.getDataTypeItems();
        for (int dataTypeIndex=0; dataTypeIndex<currentTaskDataTypeItems.length;dataTypeIndex++) {
            if (currentTaskDataTypeItems[dataTypeIndex].getSize()>0){
                Integer degree= getDataTypeItems()[dataTypeIndex].getDegree()+1;
                getDataTypeItems()[dataTypeIndex]
                        .setDegree(degree);
            }
        }
    }
    /*
        This method is for the last iteration, when the final task is assigned to corresponding knapsack while
        its shared data is not assigned to the Candid Data Type array
    */
    public void setCandidDataTypeItemsByCheckingFinalStates() {
        for (Task taskItem:taskItems) {
            if (taskItem.belongsTo(candidTaskItems)){
                updateCandidDataTypeItemIfIsNotIncluded( candidDataTypeItems, taskItem);
            }
        }
    }
    public void updateCandidDataTypeItemIfIsNotIncluded(ArrayList<DataType> candidDataTypeItems,Task  currentTaskItem){
        for (int index = 0; index < dataTypeItemsCount; index++) {
            updateCandidDataTypeItemIfIsNotIncluded(index,candidDataTypeItems,currentTaskItem);

        }
    }
    public void updateCandidDataTypeItemIfIsNotIncluded(int index, ArrayList<DataType> candidDataTypeItems,Task  currentTaskItem){
        if (!currentTaskItem.getDataTypeItems()[index].belongsTo(candidDataTypeItems)
                && currentTaskItem.getDataTypeItems()[index].getSize()!=0)
            setCandidDataTypeItems(DataType.union(getCandidDataTypeItems(),getDataTypeItems()[index]));
    }

    @Override
    public String toString() {
        DataType[] candidDataTypeArray = new DataType[candidDataTypeItems.size()];
        candidDataTypeArray = candidDataTypeItems.toArray(candidDataTypeArray);

        return "GreedyAlgorithm [\ntaskItems=" + taskItems + ",\n requestItems=" + Arrays.toString(requestItems)
                + ",\n profitItems=" + Arrays.toString(profitItems) + ",\n taskCount=" + taskCount + ",\n taskDataTypeMatrix="
                + taskDataTypeMatrixToString() + ",\n knapsackCount=" + knapsackCount + "\n, knapsackItems="
                + knapsackItemsToString()  +
                ",\n dataTypeItems=" + Arrays.toString(dataTypeItems) +
                ",\n candidDataTypeItems=" + Arrays.toString(candidDataTypeArray) +
                ",\n dataTypeItemsSum=" + Arrays.toString(sum) +
                ",\n totalProfit="+totalProfit+
                ",\n candidDataTypeItemsProportionValue="+candidDataTypeItemsProportionValue+
                ",\n totalDataSize="+totalDataSize+
                '}';
    }

    private String taskDataTypeMatrixToString() {
        StringBuilder oStringBuilder=new StringBuilder();
        oStringBuilder.append("[");
        for(int row=0;row<taskDataTypeMatrix.length;row++)
        {
            oStringBuilder.append("[");
            for (int colVal:taskDataTypeMatrix[row]) {
                oStringBuilder.append(colVal+", ");
            }
            oStringBuilder.append("]\n");
        }
        oStringBuilder.append("]\n");
        return oStringBuilder.toString();
    }
    String knapsackItemsToString() {
        StringBuilder oStringBuilder=new StringBuilder();
        oStringBuilder.append("[");
        for (Knapsack knapsack : knapsackItems) {
            oStringBuilder.append(knapsack.toString());
            oStringBuilder.append("\n");
        }
        oStringBuilder.append("],\n");
        return oStringBuilder.toString();
    }
//nil
    Integer[] getTaskserversAssignment( ArrayList<Task> taskItems ){// returns an array of assigned server indexes for each task -> up to the num of tasks

        Integer [] servers= new Integer[taskItems.size()];
        for (int i=0; i<taskItems.size(); i++ )
          servers[i]=taskItems.get(i).assignedServer;

        return servers;

    }


    String[] getserversSetAssignment( ArrayList<Task> taskSet ){// for phase 2, after local search returns an array of assigned task indexes for each server-> up to the num of servers

        String [] servers= new String[knapsackItems.length];
        Arrays.fill(servers, "");

        for (int i=0; i< taskSet.size();i++ )
            if(taskSet.get(i).assignedServer!=-1){ // gets the set

                List<Integer> tempTasks= taskSet.get(i).subsetTask;// gets tasks of each set

               String tasksInsideSet = tempTasks.toString(); // convert it to string

                int index =taskSet.get(i).assignedServer;// gets assigned server for whole set

                    servers[index] = tasksInsideSet;

            }

        int k=0;
        while(k<knapsackItems.length) {
            if (servers[k] == "")
                servers[k] = "null";
            k++;
        }
        return servers;

    }



    String[] getserversAssignment( ArrayList<Task> taskItems ){// for phase 1 returns an array of assigned task indexes for each server-> up to the num of servers

        String [] servers= new String[knapsackItems.length];
        Arrays.fill(servers, "");

       // List<String> t = new ArrayList<>();

        HashMap<Integer, List<Integer>> taskMap= TaskSetMap();


        for (int i=0; i< taskMap.size();i++ )
          //
            {
                List<Integer> tmp= taskMap.get(i);// gets task list of each set
                int firstTaskIndx=tmp.get(0); // we know that server assigned to all tasks of this set are equal
                int assignedServer= taskItems.get(firstTaskIndx).assignedServer;
                if(assignedServer!=-1)
                {
                    String taksList= tmp.toString();
                    servers[assignedServer]+= taksList;
                }


            }

        int k=0;
        while(k<servers.length) {
            if (servers[k] == "")
                servers[k] = "null";
            k++;
        }
        return servers;

    }


    Integer getThroughput( ArrayList<Task> taskItems){ // for DSTA
        int thr=0;
        for(Task task : taskItems)
        {
           if(task.assignedServer!=-1)
              thr++;
        }
        return thr;

    }


    Integer getSetThroughput( ArrayList<Task> taskSet){ // for set - local Search count of tasks
        int thr=0;

        for(Task set : taskSet) // for set
        {
            if(set.assignedServer!=-1)
                thr+= set.subsetTask.size();//  means that all the tasks in each set are also assigned

        }
        return thr;

    }


    double getMaxProfit(){// max profit totally if we assign all the tasks

        double maxProf=0;
        for (double prof: profitItems)
            maxProf+=prof;

        maxProf=Math.round(maxProf*1000);
        double res= (double)maxProf/1000;
        return res;
    }



}
