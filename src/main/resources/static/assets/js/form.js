function validationSignup(frm){
    var emailReg = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
    var phoneReg = /[0-9]{10,}/;
    if (frm.fullname.value.length<4 ) {
        window.alert("Nhập lại tên.")
        frmfull.name.focus();
        return false;
        }
     if ( phoneReg.test(frm.phone.value) == false &&  frm.phone.value.length !=10  ){
            frm.phone.focus();
            window.alert("Số điện thoại không hợp lệ.");
            return false;
            }

    if ( emailReg.test(frm.email.value) == false ){
        frm.email.focus();
        return false;
        }
    if ( frm.psw1.value.length<8 ) {
            window.alert("Mật khẩu không hợp lệ.");
            frm.psw1.focus();
            return false;
        }
    if ( frm.psw2.value.length<8 ) {
            window.alert("Mật khẩu không khớp với mật khẩu đã nhập.");
            frm.psw2.focus();
            return false;
    }     
    if ( frm.psw2.value!= frm.psw1.value) {
        window.alert("Nhập lại mật khẩu.")
        frm.psw2.focus();
        return false;
}    
    if(frm.term.checked==false){
    frm.term.focus();
    return false;
        }
    alert("Đăng Ký thành công.")
    return true;
    
}
function validationLogin(frm){
    var emailReg = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
    if ( emailReg.test(frm.email.value) == false ){
        frm.email.focus();
        return false;
        }
        if ( frm.psw1.value.length<8 ) {
            window.alert("Mật khẩu không hợp lệ.");
            frm.psw1.focus();
            return false;
        }
    if(frm.remember.checked==false){
        frm.remember.focus();
        return false;
    }
        alert("Đăng Nhập thành công.")
    return true;
}