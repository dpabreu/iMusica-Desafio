angular.module("DesafioApp", []).controller("DefafioController", function($http, $scope, $rootScope){
	$scope = this;
	$scope.container = {};
	
	var urlBase = 'http://localhost:8085/Desafio/rest/';


//	$scope.recuperaProduto = function(){
//		if($scope.container.idProduto == "" || $scope.container.idProduto == undefined){
//			$scope.readAll();
//		} else {
//			$scope.read($scope.container.idProduto);
//		}
//	}
	
	$scope.readAll = function(){
		var metodo = 'GET';
		
		$http({
			method: metodo,
			url: urlBase + "service/readAll/",
			data: self.container
		}).then(function sucessCallBack(response){
			$scope.container = response.data;
			$scope.container.idProduto = $scope.container.produtos[0].idProduto;
		}, function errorCallBack(response){
			alert("Erro");
		});
	}
	
	$scope.readAll();
});