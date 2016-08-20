# -*- coding: utf-8 -*-
# @Author: prabhakar
# @Date:   2016-08-17 22:14:14
# @Last Modified by:   Prabhakar Gupta
# @Last Modified time: 2016-08-20 23:50:18

import json
import os.path
import pickle

from flask import Flask, request, redirect
from flask_cors import CORS

from constants import GITHUB_REPOSITORY_LINK
from utils import update_dict, check_prediction


app = Flask(__name__)
CORS(app)


@app.route("/user", methods=['GET'])
def main():
	# # get data from args
	# imei_num = request.args.get("imei")
	# new_data = request.args.get("user_data", "")
	# new_data = json.loads(new_data)

	# get data from sample file
	with open('sample') as data_file:    
		data = json.load(data_file)

	imei_num = data['imei']
	new_data = data['user_data']

	file_path = "user_data/" + imei_num

	if os.path.isfile(file_path):
		existing_data = pickle.load(open(file_path, "rb"))
		data_dict = update_dict(existing_data, new_data)
	else:
		data_dict = update_dict({}, new_data)

	pickle.dump(data_dict, open(file_path, "wb"))
	return json.dumps(data_dict)


@app.errorhandler(404)
def page_not_found(error):
	return redirect(GITHUB_REPOSITORY_LINK)


if __name__ == "__main__":
	app.run(debug=True)
