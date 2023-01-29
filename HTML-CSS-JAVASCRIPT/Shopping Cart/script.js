//Shopping Cart Items-----------------------------------------------------------------------------
var prod1 = {name: "The Invisible Life of Addie Larue by V.E. Schwab", price: 0.0, quantity: 0};
var prod2 = {name: "LED Cat Light", price: 0.0, quantity: 0};
var prod3 = {name: "Yuzuru Hanyu Pyeongchang Olympics Photobook", price: 0.0, quantity: 0};

$("#updateQuantity").on("click", updateQuantity);

function updateQuantity() {
    prod1.quantity = document.querySelector("#dropDown1").value;        //if document.querySelector use .value
    prod2.quantity = document.querySelector("#dropDown2").value;        //if $ use .val()
    prod3.quantity = $("#dropDown3").val();

    prod1.price = 16.19;        //resets price to original price
    prod2.price = 25.00;
    prod3.price = 51.25;

    prod1.price *= prod1.quantity;
    prod2.price *= prod2.quantity;
    prod3.price *= prod3.quantity;
    $("#itemPrice1").html("Price: $" + prod1.price.toFixed(2));
    $("#itemPrice2").html("Price: $" + prod2.price.toFixed(2));
    $("#itemPrice3").html("Price: $" + prod3.price.toFixed(2));
}

//Shipping and Total----------------------------------------------------------------------------------
var cost = {shipping: 0.0, subtotal: 0.0, tax: 0.0, total: 0.0, discount: 0.0};
$("#updateTotal").on("click", updateTotal);

function updateTotal() {
    //calculate subtotal------------------------
    cost.subtotal = prod1.price + prod2.price + prod3.price;
    $("#subtotal").html("Subtotal: $" + cost.subtotal.toFixed(2));

    //updates shipping--------------------------
    let shippingType = $("input:checked").val();
    if(shippingType == "plane") {
        cost.shipping = 0.0;
        $("#shipping").html("Shipping: $" + cost.shipping.toFixed(2))
    }
    else if(shippingType == "car") {
        cost.shipping = 2.00;
        $("#shipping").html("Shipping: $" + cost.shipping.toFixed(2))
    }
    else if(shippingType == "train") {
        cost.shipping = 5.00;
        $("#shipping").html("Shipping: $" + cost.shipping.toFixed(2))
    }
    else if(shippingType == "snail") {
        cost.shipping = 50.00;
        $("#shipping").html("Shipping: $" + cost.shipping.toFixed(2))
    }
    else {
        $("#shipping").html("Shipping: -Select Shipping-")
    }

    //calculate tax-----------------------------
    cost.tax = cost.subtotal * .0725;
    $("#tax").html("Tax: $" + cost.tax.toFixed(2));

    //calculate total---------------------------
    cost.total = cost.subtotal + cost.shipping + cost.tax;
    $("#total").html("Total: $" + cost.total.toFixed(2));
    
    //add promo code----------------------------
    let discount = $("#promoCode").val();
    if(discount == "csumb"){
         cost.total *= .85;
        $("#total").html("Total: $" + cost.total.toFixed(2));
    }
}

//Place Order-----------------------------------------------------------------------------------------
var order = [prod1, prod2, prod3, cost];
$("#placeOrder").on("click", placeOrder);
$(".close-btn").on("click", placeOrder);
// $("#order").html(order);

function placeOrder() {
    document.querySelector("#popup-1").classList.toggle("active");
    document.getElementById("order").innerHTML = JSON.stringify(order, null, 4);

}
