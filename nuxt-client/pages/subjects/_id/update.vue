<template>
  <form @submit.prevent="create">
    <div>
      <label>
        course:
        <select v-model="courseCode">
          <template v-for="course in courses">
            <option :key="course.id" :value="course.id">
              {{ course.name }}
            </option>
          </template>
        </select>
      </label>
    </div>
    <div>
      <label> name:
        <input v-model="name" type="text">
      </label>
    </div>
    <div>
      <label> course year:
        <input v-model="courseYear" type="text">
      </label>
    </div>
    <div>
      <label> scholar year:
        <input v-model="scholarYear" type="number">
      </label>
    </div>
    <nuxt-link :to="`/subjects/${id}`">
      Return
    </nuxt-link>
    <button type="reset">
      RESET
    </button>
    <button @click.prevent="update">
      UPDATE
    </button>
  </form>
</template>
<script>
export default {
  data () {
    return {
      name: null,
      courseYear: null,
      scholarYear: null,
      courseCode: null,
      courses: []
    }
  },
  computed: {
    id () {
      return this.$route.params.id
    }
  },
  created () {
    this.$axios.$get(`/api/subjects/${this.id}`)
      .then((subject) => {
        this.name = subject.name
        this.courseYear = subject.courseYear
        this.scholarYear = subject.scholarYear
        this.courseCode = subject.courseCode
      })
      // .then((courses) => { this.courses = courses })
    this.$axios.$get('/api/courses')
      .then((courses) => { this.courses = courses })
  },
  methods: {
    update () {
      this.$axios.$put(`/api/subjects/${this.id}`, {
        courseCode: this.courseCode,
        courseYear: this.courseYear,
        name: this.name,
        scholarYear: this.scholarYear
      })
        .then(() => {
          this.$router.push(`/subjects/${this.id}`)
        })
    }
  }
}
</script>
