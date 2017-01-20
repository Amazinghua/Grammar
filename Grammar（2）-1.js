//条件语句
// if 
if (1 > 2) {
alert("The world has gone mad!");    
}else{
alert("All is well with the world");    
}

//==
//===

//逻辑与 与 逻辑或
if ( num >= 5 && num <= 10 ) {
    alert("The number is in the right range.");
    
}

if ( num > 10 || num < 5 ) {
    alert("The number is not in the right range.");
}

//逻辑非 !

//循环语句

var count = 1;
while (count < 11) {
    alert (count);
    count++;
}
//do while 至少循环一次

var beatles = Array("John","Paul","George","Ringo");
for (var count = 0; count < beatles.length; count++) {
     alert(beatles[count]);
    
}
//函数