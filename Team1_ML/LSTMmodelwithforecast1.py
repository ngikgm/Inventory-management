#!/usr/bin/env python
# coding: utf-8

# In[4]:


import pickle
import pandas as pd
import numpy as np
import keras
import tensorflow as tf
from keras.preprocessing.sequence import TimeseriesGenerator

import warnings
warnings.filterwarnings("ignore")

import chart_studio.plotly as py
import plotly.offline as pyoff
import plotly.graph_objs as go


# In[5]:


filename="itemconsumption.csv"
df=pd.read_csv(filename)
df['date']=pd.to_datetime(df['date'])
df_consumption=df.copy()
#Data Transformation
#plot the data to see if it's stationary

plot_data=[go.Scatter(x=df_consumption['date'],y=df_consumption['monthlyconsumption'],)]

plot_layout=go.Layout(title='Monthly Consumption')
fig=go.Figure(data=plot_data,layout=plot_layout)
pyoff.iplot(fig)


# In[6]:


#data preprocessing
look_back=12
cons_data=df_consumption['monthlyconsumption'].values
cons_data=cons_data.reshape((-1,1))

split_percent=0.80
split=int(split_percent*len(cons_data))
print(split_percent*len(cons_data))
cons_train=cons_data[:split]
cons_test=cons_data[split-look_back:]

date_train=df['date'][:split]
date_test=df['date'][split:]
print(len(cons_train))
print(len(cons_test))


# In[7]:


#TimeseriesGenerate


train_generator=TimeseriesGenerator(cons_train,cons_train,length=look_back,batch_size=20)
test_generator=TimeseriesGenerator(cons_test,cons_test,length=look_back,batch_size=1)


# In[8]:


from keras.models import Sequential
from keras.layers import LSTM,Dense

model=Sequential()
model.add(
    LSTM(10,
         activation='relu',
         input_shape=(look_back,1))
)
model.add(Dense(1))
model.compile(optimizer='adam',loss='mse')

num_epochs=800
model.fit_generator(train_generator,epochs=num_epochs,verbose=1)


# In[ ]:





# In[9]:


#Prediction

prediction=model.predict_generator(test_generator)
print(prediction)

cons_train=cons_train.reshape((-1))
cons_test=cons_test.reshape((-1))
prediction=prediction.reshape((-1))

trace1=go.Scatter(x=date_train, y=cons_train,mode='lines',name='Data')

trace2=go.Scatter(x=date_test,y=prediction,mode='lines',name='Prediction')

trace3=go.Scatter(x=date_test,y=cons_test,mode='lines',name='Ground Truth')

layout=go.Layout(title='Item consumption',xaxis={'title':"Date"},yaxis={'title':"Consumption"})

fig=go.Figure(data=[trace1,trace2,trace3],layout=layout)
fig.show()


# In[10]:


#Forecasting

cons_data=cons_data.reshape((-1))

def predict(num_prediction,model):
    prediction_list=cons_data[-look_back:]
    
    for _ in range(num_prediction):
        x=prediction_list[-look_back:]
        x=x.reshape((1,look_back,1))
        out=model.predict(x)[0][0]
        prediction_list=np.append(prediction_list,out)
    prediction_list=prediction_list[look_back-1:]
    
    return prediction_list

def predict_dates(num_prediction):
    last_date=df['date'].values[-1]
    prediction_dates=pd.date_range(last_date,periods=num_prediction,freq='M').tolist()
    return prediction_dates

num_prediction=12
forecast=predict(num_prediction,model)
forecast_dates=predict_dates(num_prediction)


# In[11]:


cons_data=cons_data.reshape((-1))

forecast=forecast.reshape((-1))

trace1=go.Scatter(x=date_train, y=cons_train,mode='lines',name='Data')

trace2=go.Scatter(x=forecast_dates,y=forecast,mode='lines',name='Prediction')

trace3=go.Scatter(x=date_test,y=cons_test,mode='lines',name='Ground Truth')

layout=go.Layout(title='Item consumption',xaxis={'title':"Date"},yaxis={'title':"Consumption"})

fig=go.Figure(data=[trace1,trace2,trace3],layout=layout)
fig.show()


# In[12]:


print(forecast)


# In[ ]:


pickle.dump(model, open('item1predictormodel.pkl', 'wb'))

