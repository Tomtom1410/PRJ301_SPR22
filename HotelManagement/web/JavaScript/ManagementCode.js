//generate pagging
function generatePagger(div, pageindex, totalpage, url, gap) {
    var container = document.getElementById(div);
    if (pageindex - gap > 1)
        container.innerHTML += '<a href="' + url + '1">First</a>';
    for (var i = pageindex - gap; i < pageindex; i++) {
        if (i > 0)
            container.innerHTML += '<a href="' + url + i + '">' + i + '</a>';
    }
    container.innerHTML += '<span >' + pageindex + '</span>';
    for (var i = pageindex + 1; i <= pageindex + gap; i++) {
        if (i <= totalpage)
            container.innerHTML += '<a href="' + url + i + '">' + i + '</a>';
    }
    if (pageindex + gap < totalpage)
        container.innerHTML += '<a href="' + url + totalpage + '">Last</a>';
}
// order have room
function searchDeptbyName(orderWaitID, pageIndex) {
    var keyWord = document.getElementById('getDeptName');
    var value = keyWord.options[keyWord.selectedIndex].value;
    window.location.href = "GetRoomByDeptName?orderWaitID=" + orderWaitID + "&deptName=" + value + "&page=" + pageIndex;
}
