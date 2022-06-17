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


		//int numberOfInstances=10;
		int numberOfInstances=5;
		// folders # to execute
		//	/* nil
		String[] inputFilePaths=new String[]{	"1.LowDemand-LowSharing.txt"

				,"2.LowDemand-AverageSharing.txt","3.LowDemand-HighSharing.txt",
				"4.AverageDemand-LowSharing.txt","5.AverageDemand-AverageSharing.txt","6.AverageDemand-HighSharing.txt",
				"7.HighDemand-LowSharing.txt", "8.HighDemand-AverageSharing.txt","9.HighDemand-HighSharing.txt"};


		String inputFilePath="2.LowDemand-AverageSharing.txt";
		TestCaseRunner oTestCaseRunner=new TestCaseRunner(instanceDirectoryName,numberOfInstances,inputFilePaths);


		oTestCaseRunner.executeAutomation();



	}
	public static GreedyAlgorithmBase[] getGreedyAlgorithmSampleArray(){
		Integer[][] taskDataTypeMatrix = {};
		Double[] requestItems= {};
		Double[] profitItems= {};
		Knapsack[] knapsackItems;
		knapsackItems=new Knapsack[] {};

		return new GreedyAlgorithmBase[] {
				new GreedyAlgorithms(taskDataTypeMatrix,requestItems,profitItems, knapsackItems),

		};
	}

}