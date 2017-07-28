var noble = require('noble');

//Example POST method invocation 
var Client = require('node-rest-client').Client;
var client = new Client();

// Search only for the Service UUID of the device (remove dashes)
var BLECONNECT_SERVICE_UUID = '0e88c8c699134578880bb84ff37583d7';

var uidlist = ['10ed1b57f6674443bc794729a5792956','40a992d85c0b45b19fbd2728fde16ad6']

// start scanning when bluetooth is powered on
noble.on('stateChange', function(state) {
  if(state === 'poweredOn') {
    console.log('BLE scan started!!!')
    noble.startScanning([BLECONNECT_SERVICE_UUID], false);
  }
  else {
    console.log('Cannot scan... state is not poweredOn')
    noble.stopScanning();
  }
});

try {
	// Discover the peripheral's IMU service and corresponding characteristics
	// Then, emit each data point on the socket stream
	noble.on('discover', function(peripheral) {
	//console.log("peripheral :" + peripheral);
	  peripheral.connect(function(error) {
	    //console.log('Connected to peripheral: ' + peripheral.uuid);
	    peripheral.discoverServices([BLECONNECT_SERVICE_UUID], function(error, services) {
	      var bleService = services[0];
	      // console.log('Discovered IMU service');
		// console.log("bleService : " + bleService);
		
	      bleService.discoverCharacteristics(null, function(error, characteristics) {

		    characteristics.forEach(function(characteristic) {
		    	
		    	if(characteristic.uuid == uidlist[0]) {
		    		// console.log("characteristic connected : " + characteristic.uuid);
				
					characteristic.on('read', function(data, isNotification) {

					setInterval(function(){
					  console.log( "Ax : " + data.readFloatLE(0) + "Ay : " + data.readFloatLE(4) + "Az : " + data.readFloatLE(8));

					  var insertAxcel = {
					  	accelx : data.readFloatLE(0),
					  	accely : data.readFloatLE(4),
					  	accelz : data.readFloatLE(8)
					  };

					  console.log(insertAxcel);

					  // set content-type header and data as json in args parameter 
						var args = {
						    data: insertAxcel,
						    headers: { "Content-Type": "application/json" }
						};
 
						client.post("http://localhost:8080/accel", args, function (data, response) {
						});
						}, 5000);
					  
					});
			        // to enable notify
			        characteristic.subscribe(function(error) {
			          console.log('AX notification on');
			    	});

		    	} else {
		    		console.log("characteristic connected : " + characteristic.uuid);
				
					characteristic.on('read', function(data, isNotification) {

						setInterval(function(){
						  console.log( "Gx : " + data.readFloatLE(0) + "Gy : " + data.readFloatLE(4) + "Gz : " + data.readFloatLE(8));

						  var insertAxcel = {
						  	gyrox : data.readFloatLE(0),
						  	gyroy : data.readFloatLE(4),
						  	gyroz : data.readFloatLE(8)
						  };

						  console.log(insertAxcel);

						  // set content-type header and data as json in args parameter 
							var args = {
							    data: insertAxcel,
							    headers: { "Content-Type": "application/json" }
							};
	 
							client.post("http://localhost:8080/gyro", args, function (data, response) {
							});
						}, 5000);
					});
			        // to enable notify
			        characteristic.subscribe(function(error) {
			          console.log('GX notification on');
			    	});
          	});
	      });
	    });
	  });
	});
} catch(Err) {
	console.log("Error in discover BLE :" + Err);
}

