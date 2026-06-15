<script setup lang="ts">
import { ref, onMounted } from 'vue'
import api from '@/api/api'

const nomeDoClube = ref('')
const data = ref('')
const integrantes = ref<any[]>([])
const selecionados = ref<number[]>([])
const mensagem = ref('')
const erro = ref(false)

onMounted(async () => {
  try {
    const response = await api.get('/integrantes')
    integrantes.value = response.data
  } catch (e) {
    console.error('Erro ao buscar integrantes:', e)
  }
})

async function cadastrar() {
  if (!nomeDoClube.value || !data.value || selecionados.value.length === 0) {
    mensagem.value = 'Preencha os dados do clube e selecione os integrantes.'
    erro.value = true
    return
  }

  try {
    await api.post('/times', {
      nomeDoClube: nomeDoClube.value,
      data: data.value,
      composicaoTime: selecionados.value.map(id => ({ integrante: { id } }))
    })
    mensagem.value = 'Time cadastrado com sucesso!'
    erro.value = false
    nomeDoClube.value = ''
    data.value = ''
    selecionados.value = []
  } catch (e) {
    mensagem.value = 'Erro ao cadastrar time.'
    erro.value = true
  }
}
</script>

<template>
  <div class="form-container">
    <div class="card">
      <h2>Cadastrar Time</h2>

      <div class="form-row">
        <div class="form-group">
          <label>Nome do Clube</label>
          <input v-model="nomeDoClube" type="text" placeholder="Nome do clube" />
        </div>
        <div class="form-group">
          <label>Data</label>
          <input v-model="data" type="date" />
        </div>
      </div>

      <div class="selection-box">
        <label class="selection-label">Selecione os Integrantes</label>

        <div class="checkbox-list" v-if="integrantes.length">
          <label v-for="integrante in integrantes" :key="integrante.id" class="checkbox-item">
            <input type="checkbox" :value="integrante.id" v-model="selecionados" />
            <span>{{ integrante.nome }} — <small>{{ integrante.funcao }}</small></span>
          </label>
        </div>
        <p v-else class="empty-text">Nenhum integrante disponível.</p>
      </div>

      <button class="btn" @click="cadastrar">Cadastrar Time</button>

      <p v-if="mensagem" :class="['feedback', { 'error-text': erro }]">
        {{ mensagem }}
      </p>
    </div>
  </div>
</template>

<style scoped>
.form-container {
  display: flex;
  justify-content: center;
}

.card {
  background: #202024;
  border: 1px solid #323238;
  border-radius: 6px;
  padding: 1.5rem;
  width: 100%;
  max-width: 600px;
}

.card h2 {
  font-size: 1.25rem;
  margin-bottom: 1.25rem;
}

.form-row {
  display: grid;
  grid-template-columns: 2fr 1fr;
  gap: 1rem;
  margin-bottom: 1.25rem;
}

@media (max-width: 480px) {
  .form-row {
    grid-template-columns: 1fr;
  }
}

.form-group {
  display: flex;
  flex-direction: column;
  gap: 0.4rem;
}

label {
  font-size: 0.85rem;
  color: #c4c4cc;
}

input[type="text"], input[type="date"] {
  background: #121214;
  border: 1px solid #323238;
  border-radius: 4px;
  padding: 0.65rem 0.85rem;
  color: #fff;
  font-family: inherit;
}

input:focus {
  outline: none;
  border-color: #00b37e;
}

.selection-box {
  margin-top: 1rem;
}

.selection-label {
  display: block;
  margin-bottom: 0.5rem;
}

.checkbox-list {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
  max-height: 200px;
  overflow-y: auto;
  background: #121214;
  border: 1px solid #323238;
  border-radius: 4px;
  padding: 0.75rem;
}

.checkbox-item {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  font-size: 0.9rem;
  cursor: pointer;
}

.checkbox-item small {
  color: #8d8d99;
}

.btn {
  background: #00b37e;
  color: #fff;
  border: none;
  font-weight: 600;
  padding: 0.75rem;
  border-radius: 4px;
  width: 100%;
  cursor: pointer;
  margin-top: 1.5rem;
}

.btn:hover {
  background: #008e63;
}

.empty-text {
  font-size: 0.9rem;
  color: #8d8d99;
}

.feedback {
  margin-top: 1rem;
  font-size: 0.9rem;
  color: #00b37e;
}

.feedback.error-text {
  color: #f75a68;
}
</style>