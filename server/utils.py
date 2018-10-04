import operator

import enchant
import nltk
from gcm import *
from nltk.stem.wordnet import WordNetLemmatizer

from api_constants import WALMART_OPEN_PRODUCT_API_KEY, GCM_API_KEY
from constants import PROPER_NOUN_POS_TAGS
from settings import DEBUG

# append custom path for nltk corpus
nltk.data.path.append("nltk_data/")

lmtzr = WordNetLemmatizer()
enchant_dictionary = enchant.Dict("en_US")


def check_candidature(query_string):
    walmart_url = "http://api.walmartlabs.com/v1/search?apiKey={0}&query={1}".format(WALMART_OPEN_PRODUCT_API_KEY,
                                                                                     query_string)
    response = requests.get(walmart_url)
    if response.ok:
        if response.json()['totalResults'] > 0:
            return True

    return False


def update_dict(existing_dict, unclean_text):
    tokens = nltk.word_tokenize(unclean_text)
    pos_tag_data = nltk.pos_tag(tokens)

    for token_tuple in pos_tag_data:
        noun_word = token_tuple[0]
        clean_noun_word = ''.join(e for e in noun_word if e.isalnum())

        # check if the "clean_noun_word" is not an empty string
        if clean_noun_word:
            if token_tuple[1] in PROPER_NOUN_POS_TAGS and enchant_dictionary.check(clean_noun_word):
                lemmatized_word = lmtzr.lemmatize(clean_noun_word)

                if len(lemmatized_word) > 2:
                    existing_dict = increase_score(existing_dict, lemmatized_word, 1)

    return existing_dict


def increase_score(score_dict, key, value):
    lowercase_key = key.lower()
    if lowercase_key in score_dict:
        score_dict[lowercase_key] += value
    else:
        score_dict[lowercase_key] = value

    return score_dict


def check_prediction(dictionary, threshold, gcm_device_id):
    sum_of_freq = sum(dictionary.values())
    max_tuple = max(dictionary.iteritems(), key=operator.itemgetter(1))

    percentage_freq = (max_tuple[1]) / sum_of_freq
    if percentage_freq >= threshold:
        candidate_string = max_tuple[0]
        dictionary.pop(candidate_string, None)

        if check_candidature(candidate_string):
            # send GCM message
            if DEBUG:
                print("{0} : successful candidate".format(candidate_string))
            else:
                gcm_send(gcm_device_id, candidate_string)

    return dictionary


def gcm_send(gcm_device_id, string):
    gcm = GCM(GCM_API_KEY)
    data = {'search_query': string}

    gcm.plaintext_request(registration_id=gcm_device_id, data=data)
