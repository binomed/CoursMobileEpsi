

var EPSI = EPSI || function(){

	// Inner vars

	var indexedDbEPSI = {};
	indexedDbEPSI.db = null;
	var carte = null;


	// Inner methods

	function pageLoad(){	

		open();

		$("#menu").on("click", function(){
			getAllObjects();
		});


		$("#loadingContent").html("Chargement en cours ...");
		
		var keyValues = "";
		for (var i = 0; i < 20; i ++){
			keyValues += "/id"+i+"/"+encodeURIComponent(JSON.stringify({
				id : "id"+i,
				value : "value"+i,
				index : i
			}));
		}

		function callAjax(){			
			$.ajax({
				url:"http://echo.jsontest.com"+keyValues,
				type: "GET",
				dataType: "jsonp",
				crossDomain: true
			})
			.done(function(data, textStatus, jqXHR){
				var items = [];
				$.each( data, function( key, val ) {
					var jsonObject = JSON.parse(decodeURIComponent(val));
				    items.push( "<li id='" + key + "'><i class='icon-exchange'></i><div class='content'>" + jsonObject.value + "</div></li>" );
				    addObject(jsonObject);
				});

				 $( "<ul/>", {
				    "class": "my-new-list",
				    html: items.join( "" )
				  }).appendTo( "#list" );

				 $("#loading").hide();
			})
			.fail(function(jqXHR, textStatus, error){
				$("#loadingContent").html("Erreur pendant le chargement des données...");
				console.log("fail ! "+error);
			});
		}

		setTimeout(callAjax, 1000);

		$('#mapId').on('click', function(){
			initMap();
		});
		
	}

	function open(){
		var version = 1;
		var request = indexedDB.open("datas", version);

		// We can only create Object stores in a versionchange transaction.
		request.onupgradeneeded = function(e) {
			var db = e.target.result;
			// A versionchange transaction is started automatically.
			e.target.transaction.onerror = indexedDbEPSI.onerror;
			if(db.objectStoreNames.contains("object")) {
			  db.deleteObjectStore("object");
			}
			var store = db.createObjectStore("object",
				{keyPath: "id"});
			store.createIndex('value', 'value',{unique:false});
		};

		request.onsuccess = function(e){
			indexedDbEPSI.db = e.target.result;
		};

		request.onerror = indexedDbEPSI.onerror;
	}

	function addObject(object) {
		var db = indexedDbEPSI.db;
		var trans = db.transaction(["object"], "readwrite");
		var store = trans.objectStore("object");
		var request = store.put(object);

		request.onsuccess = function(e) {
			console.log('object store !');
		};

		request.onerror = function(e) {
			console.log(e.value);
		};
	};

	function getAllObjects() {
		
		$('#list').html("<ul id='list_ul'></ul>");

		var db = indexedDbEPSI.db;
		var trans = db.transaction(["object"], "readwrite");
		var store = trans.objectStore("object");
		// Get everything in the store;
		var cursorRequest = store.openCursor();
		cursorRequest.onsuccess = function(e) {
			var result = e.target.result;
			if(!!result == false){
				$("#loadingContent").html("Aucune donnée chargée depuis la base");
			  	return;
			}
			$("#list_ul").append("<li><i class='icon-hdd'></i><div class='content'>"+result.value.value+" from DB</div></li>");
			result.continue();
		};
		cursorRequest.onerror = indexedDbEPSI.onerror;
	};

	// Maps

	function initMap(){
		$("#list").html("<div id='mapBtn'><i class='icon-globe icon-3x'></i></div><div id='map'></div>");
		var latlng = new google.maps.LatLng(46.779231, 6.659431);
		//objet contenant des propriétés avec des identificateurs prédéfinis dans Google Maps permettant
		//de définir des options d'affichage de notre carte
		var options = {
			center: latlng,
			zoom: 19,
			mapTypeId: google.maps.MapTypeId.ROADMAP
		};

		//constructeur de la carte qui prend en paramêtre le conteneur HTML
		//dans lequel la carte doit s'afficher et les options
		carte = new google.maps.Map(document.getElementById("map"), options);

		$("#mapBtn").on('click', function(){
			if(navigator.geolocation)
				navigator.geolocation.getCurrentPosition(mapLocationCallBack, mapErrorCallBack);
		});
	}

	function mapLocationCallBack(position){
		carte.setCenter(new google.maps.LatLng(position.coords.latitude, position.coords.longitude));
		carte.setZoom(15);

		$.getJSON('http://nominatim.openstreetmap.org/reverse?format=json&lat='+position.coords.latitude+'&lon=-'+position.coords.longitude+'&zoom=18&addressdetails=1', function(data){

			var infowindow = new google.maps.InfoWindow({
			    content: data.display_name
			});
			
			var marqueur = new google.maps.Marker({
		        position: new google.maps.LatLng(position.coords.latitude, position.coords.longitude),
	    	    map: carte,
	    	    title: data.display_name
	    	});

	    	google.maps.event.addListener(marqueur, 'click', function(){
	    		infowindow.open(carte, marqueur);
	    	});
		})
		.fail(function(jqXHR, textStatus, error){
			console.log("fail ! "+error);
		});
		

	}

	function mapErrorCallBack(error){
		switch(error.code) 
		{
		case error.PERMISSION_DENIED:
		case error.POSITION_UNAVAILABLE:
		case error.TIMEOUT:
		case error.UNKNOWN_ERROR:
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

