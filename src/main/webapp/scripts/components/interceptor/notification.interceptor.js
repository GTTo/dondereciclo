 'use strict';

angular.module('donderecicloApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-donderecicloApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-donderecicloApp-params')});
                }
                return response;
            }
        };
    });
