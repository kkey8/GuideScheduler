(function() {

    $("#calendarButton").on("click", function() {
        location.href = "admin/calendar.html";
    });
    $("#employeesButton").on("click", function() {
        location.href = "admin/employees.html";
    });
    $("#offRequestsButton").on("click", function() {
        location.href = "admin/offRequests.html";
    });
    $("#weekViewButton").on("click", function() {
        location.href = "admin/weekView.html";
    });
    $("#generateScheduleButton").on("click", function() {
        location.href = "admin/generateSchedule.html";
    });
    $("#signOutButton").on("click", function() {
        location.href = "index.html";
    });

})();