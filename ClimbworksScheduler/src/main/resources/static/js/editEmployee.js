var empAtHand;
var jobs;

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

    var id = window.location.hash.substring(1);
    var name = window.location.hash;
    var qMark = name.indexOf('?');
    name = name.substring(qMark + 1).replace('%20', ' ');

    $.ajax({
        type: "get",
        url: "/employeejobs/" + id,
        success: function (jobs) {
            $("#guideName").append(`<span>` + name + ":" + `</span>`);
            jobs.forEach(j => {
                $('input[value="' + j.jobId + '"]').attr("checked", "checked");
            })
        },
        error: function() {
            $("#errorMessages")
                .append($('<li>')
                .attr({class: 'list-group-item list-group-item-danger'})
                .text('Error calling web service. Please try again later.'));
        }
    });

    $("#saveButton").on("click", function() {
        // var value = 0;
        // $(":checkbox:checked").forEach(input => function() {
        //     value += input.value;
        // })

        var vals = [];

        console.log($(":checkbox:checked"));

        for (var i = 0; i < $(":checkbox:checked").length; i++) {
            console.log("good job. making progress");
            vals.push($(":checkbox:checked")[i].value);
        }

        $.ajax({
            type: "post",
            url: "/employeejobs/" + id,
            data: JSON.stringify(
                vals
            ),
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
        })
        
    })

    $("#cancelButton").on("click", function () {
        location.href = "employees.html";
    })

})();