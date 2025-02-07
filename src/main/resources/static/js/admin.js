
    document.addEventListener('DOMContentLoaded', () => {

    loadUsers();        // Загрузить пользователей при старте
    initCreateForm();   // Привязать submit на форму создания
    initEditForm();     // Привязать submit на форму Edit
    initDelete();       // Привязать клик на кнопку Delete
});

    // 1. Загрузка пользователей
    async function loadUsers() {
    try {
    const res = await fetch('/api/admin/users');
    const users = await res.json();
    renderUsers(users);
} catch (err) {
    console.error('Ошибка загрузки:', err);
}
}


    // Отрисовать таблицу
    function renderUsers(users) {
    const tbody = document.getElementById('usersTableBody');
    tbody.innerHTML = '';
    users.forEach(u => {
    const roleNames = u.roles?.map(r => typeof r === 'object' ? r.name : r).join(', ');
    const tr = document.createElement('tr');
    tr.innerHTML = `
                <td>${u.id}</td>
                <td>${u.firstName}</td>
                <td>${u.lastName}</td>
                <td>${u.email}</td>
                <td>${roleNames||''}</td>
                <td><button class="btn btn-sm btn-info text-white"
                    onclick="openEditModal(${u.id}, '${u.firstName}', '${u.lastName}', '${u.email}', '${roleNames||''}')">
                    Edit</button></td>
                <td><button class="btn btn-sm btn-danger"
                    onclick="openDeleteModal(${u.id}, '${u.firstName}')">Delete</button></td>
            `;
    tbody.appendChild(tr);
});
}

    // 2. Создание пользователя (submit createUserForm)
    function initCreateForm() {
    const form = document.getElementById('createUserForm');
    form.addEventListener('submit', async (e) => {
    e.preventDefault();
    const firstName = document.getElementById('firstName').value;
    const lastName = document.getElementById('lastName').value;
    const email = document.getElementById('emailNew').value;
    const password = document.getElementById('passwordNew').value;

    // Роли
    const adminChecked = document.getElementById('adminCheck').checked;
    const userChecked = document.getElementById('userCheck').checked;
    const roles = [];
    if (adminChecked) roles.push({ id: 1 , name: 'ROLE_ADMIN' });
    if (userChecked) roles.push({ id: 2 , name: 'ROLE_USER' });

    const newUser = { firstName, lastName, email, password, roles };

    try {
    const res = await fetch('/api/admin/users', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(newUser)
});
    if (res.ok) {
    form.reset();
    loadUsers();
} else if (res.status === 409) {
    alert('Email уже используется!');
} else {
    console.error('Ошибка создания:', res.status);
}
} catch (err) {
    console.error('Ошибка:', err);
}
});
}

    // 3. Редактирование пользователя (submit editUserForm)
    function initEditForm() {
    const form = document.getElementById('editUserForm');
    form.addEventListener('submit', async (e) => {
    e.preventDefault();
    const id = document.getElementById('editId').value;
    const firstName = document.getElementById('editFirstName').value;
    const lastName = document.getElementById('editLastName').value;
    const email = document.getElementById('editEmail').value;
    const password = document.getElementById('editPassword').value;

    const adminChecked = document.getElementById('editAdminCheck').checked;
    const userChecked = document.getElementById('editUserCheck').checked;
    const roles = [];

    if (adminChecked) roles.push({ id: 1 , name: 'ADMIN' });
    if (userChecked) roles.push({id: 2 ,  name: 'USER' });

    const updatedUser = { id, firstName, lastName, email, password, roles };

    try {
    const res = await fetch('/api/admin/users', {
    method: 'PATCH',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(updatedUser)
});
    if (res.ok) {
    // Закрываем модал
    const modal = bootstrap.Modal.getInstance(document.getElementById('editModal'));
    modal.hide();
    loadUsers();
} else if (res.status === 409) {
    alert('Email уже используется!');
} else {
    console.error('Ошибка обновления:', res.status);
}
} catch (err) {
    console.error('Ошибка:', err);
}
});
}

    // Открыть модал Edit
    function openEditModal(id, firstName, lastName, email, roleNames) {
    document.getElementById('editId').value = id;
    document.getElementById('editFirstName').value = firstName;
    document.getElementById('editLastName').value = lastName;
    document.getElementById('editEmail').value = email;
    document.getElementById('editPassword').value = '';

    document.getElementById('editAdminCheck').checked = roleNames.includes('ADMIN');
    document.getElementById('editUserCheck').checked = roleNames.includes('USER');

    const modal = new bootstrap.Modal(document.getElementById('editModal'));
    modal.show();
}

    // 4. Удаление пользователя
    function initDelete() {
    const deleteBtn = document.getElementById('confirmDeleteBtn');
    deleteBtn.addEventListener('click', async () => {
    const id = document.getElementById('deleteUserId').textContent;
    try {
    const res = await fetch(`/api/admin/users?id=${id}`, { method: 'DELETE' });
    if (res.ok) {
    // Закрываем модал
    const modal = bootstrap.Modal.getInstance(document.getElementById('deleteModal'));
    modal.hide();
    loadUsers();
} else {
    console.error('Ошибка удаления:', res.status);
}
} catch (err) {
    console.error('Ошибка:', err);
}
});
}

    // Открыть модал Delete
    function openDeleteModal(id, firstName) {
    document.getElementById('deleteUserId').textContent = id;
    document.getElementById('deleteUserName').textContent = firstName;
    const modal = new bootstrap.Modal(document.getElementById('deleteModal'));
    modal.show();
}