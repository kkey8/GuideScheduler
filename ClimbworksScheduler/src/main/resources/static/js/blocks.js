(function() {

    $("#calendarButton").on("click", function() {
        location.href = "calendar.html";
    });
    $("#ratingsButton").on("click", function() {
        location.href = "ratings.html";
    });
    $("#blocksButton").on("click", function() {
        location.href = "blocks.html";
    });
    $("#signOutButton").on("click", function() {
        location.href = "../index.html";
    });
    $("#requestButton").on("click", function() {

        $.ajax({
            type: "post",
            url: "/submitOffRequest",
            data: JSON.stringify({
                employeeId: 1,
                offDate: $("#theDate")[0].value,
                isResolved: false
            }),
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json"
            },
            'dataType': 'json',
            success: function() {
                window.location.href = "blocks.html";
            },
            error: function() {
                console.log($("#theDate")[0].value);
                console.log("unsuccessful request");
            }
        })

    });
    $("#cancelButton").on("click", function() {
        window.location.href = "guideHome.html";
    })

})();