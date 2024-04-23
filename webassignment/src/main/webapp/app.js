function searchUsers() {
    var searchInput = document.getElementById('searchInput').value.trim();
    if (searchInput === "") {
        alert("Please enter a user name to search.");
        return;
    }

    fetch('/webassignment/search?userName=' + searchInput)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            displaySearchResult(data);
        })
        .catch(error => console.error('Error:', error));
}

function addUser() {
    var newUserID = document.getElementById('newUserID').value.trim();
    var newUserName = document.getElementById('newUserName').value.trim();
    var newUserType = document.getElementById('newUserType').value.trim();
    if (newUserID === "") {
        alert("Please enter a new user ID to add.");
        return;
    }
    else if (newUserName === "") {
        alert("Please enter a new user name to add.");
        return;
    }
    else if (newUserType === "") {
        alert("Please enter a new user type to add.");
        return;
    }

    // Construct the URL with all variables
    var url = '/webassignment/add?userID=' + encodeURIComponent(newUserID) +
              '&userName=' + encodeURIComponent(newUserName) +
              '&userType=' + encodeURIComponent(newUserType);

    fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            displaySearchResult(data);
        })
        .catch(error => console.error('Error:', error));
}

function addUser() {
    var newUserID = document.getElementById('newUserID').value.trim();
    var newUserName = document.getElementById('newUserName').value.trim();
    var newUserType = document.getElementById('newUserType').value.trim();
    if (newUserID === "") {
        alert("Please enter a new user ID to add.");
        return;
    }
    else if (newUserName === "") {
        alert("Please enter a new user name to add.");
        return;
    }
    else if (newUserType === "") {
        alert("Please enter a new user type to add.");
        return;
    }

    // Construct the URL with all variables
    var url = '/webassignment/add?userID=' + encodeURIComponent(newUserID) +
              '&userName=' + encodeURIComponent(newUserName) +
              '&userType=' + encodeURIComponent(newUserType);

    fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            displaySearchResult(data);
        })
        .catch(error => console.error('Error:', error));
}

function updateUser() {
    var newuserType = document.getElementById('updateUserType').value.trim();
    var newuserName = document.getElementById('updateUserName').value.trim();
    var olduserID = document.getElementById('oldUserID').value.trim();
    if (olduserID === "") {
        alert("Please enter current user ID to change.");
        return;
    }
    else if (newuserName === "") {
        alert("Please enter a new user name to change.");
        return;
    }
    else if (newuserType === "") {
        alert("Please enter a new user type to change.");
        return;
    }

    // Construct the URL with all variables
    var url = '/webassignment/change?oldUserID=' + olduserID +
              '&newUserName=' + newuserName +
              '&newUserType=' + newuserType;

    fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            displaySearchResult(data);
        })
        .catch(error => console.error('Error:', error));
}

function deleteUser() {
    var userID = document.getElementById('deleteUserID').value.trim();
    if (searchInput === "") {
        alert("Please enter a user ID to delete.");
        return;
    }

    fetch('/webassignment/delete?userID=' + userID)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            displaySearchResult(data);
        })
        .catch(error => console.error('Error:', error));
}


function displaySearchResult(users) {
    var searchResultDiv = document.getElementById('searchResult');
    searchResultDiv.innerHTML = "";

    if (users.length === 0) {
        searchResultDiv.textContent = "No users found.";
    } else {
        var userList = document.createElement('ul');
        users.forEach(user => {
            var listItem = document.createElement('li');
            listItem.textContent = user.userName + ' - ' + user.userType; // Update property names according to the backend
            userList.appendChild(listItem);
        });
        searchResultDiv.appendChild(userList);
    }
}


