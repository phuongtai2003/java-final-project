$(function () {
    $("#input-search").on("keyup", function () {
        const rex = new RegExp($(this).val(), "i");
        $(".search-table .search-items:not(.header-item)").hide();
        $(".search-table .search-items:not(.header-item)")
            .filter(function () {
                return rex.test($(this).text());
            })
            .show();
    });

    $("#btn-add-product").on("click", function (event) {
        $("#addProductModal #btn-add").show();
        console.log("add show");
        $("#addProductModal #btn-edit").hide();
        $("#addProductModal").modal("show");
    });
});