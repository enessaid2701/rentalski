$(document).ready(function(){

  var obj = {
    gender : 'male',
    orderType: 'full',
    sky : false,
    board : false,
    coats : false,
    glasses : false,
    helmet: false,
    sled : false,
    iceskate: false,
    cabinet: false,
    sledTime: 1,
    totalPrice : 0,
    discountPrice : 0,
    customer : null,
    extraDiscount: false,
    discountRate : 0,
    iceskateHour:0.5,
    nightsky:false,
    glove:false,
    socks:false
  };

  var customers = [];

  var summaryItem = '<div class="row summary-item" style="padding-left: 15px"> \
                            <div class="col product-name" style="padding-left:0;"></div> \
                            <div class="col text-right price"></div>\
                        </div>';

  function populateCustomer()
  {
    if(obj.customer == null)
    {
      obj.customer = {
        fullname : $('#full-name').val(),
        phoneNumber : $('#phone-number').val().replace(/\s/g, ""),
        age : parseInt($('#age').val()),
        weight : parseInt($('#weight').val()),
        height :  parseInt($('#height').val()),
        shoeSize : parseInt($('#shoe-size').val())
      }
    }
  }

  function postData()
  {
    var warningMessage = '';
    var existError = false;
    if($('#full-name').val() == "" )
    {
      warningMessage += '<p>İsim Soyisim Boş olmamalıdır.</p>';
      existError = true;
    }
    if($('#phone-number').val()  == "")
    {
      warningMessage += '<p>Telefon numarası boş olmamalıdır</p>';
      existError = true;
    }

    // if(obj.sky == true || obj.board == true)
    // {
    //   if( $('#weight').val() == ""
    //     || $('#age').val() == ""
    //     || $('#height').val() == ""
    //     || $('#shoe-size').val() == "")
    //   {
    //     warningMessage += '<p>Kullanıcı bilgileri boş olmamalıdır</p>';
    //     existError = true;
    //   }
    // }

    if(existError)
    {
      $('#warning-modal-body').find('p').remove();
      $('#warning-modal-body').append(warningMessage);
      $('#warning-modal').modal('show');
    }
    else
    {
      if(obj.extraDiscount)
      {
        $('#select-discount-modal').modal('show');
        $('#total-price-in-modal').text('');
        $('#total-price-in-modal').text(obj.totalPrice + " TL");
      }
      else
      {
        sendOjb();
      }
    }
  }

  function sendOjb()
  {
    var printerCode = $('#printer-code').val();
    obj.printerCode = parseInt(printerCode);
    populateCustomer();
    $.ajax({
      type : "POST",
      url :  "/create-order",
      contentType: "application/json",
      data : JSON.stringify(obj), // I tried "myMap="+JSON.stringify(myMap),  as well, it doesn't work neither
      success : function(response) {
        location.reload();
      },
      error : function(e) {
        alert("AJAX error");
      }
    });
  }

  $("#phone-number").inputmask({
    mask: '999 999 9999',
    placeholder: ' ',
    showMaskOnHover: false,
    showMaskOnFocus: false,
    onBeforePaste: function (pastedValue, opts) {
      var processedValue = pastedValue;
      return processedValue;
    }
  });

  $(".numeric").inputmask('integer',{min:1, max:100});
  $(".decimal").inputmask('decimal',{min:1, max:100});

  function populateCustomerInfo()
  {
    $('#full-name').val(obj.customer.fullname);
    $('#age').val(obj.customer.age);
    $('#weight').val(obj.customer.weight);
    $('#height').val(obj.customer.height);
    $('#shoe-size').val(obj.customer.shoeSize);
  }

  $("body").on("click", ".customer-select-btn", function () {
    var customerId = $(this).data('customer-id');
    $.each(customers, function (i, val){
      if(val.id == customerId)
      {
        obj.customer = val;
        $('#customer-select-modal').modal('hide');
        populateCustomerInfo();
      }
    });
  });

  $("body").on("click", "#save-with-discount", function () {
     sendOjb();
  });


  $('#search-customer').click(function (){
    var phoneNumber = $('#phone-number').val().replace(/\s/g, "");

    $.ajax({
      type : "GET",
      url :  "/customer/search/"+ phoneNumber,
      contentType: "application/json",
      success : function(response) {
        $('#customer-select-modal').modal('show');
        customers = response;
        $('#customer-select-table').find('tr').remove();
        $.each(response, function (i, val){
          $('#customer-select-table').append('<tr><td>'+ val.fullname +'</td><td><a class="btn btn-block customer-select-btn" href="#" data-customer-id="'+ val.id +'"> Seç</a></td></tr>')
        });
      },
      error : function(e) {
        alert("AJAX error");
      }
    });
  });


  $("#save").click( function(){
    postData();
  });

  $('#extra-hour').change(function() {
    if(this.checked) {
      var price = parseFloat($("[name='sled']").data(obj.orderType +'-price')) * 0.5;
      var oldPrice = parseFloat($("[name='sled']").data(obj.orderType +'-price')) * obj.sledTime;

      obj.totalPrice = (obj.totalPrice - oldPrice) + price;
      $('.price.sled').text('');
      $('.price.sled').text(price +' TL');

      $('#total-price').text('');
      $('#total-price').text(obj.totalPrice +" TL")
      obj.sledTime = 0.5;

      $('#sled-hour').prop( "disabled", true );
    }
    else
    {
      var price = parseFloat($("[name='sled']").data(obj.orderType +'-price')) * 1;
      var oldPrice = parseFloat($("[name='sled']").data(obj.orderType +'-price')) * obj.sledTime;

      obj.totalPrice = (obj.totalPrice - oldPrice) + price;
      $('.price.sled').text('');
      $('.price.sled').text(price +' TL');

      $('#total-price').text('');
      $('#total-price').text(obj.totalPrice +" TL")
      obj.sledTime = 1;
      $('#sled-hour').prop( "disabled", false );
      $('#sled-hour').val(1);
    }
  });

  $('#sled-hour').change(function ()
  {
    var hour = parseFloat($(this).val());
    var price = parseFloat($("[name='sled']").data(obj.orderType +'-price')) * hour;
    var oldPrice = parseFloat($("[name='sled']").data(obj.orderType +'-price')) * obj.sledTime;

    obj.totalPrice = (obj.totalPrice - oldPrice) + price;
    $('.price.sled').text('');
    $('.price.sled').text(price +' TL');

    $('#total-price').text('');
    $('#total-price').text(obj.totalPrice +" TL")
    obj.sledTime = hour;
  });


  $('#sket-hour').change(function ()
  {
    var hour = parseFloat($(this).val());
    var price = parseFloat($("[name='iceskate']").data(obj.orderType +'-price')) * hour;
    var oldPrice = parseFloat($("[name='iceskate']").data(obj.orderType +'-price')) * obj.iceskateHour;

    obj.totalPrice = (obj.totalPrice - oldPrice) + price;
    $('.price.iceskate').text('');
    $('.price.iceskate').text(price +' TL');

    $('#total-price').text('');
    $('#total-price').text(obj.totalPrice +" TL")
    obj.iceskateHour = hour;
  });

  function updateSummary(orderType)
  {
    $.each( obj, function(i, value)
    {
      if(value === true)
      {
        $('.row.summary-item.'+i).remove();
      }
    });
    obj.totalPrice = 0;

    $.each( obj, function(i, value)
    {
      if(value === true && i!='extraDiscount')
      {
        $("[name='"+ i +"']").val();

        var productName = $("[name='"+ i +"']").data('name');
        var inputName = $("[name='"+ i +"']").attr('name');
        var price = parseFloat($("[name='"+ i +"']").data(orderType +'-price'));

        if(inputName == 'sled')
        {
          price = parseFloat($("[name='"+ i +"']").data(orderType +'-price'))  * obj.sledTime;
        }
        if(inputName == 'iceskate')
        {
          price = parseFloat($("[name='"+ i +"']").data(orderType +'-price'))  * obj.iceskateHour;
        }

        var item = $(summaryItem);
        console.log("--------------------------")
        console.log(price)
        obj.totalPrice = obj.totalPrice + price;
        console.log("--------------------------1")
        console.log(price);
        console.log(obj);
        $(item).find('.product-name').text(productName);
        $(item).find('.product-name').addClass(inputName)
        $(item).find('.price').text(price +' TL');
        $(item).find('.price').addClass(inputName)
        $(item).addClass(inputName);

        $('#total-price').text(obj.totalPrice +" TL")

        $('#items').append(item);
      }
    });

  }

  $('input.discount-rate:radio').change(function()
  {
    var ratio = parseInt($(this).val());
    obj.discountRate = ratio;
    console.log(obj)
    obj.discountPrice = (obj.totalPrice * ratio) / 100;
    $('#total-price-in-modal').text('');
    $('#total-price-in-modal').text((obj.totalPrice - obj.discountPrice) + " TL");
  })

  $('input.gender:radio').change(function()
  {
    obj.gender = $(this).val();
  });

  $('input.order-type:radio').change(function()
  {
    if($(this).val() != obj.orderType.toLowerCase())
    {
      if($(this).val() == 'extra')
      {
        obj.orderType = 'full';
        obj.extraDiscount = true;
        updateSummary('full');
      }
      else
      {
        obj.extraDiscount = false;
        obj.orderType = $(this).val().toLowerCase();
        updateSummary($(this).val().toLowerCase());
      }
    }
  });

  $('input.select-product:radio').change(function(){

    var productName = $(this).data('name');
    var inputName = $(this).attr('name');
    var price = parseFloat($(this).data(obj.orderType.toLowerCase() +'-price'));
    var item = $(summaryItem);

    if($(this).val() == 'yes')
    {
      obj[inputName] = true;

      if(inputName == 'iceskate')
      {
        price = price * obj.iceskateHour;
      }

      obj.totalPrice = obj.totalPrice + price;

      $(item).find('.product-name').text(productName);
      $(item).find('.price').text(price +' TL');

      $(item).find('.product-name').addClass(inputName);
      $(item).find('.price').addClass(inputName);
      $(item).addClass(inputName);

      $('#total-price').text(obj.totalPrice +" TL")

      $('#items').append(item);
    }
    else if($(this).val() == 'no')
    {
      if(obj[inputName] == true)
      {
        obj[inputName] = false;
        if(inputName == 'iceskate')
        {
          price = price * obj.iceskateHour;
        }
        $('.row.summary-item.'+inputName).remove();

        obj.totalPrice = obj.totalPrice - price;
        console.log("--------------------------1")
        console.log(price);
        console.log(obj.totalPrice);

        $('#total-price').text();
        $('#total-price').text(obj.totalPrice +" TL")
      }
    }

    // if((inputName == 'board' || inputName == 'sky') && $(this).val() == 'yes')
    // {
    //   $('.customer-info').removeClass('customer-info-hide');
    // }
    // else if((inputName == 'board' || inputName == 'sky') && $(this).val() == 'no')
    // {
    //   $('.customer-info').addClass('customer-info-hide');
    // }

    if(inputName == 'sled'  && $(this).val() == 'yes')
    {
      $('#sled-time-area').removeClass('customer-info-hide');

    }else if(inputName == 'sled'  && $(this).val() == 'no'){
      $('#sled-time-area').addClass('customer-info-hide');
    }

    if(inputName == 'iceskate'  && $(this).val() == 'yes')
    {
      $('#iceskate-time-area').removeClass('customer-info-hide');
      $('#sket-hour').val('0.5').trigger("change");

    }else if(inputName == 'iceskate'  && $(this).val() == 'no'){
      $('#iceskate-time-area').addClass('customer-info-hide');
    }
  });


  $('.btn-number').click(function(e){
    e.preventDefault();

    fieldName = $(this).attr('data-field');
    type      = $(this).attr('data-type');
    var input = $("input[name='"+fieldName+"']");
    var currentVal = parseFloat(input.val());
    if (!isNaN(currentVal)) {
      if(type == 'minus') {

        if(currentVal > input.attr('min')) {
          input.val(currentVal - 0.5).change();
        }
        if(parseInt(input.val()) == input.attr('min')) {
          $(this).attr('disabled', true);
        }

      } else if(type == 'plus') {

        if(currentVal < input.attr('max')) {
          input.val(currentVal + 0.5).change();
        }
        if(parseInt(input.val()) == input.attr('max')) {
          $(this).attr('disabled', true);
        }

      }
    } else {
      input.val(0);
    }
  });
  $('.input-number').focusin(function(){
    $(this).data('oldValue', $(this).val());
  });
  $('.input-number').change(function() {

    minValue =  parseFloat($(this).attr('min'));
    maxValue =  parseFloat($(this).attr('max'));
    valueCurrent = parseFloat($(this).val());

    name = $(this).attr('name');
    if(valueCurrent >= minValue) {
      $(".btn-number[data-type='minus'][data-field='"+name+"']").removeAttr('disabled')
    } else {
      alert('Sorry, the minimum value was reached');
      $(this).val($(this).data('oldValue'));
    }
    if(valueCurrent <= maxValue) {
      $(".btn-number[data-type='plus'][data-field='"+name+"']").removeAttr('disabled')
    } else {
      alert('Sorry, the maximum value was reached');
      $(this).val($(this).data('oldValue'));
    }
  });

  $(".input-number").keydown(function (e) {
    // Allow: backspace, delete, tab, escape, enter and .
    if ($.inArray(e.keyCode, [46, 8, 9, 27, 13, 190]) !== -1 ||
      // Allow: Ctrl+A
      (e.keyCode == 65 && e.ctrlKey === true) ||
      // Allow: home, end, left, right
      (e.keyCode >= 35 && e.keyCode <= 39)) {
      // let it happen, don't do anything
      return;
    }
    // Ensure that it is a number and stop the keypress
    if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)) {
      e.preventDefault();
    }
  });
});
