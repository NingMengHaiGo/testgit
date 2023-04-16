function getPhoto(node,classId,imageId) {
var imgURL = "";
try {
    var file = null;
    if (node.files && node.files[0]) {
        file = node.files[0];
    } else if (node.files && node.files.item(0)) {
        file = node.files.item(0);
    }
    //Firefox 因安全性问题已无法直接通过input[file].value 获取完整的文件路径  
        try {
            imgURL = file.getAsDataURL();
        } catch (e) {
            imgRUL = window.URL.createObjectURL(file);
        }
    } catch (e) {
        if (node.files && node.files[0]) {
            var reader = new FileReader();
            reader.onload = function(e) {
                imgURL = e.target.result;
            };
            reader.readAsDataURL(node.files[0]);
        }
    }
    creatImg(imgRUL,classId,imageId);
    return imgURL;
}
	
function creatImg(imgRUL,classId,imageId) {
    var textHtml = "<img src='" + imgRUL + "'width='350px' height='350px' class='"+ classId +"'/>";
    $("#"+imageId+"").val("");
    $("."+ classId +"").replaceWith(textHtml);
}