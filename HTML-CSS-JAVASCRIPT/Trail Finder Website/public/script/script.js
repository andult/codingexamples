showCities();
displayTrails();
setBackground();
$("#loginButton").on("click", login);
$("#createAccountBtn").on("click", createAccount);

// API Key: 20482482-7264a739a5728f28754b634ac
var pixUrl = "https://pixabay.com/api/?key=20482482-7264a739a5728f28754b634ac&q=";

//FETCHES DATA FROM API--------------------------------------
async function getData(url) {
  let response = await fetch(url);
  let data = await response.json();
  return data;
}

//POPULATES CITY DROPDOWN----------------------------------------
async function showCities() {
  let url = "/api/getTrails";
  let data = await getData(url);

  for (trail of data) {
    let option = new Option (trail.City, trail.City);
    $("#cityName").append(option);
  }
}

//DISPLAYS TABLE OF TRAILS ON ADMIN PAGE-----------
async function displayTrails() {
  let url = "/api/getTrails";
  let data = await getData(url);

  for (trail of data) {
    $("table").append(`<tr>
      <td><form action="/trail/update" method="post"><button>Update</button><input type="hidden" name="trailId" value=${trail.Trail_ID}></input></form></td>
      <td>${trail.Name}</td>
      <td>${trail.City}</td>
      <td>${trail.Miles}</td>
      <td>${trail.Elevation}</td>
      <td>${trail.Difficulty}</td>
      <td>${trail.Estimated_Time}</td>
      </tr>`);
    }
}

//SETS BACKGROUND USING API-------------------------------
async function setBackground(){
  let keys = ["river", 
              "tree", 
              "trail", 
              "nature", 
              "forest"];
  let num = Math.floor(Math.random() * 5);
  let data = await getData(`https://pixabay.com/api/?key=20482482-7264a739a5728f28754b634ac&q=${keys[num]}&orientation=horizontal`);
  let rand = Math.floor(Math.random() * data.hits.length);

  $("#mainPage").css("background-image", "url(" + data.hits[rand].webformatURL + ")");
  $("#mainPage").css("background-repeat", "no-repeat");
}

//LOGIN USING AJAX---------------------------------------
async function login() {
  console.log("Logging in...");
  let username = document.querySelector("#username").value;
  let password = document.querySelector("#password").value;

  if(username == ""){
    console.log("empty username");
    $("#errorMsg").html("Please enter a username");
  }
  else if(password == ""){
    console.log("empty password");
    $("#errorMsg").html("Please enter a password");
  }
  else{
      let url = "/api/login";
      let response = await fetch(url, {method:'post', body: JSON.stringify({"username":username, "password":password}), 
      headers: {
        "Content-Type": "application/json",
        "Accept": "application/json"
      } 
    });

    let data = await response.json();
    if(data.authentication == "success"){
      console.log("successful login");
      window.location.href="/myTrails";
    }
    else{
      console.log("Login failed");
      $("#errorMsg").html("Login failed");
      $("#errorMsg").css("background-color", "red");
    }
  }
}

//CREATE ACCOUNT USING AJAX------------------------------
async function createAccount() {
  let username = document.querySelector("#username").value;
  let password = document.querySelector("#password").value;
  let retypePassword = document.querySelector("#retypePassword").value;
  if(username == "") {
    $("#errorMsg").html("Username cannot be blank");
  } else if(password == "") {
    $("#errorMsg").html("Password cannot be blank");
  } else if(password != retypePassword) {
    console.log(password + " " + retypePassword);
    $("#errorMsg").html("Passwords do not match");
  } else {
    let url = "/createAccount";
    let response = await fetch(url, {
      method:'post', body: 
        JSON.stringify({
          "username":username, "password":password}), 
      headers: {
        "Content-Type": "application/json",
        "Accept": "application/json"
      }
    });
    let data = await response.json();

    if(data.message == "success"){
      window.location.href="/login";
    } else {
      $("#errorMsg").html(data.message);
    }
  }
}