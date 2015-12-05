var app = angular.module('myapp', []);

    app.controller('greetingCtrl', function($scope, $http){
        waitingDialog.show('Greeting...');
        $http.get('/greeting?name=Hackers').
            then(function(response) {
                waitingDialog.hide();
                $scope.greetingResult = response.data;
            });
    });

    app.controller('Ctrl', function($scope, $q, $http){
        $scope.variable = "Hello";

        $scope.test = function() {

            var deferred = $q.defer();

            waitingDialog.show('I\'m waiting');
            setTimeout(function(){
                deferred.resolve('cool');
            }, 5000);

            var promise = deferred.promise;
            promise.then(function(result){
                waitingDialog.hide();
                alert('Success: ' + result);
            }, function(reason){
                waitingDialog.hide();
                alert('Error: ' + reason);
            });
        }

        $scope.fail2 = false;
        $scope.test2 = function() {
            var deferred = $q.defer();
            var promise = deferred.promise;
            promise.then(function(result){
                $scope.status2 = 'success pass 1 - ' + result;
                return result + ' hussa!';
            }, function(reason){
                $scope.status2 = 'failure pass 1';
                return reason;
            })
            .then(function(result){
                alert('Success: ' + result);
            }, function(reason){
                alert('Error: ' + reason);
            });

            if($scope.fail2)
                deferred.reject('Bad luck');
            else
                deferred.resolve('Hurray');
        }
    });