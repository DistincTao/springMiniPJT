$(function (){

	$(".upFileArea").on("dragenter dragover", function (e) {
		e.preventDefault();
	});
	
	$(".upFileArea").on("drop", function (e) {
		e.preventDefault();

		console.log(e.originalEvent.dataTransfer.files);
		
		let files = e.originalEvent.dataTransfer.files;
		
		for (let file of files) {
			console.log(file)
			
			let form = new FormData();
			form.append("uploadFile", file);
			// 위 문장에서 "uploadFile" 파일의 이름은 컨트롤러 단의 multipartFile 매개변수 명과 일치시킨다.
			
			$.ajax ({
				url : "uploadFile",
				type : "POST",
				data : form,
				dataType : "json",
				processData : false, // text 데이터에 대해서 쿼리 스트링 처리를 하지 않음
				contentType : false, // form 전송 시 기본타입(application/x-www-form-un을 사용하지 않음
				success: function(data) {
					console.log(data);
				},
				error : function() {},
				complete : function() {},
			});
		}
	});

});