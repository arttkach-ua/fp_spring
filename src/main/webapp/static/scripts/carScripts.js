function setCarModels() {
    console.log("setCarModels script works");
    $('#complete_set').find('option').remove();
    $('#complete_set').append('<option><fmt:message key="select_general_placeholder"/></option>');

    let model_id = $('#car_model').val();
    let data = {
        operation: "complete_set",
        id: model_id
    };
    $.ajax({
        url: "GetCompleteSets",
        method: "GET",
        data: data,
        success: function (data, textStatus, jqXHR) {
            console.log(data);
            let obj = $.parseJSON(data);
            $.each(obj, function (key, value) {
                $('#complete_set').append('<option value="' + value.ID + '">' + value.name + '</option>')
            });

        },
        error: function (jqXHR, textStatus, errorThrown) {
            $('#complete_set').append('<option>Complete set Unavailable</option>');
        },
        cache: false
    });
    }
function setCompleteSets() {
    $('#complete_set').find('option').remove();
    $('#complete_set').append('<option><fmt:message key="select_general_placeholder"/></option>');

    let model_id = $('#car_model').val();
    let data = {
        operation: "complete_set",
        id: model_id
    };
    $.ajax({
        url: "GetCompleteSets",
        method: "GET",
        data: data,
        success: function (data, textStatus, jqXHR) {
            console.log(data);
            let obj = $.parseJSON(data);
            $.each(obj, function (key, value) {
                $('#complete_set').append('<option value="' + value.ID + '">' + value.name + '</option>')
            });

        },
        error: function (jqXHR, textStatus, errorThrown) {
            $('#complete_set').append('<option>Complete set Unavailable</option>');
        },
        cache: false
    });

}