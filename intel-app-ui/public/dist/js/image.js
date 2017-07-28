setInterval(function(){
 
 console.log("Image Removed");
 $("#orginialImg").prop("src","");
 $("#outputImg").prop("src","");
 setTimeout(function(){
console.log("Image Reloaded");
 $("#orginialImg").prop("src","http://192.168.0.103:3000/dist/img/pothole/pothole1.jpg?foo=" + new Date().getTime());
 $("#outputImg").prop("src","http://192.168.0.103:3000/dist/img/pothole/output.jpg?foo=" + new Date().getTime());

},1500);
 
},3000);