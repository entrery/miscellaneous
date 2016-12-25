(function() {
  var App;
  App = {};
  /*
  	Init 
  */
  App.init = function() {
	  var hostname = window.location.hostname;
	  
	  App.chatSocket = new WebSocket("ws://" + hostname + ":8080/websockets.drawing/chat");
	  App.chatSocket.onmessage = function(event) {		  
		  $('#chatbox')
		    .append($('<div>'+ event.data + '</div>')
		        .addClass("msgln"))
	  };  
	  
	  $("#submitmsg").click(function() {
		  App.chatSocket.send($("#usermsg").val());
		  $("#usermsg").val('')
	  });
	
	
    App.canvas = document.createElement('canvas');
    App.canvas.height = 400;
    App.canvas.width = 800;
    document.getElementsByTagName('article')[0].appendChild(App.canvas);
    App.ctx = App.canvas.getContext("2d");
    App.ctx.fillStyle = "solid";
    App.ctx.lineWidth = 5;
    App.ctx.lineCap = "round";
    
    
    // Create a new instance of the websocket
    App.socket = new WebSocket("ws://" + hostname + ":8080/websockets.drawing/echo");
    
    App.socket.onmessage = function(event) {
      var data = JSON.parse(event.data);
      return App.draw(data.x, data.y, data.type);
    };
    App.draw = function(x, y, type) {
    	
  	  var pickRandomColor = function getRandomColor() {
		  var letters = '0123456789ABCDEF'.split('');
		  var color = '#';
		  for (var i = 0; i < 6; i++ ) {
			  color += letters[Math.floor(Math.random() * 16)];
		  }
		  return color;
	  }; 
	  
      App.ctx.strokeStyle = pickRandomColor();
      
      if (type === "dragstart") {
        App.ctx.beginPath();
        return App.ctx.moveTo(x, y);
      } else if (type === "drag") {
        App.ctx.lineTo(x, y);
        return App.ctx.stroke();
      } else {
        return App.ctx.closePath();
      }
    };
  };
  /*
  	Draw Events
  */
  $('canvas').live('drag dragstart dragend', function(e) {
    var offset, type, x, y;
    type = e.handleObj.type;
    offset = $(this).offset();
    e.offsetX = e.layerX;
    e.offsetY = e.layerY;
    x = e.offsetX;
    y = e.offsetY;
    App.draw(x, y, type);
    App.socket.send(JSON.stringify({
      x: x,
      y: y,
      type: type
    }));
  });
  $(function() {
    return App.init();
  });
  

  
  
  
  
  
  
  
}).call(this);