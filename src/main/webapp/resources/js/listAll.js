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

function searchValid() {
	let searchWord = $("#searchWord").val();
	let searchType = $("#searchType").val();
//	alert(searchWord);
	
	if (searchWord.length == 0) {
		alert("검색어를 입력하세요");
		return false;
	}
	if (searchWord.length != 0 && searchType.length == 0){
		alert("검색 기준을 선택해 주세요");
		return false;
	}
	
	let expText = /[%=><]/; //데이터 베이스에서 조건 연산자들을 정의

	if (expText.test(searchWord) == true){
		alert("특수문자를 입력할 수 없습니다");
		return false;	
	}	
	
	let sql = new Array ("or", "select", "insert", "update", "delete", "create", "alert",
						 "drop", "exec", "union", "fetch", "declare", "truncate");
						 
	for (let i = 0; i < sql.length; i++) {
		regEx = new RegExp(sql[i], "gi"); // 대소문자 없이 전역으로 검색하며 확인
		
		if (regEx.test(searchWord) == true) {
			alert("특정 문자로 검색할 수 없습니다.");
			return false;
		}
	}
	
	return true;

}