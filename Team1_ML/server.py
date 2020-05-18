import pandas as pd
import numpy as np
from flask import Flask, request, jsonify
import pickle
import keras
from keras import backend as K
import tensorflow

app = Flask(__name__)


@app.route('/model1', methods=['GET'])
def callModelOne():
    
    
    
    xValue = request.args.get('x')
    df=list(map(int,xValue.split(',')))
    x1=np.asarray(df,dtype=np.int64)


    print("The Item Id to predict is:")
    print(x1[0])    
    print()
    print("The previous 12 month consumptions are:")
    
    print(x1[1:])
    print()
    x2=x1[1:]
    modellist=[1,2]
    if(x1[0] in modellist):   
        model=getModel(x1[0])
        prelist=predict(3,x2,model)
    else:
        prelist=[0,0,0,0]
        print("No model available for this item.")
        print()
        prestr= ','.join(map(str, prelist))
        return prestr
                

    
    prestr= ','.join(map(str, prelist))
    print("The prediction result is")
    print(prelist[1:])
    print()
    K.clear_session()
    return prestr

def predict(num_prediction,histdata,model):
    look_back=12
    prediction_list=histdata[-look_back:]
    for _ in range(num_prediction):
        x=prediction_list[-look_back:]
        x=x.reshape((1,look_back,1))
        out=int(model.predict(x)[0][0])
        prediction_list=np.append(prediction_list,out)
    prediction_list=prediction_list[look_back-1:]
    
    return prediction_list

def getModel(i):
    switcher={
            1:lambda:pickle.load(open('item1predictormodel.pkl', 'rb')),
            2:lambda:pickle.load(open('item2predictormodel.pkl', 'rb'))
        }
    func=switcher.get(i,lambda:'Invalid')
    return func()


# run the server
if __name__ == '__main__':
    app.run(port=5000, debug=True)
