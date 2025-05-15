document.addEventListener("DOMContentLoaded", async () => {
    try {
        const cartSizeResponse = await fetch('http://localhost:8081/cart/size', {
            method: 'GET',
            headers: { 'Content-Type': 'application/json' }
        });

        if (cartSizeResponse.ok) {
            const text = await cartSizeResponse.text();
            const counter = document.getElementById('my-cart-counter');
            if (counter) {
                counter.textContent = text;
                counter.classList.remove('pop-animation');
                void counter.offsetWidth;
                counter.classList.add('pop-animation');
            }
        } else {
            console.error("Hiba történt a kosár méretének lekérdezésekor");
        }
    } catch (error) {
        console.log(error)
        console.error("AJAX hiba:", error);
    }
});

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

document.addEventListener('DOMContentLoaded', () => {
    const profileIcon = document.getElementById('profile-icon');
    const dropdown = document.getElementById('profile-dropdown');

    profileIcon.addEventListener('click', () => {
        dropdown.style.display = dropdown.style.display === 'flex' ? 'none' : 'flex';
    });

    window.addEventListener('click', (e) => {
        if (!profileIcon.contains(e.target) && !dropdown.contains(e.target)) {
            dropdown.style.display = 'none';
        }
    });
});

document.addEventListener("DOMContentLoaded", async function (){
    const logo = document.getElementById("header-logo");
    logo.addEventListener('click', () => {
        localStorage.clear();
    });

    const currentPath = window.location.pathname;
    console.log(currentPath === '/');
    if (currentPath !== '/') {
        document.querySelectorAll(".child-category").forEach(item => {
            item.addEventListener("click", async (event) => {
                // A kattintott elemhez tartozó categoryId értékének lekérése
                let categoryId = event.target.getAttribute("data-id");
                console.log("Kattintott gyermek ID:", categoryId);
                document.querySelector('.categories-menu').classList.toggle('hidden');

                await localStorage.setItem("currentPage", 0);
                await localStorage.setItem("categoryId", categoryId);
                await localStorage.setItem("currentFilterName", event.target.textContent);
                window.location.href = "/";
            });
        });
    }
});