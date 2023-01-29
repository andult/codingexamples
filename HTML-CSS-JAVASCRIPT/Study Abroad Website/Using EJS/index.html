//THIS IS THE CONTROLLER FILE, HAS ALL THE COMMANDS

//DEFAULT CODE------------------------------------------------
const express = require('express');     //imports express library
const fetch = require('node-fetch');    //requires the fetch package

const emoji = require('emoji-ice-cream');

const app = express();      //variable to access the methods in library (like an object)
app.set("view engine", "ejs");  //view engine
app.use(express.static("public"));  //specifies folder for static files


//ROUTES------------------------------------------------------
app.get('/', (req, res) => {        //creates the root route folder/page
    // res.send('Hello Express app!') Instead of send (hardcode), use render (dynamic)
    res.render('index', {"iceCream":emoji})
});

//more routes
app.get('/france', (req, res) => {
    res.render('france')   
})
app.get('/southKorea', (req, res) => {
    res.render('southKorea')   
})
app.get('/resources', async (req, res) => {
    let url = "https://pixabay.com/api/?key=5589438-47a0bca778bf23fc2e8c5bf3e&q=travel&orientation=$vertical";
    let data = await getData(url);
    console.log(data)
    res.render('resources', {"image":data.hits[1].webformatURL})    //data we are passing to the template
})


//LISTENER----------------------------------------------------
app.listen(3000,() => {   //allows the server to listen for any request
    console.log('server started');
});

//FUNCTIONS---------------------------------------------------
async function getData(url){
    let response = await fetch(url);
    let data = await response.json();
    return data;
}

