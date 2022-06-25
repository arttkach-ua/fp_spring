var form = document.querySelector('.form-with-validation')
var registerButton = form.querySelector('.btn btn-primary')
var email = document.getElementById('email')
var first_name = document.getElementById('first_name')
var second_name = document.getElementById('second_name')
var doc = document.getElementById('document')
var pass = document.getElementById('password')
var pass_confirm = document.getElementById('confirm_password')
var goodColor = "#66cc66";
var badColor = "#ff6666";

function Valid() {
    //getting validation for each field
    //event.preventDefault()
    emailIsValid = email_validate(email.value);
    passIsValid = passwordIsValid();
    //Getting form validation
    formIsValid = emailIsValid&&passIsValid;
    console.log("mail valid" + emailIsValid)
    console.log("pass valid " + passIsValid)
    console.log("valid form " + formIsValid)
    if (formIsValid==false) {event.preventDefault()}
    return formIsValid;
}

function email_validate(email){
    var regMail = /^([_a-zA-Z0-9-]+)(\.[_a-zA-Z0-9-]+)*@([a-zA-Z0-9-]+\.)+([a-zA-Z]{2,3})$/;

    if(regMail.test(email) == false){
        document.getElementById("status").innerHTML    = "<span class='warning'>Email address is not valid yet.</span>";
        return false;
    }
    else{
        document.getElementById("status").innerHTML	= "<span class='valid'>Thanks, you have entered a valid Email address!</span>";
        return true;
    }
}

function passwordIsValid(){
    //Store the Confimation Message Object ...
    var message = document.getElementById('confirmMessagePass');
    //Set the colors we will be using ...

    //Compare the values in the password field
    //and the confirmation field
    if(pass.value == pass_confirm.value){
        //The passwords match.
        //Set the color to the good color and inform
        //the user that they have entered the correct password
        pass_confirm.style.backgroundColor = goodColor;
        message.style.color = goodColor;
        message.innerHTML = "Passwords Match"
        return true;
    }else{
        //The passwords do not match.
        //Set the color to the bad color and
        //notify the user.
        pass_confirm.style.backgroundColor = badColor;
        message.style.color = badColor;
        message.innerHTML = "Passwords Do Not Match!"
        return false;
    }
}

// function documentIsValid() {
//     if(doc.value.length = 0){
//         return false;
//     }else{
//         return true;
//     }
// }