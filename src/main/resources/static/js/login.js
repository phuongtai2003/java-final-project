const navbarMenu = document.querySelector(".navbar .links");
const hamburgerBtn = document.querySelector(".hamburger-btn");
const hideMenuBtn = navbarMenu.querySelector(".close-btn");
const showPopupBtn = document.querySelector(".login-btn");
const showModalBtn = document.querySelector(".modal-btn");
const formPopup = document.querySelector(".form-popup");
const modalPopup = document.querySelector(".modal-popup");
const hidePopupBtn = formPopup.querySelector(".close-btn");
const hideModalPopup = modalPopup.querySelector(".close-btn");


hamburgerBtn.addEventListener("click", () => {
    navbarMenu.classList.toggle("show-menu");
});

hideMenuBtn.addEventListener("click", () =>  hamburgerBtn.click());

showPopupBtn.addEventListener("click", () => {
    document.body.classList.toggle("show-popup");
});

showModalBtn.addEventListener("click", () => {
    console.log("show modal Clicked");
    document.body.classList.toggle("show-modal");
});

hidePopupBtn.addEventListener("click", () => showPopupBtn.click());
hideModalPopup.addEventListener("click", () => showModalBtn.click());



/* Authentication Modal */
const section = document.querySelector("section"),
    closeBtn = document.querySelector(".close-authentication-btn");

const title = document.getElementById('authentication-title'),
    desc = document.getElementById('authentication-desc'),
    icon = document.getElementById('authentication-icon')

let timerInterval;

closeBtn.addEventListener("click", () =>{
        section.classList.remove("active");
        clearInterval(timerInterval);
    }
);


function openSuccessModal() {
    let counter = 4;
    icon.classList.remove("fa-xmark");
    icon.classList.add("fa-check");
    title.textContent = "SUCCESSFUL";
    desc.textContent = `Login success! Automatically redirects after 5 seconds.`;
    section.classList.add("active")

    timerInterval = setInterval(function () {
        if (counter > 0) {
            desc.textContent = `Login success! Automatically redirects after ${counter} seconds.`;
            counter--;
        } else {
            clearInterval(timerInterval);
            desc.textContent = 'Redirecting';
            window.location.href = "/dashboard"
        }
    }, 1000);
}

function openFailureModal() {
    let title = document.getElementById('authentication-title');
    let desc = document.getElementById('authentication-desc');
    icon.classList.remove("fa-check");
    icon.classList.add("fa-xmark");
    title.textContent = "UNSUCCESSFUL";
    desc.textContent = `Login failed! Please check your email or password.`;
    section.classList.add("active")
}
function checkLogin(){
    var username = $("#email").val();
    var password = $("#password").val();

    if (username === "user" && password === "pass") {
        openSuccessModal();
    } else {
        openFailureModal();
    }
}