function animateTextReveal(element, finalText, speed = 50) {
    const chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+=-<>?/|';
    let displayed = '';
    let i = 0;

    function revealNextChar() {
        if (i >= finalText.length) return;

        let interval = setInterval(() => {
            element.textContent = displayed + chars[Math.floor(Math.random() * chars.length)];
        }, speed / 3);

        setTimeout(() => {
            clearInterval(interval);
            displayed += finalText[i];
            element.textContent = displayed;
            i++;
            revealNextChar();
        }, speed);
    }

    revealNextChar();
}

document.addEventListener('DOMContentLoaded', () => {
    // welcome animation
    const el = document.querySelector('.welcome-text');
    const finalText = el.getAttribute('data-text');
    animateTextReveal(el, finalText, 80);

    // scroll behavior
    let lastScrollTop = 0;
    const header = document.querySelector('header');
    window.addEventListener('scroll', () => {
        const scrollTop = window.pageYOffset || document.documentElement.scrollTop;
        header.classList.toggle('hidden', scrollTop > lastScrollTop && scrollTop > 0);
        lastScrollTop = Math.max(scrollTop, 0);
    });

    // dropdown toggle
    const categories = document.querySelector('.categories-menu-item');
    categories.addEventListener('click', () => {
        document.querySelector('.categories-menu').classList.toggle('hidden');
        categories.classList.toggle('open');
    });


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

document.addEventListener("DOMContentLoaded", () => {
    const observer = new IntersectionObserver((entries) => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                entry.target.classList.add('visible');
            } else {
                entry.target.classList.remove('visible');
            }
        });
    }, { threshold: 0.1 });

    document.querySelectorAll('.product-card, .product-nav').forEach(el => {
        el.classList.add('fade-in');
        observer.observe(el);
    });
});

document.addEventListener("DOMContentLoaded", function () {
    const carousel = document.getElementById('carousel');
    if (!carousel) {
        console.error("A 'carousel' elem nem található!");
        return;
    }

    const cardWidthRem = 24; // 10rem széles + 2x1rem margó + puffer
    const cardsPerPage = 3;
    const totalCards = carousel.children.length;
    const maxIndex = totalCards - cardsPerPage;
    let currentIndex = 0;

    function scrollCarousel(direction) {
        currentIndex += direction;
        if (currentIndex < 0) currentIndex = 0;
        if (currentIndex > maxIndex) currentIndex = maxIndex;

        const scrollAmount = currentIndex * cardWidthRem;
        carousel.style.transform = `translateX(${-scrollAmount}rem)`;
    }

    // Ha máshonnan akarod elérni scrollCarousel-t:
    window.scrollCarousel = scrollCarousel;
});