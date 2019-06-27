(function() {

    loadEmployees();

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

    $("#deleteButton").on("click", function() {

    })

})();

function loadEmployees() {

    var contentRows = $('#contentRows');

    $.ajax({
        type: "get",
        url: "/employees",
        dataType: "json",
        success: function (emps) {
            emps.forEach(e => {
                var row = (`<tr><td>` + e.lastName + `</td>`);
                row += (`<td>` + e.firstName + `</td>`);
                row += (`<td><a class="btn btn-outline-primary" data-type="edit" data-name="` + e.firstName + ` ` + e.lastName + `" data-value="` + e.employeeId + `">Edit</a></td>`);
                row += (`<td><a class="btn btn-outline-danger" data-type="delete" data-name="` + e.firstName + ` ` + e.lastName + `" data-value="` + e.employeeId + `">Delete</a></td></tr>`);
                contentRows.append(row);
            });
            $('a').on("click", function(){
                if ($(this).data("type") == "edit") {
                    window.location.href = "editEmployee.html#" + $(this).data("value") + `?` + $(this).data("name");
                } else {
                    window.location.href = "deleteEmployee.html#" + $(this).data("value") + `?` + $(this).data("name");
                }
            })
        },
        error: function(j, status, thrown) {
            $("#errorMessages")
                .append($('<li>')
                .attr({class: 'list-group-item list-group-item-danger'})
                .text('Error calling web service. Please try again later.'));
        }
    });
};