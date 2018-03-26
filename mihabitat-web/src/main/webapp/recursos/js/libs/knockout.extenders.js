ko.bindingHandlers.currency = {
    symbol: ko.observable('$'),
    update: function(element, valueAccessor, allBindingsAccessor){
        return ko.bindingHandlers.text.update(element,function(){
            var value = +(ko.utils.unwrapObservable(valueAccessor()) || 0),
                symbol = ko.utils.unwrapObservable(allBindingsAccessor().symbol === undefined
                            ? allBindingsAccessor().symbol
                            : ko.bindingHandlers.currency.symbol);
            return symbol + value.toFixed(2).replace(/(\d)(?=(\d{3})+\.)/g, "$1,");
        });
    }
};

ko.extenders.currency = function (target, scale) {
    var cleanInput = function (value) {
        if (value || value === 0) {
            value = value + "";
            return parseFloat(value.replace(/[^0-9.\-]/g, ''));
        } else {
            return null;
        }
    };

    var format = function (value) {
        if (typeof value != 'undefined' && value != null) {
            var toks = value.toFixed(scale).replace('-', '').split('.');
            var display = $.map(toks[0].split('').reverse(), function (elm, i) {
                    return [(i % 3 == 0 && i > 0 ? ',' : ''), elm];
                }).reverse().join('') + '.' + toks[1];

            /*return value < 0 ? '(' + display + ')' : display;*/
            return value < 0 ?  display : display;
        } else {
            return null;
        }


    };

    var raw = typeof target == "function" ? ko.dependentObservable(target) : ko.observable(target);

    //create a writeable computed observable to intercept writes to our observable
    var result = ko.computed({
        read: ko.dependentObservable({
            read: function () {
                return raw();
            },
            write: function (value) {
                raw(cleanInput(value));
            }
        }), //always return the original observables value

        write: function (newValue) {
            newValue = cleanInput(newValue);
            var current = target(),
                roundingMultiplier = Math.pow(10, scale),
                newValueAsNum = newValue == "" || newValue == null ? 0 : parseFloat(+newValue);
            var valueToWrite;
            valueToWrite = !! newValue || newValue === 0 ? Math.round(newValueAsNum * roundingMultiplier) / roundingMultiplier : null;

            //only write if it changed
            if (valueToWrite !== current) {
                target(valueToWrite);
            } else {
                //if the rounded value is the same, but a different value was written, force a notification for the current field
                if (newValue !== current) {
                    target.notifySubscribers(valueToWrite);
                }
            }
        }
    });
    result.formatted = ko.dependentObservable({
        read: function () {
            return format(raw());
        },
        write: function (value) {
            result(cleanInput(value));
        }
    }); //always return the original observables value
    //initialize with current value to make sure it is rounded appropriately
    result(target());

    //return the new computed observable
    return result;
};

ko.bindingHandlers.select2 = {
    init: function(element, valueAccessor) {
        var options = ko.toJS(valueAccessor()) || {};
        setTimeout(function() { 
            $(element).select2(options);
        }, 0);
    },
    update: function(element) {
        $(element).trigger('change');
    }
};

ko.bindingHandlers.spinner = {
	 init: function (element, valueAccessor, allBindingsAccessor) {	      

	    var options = allBindingsAccessor().spinnerOptions || {};
	    $(element).spinner(options);
	            
	    $(element).on("spinstop", function () {
		    var observable = valueAccessor();
		    observable($(element).spinner("value"));
	    });
	    
	    ko.utils.domNodeDisposal.addDisposeCallback(element, function () {
	    	$(element).off("spinstop");
	        $(element).spinner("destroy");
	    });	        	       
	 },
	 update: function (element, valueAccessor, allBindingsAccessor) {	     
	       	var value = ko.utils.unwrapObservable(valueAccessor());
	        var disable = allBindingsAccessor().disable;
	            
	        if (typeof disable !== "undefined") {
	           $(element).spinner((disable) ? "disable" : "enable");
	        }	            
	        var current = $(element).spinner("value");
	        if (value !== current) {
	            $(element).spinner("value", value);
	        }	       
	 }
};

ko.bindingHandlers.unique = {
    init: function(element, valueAccessor) {
        var value = valueAccessor();
        var a = (value.prefix ? value.prefix : ko.bindingHandlers.unique.prefix) + "-" + 
        ((value.counter != undefined) ? value.counter : ++ko.bindingHandlers.unique.counter);
        
        if (value.name) {
        	element.name = a;
        }
        
        if (value.id){
        	element.id = a;
        }
    },
    counter: 0,
    prefix: "unique"
};
