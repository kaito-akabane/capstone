const id = document.querySelector(".id");
const pw1 = document.querySelector(".pw1");
const pw2 = document.querySelector(".pw2");
const idCheck = document.querySelector(".idCheck");
const pwCheck1 = document.querySelector(".pwCheck1");
const pwCheck2 = document.querySelector(".pwCheck2");
let i = 0;
let p1 = 0;
let p2 = 0;

let xhr = new XMLHttpRequest();

id.addEventListener("keyup", function () {
    xhr.open("POST", "/duplicate", true);
    xhr.setRequestHeader("content-type", "text/plain");
    xhr.send(id.value);

    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            let data = xhr.responseText;
            if (data === "available") {
                id.style.border = "2px solid green";
                idCheck.innerText = "사용가능한 아이디입니다";
                idCheck.style.color = "green";
                i = 1;
            } else {
                id.style.border = "2px solid red";
                idCheck.innerText = "중복된 아이디입니다";
                idCheck.style.color = "red";
                i = 0
            }
        }
    }
})

pw1.addEventListener("keyup", function () {
    if (!/^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*?_]).{4,20}$/.test(pw1.value)) {
        pwCheck1.innerText = "비밀번호 입력 예시 : yju123@ | 4~20자 사이";
        pwCheck1.style.size = "5px";
        pwCheck1.style.color = "red";
        pw1.style.border = "2px solid red";
        p1 = 0;
    }
    else {
        pwCheck1.innerText = "비밀번호가 올바르게 구성되었습니다";
        pwCheck1.style.color = "green";
        pw1.style.border = "2px solid green";
        p1 = 1;
    }

    if (pw1.value !== pw2.value) {
        pwCheck2.innerText = "비밀번호가 일치하지 않습니다";
        pwCheck2.style.color = "red";
        pw2.style.border = "2px solid red";
        p2 = 0;
    }
    else {
        pwCheck2.innerText = "비밀번호가 일치합니다";
        pwCheck2.style.color = "green";
        pw2.style.border = "2px solid green";
        p2 = 1;
    }
})

pw2.addEventListener("keyup", function () {
    if (pw1.value !== pw2.value) {
        pwCheck2.innerText = "비밀번호가 일치하지 않습니다";
        pwCheck2.style.color = "red";
        pw2.style.border = "2px solid red";
        p2 = 0;
    }
    else {
        pwCheck2.innerText = "비밀번호가 일치합니다";
        pwCheck2.style.color = "green";
        pw2.style.border = "2px solid green";
        p2 = 1;
    }
})

function check() {
    if (i && p1 && p2) {
        i = 0; p1 = 0; p2 = 0;
        return true;
    }
    return false;
}