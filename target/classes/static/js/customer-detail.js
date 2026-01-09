$(document).ready(function(){

  var customerId = $('#customer-id').val();
  var orders = [];
  var summaryItem = '<div class="row summary-item" style="padding-left: 15px"> \
                            <div class="col product-name" style="padding-left:0;"></div> \
                            <div class="col text-right price"></div>\
                        </div>';

  $.ajax({
    type : "GET",
    url :  "/customer/orders/"+ customerId,
    contentType: "application/json",
    success : function(response) {
      orders = response;
      $.each(response, function (i, val){
        $('#items').append('<tr>\
                              <td>'+ val.id +'</td>\
                              <td>'+ val.date +'</td>\
                              <td><a class="btn btn-block select-order" data-order-id="'+ val.id +'"> Görüntüle</a></td>\
                            </tr>');
      });

    },
    error : function(e) {
      alert("AJAX error");
    }
  });

  $("body").on("click", ".select-order", function () {
    var customerId = $(this).data('order-id');
    $.each(orders, function (i, val){
      if(val.id == customerId)
      {
        $.each(val.entries, function (i, entry){
          var item = $(summaryItem);
          $(item).find('.product-name').text(entry.name);
          $(item).find('.price').text(entry.price +' TL');

          $('#summary-items').append(item);
        });
        $('#total-price').text('');
        $('#total-price').text(val.totalPrice +" TL");

        $('#summary-modal').modal('show');
        $('#selected-order').val(val.id);
      }
    });
  });

  $("body").on("click", "#print-data", function ()
  {
    var orderId = $('#selected-order').val();
    $.ajax({
      type : "GET",
      url :  "/customer/order/print/"+ orderId,
      contentType: "application/json",
      success : function(response) {
      },
      error : function(e) {
        alert("AJAX error");
      }
    });
  });
});
