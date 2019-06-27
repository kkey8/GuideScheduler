var empIdArr = [];

(function () {

    loadEmployees();

    $("#calendarButton").on("click", function () {
        location.href = "calendar.html";
    });
    $("#ratingsButton").on("click", function () {
        location.href = "ratings.html";
    });
    $("#blocksButton").on("click", function () {
        location.href = "blocks.html";
    });
    $("#signOutButton").on("click", function () {
        location.href = "../index.html";
    });

    $("#saveButton").on("click", saveRatings);

    $("#cancelButton").on("click", function () {
        location.href = "guideHome.html";
    })

})();

function loadEmployees() {

    var contentRows = $('#contentRows');

    $.ajax({
        type: "get",
        url: "/getEmployees",
        dataType: "json",
        success: function (emps) {
            emps.forEach(e => {
                var row = (`<tr><td>` + e.firstName + `</td>`);
                row += (`<td>` + e.lastName + `</td>`);
                row += (`<td style="text-align: center;"><input id="` + e.employeeId + `meh" name="` + e.employeeId + `" type="radio" value="1"></td>`);
                row += (`<td style="text-align: center;"><input id="` + e.employeeId + `neutral" name="` + e.employeeId + `" type="radio" value="2"></td>`);
                row += (`<td style="text-align: center;"><input id="` + e.employeeId + `uhhyesplz" name="` + e.employeeId + `" type="radio" value="3"></td></tr>`);
                contentRows.append(row);
                empIdArr.push(e.employeeId);
            });
            loadRatings();
        },
        error: function (j, status, thrown) {
            $("#errorMessages")
                .append($('<li>')
                    .attr({ class: 'list-group-item list-group-item-danger' })
                    .text('Error calling web service. Please try again later.'));
        }
    });
};

function loadRatings() {

    $.ajax({
        type: "get",
        url: "/getRatings",
        dataType: "json",
        success: function (ratings) {
            ratings.forEach(r => {
                var ratedId = r.ratedEmployeeId;
                var rate = r.rate;
                if (rate == 1) {
                    $(`#` + ratedId + `meh`).prop("checked", true);
                }
                if (rate == 3) {
                    $(`#` + ratedId + `uhhyesplz`).prop("checked", true);
                }
                if (rate == 2) {
                    $(`#` + ratedId + `neutral`).prop("checked", true);
                }

            })
        },
        error: function () {
            console.log("failed to load ratings");
        }
    })

}

function saveRatings() {

    var Ratings = [];

    empIdArr.forEach(id => {
        Ratings.push({rate: $('input[name=' + id + ']:checked').val(), raterId: 1, ratedEmployeeId: id});
    })

    $.ajax({
        type: "post",
        url: "/saveRatings",
        data: JSON.stringify(
            Ratings
        ),
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function() {
            window.location.href = "ratings.html";
        },
        error: function() {
            alert("didn't work");
        }
    })
}