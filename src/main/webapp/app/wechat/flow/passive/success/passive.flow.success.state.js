/**
 * Created by markfredchen on 7/22/15.
 */
'use strict';
angular.module('wehcmApp')
    .config(['$stateProvider', function ($stateProvider) {
        $stateProvider.state('passive-flow-success', {
            parent: 'wechat',
            url: '/passive/flow/success',
            views: {
                'content@': {
                    templateUrl: 'app/wechat/flow/passive/success/passive.flow.success.tpl.html'
                }
            }
        });
    }]);

