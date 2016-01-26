'use strict';

describe('Controller Tests', function() {

    describe('Contenedor Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockContenedor, MockUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockContenedor = jasmine.createSpy('MockContenedor');
            MockUser = jasmine.createSpy('MockUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Contenedor': MockContenedor,
                'User': MockUser
            };
            createController = function() {
                $injector.get('$controller')("ContenedorDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'donderecicloApp:contenedorUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
