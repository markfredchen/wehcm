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
    'URLBuilder'
    ])
    .config(function ($urlRouterProvider, $httpProvider, $translateProvider) {
        $urlRouterProvider.otherwise('/');

        //enable CSRF
        $httpProvider.defaults.xsrfCookieName = 'CSRF-TOKEN';
        $httpProvider.defaults.xsrfHeaderName = 'X-CSRF-TOKEN';

        $translateProvider.useLoader('MessageLoader');
        $translateProvider.useSanitizeValueStrategy('sanitize');
        $translateProvider.preferredLanguage('zh_CN');
        $translateProvider.useCookieStorage();

    });

