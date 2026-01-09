$(document).ready(function(){
  var summaryItem = '<div class="row summary-item" style="padding-left: 15px"> \
                            <div class="col product-name" style="padding-left:0;"></div> \
                            <div class="col text-right price"></div>\
                        </div>';

  $('.modal').on('hidden.bs.modal', function(e)
  {
    $(this).removeData();
  }) ;

  function search()
  {
    var orderCode = $('#order-id').val().replace(/\s/g, "");

    $.ajax({
      type : "GET",
      url :  "/order-search/"+ orderCode,
      contentType: "application/json",
      success : function(val) {
        $('#summary-items').html('');
        $.each(val.entries, function (i, entry){
          var item = $(summaryItem);
          $(item).find('.product-name').text(entry.name);
          $(item).find('.price').text(entry.price +' TL');

          $('#summary-items').append(item);
        });
        $('#total-price').text('');
        $('#total-price').text(val.totalPrice +" TL");


        $('#cancel-area').text("");
        if(val.status == 'CANCELLED')
        {
          $('#cancel-area').text("SİPARİS İPTAL EDİLDİ");
        }
        $('#summary-modal').modal('show');
        $('#selected-order').val(val.id);
      },
      error : function(e) {
        alert("Kayit Bulunamadi");
      }
    });
  }
  $('#search-btn').click(function (){
    search();
  });

  $("body").on("click", "#print-data", function ()
  {
    var orderId = $('#selected-order').val();
    $.ajax({
      type : "GET",
      url :  "/customer/order/print/"+ orderId,
      contentType: "application/json",
      success : function(response)
      {
        search();
      },
      error : function(e) {
        alert("AJAX error");
      }
    });
  });

  $("body").on("click", "#cancel-order", function ()
  {
    var orderId = $('#selected-order').val();
    $.ajax({
      type : "GET",
      url :  "/cancel-order/"+ orderId,
      contentType: "application/json",
      success : function(response) {
        alert('Siparis iptal edildi')
      },
      error : function(e) {
        alert("AJAX error");
      }
    });
  });
});
