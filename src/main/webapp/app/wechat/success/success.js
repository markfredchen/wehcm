/**
 * Created by markfredchen on 8/20/15.
 */
'use strict';
angular.module('wehcmApp')
    .controller('SuccessController', ['$scope', 'messageKey', function ($scope, messageKey) {
        $scope.messageKey = messageKey;
    }])
