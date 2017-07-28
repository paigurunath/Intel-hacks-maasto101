  // initial data..

jQuery.extend({
   getValues: function(url){
      var result = null;
      $.ajax({
         url: url,
         type: 'get',
         async: false,
         success: function(data){ result = data },

      });
    return result;
   }
});


        

setInterval(function(){

z1 = $.parseJSON($.getValues('http://192.168.0.103:8080/getGyroAccel'));
	var data_z1 = {z: z1, type: 'surface'};
	
	// Plotting the surfaces..
	Plotly.newPlot('surfaceDiv', [data_z1]);

},5000);


