const me = document.querySelector("#myId").innerText;
const you = document.querySelector("#yourId").innerText;
const yourName = document.querySelector("#yourName").innerText;

let socket = new WebSocket("ws://localhost:8080/websocket");
let xhr = new XMLHttpRequest();

xhr.open("POST", "/chatting/findMsg", true);
xhr.setRequestHeader("content-type", "application/json");
const data = {
    "member1" : me,
    "member2" : you
};
xhr.send(JSON.stringify(data));

xhr.onreadystatechange = function () {
    if (xhr.readyState === 4 && xhr.status === 200) {
        let responseData = xhr.responseText;
        let messages = JSON.parse(responseData); // Assuming responseData is a JSON array of strings

        if (messages != null) {
            messages.forEach(function (msg) {
                if (msg.sender === me) {
                    let MsgArea = document.querySelector('#msgArea');
                    let newMsg = document.createElement('div');
                    let hours = Number(msg.dateTime.substring(11, 13));
                    var am_pm = "오전";

                    if (hours > 12) {
                        am_pm = "오후";
                        hours -= 12;
                    }
                    var time = am_pm + " " + hours + ':' + msg.dateTime.substring(14, 16);

                    // 자신의 메시지인 경우
                    newMsg.innerHTML = `<div class="me-chat">
                                <div class="me-chat-col">
                                    <span class="balloon" id="meMsgArea">${msg.content}</span>
                                </div>
                                <time>${time}</time>
                            </div>`;
                    newMsg.classList.add('balloon');
                    MsgArea.appendChild(newMsg);
                } else {
                    let MsgArea = document.querySelector('#msgArea');
                    let newMsg = document.createElement('div');
                    let hours = Number(msg.dateTime.substring(11, 13));
                    var am_pm = "오전";

                    if (hours > 12) {
                        am_pm = "오후";
                        hours -= 12;
                    }
                    var time = am_pm + " " + hours + ':' + msg.dateTime.substring(14, 16);

                    newMsg.classList.add('msg');
                    newMsg.classList.add('friend-chat-col');
                    newMsg.innerHTML = `<div class="friend-chat">
                                <img class="profile-img" src="/userImg/${you}.png" alt="프로필"  onerror="this.onerror=null; this.src='/img/profile.png'">
                                <div class="friend-chat-col">
                                    <div class="profile-name">${yourName}</div>
                                    <span class="balloon" id="youMsgArea">${msg.content}</span>
                                </div>
                                <time>${time}</time>
                            </div>`;
                    MsgArea.appendChild(newMsg);
                }
            });
        }
    }

}

socket.onopen = function (e) {
    console.log('open server!')
    register();
};

function register() { //메시지 수신을 위한 서버에 id 등록하기
    var msg = {
        type: "register", //메시지 구분하는 구분자 - 상대방 아이디와 메시지 포함해서 보냄
        userid: me,
    };
    socket.send(JSON.stringify(msg));
}

socket.onerror = function (e) {
    console.log(e);
};

function sendMsg() {
    let MsgArea = document.querySelector('#msgArea');
    let newMsg = document.createElement('div');
    let content = document.querySelector('#content').value;

    var msg = {
        type: "chat", //메시지 구분하는 구분자 - 상대방 아이디와 메시지 포함해서 보기
        target: you,
        message: content,
        sender: me,
    };

    socket.send(JSON.stringify(msg));
    document.querySelector('#content').value = "";

    var timeString = getTime();

    // 자신의 메시지인 경우
    newMsg.innerHTML = `<div class="me-chat">
                        <div class="me-chat-col">
                            <span class="balloon" id="meMsgArea">${content}</span>
                        </div>
                        <time>${timeString}</time>
                    </div>`;
    newMsg.classList.add('balloon');
    MsgArea.appendChild(newMsg);

    xhr.open("POST", "/chatting/saveMsg", true);
    xhr.setRequestHeader("content-type", "application/json");
    const data = {
        "sender": me,
        "receiver": you,
        "content": content,
    };
    xhr.send(JSON.stringify(data));
}

socket.onmessage = function (e) {
    let MsgArea = document.querySelector('#msgArea');
    let newMsg = document.createElement('div');

    var timeString = getTime();

    newMsg.classList.add('msg');
    newMsg.classList.add('friend-chat-col');
    const content = e.data;
    newMsg.innerHTML = `<div class="friend-chat">
                        <img class="profile-img" src="/userImg/${you}.png" alt="프로필"  onerror="this.onerror=null; this.src='/img/profile.png'">
                        <div class="friend-chat-col">
                            <div class="profile-name">${yourName}</div>
                            <span class="balloon" id="youMsgArea">${content}</span>
                        </div>
                        <time>${timeString}</time>
                    </div>`;
    MsgArea.appendChild(newMsg);
};

socket.onclose = function (e) {
    console.log(e);
}

function getTime() {
    var today = new Date();
    var hours = ('0' + today.getHours()).slice(-2);
    var minutes = ('0' + today.getMinutes()).slice(-2);
    var am_pm = "오전";
    if (hours > 12) {
        am_pm = "오후";
        hours -= 12;
    }
    var timeString = am_pm + " " + hours + ':' + minutes;

    return timeString;
}
