<script setup lang="ts">
import { ref } from 'vue'
import api from '@/api/api'

const nome = ref('')
const funcao = ref('')
const mensagem = ref('')
const erro = ref(false)

async function cadastrar() {
  if (!nome.value || !funcao.value) {
    mensagem.value = 'Preencha todos os campos.'
    erro.value = true
    return
  }

  try {
    await api.post('/integrantes', { nome: nome.value, funcao: funcao.value })
    mensagem.value = 'Integrante cadastrado com sucesso!'
    erro.value = false
    nome.value = ''
    funcao.value = ''
  } catch (e) {
    mensagem.value = 'Erro ao cadastrar integrante.'
    erro.value = true
  }
}
</script>

<template>
  <div class="form-container">
    <div class="card">
      <h2>Cadastrar Integrante</h2>

      <div class="form-group">
        <label>Nome</label>
        <input v-model="nome" type="text" placeholder="Nome do integrante" />
      </div>

      <div class="form-group">
        <label>Função</label>
        <input v-model="funcao" type="text" placeholder="Função ou posição" />
      </div>

      <button class="btn" @click="cadastrar">Cadastrar</button>

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
  max-width: 450px;
}

.card h2 {
  font-size: 1.25rem;
  margin-bottom: 1.25rem;
}

.form-group {
  display: flex;
  flex-direction: column;
  gap: 0.4rem;
  margin-bottom: 1rem;
}

label {
  font-size: 0.85rem;
  color: #c4c4cc;
}

input {
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

.btn {
  background: #00b37e;
  color: #fff;
  border: none;
  font-weight: 600;
  padding: 0.65rem;
  border-radius: 4px;
  width: 100%;
  cursor: pointer;
}

.btn:hover {
  background: #008e63;
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