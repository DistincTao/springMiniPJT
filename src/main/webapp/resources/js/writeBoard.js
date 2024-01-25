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
					if (data != null) {
						displayUploadedFile(data);
					}
				},
				error : function() {},
				complete : function() {},
			});
		}
	});

});


function displayUploadedFile(json){
	console.log("1");

	let output = "";
	$.each(json, function(i, elem) {
		let name = elem.newFilename.replaceAll("\\", "/");
		
		console.log(elem.newFilename);
		if (elem.thumbFilename != null) {
			let thumb = elem.thumbFilename.replaceAll("\\", "/");
			
			output += `<img src='../resources/uploads${thumb }' id='${elem.newFilename }'/>`;
			output += `<img src='../resources/img/remove.png' class='removeIcon' onclick='removeFile(this)'/>`;
		} else {
			output += `<a href='../resources/uploads${name}' id='${elem.newFilename }'>${elem.originalFilename}</a>`;
			output += `<img src='../resources/img/remove.png' class='removeIcon' onclick='removeFile(this)'/>`;
		}
		
	});
	$(".upLoadFiles").html(output);
	
}


function removeFile(fileId) {
	console.log(fileId);
	let removeFile = $(fileId).prev().attr('id');
	console.log(removeFile);
	
				
	$.ajax ({
		url : "removeFile",
		type : "GET",
		data : {
			"removeFile" : removeFile
		},
		dataType : "text",
		success: function(data) {
			console.log(data);
			if (data == 'success') {
				$(fileId).prev().remove();
				$(fileId).remove();
				
				if ($(".upLoadFiles").html() == "") {
					let output = "Drag and Drop Files";
					$(".upLoadFiles").html(output);
				}			
			}
		},
		error : function() {},
		complete : function() {},
	});
	
}

function btnCancel(userId) {
	// 취소 버튼 클릭 시 드래그 드랍한 모든 파일 삭제
	
	$.ajax ({
		url : "removeAllFile",
		type : "GET",
		dataType : "text",
		success: function(data) {
			console.log(data);
			if (data == 'success') {
				$(".upLoadFiles").empty();
				// let output = "Drag and Drop Files";
				// $(".upLoadFiles").html(output);
				location.href = `listAll?userId=${userId}&pageNo=1`;
			}
		},
		error : function() {},
		complete : function() {},
	});

}

