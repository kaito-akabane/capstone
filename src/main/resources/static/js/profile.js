const id = document.querySelector("#userId").innerText;
const input = document.querySelector("#imageInput"); // 파일 입력(input) 요소 가져오기
const btn = document.querySelector("#save");
var image;

let xhr = new XMLHttpRequest();

function showImage(event) {
    var input = event.target;
    var reader = new FileReader();

    reader.onload = function() {
        var dataURL = reader.result;
        var preview = document.getElementById('preview');
        preview.src = dataURL;
        preview.style.display = 'block';
    };
    reader.readAsDataURL(input.files[0]);

    const file = input.files[0]; // 파일 객체 가져오기
    if (file) {
        image = URL.createObjectURL(file); // 파일 객체를 이용하여 이미지 경로 생성
    }
}
