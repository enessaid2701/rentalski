$(function () {
  $('#search-btn').click(function ()
  {
    var date = $('#date-input').val();
    window.location.href = '/dashboard/'+ date
  });
});
