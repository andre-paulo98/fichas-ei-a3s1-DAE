<template>
  <b-container>
    <h4>Subject Details:</h4>
    <p>Name: {{ course.name }}</p>
    <p>Course Year: {{ course.courseYear }}</p>
    <p>Scholar Year: {{ course.scholarYear }}</p>
    <p>Course Name: {{ course.courseName }}</p>
    <nuxt-link :to="`/subjects/${id}/students`">
      Students
    </nuxt-link>|
    <nuxt-link :to="`/subjects/${id}/teachers`">
      Teachers
    </nuxt-link>|
    <nuxt-link :to="`/subjects/${id}/update`">
      Update
    </nuxt-link>|
    <button @click="apagar(`${id}`)">
      Delete
    </button>|
    <nuxt-link to="/subjects/">
      Back
    </nuxt-link>
  </b-container>
</template>
<script>
export default {
  data () {
    return {
      course: {}
    }
  },
  computed: {
    id () {
      return this.$route.params.id
    }
  },
  created () {
    this.$axios.$get(`/api/subjects/${this.id}`)
      .then((course) => { this.course = course || {} })
  },
  methods: {
    apagar (id) {
      event.preventDefault()
      this.$axios.$delete(`/api/subjects/${id}`).then(() => {
        this.$router.push({
          path: '/subjects/'
        })
      })
    }
  }
}
</script>
