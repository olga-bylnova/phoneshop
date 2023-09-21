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
            let parsedData = JSON.parse(data);
            $('#cartDisplay').html(`My cart: ${parsedData.totalQuantity}
                items ${parsedData.totalCost}$`);
            if (parsedData.isError) {
                $('#errors').html("Error adding to cart");
                $('#success').html("");
                $('#error' + id).html(parsedData.message);
            } else {
                $('#success').html(parsedData.message);
                $('#errors').html("");
                $('#error' + id).html("");
            }
        })
        .fail(function (data) {
            $('#errors').html("Error adding to cart");
            $('#success').html("");
            $('#error' + id).html("Wrong quantity value");
        });
}