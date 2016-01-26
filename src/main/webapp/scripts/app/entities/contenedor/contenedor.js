'use strict';

angular.module('donderecicloApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('contenedor', {
                parent: 'entity',
                url: '/contenedors',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'donderecicloApp.contenedor.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/contenedor/contenedors.html',
                        controller: 'ContenedorController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('contenedor');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('contenedor.detail', {
                parent: 'entity',
                url: '/contenedor/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'donderecicloApp.contenedor.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/contenedor/contenedor-detail.html',
                        controller: 'ContenedorDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('contenedor');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Contenedor', function($stateParams, Contenedor) {
                        return Contenedor.get({id : $stateParams.id});
                    }]
                }
            })
            .state('contenedor.new', {
                parent: 'contenedor',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/contenedor/contenedor-dialog.html',
                        controller: 'ContenedorDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    lat: null,
                                    lon: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('contenedor', null, { reload: true });
                    }, function() {
                        $state.go('contenedor');
                    })
                }]
            })
            .state('contenedor.edit', {
                parent: 'contenedor',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/contenedor/contenedor-dialog.html',
                        controller: 'ContenedorDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Contenedor', function(Contenedor) {
                                return Contenedor.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('contenedor', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('contenedor.delete', {
                parent: 'contenedor',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/contenedor/contenedor-delete-dialog.html',
                        controller: 'ContenedorDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Contenedor', function(Contenedor) {
                                return Contenedor.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('contenedor', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
