$(document).ready(function(){
    $("#demo").on("hide.bs.collapse", function(){
        $(".btn").html('<span class="glyphicon glyphicon-collapse-down"></span> Zmień hasło');
    });
    $("#demo").on("show.bs.collapse", function(){
        $(".btn").html('<span class="glyphicon glyphicon-collapse-up"></span> Zmień hasło');
    });
});