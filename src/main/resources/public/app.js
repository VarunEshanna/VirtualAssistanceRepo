$(document).ready(function(){
    $('[data-toggle="tooltip"]').tooltip();   
});


function getval(sel)
{
    if(sel.value == 'SOQL'){
    	console.log('add text area');
    }else if(sel.value == 'Expression'){
    	console.log('add expression statements');
    }
}


var text = "";
function getSelectionText() {
    var activeEl = document.activeElement;
    var activeElTagName = activeEl ? activeEl.tagName.toLowerCase() : null;
    if (
      (activeElTagName == "textarea") || (activeElTagName == "input" &&
      /^(?:text|search|password|tel|url)$/i.test(activeEl.type)) &&
      (typeof activeEl.selectionStart == "number")
    ) {
        text = activeEl.value.slice(activeEl.selectionStart, activeEl.selectionEnd);
    } else if (window.getSelection) {
        text = window.getSelection().toString();
    }
    return text;
}

document.onselectionchange = function() {
  document.getElementById("sel").value = getSelectionText();
};

var module = angular.module("myApp",[]);
module.controller("mainCtrl",main);


function main($scope){
	$scope.displayModule = '';
	$scope.initialiseDisplay = '';
	$scope.seletedConnector = '';
	$scope.showSetUp = true;
	$scope.entity = {};
	$scope.myList = [];

	$scope.initDisplay = function(option){
		if(option == "setUp"){
			$scope.initialiseDisplay = "setUp";
			$scope.displayModule = "mongodb";
			$scope.showSetUp = false;
		}
		if(option == "config"){
			$scope.initialiseDisplay = "config";
			$scope.displayModule = "configureLuis";
			$scope.showSetUp = false;
		}
	}

	$scope.displayContent = function(option){
		$scope.displayModule = option;
	}

	$scope.showConnectorDetails = function(option){
		$scope.seletedConnector =option;
	}

	$scope.addEntity = function(option){
		var size = $scope.myList.length;
		$scope.myList.push(size + 1);
		$scope.entity[size+1] = text;
	}

	$scope.removeEntity = function(option){
		var size = $scope.myList.length;
		$scope.myList.splice(size - 1);
	}

	$scope.submitData = function(){
		console.log("intent: "+$scope.intentName);

		for (var key in $scope.entity) {
			console.log("key: "+key+ ", value: "+$scope.entity[key]);
		}
	}
	
	$scope.saveDbData = function(){
		console.log("username "+$scope.username);
		console.log("pwd "+$scope.pwd);
		console.log("host "+$scope.host);
		console.log("port "+$scope.port);
		console.log("database "+$scope.database);
		
		// TODO: Make REST call to save connection
		
	}
	
	
	$scope.validateDbData = function(){
		console.log("username "+$scope.username);
		console.log("pwd "+$scope.pwd);
		console.log("host "+$scope.host);
		console.log("port "+$scope.port);
		console.log("database "+$scope.database);
		
		// TODO: Make REST call to validate connection
	}
	
}
