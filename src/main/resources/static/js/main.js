'use strict';

var usernamePage = document.querySelector('#username-page');
var chatPage = document.querySelector('#chat-page');
var usernameForm = document.querySelector('#usernameForm');
var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');
var connectingElement = document.querySelector('.connecting');

var newRoomForm = document.querySelector('#newRoomForm');

var stompClient = null;
var username = null;
var roomId = null;

var chatRooms = [];

var colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

getChatRooms();

window.addEventListener('beforeunload', disconnect);


function disconnect(event) {
	leaveRoom(event);
	stompClient.disconnect(function(frame) {
 	});
}

function connect(event) {
    username = document.querySelector('#name').value.trim();
	roomId = document.querySelector('#roomId').value.trim();
	
    if(username) {
        usernamePage.classList.add('hidden');
        chatPage.classList.remove('hidden');

        var socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);

        stompClient.connect({}, onConnected, onError);
    }
    event.preventDefault();
}

function newRoom(event) {
	console.log("new Room");
	var newRoomId = document.querySelector('#roomId-new').value.trim();
	if(newRoomId) {
		$.ajax({ 
		type: "post",
		url: "/chat/room",
		data: {
			name: newRoomId,
			employeeId: 1,
			memberId: 1
		},
		success: function(data){
			console.log("newRoom " + data);
		}
	})
	}
}

function leaveRoom() {
    if(stompClient) {
        var chatMessage = {
            sender: username,
            content: 'leave room',
            type: 'LEAVE',
            roomId: roomId,
            deviceToken : '232'
        };
        stompClient.send("/pub/chat/message", {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
    event.preventDefault();
}

function getChatRooms() {
/*	$.get("/chat/rooms", function(data){
		if (data.responseCode == "SUCCESS" && data.data == 0) {
			chatRooms = data.data;
			alert(chatRooms);
		} else {
			
		}
		
	}, function(xhr, ajaxOptions, thrownError){
		alert("get list fail");        
	});*/
	
	$.ajax({ 
		type: "get",
		url: "/chat/rooms",
		success: function(data){
			console.log("roomList " + data);
		}
	})
}


function onConnected() {
    // Subscribe to the Public Topic
    //stompClient.subscribe('/topic/public', onMessageReceived);
    stompClient.subscribe('/sub/chat/room/'+roomId, onMessageReceived);

	var chatMessage = {
            sender: username,
            content: 'join room',
            type: 'JOIN',
            roomId: roomId,
            deviceToken : '232'
        };
    // Tell your username to the server
    stompClient.send(
		"/pub/chat/message",
        {},
       	JSON.stringify(chatMessage)
    )

    connectingElement.classList.add('hidden');
}


function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}


function sendMessage(event) {
	console.log("!!!!!!!! sendMessage");
    var messageContent = messageInput.value.trim();
    console.log("!!!!!!!! sendMessage. msg=" + messageContent);
    if(messageContent && stompClient) {
        var chatMessage = {
            sender: username,
            content: messageInput.value,
            type: 'CHAT',
            roomId: roomId,
            deviceToken : '232'
        };
        console.log("!!!!!!!! sendMessage. chatMessage=" + chatMessage);
        stompClient.send("/pub/chat/message", {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    } else {
		console.log("!!!!!!!! sendMessage error");
	}
    event.preventDefault();
}


function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);

    var messageElement = document.createElement('li');

    if(message.type === 'JOIN') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' joined!';
    } else if (message.type === 'LEAVE') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' left!';
    } else {
        messageElement.classList.add('chat-message');

        var avatarElement = document.createElement('i');
        var avatarText = document.createTextNode(message.sender[0]);
        avatarElement.appendChild(avatarText);
        avatarElement.style['background-color'] = getAvatarColor(message.sender);

        messageElement.appendChild(avatarElement);

        var usernameElement = document.createElement('span');
        var usernameText = document.createTextNode(message.sender);
        usernameElement.appendChild(usernameText);
        messageElement.appendChild(usernameElement);
    }

    var textElement = document.createElement('p');
    var messageText = document.createTextNode(message.content);
    textElement.appendChild(messageText);

    messageElement.appendChild(textElement);

    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}


function getAvatarColor(messageSender) {
    var hash = 0;
    for (var i = 0; i < messageSender.length; i++) {
        hash = 31 * hash + messageSender.charCodeAt(i);
    }
    var index = Math.abs(hash % colors.length);
    return colors[index];
}

newRoomForm.addEventListener('submit', newRoom, true)
usernameForm.addEventListener('submit', connect, true)
messageForm.addEventListener('submit', sendMessage, true)