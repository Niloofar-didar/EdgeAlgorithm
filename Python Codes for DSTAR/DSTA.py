# -*- coding: utf-8 -*-
"""
Simulator for DSTA

Created on Tue Dec  7 16:59:15 2021

@author: gx7594
"""

from sklearn.preprocessing import PolynomialFeatures
from sklearn.linear_model import LinearRegression
import pandas as pd
import numpy as np
import random
import matplotlib.pyplot as plt
import csv
import statistics
import math 
from sklearn.datasets import load_boston
from sklearn.linear_model import LinearRegression
from sklearn.metrics import mean_squared_error, r2_score
from matplotlib import pyplot as plt
from matplotlib import pyplot as plt2
import os.path
from os import path
from matplotlib import rcParams

datasize=5
profit= [60,50,40,20,30,10] # to the number of tasks
resources=[30,40,20,10,40,15]
tasks_count=len(profit)
servers=[45,40,35,30]
total_cap= sum(servers)
efficiency=[]
assignment= list(np.zeros(tasks_count))

A= [[30, 20, 15, 0, 0],
 [30, 0, 15, 10, 0],
 [30, 0, 15, 0, 0],
 [0, 20, 0, 0, 0],
 [0, 20, 0, 0, 5],
  [0, 0, 15, 0, 0]]


k2=len(A[0]) # j
k1=len(A) # i index
s=np.zeros(k2)
efficiency=list(np.zeros(k1))

j=0
while(j<k2):
 for i in range (0, k1):
    s[j]+= (A[i][j])
 j+=1   

'''in this example, s is sorted, servers are sorted too'''

s=list(s)



#while 0 in assignment:
while    0 in assignment:
 elm_index= s.index( max(s))
 s[elm_index]=0
 for i in range (0, tasks_count):

  efficiency[i]=0
  if(A[i][elm_index]!=0 and int(assignment[i])==0 ): 
    '''  # check if those tasks with data[elm_index] and also check if those tasks are not assigned to a server '''
    efficiency[i]=( profit[i]/ (math.sqrt(resources[i]/total_cap) ))
    

 eff,rsc, prf= zip(*sorted(zip(efficiency,resources,profit), reverse=True))      

 for i in range (0,tasks_count):
  if(eff[i]>0):  
    real_index= efficiency.index(eff[i])  # find the actual index in not sorted array 
    for j in range (0, len(servers)):
        if (servers[j]-rsc[i]>0):
          servers[j]=servers[j]-rsc[i]
          assignment[real_index]=j+1
          break
    if( assignment[real_index]==0): #couldn't assign any server to it  
         assignment[real_index]=-1
         
  else: # don't check the rest of cells since we have sorted the eff array
       break
   
    
'''calculate profit'''
total_profit=0
throughput=0
for i in range (0,tasks_count):
    if (assignment[i]!= -1):
        total_profit+=profit[i]
        throughput+=1
        
throughput/=tasks_count        