let dropdown1 = $('#fromCurrency');
let dropdown2 = $('#toCurrency');
let dropdown4 = $('#addCurrency')

dropdown1.empty();
dropdown2.empty();
dropdown4.empty();

dropdown1.append('<option selected="true" disabled>Choose Currency</option>');
dropdown2.append('<option selected="true" disabled>Choose Currency</option>');
dropdown4.append('<option selected="true" disabled>Choose Currency</option>');

const url = '/currencies';

$.getJSON(url, function (data) {
    $.each(data, function (key, entry) {
        dropdown1.append($('<option></option>').attr('value', entry.currency).text(entry.currency));
        dropdown2.append($('<option></option>').attr('value', entry.currency).text(entry.currency));
        dropdown4.append($('<option></option>').attr('value', entry.currency).text(entry.currency));
    })
})

$("#convertBtn").click(function(e) {
    e.preventDefault();
    let from = $('#fromCurrency').children("option").filter(":selected").text();
    let to = $('#toCurrency').children("option").filter(":selected").text();
    let value = $('#amount').val();

    $.ajax({
        type: "POST",
        url: "/result",
        data: JSON.stringify({
            "from": from,
            "to": to,
            "value": value
        }),
        contentType: "application/json;",
        success: function(data) {
            if(data != 0) {
                $("#default").text(data)
                $("#message").html('<div></div>')
                updateChart(from,to,value);
            } else {
                $("#message").html('<div align="center" class="alert alert-danger">Some required information is missing or incomplete. ' +
                    'Please correct your entries and try again.</div>')
            }
        },
        error: function() {
            $("#message").text('There was a problem converting!')
        }
    });

});

function updateChart(from,to, value){
    $.ajax({
        type: "POST",
        url: "/data-chart",
        data: JSON.stringify({
            "from": from,
            "to": to,
            "value": value
        }),
        contentType: "application/json;",
        success: function(result) {
            chart.update({
                series: [{
                    name: result[0].currency,
                    data: result[0].rates
                },{
                    name: result[1].currency,
                    data: result[1].rates
                }]})

        }
    })
}

$(document).ready(function(){
    let url = '/data-table';
    $.getJSON(url, function (data) {
        let values;
        $.each(data,function(key,value){
            values = $('<tr>');
            values.append('<td>' + value.currency + '</td>');
            values.append('<td>' + value.amount + '</td>');
            values.append('<td>' + value.difference + '</td>');
            values.append('<td>' + value.differencePercentage + '</td>');
            values.append('<td><input type="button" value="X"></input></td>');
            $('#ratesTable').append(values);
        })
    });
});
$('table').on('click', 'input[type="button"]', function(e){
    $(this).closest('tr').remove()
})

$("#addRow").click(function() {
    let add = $('#addCurrency').children("option").filter(":selected").text();
    $.ajax({
        type: "POST",
        url: "/new-row",
        data: JSON.stringify({
            "currency": add
        }),
        contentType: "application/json;",
        success: function (data) {
            let values = $('<tr>');
            values.append('<td>' + data.currency + '</td>');
            values.append('<td>' + data.amount + '</td>');
            values.append('<td>' + data.difference + '</td>');
            values.append('<td>' + data.differencePercentage + '</td>');
            values.append('<td><input type="button" value="X"></input></td>');
            $('#ratesTable').append(values);
        },
        error: function () {
            alert('There was a problem converting!')
        }
    });
});

let chart = Highcharts.chart('myChart', {

    title: {
        text: 'Current Exchange Rates'
    },

    subtitle: {
        text: ''
    },

    yAxis: {
        title: {
            text: 'Change in EUR'
        }
    },

    xAxis: {
        type: 'datetime'
    },

    legend: {
        layout: 'vertical',
        align: 'right',
        verticalAlign: 'middle'
    },

    plotOptions: {
        series: {
            label: {
                connectorAllowed: false
            },
            pointStart: Date.now() - 6 * 24 * 60 * 60 * 1000,
            pointInterval: 24 * 3600 * 1000
        }
    },

    series: [{
        name: "from",
        data: [0,0,0,0,0,0,0]
    }, {
        name: "to",
        data: [0,0,0,0,0,0,0]
    }],

    responsive: {
        rules: [{
            condition: {
                maxWidth: 200
            },
            chartOptions: {
                legend: {
                    layout: 'horizontal',
                    align: 'center',
                    verticalAlign: 'bottom'
                }
            }
        }]
    }

});