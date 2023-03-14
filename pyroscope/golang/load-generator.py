import random
import requests
import time

VEHICLES = [
    'bike',
    'scooter',
    'car',
]

if __name__ == "__main__":
    print(f"starting load generator")
    time.sleep(3)
    while True:
        vehicle = VEHICLES[random.randint(0, len(VEHICLES) - 1)]
        resp = requests.get(f'http://127.0.0.1:5000/{vehicle}')
        print(f"received {resp}")
        time.sleep(random.uniform(0.2, 0.4))
