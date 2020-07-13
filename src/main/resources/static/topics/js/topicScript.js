
document.getElementById("logOut").addEventListener("click",function(e) {
    e.preventDefault();
    alert("Log out clicked")
});

document.getElementById("submitPost").addEventListener("click",function(e) {
    e.preventDefault();
    var txt;
    var person = prompt("Please enter your Email:", "");
    if (person == null || person == "") {
      txt = '<div class="uk-alert-warning" uk-alert> <a class="uk-alert-close" uk-close></a> <p>you have cancelled.</p> </div>';
    } else {
      txt = '<div class="uk-alert-success" uk-alert> <a class="uk-alert-close" uk-close></a> <p>Your question has been successfully posted.</p> </div>';
    }
    document.getElementById("demo").innerHTML = txt;
});

document.getElementById("topicSearch").addEventListener("change",function(e) {
    
    alert("topic")
});
