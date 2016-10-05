function get() {
 
	alert ("hi");
    var req = new XMLHttpRequest();
     
    // Create the callback:
    req.onreadystatechange = function() {
        if (req.readyState != 4)
            return; // Not there yet
        if (req.status != 200) {
            // Handle request failure here...
            return;
        }
        // Request successful, read the response
        var resp = req.responseText;
        // ... and use it as needed by your app.
        alert("response: " + this.responseText);
    }
    
    req.open("GET", 'http://localhost:8080/FirstTest/rest/status/hunger', false);
    req.open("GET", 'http://localhost:8080/FirstTest/rest/status/hygiene', false);
    req.setRequestHeader("Content-Type", "application/json");
    req.send();
    alert ("hi2");
}