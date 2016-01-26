'use strict';

angular.module('donderecicloApp')
    .controller('ContenedorController', function ($scope, $state, Contenedor) {

        $scope.contenedors = [];
        $scope.loadAll = function() {
            Contenedor.query(function(result) {
               $scope.contenedors = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.contenedor = {
                name: null,
                lat: null,
                lon: null,
                id: null
            };
        };
    });
