/**
 * Created by markfredchen on 7/5/15.
 */
'use strict';
angular.module('wehcmApp')
    .config(function ($stateProvider) {
        $stateProvider.state('wechat', {
            abstract: true,
            resolve: {
                accountOID: function (localStorageService) {
                   return localStorageService.get('accountOID');
                },

                cssLoader: function (accountOID, angularLoad, $rootScope) {
                    if (!$rootScope.accountCSSLoaded) {
                        angularLoad.loadCSS('/clients/' + accountOID + '/default.css')
                            .then(function (data, status) {
                                $rootScope.accountCSSLoaded = true;
                            });
                    }

                }
            }
        })
    });
