/**
 * Created by marcello on 07/05/15.

 */

window.onload = function() {
	"use strict";
    if (window.WebSocket !== undefined) {

        // Let us open a web socket
        var ws = new WebSocket("ws://localhost:8025/websocket/echo");

        ws.onopen = function() {
            // Web Socket is connected, send data using send()
        };

        ws.onmessage = function(evt) {
            	console.log(evt.data);
            	drawImage(evt.data);
        };

        ws.onclose = function() {
            // websocket is closed.
        };
            

    } else {
        // The browser doesn't support WebSocket
        window.alert("WebSocket NOT supported by your Browser!");
    }
};

function drawImage(imgString){
	"use strict";
    var img = document.getElementById("img");
    img.src = "data:image/png;base64,"+(imgString);
}