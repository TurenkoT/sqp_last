$(document).ready(function () {
    var grid = document.getElementById('questionstable');
    $('#questionstable th').on('click', function () {
        sortGrid($(this).index(), $(this).data('type'));
    });

    function sortGrid(colNum, type) {
        var tbody = grid.getElementsByTagName('tbody')[0];
        var rowsArray = [].slice.call(tbody.rows);
        var compare;
        switch (type) {
            case 'number':
                compare = function (rowA, rowB) {
                    return rowA.cells[colNum].innerHTML - rowB.cells[colNum].innerHTML;
                };
                break;
            case 'string':
                compare = function (rowA, rowB) {
                    return rowA.cells[colNum].innerHTML.localeCompare(rowB.cells[colNum].innerHTML, "ru");
                };
                break;
            case 'boolean':
                compare = function (rowA, rowB) {
                };
                break;
        }
        rowsArray.sort(compare);
        grid.removeChild(tbody);
        for (var i = 0; i < rowsArray.length; i++) {
            tbody.appendChild(rowsArray[i]);
        }
        grid.appendChild(tbody);
    }


    $('.subdivision-container').each(function (i, el) {
        var arr = [];

        $(el).find('td').each(function (j, td) {
            arr.push($(td).text());
        });

        $(el).html(_.uniq(arr).join('<br>'));
    });
});