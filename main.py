from fastapi import FastAPI
import logging
import gpio_connector

gpio=gpio_connector.GpioConnector()

logging.basicConfig(
    level=logging.INFO,
    filename="py_log.log",filemode="a",
    format="%(message)s %(asctime)s"
    )

#.venv\Scripts\activate
#uvicorn main:app --host 0.0.0.0 --port 8000 --reload

logging.info("LED OFF") #base stat on gpio(low)

app = FastAPI()

@app.get('/')
async def root():
    return {"message": "Hello"}

@app.get('/status')
def status_On():
    file=open('py_log.log')
    str=file.readlines()[-1]
    status=str.find("ON") !=-1
    file.close()
    return {"status": status}

@app.get('/LED_ON')
def LED_On():
    if(status_On()!={"status": True}):
        gpio.LED_on()
        logging.info("LED ON")
        return {"LED_ON_INFO": "Turned on the LED"}
    else:
        return {"LED_ON_INFO": "LED already on"}
    
@app.get('/LED_OFF')
def LED_Off():
    if(status_On()=={"status": True}):
        gpio.LED_off()
        logging.info("LED OFF")
        return {"LED_OFF_INFO": "Turned off the LED"}
    else:
        return {"LED_ON_INFO": "LED already off"}