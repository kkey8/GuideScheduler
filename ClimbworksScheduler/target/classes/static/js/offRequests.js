var e;

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

    var contentRows = $("#contentRows");

    $.ajax({

        type: "get",
        url: "/offrequests",
        success: function (offRequests) {

            offRequests.forEach(or => {
                console.log(or);
                e = $.ajax({
                    type: "get",
                    url: "/employee/" + or.employeeId,
                    success: function (e) {
                        var row = (`<tr><td>` + e.firstName + ` ` + e.lastName + `</td>`);
                        row += (`<td>` + or.offDate + `</td>`);
                        row += (`<td id="receiverCountDay` + or.offDayId + `"></td>`);
                        row += (`<td id="senderCountDay` + or.offDayId + `"></td>`);
                        row += (`<td><a class="btn btn-primary" data-type="accept" data-name="` + e.firstName + ` ` + e.lastName + `" data-value="` + or.offDayId + `">Accept</a></td>`);
                        row += (`<td><a class="btn btn-danger" data-type="reject" data-name="` + e.firstName + ` ` + e.lastName + `" data-value="` + or.offDayId + `">Reject</a></td></tr>`);
                        contentRows.append(row);
                        $('a').on("click", function () {
                            if ($(this).data("type") == "accept") {
                                acceptRequest($(this).data("value"));
                                window.location.href = "offRequests.html";
                            } else {
                                console.log($(this).data("value"));
                                rejectRequest($(this).data("value"));
                                window.location.href = "offRequests.html";
                            }
                        })
                        getReceivers(or.offDate, or.offDayId);
                        getSenders(or.offDate, or.offDayId);
                    },
                    error: function () {
                        console.log("you messed up getting employee");
                    }
                });
            });
        },
        error: function () {
            console.log("you messed up initially")
        }

    })

})();

function acceptRequest(id) {
    $.ajax({
        type: "post",
        url: "/acceptrequest/" + id,
        error: function () {
            console.log("error accepting request");
        }
    })
}

function rejectRequest(id) {
    $.ajax({
        type: "post",
        url: "/rejectrequest/" + id,
        error: function () {
            console.log("error rejecting request");
        }
    })
}

function getReceivers(offDate, odId) {
    $.ajax({
        type: "get",
        url: "/getAvailableReceivers/" + offDate,
        success: function (receivers) {
            $("#receiverCountDay" + odId).text(Object.keys(receivers).length);
        },
        error: function () {
            console.log("error rejecting request");
        }
    })
}

function getSenders(offDate, odId) {
    $.ajax({
        type: "get",
        url: "/getAvailableSenders/" + offDate,
        success: function (senders) {
            $("#senderCountDay" + odId).text(Object.keys(senders).length);
        },
        error: function () {
            console.log("error rejecting request");
        }
    })
}