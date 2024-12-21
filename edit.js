let listDelG = [];
let listDelP = [];
let listDelM = [];
let listDelS = [];
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

async function validate() {
    const token = window.localStorage.getItem('token');
    fetch('http://localhost:8080/users/validate', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
        body: null
    })
        .then(res => {
            console.log('Response status:', res.status);
            if (res.status === 401) {
                alert("You dont have permission to use this page.");
                window.location.replace('dashboard.html');
            }
        }).then(data => {
    })
        .catch(error => {
            console.error('Error:', error);
        });
}

async function getGroups() {
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

async function update(tableId) {
    const token = window.localStorage.getItem('token');
    let type = -1;
    if (tableId === 'groupsTable') type = 0;
    if (tableId === 'peopleTable') type = 1;
    if (tableId === 'subjectsTable') type = 2;
    if (tableId === 'marksTable') type = 3;
    const n = document.getElementById(tableId).getElementsByTagName('tbody')[0].rows.length;
    let tableBody = document.getElementById(tableId).getElementsByTagName('tbody')[0];
    console.log(type);
    let j = [];
    for (let i = 0; i < n; i++) {
        if (type === 0 || type === 2) {
            if (isNaN(Number(tableBody.rows[i].cells[0].textContent))) {
                j[i] = {
                    id: -1,
                    name: tableBody.rows[i].cells[1].textContent
                }
            } else j[i] = {
                id: Number(tableBody.rows[i].cells[0].textContent),
                name: tableBody.rows[i].cells[1].textContent
            }
        } else if (type === 1) {
            if (isNaN(Number(tableBody.rows[i].cells[0].textContent))) {
                j[i] = {
                    id: -1,
                    first_name: tableBody.rows[i].cells[1].textContent,
                    last_name: tableBody.rows[i].cells[2].textContent,
                    father_name: tableBody.rows[i].cells[3].textContent,
                    group_id: tableBody.rows[i].cells[4].textContent,
                    type: tableBody.rows[i].cells[5].textContent.at(0)
                }
            } else j[i] = {
                id: Number(tableBody.rows[i].cells[0].textContent),
                first_name: tableBody.rows[i].cells[1].textContent,
                last_name: tableBody.rows[i].cells[2].textContent,
                father_name: tableBody.rows[i].cells[3].textContent,
                group_id: Number(tableBody.rows[i].cells[4].textContent),
                type: tableBody.rows[i].cells[5].textContent.at(0)
            }
        }else if(type === 3){
            const dateCell = tableBody.rows[i].cells[4];
            const dateInput = dateCell.querySelector('input[type="date"]');
            const dateValue = dateInput ? dateInput.value : dateCell.textContent.trim();

            if (isNaN(Number(tableBody.rows[i].cells[0].textContent))) {
                j[i] = {
                    id: -1,
                    student_id: Number(tableBody.rows[i].cells[1].textContent),
                    subject_id: Number(tableBody.rows[i].cells[2].textContent),
                    teacher_id: Number(tableBody.rows[i].cells[3].textContent),
                    date: dateValue || null,
                    value: Number(tableBody.rows[i].cells[5].textContent)
                }
            } else {
                j[i] = {
                    id: Number(tableBody.rows[i].cells[0].textContent),
                    student_id: Number(tableBody.rows[i].cells[1].textContent),
                    subject_id: Number(tableBody.rows[i].cells[2].textContent),
                    teacher_id: Number(tableBody.rows[i].cells[3].textContent),
                    date: dateValue || null,
                    value: Number(tableBody.rows[i].cells[5].textContent)
                }
            }

        }

    }
    console.log(j);
    let url = ['http://localhost:8080/users/remG', 'http://localhost:8080/users/remP', 'http://localhost:8080/users/remS', 'http://localhost:8080/users/remM'];
    let url1 = ['http://localhost:8080/users/updG', 'http://localhost:8080/users/updP', 'http://localhost:8080/users/updS', 'http://localhost:8080/users/updM'];
    let lists = [listDelG, listDelP, listDelS, listDelM];
    console.log({
        list: lists[type]
    });
    fetch(url[type], {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify({
            list: lists[type]
        })

    })
        .then(res => {
            console.log('Response status on delete:', res.status);
            if (res.status === 400) {
                alert('bad request')
            }

        }).then(data => {
    })
        .catch(error => {

            console.error('Error:', error);
        });
    fetch(url1[type], {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify({
            list: j
        })

    })
        .then(res => {
            console.log('Response status on update:', res.status);
            if (res.status === 400) {
                alert(res.body)
            }

        }).then(data => {
    })
        .catch(error => {

            console.error('Error:', error);
        });
    await new Promise(r => setTimeout(r, 500));
    if (type === 0) await getGroups();
    if (type === 1) await getPeople();
    if (type === 2) await getSubjects();
    if (type === 3) await getMarks();

    listDelG = [];
    listDelP = [];
    listDelM = [];
    listDelS = [];
}

function displayData(data, tableId) {
    const tableBody = document.getElementById(tableId).getElementsByTagName('tbody')[0];

    tableBody.innerHTML = '';
    let id = 0;

    data.forEach(d => {
        const row = tableBody.insertRow();
        let i = 0;

        d.forEach(value => {
            const cell = row.insertCell();

            if (tableId === 'marksTable' && i === 4) {

                const dateInput = document.createElement('input');
                dateInput.type = 'date';
                dateInput.value = value;
                dateInput.classList.add('edit-date-input');


                dateInput.addEventListener('blur', () => {
                    cell.textContent = dateInput.value || value;
                    makeDateCellEditable(cell);
                });


                dateInput.addEventListener('keydown', (event) => {
                    if (event.key === 'Enter') {
                        dateInput.blur();
                    }
                });

                cell.appendChild(dateInput);
            } else {
                cell.textContent = value;
                if (i === 0) id = value;
                if (i !== 0) cell.contentEditable = true;
            }

            i++;
        });
        let cell1 = row.insertCell();
        cell1.innerHTML = '<span id="cross" onclick="deleteData(this,' + tableId + ')"> &#x274c </span>';
        cell1.classList.add('op');
    });
}


function makeDateCellEditable(cell) {
    cell.addEventListener('click', function onClick() {
        const value = cell.textContent.trim();
        cell.removeEventListener('click', onClick);

        const dateInput = document.createElement('input');
        dateInput.type = 'date';
        dateInput.value = value;
        dateInput.classList.add('edit-date-input');


        dateInput.addEventListener('blur', () => {
            cell.textContent = dateInput.value || value;
            makeDateCellEditable(cell);
        });


        dateInput.addEventListener('keydown', (event) => {
            if (event.key === 'Enter') {
                dateInput.blur();
            }
        });

        cell.textContent = '';
        cell.appendChild(dateInput);
        dateInput.focus();
    });
}


function deleteData(button, tableId) {

    let row = button.parentNode.parentNode;
    console.log(row.getElementsByTagName('td')[0].textContent);
    row.parentNode.removeChild(row);
    if(tableId.id === 'groupsTable')  listDelG.push(Number(row.getElementsByTagName('td')[0].textContent));
    if(tableId.id === 'marksTable')  listDelM.push(Number(row.getElementsByTagName('td')[0].textContent));
    if(tableId.id === 'subjectsTable')  listDelS.push(Number(row.getElementsByTagName('td')[0].textContent));
    if(tableId.id === 'peopleTable')  listDelP.push(Number(row.getElementsByTagName('td')[0].textContent));
    console.log(tableId.id);

}

function addT(tableId) {
    let row = document.getElementById(tableId).getElementsByTagName('tbody')[0].insertRow();
    let n = 2;
    if (tableId === 'peopleTable' || tableId === 'marksTable') n = 6;

    for (let i = 0; i < n; i++) {
        let cell = row.insertCell();
        if (i > 0) {
            if (tableId === 'marksTable' && i === 4) {
                const dateInput = document.createElement('input');
                dateInput.type = 'date';
                dateInput.value = new Date().toISOString().split('T')[0];
                cell.appendChild(dateInput);
            } else {
                cell.contentEditable = true;
                cell.textContent = ' ';
            }
        } else {
            cell.textContent = 'this value will be generated';
        }
    }
    row.insertCell().innerHTML = '<span id="cross" onclick="deleteData(this,' + tableId + ')"> &#x274c </span>';
}

async function getSubjects() {
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
}
function out(){
    window.localStorage.removeItem('token');
    window.location.replace('index.html');
}

window.onload = () => {
    validate();
    getGroups();
    getMarks();
    getSubjects();
    getPeople();
    getUserData();

}