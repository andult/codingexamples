setQuestions();

//FETCHES DATA FROM API--------------------------------------------------------
async function getData(url) {
    let response = await fetch(url);
    let data = await response.json();
    return data;
}

async function setQuestions() {
    let url = "https://cst336.herokuapp.com/projects/api/superheroesAPI.php";
    let data = await getData(url);        //will get an array of heros in a random order
    console.log(data[0]);
    console.log(data[1].image);

    //QUESTION 1---------------------------------------------------------------
    // sets image for question1
    let image = data[0].image;
    $("#superHero").html(`<img src="images/${image}.png" alt="hero">`);

    //fills in dropdown menu
    //TODO: put in random order
    for(superhero of data) {
        let option = new Option(superhero.firstName + " "+ superhero.lastName);
        $("select").append(option);
    }
    //checks if the correct name was chosen
    $("#realNames").on("change", checkName)
    async function checkName() {  
        let choice = $("#realNames").val();
        console.log(choice);
        let realName = (data[0].firstName + " " + data[0].lastName);
        console.log(realName);
    
        if(choice == realName) {
            $("#check").html(" correct!");
            $("#check").css("color", "green");

        }
        else {
            $("#check").html(" try again");
            $("#check").css("color", "red");
        }
    }

    //QUESTION 2---------------------------------------------------------------
    $("#heroName").html(data[0].name);
    $("#heroName").css("font-style", "italic");

    $("#submit").on("click", checkAnswers)
    async function checkAnswers() {
        $("#submit").css("display", "none");
        $("#villains").css("display", "block");
        let radio_ans = $("input:checked").val(); 
        let url = `https://cst336.herokuapp.com/projects/api/superheroesAPI.php?heroId=${data[0].id}&pob=${radio_ans}`;
        let ans = await getData(url);
        console.log(radio_ans);
        console.log(ans.answer);

        if(ans.answer == "right") {
            $("#checkTown").html(" correct!");
            $("#checkTown").css("color", "green");
        }
        else if(ans.answer == "wrong") {
            $("#checkTown").html(" incorrect!");
            $("#checkTown").css("color", "red");
        }
    }

    //DISPLAY VILLAINS---------------------------------------------------------
    $("#villains").on("click", getVillains) 
    async function getVillains() {
        let url = `https://cst336.herokuapp.com/projects/api/superheroesAPI.php?heroId=${data[0].id}&data=villains`;
        let images = await getData(url);
        console.log(images);

        for(let i = 0; i < images.length; i++) {
            $("#villainPics").append(`<img src="${images[i].villainImage}" alt="villain">`);
        }
    }
}
