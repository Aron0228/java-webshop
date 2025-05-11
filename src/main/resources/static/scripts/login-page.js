document.addEventListener("DOMContentLoaded", () => {
    const el = document.getElementById('login-message');
    el.classList.remove('show');
    el.classList.add('hide');
    el.style.display = 'none';
});

document.addEventListener('DOMContentLoaded', function () {
    const form = document.getElementById("login-form");

    form.addEventListener('submit', async function (e) {
        e.preventDefault();

        const email = document.getElementById('email').value;
        const password = document.getElementById('password').value;
        const loginBtn = document.querySelector('.login-btn');

        try {
            const response = await fetch('/api/auth/login/check-credentials', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ email, password })
            });

            const text = await response.text();
            if (response.ok && text === "OK") {
                loginBtn.innerHTML = '<i class="fa fa-spinner fa-spin"></i> Logging in...';
                hideMessage();
                form.submit();
            } else if (response.ok && text === "User not found") {
                showMessage('User not found with the given credentials.', 'danger', 4000);
            } else {
                showMessage('Unknown error.', 'warning', 4000);
            }
        } catch (error) {
            console.log(error);
            showMessage('Unknown error.', 'danger', 4000);
        }
    });

    function showMessage(text, type = 'danger', timeout = 4000) {
        const el = document.getElementById('login-message');

        el.textContent = text;
        el.style.display = 'block';
        void el.offsetHeight;
        el.className = `custom-alert custom-alert-${type} show`;

        if (timeout) {
            setTimeout(() => {
                hideMessage();
            }, timeout);
        }
    }

    function hideMessage() {
        const el = document.getElementById('login-message');
        el.classList.remove('show');
        el.classList.add('hide');

        setTimeout(() => {
            el.style.display = 'none';
        }, 300);
    }
});