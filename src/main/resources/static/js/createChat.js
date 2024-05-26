var modal = document.getElementById("myModal");
var close = document.getElementsByClassName("close")[0];
var sirens = document.querySelectorAll(".siren");

sirens.forEach(function(siren) {
    siren.onclick = function(event) {
        event.preventDefault();
        modal.style.display = "block";
    }
});

close.onclick = function() {
    modal.style.display = "none";
    abuseCheckbox.checked = false;
    sexualCheckbox.checked = false;
    insultCheckbox.checked = false;
    text.value = "";
}

window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
        abuseCheckbox.checked = false;
        sexualCheckbox.checked = false;
        insultCheckbox.checked = false;
        text.value = "";
    }
}

var id = document.querySelector("#userId").innerText;
var target = document.querySelector("#target").innerText;
var text = document.querySelector("#text");
var abuseCheckbox = document.getElementById("abuseCheckbox");
var sexualCheckbox = document.getElementById("sexualCheckbox");
var insultCheckbox = document.getElementById("insultCheckbox");
var declaBtn = document.querySelector(".submit-btn");
var abuse = "";
var sexual = "";
var insult = "";

let xhr = new XMLHttpRequest();

declaBtn.addEventListener("click", function () {
    Swal.fire({
        title: '신고하시겠습니까?',
        text: "무분별한 신고는 제재를 받을 수 있습니다",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: '신고',
        cancelButtonText: '취소'
    }).then((result) => {
        if (result.isConfirmed) {
            if (abuseCheckbox.checked) {
                abuse = "checked";
            }
            if (sexualCheckbox.checked) {
                sexual = "checked";
            }
            if (insultCheckbox.checked) {
                insult = "checked";
            }

            xhr.open("POST", "/createChat/Declaration", true);
            xhr.setRequestHeader("content-type", "application/json");
            var data = {
                "신고자" : id,
                "신고대상" : target,
                "abuse" : abuse,
                "sexual" : sexual,
                "insult" : insult,
                "content" : text.value
            }

            xhr.send(JSON.stringify(data));

            Swal.fire({
                title: "신고가 정상적으로 \n접수되었습니다",
                icon: "success",
            });
            modal.style.display = "none";
            abuseCheckbox.checked = false;
            sexualCheckbox.checked = false;
            insultCheckbox.checked = false;
            text.value = "";
        }
    })
})