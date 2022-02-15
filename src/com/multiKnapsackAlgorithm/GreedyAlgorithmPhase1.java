package com.multiKnapsackAlgorithm;

import com.multiKnapsackAlgorithm.hm.Logger;

import java.util.*;
import java.util.stream.IntStream;

public class GreedyAlgorithmPhase1 extends GreedyAlgorithmBase{
	public GreedyAlgorithmPhase1(String inputFilePath){
		super(inputFilePath);
	}
	public GreedyAlgorithmPhase1(Integer[][] taskDataTypeMatrix, Double[] requestItems, Double[] profitItems) {
		super(taskDataTypeMatrix, requestItems, profitItems);
	}

	public GreedyAlgorithmPhase1(Integer[][] taskDataTypeMatrix, Double[] requestItems, Double[] profitItems, Knapsack[] knapsackItems) {
		super(taskDataTypeMatrix, requestItems, profitItems, knapsackItems);
	}

	@Override
	public void run() {
		//initialization
		// sort the servers based on non-increasing capacity order
		Arrays.sort(getKnapsackItems(), Collections.reverseOrder());
		setCandidTaskItems(new ArrayList<Task>()); // array for candidate tasks
		setCandidDataTypeItems(new ArrayList<DataType>());// // array for candidate data
		setTotalProfit(0.0);
		setTotalDataSize(0);
		Logger.message("MultiKnapsack Algorithm -> Run -> Set IsUsed Property: Begin" );
		for (Knapsack knapsackItem : getKnapsackItems()) {
			knapsackItem.setUsed(false);
			Logger.message( knapsackItem.toString());
		}
		Logger.message("MultiKnapsack Algorithm -> Run -> Set IsUsed Property: End" );

		Logger.message("MultiKnapsack Algorithm -> Run -> Set Sum Property: Initiative: Begin" );
		Integer[] sum=getSum();
		Arrays.fill(sum,0);
		setSum(sum);
		Logger.message(Arrays.toString(getSum()));
		Logger.message("MultiKnapsack Algorithm -> Run -> Set Sum Property: Initiative: End" );

		Logger.message("MultiKnapsack Algorithm -> Run -> Set Sum Property: Begin" );
		setSum(); // line 8 DSTA -> Sk : loops over all data types and tasks to calculate the total amount of data item k needed by the tasks
		Logger.message(Arrays.toString(getSum()));
		Logger.message("MultiKnapsack Algorithm -> Run -> Set Sum Property: End" );

		//!isTaskItemsEmpty()
		while ( !isTaskItemsAllCandid()){ //if at least one task is not assigned  line 9 algorithm
			Logger.message("MultiKnapsack Algorithm -> Run ->\tWhile Loop ->Set SumPrime Property: Initiative: Begin" );
			setSumPrimeByZero();
			Byte[] sumPrime=getSumPrime();
			Logger.message(Arrays.toString(sumPrime));
			Logger.message("MultiKnapsack Algorithm -> Run ->\tWhile Loop ->Set SumPrime Property: Initiative: End" );

//			Logger.message("MultiKnapsack Algorithm -> Run ->\tWhile Loop ->Adding Mock candidTaskItems: Begin" );
//			candidTaskItems.add(taskItems.get(0));
//			candidTaskItems.add(taskItems.get(3));
//			Logger.message(Arrays.deepToString(candidTaskItems.toArray()));
//			Logger.message("MultiKnapsack Algorithm -> Run ->\tWhile Loop ->Adding Mock candidTaskItems: End" );

			Logger.message("MultiKnapsack Algorithm -> Run ->\tWhile Loop ->Set SumPrime By CandidTaskItems: Begin" );
			setSumPrimeByCandidTaskItems(); // updates s'k of data that belongs to the candidate tasks and their size is nonzero and are not assigned yet
			Logger.message("SumPrim: "+ Arrays.toString(getSumPrime()));
			Logger.message("MultiKnapsack Algorithm -> Run ->\tWhile Loop ->Set SumPrime By CandidTaskItems: End" );

			int jTilda=0;
			Logger.message("MultiKnapsack Algorithm -> Run ->\tWhile Loop ->Set SumPrimeSupport: Begin" );
			int sumPrimeSupport=getSumPrimeSupport(); //sum of (set of all indices corresponding to nonzero entries in s')
			// if sumPrimeSupport!=0 , then we have still data needed to be assigned

			Logger.message("Sum Prime Support: "+sumPrimeSupport);
			Logger.message("MultiKnapsack Algorithm -> Run ->\tWhile Loop ->Set SumPrimeSupport: End" );

			Logger.message("MultiKnapsack Algorithm -> Run ->\tWhile Loop ->Set jTilda: Begin" );
/*
JTilda first implementation without considering that for calculating
jTilda, we need an array first
* */
//			if(sumPrimeSupport>0){
//
//				jTilda=getArgMax(getSum(),getSumPrime());
//			}else{
//				jTilda=getArgMax(getSum());
//			}
			ArrayList<Integer> jTildaArray=new ArrayList<>();
			if (sumPrimeSupport > 0) {
				jTildaArray=getArgsMax(getSum(),getSumPrime());// gets the index of data with largest shared data
			} else {
				jTildaArray=getArgsMax(getSum());// finds data index with maximum s value-> data with highest priority
			}
			// Implement JTilda Array Support
			if (jTildaArray.size()>1){
				jTilda= getSelectedDataTypeItemsSupport(jTildaArray);// gets the
			}
			else{
				jTilda=jTildaArray.get(0);
			}

			Logger.message("jTilda: "+jTilda);
			Logger.message("MultiKnapsack Algorithm -> Run ->\tWhile Loop ->Set jTilda: End" );

			Logger.message("MultiKnapsack Algorithm -> Run ->\tupdate TaskItems Efficiency: Begin");
			ArrayList<Task> updatedTaskItems= updateTaskItemsEfficiency(jTilda);
			Logger.message(Arrays.deepToString(updatedTaskItems.toArray()));
			Logger.message("MultiKnapsack Algorithm -> Run ->\tupdate TaskItems Efficiency: End");


			Logger.message("MultiKnapsack Algorithm -> Run ->\tsorting tasks in non-decreasing order" +
					" by efficiency: Begin");
			ArrayList<Task> nonCandidTasks=new ArrayList<Task>() ;
			//adds non-candidate tasks to nonCandidTasks list
			for (Task currentTask:getTaskItems()) {
				if(!currentTask.isCandid()){
					nonCandidTasks.add(currentTask);
				}
			}
			Task[] sortedTaskItems=new Task[nonCandidTasks.size()];
			sortedTaskItems= nonCandidTasks.toArray(sortedTaskItems) ;

			Arrays.sort(sortedTaskItems, Collections.reverseOrder());
			Logger.message(Arrays.deepToString( sortedTaskItems));
			Logger.message("MultiKnapsack Algorithm -> Run ->\tsorting tasks in non-decreasing order" +
					" by efficiency: End");

			Logger.message("MultiKnapsack Algorithm -> Run ->\tknapsack task assignment: Begin");




			//line 26 alg
			for (Task sortedTaskItem:sortedTaskItems) {
				if(sortedTaskItem.getEfficiency()>0){
					//The flag is for double checking purpose
					boolean flag=true;
					for (Knapsack knapsackItem:getKnapsackItems()) {//loop over servers to find the first server that can process the task (if is not assigned before and capacity is OK)

						//First check if task efficiency is still non-zero
						//After assigning taskItem to any knapsack set its efficiency to zero(To not let it be added again)
						// Greedy Algorithm: Version 1 -> line 34
						if(knapsackItem.isTaskFeasibleToAssign(sortedTaskItem)	) // capacity is OK and task is not candidate
						{//this.capacity-sortedTaskItem.getRequest()>=0

							setCandidTaskItems (Task.union(getCandidTaskItems(),sortedTaskItem));// add the task to the candidate list

							knapsackItem.setCapacity(knapsackItem.getCapacity()-sortedTaskItem.getRequest());// update server's cap
							sortedTaskItem.setServer(knapsackItem.index);

							for(int index: sortedTaskItem.getReqDataList())// adds all the required data of the assigned task to the assigned server
						     	knapsackItem.indexOfData.add(index);

							knapsackItem.addTaskItem(sortedTaskItem);// we assigned the task to the server, now it's time to also add its data to the corresponding set




							sortedTaskItem.setEfficiency(0.0);
							flag=false;
							setTotalProfit(getTotalProfit()+sortedTaskItem.getProfit());

							knapsackItem.setUsed(true);

						}

						if (!flag)
						break;
					}
					sortedTaskItem.setCandid(true);
//						setTaskItems(Task.subtract(getTaskItems(),sortedTaskItem));
					for (Task currentTask:getTaskItems()) { //update the candidacy of the task in main array of taskItems
						if( currentTask.getIndex()== sortedTaskItem.getIndex() ){
							currentTask.setCandid(true);
						}
					}


				}
			}
			//count of servers issue 19
//			int candidDataTypeItemCounter=0;
//			for (Knapsack knapsackItem : getKnapsackItems()) {
//				if(knapsackItem.indexOfData.contains(jTilda))
//				   candidDataTypeItemCounter++;
//			}


			Logger.message("MultiKnapsack Algorithm -> Run ->\tknapsack task assignment: End");
			// stores assigned data type information
			setCandidDataTypeItems(DataType.union(getCandidDataTypeItems(),getDataTypeItems()[jTilda]));
			//setTotalDataSize(getTotalDataSize() + getDataTypeItems()[jTilda].getSize()*candidDataTypeItemCounter);// this is wrong, just gets back dataType row one
			sum[jTilda]=0;
			setSum(sum);
			for (Knapsack currentKnapsackItem : getKnapsackItems()) {
				currentKnapsackItem.setUsed(false);/// ????
			}
		}


//********************************///////////
// //This is to calculate datasharing for DSTA

		Set<Integer> dataUSedSet =  new HashSet<>();;
		for( Knapsack server: getKnapsackItems()){
			for(Integer dataIndex:server.indexOfData)
					setTotalDataSize( getTotalDataSize() + getDataSize()[dataIndex]);
		}




		// records data used after DSTA

		setCandidDataTypeItemsByCheckingFinalStates();
		ArrayList<DataType>  finalCandidDataTypeItems= getCandidDataTypeItems();
        setCandidDataTypeItemsProportionValue();

		// starting local search algorithm -> best fit, second round
		HashMap<Integer, List<Integer>> setOfTasks= TaskSetMap();// returns a map of un assigned and assigned tasks after DSTA

		// here we need to reset server's capacity for the new assignment and efficiency function calculation
		setKnapsackItems(serverInf,",");// reset servers
		Arrays.sort(getKnapsackItems()); //sort servers in increasing order for best fit local search
		setTotalProfitset(0.0);// reset total profit of set
		setTotalDataSizeSet(0);// reset datasieze of set
		initializeSetItems(setOfTasks);//  creates a list of set , assigns the profit, request, and efficiency




		Task[] sortedSetItems=new Task[getSetItems().size()];
		sortedSetItems= getSetItems().toArray(sortedSetItems) ;
		Arrays.sort(sortedSetItems, Collections.reverseOrder());// sorts array of set based on the efficiency parameter
        //System.out.println(sortedSetItems[0]);

		for (Task sortedsetItem:sortedSetItems) {// start to assign the task set to the servers

			if(sortedsetItem.getEfficiency()>0){
				//The flag is for double checking purpose
				boolean flag=true;
				for (Knapsack knapsackItem:getKnapsackItems()) {//loop over servers to find the first server that can process the task (if is not assigned before and capacity is OK)

						if(knapsackItem.isTaskFeasibleToAssignCapacityWise(sortedsetItem)	) // capacity is OK for the assignment
					{

						knapsackItem.setCapacity(knapsackItem.getCapacity()-sortedsetItem.getRequest());// update server's cap
						sortedsetItem.setServer(knapsackItem.index);

						for(int index: sortedsetItem.getReqDataList())// adds all the required data of the assigned task to the assigned server
							knapsackItem.indexOfData.add(index);


						flag=false;
						setTotalProfitset(getTotalProfitset()+sortedsetItem.getProfit());
						knapsackItem.setUsed(true);

					}

					if (!flag)
						break;
				}
				sortedsetItem.setCandid(true);

			}
		}

		//********************************///////////
// //This is to calculate datasharing for local search

	//	Set<Integer> dataUSedSetlocals =  new HashSet<>();;
		for( Knapsack server: getKnapsackItems()){
			for(Integer dataIndex:server.indexOfData)
				setTotalDataSizeSet( getTotalDataSizeSet() + getDataSize()[dataIndex]);// totalprofit for all sets
		}


		String [] servers= getserversSetAssignment(getSetItems());
		Logger.message( "end of local search");
	}



}
