let pageLogoutLink;

document.addEventListener("DOMContentLoaded", function () {
    document.getElementById("logoutLink").addEventListener("click", function (event) {
        event.preventDefault();
        pageLogoutLink = window.location.href;
        $.ajax({
            type: "GET",
            url: "/phoneshop-web/logout"
        })
            .done(function (data) {
                window.location.href = pageLogoutLink;
            });
    });
});