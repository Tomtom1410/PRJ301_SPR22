// orderWaitd(url = 'bookingDetail')
function checkRoom() {
    var noOfRoom = document.getElementById('noOfRoom').value;
    var rooms = document.getElementsByName('deptID');
    let count = 0;
    for (var i = 0; i < rooms.length; i++) {
        if (rooms[i].checked === true) {
            count++;
        }
    }
    if (Number.parseInt(count) !== Number.parseInt(noOfRoom)) {
        alert("Please check the room. The number of rooms is different from the number of rooms the customer wants!");
        document.getElementById('insertBooking').onsubmit = function () {
            return false;
        };
    }else if (Number.parseInt(count) === Number.parseInt(noOfRoom)){
        document.getElementById('insertBooking').onsubmit = function () {
            return true;
        };
    }
}

function search(keyName){
    var content = document.getElementById('content').value;
    window.location.href = "search?value=" + content + "&keyName="+keyName;
}