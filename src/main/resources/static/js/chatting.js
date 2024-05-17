
    const roomNum = document.querySelector("#title").innerText;
    let me = document.querySelector("#myId").innerText;
    let socket = new WebSocket("ws://localhost:8080/websocket");

    socket.onopen = function (e) {
    console.log('open server!')
    register();
};

    socket.onerror = function (e) {
    console.log(e);
};

    socket.onmessage = function (e) {
    let youMsgArea = document.querySelector('#youMsgArea');
    let meMsgArea = document.querySelector('#meMsgArea');
    let newMsg = document.createElement('div');

    newMsg.classList.add('msg');
    // 메시지의 내용을 가져옴
    let messageContent = e.data;

    // 상대방의 메시지인 경우
    newMsg.innerText = messageContent;
    youMsgArea.appendChild(newMsg);
};

    socket.onclose = function (e) {
    console.log(e);
}

    function register() { //메시지 수신을 위한 서버에 id 등록하기
    var msg = {
    type : "register", //메시지 구분하는 구분자 - 상대방 아이디와 메시지 포함해서 보냄
    userid : me
};
    socket.send(JSON.stringify(msg));
}

    function sendMsg() {
    const youName = document.querySelector("#yourId").innerText;
    let content = document.querySelector('#content').value;
    let youMsgArea = document.querySelector('#youMsgArea');
    let meMsgArea = document.querySelector('#meMsgArea');
    let newMsg = document.createElement('div');

    var msg = {
    type : "chat", //메시지 구분하는 구분자 - 상대방 아이디와 메시지 포함해서 보냄
    target : youName,
    message : content
};

    socket.send(JSON.stringify(msg));
    document.querySelector('#content').value = "";

    // 자신의 메시지인 경우
    newMsg.innerText = content;
    meMsgArea.appendChild(newMsg);
}
