
document.getElementById("register-flip").addEventListener("click",function() {
    document.getElementById("loginForm").style.display = 'none';
    document.getElementById("registerForm").style.display = '';
});

document.getElementById("login-flip").addEventListener("click",function() {
    document.getElementById("loginForm").style.display = '';
    document.getElementById("registerForm").style.display = 'none';
});

document.getElementById("login").addEventListener("click",function(e) {
    e.preventDefault();
    alert("login clicked")
});

document.getElementById("signUp").addEventListener("click",function(e) {
    e.preventDefault();
    alert("Sign Up clicked")
});

document.getElementById("search").addEventListener("change",function(e) {
    
    alert("jj")
});
