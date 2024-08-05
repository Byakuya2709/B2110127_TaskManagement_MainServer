function changeTitle(){
   
        var title_lists=document.querySelectorAll('.card-img-top');
        var card_title_lists=document.querySelectorAll('.card-title');
        var fragment_title= document.createDocumentFragment();
        for( var i = 0; i < title_lists.length; i++ ){
           var title_list = title_lists[i];
           var title_item = title_list.getAttribute('title');
            fragment_title[i] = title_item;
            const abc = String(title_item);
           title_list.setAttribute('alt', abc);
        }
        for( var j = 0; j < card_title_lists.length; j++ ){
        var card_title_list=card_title_lists[j];
        card_title_list.innerHTML=fragment_title[j];
        }
}

function checkKeySearch(e){
  var key = e.which || e.keycode;
  if (key==32){
     doSearch();
  }
 }
 
 function doSearch(){
   var form=document.getElementById('form-searching');
     if (form.textSearch.value.length > 0){
         form.submit();
     }
     
     
 }
 function showSearch(){
     var url = new URL(window.location);
     var ws = url.searchParams.get("textSearch");
     var div1=document.getElementById('text-search');
     div1.innerHTML= '<h1 class="text-center"> Từ khóa đã tìm </h1>' + '<p  class="text-center">'+ ws+ '</p>' ;
 }
const VND = new Intl.NumberFormat('vi-VN', {
  style: 'currency',
  currency: 'VND',
});

function data_price(){
      var price = document.querySelector('p.card-text').getAttribute('origin-price');
        $('p.card-text').prepend("<span>" + VND.format(price)+ "</span>");
      
}

function getProduct_info(){
        var title=document.querySelector('h5.card-title').textContent;
        var price=document.querySelector('p.card-text').getAttribute('origin-price');
        var src=document.querySelector('img.product-image').getAttribute('src');
        var quantity= document.querySelector("input.quantity").value;

        var getSize= document.querySelectorAll('.btn-check');
        var size;
          for (let i = 0; i < getSize.length; i++) {
              if(getSize[i].checked==true)
              size=getSize[i].value; 
        }
        var temp_price=document.querySelector('span.temp-price');

        document.querySelector('.bill-size').textContent='Size '+size;

        temp_price.innerHTML=VND.format(price*quantity);

        var bill_title=document.querySelector('h5.bill-title');
        var bill_text=document.querySelector('p.bill-text');
              bill_title.innerHTML=title; 
              bill_text.innerHTML = VND.format(price);
        var bill_img=document.querySelector('img.buying-img');
        var src_img=document.createAttribute('src');
              src_img.value=src;
              bill_img.setAttributeNode(src_img);

        
        
        
        
 
}
function countProduct(){
  document.querySelector('span.cart-quantity').innerHTML=localStorage.length;
}
function delivery(){
      var x = document.getElementById("location-city").value;
      const city1="tphcm";
      const city2="cantho";
      const city3="hanoi";
      var change= document.getElementById("delivery-price");
      if (String(x)  == city1){
        var delivery=20000;
        change.innerHTML ="Phí Vận Chuyển : 20.000d";
      }
         
      if (String(x) == city2) {
        var delivery=15000;
        change.innerHTML ='Phí Vận Chuyển : 15.000d';
      }
      
      if (String(x) == city3) {
        var delivery=30000; 
        change.innerHTML ='Phí Vận Chuyển :30.000d';
      }

        var price=document.querySelector('p.card-text').getAttribute('origin-price');
        var quantity= document.querySelector("input.quantity").value;
        var total_price=document.querySelector('span.total-price');
        const money= (price * quantity)  +delivery;
                document.querySelector("span.temp-delivery").innerHTML=VND.format(delivery);
                total_price.innerHTML=VND.format(money);
        
}

function up(max) {
  document.querySelector("input.quantity").value = parseInt(document.querySelector("input.quantity").value) + 1;
  if (document.querySelector("input.quantity").value >= parseInt(max)) {
      document.querySelector("input.quantity").value = max;
  }
}
function down(min) {
  document.querySelector("input.quantity").value = parseInt(document.querySelector("input.quantity").value) - 1;
  if (document.querySelector("input.quantity").value <= parseInt(min)) {
      document.querySelector("input.quantity").value = min;
  }
}
function openCart(){
  window.location.href="cart.html";
}
var itemList={
  "sp001":{ "name":"Linen Cuban Shirt","price":550000,"photo":"data/img/product/product-1-top.png"},
  "sp002":{ "name":"Leather Bomber Jacket","price":1180000,"photo":"data/img/product/product-2-top.png"},
  "sp003":{ "name":"Baggy Jeans Green Wash","price":790000,"photo":"data/img/product/product-3-bottom.png"},
  "sp004":{ "name":"Thương Bạn Trai T-shirt","price":420000,"photo":"data/img/product/product-4-top.png"},
  "sp005":{ "name":"CanvasCraze Handbag","price":1150000,"photo":"data/img/product/product-5-asso.png"},
  "sp006":{ "name":"Two Tone Denim Hoodie","price":1260000,"photo":"data/img/product/product-6-top.png"},
  "sp007":{ "name":"Tennis Skirt","price":440000,"photo":"data/img/product/product-7-bottom.png"},
  };
 
  function addCart(code){
    var number=parseInt(document.getElementById(code).value);
    var name = itemList[code].name;
    if (number ==0)
             return;
    if (typeof localStorage[code] === 'undefined'){
        window.localStorage.setItem(code,number);
    }
    else{
        var current=parseInt(window.localStorage.getItem(code));
        if (current + number >10){
            window.localStorage.setItem(code,10)
            alert("Moi san pham khong the dat qua 10 mon");
            return;
        }
        else{
            window.localStorage.setItem(code,number+ current);
        }
    }
    alert("da cap nhap san pham " + name + "voi so luong: " + number + " vao gio hang. So luong san pham da dat la: "+parseInt(window.localStorage.getItem(code)) );
}
function showCart(){
  if(window.localStorage.length == 0){
    $('#cart-block').addClass("d-none");
    $('#cart-none').removeClass('d-none');
    //  document.querySelector('#cart-block').classList.add("d-none");
   // document.querySelector('#cart-none').classList.remove("d-none");
  }
  else{
      var container=document.getElementById('shopping-cart').getElementsByTagName('tbody')[0];
      container.innerHTML="";
  var sum=0;
  var totalPreTax=0;
  for (var i=0;i<window.localStorage.length;i++){
      if (typeof itemList[localStorage.key(i)]==='undefined')
      continue;
      var tr=document.createElement('tr');
      var photoCell=document.createElement('td');
      var nameCell=document.createElement('td');
      var priceCell=document.createElement('td');
      var numberCell=document.createElement('td');
      var sumCell=document.createElement('td');
      var removeCell=document.createElement('td');
      
      var key=localStorage.key(i);
      var item= itemList[key];


              var img=document.createElement('img');
              var src=document.createAttribute('src');
              var width=document.createAttribute('width');
              width.value='100px'
              src.value=item.photo;
              img.setAttributeNode(width);
              img.setAttributeNode(src);
              photoCell.appendChild(img);

              var pName= document.createElement('p');
              pName.textContent=item.name;
              nameCell.appendChild(pName);

              numberCell.innerHTML=localStorage.getItem(key);

              priceCell.innerHTML= VND.format(item.price);
              var sum=item.price * localStorage.getItem(key);
              sumCell.innerHTML=VND.format(sum);

              var a= document.createElement('a');
              var href=document.createAttribute('href');
              var data_code=document.createAttribute('data-code');
              data_code.value=key;
              href.value="#";
              a.setAttributeNode(data_code);
              a.setAttributeNode(href);
              a.innerHTML='<i class="fa fa-trash icon-pink"></i>';
              a.onclick = function(){
                  removeCart(this.dataset.code);
              }
              removeCell.appendChild(a);
              tr.appendChild(photoCell);
              tr.appendChild(nameCell);
              tr.appendChild(numberCell);
              tr.appendChild(priceCell);
              tr.appendChild(sumCell);
              tr.appendChild(removeCell);
              container.appendChild(tr);
              totalPreTax+=sum;
            }
      document.querySelector('p.tax-price').innerHTML="Thành Tiền: "+VND.format(totalPreTax);

      

  }
                                  
}
function removeCart(code){
  if (typeof window.localStorage[code]!=='undefined')
  {
      window.localStorage.removeItem(code);
      document.getElementById('shopping-cart').getElementsByTagName('tbody')[0].innerHTML="";
      showCart();
  }
}
window.onstorage = () => function(){
  showCart();
};