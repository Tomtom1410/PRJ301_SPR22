// Contact
function submitForm() {
    var name = document.getElementById('name').value;
    var email = document.getElementById('email').value;
    var phone = document.getElementById('phone').value;
    var address = document.getElementById('address').value;
    var feedback = document.getElementById('feedback').value;

    if (name !== '' && email !== '' && phone !== '' && address !== '' && feedback !== '') {
        alert("Thanks for your feedback!");
    }
}
