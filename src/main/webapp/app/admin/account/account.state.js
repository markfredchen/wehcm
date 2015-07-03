/**
 * Created by markfredchen on 6/23/15.
 */
'use strict';
angular.module('wehcmApp')
    .config(['$stateProvider', function ($stateProvider){
        $stateProvider.state('account', {
            url: '/',
            templateUrl: 'app/admin/account/account.tpl.html',
            controller: 'AccountController'
        });
    }]);
