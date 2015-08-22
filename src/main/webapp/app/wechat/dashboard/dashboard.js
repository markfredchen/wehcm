/**
 * Created by markfredchen on 8/18/15.
 */
'use strict';
angular.module('wehcmApp')
    .config(function ($stateProvider) {
        $stateProvider.state('dashboard', {
            parent: 'wechat',
            url: '/',
            views: {
                'content@': {
                    templateUrl: 'app/wechat/dashboard/dashboard.tpl.html',
                    controller: 'DashBoardCtrl'
                }
            }
        })
    })
    .controller('DashBoardCtrl', function ($scope, $location) {
        $scope.data = $location.search();
        $scope.employeeTotalDeviationRatio = {
            title: '员工总数偏差率(%)',
            min: -20,
            max: 20,
            valueSuffix: '%',
            value: 3.61,
            plotBands: [{
                from: -20,
                to: -12,
                color: '#DF5353' // red
            }, {
                from: -12,
                to: -4,
                color: '#DDDF0D' // yellow
            }, {
                from: -4,
                to: 4,
                color: '#55BF3B' // green
            }, {
                from: 4,
                to: 12,
                color: '#DDDF0D' // yellow
            }, {
                from: 12,
                to: 20,
                color: '#DF5353' // red
            }]
        };
        $scope.revenueGrowthRate = {
            title: '营业收入增长率(%)',
            min: -20,
            max: 50,
            valueSuffix: '%',
            value: -2.23,
            plotBands: [{
                from: -20,
                to: -15,
                color: '#DF5353' // red
            }, {
                from: -15,
                to: 0,
                color: '#DDDF0D' // yellow
            }, {
                from: 0,
                to: 50,
                color: '#55BF3B' // green
            }]
        };
        $scope.inputOuputRatio = {
            title: '投入产出率(%)',
            min: 10,
            max: 28,
            valueSuffix: '%',
            value: 17.62,
            plotBands: [{
                from: 10,
                to: 12,
                color: '#DF5353' // red
            }, {
                from: 12,
                to: 18,
                color: '#DDDF0D' // yellow
            }, {
                from: 18,
                to: 28,
                color: '#55BF3B' // green
            }]
        };
        $scope.resourceExpenseRate = {
            title: '人事费用率(%)',
            min: 6,
            max: 20,
            valueSuffix: '%',
            value: 12.24,
            plotBands: [{
                from: 6,
                to: 10,
                color: '#DF5353' // red
            }, {
                from: 10,
                to: 12,
                color: '#DDDF0D' // yellow
            }, {
                from: 12,
                to: 20,
                color: '#55BF3B' // green
            }]
        };
        $scope.costAnalysis = {
            title: '人工成本分析(百万元)',
            categories: [,'201409','201410','201411','201412'],
            valueSuffix: '百万元',
            yAxisTitle: '成本 (百万元)',
            data: [{
                name: '事业部A',
                data: [1, 1, 1, 1, 1]
            }, {
                name: '事业部B',
                data: [1, 1, 1, 5, 1]
            }, {
                name: '事业部C',
                data: [46, 42, 40, 61, 52]
            }, {
                name: '事业部D',
                data: [9.1, 9, 9, 9.7, 8.2]
            }]
        }
    })
