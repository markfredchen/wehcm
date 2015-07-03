/**
 * Created by markfredchen on 6/24/15.
 */
'use strict';
angular.module('wehcmApp')
    .config(['$stateProvider', function ($stateProvider){
        $stateProvider.state('flow', {
            url: '/flow',
            templateUrl: 'app/flow/form.flow.tpl.html',
            controller: 'FlowController',
            resolve: {
                test: function($location){
                    return $location.search();
                }
            }
        });
    }]);
