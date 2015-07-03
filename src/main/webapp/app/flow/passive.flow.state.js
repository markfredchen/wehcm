/**
 * Created by markfredchen on 6/27/15.
 */
'use strict';
angular.module('wehcmApp')
    .config(['$stateProvider', function ($stateProvider) {
        $stateProvider.state('passiveFlow', {
            url: '/passive/flow',
            templateUrl: 'app/flow/passive.flow.tpl.html',
            controller: 'PassiveFlowController',
            resolve: {
                data: function ($http, $location) {
                    var messageOID = $location.search().messageOID;
                    return $http.get('/passive/flow/' + messageOID)
                        .success(function (data, status, headers, config) {
                            console.log(data);
                            return data;
                        });
                }


            }
        });
    }]);
