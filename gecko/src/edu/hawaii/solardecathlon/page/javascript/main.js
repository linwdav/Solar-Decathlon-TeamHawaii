$(document).ready(function() {
	$('.tab-content li').hide();
	$('.tab-content li:first-child').show();
	$('.tabs li:first-child').addClass('current-tab');
	
	$('ul.tabs li a:first-child').click(function(event) {
		event.preventDefault();
		tabset = $(this).parent().parent().parent().attr('id');
		$('#' + tabset + ' ul.tabs li').removeClass('current-tab');
		tabclass = $(this).parent().attr('class');
		tabnumber = tabclass.substring(4, tabclass.length);
		$(this).parent().addClass('current-tab');
		$('#' + tabset + ' ul.tab-content li').hide();
		$('#' + tabset + ' ul.tab-content li.content-' + tabnumber).show();
	});
});

function helpPopUp() {
	alert('To-Do: Create a Help Pop-Up...');
}