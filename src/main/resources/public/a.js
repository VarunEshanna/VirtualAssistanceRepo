var id =1
addAnother = function() {
    var ul = document.getElementById("entity");
    id+=1;
    var q = '<li id="'+id+'"><label>ENTITIES <span class="required">*</span></label><input type="text" name="entity1" class="field-divided" placeholder="Entity" />&nbsp;<input type="text" name="entity2" class="field-divided" placeholder="Entity" /></li>'
    ul.innerHTML+=q;    
}

function remove() {
    var A = document.getElementById(id);
    if(id<=1){
    	alert("you can't delete this");
    }
    else{
    	A.parentNode.removeChild(A);
    	id--;
    }
    
}