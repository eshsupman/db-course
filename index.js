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
    var x = document.getElementById("pass1");
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
function formvalid() {
    var vaildpass = document.getElementById("pass").value;
    var spass = document.getElementById("pass1").value;
    if(spass != vaildpass){
        document.getElementById("vaild-pass").innerHTML = "Passwords dosnt match";
        return false;
    }else{
        document.getElementById("vaild-pass").innerHTML = " ";

    }
}
async function register(){
    event.preventDefault();
    let username = document.getElementById("user").value;
    let pwd = document.getElementById("pass").value;
    let pwdchek = document.getElementById("pass1").value;
    if(pwd !== pwdchek){
        document.getElementById("vaild-pass").textContent= 'passwords do not match';
        return;
    }

    const userData = {
        password: pwd,
        username: username
    };
    console.log(userData);
    fetch('http://localhost:8080/users/sigup', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json; charset=UTF-8'
        },
        body: JSON.stringify(userData)
    })
        .then(async response => {
            if (!response.ok) {
               alert('Error occurred! Check input data');
            }
            const responseText = await response.text();
            console.log(responseText);
            window.localStorage.setItem('token', responseText);
            return response.status;
        })
        .then(data => {
            //alert('Аккаунт успешно создан!');
            //console.log('Ответ от сервера:', data);
            window.location.replace('dashboard.html');
        })
        .catch(error => {
            alert('error: ' + error.message);
            console.error('error:', error);
        });
    window.localStorage.setItem()
}







// async function main() {
//     try{
//         const response = await fetch("http://localhost:8080/users");
//         let users = await response.json();
//         console.log(users);
//
//         for(let i=0;i<users.length;i++){
//             document.getElementById("Cid").innerHTML=users[i].id;
//             document.getElementById("Cname").innerHTML=users[i].username;
//             document.getElementById("Cpwd").innerHTML=users[i].password;
//
//         }
//
//     }catch(err){
//         console.log("ErrorA!!! "+err);
//     }
// }
// window.onload = main;
