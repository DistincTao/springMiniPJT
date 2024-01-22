$(function (){
	$(".board").each (function (){
		let currDate = new Date();
		let postDate = new Date($(this).children().eq(3).html());
		let diff = (currDate - postDate) / 1000 / 60 / 60;
		let output = "<span><img src='../resources/img/new.png' class='new' style='width : 32px;'></span>";
		let title = $(this).children().eq(1).html();
		if (diff < 5) {
			$(this).children().eq(1).html(title + " " + output);
		}
	});

});