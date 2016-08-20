# -*- coding: utf-8 -*-
# @Author: prabhakar
# @Date:   2016-08-17 22:40:37
# @Last Modified by:   Prabhakar Gupta
# @Last Modified time: 2016-08-18 22:39:25

import nltk
from nltk.stem.wordnet import WordNetLemmatizer

from constants import NOUN_TAGS


def update_dict(existing_dict, unclean_text):
	new_dict = get_lemmatized_noun_dict(existing_dict, unclean_text)

	return existing_dict

def get_lemmatized_noun_dict(data_dict, unclean_text):
	tokens = nltk.word_tokenize(unclean_text)
	pos_tag_data = nltk.pos_tag(tokens)

	for i in pos_tag_data:
		if i[1] in NOUN_TAGS:
			noun_word = i[0]
			clean_noun_word = ''.join(e for e in noun_word if e.isalnum())
			lemmatized_word = lmtzr.lemmatize(clean_noun_word)

			data_dict = increase_score(existing_dict, lemmatized_word, 1)
	return data_dict


def check_prediction():
	return