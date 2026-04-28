document.getElementById("loadUsers").addEventListener("click", function () {

    fetch("/bin/users")
        .then(response => response.json())
        .then(data => {

            let html = "";

            data.forEach(user => {
                html += `
                    <div>
                        <p><strong>Name:</strong> ${user.name}</p>
                        <p><strong>Email:</strong> ${user.email}</p>
                    </div>
                    <hr/>
                `;
            });

            document.getElementById("userContainer").innerHTML = html;
        })
        .catch(error => {
            console.error("Error:", error);
            document.getElementById("userContainer").innerHTML = "Failed to load data";
        });
});