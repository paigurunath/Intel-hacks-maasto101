# Maasto-101 Intel Hacks

Pre-requisites to be present on Raspberry Pi 3 installed

a) Mysql database
b) jdk 8 or above installed
c) Python OpenCV libraries setup
d) Install Bluetooth library's

Steps to follow to set up Maasto-101

Step 1 : Refer wiring diagram from the below path.

cd Intel-hacks-maasto101/sketch-diagram/
open Maasto-101.jpg

Step 2 : Save the bleconnect.ino to Arduino101.

Step 3 : Execute maasto.sql query file in Raspberry PI.

Step 4 : Execute Spring boot app using the below command.

cd Intel-hacks-maasto101/spring-boot-app/target/
java -jar BleConnect-0.0.1-SNAPSHOT.jar

Step 5 : Start Node.js web app using below command.

cd Intel-hacks-maasto101/intel-app-ui/
node index.js

Step 6 : Start ble-connect.js using below command

cd Intel-hacks-maasto101/ble-connect/
node ble-connect.js

Step 7 : Start python scripts for collecting GPS data, image capture through web-cam and process those captured images.

cd Intel-hacks-maasto101/python-scripts/
python gps-data-reciever.py
python image-capture-cam.py
python image-process.py

Mounting the set-up on top of a toy car and Enjoy!!!.
