#include "CurieIMU.h"
#include <CurieBLE.h>


BLEPeripheral blePeripheral;

//define ble service using custom UUID
BLEService bleconnectService("0e88c8c6-9913-4578-880b-b84ff37583d7"); 

//define characteristics
BLECharacteristic bleconnectAccCharacteristic("10ed1b57-f667-4443-bc79-4729a5792956", BLERead | BLENotify, 12 );
BLECharacteristic bleconnectGyroCharacteristic("40a992d8-5c0b-45b1-9fbd-2728fde16ad6", BLERead | BLENotify, 12 );

//define descriptors
BLEDescriptor bleconnectAccDescriptor("2902", "block");
BLEDescriptor bleconnectGyroDescriptor("2902", "block");

 union 
 {
  float a[3];
  unsigned char bytes[12];      
 } accData;

 union 
 {
  float g[3];
  unsigned char bytes[12];         
 } gyroData;
 

void setup() {
  Serial.begin(9600); 
  while (!Serial);    
  Serial.println("Arduino101 Ble connect started");
  Serial.println("Serial port working on 9600");
  
  CurieIMU.begin();
  CurieIMU.setAccelerometerRange(2);
  CurieIMU.setGyroRange(250);

  blePeripheral.setLocalName("bleconnect");
  blePeripheral.setAdvertisedServiceUuid(bleconnectService.uuid()); 
  blePeripheral.addAttribute(bleconnectService);   
  blePeripheral.addAttribute(bleconnectAccCharacteristic);
  blePeripheral.addAttribute(bleconnectAccDescriptor);
  blePeripheral.addAttribute(bleconnectGyroCharacteristic);
  blePeripheral.addAttribute(bleconnectGyroDescriptor);
 
  const unsigned char initializerAcc[12] = { 0,0,0,0,0,0,0,0,0,0,0,0 };
  const unsigned char initializerGyro[12] = { 0,0,0,0,0,0,0,0,0,0,0,0 };
 
  bleconnectAccCharacteristic.setValue( initializerAcc, 12);
  bleconnectGyroCharacteristic.setValue( initializerGyro, 12 );
  
  blePeripheral.begin();
}

void loop() {
  
  int axRaw, ayRaw, azRaw;       
  int gxRaw, gyRaw, gzRaw;       

  BLECentral central = blePeripheral.central();
  if (central) {
    
    Serial.print("Connected to central: "); Serial.println(central.address());
 
    while (central.connected()) {
      
      CurieIMU.readAccelerometer(axRaw, ayRaw, azRaw);

      accData.a[0] = convertRawAcceleration(axRaw);
      accData.a[1] = convertRawAcceleration(ayRaw);
      accData.a[2] = convertRawAcceleration(azRaw);
      
      // read raw gyro measurements from device
     CurieIMU.readGyro(gxRaw, gyRaw, gzRaw);

     gyroData.g[0] = convertRawGyro(gxRaw);
     gyroData.g[1] = convertRawGyro(gyRaw);
     gyroData.g[2] = convertRawGyro(gzRaw);
     
     Serial.print("{\"accelx\":"); Serial.print(accData.a[0]); Serial.print(",\"accely\":"); Serial.print(accData.a[1]); Serial.print(",\"accelz\":"); Serial.print(accData.a[2]); Serial.print("}");Serial.println();
     Serial.print("{\"gyrox\":"); Serial.print(gyroData.g[0]); Serial.print(",\"gyroy\":"); Serial.print(gyroData.g[1]); Serial.print(",\"gyroz\":"); Serial.print(gyroData.g[2]); Serial.print("}");Serial.println();
     
     unsigned char *acc = (unsigned char *)&accData;
     unsigned char *gyro = (unsigned char *)&gyroData;

     bleconnectAccCharacteristic.setValue( acc, 12 );
     bleconnectGyroCharacteristic.setValue( gyro, 12 );

    } 
  } 
} 

float convertRawAcceleration(int aRaw) {

  float a = (aRaw * 2.0) / 32768.0;

  return a;
}

float convertRawGyro(int gRaw) {

  float g = (gRaw * 250.0) / 32768.0;

  return g;
}
