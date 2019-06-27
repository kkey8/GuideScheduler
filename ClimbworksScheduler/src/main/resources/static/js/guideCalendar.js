(function () {

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
    $("#findButton").on("click", function () {
        var theDate = $("#mydate").val();
        if (theDate == null || theDate == '') {
            alert("Please select a date.");
        }
        else {
            theDate = new Date(theDate);
            getSchedule(theDate);
        }
    });
    $("#cancelButton").on("click", function () {
        location.href = "calendar.html";
    });

})();

function getSchedule(d) {
    d = d.toISOString().slice(0, 10);
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
                for (var i = 0; i < 8; i++) {
                    var row = `<tr><td id="timeslot` + i + `"></td>`;
                    row += `<td>` + guides[2 * i].firstName + ` ` + guides[2 * i].lastName + `</td>`;
                    row += `<td>` + guides[2 * i + 1].firstName + ` ` + guides[2 * i + 1].lastName + `</td></tr>`;
                    contentRows.append(row);
                }
                getTimes();
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