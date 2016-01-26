'use strict';

angular.module('donderecicloApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


