$(document).ready(function () {
    // Has the popover been hidden once before?
    var hiddenOnce = false;
    $(function () {
        $("#popbadge1").popover('show');
        // Now it has
        shownOnce = true;
    });

    // New function:
    $(function () {
        // When anything is clicked...
        $('*').click(function (e) {
            // Except popbadge1 & if popover has not been hidden once before
            if (e.target.attr != 'popbadge1' && hiddenOnce === false) {
                // Hide the popover
                $("#popbadge1").popover('hide');
                // Set as hidden once before
                hiddenOnce = true;
            }
        });
    });
    $(function() {
  $("#popbadge1").focus();
});

    $("#popbadge1").popover({
        html: true,
        content: function () {
            return $('#hiddenpopbadge1').html();
        }
    });
    $("#popbadge2").popover({
        html: true,
        content: function () {
            return $('#hiddenpopbadge2').html();
        }
    });
    $("#popbadge3").popover({
        html: true,
        content: function () {
            return $('#hiddenpopbadge3').html();
        }
    });
});