document.addEventListener('DOMContentLoaded', function () {
    for (let i = 100; i <= 5000; i += 100) {
        setTimeout(cardHeightChange, i)
        setTimeout(bottomRowHeightChange, i)
    }
    cardHeightChange();
    bottomRowHeightChange();

    window.addEventListener(`resize`, event => cardHeightChange(), false);
    window.addEventListener(`resize`, event => bottomRowHeightChange(), false);

    function cardHeightChange() {
        let new_height = document.getElementById('hidden-card').offsetHeight;
        document.getElementById('card-alg').style.height = new_height + 'px';
    }

    function bottomRowHeightChange() {
        let new_height = window.innerHeight - document.getElementById('navigation').offsetHeight - document.getElementById('top-row').offsetHeight - document.getElementById('row-1').offsetHeight - 50;
        document.getElementById('row-2').style.height = new_height + 'px';
    }
})

