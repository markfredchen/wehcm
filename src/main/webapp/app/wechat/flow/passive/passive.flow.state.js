/**
 * Created by markfredchen on 6/27/15.
 */
'use strict';
angular.module('wehcmApp')
    .config(['$stateProvider', function ($stateProvider) {
        $stateProvider.state('passiveFlow', {
            parent: 'wechat',
            url: '/passive/flow',
            views: {
                'content@': {
                    templateUrl: 'app/wechat/flow/passive/passive.flow.tpl.html',
                    controller: 'PassiveFlowController',
                }
            },

            resolve: {
                data: function ($http, $location, $q) {
                    var deferred = $q.defer();
                    var messageOID = $location.search().messageOID;
                    $http.get('/passive/flow/' + messageOID)
                        .success(function (data, status, headers, config) {
                            deferred.resolve(data);
                        }).error(function (data) {
                            deferred.reject(data);
                        });
                    return deferred.promise;
                }


            }
        });
    }]);
