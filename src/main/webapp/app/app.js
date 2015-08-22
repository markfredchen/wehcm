/**
 * Created by markfredchen on 6/23/15.
 */
'use strict';
angular.module('wehcmApp', [
    'ngAnimate',
    'ngAria',
    'ngCookies',
    'ngMessages',
    'ngResource',
    'ui.router',
    'ngFileUpload',
    'angularLoad',
    'angular-datepicker',
    'pascalprecht.translate',
    'URLBuilder',
    'ngSanitize',
    'charts',
    'LocalStorageModule'
    ])
    .run(function (localStorageService, $http) {
        $http.get('/api/accounts/accountOID')
            .success(function (data) {
                localStorageService.set('accountOID', data.accountOID);
                console.log(localStorageService.get('accountOID'));
            })
            .error(function (data) {
                console.error('Cannot load accountOID');
            });
    })
    .config(function ($urlRouterProvider, $httpProvider, $translateProvider, localStorageServiceProvider) {
        $urlRouterProvider.otherwise('/');

        //enable CSRF
        $httpProvider.defaults.xsrfCookieName = 'CSRF-TOKEN';
        $httpProvider.defaults.xsrfHeaderName = 'X-CSRF-TOKEN';

        $translateProvider.useLoader('AccountMessageLoader');
        $translateProvider.useSanitizeValueStrategy('escape');
        $translateProvider.preferredLanguage('zh_CN');
        $translateProvider.useCookieStorage();

        localStorageServiceProvider.setPrefix('wehcm');

    });

