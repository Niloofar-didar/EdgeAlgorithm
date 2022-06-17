# -*- coding: utf-8 -*-
"""

4/12/2022 update 
Edge project

This is to generate all the graphs for DSTAR paper

To read from result.csv and return three graphs of profit, datasize and throughput 

In the second stage
generates the graphs for mean of 5 same profit instances of (XDemanXSharing)
- mean DataSize
 

Created on Mon Mar 28 21:28:06 2022

@author: Niloofar Didar
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




inform= pd.read_csv('result.csv')
profit1=inform['TotalProfit'].values.reshape(-1,1)[:,0] # shuld be multiplied by 0.2
throughput1=(inform['Throughput'].values.reshape(-1,1)[:,0])
dSize1=inform['TotalDataSize'].values.reshape(-1,1)[:,0] # shuld be multiplied by 0.2
profit2=inform['LSTotalProfit'].values.reshape(-1,1)[:,0] # shuld be multiplied by 0.2
throughput2=(inform['LSThroughput'].values.reshape(-1,1)[:,0])
dSize2=inform['LSTotalDataSize'].values.reshape(-1,1)[:,0] # shuld be multiplied by 0.2
maxProfit=inform['MaxProfit'].values.reshape(-1,1)[:,0] # shuld be multiplied by 0.2
exTime=inform['ExecutionTime'].values.reshape(-1,1)[:,0] # shuld be multiplied by 0.2
exTimeR=inform['ExecutionTimeDSTAR'].values.reshape(-1,1)[:,0] # shuld be multiplied by 0.2


#minDataSize_DSTA=inform['minDataSize_DSTA'].values.reshape(-1,1)[:,0] # shuld be multiplied by 0.2
#minDataSize_LS=inform['minDataSize_LS'].values.reshape(-1,1)[:,0] # shuld be multiplied by 0.2

profitChg = [0] * len(profit2)
dataChg= [0] * len(profit2)
thrChg= [0] * len(profit2)


i=0
while i< len(profit2)-1:
    
  profitChg[i]=((profit2[i]-profit1[i])/profit1[i]) # inc in DSTAR in relative to DSTA
  thrChg[i]=((throughput2[i]-throughput1[i])*100/throughput1[i])#DSTAR in relative to DSTA
  dataChg[i]=((dSize2[i]-dSize1[i])/dSize1[i])
  i+=1
  profitChg[i]=((profit2[i-1]-profit1[i])/profit1[i]) # DSTAR  in relative to Pgreedy
  thrChg[i]=((throughput2[i-1]-throughput1[i])*100/throughput1[i])
  dataChg[i]=((dSize2[i-1]-dSize1[i])/dSize1[i])
  i+=1

'''
x= list(range(1,len(profit1)+1))
fig, ax = plt.subplots() 

plt.plot(x ,throughput1 , label="DSTA" )
plt.plot(x ,throughput2 , label="DSTA with LocalSearch" )
ax.legend(loc="best", ncol=1,columnspacing=0.8,handletextpad=0.31)#,  ) 
#ax.set_xscale('log')
plt.ylim(20,124)
plt.xlabel('Instance',  )  
plt.ylabel('Throughput',  ) 
plt.tight_layout()
fig.savefig( "Instance_Throughput.pdf" )


fig, ax = plt.subplots() 
plt.plot(x ,profit1 , label="DSTA" )
plt.plot(x ,profit2 , label="DSTA with LocalSearch" )
ax.legend(loc="best", ncol=1,columnspacing=0.8,handletextpad=0.31)#,  ) 
#ax.set_xscale('log')
plt.ylim(min(profit1),1.4*max(profit2))
plt.xlabel('Instance',  )  
plt.ylabel('Profit',  ) 
plt.tight_layout()
fig.savefig( "Instance_Profit.pdf" )


fig, ax = plt.subplots() 

plt.plot(x ,dSize1 , label="DSTA" )
plt.plot(x ,dSize2 , label="DSTA with LocalSearch" )
ax.legend(loc="best", ncol=1,columnspacing=0.8,handletextpad=0.31)#,  ) 
#ax.set_xscale('log')
plt.ylim(min(dSize1),1.4*max(dSize2))
plt.xlabel('Instance',  )  
plt.ylabel('DataSize',  ) 
plt.tight_layout()
fig.savefig( "Instance_DataSize.pdf" )

fig2, ax2 = plt.subplots()
fig3, ax3 = plt.subplots()
fig4, ax4 = plt.subplots()
widt=0.7
for j in range (1, len(profit1)+1):
    
  i=j-1
 
  model_std=np.std(profitChg[i])
    
  ax4.bar(i,profitChg[i] ,yerr=model_std, hatch = '/',width=widt,edgecolor = "black", align='center',alpha=1,color='darkgray',ecolor='red', capsize=10)
  ax3.bar(i,thrChg[i] ,yerr=model_std, hatch = '/',width=widt,edgecolor = "black", align='center',alpha=1,color='darkgray',ecolor='red', capsize=10)
  ax2.bar(i,dataChg[i] ,yerr=model_std, hatch = '/',width=widt,edgecolor = "black", align='center',alpha=1,color='darkgray',ecolor='red', capsize=10)
   
    
    



maxPerc=40
title = list(range(0, len(profit1)))
 
ax2.set_xticks(title[0:len(title)]) # define points up to the length of names we added
ax2.set_xticklabels(x,  rotation=40 )
ax2.set_ylabel('DataSize Change')
ax2.set_ylim(min(dataChg),maxPerc)
ax2.yaxis.grid(True)
ax2.legend(loc='best',handletextpad=0.31 ,ncol=2,columnspacing=0.99,)
fig2.savefig("dataChange.pdf", dpi=300, bbox_inches = 'tight')

ax3.set_xticks(title[0:len(title)]) # define points up to the length of names we added
ax3.set_xticklabels(x,  rotation=40 )
ax3.set_ylabel('Throughput Change')
ax3.set_ylim(min(thrChg),maxPerc)
ax3.yaxis.grid(True)
ax3.legend(loc='best',handletextpad=0.31 ,ncol=2,columnspacing=0.99,)
fig3.savefig("throughputChange.pdf", dpi=300, bbox_inches = 'tight')


ax4.set_xticks(title[0:len(title)]) # define points up to the length of names we added
ax4.set_xticklabels(x,  rotation=40 )
ax4.set_ylabel('Profit Change')
ax4.set_ylim(min(profitChg),maxPerc)
ax4.yaxis.grid(True)
ax4.legend(loc='best',handletextpad=0.31 ,ncol=2,columnspacing=0.99,)
fig4.savefig("profitChange.pdf", dpi=300, bbox_inches = 'tight')
'''

averageProfDSTA=[] # for DSTA
averageProfDSTAR=[] # 5 instances per 5*9 = 45 row of data 
averageProfpg=[]

averageDratioR=[] # for DSTAR/DSTA
averageDratioG=[]
averageDratioAG=[]
# for PGreedy/DSTA
averageMaxpf=[]
averageThchangeR=[]
averageThchangeG=[]
averagetA=[]
averagetR=[] #DsTAR
averagetG=[]
'''-> we have 9 different Deman-Sharingto calculate mean of 5 data points for each
First DSTA : profit1
Next Local SEarch : profit 2
'''





plt.rcParams['font.size'] = '18'
plt.rcParams["font.family"] = "Times New Roman"
plt.rcParams["axes.labelweight"] = "bold"
plt.rcParams['legend.fontsize']=16 # using a size in points

stdpfA=[]
stdpfR=[]
stdpfG=[]
stdSizeR=[]
stdSizeG=[]
stdthR=[]
stdthG=[]
stdTA=[]
stdTR=[]
stdTG=[]

for j in range (0,9):
   
  j= j*2 # since the indexes for DSTA are even in the excel file  
  profit11=[]
  profit22=[]  
  profitpg=[] 
  thchRangeR=[]
  thchRangeG=[]
  timeRangeA=[]
  timeRangeR=[]
  timeRangeG=[]
  
  datasizeRatioRangeR=[]
  datasizeRatioRangeG=[]
  sumthR=0
  sumthG=0
  k=0
  summpf1=0
  summpf2=0
  summpfpg=0
  sumdt1=0
  sumdt2=0 
  sumdt3=0
  summaxpf=0
  sumMinDSTA=0
  sumMinLS=0
  sumtA=0
  sumtR=0
  sumtG=0
  
  for i in range (0,5):
      
    summpf1+=profit1[j+k]/maxProfit[j+k] # first cal normalized and then take the sum and finally calculate the average
    profit11.append(profit1[j+k]/maxProfit[j+k])
    summpf2+=profit2[j+k]/maxProfit[j+k] # DSTAR over maxprof
    profit22.append(profit2[j+k]/maxProfit[j+k])
    summpfpg+=profit1[1+j+k]/maxProfit[j+k] # PGreedy over maxprof
    profitpg.append(profit1[1+j+k]/maxProfit[j+k])
    
    '''for time'''
    sumtR+=(exTimeR[j+k]+exTime[j+k])
    sumtA+=exTime[j+k]
    sumtG+=exTime[1+j+k]
    timeRangeA.append(exTime[j+k])
    timeRangeR.append(exTime[j+k]+exTimeR[j+k]) 
    timeRangeG.append(exTime[1+j+k])   
    
    '''for data size:'''
    sumdt1+=(dSize2[j+k]/dSize1[j+k]) # take the ratio between DSTAR and DSTA
    datasizeRatioRangeR.append(dSize2[j+k]/dSize1[j+k])
    
    sumdt2+=(  dSize2[j+k]/dSize1[1+j+k]) # take the ratio between DSTAR and PGreedy
    datasizeRatioRangeG.append(dSize2[j+k]/dSize1[1+j+k])
    
    sumdt3+=( dSize1[j+k]/dSize1[1+j+k]) # take the ratio between DSTAR and PGreedy


    thchRangeR.append(thrChg[j+k])
    sumthR+=thrChg[j+k]
    
    thchRangeG.append(thrChg[1+j+k])
    sumthG+=thrChg[1+j+k]
    
    k+=18
    
  averageProfDSTA.append(summpf1/5) # keeps data of 9 different Deman-Sharingto  
  averageProfDSTAR.append(summpf2/5) # this is the average of already normalized data points
  averageProfpg.append(summpfpg/5)
  
  averageThchangeR.append(sumthR/5) # change from DSTA to DSTAR
  averageThchangeG.append(sumthG/5)# change from DSTA to PGreedy
    
  averageDratioR.append(sumdt1/5) # DSTA in relative to DSTA
  averageDratioG.append(sumdt2/5) # PG in relative to DSTA
  averageDratioAG.append(sumdt3/5) # DSTA to PG
  
  averagetR.append(sumtR/5)
  averagetG.append(sumtG/5)
  averagetA.append(sumtA/5)

  
  stdpfA.append(profit11)  # range of normalized profits
  stdpfR.append(profit22)
  stdpfG.append(profitpg)
  stdSizeR.append(datasizeRatioRangeR)
  stdthR.append(thchRangeR)
  stdSizeG.append(datasizeRatioRangeG)
  stdthG.append(thchRangeG)
  stdTA.append(timeRangeA)
  stdTR.append(timeRangeR)
  stdTG.append(timeRangeG)
  
caseName=['LowDemand-LowSharing','LowDemand-MedSharing','LowDemand-HighSharing','MedDemand-LowSharing'
         , 'MedDemand-MedSharing','MedDemand-HighSharing','HighDemand-LowSharing',
          'HighDemand-MedSharing','HighDemand-HighSharing']



#X_axis = np.arange(len(averageDratioR))    
X_axis=np.arange(1,len(averageDratioR)*4, 3.9)
X_axis2=np.arange(1,len(averageDratioR)*3, 3)
j=1
fig, ax = plt.subplots()
fig2, ax2 = plt.subplots()
fig3, ax3= plt.subplots()
fig4, ax4= plt.subplots()
labels = ['LDLS', 'LDMS', 'LDHS', 'MDLS', 'MDMS','MDHS', 'HDLS', 'HDMS','HDHS']
for i in range (0, len(averageDratioR)) :

  widt=0.7
  stdA=np.std(stdpfA[i])
  stdR=np.std(stdpfR[i])
  stdG=np.std(stdpfG[i])
  #/averageMaxpf[i]
  cps=5
  #x = np.arange(len(averageDratio))  # the label locations
  width = 1
  if (j==1):
    ax.bar( X_axis[i]-width,averageProfDSTAR[i] ,yerr=stdR, label='DSTAR', hatch = '',width=width,edgecolor = "black", align='center',alpha=1,color='gray',ecolor='red', capsize=cps)
    ax.bar(X_axis[i],averageProfDSTA[i] ,yerr=stdA,label='DSTA'
       ,hatch = '',width=width,edgecolor = "black", align='center',alpha=1,color='darkgray',ecolor='red', capsize=cps)
    ax.bar(X_axis[i]+width,averageProfpg[i] ,yerr=stdG, label='P-Greedy',hatch = '',width=width,edgecolor = "black", align='center',alpha=1,color='lightgray',ecolor='red', capsize=cps)
   
    ax2.bar(X_axis2[i]-width/2,averageDratioR[i] ,yerr=np.std(stdSizeR[i]),label='DSTAR/DSTA', hatch = '',width=width,edgecolor = "black", align='center',alpha=1,color='gray',ecolor='red', capsize=cps)
    ax2.bar(X_axis2[i]+width/2,averageDratioG[i] ,yerr=np.std(stdSizeG[i]),label="DSTAR/P-Greedy",hatch = '',width=width,edgecolor = "black", align='center',alpha=1,color='darkgray',ecolor='red', capsize=cps)
    
    ax3.bar(X_axis2[i]-width/2,averageThchangeR[i] ,yerr=np.std(stdthR[i]),label="DSTAR- DSTA",width=width,edgecolor = "black", align='center',alpha=1,color='gray',ecolor='red', capsize=cps)
    ax3.bar(X_axis2[i]+width/2,averageThchangeG[i] ,yerr=np.std(stdthG[i]),label="DSTAR- P-Greedy ",width=width,edgecolor = "black", align='center',alpha=1,color='darkgray',ecolor='red', capsize=cps)
 
    ax4.bar(X_axis[i]-width,averagetR[i] ,yerr=np.std(stdTR[i]),label="DSTAR",width=width,edgecolor = "black", align='center',alpha=1,color='gray',ecolor='red', capsize=cps)
    ax4.bar(X_axis[i],averagetA[i] ,yerr=np.std(stdTA[i]),label="DSTA", width=width,edgecolor = "black", align='center',alpha=1,color='darkgray',ecolor='red', capsize=cps)
    ax4.bar(X_axis[i]+width,averagetG[i] ,yerr=np.std(stdTG[i]),label="PG",width=width,edgecolor = "black", align='center',alpha=1,color='lightgray',ecolor='red', capsize=cps)
  

  else:
    ax.bar(X_axis[i]-width,averageProfDSTAR[i] ,yerr=stdR, hatch = '',width=width,edgecolor = "black", align='center',alpha=1,color='gray',ecolor='red', capsize=cps)
    ax.bar(X_axis[i],averageProfDSTA[i] ,yerr=stdA
        ,hatch = '',width=width,edgecolor = "black", align='center',alpha=1,color='darkgray',ecolor='red', capsize=cps)
    ax.bar(X_axis[i]+width,averageProfpg[i] ,yerr=stdG,hatch = '',width=width,edgecolor = "black", align='center',alpha=1,color='lightgray',ecolor='red', capsize=cps)
  
    ax2.bar(X_axis2[i]-width/2,averageDratioR[i] ,yerr=np.std(stdSizeR[i]), hatch = '',width=width,edgecolor = "black", align='center',alpha=1,color='gray',ecolor='red', capsize=cps)
    ax2.bar(X_axis2[i]+width/2,averageDratioG[i] ,yerr=np.std(stdSizeG[i]),hatch = '',width=width,edgecolor = "black", align='center',alpha=1,color='darkgray',ecolor='red', capsize=cps)
   
    ax3.bar(X_axis2[i]-width/2,averageThchangeR[i] ,yerr=np.std(stdthR[i]),width=width,edgecolor = "black", align='center',alpha=1,color='gray',ecolor='red', capsize=cps)
    ax3.bar(X_axis2[i]+width/2,averageThchangeG[i] ,yerr=np.std(stdthG[i]), width=width,edgecolor = "black", align='center',alpha=1,color='darkgray',ecolor='red', capsize=cps)
   
    ax4.bar(X_axis[i]-width,averagetR[i] ,yerr=np.std(stdTR[i]),width=width,edgecolor = "black", align='center',alpha=1,color='gray',ecolor='red', capsize=cps)
    ax4.bar(X_axis[i],averagetA[i] ,yerr=np.std(stdTA[i]), width=width,edgecolor = "black", align='center',alpha=1,color='darkgray',ecolor='red', capsize=cps)
    ax4.bar(X_axis[i]+width,averagetG[i] ,yerr=np.std(stdTG[i]),width=width,edgecolor = "black", align='center',alpha=1,color='lightgray',ecolor='red', capsize=cps)
  
 
  j+=2
 
    
 
#title = list(range(0, len(averageDratioR)+1))
title=list(X_axis)    
#title = list(range(1, j)) 
ax.set_xticks(title[0:len(title)]) # define points up to the length of names we added
ax.set_xticklabels(labels,  rotation=40 )
ax.set_ylabel('Profit Ratio')
#ax.yaxis.grid(True)
ax.legend(loc='best',handletextpad=0.21 ,ncol=1,columnspacing=0.59)
ax.grid(False)
ax.set_yticks(np.arange(0, 1.1, 0.2))
fig.savefig("profitComp.pdf", dpi=300, bbox_inches = 'tight')
 
title=list(X_axis2) 
ax2.set_xticks(title) # define points up to the length of names we added
ax2.set_xticklabels(  labels , rotation=40)
ax2.set_ylabel('DataSize Ratio')
#ax2.yaxis.grid(True)
ax2.set_yticks(np.arange(0,max(stdSizeR[0])+0.4, 0.2))
#ax2.set_ylim(0,max(stdSizeR[0])+0.3) 
ax2.legend(loc='best',handletextpad=0.31 ,ncol=2,columnspacing=0.19,)
ax2.grid(False)
fig2.savefig("DataSizeComp.pdf", dpi=300, bbox_inches = 'tight')
  
ax3.set_xticks(title) # define points up to the length of names we added
ax3.set_xticklabels(  labels , rotation=40)
ax3.set_ylabel(    'Increase in\nAllocated Tasks (%)')
ax3.yaxis.grid(True)
ax3.legend(loc='best',handletextpad=0.31 ,ncol=2,columnspacing=0.19,)
ax3.grid(False)
ax3.set_ylim(-0.9,30.0) 
fig3.savefig("Allocated_Tasks.pdf", dpi=300, bbox_inches = 'tight')

title=list(X_axis)    
ax4.set_xticks(title[0:len(title)]) # define points up to the length of names we added
ax4.set_xticklabels(labels,  rotation=40 )
ax4.set_ylabel(    'Execution Time (ms)')
#ax4.yaxis.grid(True)
ax4.legend(loc='best',handletextpad=0.31 ,ncol=3,columnspacing=0.19,)
ax4.grid(False)
#ax4.set_ylim(0,70)
fig4.savefig("Exc_Time.pdf", dpi=300, bbox_inches = 'tight')


'''plot best case and worse cases''' 
i=2
minPchgI=0 
maxPchgI=0
minDChgI=0
maxDChgI=0
while i< len(profitChg):
    if(profitChg[i]<profitChg[minPchgI]):
        minPchgI=i
    if(profitChg[i]>profitChg[maxPchgI]):
        maxPchgI=i
    if(dataChg[i]<dataChg[minDChgI]):
        minDChgI=i
    if(dataChg[i]>dataChg[maxDChgI]):
        maxDChgI=i
             
    i+=2    
    


plt.rcParams['font.size'] = '24'
plt.rcParams['lines.markersize'] = 24
plt.rcParams["font.family"] = "Times New Roman"
plt.rcParams["axes.labelweight"] = "bold"


fs=24


cps=5
width=0.4
'''DR = data ratio or the ratio of size of DSTAR/DSTA
PR= Profit RAtio= thr ratio of DSTR profit over DSTA'''

labels=['PR', 'DR','PR', 'DR', ]
x=np.arange(1, 4.0, 0.7)
fig3, ax3= plt.subplots()
ax2 = ax3.twinx() 

pmax= profit2[maxPchgI]/profit1[maxPchgI] 
dpmax= dSize2[maxPchgI]/dSize1[maxPchgI]
ax3.bar(x[0],pmax,yerr=np.std(profit2[maxPchgI]/profit1[maxPchgI]),hatch = '',width=width/2,edgecolor = "black", align='center',alpha=1,color='gray',ecolor='red', capsize=cps)
ax2.bar(x[0]+0.24,dpmax ,yerr=np.std(dSize2[maxPchgI]/dSize1[maxPchgI]), hatch = '',width=width/2,edgecolor = "black", align='center',alpha=1,color='gray',ecolor='red', capsize=cps)

#ax.bar(x[0],thrChg[maxPchgI] ,yerr=np.std(thrChg[maxPchgI]),hatch = '',width=width/2,edgecolor = "black", align='center',alpha=1,color='gray',ecolor='red', capsize=10)
print("Best_case of profit : thr allocated "+ str(thrChg[maxPchgI]))

pmin=profit2[minPchgI]/profit1[minPchgI]
dpmin=dSize2[minPchgI]/dSize1[minPchgI]
ax3.bar(x[1], pmin,yerr=np.std(profit2[minPchgI]/profit1[minPchgI]),hatch = '',width=width/2,edgecolor = "black", align='center',alpha=1,color='darkgray',ecolor='red', capsize=cps)
#ax3.bar(x[1]+0.2,profit1[minPchgI]/maxProfit[minPchgI] ,yerr=np.std(profit1[minPchgI]/maxProfit[minPchgI]),label='WorseCase', hatch = '/',width=width/2,edgecolor = "black", align='center',alpha=1,color='darkgray',ecolor='red', capsize=10)
ax2.bar(x[1]+0.24,dpmin ,yerr=np.std(dSize2[minPchgI]/dSize1[minPchgI]),hatch = '',width=width/2,edgecolor = "black", align='center',alpha=1,color='darkgray',ecolor='red', capsize=cps)
#ax2.bar(x[1]+0.6,thrChg[minPchgI] ,yerr=np.std(thrChg[minPchgI]), hatch = '/',width=width/2,edgecolor = "black", align='center',alpha=1,color='darkgray',ecolor='red', capsize=10)
print("Worse_case  of profit: thr allocated "+ str(thrChg[minPchgI]))

#ax2.set_ylim(0,119)
#ax3.set_ylim(0,119)
title = [x[0],x[0]+0.24,x[1],x[1]+0.24 ]#should be aligned with the values defined in bar graph in the same order

 
ax3.set_xticks(title) # define points up to the length of names we added
ax3.set_xticklabels(  labels , rotation=0)
ax3.set_ylabel('Profit Ratio (PR)')
#ax3.yaxis.grid(True)

ax2.set_xticks(title) # define points up to the length of names we added
ax2.set_xticklabels(  labels , rotation=0)
ax2.set_ylabel('DataSize Ratio (DR)')
#ax2.yaxis.grid(True)
ax2.set_ylim(0,1.48) 
ax3.set_ylim(0,1.48) 
#ax2.legend(loc='best',handletextpad=0.31 ,ncol=2,columnspacing=0.19,)
ax3.text(x[0]-0.08, 1.32, 'Best Case',
        fontsize = fs, color ="black")
ax3.text(x[1]-0.08, 1.05, 'Worst Case',
        fontsize = fs, color ="black")
fig3.savefig("best_worst_profit.pdf", dpi=300, bbox_inches = 'tight')
#fig3.savefig("best_worse_DataSize.pdf", dpi=300, bbox_inches = 'tight')

'''now for best abd worse DataChange'''

#labels=['DSTAR/DSTA','DSTAR', 'DSTA','DSTAR/DSTA','DSTAR', 'DSTA' ]
labels=['DR', 'PR','DR', 'PR', ]
fig3, ax3= plt.subplots()
ax2 = ax3.twinx() 

dmin=dSize2[minDChgI]/dSize1[minDChgI]
pdmin=profit2[minDChgI]/profit1[minDChgI] 

ax2.bar(x[0],dmin ,yerr=np.std(dSize2[minDChgI]/dSize1[minDChgI]),width=width/2,edgecolor = "black", align='center',alpha=1,color='gray',ecolor='red', capsize=cps)
#ax3.bar(x[0]+0.2,profit2[minDChgI]/maxProfit[minDChgI] ,yerr=np.std(profit2[minDChgI]/maxProfit[minDChgI]),hatch = '',width=width/2,edgecolor = "black", align='center',alpha=1,color='blue',ecolor='red', capsize=10)
ax3.bar(x[0]+0.24,pdmin,yerr=np.std(profit2[minDChgI]/profit1[minDChgI]),width=width/2,edgecolor = "black", align='center',alpha=1,color='gray',ecolor='red', capsize=cps)
#ax2.bar(x[0]+0.6,thrChg[minDChgI] ,yerr=np.std(thrChg[minDChgI]),hatch = '',width=width/2,edgecolor = "black", align='center',alpha=1,color='gray',ecolor='red', capsize=10)
print("best_case of dsize: thr allocated "+ str(thrChg[minDChgI]))

dmax=dSize2[maxDChgI]/dSize1[maxDChgI]
pdmax=profit2[maxDChgI]/profit1[maxDChgI]

ax2.bar(x[1], dmax,yerr=np.std(dSize2[maxDChgI]/dSize1[maxDChgI]),width=width/2,edgecolor = "black", align='center',alpha=1,color='darkgray',ecolor='red', capsize=cps)
#ax3.bar(x[1]+0.2,profit2[maxDChgI]/maxProfit[maxDChgI] ,yerr=np.std(profit2[maxDChgI]/maxProfit[maxDChgI]),hatch = '/',width=width/2,edgecolor = "black", align='center',alpha=1,color='blue',ecolor='red', capsize=10)
ax3.bar(x[1]+0.24,pdmax ,yerr=np.std(profit2[maxDChgI]/profit1[maxDChgI]),width=width/2,edgecolor = "black", align='center',alpha=1,color='darkgray',ecolor='red', capsize=cps)
#ax2.bar(x[1]+0.6,thrChg[maxDChgI] ,yerr=np.std(thrChg[maxDChgI]), hatch = '/',width=width/2,edgecolor = "black", align='center',alpha=1,color='darkgray',ecolor='red', capsize=10)
'''Nilooo @@@@@@ report kon thrchng ro '''
print("Worse_case of dsize: thr allocated "+ str(thrChg[maxDChgI]))

ax3.set_xticks(title) # define points up to the length of names we added
ax3.set_xticklabels(  labels , rotation=0)
ax3.set_ylabel('DataSize Ratio (DR)', fontsize = fs,)

#ax3.legend(loc='best',handletextpad=0.31 ,ncol=1,columnspacing=0.19,)
ax2.set_ylim(0,1.5) 
ax3.set_ylim(0,1.52) 
#ax3.set_ylim(0,129) 
ax2.set_xticks(title) # define points up to the length of names we added
ax2.set_xticklabels(  labels , rotation=0)
ax2.set_ylabel('Profit Ratio (PR)', fontsize = fs,)
ax2.grid(False)
ax3.grid(False)
ax3.text(x[0]-0.08, 1.07, 'Best Case',
        fontsize= fs, color ="black")
ax3.text(x[1]-0.08, 1.35, 'Worst Case',
        fontsize = fs, color ="black")
#ax2.legend(loc='left',handletextpad=0.31 ,ncol=1,columnspacing=0.19,)
fig3.savefig("best_worst_DataSize.pdf", dpi=300, bbox_inches = 'tight')

'''forpaper results'''
incpg=[]
incA=[]

DSTA_pg_profit=[]
DSTA_p_datag=[]

for i in range (0, len(averageProfpg)):
    incpg.append((averageProfDSTAR[i]-averageProfpg[i])/averageProfpg[i])

    incA.append((averageProfDSTAR[i]-averageProfDSTA[i])/averageProfDSTA[i])

    DSTA_pg_profit.append((averageProfDSTA[i]-averageProfpg[i])/averageProfpg[i])



fig, ax= plt.subplots()
 

import numpy as np
import matplotlib.pyplot as plt
from scipy.stats import norm

#x-axis ranges from -3 and 3 with .001 steps
x = np.arange(-3, 3, 0.001)

plt.yticks(([]))
plt.ylabel('Probability Density', fontsize=20)
plt.gca().xaxis.grid(True)
loc = np.arange(-3,4, 1)
labels = ["μ−3σ","μ−2σ","μ−σ","μ","μ+σ","μ+2σ","μ+3σ"]
plt.xticks(loc, labels, rotation='0')
#plot normal distribution with mean 0 and standard deviation 1
plt.plot(x, norm.pdf(x, 0, 1))
fig.subplots_adjust(right =1.1)
plt.show()
fig.savefig("Normal_distribution.pdf", dpi=300, bbox_inches = 'tight')
fig.savefig("Normal_distribution.jpg", dpi=300, bbox_inches = 'tight')
