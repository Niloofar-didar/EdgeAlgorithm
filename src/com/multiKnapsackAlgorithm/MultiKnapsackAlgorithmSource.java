package com.multiKnapsackAlgorithm;

import com.multiKnapsackAlgorithm.PGreedy.NaivePGreedyAlgorithm;
import com.multiKnapsackAlgorithm.PGreedy.PGreedyAlgorithm;

import java.time.Duration;
import java.time.Instant;

public class MultiKnapsackAlgorithmSource {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String instanceDirectoryName="instance";

	//	String[] inputFilePaths=new String[]{"testCase00.txt"};
		//nil cmn
		//TestCaseRunner oTestCaseRunner=new TestCaseRunner("init",1,inputFilePaths);


		// nil
		//int numberOfInstances=10;
		int numberOfInstances=3;
		// folders # to execute
			//	/* nil
		String[] inputFilePaths=new String[]{	"1.LowDemand-LowSharing.txt"

				,"2.LowDemand-AverageSharing.txt","3.LowDemand-HighSharing.txt",
				"4.AverageDemand-LowSharing.txt","5.AverageDemand-AverageSharing.txt","6.AverageDemand-HighSharing.txt",
				"7.HighDemand-LowSharing.txt", "8.HighDemand-AverageSharing.txt","9.HighDemand-HighSharing.txt"};


			String inputFilePath="2.LowDemand-AverageSharing.txt";
      TestCaseRunner oTestCaseRunner=new TestCaseRunner(instanceDirectoryName,numberOfInstances,inputFilePaths);

			 //*/



		oTestCaseRunner.executeAutomation();













			//oTestCaseRunner.runAlgorithms("data\\"+instanceDirectoryName+"1\\"+ inputFilePaths[0]);

			//oTestCaseRunner.runTestCases(instanceDirectoryName,0,inputFilePaths);
			//oTestCaseRunner.executeForAllInstances(instanceDirectoryName,numberOfInstances, inputFilePaths);

		/*GreedyAlgorithmBase[] oGreedyAlgorithmInstances={
				new GreedyAlgorithmPhase1(inputFilePath),
				new DGreedyAlgorithm(inputFilePath),
				new PGreedyAlgorithm(inputFilePath),
				//new NaivePGreedyAlgorithm(inputFilePath),
				//new GreedyAlgorithmPhase2(inputFilePath),
				//new ExhaustiveSearch(inputFilePath);
		};


		for (GreedyAlgorithmBase oGreedyAlgorithmItem:oGreedyAlgorithmInstances) {
			System.out.println("----------------------------------------------------------");
			System.out.println(oGreedyAlgorithmItem.toString());
			System.out.println("Before Run");
			Instant runStart=Instant.now();
			oGreedyAlgorithmItem.run();
			Instant runEnd=Instant.now();
			Duration runTimeElpased=Duration.between(runStart,runEnd) ;
			System.out.println("Run: TimeElapsed: "+runTimeElpased.toMillis());
			System.out.println("After Run");
			System.out.println(oGreedyAlgorithmItem.toString());
			System.out.println("----------------------------------------------------------");
			System.out.println();
			System.out.println();
		}*/
	}
	public static GreedyAlgorithmBase[] getGreedyAlgorithmSampleArray(){
		Integer[][] taskDataTypeMatrix = {};
		Double[] requestItems= {};
		Double[] profitItems= {};
		Knapsack[] knapsackItems;
		knapsackItems=new Knapsack[] {};

		return new GreedyAlgorithmBase[] {
				new GreedyAlgorithmPhase1(taskDataTypeMatrix,requestItems,profitItems, knapsackItems),
				new GreedyAlgorithmPhase2(taskDataTypeMatrix,requestItems,profitItems, knapsackItems),
		};
	}

}
