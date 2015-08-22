/**
 * Created by markfredchen on 8/18/15.
 */
'use strict';
angular.module('charts', [])
    .directive('ngGauge', function () {
        return {
            scope: {
                config: '=config'
            },
            link: function (scope, element) {
                element.highcharts({
                        chart: {
                            type: 'gauge',
                            plotBackgroundColor: null,
                            plotBackgroundImage: null,
                            plotBorderWidth: 0,
                            plotShadow: false
                        },

                        title: {
                            text: scope.config.title
                        },

                        pane: {
                            startAngle: -150,
                            endAngle: 150,
                            background: [{
                                backgroundColor: {
                                    linearGradient: {x1: 0, y1: 0, x2: 0, y2: 1},
                                    stops: [
                                        [0, '#FFF'],
                                        [1, '#333']
                                    ]
                                },
                                borderWidth: 0,
                                outerRadius: '109%'
                            }, {
                                backgroundColor: {
                                    linearGradient: {x1: 0, y1: 0, x2: 0, y2: 1},
                                    stops: [
                                        [0, '#333'],
                                        [1, '#FFF']
                                    ]
                                },
                                borderWidth: 1,
                                outerRadius: '107%'
                            }, {
                                // default background
                            }, {
                                backgroundColor: '#DDD',
                                borderWidth: 0,
                                outerRadius: '105%',
                                innerRadius: '103%'
                            }]
                        },

                        // the value axis
                        yAxis: {
                            min: scope.config.min,
                            max: scope.config.max,

                            minorTickInterval: 'auto',
                            minorTickWidth: 1,
                            minorTickLength: 10,
                            minorTickPosition: 'inside',
                            minorTickColor: '#666',

                            tickPixelInterval: 30,
                            tickWidth: 2,
                            tickPosition: 'inside',
                            tickLength: 10,
                            tickColor: '#666',
                            labels: {
                                step: 2,
                                rotation: 'auto'
                            },

                            plotBands: scope.config.plotBands
                        },

                        series: [{
                            name: scope.config.title,
                            data: [10],
                            tooltip: {
                                valueSuffix: ' ' + scope.config.valueSuffix
                            }
                        }]

                    },
                    // Add some life
                    function (chart) {
                        var point = chart.series[0].points[0];
                        point.update(scope.config.value);
                    });
            }
        }
    })
    .directive('ngLine', function () {
        return {
            scope: {
                config: '=config'
            },
            link: function (scope, element) {
                element.highcharts({
                    title: {
                        text: scope.config.title
                    },
                    xAxis: {
                        categories: scope.config.categories
                    },
                    yAxis: {
                        title: {
                            text: scope.config.yAxisTitle
                        },
                        plotLines: [{
                            value: 0,
                            width: 1,
                            color: '#808080'
                        }]
                    },
                    tooltip: {
                        valueSuffix: scope.config.valueSuffix
                    },
                    legend: {
                        align: 'center',
                        verticalAlign: 'bottom',
                        borderWidth: 0
                    },
                    series: scope.config.data
                });
            }
        }
    })
