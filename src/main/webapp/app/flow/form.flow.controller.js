/**
 * Created by markfredchen on 6/24/15.
 */
'use strict';
angular.module('wehcmApp')
    .controller('FlowController', function ($scope, $location, angularLoad, $http,  URLBuilder) {
        $scope.config = {};
        $scope.data = {};

        $scope.submit = function () {
            $http({
                method: 'POST',
                url: '/form/flow',
                data: $.param({data: JSON.stringify($scope.data) }),
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            }).success(function (data,status) {
                console.log(status);
            })
        };

        $scope.init = function () {
            var url = URLBuilder.build('/form/flow', {userOID: $location.search().userOID, flowOID: $location.search().flowOID});

            $http.get(url).success(function(data, status, headers, config) {
                $scope.config = data;
                if(data.hasCSS) {
                    angularLoad.loadCSS('/clients/' + data.accountOID + '/default.css')
                }
            })

        };

        $scope.init();

    }).config(function ($translateProvider) {
        $translateProvider.useLoader('AccountMessageLoader');
    });
