# -*- coding: utf-8 -*-
# @Author: prabhakar
# @Date:   2016-08-17 22:40:37
# @Last Modified by:   Prabhakar Gupta
# @Last Modified time: 2016-08-20 23:55:33

import nltk
from nltk.stem.wordnet import WordNetLemmatizer

from constants import PROPER_NOUN_POS_TAGS

lmtzr = WordNetLemmatizer()

def update_dict(existing_dict, unclean_text):
	tokens = nltk.word_tokenize(unclean_text)
	pos_tag_data = nltk.pos_tag(tokens)

	for token_tuple in pos_tag_data:
		if token_tuple[1] in PROPER_NOUN_POS_TAGS:
			noun_word = token_tuple[0]
			clean_noun_word = ''.join(e for e in noun_word if e.isalnum())

			lemmatized_word = lmtzr.lemmatize(clean_noun_word)
			existing_dict = increase_score(existing_dict, lemmatized_word, 1)

	return existing_dict

def increase_score(score_dict, key, value):
	lowercase_key = key.lower()
	if lowercase_key in score_dict:
		score_dict[lowercase_key] += value
	else:
		score_dict[lowercase_key] = value

	return score_dict


