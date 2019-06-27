var receiverArr = [];
var senderArr = [];
var offArr = [];

var weeklyOffArr = [];
var weeklyGuidesArr = [];
var weeklyReceiverArr = [];
var weeklySenderArr = [];

var receivercounts = {};
var sendercounts = {};
var offcounts = {};

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
            getEmployees(theDate);
        }
    });
})();

function getEmployees(d) {
    removeStuff();
    $.ajax({
        type: "get",
        url: "/employees",
        dataType: "json",
        success: function (emps) {
            var contentRows = $("#contentRows");
            emps.forEach(e => {
                var row = (`<tr><td>` + e.firstName + ` ` + e.lastName + `</td>`);
                row += (`<td style="text-align: center;" id="` + e.employeeId + `receivercount">0</td>`);
                row += (`<td style="text-align: center;" id="` + e.employeeId + `sendercount">0</td>`);
                row += (`<td style="text-align: center;" id="` + e.employeeId + `offcount">0</td></tr>`);
                contentRows.append(row);
            });
            getWeeklyGuideCount(d);
            getWeeklyOffCount(d);
        },
        error: function (j, status, thrown) {
            $("#errorMessages")
                .append($('<li>')
                    .attr({ class: 'list-group-item list-group-item-danger' })
                    .text('Error calling web service. Please try again later.'));
        }
    });
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
}

function addErrorMsg() {
    $('#errorMessages')
        .append($('<li>')
            .attr({ class: 'list-group-item list-group-item-danger' })
            .text('The date you selected currently does not have a generated schedule.'));
}

function getWeeklyGuideCount(d) {
    $.ajax({
        type: "get",
        url: "/getWeeklyGuideCount/" + d,
        success: function (ids) {
            weeklyGuidesArr = [];
            weeklySenderArr = [];
            weeklyReceiverArr = [];
            ids.forEach(id => {
                weeklyGuidesArr.push(id);
            })
            for (i = 0; i < weeklyGuidesArr.length; i++) {
                if (i % 2 == 0) {
                    weeklyReceiverArr.push(weeklyGuidesArr[i]);
                } else {
                    weeklySenderArr.push(weeklyGuidesArr[i]);
                }
            }

            receivercounts = {};

            for (var i = 0; i < weeklyReceiverArr.length; i++) {
                var num = weeklyReceiverArr[i];
                receivercounts[num] = receivercounts[num] ? receivercounts[num] + 1 : 1;
            }

            sendercounts = {};

            for (var i = 0; i < weeklySenderArr.length; i++) {
                var num = weeklySenderArr[i];
                sendercounts[num] = sendercounts[num] ? sendercounts[num] + 1 : 1;
            }

            var keys = Object.keys(receivercounts);
            keys.forEach(key => {
                $("#" + key + "receivercount").text(receivercounts[key]);
            })

            keys = Object.keys(sendercounts);
            keys.forEach(key => {
                $("#" + key + "sendercount").text(sendercounts[key]);
            })
        }
    })
}

function getWeeklyOffCount(d) {
    $.ajax({
        type: "get",
        url: "/getWeeklyOffCount/" + d,
        success: function (ids) {
            weeklyOffArr = [];
            ids.forEach(id => {
                weeklyOffArr.push(id);
            })
            
            offcounts = {};

            for (var i = 0; i < weeklyOffArr.length; i++) {
                var num = weeklyOffArr[i];
                offcounts[num] = offcounts[num] ? offcounts[num] + 1 : 1;
            }

            var keys = Object.keys(offcounts);
            keys.forEach(key => {
                $("#" + key + "offcount").text(offcounts[key]);
            })

        }
    })
}