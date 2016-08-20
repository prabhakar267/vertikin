# -*- coding: utf-8 -*-
# @Author: prabhakar
# @Date:   2016-08-17 22:40:37
# @Last Modified by:   Prabhakar Gupta
# @Last Modified time: 2016-08-21 00:46:02

import operator
import enchant
import nltk
from nltk.stem.wordnet import WordNetLemmatizer

from constants import PROPER_NOUN_POS_TAGS, DEFAULT_THRESHOLD

lmtzr = WordNetLemmatizer()
enchant_dictionary = enchant.Dict("en_US")

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


def check_prediction(dictionary, threshold=DEFAULT_THRESHOLD):
	sum_of_freq = sum(dictionary.values())
	max_tuple = max(dictionary.iteritems(), key=operator.itemgetter(1))

	percentage_freq = (max_tuple[1]) / sum_of_freq
	if percentage_freq >= threshold:
		candidate_string = max_tuple[0]
		dictionary.pop(candidate_string, None)
		print "\n\n\n\n\n\n"
		print candidate_string
		print percentage_freq
		print "\n\n\n\n\n\n"

	return dictionary