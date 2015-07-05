/**
 * Created by markfredchen on 6/24/15.
 */
'use strict';
angular.module('wehcmApp')
    .config(['$stateProvider', function ($stateProvider) {
        $stateProvider.state('flow', {
            parent: 'wechat',
            url: '/flow',
            views: {
                'content@': {
                    templateUrl: 'app/wechat/flow/form/form.flow.tpl.html',
                    controller: 'FormFlowController',
                }
            },
            resolve: {
                config: function (URLBuilder, $location, accountOID, $http, $q) {
                    var deferred = $q.defer();
                    var params = $location.search();
                    var url = URLBuilder.build('/form/flow', {
                        userOID: params.userOID,
                        flowName: params.flowName,
                        accountOID: accountOID
                    });
                    console.log(accountOID);
                    $http.get(url)
                        .success(function (data) {
                            console.log(data);
                            deferred.resolve(data);
                        }).error(function (data) {
                            deferred.reject(url);
                        });
                    return deferred.promise;
                }
            }
        });
    }]);
