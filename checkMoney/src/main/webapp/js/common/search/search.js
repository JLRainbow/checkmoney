$('.morebtn').click(function(){
	$('.morebody').show();
	$(this).hide();
	$('.streamline').show();
})
$('.streamline').click(function(){
	$('.morebody').hide();
	$(this).hide();
	$('.morebtn').show();
})
function sele(){    
    var oForm = document.getElementById('searchForm');
    var oSelect = oForm.getElementsByTagName('select');
    for(var i=0; i< oSelect.length; i++){
    	oSelect[i].selectedIndex = -1;    
    }   
}
sele();