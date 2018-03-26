/**
 * 
 */

if(!Object.get) {
	Object.get = function(data, prop) {
		return data ? data[prop] : undefined;
	}
}

if(!Object.getDefault) {
	Object.getDefault = function(data, prop, value) {
		var valor = Object.get(data, prop);
		return valor ? valor : value;
	}
}

if(!Object.getArray) {
	Object.getArray = function(data, prop) {
		return (data && $.isArray(data[prop])) ? data[prop] : [];
	}
}

if (!Object.only) {
	Object.only = function(object, properties) {
		for (var key in object) {
			if (properties.indexOf(key) == -1 ) {
				delete object[key];
			}
		}
		
		return object;
	}
}

if (!Object.deleteProperties) {
	Object.deleteProperties = function(object, properties) {
		for (var i = 0; i < properties.length; i++ ) {
			delete object[properties[i]];
		}
		
		return object;
	}
}
