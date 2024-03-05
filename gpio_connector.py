import OPi.GPIO as GPIO

PIN=7
GPIO.setmode(GPIO.BOARD)
GPIO.setup(PIN, GPIO.OUT)
GPIO.output(PIN, GPIO.LOW)

class GpioConnector():

    def LED_on(self):
        GPIO.output(PIN, GPIO.HIGH)

    def LED_off(self):
        GPIO.output(PIN, GPIO.LOW)
    