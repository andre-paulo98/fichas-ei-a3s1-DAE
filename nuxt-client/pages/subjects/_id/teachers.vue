<template>
  <!-- easy components usage, already shipped with bootstrap css-->
  <b-container>
    <!-- try to remove :fields=”fields” to see the magic -->
    <b-table striped over :items="teachers" :fields="fields">
      <template v-slot:cell(actions)="row">
        <button
          class="btn btn-link"
          @click="apagar(`${row.item.id}`)">
          Remove
        </button>
      </template>
    </b-table>
    <select v-model="selected">
      <option v-for="teacher in possibleTeachers" :key="teacher.id">
        {{ teacher.name }}
      </option>
    </select>
    <button
      class="btn btn-link"
      @click="add()">
      Add
    </button> |
    <nuxt-link :to="`/subjects/${id}`">
      Back
    </nuxt-link>
  </b-container>
</template>
<script>
export default {
  data () {
    return {
      fields: ['id', 'name', 'email', 'courseName', 'actions'],
      teachers: [],
      possibleTeachers: [],
      selected: ''
    }
  },
  computed: {
    id () {
      return this.$route.params.id
    }
  },
  created () {
    this.$axios.$get(`/api/subjects/${this.id}/teachers/`)
      .then((teachers) => { this.teachers = teachers })
      .then(() => this.$axios.$get('/api/teachers/'))
      .then((teachers) => {
        const ids = this.teachers.map(x => x.id)
        this.possibleTeachers = teachers.filter(x => !ids.includes(x.id))
      })
  },
  methods: {
    apagar (id) {
      event.preventDefault()
      this.$axios.$delete(`/api/subjects/${this.id}/teachers/${id}`).then(() => {
        window.location.reload()
      })
    },
    add () {
      this.$axios.$post(`/api/subjects/${this.id}/teachers/${this.selected}`).then(() => {
        window.location.reload()
      })
    }
  }
}
</script>
<style>
</style>
