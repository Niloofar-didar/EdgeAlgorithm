This project is an extension of the 'Data Sharing-Aware Task Allocation in Edge Computing Systems' paper. The initial problem involves allocating servers to various tasks/users that require offloading shared data to the servers for high performance and low latency computation. The new algorithm, called DSTAR, leverages a heuristic reallocation method and utilizes the provided algorithm (DSTA) in the above paper to increase the number of allocated tasks and maximize profit while minimizing data traffic over the network.

Here, you can observe an illustrative example comparing DSTA and DSTAR. In this example, 'c' denotes the server capacity, 'D1-D3' represent different data types that tasks may require, and 'T1-T4' are available tasks requiring edge servers for computation. While DSTA allocates 3 tasks in this scenario, DSTAR succeeds in improving task allocation to 4 tasks.


![DSTAR-examp](https://github.com/Niloofar-didar/EdgeAlgorithm/assets/27611369/c868564e-132c-4537-a4be-1c2cfb4b8948)
