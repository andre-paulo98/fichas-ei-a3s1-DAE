<template>
  <b-container>
    <h3>Login into Academics Management</h3>
    <b-form @submit.prevent="onSubmit" @reset="onReset">
      <b-form-group label="Id" description="Enter your id">
        <b-input
          v-model.trim="id"
          name="id"
          placeholder="Your id"
          required
        />
      </b-form-group>
      <b-form-group label="Password" description="Enter your password">
        <b-input
          v-model="password"
          name="password"
          type="password"
          placeholder="Your password"
          required
        />
      </b-form-group>
      <b-button type="reset" class="btn-warning">
        Reset
      </b-button>
      <b-button type="submit" class="btn-success">
        Submit
      </b-button>
    </b-form>
  </b-container>
</template>
<script>
export default {
  auth: false,
  data () {
    return {
      id: null,
      password: null
    }
  },
  methods: {
    onSubmit () {
      const promise = this.$auth.loginWith('local', {
        data: {
          id: this.id,
          password: this.password
        }
      })
      promise.then(() => {
        this.$toast.success('You are logged in!').goAway(1500)
        // check if the user $auth.user object is set
        // TODO redirect based on the user role
        // eg:
        if (this.$auth.user.groups.includes('Teacher')) {
          this.$router.push('/subjects')
        } else if (this.$auth.user.groups.includes('Administrator')) {
          this.$router.push('/')
        } else if (this.$auth.user.groups.includes('Student')) {
          this.$router.push(`/students/${this.$auth.user.sub}`)
        }
      })
      promise.catch(() => {
        this.$toast.error('Sorry, you cannot login. Ensure your credentials are correct').goAway(1500)
      })
    },
    onReset () {
      this.id = null
      this.password = null
    }
  }
}
</script>
