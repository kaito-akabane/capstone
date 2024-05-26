const menuLink = document.getElementById("menu-link");
const menuContent = document.querySelector("#menuContent");
const chatLink = document.getElementById("chat-link");
const chatContent = document.querySelector("#chatContent");
const acLink = document.getElementById("ac-link");
const acContent = document.querySelector("#acContent");
const navLinks = document.querySelectorAll(".nav-link");
const itemLinks = document.querySelectorAll(".list-group-item");

    navLinks.forEach(link => {
      link.addEventListener("mouseover", () => {
        link.classList.add("active");
      });

      link.addEventListener("mouseout", () => {
        link.classList.remove("active");
      });
    });

    itemLinks.forEach(link => {
      link.addEventListener("mouseover", () => {
        link.classList.add("active");
      });

      link.addEventListener("mouseout", () => {
        link.classList.remove("active");
      });
    });

    menuLink.addEventListener("click", (event) => {
      if (menuContent.style.display === "none") {
        menuContent.style.display = "block"; // Show the menuContent content
        chatContent.style.display = "none"; // Hide the chatContent content
        acContent.style.display = "none"; // Hide the acContent content
      } else {
        menuContent.style.display = "none"; // Hide the menuContent content
      }
    });

    chatLink.addEventListener("click", (event) => {
      if (chatContent.style.display === "none") {
        chatContent.style.display = "block"; // Show the chatContent content
        menuContent.style.display = "none"; // Hide the menuContent content
        acContent.style.display = "none"; // Hide the acContent content
      } else {
        chatContent.style.display = "none"; // Hide the chatContent content
      }
    });

    acLink.addEventListener("click", (event) => {
      if (acContent.style.display === "none") {
        acContent.style.display = 'block'; // Show the acContent content
        menuContent.style.display = "none"; // Hide the menuContent content
        chatContent.style.display = "none"; // Hide the chatContent content
      } else {
        acContent.style.display = "none"; // Hide the acContent content
      }
    });
