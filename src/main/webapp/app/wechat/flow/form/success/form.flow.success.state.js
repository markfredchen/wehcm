/**
 * Created by markfredchen on 7/22/15.
 */
'use strict';
angular.module('wehcmApp')
    .config(['$stateProvider', function ($stateProvider) {
        $stateProvider.state('form-flow-success', {
            parent: 'wechat',
            url: '/flow/success',
            views: {
                'content@': {
                    templateUrl: 'app/wechat/flow/form/success/form.flow.success.tpl.html'
                }
            }
        });
    }]);

