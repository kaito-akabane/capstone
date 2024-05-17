const id = document.querySelector(".id");
const btn = document.querySelector(".confirmBtn");

let xhr = new XMLHttpRequest();

btn.addEventListener("click", function () {
    xhr.open("POST", "/findPw", true);
    xhr.setRequestHeader("content-type", "text/plain");
    xhr.send(id.value);

    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            let data = xhr.responseText;
            if (data === "wrong") {
                swal({
                    title:"이런... 존재하지 않는 아이디입니다",
                    icon: "error",
                });
            }
            else {
                swal({
                    title: "비밀번호는 " + data + "입니다",
                    icon: "success",
                });
            }
        }
    }
    event.preventDefault();
})