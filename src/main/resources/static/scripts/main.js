import axios from 'https://cdn.jsdelivr.net/npm/axios@1.3.5/+esm';

axios.defaults.headers.post["Content-Type"] = 'application/json'

const tokenPages= ['/library.html', '/student-info.html'];

document.addEventListener("DOMContentLoaded", function() {
    // Add event listener to all navigation links
    const navLinks = document.querySelectorAll('.nav-link');
    navLinks.forEach(link => {
        link.addEventListener('click', function(event) {
            event.preventDefault();
            window.location.href = this.getAttribute('href');
        });
    });

    // Check if on register page and add event listener
    const registerForm = document.getElementById("register-form");
    if (registerForm) {
        registerForm.addEventListener("submit", function(event) {
            event.preventDefault();
            register();
        });
    }

    // Check if on login page and add event listeners
    const loginForm = document.getElementById("login-form");
    if (loginForm) {
        loginForm.addEventListener('submit', function(event){
            event.preventDefault();
            signIn();
        });

        const registrationButton = document.getElementById("registration-button");
        registrationButton.addEventListener('click', function(event){
            event.preventDefault();
            window.location.href='/sing-up.html'
        });
    }

    // Check if on authenticated page and fetch student info
    if (isRequiredAuthPage() && isAuthenticated()) {
        const expectedURL = '/student-info.html';
        if(window.location.pathname === expectedURL){

            const token = localStorage.getItem('token');
            const nameSpan = document.querySelector('#name-span');
            const emailSpan = document.querySelector('#email-span');
            const rollNumberSpan = document.querySelector('#rollNumber-span');

            axios.get('http://localhost:8080/api/v1/student/info', {
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            })
                .then(response => {
                    nameSpan.textContent = response.data.name;
                    emailSpan.textContent = response.data.email;
                    rollNumberSpan.textContent = response.data.rollNumber;
                })
                .catch(error => {
                    console.error("Error fetching student info:", error);
                });
        }
    } else if (isRequiredAuthPage()) {
        console.log(`Authentication required for ${window.location.pathname}`);
        window.location.href='/login.html';
    }
});

function signIn() {
    const username = document.querySelector('input[name="username"]').value;
    const password = document.querySelector('input[name="password"]').value;

    const payload = {
        username: username,
        password: password
    };

    axios.post('http://localhost:8080/api/v1/auth/login', payload)
        .then(response => {
            const token = response.data.token;
            localStorage.setItem('token', token);
            window.location.href = '/library.html';
        })
        .catch(error => {
            console.error(`Error occurred during login: ${error}`);
        });
}

function register(){
    const name = document.getElementById("name").value;
    const email = document.getElementById("email").value;
    const rollNumber = document.getElementById("rollNumber").value;
    const age = document.getElementById("age").value;
    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    const payload = {
        name: name,
        email: email,
        rollNumber: rollNumber,
        age: age,
        username: username,
        password: password
    };

    axios.post('http://localhost:8080/api/v1/auth/register', payload)
        .then(response => {
            console.log(`User information sent: ${response.data}`);
            window.location.href = '/registration-success.html'
        })
        .catch(error => {
            console.error(`An error occurred: ${error}`);
        });
}

function isAuthenticated(){
    const token = localStorage.getItem('token');
    return token !== null;
}

function isRequiredAuthPage(){
    const currentPage = getCurrentPageURL();
    return tokenPages.includes(currentPage);
}

function getCurrentPageURL() {
    return '/' + window.location.pathname.split('/').pop();
}

function generateCardElements(books) {
    const container = document.querySelector('.row-cols-2'); // Assuming this is the container for card elements
    books.forEach(book => {
        const col = document.createElement('div');
        col.classList.add('col', 'mb-5');

        const card = document.createElement('div');
        card.classList.add('card', 'h-100');

        const cardImg = document.createElement('img');
        cardImg.classList.add('card-img-top');
        cardImg.src = book.imageURL;
        cardImg.alt = 'Book Image';

        const cardBody = document.createElement('div');
        cardBody.classList.add('card-body', 'p-4');

        const textCenter = document.createElement('div');
        textCenter.classList.add('text-center');

        const productName = document.createElement('h5');
        productName.classList.add('fw-bolder');
        productName.textContent = book.title;

        const productPrice = document.createElement('div');
        productPrice.textContent = `$${book.price}`;

        const cardFooter = document.createElement('div');
        cardFooter.classList.add('card-footer', 'p-4', 'pt-0', 'border-top-0', 'bg-transparent');

        const btnContainer = document.createElement('div');
        btnContainer.classList.add('text-center');

        const addToCartBtn = document.createElement('a');
        addToCartBtn.classList.add('btn', 'btn-outline-dark', 'mt-auto');
        addToCartBtn.href = '#';
        addToCartBtn.textContent = 'Add to cart';

        // Append elements to their respective parent elements
        textCenter.appendChild(productName);
        textCenter.appendChild(productPrice);
        cardBody.appendChild(textCenter);
        btnContainer.appendChild(addToCartBtn);
        cardFooter.appendChild(btnContainer);
        card.appendChild(cardImg);
        card.appendChild(cardBody);
        card.appendChild(cardFooter);
        col.appendChild(card);
        container.appendChild(col);
    });
}

