// TODO: pulire il contenute dei fields
// TODO: funziona con un numero predefinito di parametri, ma ora serve pensare se aggiungere un form per creare service
//  oppure usare js per inserrire nell'innerHTML
function addServiceFields() {
    let target2 = document.getElementById("service-insertion-2");
    let target3 = document.getElementById("service-insertion-3");
    if (target2.classList.contains('hidden') && target3.classList.contains('hidden')) {
        target2.classList.remove("hidden");
    }
    else if (!target2.classList.contains('hidden') && target3.classList.contains('hidden')) {
        target3.classList.remove("hidden");
    }

}
function removeServiceFields(idx) {
    // let target2 = document.getElementById("service-insertion-2");
    // let target3 = document.getElementById("service-insertion-3");
    // if (!target2.classList.contains('hidden') && !target3.classList.contains('hidden')) {
    //     target3.classList.add("hidden");
    // }
    // else if (target2.classList.contains('hidden') && !target3.classList.contains('hidden')) {
    //     target2.classList.add("hidden");
    // }

    let target = document.getElementById("service-insertion-" + idx);
    if (!target.classList.contains('hidden')) {
        target.classList.add('hidden');
    }
}

function addValidityOptionFields() {
    console.log('hello there');
    let target2 = document.getElementById("validity-insertion-2");
    let target3 = document.getElementById("validity-insertion-3");
    if (target2.classList.contains('hidden') && target3.classList.contains('hidden')) {
        target2.classList.remove("hidden");
    }
    else if (!target2.classList.contains('hidden') && target3.classList.contains('hidden')) {
        target3.classList.remove("hidden");
    }

}
function removeValidityOptionFields(idx) {
    // let target2 = document.getElementById("validity-insertion-2");
    // let target3 = document.getElementById("validity-insertion-3");
    // if (!target2.classList.contains('hidden') && !target3.classList.contains('hidden')) {
    //     target3.classList.add("hidden");
    // }
    // else if (target2.classList.contains('hidden') && !target3.classList.contains('hidden')) {
    //     target2.classList.add("hidden");
    // }

    let target = document.getElementById("validity-insertion-" + idx);
    if (!target.classList.contains('hidden')) {
        target.classList.add('hidden');
    }
}