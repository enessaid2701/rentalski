$(document).ready(function(){

  $('#search-btn').click(function (){
    var phoneNumber = $('#phone-number').val().replace(/\s/g, "");

    $.ajax({
      type : "GET",
      url :  "/customer/search/"+ phoneNumber,
      contentType: "application/json",
      success : function(response) {
        $('#customer-select-modal').modal('show');
        $('#customer-select-table').find('tr').remove();
        $.each(response, function (i, val){
          $('#customer-select-table').append('<tr><td>'+ val.fullname +'</td><td>'+ val.phoneNumber +'</td><td><a class="btn btn-block customer-select-btn" href="/customer/detail/'+ val.id +'" data-customer-id="'+ val.id +'"> Git</a></td></tr>')
        });
      },
      error : function(e) {
        alert("AJAX error");
      }
    });
  });

});
