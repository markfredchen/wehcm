/**
 * Created by markfredchen on 7/5/15.
 */
'use strict';
angular.module('wehcmApp')
    .config(function($stateProvider){
        $stateProvider.state('wechat', {
            abstract: true,
            resolve: {
                accountOID: function ($http, $q) {
                    var deferred = $q.defer();
                    $http.get('/api/accounts/accountOID')
                        .success(function (data) {
                            deferred.resolve(data.accountOID);
                        })
                        .error(function (data) {
                            deferred.reject(data);
                        });
                    return deferred.promise;
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
