

var EPSI = EPSI || function(){

	// Inner vars

	// Inner methods

	function pageLoad(){	

		
		$("#mobile-tab").on("click", toggleVisibility);

		function toggleVisibility(){
			$('#list').toggleClass('hide');
			$('#details').toggleClass('hide');			
			if ($('#list').hasClass('hide')){
				$('#iconTab').removeClass('icon-play');
				$('#iconTab').addClass('icon-tasks');
			}else{
				$('#iconTab').addClass('icon-play');
				$('#iconTab').removeClass('icon-tasks');
			}
		}


		var keyValues = "";
		for (var i = 0; i < 20; i ++){			
			$("#list_ul").append("<li id='li"+i+"'><i class='icon-info'></i><div class='row'>Donnée "+i+" :  Click me!</div></li>");

			$('li').on('click', function(event){
				$('#details').html('élément cliqué : '+event.currentTarget.id);
				toggleVisibility();
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

