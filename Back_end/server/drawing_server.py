from flask import Flask, request
from flask_cors import CORS
import json

app = Flask(__name__)
CORS(app)

@app.route("/", methods=["POST"])
def helloWorld():
  drawing = request.data.decode("utf-8")
  drawing = json.loads(drawing)
  print(drawing[2][1][1])
  return request.data

app.run(host='0.0.0.0', port=5000)
