/**
 * Created by markfredchen on 8/15/15.
 */
'use strict';
angular.module('wehcmApp')
    .controller('AuthorizeController', function ($scope, $location, $http, $state) {
        $scope.param = $location.search();
        $scope.authorize = function () {
            $http({
                method: 'POST',
                url: '/weChat/authorize',
                data: $.param({userID: $scope.param.userID}),
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            }).success(function (data) {
                $state.go('authorize.success');
            });
        };
    });
