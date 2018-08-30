$(document).ready(function () {
    var timeout = null;
    // remember initial subdivision/profession table body
    // to quick restore when search text is empty
    var professionTbodyContent = $(".js-prof-table tbody").html();
    var subjectTbodyContent = $(".js-subj-table tbody").html();
    var relationTbodyContent = $(".js-relation-table tbody").html();

    $("#js-searchProf").on("keyup paste", function () {
        clearTimeout(timeout);
        timeout = setTimeout(function() {
                  var value = $("#js-searchProf").val().toUpperCase();
                  var $rows = $(".js-prof-table tr").slice(1);
                  if (value === '') {
                      $(".js-prof-table tbody").html(professionTbodyContent);
                      return;
                  }

                  $rows.each(function (index) {
                          $row = $(this);
                          var rowText = $row.text().toUpperCase();
                          if (rowText.indexOf(value) > -1) {
                            $row.show();
                          } else {
                            $row.hide();
                          }
                  });
        }, 500);
    });

    $("#js-searchrel").on("keyup paste", function () {
        clearTimeout(timeout);
        timeout = setTimeout(function() {
            var value = $("#js-searchrel").val().toUpperCase();
            var $rows = $(".js-relation-table tr").slice(1);
            if (value === '') {
                $(".js-relation-table tbody").html(relationTbodyContent);
                return;
            }

            $rows.each(function (index) {
                $row = $(this);
                var rowText = $row.text().toUpperCase();
                if (rowText.indexOf(value) > -1) {
                    $row.show();
                } else {
                    $row.hide();
                }
            });
        }, 500);
    });

    $("#js-search-subj").on("keyup paste", function () {
        clearTimeout(timeout);
        timeout = setTimeout(function() {
            var value = $("#js-search-subj").val().toUpperCase();
            var $rows = $(".js-subj-table tr").slice(1);
            if (value === '') {
                $(".js-subj-table tbody").html(subjectTbodyContent);
                return;
            }

            $rows.each(function (index) {
                $row = $(this);
                var rowText = $row.text().toUpperCase();
                if (rowText.indexOf(value) > -1) {
                    $row.show();
                } else {
                    $row.hide();
                }
            });
        }, 500);
    });
});