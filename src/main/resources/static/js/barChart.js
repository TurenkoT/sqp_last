function makeBarChart(data, width, height, barcolor) {
    if (!width) width = 600;
    if (!height) height = 200;
    if (!barcolor) barcolor = "solid #000 1px";
    var chart = document.createElement("div");
    chart.style.position = "absolute";
    chart.style.width = width + "px";
    chart.style.height = height + "px";
    chart.style.border = "solid #80bdff 2px";
    chart.style.boxShadow = "0 0 0 0.2rem rgba(0,123,255,.25)";
    chart.style.paddingLeft = "10px";
    chart.style.paddingRight = "10px";
    chart.style.paddingTop = "0px";
    chart.style.paddingBottom = "0px";
    chart.style.backgroundColor = "white";
    var barwidth = 60;
    var maxdata = Math.max.apply(this, data);
    var scale = height / maxdata;
    for (var i = 0; i < data.length - 1; i++) {
        var bar = document.createElement("div");
        bar.style.position = "absolute";
        bar.style.left = (barwidth * i + 1 + 10 * i) + "px";
        bar.style.width = "60px";
        bar.style.height = data[i] * 2 + "px";
        bar.style.top =(height - 4 - data[i] * 2) + "px";
        if (data[i] >= 80.0){
            bar.style.backgroundColor = "#3c763d";
        }
        if (data[i] >= 50.0 && data[i] < 80.0) {
            bar.style.backgroundColor = "#e5ca1b";
        }
        if (data[i] < 50.0) {
            bar.style.backgroundColor = "#a31515";
        }
        bar.style.fontSize = "0px";
        chart.appendChild(bar);
    }
    return chart;
}