# -*- coding: utf-8 -*-
"""

This is to generate input data for profit, req-size and server (There is variation based on the server number and capacity )
Egde project

has the corresponding plots 
The code is parametric based on mu and standard deviation value
Created on Mon Feb 28 07:40:00 2022

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
from openpyxl import load_workbook
import pandas as pd
from openpyxl.styles import PatternFill






#pS_mu=17 #average value of mu for small pforit 
#pM_mu=24 #average value of mu for medium pforit -> calculated from p1 and p2 in the bellow lines
pL_mu=30 #average value of mu for large pforit 

p_sigma=1.5
n=100
totalCap=1000

mu, sigma = 20,6 # both are for request count
reqColumn=[]
efficiencyCol=[]
profCol=[]
reqCol=[]



efficiencyR1=[] # to store all data with diff 
profR1=[]
reqR1=[]

efficiencyR2=[]
profR2=[]
reqR2=[]

efficiencyR3=[]
profR3=[]
reqR3=[]


MaxReqNeeded=[]

# x and y are mu for the medium and min profit
'''p1 is 17 and p2 is 24 so we set the mu for min and medium profit to 19 and 26 with sigma 2'''





 # holds three values/columns that each corresponds to small, medium and large profit 

#for k in range(0,3): # we need three groups of profit




plt.rcParams['font.size'] = '13'
plt.rcParams['lines.markersize'] = 10
plt.rcParams["font.family"] = "Times New Roman"
plt.rcParams["axes.labelweight"] = "bold"
markers=['o','+','*','.','v','1','s','x','p','d'] 

'''generate the req #'''

variety=3
for i in range (0,variety):
 
  '''generate profit based on new request mean and efficiency inequality, profit3 is assumed to be fixed to 30'''
  profitList=[]

  rmax=mu+(1.5*sigma)
  rmin=mu-(1.5*sigma)
 
  rmid=mu
  if(rmin<0):
    rmin=1
  pmin= ( pL_mu * np.sqrt(rmin/totalCap))/np.sqrt(rmax/totalCap)
  pmid= (pmin * np.sqrt(rmid/totalCap))/np.sqrt(rmin/totalCap)
  #pMu=[p1,p2+1.7,pL_mu-5] 
  pMu=[pmin,pmid+2,pL_mu-2] 
  
  
  den=(np.sqrt(rmin/totalCap))
  ef1= float(pMu[0])/float(den)
  den=(np.sqrt(rmid/totalCap))
  ef2=float(pMu[1])/float(den)
  den=(np.sqrt(rmax/totalCap))
  ef3= int(pMu[2])/den
  
  for j in range(0,3):
    muP, sigmaP= pMu[j],p_sigma
    fig, ax = plt.subplots() 
    profit=(np.random.normal(muP, sigmaP, n))
    profit=pd.Series(profit)
    profit[profit < muP-1.5*sigmaP] = np.random.normal(muP, sigmaP, 1)
    profit[profit > muP+1.5*sigmaP] = np.random.normal(muP, sigmaP, 1)
  
    lp4=max(profit) #Ls are boundaries for three regions
    lp1= min(profit)
    lp2= muP- sigmaP
    lp3=muP+sigmaP
    binlist=[lp1,lp2,lp3,lp4]
    binlist.sort()
    count, bins1, ignored = plt.hist(profit, bins=binlist, density=True)
    value=profit.value_counts(bins=binlist).sort_index()
    #plt.plot(bins1, 1/(sigmaP * np.sqrt(2 * np.pi)) *np.exp( - (bins1 - muP)**2 / (2 * sigmaP**2) ), linewidth=2, color='r')
    plt.plot(profit, 1/(sigmaP * np.sqrt(2 * np.pi)) *np.exp( - (profit - muP)**2 / (2 * sigmaP**2) ),
         linewidth=2, color='r') 
    plt.xlabel(    'profit')
    plt.ylabel(    'probability density')
    plt.show()
    fig.savefig( "profit"+str(j+1)+"SD"+ str(i+1)+".pdf" )
    profitList.append(profit)

  '''generate profit based on new request mean and efficiency inequality, profit3 is assumed to be fixed to 30'''

  fig, ax = plt.subplots() 
  requests=(np.random.normal(mu, sigma, n))
  requests=pd.Series(requests)
  requests[requests < 0] = np.random.normal(mu, sigma, 1)
  requests[requests > mu+ 2.5*sigma] = np.random.normal(mu, sigma, 1)
  requests[requests < mu-2.5*sigma] = np.random.normal(mu, sigma, 1)

  l4=max(requests) #Ls are boundaries for three regions
  l1= min(requests)
  l2= mu-sigma
  l3=mu+sigma
  binlist=[l1,l2,l3,l4]
  binlist.sort()
  count, bins1, ignored = plt.hist(requests, bins=binlist, density=True)
  value=requests.value_counts(bins=binlist).sort_index()

  plt.plot(requests, 1/(sigma * np.sqrt(2 * np.pi)) *np.exp( - (requests - mu)**2 / (2 * sigma**2) ),
         linewidth=2, color='r')
  plt.xlabel(    'request size')
  plt.ylabel(    'probability density')
  plt.show()
  fig.savefig( "RequestCount"+str(i+1)+".pdf" )
  
  request1=requests[requests < binlist[1]]# values bellow b1
  request2=requests[requests <= binlist[2]]# values bellow boundry2
  request3=requests[requests > binlist[2]] # values bellow boundry3
  
  ''' we need to separete the regions to assign the proper profit to each and cal efficiency , assume the boundries are as b0, b1, b2, b3 we did as above to store all
  in req1-re13 series because in the next lines of code we can just access the values using one
  inequality'''
  
  reqRegion1=( list( request1)) #small req, med profit-> region stores request
  reqRegion2=(list( request2[ request2 >= binlist[1]])) # med req, large profit -> we know they are bellow bound2 by above codes
  reqRegion3=( list(request3))#large req, large profit
  
  profRegion1=[] #small req, small profit-> region stores request
  profRegion2=[]# med req, med profit
  profRegion3=[]# large req, large profit
  sm_prof=profitList[0]
  med_prof=profitList[1]
  lg_prof=profitList[2]

  
  effRegion1=[] #small req, med profit-> region stores request
  effRegion2=[]# med req, large profit
  effRegion3=[]# large req, large profit
  effList=[] # stores the efficiency for each region
  reqList=[]
  profitL=[]

  
  '''store prfoit value and calculate efficiency of each region'''
  for k in range(0,len(reqRegion1)):
      den=np.sqrt(reqRegion1[k]/totalCap)
      profRegion1.append(sm_prof[k])
      effRegion1.append(profRegion1[k]/den)
  for k in range(0,len(reqRegion2)):
      den=np.sqrt(reqRegion2[k]/totalCap)
      profRegion2.append(med_prof[k])
      effRegion2.append(profRegion2[k]/den)
  for k in range (0,len(reqRegion3)):
      den=np.sqrt(reqRegion3[k]/totalCap)
      profRegion3.append(lg_prof[k])
      effRegion3.append(profRegion3[k]/den)
  
  for pf  in profRegion1 :
      profitL.append(np.round(pf,2))
  for pf  in profRegion2 :
      profitL.append(np.round(pf,2))
  for pf  in profRegion3 :
      profitL.append(np.round(pf,2))


   
  for req  in  reqRegion1:
      reqList.append(np.round(req,2))
  for req  in  reqRegion2:
      reqList.append(np.round(req,2))
  for req  in  reqRegion3:
      reqList.append(np.round(req,2))  
    
  
  for eff  in effRegion1:
    effList.append(eff)
  for eff  in effRegion2:
    effList.append(eff)   
  for eff  in effRegion3:
    effList.append(eff)
    
  efficiencyCol.append(effList) 
  reqCol.append(reqList)
  profCol.append(profitL)
  
 
      
  sigma+=1.5
  reqColumn.append(requests)
  MaxReqNeeded.append( sum(requests))
 
 


 

#''' nill temporarly commented to see which task type is correct 
 
writer = pd.ExcelWriter('test.xlsx', engine='openpyxl') 
wb  = writer.book  
df = pd.DataFrame({'Req_SD1': reqColumn[0],
                  'Req_SD2': reqColumn[1],
                  'Req_SD3': reqColumn[2],
                'Small_prof': sm_prof,
                'Medium_prof': med_prof ,
                'Large_prof': lg_prof  })

df.to_excel(writer, index=False)
wb.save('Tasks.xlsx')


writer = pd.ExcelWriter('test2.xlsx', engine='openpyxl') 
wb  = writer.book  
df = pd.DataFrame({
                    'reqR1-R3_SD1': reqCol[0],
                    'profitR1-R3_SD1': profCol[0],
                     'EfficiencyR1-R3_SD1': efficiencyCol[0],
                    'reqR1-R3_SD2': reqCol[1],
                     'profitR1-R3_SD2': profCol[1],
                      'EfficiencyR1-R3_SD2': efficiencyCol[1],
                    'reqR1-R3_SD3': reqCol[2],
                    'profitR1-R3_SD3': profCol[2],
                  'EfficiencyR1-R3_SD3': efficiencyCol[2],
               })
df.to_excel(writer, index=False)
wb.save('RegionResult.xlsx')
#'''



with open('InstanceSD1_req_profit.txt', 'w') as wr1:
    
  for l in range (0,3):
    wr1.write("shuffle"+str(l+1)+"\n")
    temp = list(zip(reqCol[0], profCol[0]))
    #random.shuffle(temp)
    res1, res2 = zip(*temp)
    res1, res2 = list(res1), list(res2)
    wr1.write(str(res1 )+"\n")
    wr1.write(str( res2  )+"\n")
    

with open('InstanceSD2_req_profit.txt', 'w') as wr2:
    
  for l in range (0,3):
    wr2.write("shuffle"+str(l+1)+"\n")
    temp = list(zip(reqCol[1], profCol[1]))
    #random.shuffle(temp)
    res1, res2 = zip(*temp)
    res1, res2 = list(res1), list(res2)
    wr2.write(str(res1 )+"\n")
    wr2.write(str( res2  )+"\n")
    
with open('InstanceSD3_req_profit.txt', 'w') as wr3:
    
  for l in range (0,3):
    wr3.write("shuffle"+str(l+1)+"\n")
    temp = list(zip(reqCol[2], profCol[2]))
    #random.shuffle(temp)
    res1, res2 = zip(*temp)
    res1, res2 = list(res1), list(res2)
    wr3.write(str(res1 )+"\n")
    wr3.write(str( res2  )+"\n")   





'''let's start generating the requests for the first SD1'''
reqSdN=0 # number of SD between 0 to 2
highDemandRatio=np.random.uniform(2.8,3.1,1) # we define a demand ratio range
lowDemandRatio=np.random.uniform(0.8,1.2,1)
avgDemandRatio=np.random.uniform(1.8,2.2,1)

sigmaCapH= MaxReqNeeded[reqSdN] /highDemandRatio
sigmaCapL=  MaxReqNeeded[reqSdN] /lowDemandRatio
sigmaCapM= MaxReqNeeded[reqSdN] /avgDemandRatio

'''assume that it's HOMOGENIOUS-> server count is up to sigmaCap/ size of max req'''
servCountH= sigmaCapH/ max(reqCol[reqSdN])
servCountL= sigmaCapL/ max(reqCol[reqSdN])
servCountM= sigmaCapM/ max(reqCol[reqSdN])
serverCap=max(reqCol[reqSdN])

#Homogenious capacity

with open('ServercapHomoGen.txt', 'w') as wr:
    
    wr.write(str("highDemandRatio\n"))
    
    for k in range (1, int(servCountH) ):
      wr.write(str(serverCap)+",")

    wr.write(str("\nMedDemandRatio\n"))
    
    for k in range (1, int(servCountM) ):
      wr.write(str(serverCap)+",")
      
    wr.write(str("\nLowDemandRatio\n"))
     
    for k in range (1, int(servCountL) ):
      wr.write(str(serverCap)+",")
    
    
''' now start to generate the heterogenious one'''

minRange= min(reqCol[reqSdN])
maxRange=max(reqCol[reqSdN])

sumCurCap=0
serverCapH=[]
serverCapM=[]
serverCapL=[]

while(sumCurCap<sigmaCapH):
    cap=np.round(np.random.uniform(minRange,maxRange,1),2)
    serverCapH.append(cap)
    sumCurCap+=cap
    
sumCurCap=0
while(sumCurCap<sigmaCapM):
    cap=np.round(np.random.uniform(minRange,maxRange,1),2)
    serverCapM.append(cap)
    sumCurCap+=cap

sumCurCap=0
while(sumCurCap<sigmaCapL):
    cap=np.round(np.random.uniform(minRange,maxRange,1),2)
    serverCapL.append(cap)
    sumCurCap+=cap
    
    

with open('ServercapHeteroGen.txt', 'w') as wr:
    
    wr.write(str("highDemandRatio\n"))
    
    for k in range (1, int(len(serverCapH)+1) ):
      wr.write(str(serverCapH[k-1][0])+",")

    wr.write(str("\nMedDemandRatio\n"))
    
    for k in range (1, int(len(serverCapM)+1) ):
      wr.write(str(serverCapM[k-1][0])+",")
      
    wr.write(str("\nLowDemandRatio\n"))
     
    for k in range (1, int(len(serverCapL)+1) ):
      wr.write(str(serverCapL[k-1][0])+",")
    
#'''    nill temporarly commented to see which task type is correct  -> this has a mix of requests

    
#'''