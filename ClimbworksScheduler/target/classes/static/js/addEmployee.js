(function () {

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
    
    $('#add-button').click(function () {

        var haveValidationErrors = checkAndDisplayValidationErrors($('#add-form').find('input'));

        if (haveValidationErrors) {
            return false;
        }

        $.ajax({
            type: 'POST',
            url: '/employees',
            data: JSON.stringify({
                firstName: $('#add-first-name').val(),
                lastName: $('#add-last-name').val(),
                locationId: parseInt($('#add-location').val()),
                username: ($('#add-username').val()),
                password: ($('#add-password').val()),
                active: 1
            }),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            'dataType': 'json',
            success: function() {
                location.href = "employees.html";
            },
            error: function() {
                $('#errorMessages')
                   .append($('<li>')
                   .attr({class: 'list-group-item list-group-item-danger'})
                   .text('Error calling web service.  Please try again later.'));
            }
        });
    });
})();

function checkAndDisplayValidationErrors(input) {
    // clear displayed error message if there are any
    $('#errorMessages').empty();
    // check for HTML5 validation errors and process/display appropriately
    // a place to hold error messages
    var errorMessages = [];

    if ($('#add-first-name').val().trim().length == 0) {
        errorMessages.push("First name is a required field.");
    }

    if ($('#add-first-name').val().trim().length > 25) {
        errorMessages.push("First name must be 25 characters or less.");
    }

    if ($('#add-last-name').val().trim().length == 0) {
        errorMessages.push("Last name is a required field.");
    }

    if ($('#add-last-name').val().trim().length > 25) {
        errorMessages.push("Last name must be 25 characters or less.")
    }

    if ($('#add-username').val().trim().length == 0) {
        errorMessages.push("Username is a required field.");
    }

    if ($('#add-username').val().trim().length > 25) {
        errorMessages.push("Username must be 25 characters or less.")
    }

    if ($('#add-password').val().trim().length > 255) {
        errorMessages.push("Username must be 255 characters or less.")
    }

    if ($('#add-username').val().trim().length == 0) {
        errorMessages.push("Password is a required field.");
    }

    // put any error messages in the errorMessages div
    if (errorMessages.length > 0){
        $.each(errorMessages,function(index,message){
            $('#errorMessages').append($('<li>').attr({class: 'list-group-item list-group-item-danger'}).text(message));
        });
        // return true, indicating that there were errors
        return true;
    } else {
        // return false, indicating that there were no errors
        return false;
    }
}