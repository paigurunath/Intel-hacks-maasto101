function getNonZeroRandomNumber(){
    var random = Math.floor(Math.random()*199) - 99;
    if(random==0) return getNonZeroRandomNumber();
    return random;
}	
      var pubnub = new PubNub({
         publishKey: 'pub-c-d228b405-18a3-481f-a38e-04d2dd6d7c50',
         subscribeKey: 'sub-c-39610e6a-6bd9-11e7-8f73-02ee2ddab7fe'
      });

      var channel = 'pubnub-mapbox' + getNonZeroRandomNumber();

      eon.map({
        pubnub: pubnub,
        id: 'map',
        mbToken: 'pk.eyJ1IjoiaWFuamVubmluZ3MiLCJhIjoiZExwb0p5WSJ9.XLi48h-NOyJOCJuu1-h-Jg',
        mbId: 'ianjennings.l896mh2e',
        channels: [channel],
        connect: connect,
        options: {
          zoomAnimation: false,
        },
      });
     

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

      function connect() {	
        setInterval(function(){
          var z1 = $.parseJSON($.getValues('http://192.168.0.103:8080/getLatLong'));
          var point = {
             latlng: [z1[0], z1[1]]
          };
          var new_point = JSON.parse(JSON.stringify(point));

          new_point.latlng = [
            new_point.latlng[0],
            new_point.latlng[1]
          ];

          pubnub.publish({
            channel: channel,
            message: [new_point]
          });

        }, 1000);

      };