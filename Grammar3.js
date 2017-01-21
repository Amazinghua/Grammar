function multiply(num1,num2) {
    var total = num1 * num2;
    alert(total);
}
multiply(10,2)

function convertToCelsius(temp) {
    var result = temp - 32;
    result = result / 1.8;
    return result;
}

var temp_fahrenheit = 95;
var temp_celsius = convertToCelsius(temp_fahrenheit);
alert(temp_celsius)

var num = 7.561;
var num = Math.round(num);
alert(num);

