const API_URL = 'http://localhost:8080';

// ===== HELPER: Alerts =====
const showAlert = (id, message, isError = false) => {
    const box = document.getElementById(id);
    if(!box) return;
    box.textContent = message;
    box.className = `alert show ${isError ? 'alert-error' : 'alert-success'}`;
    setTimeout(() => { box.className = 'alert'; }, 4000);
};

// ===== LOGIN PAGE LOGIC =====
if (window.location.pathname.endsWith('index.html') || window.location.pathname === '/') {
    
    const loginForm = document.getElementById('loginForm');
    const createMasterLink = document.getElementById('createMasterLink');

    if(loginForm) {
        loginForm.addEventListener('submit', async (e) => {
            e.preventDefault();
            const user = document.getElementById('username').value;
            const pass = document.getElementById('password').value;

            try {
                const masterDb = localStorage.getItem('guilda_master_account');
                const masterPass = localStorage.getItem('guilda_master_pass');
                
                if(masterDb && masterDb === user && masterPass === pass) {
                    localStorage.setItem('guilda_master', user);
                    window.location.href = 'dashboard.html';
                } else if(!masterDb) {
                    showAlert('loginAlert', 'Nenhum Mestre registrado. Cadastre-se primeiro.', true);
                } else {
                    showAlert('loginAlert', 'Senha Incorreta ou Mestre Inválido.', true);
                }
            } catch (err) {
                console.error(err);
            }
        });
    }

    if(createMasterLink) {
        createMasterLink.addEventListener('click', async (e) => {
            e.preventDefault();
            
            if (localStorage.getItem('guilda_master_account')) {
                showAlert('loginAlert', '❌ ERRO: A Guilda só pode ter um (1) único Mestre registrado.', true);
                return;
            }
            
            const user = prompt("Defina o Título/Usuário para o novo Mestre da Guilda:");
            const pass = prompt("Defina a Senha de Acesso:");
            if(user && pass) {
                localStorage.setItem('guilda_master_account', user.trim());
                localStorage.setItem('guilda_master_pass', pass);
                showAlert('loginAlert', '👑 Mestre Nomeado! Use os dados preenchidos acima para Entrar no Painel.', false);
            }
        });
    }
}

// ===== DASHBOARD LOGIC =====
if (window.location.pathname.endsWith('dashboard.html')) {
    
    const logoutBtn = document.getElementById('logoutBtn');
    const advForm = document.getElementById('aventurreiroForm');
    const refreshBtn = document.getElementById('refreshBtn');
    const tbody = document.getElementById('aventurreirosList');

    if(logoutBtn) {
        logoutBtn.addEventListener('click', () => {
            localStorage.removeItem('guilda_master');
            window.location.href = 'index.html';
        });
    }

    const loadAventureiros = async () => {
        try {
            const response = await fetch(`${API_URL}/aventureiros`);
            if(!response.ok) throw new Error("Falha ao buscar");
            const data = await response.json();
            
            tbody.innerHTML = '';
            document.getElementById('totalInfo').innerHTML = `Total listado: <b>${data.length}</b>`;

            if(data.length === 0) {
                tbody.innerHTML = '<tr><td colspan="6" style="text-align: center; color: var(--text-secondary);">Nenhum aventureiro alistado.</td></tr>';
                return;
            }

            data.forEach(adv => {
                const tr = document.createElement('tr');
                const badgeClass = adv.ativo ? 'badge-active' : 'badge-inactive';
                const statusTxt = adv.ativo ? 'Ativo' : 'Afastado';
                const compTag = adv.nomeCompanheiro ? `<span class="badge" style="background:var(--primary); color:white; font-size:0.65rem; margin-left:8px;">🐾 ${adv.nomeCompanheiro}</span>` : '';
                
                const btnAcao = adv.ativo 
                    ? `<button onclick="inativar(${adv.id})" style="background:transparent; border:none; color:var(--error); cursor:pointer;" title="Afastar Aventureiro">✖</button>`
                    : `<button onclick="ativar(${adv.id})" style="background:transparent; border:none; color:var(--primary); cursor:pointer;" title="Reativar Aventureiro">✔️</button>`;

                tr.innerHTML = `
                    <td style="color:var(--text-secondary)">#${adv.id}</td>
                    <td style="font-weight: 500">${adv.nome} ${compTag}</td>
                    <td style="color:var(--text-secondary)">${adv.classe.substring(0,1) + adv.classe.substring(1).toLowerCase()}</td>
                    <td><b>Lvl ${adv.nivel}</b></td>
                    <td><span class="badge ${badgeClass}">${statusTxt}</span></td>
                    <td>
                        <button onclick="gerenciarCompanheiro(${adv.id})" style="background:transparent; border:none; cursor:pointer;" title="Gerenciar Companheiro">🐾</button>
                        ${btnAcao}
                    </td>
                `;
                tbody.appendChild(tr);
            });
        } catch(err) {
            console.error(err);
            tbody.innerHTML = '<tr><td colspan="6" style="text-align: center; color: var(--error);">Servidor Offline. Rode o Spring Boot!</td></tr>';
        }
    };

    if(advForm) {
        advForm.addEventListener('submit', async (e) => {
            e.preventDefault();
            const btn = advForm.querySelector('button');
            const originalText = btn.textContent;
            btn.textContent = 'Enviando...';
            btn.disabled = true;

            const dto = {
                nome: document.getElementById('advNome').value,
                classe: document.getElementById('advClasse').value,
                nivel: parseInt(document.getElementById('advNivel').value)
            };

            try {
                const res = await fetch(`${API_URL}/aventureiros`, {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(dto)
                });

                if(res.ok) {
                    showAlert('cadastroAlert', '✨ Aventureiro registrado na Guilda com sucesso!', false);
                    advForm.reset();
                    loadAventureiros();
                } else {
                    showAlert('cadastroAlert', 'Erro de validação, verifique os campos.', true);
                }
            } catch(err) {
                showAlert('cadastroAlert', 'Erro de conexão com servidor.', true);
            } finally {
                btn.textContent = originalText;
                btn.disabled = false;
            }
        });
    }

    if(refreshBtn) {
        refreshBtn.addEventListener('click', () => {
            const i = refreshBtn.innerHTML;
            refreshBtn.innerHTML = '⏳';
            loadAventureiros().finally(() => refreshBtn.innerHTML = i);
        });
    }

    // Export function so it can be called from onclick
    window.inativar = async (id) => {
        if(confirm("Deseja realmente afastar este aventureiro?")) {
            await fetch(`${API_URL}/aventureiros/${id}`, { method: 'DELETE' });
            loadAventureiros();
        }
    }

    window.ativar = async (id) => {
        if(confirm("Deseja reativar o contrato deste aventureiro?")) {
            await fetch(`${API_URL}/aventureiros/${id}/recrutar`, { method: 'PATCH' });
            loadAventureiros();
        }
    }

    window.gerenciarCompanheiro = async (id) => {
        const action = prompt("Digite 'A' para Adicionar/Editar companheiro ou 'R' para Remover:");
        if (!action) return;

        if (action.toUpperCase() === 'R') {
            if(confirm("Tem certeza que deseja soltar o companheiro?")) {
                await fetch(`${API_URL}/aventureiros/${id}/companheiro`, { method: 'DELETE' });
                showAlert('cadastroAlert', '🐾 Companheiro solto na natureza!', false);
                loadAventureiros();
            }
        } else if (action.toUpperCase() === 'A') {
            const nome = prompt("Nome do Companheiro:");
            const especie = prompt("Espécie (LOBO, TIGRE, AGUIA, CORUJA, DRAGAO_MENOR):");
            const lealdade = prompt("Nível de Lealdade (0 a 100):");
            
            if(nome && especie && lealdade) {
                const dto = { nome, especie: especie.toUpperCase(), lealdade: parseInt(lealdade) };
                const res = await fetch(`${API_URL}/aventureiros/${id}/companheiro`, {
                    method: 'PUT',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(dto)
                });
                if(res.ok) {
                    showAlert('cadastroAlert', '🐾 Companheiro vinculado com sucesso!', false);
                    loadAventureiros();
                } else {
                    showAlert('cadastroAlert', 'Erro ao vincular companheiro. Verifique a Espécie.', true);
                }
            }
        }
    }

    // Inicial carga
    loadAventureiros();
}
