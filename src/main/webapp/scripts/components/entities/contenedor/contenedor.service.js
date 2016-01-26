'use strict';

angular.module('donderecicloApp')
    .factory('Contenedor', function ($resource, DateUtils) {
        return $resource('api/contenedors/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
