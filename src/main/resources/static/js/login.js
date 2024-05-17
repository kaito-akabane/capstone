const id = document.querySelector(".id");
const pw = document.querySelector(".pw");
const idCheck = document.querySelector(".idCheck");
const pwCheck = document.querySelector(".pwCheck");
const button = document.querySelector(".confirmBtn");

let xhr = new XMLHttpRequest();

button.addEventListener("click", function () {
    xhr.open("POST", "/login", true);
    xhr.setRequestHeader("content-type", "application/json");
    const data = {
        "id" : id.value,
        "pw" : pw.value
    };
    xhr.send(JSON.stringify(data));

    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            let data = xhr.responseText;
            if (data === "idNoExist") {
                id.style.border = "2px solid red"
                idCheck.innerText = "존재하지 않는 아이디입니다";
                idCheck.style.color = "red";
                pw.style.border = "1px solid #dbdbdb"
                pwCheck.innerText = " ";
            } else if (data === "pwFailed") {
                pw.style.border = "2px solid red"
                pwCheck.innerText = "잘못된 비밀번호입니다";
                pwCheck.style.color = "red";
                id.style.border = "1px solid #dbdbdb";
                idCheck.innerText = " ";
            } else {
                let f= document.createElement('form');
                f.setAttribute('method','post');
                f.setAttribute('action','home');
                let obj= document.createElement('input');
                obj.setAttribute('type', 'hidden');
                obj.setAttribute('name', 'id');
                obj.setAttribute('value', id.value);
                f.appendChild(obj);
                document.body.appendChild(f);
                f.submit();
            }
        }
    }
    event.preventDefault();
});
