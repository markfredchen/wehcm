/**
 * Created by markfredchen on 6/23/15.
 */
'use strict';
angular.module('wehcmApp')
    .controller('AccountController', ['$scope', 'Upload', function ($scope, Upload) {
        $scope.account = {};
        $scope.customCSSFile = null;


        $scope.upload = function () {
            if($scope.customCSSFile) {
                Upload.upload({
                    url: '/api/accounts/upload/css/' + $scope.account.accountOID,
                    file: $scope.customCSSFile
                }).success(function (data, status, headers, config) {
                    console.log('file ' + config.file.name + 'uploaded. Response: ' + data);
                });
            }
        };
    }])
