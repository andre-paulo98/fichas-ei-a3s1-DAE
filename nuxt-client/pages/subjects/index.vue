<template>
  <!-- easy components usage, already shipped with bootstrap css-->
  <b-container>
    <!-- try to remove :fields=”fields” to see the magic -->
    <b-table striped over :items="subjects" :fields="fields">
      <template v-slot:cell(actions)="row">
        <nuxt-link
          class="btn btn-link"
          :to="`/subjects/${row.item.code}`"
        >
          Details
        </nuxt-link>
      </template>
    </b-table>
    <nuxt-link to="/subjects/create">
      Create a New Subject
    </nuxt-link>|
    <nuxt-link to="/">
      Back
    </nuxt-link>
  </b-container>
</template>
<script>
export default {
  data () {
    return {
      fields: ['code', 'name', 'courseYear', 'scholarYear', 'courseName', 'actions'],
      subjects: []
    }
  },
  created () {
    this.$axios.$get('/api/subjects')
      .then((subjects) => {
        this.subjects = subjects
      })
  }
}
</script>
<style>
</style>
