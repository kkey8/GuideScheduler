var gList;
var empIds = [];

(function () {

    // var theD = new Date($("#mydate").val());
    // theD = theD.toISOString().slice(0, 10);

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
    $("#saveButton").on("click", function () {

        if ($("#mydate").val() == '') {
            removeStuff();
            addErrorMsg();
            return;
        }

        removeStuff();
        var theD = new Date($("#mydate").val());

        theD = theD.toISOString().slice(0, 10);

        if (typeof gList == 'undefined') {
            addErrorMsg2();
            return;
        }

        for (var i = 0; i < gList.length; i++) {
            empIds.push(gList[i].employeeId);
        }

        $.ajax({
            type: "post",
            url: "/persistGuides/" + theD,
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            data: JSON.stringify(empIds),
            success: function () {
                alert("Schedule saved");
                window.location.href = "generateSchedule.html";
            },
            error: function () {
                $('#errorMessages')
                    .append($('<li>')
                        .attr({ class: 'list-group-item list-group-item-danger' })
                        .text('Please select a date.'));
            }
        })
    });
    $("#generateButton").on("click", function () {
        if ($("#mydate").val() == '') {
            removeStuff();
            addErrorMsg();
            return;
        }
        removeStuff();
        var theD = new Date($("#mydate").val());

        if (theD.getDay() == 0) {
            addErrorMsg3();
            return;
        }

        var now = new Date();
        
        if (!(theD >= now)) {
            removeStuff();
            addErrorMsg4();
            return;
        }

        theD = theD.toISOString().slice(0, 10);

        getGuides(theD);
    });

})();

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

function checkAndDisplayValidationErrors(input) {
    // clear displayed error message if there are any
    $('#errorMessages').empty();
    // check for HTML5 validation errors and process/display appropriately
    // a place to hold error messages
    var errorMessages = [];

    // loop through each input and check for validation errors
    input.each(function () {
        // Use the HTML5 validation API to find the validation errors
        if (!this.validity.valid) {
            var errorField = $('label[for=' + this.id + ']').text();
            errorMessages.push(errorField + ' ' + this.validationMessage);
        }
    });

    // put any error messages in the errorMessages div
    if (errorMessages.length > 0) {
        $.each(errorMessages, function (index, message) {
            $('#errorMessages').append($('<li>').attr({ class: 'list-group-item list-group-item-danger' }).text(message));
        });
        // return true, indicating that there were errors
        return true;
    } else {
        // return false, indicating that there were no errors
        return false;
    }
}

function getGuides(theD) {

    var contentRows = $("#contentRows");

    $.ajax({
        type: "get",
        url: "/getGuides/" + theD,
        success: function (guides) {
            
            gList = guides;
            for (var i = 0; i < 8; i++) {
                var row = `<tr><td id="timeslot` + i + `"></td>`;
                row += `<td>` + guides[i].firstName + ` ` + guides[i].lastName + `</td>`;
                row += `<td>` + guides[i + 8].firstName + ` ` + guides[i + 8].lastName + `</td></tr>`;
                contentRows.append(row);
            }
            getTimes();
        },
        error: function () {
            console.log("you messed up a a ron");
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
            .text('Please select a date.'));
}

function addErrorMsg2() {
    $('#errorMessages')
        .append($('<li>')
            .attr({ class: 'list-group-item list-group-item-danger' })
            .text('You must generate a schedule before saving it.'));
}

function addErrorMsg3() {
    $('#errorMessages')
        .append($('<li>')
            .attr({ class: 'list-group-item list-group-item-danger' })
            .text('You cannot generate a schedule with this date, for this day is a Sunday.'));
}

function addErrorMsg4() {
    $('#errorMessages')
        .append($('<li>')
            .attr({ class: 'list-group-item list-group-item-danger' })
            .text('This date is in the past. Please select any date from today forth.'));
}

function successMsg() {
    $('#errorMessages')
        .append($('<li>')
            .attr({ class: 'list-group-item list-group-item' })
            .text('Successful save.'));
}