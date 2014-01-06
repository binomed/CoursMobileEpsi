

var EPSI = EPSI || function(){

	// Inner vars

	// Inner methods

	function pageLoad(){	

		open();

		$("#menu").on("click", function(){
			getAllObjects();
		});


		var keyValues = "";
		for (var i = 0; i < 20; i ++){			
			$("#list_ul").append("<li><i class='icon-info'></i><div class='content'>Donnée "+i+" :  Click me!</div></li>");

			$('li').on('click', function(){

			});
		}

	
		
	}

	// API

	function init(){
		document.addEventListener('DOMComponentsLoaded', pageLoad);
	}


	return {
		init : init
	};

}();

EPSI.init();

