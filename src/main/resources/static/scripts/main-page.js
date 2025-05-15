document.addEventListener('DOMContentLoaded', () => {
    const sortWrapper = document.querySelector('.sort-wrapper');
    const sortMenu = document.querySelector('.sort-menu');
    const sortToggle = document.querySelector('.sort-toggle');

    // Figyeljük a sort-wrapper kattintását
    sortWrapper.addEventListener('click', () => {
        // Ha a sort-wrapper aktív, mutatjuk a menüt
        if (sortWrapper.classList.contains('active')) {
            sortWrapper.classList.remove('active');
        } else {
            sortWrapper.classList.add('active');
        }

        // Szimbólum váltás
        sortToggle.textContent = sortWrapper.classList.contains('active') ? '–' : '+';
    });
});