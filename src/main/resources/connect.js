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
            ws.send("Message to send");
        };

        ws.onmessage = function(evt) {
            var received_msg = evt.data;
            window.alert(received_msg);
        };

        ws.onclose = function() {
            // websocket is closed.
            window.alert("Connection is closed...");
        };

    } else {
        // The browser doesn't support WebSocket
        window.alert("WebSocket NOT supported by your Browser!");
    }
};