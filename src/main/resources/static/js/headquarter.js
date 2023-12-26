$(function () {
    $("#input-search").on("keyup", function () {
        var rex = new RegExp($(this).val(), "i");
        $(".search-table .search-items:not(.header-item)").hide();
        $(".search-table .search-items:not(.header-item)")
            .filter(function () {
                return rex.test($(this).text());
            })
            .show();
    });
});