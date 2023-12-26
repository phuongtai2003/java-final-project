if(localStorage.getItem('token') === null) {
    window.location.href = '/login';
}
else{
    $.ajax(
        {
            type: 'POST',
            url: '/api/token/checkToken',
            headers: {
                "Authorization": `${localStorage.getItem('token')}`,
                "Content-Type":"application/json"
            },
            success: function (response) {
                if(!response['success']){
                    window.location.href = "/login";
                }
                else if(response['data']['role'] !== "salesperson" && response['data']['role'] !== "admin"){
                    window.location.href = "/login";
                }
                else{
                    if(response['data']['role'] === "salesperson"){
                        const currentUser = JSON.parse(localStorage.getItem("userJson"));
                        if(currentUser['hasChangedPassword'] === false){
                            $(".management-item").css("display", "none");
                            $(".pos-item").css("display", "none");
                            $(".customer-item").css("display", "none");
                            $(".invoice-item").css("display", "none");
                            $(".product-item").css("display", "none");
                        }
                        $(".employee-item").css("display", "none");
                        $(".admin-item").css("display", "none");
                    }
                }
            },
            error: function (response) {
                window.location.href = "/login";
            }
        }
    )
}