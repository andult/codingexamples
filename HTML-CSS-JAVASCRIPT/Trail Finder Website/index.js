//REQUIRED CODE---------------------------------
const express = require("express");
const mysql = require('mysql');
const app = express();
const pool = dbConnection();
const bcrypt = require('bcrypt'); // bcrypt link: https://www.browserling.com/tools/bcrypt
const session = require('express-session');
const fetch = require('node-fetch');
// WEB API URLS

app.set("view engine", "ejs");
app.use(express.static("public"));
app.use(express.urlencoded({ extended: true })); // used for POST method
app.use(express.json());

app.set('trust proxy', 1); // session variable stuff
app.use(session({
  secret: 'security',
  resave: false,
  saveUninitialized: true,
  cookie: { secure: true }
}));

//ROUTES----------------------------------------
app.get("/dbTest", async function(req, res) {
  let sql = "SELECT CURDATE()";
  let rows = await executeSQL(sql);
  res.send(rows);
});//dbTest

app.get("/", async (req, res) => {
  if (typeof req.session.userId == "undefined") {
    req.session.userId = -1; // Set userId to -1 if user is not logged in.
  }
  res.render("index", { "userId": req.session.userId });
});

app.get("/browse", async (req, res) => {
  // for searching trails and adding to user specific list
  let trailName = req.query.trailName;
  let miles = req.query.miles;
  let elevation = req.query.elevation;
  let city = req.query.city;
  let difficulty = req.query.difficulty;
  let params = [];

  let sql = `SELECT * FROM trails WHERE 1`;

  //---
  if (trailName == "" || trailName == undefined) { console.log("empty") }
  else {
    sql += ` AND Name = ?`;
    params += [trailName];
  }
  //---
  if (city == "" || city == undefined) { console.log("empty") }
  else {
    sql += ` AND City = ?`;
    params += [city];
  }
  // //---
  if (miles == "" || miles == undefined) { console.log("empty") }
  else {
    sql += ` AND Miles = ?`;
    params += [miles];
  }
  // //---
  if (elevation == "" || elevation == undefined) { console.log("empty") }
  else {
    sql += ` AND Elevation = ?`;
    params += [elevation];
  }
  //---
  if (difficulty == "" || difficulty == undefined) { console.log("empty") }
  else {
    sql += ` AND Difficulty = ?`;
    params += [difficulty];
  }

  let rows = await executeSQL(sql, params);
  res.render("browse", { "trails": rows, "userId": req.session.userId });
});

app.post("/browse", async (req, res) => { // Add to Saved Trails
  let trailId = req.body.trailId;
  let userId = req.session.userId;
  let sql = `INSERT INTO saved_trails (Trail_ID, User_ID) VALUES (${trailId}, ${userId})`;
  let response = await executeSQL(sql);
  res.redirect("/myTrails");
});

app.post("/removeFromMyTrails", isLoggedIn, async (req, res) => { // Remove from Saved Trails
  let trailId = req.body.trailId;
  let userId = req.session.userId;
  let sql = `DELETE FROM saved_trails WHERE Trail_ID = ${trailId} AND User_ID = ${userId}`;
  let response = await executeSQL(sql);
  res.redirect("/myTrails");
});

app.get("/details", async (req, res) => {
  let sql = `SELECT * FROM trails WHERE ID = "${req.body.trailId}"`;
  let rows = await executeSQL(sql);
  // render detailed trail page w/ rows
  // TODO: ^ That.
  // This path will display all details of a selected trail, ID from req
});

app.get("/login", async (req, res) => {
  res.render("login");
});

app.get("/createAccount", async (req, res) => {
  res.render("createAccount");
});

app.post("/createAccount", async (req, res) => {
  let username = req.body.username;
  let password = req.body.password;

  let sql = "SELECT username FROM users WHERE username = ?";
  let existingUsername = await executeSQL(sql, [username]);
  if (existingUsername.length > 0) {
    res.send({ "message": "Username taken" });
  } else {
    sql = "INSERT INTO users (Username, Password, IsAdmin) VALUES (?, ?, ?)";
    let hashedPass = await bcrypt.hash(password, 10);
    let params = [username, hashedPass, 0];
    let rows = await executeSQL(sql, params);

    res.send({ "message": "success" });
  }
});

app.get("/logout", async (req, res) => {
  req.session.destroy();
  res.redirect("/");
});

app.get("/myTrails", isLoggedIn, async (req, res) => {
  // TODO: Pass userId, render trail details saved by user
  userId = req.session.userId;
  let sql = `SELECT * FROM trails NATURAL JOIN saved_trails WHERE User_ID = "${userId}"`;
  let rows = await executeSQL(sql);
  res.render("myTrails", { "trails": rows, "userId": req.session.userId });
});

app.get("/trail/new", isAdmin, async function(req, res) {
  res.render("addTrail", { "userId": req.session.userId });
});

app.post("/trail/new", isAdmin, async function(req, res) {
  let trailName = req.body.name;
  let miles = req.body.miles;
  let elevation = req.body.elevation;
  let city = req.body.city;
  let difficulty = req.body.difficulty;
  let estTime = req.body.estTime;
  let description = req.body.description;

  let sql = "INSERT INTO trails (Name, City, Miles, Elevation, Difficulty, Estimated_Time, Description) VALUES ( ?, ?, ?, ?, ?, ?, ?)";
  let params = [trailName, miles, elevation, city, difficulty, estTime, description];
  let rows = await executeSQL(sql, params);

  res.render("addTrail", { "message": "Record added!", "userId": req.session.userId });
});

app.post("/trail/update", isAdmin, async function(req, res) {
  let trailId = req.body.trailId;

  let sql = `SELECT * FROM trails WHERE Trail_ID ="${trailId}"`;
  let rows = await executeSQL(sql);
  console.log(rows);

  res.render("updateTrail", { "trails": rows, "userId": req.session.userId });
});

app.post("/trail/edit", async function(req, res) {
  let trailId = req.body.trailId;

  let sql = `UPDATE trails 
              SET Name = ?,
              City = ?,
              Miles = ?,
              Elevation = ?,
              Difficulty = ?,
              Estimated_Time = ?,
              Description = ? 
            WHERE Trail_ID = ${trailId}`;

  let params =
    [req.body.name,
    req.body.city,
    req.body.miles,
    req.body.elevation,
    req.body.difficulty,
    req.body.estTime,
    req.body.description];
  let rows = await executeSQL(sql, params);

  res.redirect(`/trail/update?trailId=${trailId}`)
});

app.get("/api/getTrails", async (req, res) => {
  let sql = "SELECT * FROM trails";
  let rows = await executeSQL(sql);
  res.send(rows);
});

app.post("/api/login", async (req, res) => {
  let username = req.body.username;
  let password = req.body.password;
  let rows = await executeSQL(`SELECT password, User_ID FROM users WHERE username="${username}"`);
  let hashedPwd = "";
  if (rows.length > 0) {
    hashedPwd = rows[0].password;
  }
  let pwdMatch = await bcrypt.compare(password, hashedPwd);
  if (pwdMatch) {
    req.session.userId = rows[0].User_ID;
    res.send({ "authentication": "success" });
  }
  else {
    res.send({ "authentication": "fail" });
  }
});

app.get("/admin", isAdmin, async (req, res) => {
  res.render("admin", { "userId": req.session.userId });
});

//FUNCTIONS-------------------------------------
async function executeSQL(sql, params) {
  return new Promise(function(resolve, reject) {
    pool.query(sql, params, function(err, rows, fields) {
      if (err) throw err;
      resolve(rows);
    });
  });
}//executeSQL

function dbConnection() {
  const pool = mysql.createPool({

    connectionLimit: 10,
    host: "lyn7gfxo996yjjco.cbetxkdyhwsb.us-east-1.rds.amazonaws.com",
    user: "ao2oebnmtkptnfol",
    password: "khre80hrsfvpvf32",
    database: "hsw0exauk3iqd5j1"
  });

  return pool;
} //dbConnection

async function getData(url) {
  let response = await fetch(url);
  let data = await response.json();
  return data;
}

// middleware function
function isLoggedIn(req, res, next) {
  if (typeof req.session.userId == "undefined" || req.session.userId == -1) {
    res.redirect("/login"); // If user is not logged in, send them to login page
  }
  else {
    next();
  }
}

function isAdmin(req, res, next) {
  if (req.session.userId == 4) {
    next();
  } else {
    res.redirect("/login");
  }
}

//start server
app.listen(3000, () => {
  console.log("Expresss server running...")
});


/* TRAIL TABLE COLUMNS
table name - trails
Trail_ID	int
Name	varchar(20)
City	varchar(20)
Miles	float
Elevation	int
Difficulty	varchar(20)
Estimated_Time	time(6)
Description	varchar(500)
*/

/* USER TABLE COLUMNS
table name - users
User_ID int
Username varchar(20)
Password varchar(20)
IsAdmin tinyInt
*/

/* SAVED TRAIL TABLE COLUMNS
table name- saved_trails
User_ID int
Trail_ID int
*/

/* Test User
Username: Test User
Password: test

Admin User
Username: Admin
Password: admin
(Very creative, I know)
*/
