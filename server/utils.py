# -*- coding: utf-8 -*-
# @Author: prabhakar
# @Date:   2016-08-17 22:40:37
# @Last Modified by:   Prabhakar Gupta
# @Last Modified time: 2016-08-17 22:46:37

def update_dict(existing_dict, new_dict):
	for key, value in new_dict.iteritems():
		if key in existing_dict:
			existing_dict[key] += value
		else:
			existing_dict[key] = value
	return existing_dict

def check_prediction():
	return