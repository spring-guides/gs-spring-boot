var text = document.querySelector('.panels .navbar .scroll');

window.onscroll = function() {text.style.display = 'none';}

function doSetTimeout(i) {
    setTimeout(function() { text.classList.toggle("appear"); }, 1000*i);
}
      
for (var i = 1; i <= 10000; i++)
    doSetTimeout(i);