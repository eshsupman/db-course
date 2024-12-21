function show() {
    var x = document.getElementById("pass");
    if (x.type === "password") {
        x.type = "text";
        document.getElementById("showimg").src =
            "https://static.thenounproject.com/png/777494-200.png";
    } else {
        x.type = "password";
        document.getElementById("showimg").src =
            "https://cdn2.iconfinder.com/data/icons/basic-ui-interface-v-2/32/hide-512.png";
    }

}

async function login() {
    event.preventDefault();
    let username = document.getElementById("user").value;
    let pwd = document.getElementById("pass").value;
    const userData = {
        password: pwd,
        username: username,
    };
    console.log(userData);


    fetch('http://localhost:8080/users/login', {
        method: 'POST',
        body: JSON.stringify(userData),
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(async response => {
            if (!response.ok) {
                throw new Error('error occurred');
            }
            const responseText = await response.text();
            console.log(responseText);
            window.localStorage.setItem('token', responseText);
            return response.status;
        })
        .then(data => {
            alert('you logged in!');
            console.log('Server  response:', data);
            window.location.replace('dashboard.html');
        })
        .catch(error => {
            alert('Error1 ' + error.message);
            console.error('Error', error);
        });
}