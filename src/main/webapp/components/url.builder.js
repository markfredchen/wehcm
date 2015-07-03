/**
 * Created by markfredchen on 6/27/15.
 */
'use strict';
angular.module('URLBuilder', [])
    .provider('URLBuilder', function() {

        function encodeUriQuery(val, pctEncodeSpaces) {
            return encodeURIComponent(val).
                replace(/%40/gi, '@').
                replace(/%3A/gi, ':').
                replace(/%24/g, '$').
                replace(/%2C/gi, ',').
                replace(/%20/g, (pctEncodeSpaces ? '%20' : '+'));
        }

        /**
         * Angular's private buildUrl function, patched to refer to the public methods on the angular globals
         */
        function buildUrl(url, params) {
            if (!params) return url;
            var parts = [];
            angular.forEach(params, function(value, key) {
                if (value === null || angular.isUndefined(value)) return;
                if (!angular.isArray(value)) value = [value];

                angular.forEach(value, function(v) {
                    if (angular.isObject(v)) {
                        v = angular.toJson(v);
                    }
                    parts.push(encodeUriQuery(key) + '=' + encodeUriQuery(v));
                });
            });
            return url + ((url.indexOf('?') == -1) ? '?' : '&') + parts.join('&');
        }

        this.$get = function() {
            return {
                build: function(url, params) {
                    return buildUrl(url, params);
                }
            };

        };
    });
