function searchUsers() {
    var inputElement = document.getElementById('searchInput');
    var searchInput = inputElement.value.trim();

    if (searchInput === "") {
        alert("Please enter a user name to search.");
        return;
    }

    // Clear the input field after validation but before fetch operation
    inputElement.value = '';

    fetch('/webassignment/search?userName=' + encodeURIComponent(searchInput))
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            console.log("Received data:", data);; // Add this to see what the data structure looks like
            displayResult(data, 'searchResult');
        })
        .catch(error => {
            console.error('Error:', error);
            // Optionally, you might want to handle error more explicitly here
        });
}

function addUser() {
    var userIDElement = document.getElementById('newUserID');
    var userNameElement = document.getElementById('newUserName');
    var userTypeElement = document.getElementById('newUserType');

    var newUserID = userIDElement.value.trim();
    var newUserName = userNameElement.value.trim();
    var newUserType = userTypeElement.value.trim();

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
    // Clear the inputs after validation
    userIDElement.value = '';
    userNameElement.value = '';
    userTypeElement.value = '';

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
            displayResult(data, 'addUserResult');
        })
        .catch(error => console.error('Error:', error));
}

function updateUser() {
    var userIDElement = document.getElementById('oldUserID');
    var userNameElement = document.getElementById('updateUserName');
    var userTypeElement = document.getElementById('updateUserType');

    var oldUserID = userIDElement.value.trim();
    var newUserName = userNameElement.value.trim();
    var newUserType = userTypeElement.value.trim();

    if (oldUserID === "") {
        alert("Please enter current user ID to change.");
        return;
    }
    else if (newUserName === "") {
        alert("Please enter a new user name to change.");
        return;
    }
    else if (newUserType === "") {
        alert("Please enter a new user type to change.");
        return;
    }
    // Clear the inputs after validation
    userIDElement.value = '';
    userNameElement.value = '';
    userTypeElement.value = '';

    var url = '/webassignment/change?oldUserID=' + encodeURIComponent(oldUserID) +
            '&newUserName=' + encodeURIComponent(newUserName) +
            '&newUserType=' + encodeURIComponent(newUserType);
    fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            displayResult(data, 'updateUserResult');
        })
        .catch(error => console.error('Error:', error));
}

function deleteUser() {
    var userIDElement = document.getElementById('deleteUserID');
    var userID = userIDElement.value.trim();

    if (userID === "") {
        alert("Please enter a user ID to delete.");
        return;
    }

    userIDElement.value = ''; // Clear the input after validation

    fetch('/webassignment/delete?userID=' + encodeURIComponent(userID))
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            if (!data.searchSuccess) { // No user found
                displayDeletionResult("No user found", 'deleteUserResult');
            } else if (data.deletionSuccess) { // Deletion successful
                displayDeletionResult("Deletion successful!", 'deleteUserResult');
            } else { // Deletion failed
                displayDeletionResult("Deletion failed. User deletion process failed", 'deleteUserResult');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            displayDeletionResult("Deletion failed. User never existed or deletion process failed", 'deleteUserResult');
        });
}

function addUses(){
    var userIDElement = document.getElementById('UserId');
    var deviceIDElement = document.getElementById('DeviceId');
    var usageDateElement = document.getElementById('UsageDate');
    var usageDurationElement = document.getElementById('UsageDuration');


    var userID = userIDElement.value.trim();
    var deviceId = deviceIDElement.value.trim();
    var usageDate = usageDateElement.value.trim();
    var usageDuration = usageDurationElement.value.trim();

    if (userID === "") {
        alert("Please enter a user ID to add.");
        return;
    }
    else if (deviceId === "") {
        alert("Please enter a deviceId to add.");
        return;
    }
    else if (usageDate === "") {
        alert("Please enter a usage date to add.");
        return;
    }
    else if (usageDuration === "") {
        alert("Please enter a usage duration to add.");
        return;
    }
    // Clear the inputs after validation
    userIDElement.value = '';
    deviceIDElement.value = '';
    usageDurationElement.value = '';
    usageDurationElement.value = '';


    // Construct the URL with all variables
    var url = '/webassignment/addUsage?userID=' + encodeURIComponent(userID) +
              '&deviceId=' + encodeURIComponent(deviceId) +
              '&usageDate=' + encodeURIComponent(usageDate) +
              '&usageDuration=' + encodeURIComponent(usageDuration);

    fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            displayResultUsage(data, 'updateUsageResult');
        })
        .catch(error => console.error('Error:', error));
}

function displayDeletionResult(message, resultDivId) {
    var resultDiv = document.getElementById(resultDivId);
    resultDiv.innerHTML = ""; // Clear any previous messages
    resultDiv.textContent = message;
}



function displayResult(users, resultDivId) {
    var resultDiv = document.getElementById(resultDivId);
    resultDiv.innerHTML = "";

    if (users.length === 0) {
        resultDiv.textContent = "No users found.";
    } else {
        var userList = document.createElement('ul');
        users.forEach(user => {
            var listItem = document.createElement('li');
            listItem.textContent = user.userName + ' - ' + user.userType + ' - ' + user.userId; 
            userList.appendChild(listItem);
        });
        resultDiv.appendChild(userList);
    }
}

function displayResultUsage(uses, resultDivId) {
    var resultDiv = document.getElementById(resultDivId);
    resultDiv.innerHTML = "";

    if (uses.length === 0) {
        resultDiv.textContent = "Usage already exists or Device ID and or User ID is not in Database";
    } else {
        var usesList = document.createElement('ul');
        uses.forEach(uses => {
            var listItem = document.createElement('li');
            listItem.textContent = 'ADDED USAGE: userID: ' + uses.userId + ' - deviceID: ' +  uses.deviceId + ' - usageDate: ' +  uses.usageDate + ' - usageDuration: ' +  uses.usageDuration; 
            usesList.appendChild(listItem);
        });
        resultDiv.appendChild(usesList);
    }
}




