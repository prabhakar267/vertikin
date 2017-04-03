# VertiKin (Server)
## Setup 
```bash
git clone https://github.com/prabhakar267/vertikin.git && cd vertkin/server
```
```bash
pip install virtualenv
virtualenv venv
source venv/bin/activate
```
```bash
[sudo] pip install -r requirements.txt
```
+ Generate [Walmart Open Product API Key](https://developer.walmartlabs.com/docs) and edit [api_constants.sample.py](api_constants.sample.py).
+ No need to Generate GCM API Key, since you will be by default working on [DEBUG](https://github.com/prabhakar267/vertikin/blob/master/server/settings.py#L1) mode
```
python app.py
```
**Open ```localhost:5000```**


