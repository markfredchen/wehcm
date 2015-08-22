/**
 * Created by markfredchen on 8/15/15.
 */
'use strict';
angular.module('wehcmApp')
    .config(function ($stateProvider) {
        $stateProvider.state('oauth2', {
            parent: 'wechat',
            url: '/oauth2/authorize',
            views: {
                'content@': {
                    templateUrl: 'app/wechat/oauth2/oauth2.authorize.tpl.html',
                    controller: 'AuthorizeController'
                }
            }
        }).state('authorize.success', {
            parent: 'wechat',
            url: '/oauth2/authorize/success',
            views: {
                'content@': {
                    templateUrl: 'app/wechat/success/success.tpl.html',
                    controller: 'SuccessController'
                }
            },
            resolve: {
                messageKey: function () {
                    return 'oauth2.authorize.success.title';
                }
            }
        })
    })
