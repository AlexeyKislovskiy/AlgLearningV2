document.addEventListener('DOMContentLoaded', function () {
    document.getElementById('chooseAll-div').addEventListener('click', ev => {
        let el = document.getElementsByClassName('using-check');
        for (let i = 0; i < el.length; i++) {
            if (el[i].checked === false) {
                el[i].checked = true
                let event = new Event('change');
                el[i].dispatchEvent(event);
            }
        }
    })

    document.getElementById('unchooseAll-div').addEventListener('click', ev => {
        let el = document.getElementsByClassName('using-check');
        for (let i = 0; i < el.length; i++) {
            if (el[i].checked === true) {
                el[i].checked = false
                let event = new Event('change');
                el[i].dispatchEvent(event);
            }
        }
    })
})
