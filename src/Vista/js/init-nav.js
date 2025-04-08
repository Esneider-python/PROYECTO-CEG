document.addEventListener('DOMContentLoaded', function() {
    var elemsDropdown = document.querySelectorAll('.dropdown-trigger');
    var instancesDropdown = M.Dropdown.init(elemsDropdown, {
        coverTrigger: false,
        constrainWidth: false
    });
});
