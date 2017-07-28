import serial,time
import pynmea2
import os
import signal
import sys
import json, urllib2
from threading import Timer
from serial import SerialException

ser = 0
latitude=''
longitude=''
prvLatitude=18.999700
prvLongitude=73.126365
print("\nTurning on gps sensor...\n")
def init_serial():
     global ser
     ser = serial.Serial()
     ser.baudrate = 9600
     ser.port = '/dev/serial0'
        
     ser.timeout = 100
     ser.open() 

     if ser.isOpen()==False:
          print 'Serial port not opened for ' + ser.portstr
     
          
def close(signal, frame):
        print("\nTurning off gps sensor...\n")
        sys.exit(0)
        
signal.signal(signal.SIGINT, close)
        
def current_milli_time():

        current_milli_time = lambda: int(round(time.time() * 1000))
        return current_milli_time();
     

init_serial()

print '1'
while True:
    try:
        recv=ser.readline()
        
        print 'recv',recv
        
        if recv.find('$GPGGA')!=-1:
            msg=pynmea2.parse(recv)
            print 'pynmea msg', msg
            print 'msg.latitude', msg.latitude
            latitude=msg.latitude
            longitude=msg.longitude
            

            if latitude!=0.0:
                 prvLatitude = latitude
                 prvLongitude = longitude

            if latitude==0.0:
                 latitude = prvLatitude
                 longitude = prvLongitude

            print 'GPS coordinates :',latitude,longitude

            data = {
                'latval': latitude,
                'longval': longitude
            }

            req = urllib2.Request('http://localhost:8080/saveLatLong')
            req.add_header('Content-Type', 'application/json')

            response = urllib2.urlopen(req, json.dumps(data))
                
            time.sleep(3)
            


    except (SerialException, OSError):
        init_serial()
        print 'exp'
        time.sleep(0.5)

