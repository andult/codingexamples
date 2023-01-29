$("#submit").on("click", validateInput);

//GETS DATA FROM API------------------------------------------------------
async function getData(url) {
    let response = await fetch(url);
    let data = await response.json();
    return data;
}

//CHECKS TO MAKE SURE THERE WAS USER INPUT--------------------------------
function validateInput() {
    let amount = $("#num").val();

    if(amount < 1) {
        $("#error").html(" No entry was made")
        $("#error").css("color", "black");
        $("#error").css("font-style", "italic");
    }
    else if(amount > 1000) {
        $("#error").html(" Entry is out of range")
        $("#error").css("color", "blue");
    }
    else {
        displayData();
    }
}

//DISPLAYS DATA FROM API--------------------------------------------------
async function displayData() {
    console.log("inside displayData");

    let choice = $("#infoType").val();
    let amount = $("#num").val();

    let url = `https://fakerapi.it/api/v1/${choice}?_quantity=${amount}`;
    let data = await getData(url);
    console.log(data.data);

    $("#APIinfo").append(`<strong>${choice}</strong> <br>`);
    for(let i = 0; i < amount; i++){              //loop to print the amount of information sets
        console.log(data.data[i].city);
        $("#APIinfo").append(`${i+1}. ${JSON.stringify(data.data[i])} 
        <br><br>`);   //turns object into string
    }    
}
