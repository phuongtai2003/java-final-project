$(function (){
    $("#btn-edit-customer").hide();

    $(".search-no").on("keyup", function () {
        var searchValue = $(this).val().toLowerCase();
        $(".table .customer-item:not(.header-item)").hide();
        $(".table .customer-item:not(.header-item)")
            .filter(function () {
                return (
                    $(this).find(".no").text().toLowerCase().includes(searchValue)
                )
            })
            .show();
    });

    $(".search-name").on("keyup", function () {
        var searchValue = $(this).val().toLowerCase();
        console.log(searchValue)
        $(".table .customer-item:not(.header-item)").hide();
        $(".table .customer-item:not(.header-item)")
            .filter(function () {
                return (
                    $(this).find(".name").text().toLowerCase().includes(searchValue)
                )
            })
            .show();
    });

    $(".search-address").on("keyup", function () {
        var searchValue = $(this).val().toLowerCase();
        $(".table .customer-item:not(.header-item)").hide();
        $(".table .customer-item:not(.header-item)")
            .filter(function () {
                return(
                    $(this).find(".address").text().toLowerCase().includes(searchValue)
                )
            })
            .show();
    });

    $(".search-phone").on("keyup", function () {
        var searchValue = $(this).val().toLowerCase();
        $(".table .customer-item:not(.header-item)").hide();
        $(".table .customer-item:not(.header-item)")
            .filter(function () {
                return (
                    $(this).find(".phone").text().toLowerCase().includes(searchValue)
                )
            })
            .show();
    });

    $(".search-bills").on("keyup", function () {
        var searchValue = $(this).val().toLowerCase();
        $(".table .customer-item:not(.header-item)").hide();
        $(".table .customer-item:not(.header-item)")
            .filter(function () {
                return(
                    $(this).find(".bills").text().toLowerCase().includes(searchValue)
                )
            })
            .show();
    });
});