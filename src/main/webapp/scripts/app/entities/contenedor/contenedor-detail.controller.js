'use strict';

angular.module('donderecicloApp')
    .controller('ContenedorDetailController', function ($scope, $rootScope, $stateParams, entity, Contenedor, User) {
        $scope.contenedor = entity;
        $scope.load = function (id) {
            Contenedor.get({id: id}, function(result) {
                $scope.contenedor = result;
            });
        };
        var unsubscribe = $rootScope.$on('donderecicloApp:contenedorUpdate', function(event, result) {
            $scope.contenedor = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
