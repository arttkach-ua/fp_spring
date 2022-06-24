function setCarModels() {
    console.log("brand selected");
    $('#carModel').find('option').remove();
    $('#carModel').append('<option value="-1" th:text="#{select_general_placeholder}">...</option>');

    let brand_id = $('#carBrand').val();
    console.log(brand_id);
    let data = {
        //operation: "car_model",
        id: brand_id
    };
    $.ajax({
        url: "/rentSpring/RestGetCarModels",
        method: "GET",
        data: data,
        success: function (data, textStatus, jqXHR) {
            console.log(data);
            let obj = $.parseJSON(data);
            $.each(obj, function (key, value) {
                $('#carModel').append('<option value="' + value.id + '">' + value.modelName + '</option>')
            });
            //$('select').formSelect();
        },
        error: function (jqXHR, textStatus, errorThrown) {
            $('#carModel').append('<option>Car model Unavailable</option>');
        },
        cache: false
    });
    }
function setCompleteSets() {
    $('#completeSet').find('option').remove();
    $('#completeSet').append('<option value="-1"><fmt:message key="select_general_placeholder"/></option>');

    let model_id = $('#carModel').val();
    let data = {
        //operation: "complete_set",
        id: model_id
    };
    $.ajax({
        url: "/rentSpring/RestGetCompleteSetsByCarModel",
        method: "GET",
        data: data,
        success: function (data, textStatus, jqXHR) {
            console.log(data);
            let obj = $.parseJSON(data);
            $.each(obj, function (key, value) {
                $('#completeSet').append('<option value="' + value.id + '">' + value.name + '</option>')
            });

        },
        error: function (jqXHR, textStatus, errorThrown) {
            $('#completeSet').append('<option>Complete set Unavailable</option>');
        },
        cache: false
    });

}