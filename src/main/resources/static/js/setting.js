const id = document.querySelector("#id");
const idCheck = document.querySelector("#idCheck");
const checkBtn = document.querySelector("#check");
const changeBtn = document.querySelector("#change");
let check = 0;

let xhr = new XMLHttpRequest();

checkBtn.addEventListener("click", function () {
    xhr.open("POST", "/setting/check", true);
    xhr.setRequestHeader("content-type", "text/plain");
    xhr.send(id.value);

    xhr.onreadystatechange = function () {
        idCheck.style.visibility = "visible";
        if (xhr.readyState === 4 && xhr.status === 200) {
            let data = xhr.responseText;
            if (data === "idNoExist") {
                idCheck.innerText = "사용가능한 아이디입니다"
                idCheck.style.color = "green";
                check = 2;
            } else {
                idCheck.innerText = "이미 존재하는 아이디입니다"
                idCheck.style.color = "red";
                check = 1;
            }
        }
    }
})

changeBtn.addEventListener("click", function () {
    if (check === 0) {
        swal({
            title: "중복확인을 해주세요",
            icon: "error",
        });
    }
    else if (check === 1) {
        swal({
            title: "이미 존재하는 아이디입니다",
            icon: "error",
        });
    }
    else {
        swal({
            title: "아이디가 변경되었습니다",
            icon: "success",
        });
    }
})

