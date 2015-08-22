/**
 * Created by markfredchen on 8/17/15.
 */
'use strict';
angular.module('wehcmApp')
    .config(function ($stateProvider) {
        $stateProvider.state('error', {
            parent: 'wechat',
            url: '/error',
            views: {
                'content@': {
                    templateUrl: 'app/wechat/error/error.tpl.html'
                }
            }
        });
    });
