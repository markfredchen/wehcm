/**
 * Created by markfredchen on 6/27/15.
 */
'use strict';
angular.module('wehcmApp')
    .controller('PassiveFlowController', function ($scope, $http, $location, angularLoad, data) {
        $scope.data = data;

        $scope.config = null;
        $scope.messageOID = $location.search().messageOID;

        $scope.init = function () {
            console.log($scope.messageOID);
            $http.get('/passive/flow/' + $scope.messageOID)
                .success(function(data, status, headers, config) {
                    $scope.config = data;
                    if(data.hasCSS) {
                        angularLoad.loadCSS('/clients/' + data.accountOID + '/default.css')
                    }
                }).error(function (data, status) {
                    if(status == 405) {

                    }
                });
        };

        $scope.makeAction = function (action) {
            $http.post('/passive/flow/action', {messageOID: $scope.messageOID, action: action})
                .success(function (data, status) {
                    console.log(status);
                })
        };

        $scope.init();


    }).config(function ($translateProvider) {
        $translateProvider.useLoader('AccountMessageLoader');
    });
