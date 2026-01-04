from flask import Flask, jsonify
import requests

app = Flask(__name__)

@app.route("/")
def home():
    return jsonify(
        app="python-sample",
        status="ok"
    )

@app.route("/health")
def health():
    response = requests.get("https://httpbin.org/status/200")
    return jsonify(
        upstream_status=response.status_code
    )

if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000)

