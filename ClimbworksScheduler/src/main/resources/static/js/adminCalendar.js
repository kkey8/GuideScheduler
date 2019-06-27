var receiverArr = [];
var senderArr = [];
var offArr = [];

(function () {

    $("#calendarButton").on("click", function () {
        location.href = "calendar.html";
    });
    $("#employeesButton").on("click", function () {
        location.href = "employees.html";
    });
    $("#offRequestsButton").on("click", function () {
        location.href = "offRequests.html";
    });
    $("#weekViewButton").on("click", function () {
        location.href = "weekView.html";
    });
    $("#generateScheduleButton").on("click", function () {
        location.href = "generateSchedule.html";
    });
    $("#signOutButton").on("click", function () {
        location.href = "../index.html";
    });

    $("#findButton").on("click", function () {

        var theDate = $("#mydate").val();
        if (theDate == null || theDate == '') {
            alert("Please select a date.");
        }
        else {
            theDate = new Date(theDate).toISOString().slice(0, 10);
            getSchedule(theDate);
        }
    });
})();

function getSchedule(d) {
    $.ajax({
        type: "post",
        url: "/getDaySchedule/" + d,
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        success: function (guides) {
            removeStuff();
            if (guides.length == 0) {
                addErrorMsg();
            }
            else {
                var contentRows = $("#contentRows");
                senderArr.length = 0;
                receiverArr.length = 0;
                for (var i = 0; i < 8; i++) {
                    var row = `<tr><td id="timeslot` + i + `"></td>`;
                    row += `<td>` + guides[2 * i].firstName + ` ` + guides[2 * i].lastName + `</td>`;
                    row += `<td>` + guides[2 * i + 1].firstName + ` ` + guides[2 * i + 1].lastName + `</td></tr>`;
                    contentRows.append(row);
                    receiverArr.push(guides[i].employeeId);
                    senderArr.push(guides[i + 8].employeeId);
                }
                getTimes();
                getReceivers(d);
                getSenders(d);
                getOff(d);
            }
        },
        error: function () {
            removeStuff();
            addErrorMsg();
        }
    })
}

function getTimes() {
    $.ajax({
        type: "get",
        url: "/getTimes",
        success: function (times) {
            for (var i = 0; i < 8; i++) {
                var time = times[i].theTime;
                var n = time.lastIndexOf(":");
                time = time.substring(0, n);
                $("#timeslot" + i).text(time);
            }
        },
        error: function () {
            console.log("time failed")
        }
    })
}

function getSenders(d) {

    var sendersRow = $("#senders");

    $("#sendersTable").find("tr:gt(0)").remove();

    $.ajax({
        type: "get",
        url: "/getAvailableSenders/" + d,
        success: function (senders) {
            senders.forEach(sender => {
                if (!senderArr.includes(sender.employeeId) && !receiverArr.includes(sender.employeeId)) {
                    sendersRow.append("<tr><td>" + sender.firstName + " " + sender.lastName + "</td></tr>");
                }
            });
        },
        error: function () {
        }
    })
}

function getReceivers(d) {
    var receiversRow = $("#receivers");

    $("#receiversTable").find("tr:gt(0)").remove();

    $.ajax({
        type: "get",
        url: "/getAvailableReceivers/" + d,
        success: function (receivers) {
            receivers.forEach(receiver => {
                if (!receiverArr.includes(receiver.employeeId) && !senderArr.includes(receiver.employeeId)) {
                    receiversRow.append("<tr><td>" + receiver.firstName + " " + receiver.lastName + "</td></tr>");
                }
            });
        },
        error: function () {
        }
    })
}

function getOff(d) {
    var offRow = $("#off");

    $("#offTable").find("tr:gt(0)").remove();

    $.ajax({
        type: "get",
        url: "/getOffEmployees/" + d,
        success: function (offEmployees) {
            console.log(offEmployees);
            offEmployees.forEach(off => {
                if (!offArr.includes(off.employeeId)) {
                    offRow.append("<tr><td>" + off.firstName + " " + off.lastName + "</td></tr>");
                }
            })
        }
    })
}

function removeStuff() {
    $('#errorMessages').empty();
    $("#employeeTable").find("tr:gt(0)").remove();
    $("#sendersTable").find("tr:gt(0)").remove();
    $("#receiversTable").find("tr:gt(0)").remove();
    $("#offTable").find("tr:gt(0)").remove();
}

function addErrorMsg() {
    $('#errorMessages')
        .append($('<li>')
            .attr({ class: 'list-group-item list-group-item-danger' })
            .text('The date you selected currently does not have a generated schedule.'));
}