/**
 * Created by markfredchen on 6/27/15.
 */
'use strict';
angular.module('wehcmApp')
    .controller('PassiveFlowController', function ($scope, $http, $location, angularLoad, $state, data) {

        $scope.config = data;

        $scope.init = function () {
            console.log($scope.messageOID);
            $http.get('/passive/flow/' + $scope.messageOID)
                .success(function (data, status, headers, config) {
                    $scope.config = data;
                }).error(function (data, status) {
                    if (status == 405) {

                    }
                });
        };

        $scope.makeAction = function (action) {
            console.log($scope.config.messageOID);
            $http.post('/passive/flow/action', {messageOID: $scope.config.messageOID, action: action})
                .success(function (data, status) {
                    $state.go('passive-flow-success');
                })
        };
    });
