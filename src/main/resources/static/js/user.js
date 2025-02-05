document.addEventListener('DOMContentLoaded', () => {
    loadUsers();
});

// 1. Загрузка пользователей
async function loadUsers() {
    try {
        const res = await fetch('/api/user');
        const user = await res.json();
        renderUsers(user);
    } catch (err) {
        console.error('Ошибка загрузки:', err);
    }
}


// Отрисовать таблицу
function renderUsers(u) {
    const tbody = document.getElementById('usersTableBody');
    tbody.innerHTML = '';
    const roleNames = u.roles?.map(r => typeof r === 'object' ? r.name : r).join(', ');
    const tr = document.createElement('tr');
    tr.innerHTML = `
            <td>${u.id}</td>
            <td>${u.firstName}</td>
            <td>${u.lastName}</td>
            <td>${u.email}</td>
        `;
    tbody.appendChild(tr);
}

