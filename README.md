# VertiKin

> VertiKin is an e-commerce platform which allows the user to search through various products. It has a one-of-its-kind feature of **automatically detecting what user might be interested in buying in near future**. 

## How VertiKin works

[VertiKin Mobile app](https://github.com/prabhakar267/vertikin/tree/master/Android) stores the keystrokes made by a user on his phone (**we do not read passwords and private information, so user can be assured of his security**). This data is sent to the [VertiKin server](https://github.com/prabhakar267/vertikin/tree/master/server), where using **Natural language processing (NLP)**, important information is extracted and using frequency as a parameter, it tries to find if the user is interested in buying some product or not. If so, a GCM push notification is sent to the user.

## VertiKin Improves itself

Let's say VertiKin realised user wants to buy product A, but user did not want it, we give him a small feedback form, where he simply needs to click **No** and VertiKin would improve the suggestion from next time. User starts with  a [``DEFAULT_THRESHOLD``](https://github.com/prabhakar267/vertikin/blob/master/server/constants.py#L11) and according to the user feedback, user threshold changes by [``THRESHOLD_DELTA``](https://github.com/prabhakar267/vertikin/blob/master/server/constants.py#L13).

## Impact

+ According to a [2010 study by The Nielsen Company](http://www.nielsen.com/us/en/insights/news/2010/global-online-shopping-report.html), people tend to talk more about their needs and reviews influencing other people's opinion. So we used this information to detect, **if a person tends to buy something, he tends to talk about it**. 
+ **Cognitive fluency**  is the human tendency to prefer things that are familiar and easy to understand. According to this [article published on boston.com](http://archive.boston.com/bostonglobe/ideas/articles/2010/01/31/easy__true/?page=full), people buy stuff which is easier to understand. So using this information and the fact that after a certain point of time our primary source of knowledge becomes our peer group, we can deduce it is highly likely a person discusses a product with his friends / family.

## Screenshot

<img src="/screenshots/Screenshot_20160821-020950.png" width="200px">

## External Tools / APIs used

+ [Custom Keyboard](https://play.google.com/store/apps/details?id=com.androapps.keystroke.logger) by Mehdi Mirzaei
+ [nltk](http://www.nltk.org/)
+ [Google Cloud Messaging](https://developers.google.com/cloud-messaging/)
+ [Walmart Open API](https://developer.walmartlabs.com/docs)


## Contact Us

+ **[Prabhakar Gupta](mailto:prabhakargupta267@gmail.com)**
+ **[Swati Garg](mailto:swati.garg.nsit@gmail.com)**

