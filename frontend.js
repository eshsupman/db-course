
document.getElementById("fetchData").addEventListener("click", function () {
    const year = document.getElementById("year").value;

    if (!year) {
        alert("Please enter a year.");
        return;
    }

    fetch(`http://localhost:8080/users/avg-by-year/${year}`)
        .then(response => {
            if (!response.ok) {
                throw new Error("Failed to fetch data from server.");
            }
            return response.json();
        })
        .then(data => {
            const tbody = document.querySelector("#resultTable tbody");
            tbody.innerHTML = ""; // Clear any existing rows

            data.forEach(row => {
                const tr = document.createElement("tr");

                const tdName = document.createElement("td");
                tdName.textContent = row.subject_name;

                const tdAvgMark = document.createElement("td");
                tdAvgMark.textContent = row.avg_mark.toFixed(2); // Round to 2 decimal places

                tr.appendChild(tdName);
                tr.appendChild(tdAvgMark);
                tbody.appendChild(tr);
            });
        })
        .catch(error => {
            console.error("Error:", error);
            alert("An error occurred while fetching data.");
        });
});
