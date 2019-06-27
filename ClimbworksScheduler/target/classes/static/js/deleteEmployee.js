(function() {

    $("#calendarButton").on("click", function() {
        location.href = "calendar.html";
    });
    $("#employeesButton").on("click", function() {
        location.href = "employees.html";
    });
    $("#offRequestsButton").on("click", function() {
        location.href = "offRequests.html";
    });
    $("#weekViewButton").on("click", function() {
        location.href = "weekView.html";
    });
    $("#generateScheduleButton").on("click", function() {
        location.href = "generateSchedule.html";
    });
    $("#signOutButton").on("click", function() {
        location.href = "../index.html";
    });

    $("#addEmployeeButton").on("click", function() {
        location.href = "addEmployee.html";
    });

    var id = window.location.hash.substring(1);
    var name = window.location.hash;
    var qMark = name.indexOf('?');
    name = name.substring(qMark + 1).replace('%20', ' ');

    $("#employeeName").text(name);

    $("#deleteButton").on("click", function() {
        $.ajax({
            type: "post",
            url: "/deleteemployee/" + id,
            success: function() {
                alert("Delete successful.");
                location.href = "employees.html";
            },
            error: function() {
                alert("Unsuccessful deletion.")
            }
        })
    })

    $("#cancelButton").on("click", function() {
        location.href = "employees.html";
    });

})();