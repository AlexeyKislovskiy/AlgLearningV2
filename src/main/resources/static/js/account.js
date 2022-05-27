document.addEventListener('DOMContentLoaded', function () {
    for (let i = 100; i <= 5000; i += 100) {
        setTimeout(setHeight, i)
    }
    setHeight();
    window.addEventListener(`resize`, event => setHeight(), false);

    function setHeight() {
        let new_height;
        if (window.innerWidth >= 768) {
            document.getElementById('col-1-insert').style.display = "none";
            new_height = window.innerHeight - document.getElementById('navigation').offsetHeight
                - document.getElementById('account-header').offsetHeight - document.getElementById('acc-header').offsetHeight - 40;
            document.getElementById('acc-info').style.height = new_height + 'px';
            new_height -= document.getElementById('header-3').offsetHeight;
            document.getElementById('stat-info').style.height = new_height + 'px';
        } else {
            document.getElementById('col-1-insert').style.display = "block";
            new_height = window.innerHeight - document.getElementById('navigation').offsetHeight
                - document.getElementById('account-header').offsetHeight - document.getElementById('acc-header').offsetHeight - 40;
            document.getElementById('acc-info').style.height = new_height / 3 + 'px';
            new_height -= document.getElementById('header-3').offsetHeight + document.getElementById('acc-info').offsetHeight + 30;
            document.getElementById('stat-info').style.height = new_height + 'px';
        }
    }
})
