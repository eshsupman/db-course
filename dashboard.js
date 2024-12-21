async function getUserData() {
    const token = localStorage.getItem('token');

    fetch('http://localhost:8080/users/userdata', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
        body: null
    })
        .then(res => {
            console.log('Response status:', res.status);
            return res.json();
        }).then(data => {
        console.log('Response data:', data);
        document.getElementById('username').textContent = data[0] + ' !';

    })
        .catch(error => {
            console.error('Error:', error);
        });
}


async function getavg() {
    const token = localStorage.getItem('token');
    fetch('http://localhost:8080/users/avg', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
        body: null
    })
        .then(res => {
            console.log('Response status:', res.status); // Статус ответа от сервера

            return res.json();
        }).then(data => {
        console.log('Response data:', data);
        document.getElementById('avg_id').textContent = data;
        // Данные от сервера
    })
        .catch(error => {
            console.error('Error:', error);
        });
}


function out(){
    window.localStorage.removeItem('token');
    window.location.replace('index.html');
}
function displayGroupmates(groupmates) {
    const groupmatesList = document.getElementById('groupmates-list');
    groupmatesList.innerHTML = ''; // Очищаем список перед добавлением

    if (!groupmates || groupmates.length === 0) {
        groupmatesList.innerHTML = '<li>No groupmates found</li>';
        return;
    }

    groupmates.forEach(mate => {
        const listItem = document.createElement('li');
        listItem.textContent = `${mate.firstName} ${mate.lastName}`;
        groupmatesList.appendChild(listItem);
    });
}
async function getavgG() {
    const token = localStorage.getItem('token');
    fetch('http://localhost:8080/users/avgGroup', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
        body: null
    })
        .then(res => {
            console.log('Response status:', res.status); // Статус ответа от сервера

            return res.json();
        }).then(data => {
        console.log('Response data:', data);
        document.getElementById('g_avg_id').textContent = data;
        // Данные от сервера
    })
        .catch(error => {
            console.error('Error:', error);
        });
}
async function getGroups1() {
    const token = window.localStorage.getItem('token');
    if (!token) {
        alert('You are not authenticated! Please log in.');
        window.location.href = 'index.html';
    } else {

        fetch('http://localhost:8080/users/allG', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            }
        })
            .then(async response => {
                if (!response.ok) {
                    throw new Error('Failed to fetch groupmates: ' + response.statusText);
                }
                return response.json();
            })
            .then(groupmates => {
                displayData(groupmates, 'groupsTable');
            })
            .catch(error => {
                alert('Error: ' + error.message);
                console.error('Error:', error);
            });
    }
}
async function getPeople() {
    const token = window.localStorage.getItem('token');
    if (!token) {
        alert('You are not authenticated! Please log in.');
        window.location.href = 'index.html';
    } else {

        fetch('http://localhost:8080/users/allP', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            }
        })
            .then(async response => {
                if (!response.ok) {
                    throw new Error('Failed to fetch groupmates: ' + response.statusText);
                }
                return response.json();
            })
            .then(peoples => {
                displayData(peoples, 'peopleTable');
            })
            .catch(error => {
                alert('Error: ' + error.message);
                console.error('Error:', error);
            });
    }
}async function getSubjects() {
    const token = window.localStorage.getItem('token');
    if (!token) {
        alert('You are not authenticated! Please log in.');
        window.location.href = 'index.html';
    } else {

        fetch('http://localhost:8080/users/allS', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            }
        })
            .then(async response => {
                if (!response.ok) {
                    throw new Error('Failed to fetch groupmates: ' + response.statusText);
                }
                return response.json();
            })
            .then(subs => {
                displayData(subs, 'subjectsTable');
            })
            .catch(error => {
                alert('Error: ' + error.message);
                console.error('Error:', error);
            });
    }
}
async function getMarks() {
    const token = window.localStorage.getItem('token');
    if (!token) {
        alert('You are not authenticated! Please log in.');
        window.location.href = 'index.html';
    } else {

        fetch('http://localhost:8080/users/allM', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            }
        })
            .then(async response => {
                if (!response.ok) {
                    throw new Error('Failed to fetch groupmates: ' + response.statusText);
                }
                return response.json();
            })
            .then(marks => {
                displayData(marks, 'marksTable');
            })
            .catch(error => {
                alert('Error: ' + error.message);
                console.error('Error:', error);
            });
    }
}
function displayData(data, tableId) {
    const tableBody = document.getElementById(tableId).getElementsByTagName('tbody')[0];
    tableBody.innerHTML = '';
    data.forEach(d => {
        const row = tableBody.insertRow();
        let i = 0;

        d.forEach(value => {
            const cell = row.insertCell();
            cell.textContent = value;
        });
    });
}
document.addEventListener('DOMContentLoaded', () => {
    // Получаем ссылки на элементы
    const startDateInput = document.getElementById('startDate');
    const endDateInput = document.getElementById('endDate');
    const filter = document.getElementById('Filter');



    // При клике на кнопку "Показать" вызываем расчет
    calculateButton.addEventListener('click', async () => {
        const filters = {
            startDate: startDateInput.value,
            endDate: endDateInput.value,
            filter: filter.value
        };

         await fetchAverageData(filters);
    });
});


// Имитация запроса к серверу для получения среднего балла
async function fetchAverageData(filters) {
    const start = document.getElementById("startDate").value.substring(0, 4);
    const end = document.getElementById("endDate").value.substring(0,4);
    const token = window.localStorage.getItem('token');
    const urls = ['http://localhost:8080/users/avgG','http://localhost:8080/users/avgS','http://localhost:8080/users/avgT','http://localhost:8080/users/avgP','http://localhost:8080/users/avgY'];
    console.log(end);
    let url;
    if(filters.filter === 'groups') url=urls[0];
    if(filters.filter === 'subject') url=urls[1];
    if(filters.filter === 'teacher') url=urls[2];
    if(filters.filter === 'student') url=urls[3];
    if(filters.filter === 'year') url=urls[4];
    let data;

        fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify({
                start: parseInt(start),
                end: parseInt(end)
            })
        })
            .then(async response => {
                if (!response.ok) {
                    throw new Error('Failed to fetch groupmates: ' + response.statusText);
                }
                //drawChart(response.json())
                return response.json();
            })
            .then(marks => {
                console.log(marks);
                drawTable(marks);
                drawChart(marks)
            })
            .catch(error => {
                alert('Error: ' + error.message);
                console.error('Error:', error);
            });

}

function drawTable(data) {
    const tableContainer = document.getElementById("tableContainer");
    tableContainer.innerHTML = ""; // Очистка перед добавлением новой таблицы

    const table = document.createElement("table");
    table.className = "table";

    // Создаём заголовок таблицы
    const thead = document.createElement("thead");
    const headerRow = document.createElement("tr");

    const nameHeader = document.createElement("th");
    nameHeader.textContent = "Name";
    const valueHeader = document.createElement("th");
    valueHeader.textContent = "Average Mark";

    headerRow.appendChild(nameHeader);
    headerRow.appendChild(valueHeader);
    thead.appendChild(headerRow);
    table.appendChild(thead);

    // Создаём тело таблицы
    const tbody = document.createElement("tbody");

    data.forEach(item => {
        const row = document.createElement("tr");

        const nameCell = document.createElement("td");
        nameCell.textContent = item.name;

        const valueCell = document.createElement("td");
        valueCell.textContent = item.val.toFixed(2); // Форматируем до двух знаков после запятой

        row.appendChild(nameCell);
        row.appendChild(valueCell);
        tbody.appendChild(row);
    });

    table.appendChild(tbody);
    tableContainer.appendChild(table);
}
// Отобразить диаграмму с использованием Chart.js
function drawChart(data) {
    const ctx = document.getElementById('myChart').getContext('2d');
    const labels = data.map(item => item.name);
    const values = data.map(item => item.val);
//console.log(data);


    window.myChart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [{
                label: 'Средний балл',
                data: values,
                backgroundColor: 'rgba(75, 192, 192, 0.2)',
                borderColor: 'rgba(75, 192, 192, 1)',
                borderWidth: 1
            }]
        },
        options: {
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero: true
                    }
                }]
            }
        }
    });

}


window.onload = () => {
    getUserData();
    getGroups1();
    getPeople();
    getSubjects();
    getMarks();
    // getavg();
    // getMates();
    // getavgG();
}
