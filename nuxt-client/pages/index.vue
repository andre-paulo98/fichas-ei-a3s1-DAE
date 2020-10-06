<template>
  <!-- easy components usage, already shipped with bootstrap css-->
  <b-container>
    <!-- try to remove :fields=”fields” to see the magic -->
    <b-table striped over :items="students" :fields="fields" />
    <nuxt-link to="/create">Create a New Student</nuxt-link>
  </b-container>
</template>
<script>
export default {
  data () {
    return {
      fields: ['id', 'name', 'email'],
      students: []
    }
  },
  created () {
    this.$axios.$get('/api/students')
      .then((students) => {
        this.students = students
      })
  }
}
</script>
<style>
</style>
