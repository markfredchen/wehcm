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
                config: function (URLBuilder, $location, accountOID, $http, $q, localStorageService) {
                    var deferred = $q.defer();
                    var params = $location.search();
                    var url = URLBuilder.build('/form/flow', {
                        code: params.code,
                        flowName: params.flowName,
                        accountOID: localStorageService.get('accountOID')
                    });
                    $http.get(url)
                        .success(function (data) {
                            deferred.resolve(data);
                        }).error(function (data) {
                            deferred.reject(url);
                        });
                    return deferred.promise;
                }
            }
        })
            .state('form-flow-success', {
                parent: 'wechat',
                url: '/flow/success',
                views: {
                    'content@': {
                        templateUrl: 'app/wechat/success/success.tpl.html',
                        controller: 'SuccessController'
                    }
                },
                resolve: {
                    messageKey: function () {
                        return 'form.flow.success.title';
                    }
                }
            });
    }]);
