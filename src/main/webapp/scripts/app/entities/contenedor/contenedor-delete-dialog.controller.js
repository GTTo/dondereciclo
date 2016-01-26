'use strict';

angular.module('donderecicloApp')
	.controller('ContenedorDeleteController', function($scope, $uibModalInstance, entity, Contenedor) {

        $scope.contenedor = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Contenedor.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
