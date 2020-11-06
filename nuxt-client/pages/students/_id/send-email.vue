<template>
  <div>
    <h1>Send an E-mail to Student {{ student.name }}</h1>
    <form @submit.prevent="send">
      <div>
        subject:
        <input
          v-model="subject"
          type="text"
        >
      </div>
      <div>
        message:
        <textarea
          v-model="message"
          name="message"
        >
        </textarea>
      </div>
      <nuxt-link :to="`/students`">
        Go to Students
      </nuxt-link>
      &nbsp;
      <nuxt-link :to="`/students/${id}`">
        Go to Student Details
      </nuxt-link>
      &nbsp;
      <button @click.prevent="send">
        SEND
      </button>
    </form>
  </div>
</template>
<script>
export default {
  data () {
    return {
      student: {},
      subject: null,
      message: null
    }
  },
  computed: {
    id () {
      return this.$route.params.id
    }
  },
  created () {
    this.$axios.$get(`/api/students/${this.id}`)
      .then((student) => { this.student = student })
  },
  methods: {
    send () {
      this.$axios.$post(`/api/students/${this.id}/email/send`, {
        subject: this.subject,
        message: this.message
      })
        .then((msg) => {
          this.$toast.success(msg).goAway(1500)
        })
        .catch((error) => {
          this.$toast.error('error sending the e-mail' + error).goAway(3000)
        })
    }
  }
}
</script>
