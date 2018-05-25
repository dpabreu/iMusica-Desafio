angular.module("App", []).controller("DefafioController", function($http, $scope, $rootScope){
	$scope = this;
	$scope.mostraCadastro = false;
	$scope.mostraVenda = false;
	
	if($scope.container == undefined){
		$scope.container = {};
	}
	
	var urlBase = 'http://localhost:8085/Desafio/rest/';


	$scope.recuperaProduto = function(){
		if($scope.container.idProduto == "" || $scope.container.idProduto == undefined){
			$scope.readAll();
		} else {
			$scope.read($scope.container.idProduto);
		}
	}
	
	$scope.readAll = function(){
		var metodo = 'GET';
		
		$http({
			method: metodo,
			url: urlBase + "service/readAll/",
			data: self.container
		}).then(function sucessCallBack(response){
			if(!response.data.temErro){
				$scope.container = response.data.tipo;
			}else{
				$scope.container = {};
				alert(response.data.msgsErro[0]);
			}
		}, function errorCallBack(response){
			alert(response.data.msgsErro[0]);
		});
	}
	
	$scope.read = function(idProduto){
		var metodo = 'GET';
		
		$http({
			method: metodo,
			url: urlBase + "service/read/" + idProduto,
			data: self.container
		}).then(function sucessCallBack(response){
			if(!response.data.temErro){
				$scope.container = response.data.tipo;
			}else{
				$scope.container = {};
				alert(response.data.msgsErro[0]);
			}
		}, function errorCallBack(response){
			alert(response.data.msgsErro[0]);
		});		
	}
	
	$scope.create = function(){
		var metodo = 'POST';
		
		$http({
			method: metodo,
			url: urlBase + "service/create",
			data: $scope.container
		}).then(function sucessCallBack(response){
			alert("Produto cadastrado com sucesso.");
			$scope.readAll();
			$scope.mostraCadastro = false;
		}, function errorCallBack(response){
			alert("Erro");
		});
	}		

	$scope.update = function(){
		var metodo = 'PUT';
		
		$http({
			method: metodo,
			url: urlBase + "service/update",
			data: $scope.container
		}).then(function sucessCallBack(response){
			alert("Produto alterado com sucesso.");
			$scope.readAll();
			$scope.mostraCadastro = false;
		}, function errorCallBack(response){
			alert("Erro");
		});
	}
	
	$scope.excluir = function(idProduto){
		var metodo = 'DELETE';
		
		$http({
			method: metodo,
			url: urlBase + "service/delete/" + idProduto,
			data: $scope.container
		}).then(function sucessCallBack(response){
			alert("Produto excluído com sucesso.");
			$scope.readAll();
			$scope.mostraCadastro = false;
		}, function errorCallBack(response){
			alert("Erro");
		});
	}
	
	$scope.vender = function(){
		if($scope.container.qtdVenda > $scope.container.quantidade){
			alert("Quantidade informada maior que a disponível em estoque. Verifique.");
		} else {
			var metodo = 'PUT';
			
			$http({
				method: metodo,
				url: urlBase + "service/venda?idProduto="+$scope.container.idProduto+"&quantidade="+$scope.container.qtdVenda,
				data: $scope.container
			}).then(function sucessCallBack(response){
				alert("Venda realizada com sucesso.");
				$scope.readAll();
				$scope.mostraVenda = false;
			}, function errorCallBack(response){
				alert("Erro");
			});			
		}
	}
	
	$scope.incluirProduto = function(){
		$scope.container = {};
		$scope.mostraCadastro = true;
	}
	
	$scope.editarProduto = function(item){
		$scope.container = {};
		$scope.container.idProduto = item.idProduto;
		$scope.container.nome = item.nome;
		$scope.container.preco = item.preco;
		$scope.container.quantidade = item.quantidade;
		$scope.mostraCadastro = true;
	}

	$scope.salvar = function(){
		if($scope.container.idProduto == "" || $scope.container.idProduto == undefined){
			$scope.create();
		} else {
			$scope.update();
		}
	}
	
	$scope.pesquisaProduto = function(idProduto){
		if(idProduto == "" || idProduto == undefined){
			alert("Informe um produto.");
		}else{
			$scope.read(idProduto);
		}
	}
	
	$scope.venda = function(){
		$scope.container = {};
		$scope.mostraVenda = true;
	}
	
	$scope.voltar = function(tipoOperacao){
		$scope.container = {};
		
		if(tipoOperacao == "manter"){
			$scope.mostraCadastro = false;
		}else if(tipoOperacao == "venda"){
			$scope.mostraVenda = false;
		}
	}
});