document.addEventListener('click', function(e) {
    if (e.target.classList.contains('delete-btn')) {
        e.preventDefault();//para que no siga el enlace
        
        const message = e.target.dataset.confirm || '¿Estás seguro?';
        
        if (confirm(message)) {
            window.location.href = e.target.href;//después del mensaje de feedback se redirige
        }
    }
});