import os
import socket
from flask import Flask, render_template_string, redirect, url_for

app = Flask(__name__)

USE_REDIS = os.environ.get("USE_REDIS", "false").lower() == "true"
HOSTNAME = socket.gethostname()

if USE_REDIS:
    import redis
    r = redis.Redis(
        host=os.environ.get("REDIS_HOST", "redis"),
        port=int(os.environ.get("REDIS_PORT", "6379")),
        decode_responses=True,
    )
else:
    _counter = 0


PAGE = """<!doctype html>
<html lang="uk">
<head>
  <meta charset="utf-8">
  <title>Cloud Lab — Scaling</title>
  <style>
    body { font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", sans-serif;
           max-width: 540px; margin: 60px auto; padding: 20px; background: #fafafa; }
    .card { border: 1px solid #ddd; border-radius: 12px; padding: 32px;
            background: white; box-shadow: 0 2px 12px rgba(0,0,0,0.06); }
    h1 { margin-top: 0; color: #1f4e79; font-size: 22px; }
    .row { display: flex; justify-content: space-between; padding: 12px 0;
           border-bottom: 1px solid #eee; }
    .row:last-of-type { border-bottom: none; }
    .label { color: #777; font-size: 14px; }
    .value { font-family: "SF Mono", Consolas, Menlo, monospace; font-weight: 600; }
    .value.hostname { color: #c0504d; font-size: 15px; }
    .value.counter { color: #2e75b6; font-size: 28px; line-height: 1; }
    button { width: 100%; padding: 14px; margin-top: 20px; font-size: 16px;
             background: #1f4e79; color: white; border: none; border-radius: 8px;
             cursor: pointer; font-weight: 600; }
    button:hover { background: #2e75b6; }
    .mode { margin-top: 16px; padding: 10px 14px; border-radius: 8px;
            font-size: 13px; text-align: center; }
    .mode.local { background: #fff3cd; color: #856404; }
    .mode.redis { background: #d4edda; color: #155724; }
    .hint { color: #999; font-size: 12px; text-align: center; margin-top: 14px; }
  </style>
</head>
<body>
  <div class="card">
    <h1>Cloud Lab — Horizontal Scaling</h1>
    <div class="row">
      <span class="label">Hostname (container ID):</span>
      <span class="value hostname">{{ hostname }}</span>
    </div>
    <div class="row">
      <span class="label">Counter:</span>
      <span class="value counter">{{ counter }}</span>
    </div>
    <form method="post" action="/increment">
      <button type="submit">+1</button>
    </form>
    <div class="mode {{ 'redis' if use_redis else 'local' }}">
      Storage: {{ 'Redis (shared between replicas)' if use_redis else 'In-memory (per replica)' }}
    </div>
    <div class="hint">Refresh the page — the hostname will change between requests.</div>
  </div>
</body>
</html>"""


def get_counter():
    if USE_REDIS:
        val = r.get("counter")
        return int(val) if val else 0
    return _counter


def incr_counter():
    global _counter
    if USE_REDIS:
        return r.incr("counter")
    _counter += 1
    return _counter


@app.route("/")
def index():
    return render_template_string(
        PAGE,
        hostname=HOSTNAME,
        counter=get_counter(),
        use_redis=USE_REDIS,
    )


@app.route("/increment", methods=["POST"])
def increment():
    incr_counter()
    return redirect(url_for("index"))


@app.route("/healthz")
def healthz():
    return {"status": "ok", "hostname": HOSTNAME}, 200


if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000)
