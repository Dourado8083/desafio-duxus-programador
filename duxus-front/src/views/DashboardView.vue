<script setup lang="ts">
import { ref, onMounted } from 'vue'
import api from '@/api/api'

const integranteMaisUsado = ref<any>(null)
const clubeMaisRecorrente = ref<string>('')
const funcaoMaisRecorrente = ref<string>('')
const contagemClubes = ref<Record<string, number>>({})
const contagemFuncoes = ref<Record<string, number>>({})
const integrantesTimeMaisRecorrente = ref<string[]>([])
const loading = ref(true)

onMounted(async () => {
  try {
const [integrante, clube, funcao, clubes, funcoes, timeMaisRecorrente] = await Promise.all([
  api.get('/api/times/integrante-mais-usado'),
  api.get('/api/times/clube-mais-recorrente'),
  api.get('/api/times/funcao-mais-recorrente'),
  api.get('/api/times/contagem-clubes'),
  api.get('/api/times/contagem-funcoes'),
  api.get('/api/times/integrantes-time-mais-recorrente')
])

    integranteMaisUsado.value = integrante.data
    clubeMaisRecorrente.value = clube.data
    funcaoMaisRecorrente.value = funcao.data
    contagemClubes.value = clubes.data
    contagemFuncoes.value = funcoes.data
    integrantesTimeMaisRecorrente.value = timeMaisRecorrente.data
  } catch (e) {
    console.error('Erro ao carregar dashboard:', e)
  } finally {
    loading.value = false
  }
})
</script>

<template>
  <div style="padding: 2rem;">
    <h2>Dashboard</h2>

    <div v-if="loading">Carregando...</div>

    <div v-else style="display: grid; grid-template-columns: repeat(3, 1fr); gap: 1rem;">

      <!-- Integrante mais usado -->
      <div style="background: #1e1e2e; padding: 1.5rem; border-radius: 8px; color: white;">
        <h4 style="color: #aaa;">⭐ Integrante Mais Usado</h4>
        <p style="font-size: 1.5rem; font-weight: bold;">{{ integranteMaisUsado?.nome }}</p>
        <p style="color: #aaa;">{{ integranteMaisUsado?.funcao }}</p>
      </div>

      <!-- Clube mais recorrente -->
      <div style="background: #1e1e2e; padding: 1.5rem; border-radius: 8px; color: white;">
        <h4 style="color: #aaa;">🏆 Clube Mais Recorrente</h4>
        <p style="font-size: 1.5rem; font-weight: bold;">{{ clubeMaisRecorrente }}</p>
      </div>

      <!-- Função mais recorrente -->
      <div style="background: #1e1e2e; padding: 1.5rem; border-radius: 8px; color: white;">
        <h4 style="color: #aaa;">🎯 Função Mais Recorrente</h4>
        <p style="font-size: 1.5rem; font-weight: bold;">{{ funcaoMaisRecorrente }}</p>
      </div>

      <!-- Time mais recorrente -->
      <div style="background: #1e1e2e; padding: 1.5rem; border-radius: 8px; color: white;">
        <h4 style="color: #aaa;">👥 Time Mais Recorrente</h4>
        <ul style="padding-left: 1rem;">
          <li v-for="nome in integrantesTimeMaisRecorrente" :key="nome">{{ nome }}</li>
        </ul>
      </div>

      <!-- Contagem de clubes -->
      <div style="background: #1e1e2e; padding: 1.5rem; border-radius: 8px; color: white;">
        <h4 style="color: #aaa;">📊 Aparições por Clube</h4>
        <div v-for="(qtd, clube) in contagemClubes" :key="clube" style="margin-bottom: 0.5rem;">
          <span>{{ clube }}</span>
          <span style="float: right; font-weight: bold;">{{ qtd }}x</span>
        </div>
      </div>

      <!-- Contagem de funções -->
      <div style="background: #1e1e2e; padding: 1.5rem; border-radius: 8px; color: white;">
        <h4 style="color: #aaa;">📋 Contagem por Função</h4>
        <div v-for="(qtd, funcao) in contagemFuncoes" :key="funcao" style="margin-bottom: 0.5rem;">
          <span>{{ funcao }}</span>
          <span style="float: right; font-weight: bold;">{{ qtd }}x</span>
        </div>
      </div>

    </div>
  </div>
</template>