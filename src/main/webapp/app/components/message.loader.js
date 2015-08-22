/**
 * Created by markfredchen on 6/29/15.
 */
'use strict';
angular.module('wehcmApp')
    .factory('AccountMessageLoader', function ($http, $q, angularLoad, localStorageService) {
        return function (options) {
            var deferred = $q.defer();
            var messages = {};
            var accountOID = localStorageService.get('accountOID');

            $http.get('/i18n/' + options.key + ".json")
                .success(function (data) {
                    messages = data;

                    $http.get('/clients/' + accountOID + "/i18n/" + options.key + ".json")
                        .success(function (data) {
                            deferred.resolve($.extend(messages, data));
                            console.log($.extend(messages, data));
                        }).error(function (data, status) {
                            console.log('Load account message failed with\ndata: ' + data + '\nstatus: ' + status);
                            deferred.resolve(messages);
                        });
                }).error(function (data, status) {
                    console.log('Load default message failed with\ndata: ' + data + '\nstatus: ' + status);
                    deferred.reject(options.key)
                });
            angularLoad.loadScript('/i18n/javascript/' + options.key + '.js');
            return deferred.promise;
        };
    });
