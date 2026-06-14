<script setup>
import { ref, onMounted } from 'vue'
import api from '@/api/api'

const nomeDoClube = ref('')
const data = ref('')
const integrantes = ref([])
const selecionados = ref([])
const mensagem = ref('')

onMounted(async () => {
  const response = await api.get('/integrantes')
  integrantes.value = response.data
})

async function cadastrar() {
  try {
    await api.post('/times', {
      nomeDoClube: nomeDoClube.value,
      data: data.value,
      composicaoTime: selecionados.value.map(id => ({ integrante: { id } }))
    })
    mensagem.value = 'Time cadastrado com sucesso!'
    nomeDoClube.value = ''
    data.value = ''
    selecionados.value = []
  } catch (e) {
    mensagem.value = 'Erro ao cadastrar time!'
  }
}
</script>

<template>
  <div style="padding: 2rem;">
    <h2>Cadastrar Time</h2>
    <div>
      <input v-model="nomeDoClube" placeholder="Nome do Clube" style="margin-right: 1rem;" />
      <input v-model="data" type="date" style="margin-right: 1rem;" />
    </div>
    <br />
    <h3>Selecione os Integrantes</h3>
    <div v-for="integrante in integrantes" :key="integrante.id">
      <input
        type="checkbox"
        :value="integrante.id"
        v-model="selecionados"
      />
      {{ integrante.nome }} - {{ integrante.funcao }}
    </div>
    <br />
    <button @click="cadastrar">Cadastrar Time</button>
    <p v-if="mensagem">{{ mensagem }}</p>
  </div>
</template>