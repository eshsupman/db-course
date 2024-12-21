async function downloadPerformanceGrowth() {
    const start = document.getElementById("startDate").value.substring(0, 4);
    const end = document.getElementById("endDate").value.substring(0,4);

    const token = window.localStorage.getItem("token");

    const response = await fetch("http://localhost:8080/users/gro", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`
        },
        body: JSON.stringify({
            start: parseInt(start),
            end: parseInt(end)
        })
    });

    if (!response.ok) {
        alert("Error: Unable to fetch data.");
        return;
    }
    function out(){
        window.localStorage.removeItem('token');
        window.location.replace('index.html');
    }

    const blob = await response.blob();
    const url = window.URL.createObjectURL(blob);

    const a = document.createElement("a");
    a.style.display = "none";
    a.href = url;
    a.download = "performance_growth.txt";
    document.body.appendChild(a);
    a.click();
    window.URL.revokeObjectURL(url);
}
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
document.addEventListener('DOMContentLoaded', () => {

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

window.onload= () => {
    getUserData();
}

