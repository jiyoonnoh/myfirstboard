function necessary(){
	var need = true;
	//반드시 입력해야할 항목에 데이터를 입력했는지의 여부를 판단
	$('.need').each(function(){
		if( $(this).val().trim() == ''){
			alert( $(this).attr('title') + '을 입력하세요!');
			$(this).val(''); //space 문자가 있는경우 제거 
			$(this).focus(); //입력안된 항목에 커서 위치하게
			need = false;
			return need;
		}
	});
	
	return need;
}
