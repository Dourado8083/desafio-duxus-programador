<script setup>
import { ref } from 'vue'
import api from '@/api/api'

const nome = ref('')
const funcao = ref('')
const mensagem = ref('')

async function cadastrar() {
  try {
    await api.post('/integrantes', { nome: nome.value, funcao: funcao.value })
    mensagem.value = 'Integrante cadastrado com sucesso!'
    nome.value = ''
    funcao.value = ''
  } catch (e) {
    mensagem.value = 'Erro ao cadastrar integrante!'
  }
}
</script>

<template>
  <div style="padding: 2rem;">
    <h2>Cadastrar Integrante</h2>
    <div>
      <input v-model="nome" placeholder="Nome" style="margin-right: 1rem;" />
      <input v-model="funcao" placeholder="Função" style="margin-right: 1rem;" />
      <button @click="cadastrar">Cadastrar</button>
    </div>
    <p v-if="mensagem">{{ mensagem }}</p>
  </div>
</template>