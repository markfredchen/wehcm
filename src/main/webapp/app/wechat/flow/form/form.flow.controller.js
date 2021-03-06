/**
 * Created by markfredchen on 6/24/15.
 */
'use strict';
angular.module('wehcmApp')
    .controller('FormFlowController', function ($scope, $location, angularLoad, $http, $state, config, accountOID, $window) {
        $scope.config = config;
        $scope.data = {};
        $scope.data.messageOID = $scope.config.messageOID;
        $scope.data.fromUser = $scope.config.username;
        $scope.data.accountOID = accountOID;
        $scope.data.flow = $scope.config.flowName;
        $scope.userAgent = $window.navigator.userAgent;

        $scope.submit = function () {
            $http({
                method: 'POST',
                url: '/form/flow',
                data: $.param({data: JSON.stringify($scope.data) }),
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            }).success(function (data,status) {
                $state.go('form-flow-success');
            })
        };

    }).config(function ($translateProvider) {
        $translateProvider.useLoader('AccountMessageLoader');
    });
