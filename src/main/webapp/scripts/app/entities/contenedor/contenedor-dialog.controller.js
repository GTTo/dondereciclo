'use strict';

angular.module('donderecicloApp').controller('ContenedorDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Contenedor', 'User',
        function($scope, $stateParams, $uibModalInstance, entity, Contenedor, User) {

        $scope.contenedor = entity;
        $scope.users = User.query();
        $scope.load = function(id) {
            Contenedor.get({id : id}, function(result) {
                $scope.contenedor = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('donderecicloApp:contenedorUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.contenedor.id != null) {
                Contenedor.update($scope.contenedor, onSaveSuccess, onSaveError);
            } else {
                Contenedor.save($scope.contenedor, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
