function handleButtonClick(id) {
    let form = $('#addToCart' + id);
    let quantity = $(form).find('input[name="quantity"]').val();

    let data = {};
    data["phoneId"] = id;
    data["quantity"] = quantity;

    $.ajax({
        type: "POST",
        url: "http://localhost:8080/phoneshop-web/ajaxCart",
        headers: {
            'Content-Type': 'application/json'
        },
        data: JSON.stringify(data),
    })
        .done(function (data) {
            $('#success').html("Item added to cart successfully");
            $('#errors').html("");
            $('#error' + id).html("");
        })
        .fail(function (data) {
            $('#errors').html("Error adding to cart");
            $('#success').html("");
            $('#error' + id).html("Wrong quantity value");
        });
}