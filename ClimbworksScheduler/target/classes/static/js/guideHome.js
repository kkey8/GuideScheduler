(function () {

    var userId = localStorage.getItem("userId");

    $("#calendarButton").on("click", function () {
        location.href = "guide/calendar.html";
    });
    $("#ratingsButton").on("click", function () {
        location.href = "guide/ratings.html";
    });
    $("#blocksButton").on("click", function () {
        location.href = "guide/blocks.html";
    });
    $("#signOutButton").on("click", function () {
        location.href = "index.html";
    });

    var empsSelect = $('#employeeSelect');

    $.ajax({
        type: "get",
        url: "/employees",
        dataType: "json",
        success: function (emps) {
            emps.forEach(e => {
                var row = `<option data-value="` + e.employeeId + `">` + e.firstName + ` ` + e.lastName + `</option>`
                empsSelect.append(row);
            });
        },
        error: function (j, status, thrown) {
            $("#errorMessages")
                .append($('<li>')
                    .attr({ class: 'list-group-item list-group-item-danger' })
                    .text('Error calling web service. Please try again later.'));
        }
    });

    $("#goButton").on("click", function() {
        var userId = $("#employeeSelect").find(':selected').data('value');
        console.log(userId);
        localStorage.setItem("userId", userId);
    }

)})();