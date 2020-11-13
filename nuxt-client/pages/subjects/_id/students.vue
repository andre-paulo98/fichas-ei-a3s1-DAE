<template>
  <b-container>
    <b-table striped over :items="students" :fields="fields">
      <template v-slot:cell(actions)="row">
        <button
          class="btn btn-link"
          @click="apagar(`${row.item.id}`)"
        >
          Remove
        </button>
      </template>
    </b-table>
    <label>
      <select v-model="selected">
        <option v-for="student in possibleStudents" :key="student.id">
          {{ student.name }}
        </option>
      </select>
    </label>
    <button
      class="btn btn-link"
      @click="add()"
    >
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
      students: [],
      possibleStudents: [],
      selected: ''
    }
  },
  computed: {
    id () {
      return this.$route.params.id
    }
  },
  created () {
    this.$axios.$get(`/api/subjects/${this.id}/students/`)
      .then((students) => { this.students = students })
      .then(() => this.$axios.$get('/api/students/'))
      .then((students) => {
        const ids = this.students.map(x => x.id)
        this.possibleStudents = students.filter(x => !ids.includes(x.id))
      })
  },
  methods: {
    apagar (id) {
      event.preventDefault()
      this.$axios.$delete(`/api/subjects/${this.id}/students/${id}`).then(() => {
        window.location.reload()
      })
    },
    add () {
      this.$axios.$post(`/api/subjects/${this.id}/students/${this.selected}`).then(() => {
        window.location.reload()
      })
    }
  }
}
</script>
<style>
</style>
