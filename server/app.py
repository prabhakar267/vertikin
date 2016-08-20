# -*- coding: utf-8 -*-
# @Author: prabhakar
# @Date:   2016-08-17 22:14:14
# @Last Modified by:   Prabhakar Gupta
# @Last Modified time: 2016-08-21 03:07:00

import json
import os.path
import pickle

from flask import Flask, request, redirect
from flask_cors import CORS

from settings import DEBUG
from constants import GITHUB_REPOSITORY_LINK, DEFAULT_THRESHOLD, THRESHOLD_DELTA
from utils import update_dict, check_prediction


app = Flask(__name__)
CORS(app)


@app.route("/update-user", methods=['GET'])
def main():
	# # get data from args
	# imei_num = request.args.get("imei")
	# new_data = request.args.get("user_data", "")
	# new_data = json.loads(new_data)

	# get data from sample file
	with open('sample') as data_file:    
		data = json.load(data_file)

	gcm_id = data['gcm_id']
	new_data = data['user_data']

	file_path = "user_data/" + gcm_id

	if os.path.isfile(file_path):
		user_history = pickle.load(open(file_path, "rb"))

		existing_data = user_history['data']
		threshold = user_history['threshold']

		data_dict = update_dict(existing_data, new_data)
	else:
		data_dict = update_dict({}, new_data)
		threshold = DEFAULT_THRESHOLD

	data_dict = check_prediction(data_dict, threshold)

	user_json = {
		'data' : data_dict,
		'threshold' : threshold,
	}
	
	pickle.dump(user_json, open(file_path, "wb"))
	
	return json.dumps(data_dict)


@app.route("/change-threshold", methods=['GET'])
def change_threshold():
	gcm_id = request.args.get("gcm_id")
	delta_flag = request.args.get("flag")

	file_path = "user_data/" + gcm_id

	if os.path.isfile(file_path):
		user_history = pickle.load(open(file_path, "rb"))

		existing_data = user_history['data']
		threshold = user_history['threshold']

		if delta_flag == 0:
			new_threshold = threshold - THRESHOLD_DELTA
		else:
			new_threshold = threshold + THRESHOLD_DELTA

		user_json = {
			'data' : existing_data,
			'threshold' : new_threshold,
		}
		
		pickle.dump(user_json, open(file_path, "wb"))

		return True

	return False


@app.errorhandler(404)
def page_not_found(error):
	return redirect(GITHUB_REPOSITORY_LINK)


if __name__ == "__main__":
	app.run(debug=True)
