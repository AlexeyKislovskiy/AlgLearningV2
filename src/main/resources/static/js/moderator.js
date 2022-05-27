document.addEventListener('DOMContentLoaded', function () {
    window.addEventListener(`resize`, event => setHeight(), false);
    for (let i = 100; i <= 5000; i += 100) {
        setTimeout(setHeight, i)
    }
    setHeight();

    function setHeight() {
        let new_height = window.innerHeight - document.getElementById('header-row').offsetHeight - 20;
        document.getElementById('content-row').style.maxHeight = new_height + 'px';
    }

})