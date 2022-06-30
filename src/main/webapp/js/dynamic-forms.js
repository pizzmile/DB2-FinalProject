// Utility functions
function safeAdd(element, className) {
    if (!element.classList.contains(className)) {
        element.classList.add(className)
    }
}
function safeRemove(element, className) {
    if (element.classList.contains(className)) {
        element.classList.remove(className)
    }
}
function updateIncrementalId(node, id, idValue) {
    let element = node.querySelector('#' + id);
    element.id = element.id += numOfServices;
    return element;
}

// Handle validity options
function addValidityOptionInput() {
    let addValidityButton = document.getElementById("add-validity");
    let validityOption2Input = document.getElementById("validity-insertion-2");
    let validityOption3Input = document.getElementById("validity-insertion-3");
    if (validityOption2Input.classList.contains('hidden') && validityOption3Input.classList.contains('hidden')) {
        // Show option 2
        validityOption2Input.classList.remove("hidden");
        // Set inputs as required
        document.getElementById('duration-2').required = true;
        document.getElementById('fee-2').required = true;
        // Set button active/inactive style
        safeRemove(addValidityButton, 'inactive-btn');
        safeAdd(addValidityButton, 'active-btn');
        addValidityButton.disabled = false;
    }
    else if (!validityOption2Input.classList.contains('hidden') && validityOption3Input.classList.contains('hidden')) {
        // Show option 3
        validityOption3Input.classList.remove("hidden");
        // Set inputs as required
        document.getElementById('duration-3').required = true;
        document.getElementById('fee-3').required = true;
        // Set add button active/inactive style
        safeAdd(addValidityButton, 'inactive-btn');
        safeRemove(addValidityButton, 'active-btn');
        addValidityButton.disabled = true;
    }

}
function removeValidityOptionInput(idx) {
    let addValidityButton = document.getElementById("add-validity");
    let validityOptionInput = document.getElementById("validity-insertion-" + idx);

    if (!validityOptionInput.classList.contains('hidden')) {
        // Hide option
        validityOptionInput.classList.add('hidden');
        // Set inputs as not required
        document.getElementById('duration-' + idx).required = false;
        document.getElementById('fee-' + idx).required = false;
        // Set add button active/inactive style
        safeRemove(addValidityButton, 'inactive-btn');
        safeAdd(addValidityButton, 'active-btn');
        addValidityButton.disabled = false;
    }
}

// Handle service type
function updateServiceAttributes(idx) {
    let serviceTypeSelector = document.getElementById('type-' + idx);
    let selectedValue = serviceTypeSelector.value;

    let minutesInput = document.getElementById('minutes-wrapper-' + idx);
    let smsInput = document.getElementById('sms-wrapper-' + idx);
    let gigaInput = document.getElementById('giga-wrapper-' + idx);

    switch (selectedValue) {
        case "0": // Fixed phone
            if (!minutesInput.classList.contains('hidden')) {
                minutesInput.classList.add('hidden');
            }
            if (!smsInput.classList.contains('hidden')) {
                smsInput.classList.add('hidden');
            }
            if (!gigaInput.classList.contains('hidden')) {
                gigaInput.classList.add('hidden');
            }
            break;
        case "1": // Fixed internet
            if (!minutesInput.classList.contains('hidden')) {
                minutesInput.classList.add('hidden');
            }
            if (!smsInput.classList.contains('hidden')) {
                smsInput.classList.add('hidden');
            }
            if (gigaInput.classList.contains('hidden')) {
                gigaInput.classList.remove('hidden');
            }
            break;
        case "2": // Mobile phone
            if (minutesInput.classList.contains('hidden')) {
                minutesInput.classList.remove('hidden');
            }
            if (smsInput.classList.contains('hidden')) {
                smsInput.classList.remove('hidden');
            }
            if (!gigaInput.classList.contains('hidden')) {
                gigaInput.classList.add('hidden');
            }
            break;
        case "3": // Mobile internet
            if (!minutesInput.classList.contains('hidden')) {
                minutesInput.classList.add('hidden');
            }
            if (!smsInput.classList.contains('hidden')) {
                smsInput.classList.add('hidden');
            }
            if (gigaInput.classList.contains('hidden')) {
                gigaInput.classList.remove('hidden');
            }
            break;
        default:
            break;
    }
}

// Handle service add/remove
var numOfServices = 1;

function addServiceInput() {
    let serviceInputsWrapper = document.getElementById('services-wrapper');         // get parent wrapper
    let node = new DOMParser().parseFromString(string, 'text/html').body.firstChild;    // create new node

    // Update incremental identifiers
    numOfServices += 1;
    node.id = node.id += numOfServices;

    let typeSelector = updateIncrementalId(node, 'type-', numOfServices);
    typeSelector.setAttribute('name', 'type-' + numOfServices);
    typeSelector.setAttribute('onchange', 'updateServiceAttributes(' + numOfServices + ')')

    updateIncrementalId(node, 'minutes-wrapper-', numOfServices);
    updateIncrementalId(node, 'sms-wrapper-', numOfServices);
    updateIncrementalId(node, 'giga-wrapper-', numOfServices);

    let minutesInput = updateIncrementalId(node, 'minutes-', numOfServices);
    minutesInput.setAttribute('name', 'minutes-' + numOfServices);
    let extraMinutesInput = updateIncrementalId(node, 'extra-minutes-', numOfServices);
    extraMinutesInput.setAttribute('name', 'extra-minutes-' + numOfServices);
    let smsInput = updateIncrementalId(node, 'sms-', numOfServices);
    smsInput.setAttribute('name', 'sms-' + numOfServices);
    let extraSmsInput = updateIncrementalId(node, 'extra-sms-', numOfServices);
    extraSmsInput.setAttribute('name', 'extra-sms-' + numOfServices);
    let gigaInput = updateIncrementalId(node, 'giga-', numOfServices);
    gigaInput.setAttribute('name', 'giga-' + numOfServices);
    let extraGigaInput = updateIncrementalId(node, 'extra-giga-', numOfServices);
    extraGigaInput.setAttribute('name', 'extra-giga-' + numOfServices);

    let removeButton = updateIncrementalId(node, 'remove-button-', numOfServices)
    removeButton.setAttribute('onclick', 'removeServiceInput(' + numOfServices + ')');

    // Append new node
    serviceInputsWrapper.appendChild(node);
}

function removeServiceInput(idx) {
    let serviceInputsWrapper = document.getElementById('services-wrapper');
    let node = document.getElementById('service-insertion-' + idx);
    serviceInputsWrapper.removeChild(node);
}


string = '<!-- Service insertion fields -->' + '<div class="w100 row aln-end jst-center margin-bottom-children-1" id="service-insertion-">\n' +
    '                  <div class="col aln-start jst-center w90 pr-1">\n' +
    '                    <!-- Type selection -->\n' +
    '                    <div class="row w100 aln-center jst-start">\n' +
    '                      <div class="pr-2">\n' +
    '                        <label>Service type: </label>\n' +
    '                      </div>\n' +
    '                      <select name="type-" id="type-" required>\n' + // HERE ****
    '                        <option value="0">Fixed Phone</option>\n' +
    '                        <option value="1">Fixed Internet</option>\n' +
    '                        <option value="2">Mobile Phone</option>\n' +
    '                        <option value="3">Mobile Internet</option>\n' +
    '                      </select>\n' +
    '                    </div>\n' +
    '                    <!-- Params selection -->\n' +
    '                    <div class="col w100 aln-center jst-center">\n' +
    '                      <div class="row w100 aln-center jst-center hidden" id="minutes-wrapper-">\n' + // HERE ***
    '                        <div class="w50 pr-1">\n' +
    '                          <input type="number" min="0" step="1" name="minutes-" id="minutes-" placeholder="minutes" required\n' +
    '                                 class="input-fld w100">\n' + // HERE ****
    '                        </div>\n' +
    '                        <div class="w50 pl-1">\n' +
    '                          <input type="number" min="0" step="1" name="extra-minutes-" id="extra-minutes-" placeholder="extra minutes" required\n' +
    '                                 class="input-fld w100">\n' + // HERE ****
    '                        </div>\n' +
    '                      </div>\n' +
    '                      <div class="row w100 aln-center jst-center hidden" id="sms-wrapper-">\n' + // HERE ****
    '                        <div class="w50 pr-1">\n' +
    '                          <input type="number" min="0" step="1" name="sms-" id="sms-" placeholder="SMSs" required\n' +
    '                                 class="input-fld w100">\n' + // HERE ****
    '                        </div>\n' +
    '                        <div class="w50 pl-1">\n' +
    '                          <input type="number" min="0" step="1" name="extra-sms-" id="extra-sms-" placeholder="extra SMSs" required\n' +
    '                                 class="input-fld w100">\n' + // HERE ****
    '                        </div>\n' +
    '                      </div>\n' +
    '                      <div class="row w100 aln-center jst-center hidden" id="giga-wrapper-">\n' +
    '                        <div class="w50 pr-1">\n' +
    '                          <input type="number" min="0" step="1" name="giga-" id="giga-" placeholder="GIGAs" required\n' +
    '                                 class="input-fld w100">\n' + // HERE ****
    '                        </div>\n' +
    '                        <div class="w50 pl-1">\n' +
    '                          <input type="number" min="0" step="1" name="extra-giga-" id="extra-giga-" placeholder="extra GIGAs" required\n' +
    '                                 class="input-fld w100">\n' + // HERE ****
    '                        </div>\n' +
    '                      </div>\n' +
    '                    </div>\n' +
    '                  </div>\n' +
    '                  <!-- Add/remove insertion fields -->\n' +
    '                  <div class="col aln-start jst-end w10 pb-1 pl-1">\n' +
    '                    <a href="#" class="submit-btn w100 active-btn" onclick="addServiceFields()" id="remove-button-">-</a>\n' + // HERE ****
    '                  </div>\n' +
    '                </div>';