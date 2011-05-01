$.widget("ui.panel", {
	options : {
		hidden : true
	},
	_create : function() {
		this.header = $(this.element).children(".system-button");
		this.panel = $(this.element).children(".system-content");
		
		this.header.bind("click", {thisObj: this}, this.click);
		
		test = 'test';
	},
	click : function(o) {
		var thisObj = o.data.thisObj;
		var panel = thisObj.panel;
		if (thisObj.options.hidden) {
			panel.slideDown("slow");
		} else {
			panel.slideUp("slow");
		}
		thisObj.options.hidden = !thisObj.options.hidden;
	},
	destroy : function() {
		this.header.unbind("click");
	},
	setOption : function(option, value) {
		this.options[option] = value;
	}
});

function panelUpdateImage(id, src) {
	document.getElementById(id).src = src;
}