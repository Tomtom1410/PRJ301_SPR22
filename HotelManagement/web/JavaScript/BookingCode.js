//RoomDetail
function booking() {
    var name = document.getElementById('name').value;
    var email = document.getElementById('email').value;
    var phone = document.getElementById('phone').value;
    var noOfRoom = document.getElementById("noOfRoom");
    var checkin = document.getElementById('checkin').value;
    var checkout = document.getElementById('checkout').value;
    if (noOfRoom.options[noOfRoom.selectedIndex].value === "0") {
        alert("Please choose number of rooms");
        document.getElementById('booking').onsubmit = function () {
            return false;
        };
    } else if (name !== '' && email !== '' && phone !== '' && checkin !== '' && checkout !== '') {
        alert("Thank you for trusting and booking at our hotel!");
        document.getElementById('booking').onsubmit = function () {
            return true;
        };
    }
}

function search(keyName){
    var content = document.getElementById('content').value;
    window.location.href = "search?value=" + content + "&keyName="+keyName;
}