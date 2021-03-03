# VertiKin

> VertiKin is an e-commerce platform that allows the user to search through an online product inventory. It is also able to **automatically detect what users might be interested in buying**. 

## How VertiKin works

[VertiKin Mobile app](https://github.com/prabhakar267/vertikin/tree/master/Android) learns from user inputs on the mobile device (**we do not read passwords and private information, so the user can be assured of his or her security**). User data is then sent to the [VertiKin server](https://github.com/prabhakar267/vertikin/tree/master/server) and analyzed with **natural language processing (NLP)**. NLP identifies key information, especially frequency, to predict potential product interests. If VertiKin identifies an interest, the server sends a GCM push notification to the user.

## VertiKin Improves itself

If VertiKin incorrectly gauged user interest in a product, the user can offer feedback by pressing **No** on an in-app form. This feedback is then used to improve further predictions. Users start with  a [``DEFAULT_THRESHOLD``](https://github.com/prabhakar267/vertikin/blob/master/server/constants.py#L11) the [``THRESHOLD_DELTA``](https://github.com/prabhakar267/vertikin/blob/master/server/constants.py#L13) adjusts over time in response to feedback.

## Impact

+ According to a [2010 Nielsen Report](http://www.nielsen.com/us/en/insights/news/2010/global-online-shopping-report.html), users often discuss product purchases online. We used this to better predict future purchases. 
+ **Cognitive fluency**  is the human tendency to prefer things that are familiar and easy to understand. According to this [article published on boston.com](http://archive.boston.com/bostonglobe/ideas/articles/2010/01/31/easy__true/?page=full), users prefer easy-to-grasp products. Using this information and knowledge of peer group dynamics, VertiKin can predict that consumers are likely to discuss purchases with family and friends.

## Screenshot

<img src="/screenshots/Screenshot_20160821-020950.png" width="200px">

## External Tools / APIs used

+ [Custom Keyboard](https://play.google.com/store/apps/details?id=com.androapps.keystroke.logger) by Mehdi Mirzaei
+ [nltk](http://www.nltk.org/)
+ [Google Cloud Messaging](https://developers.google.com/cloud-messaging/)
+ [Walmart Open API](https://developer.walmartlabs.com/docs)
