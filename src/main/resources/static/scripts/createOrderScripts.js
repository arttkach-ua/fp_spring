var el_daysCount = document.getElementById('days_count');
var el_price     = document.getElementById('price_per_day');
var el_sumElement = document.getElementById('sum');
var el_driverPrice = document.getElementById('driver_price');
var el_withDriver = document.getElementById('with_driver');

function getSum(){
    var driverPrice = parseFloat(el_driverPrice.innerHTML);
    var rent_price = parseFloat(el_price.innerHTML);
    var days = parseInt(el_daysCount.value);
    var sum = 0;

    if (el_withDriver.checked) {
        sum = (driverPrice+rent_price)*days;
    }else{
        sum = rent_price*days;
    }
    sum = sum*100;
    sum = Math.round(sum);
    el_sumElement.value = sum/100;
}